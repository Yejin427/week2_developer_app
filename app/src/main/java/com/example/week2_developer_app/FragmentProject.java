package com.example.week2_developer_app;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

import com.example.week2_developer_app.databinding.ActivityMainBinding;
import com.example.week2_developer_app.databinding.FragmentProjectBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProject extends Fragment {

    private ArrayList<Project> projectlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private AdapterProject adapter;
    private String name;
    private String email;

    private FragmentProjectBinding binding;


    private void getProjectlist(){

        projectlist.clear();
        ProjectApi service = RetrofitClient.getClient().create(ProjectApi.class);
        Call<List<Project>> call = service.getProject();
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                Log.d("response", response.body().toString());
                projectlist.addAll(response.body());

                adapter = new AdapterProject(projectlist);
                binding.projectlistview.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
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
                binding.projectlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.projectlistview.setAdapter(adapter);
                return false;
            }
        });

        binding.refreshProject.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(requireContext(), "Refeshed", Toast.LENGTH_SHORT).show();
                getProjectlist();
                binding.refreshProject.setRefreshing(false);
                Log.d("refresh", projectlist.toString());
            }
        });

        binding.projectlistview.setHasFixedSize(true);
        binding.projectlistview.addItemDecoration(new DividerItemDecoration(rootView.getContext(), 1));

        adapter = new AdapterProject(projectlist);
        binding.projectlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.projectlistview.setItemAnimator(new DefaultItemAnimator());
        binding.projectlistview.setAdapter(adapter);

//        AdapterProject.setOnItemClicklistener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterProject.ViewHolder holder, View view, int position) {
//                Item item = mAdapter.getItem(position);
//                Toast.makeText(getActivity().getApplicationContext(), item.getName() +": 전화를 거시겠습니까?",
//                        Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(getActivity(),CallActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//                Project curitem = projectlist.get(position);
//                String pNum = "tel:"+curitem.getNumber();
//                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(pNum)));
//
//            }
//        });
        binding.addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProjectaddActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getProjectlist();
        Log.d("str", this.getArguments().getString("name"));
        Log.d("str", this.getArguments().getString("email"));
        name = this.getArguments().getString("name");
        email = this.getArguments().getString("email");
        binding = FragmentProjectBinding.inflate(getLayoutInflater());
    }
}