package com.example.week2_developer_app;


import android.content.Intent;
import android.net.Uri;
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

public class FragmentProject extends Fragment implements Listener {

    private ArrayList<Project> projectlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private AdapterProject adapter;

    private FragmentProjectBinding binding;


    private void getProjectlist(int viewtype){

        projectlist.clear();
        GetProject service = RetrofitClient.getClient().create(GetProject.class);
        Call<List<Project>> call = service.getProject();
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                Log.d("response", response.body().toString());
                projectlist.addAll(response.body());

                adapter = new AdapterProject(projectlist);
                adapter.setViewtype(viewtype);
                binding.projectlistview.setAdapter(adapter);
                binding.refreshProject.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
            }
        });
    }

    private void getmyProjectlist(int viewtype){

        projectlist.clear();
        ProjectApi service = RetrofitClient.getClient().create(ProjectApi.class);
        Project_myData data = new Project_myData(email);
        Call<List<Project>> call = service.getmyProject(data);
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                Log.d("response", response.body().toString());
                projectlist.addAll(response.body());

                adapter = new AdapterProject(projectlist);
                adapter.setViewtype(viewtype);
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
                adapter.setViewtype(0);
                binding.projectlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.projectlistview.setAdapter(adapter);
                return false;
            }
        });

        binding.refreshProject.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(requireContext(), "Refeshed", Toast.LENGTH_SHORT).show();
<<<<<<< Updated upstream
                getProjectlist();
=======
                getProjectlist(0);
                binding.refreshProject.setRefreshing(false);
>>>>>>> Stashed changes
                Log.d("refresh", projectlist.toString());
            }
        });

        binding.projectlistview.setHasFixedSize(true);
        binding.projectlistview.addItemDecoration(new DividerItemDecoration(rootView.getContext(), 1));


//        adapter = new AdapterProject(projectlist);
//        binding.projectlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
//        binding.projectlistview.setItemAnimator(new DefaultItemAnimator());
//        binding.projectlistview.setAdapter(adapter);

<<<<<<< Updated upstream
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
=======

        adapter.setOnItemClicklistener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterProject.ViewHolder holder, View v, int pos) {

            }
        });

        binding.addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProjectaddActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
>>>>>>> Stashed changes

        binding.deletebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getmyProjectlist(1);
                binding.deletebtn.setVisibility(View.INVISIBLE);
                binding.backbtn.setVisibility(View.VISIBLE);

                adapter.setOnItemClicklistener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterProject.ViewHolder holder, View v, int pos) {
                        Listener listener2 = new Listener() {
                            @Override
                            public void returnyes(String str) {
                                Log.d("str", str);
                                Log.d("str", holder.project_id.getText().toString());
                                int proj_id = Integer.parseInt(holder.project_id.getText().toString());

                                ProjectApi service = RetrofitClient.getClient().create(ProjectApi.class);
                                Project_deleteData data = new Project_deleteData(proj_id);
                                Log.d("확인", ""+proj_id);

                                service.deleteProject(data).enqueue(new Callback<ProjectResponse>(){
                                    @Override
                                    public void onResponse(Call<ProjectResponse> call, Response<ProjectResponse> response) {
                                        ProjectResponse result = response.body();
                                        if(result.getCode() == 200) {
                                            Log.d("확인", ""+result.getCode());
                                            getmyProjectlist(1);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ProjectResponse> call, Throwable t) {
                                        Log.d("실패", "실패");
                                    }
                                });
                            }
                        };

                        if(holder.viewType.getText().toString().equals("1")) {
                            DialogProjectdelete dialog = new DialogProjectdelete(getContext(), listener2);
                            dialog.showDialog();
                        }

                    }
                });
            }
        });

        binding.backbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getProjectlist(0);
                binding.deletebtn.setVisibility(View.VISIBLE);
                binding.backbtn.setVisibility(View.INVISIBLE);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream
        getProjectlist();
        binding = FragmentProjectBinding.inflate(getLayoutInflater());
    }

=======
        getProjectlist(0);
        Log.d("str", this.getArguments().getString("name"));
        Log.d("str", this.getArguments().getString("email"));
        name = this.getArguments().getString("name");
        email = this.getArguments().getString("email");
        binding = FragmentProjectBinding.inflate(getLayoutInflater());
    }

    @Override
    public void returnyes(String str) {

    }
}

interface Listener{
    void returnyes(String str);
>>>>>>> Stashed changes
}