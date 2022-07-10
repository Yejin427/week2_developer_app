package com.example.week2_developer_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.week2_developer_app.databinding.ActivityChatmsgBinding;
import com.example.week2_developer_app.databinding.ActivityDetailprojectBinding;
import com.example.week2_developer_app.databinding.FragmentChatroomBinding;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {


    private ArrayList<Chatmsg> chatmsglist = new ArrayList<>();
    private AdapterChatmsg adapter;
    private String name;
    private String email;
    private int chat_id;
    private ActivityChatmsgBinding binding;

    private void getChatmsglist(){

        chatmsglist.clear();
        ChatmsgApi service = RetrofitClient.getClient().create(ChatmsgApi.class);
        Call<List<Chatmsg>> call = service.getChatmsg(new Chatmsg_myData(chat_id));
        call.enqueue(new Callback<List<Chatmsg>>() {
            @Override
            public void onResponse(Call<List<Chatmsg>> call, Response<List<Chatmsg>> response) {

                chatmsglist.clear();
                for(int i=0; i < response.body().size() ;i++){
                    if(response.body().get(i).getSender_name().equals(name))
                        response.body().get(i).setviewType(1);
                    chatmsglist.add(response.body().get(i));
                }
                adapter.notifyDataSetChanged();
                adapter = new AdapterChatmsg(chatmsglist);
                binding.chatmsglistview.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Chatmsg>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatmsgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getChatmsglist();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        chat_id = intent.getIntExtra("chat_id", 2);

//        binding.topAppBarName.setTitle();

        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                binding.chatmsglistview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.chatmsglistview.setAdapter(adapter);
                return false;
            }
        });

        binding.refreshChatmsg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Refeshed", Toast.LENGTH_SHORT).show();
                getChatmsglist();
                binding.refreshChatmsg.setRefreshing(false);
                Log.d("refresh", chatmsglist.toString());
            }
        });

        binding.chatmsglistview.setHasFixedSize(true);
        binding.chatmsglistview.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));

        adapter = new AdapterChatmsg(chatmsglist);
        binding.chatmsglistview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.chatmsglistview.setItemAnimator(new DefaultItemAnimator());
        binding.chatmsglistview.setAdapter(adapter);



    }
}

