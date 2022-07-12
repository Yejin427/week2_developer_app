package com.example.week2_developer_app;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.week2_developer_app.databinding.FragmentChatroomBinding;
import com.example.week2_developer_app.databinding.FragmentProjectBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChatroom extends Fragment implements Listener {

    private ArrayList<Chatroom> chatroomlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private AdapterChatroom adapter;
    private String name;
    private String email;

    private FragmentChatroomBinding binding;


    private void getChatroomlist(){

        chatroomlist.clear();
        ChatroomApi service = RetrofitClient.getClient().create(ChatroomApi.class);
        Call<List<Chatroom>> call = service.getChatroom(new Chatroom_myData(email));
        call.enqueue(new Callback<List<Chatroom>>() {
            @Override
            public void onResponse(Call<List<Chatroom>> call, Response<List<Chatroom>> response) {
                Log.d("response", response.body().toString());
                chatroomlist.clear();
                chatroomlist.addAll(response.body());

                adapter.notifyDataSetChanged();
                adapter = new AdapterChatroom(chatroomlist);
                binding.chatlistview.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Chatroom>> call, Throwable t) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = binding.getRoot();

        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.setViewtype(0);
                binding.chatlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.chatlistview.setAdapter(adapter);
                return false;
            }
        });

        binding.refreshProject.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(requireContext(), "Refeshed", Toast.LENGTH_SHORT).show();
                getChatroomlist();
                binding.refreshProject.setRefreshing(false);
                Log.d("refresh", chatroomlist.toString());
            }
        });

        binding.chatlistview.setHasFixedSize(true);
//        binding.chatlistview.addItemDecoration(new DividerItemDecoration(rootView.getContext(), 1));

        adapter = new AdapterChatroom(chatroomlist);
        binding.chatlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.chatlistview.setItemAnimator(new DefaultItemAnimator());
        binding.chatlistview.setAdapter(adapter);

        adapter.setOnItemClicklistener(new OnItemClickListener2() {
            @Override
            public void onItemClick(AdapterChatroom.ViewHolder holder, View v, int pos) {

                Chatroom obj = chatroomlist.get(pos);
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("chat_id", obj.getChat_id());
                intent.putExtra("chat_name", obj.getChat_name());
                intent.putExtra("type", "no");
                intent.putExtra("regdata", "old");
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getChatroomlist();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getChatroomlist();
        name = this.getArguments().getString("name");
        email = this.getArguments().getString("email");
        binding = FragmentChatroomBinding.inflate(getLayoutInflater());
    }

    @Override
    public void returnyes(String str) {}
}

