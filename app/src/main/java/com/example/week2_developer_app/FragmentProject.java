package com.example.week2_developer_app;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class FragmentProject extends Fragment {

    private ArrayList<Project> projectlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private AdapterProject adapter;

    private FragmentProjectBinding binding;

    private String getJsonString()
    {
        String json = "";
        try {
            InputStream is = getActivity().getAssets().open("Projects.json");
            int fileSize = is.available();
            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return json;
    }
    private void jsonParsing(String json)
    {
        try{
            projectlist = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);

            JSONArray projectArray = jsonObject.getJSONArray("Projects");

            for(int i=0; i<projectArray.length(); i++)
            {
                JSONObject obj = projectArray.getJSONObject(i);

                Project project = new Project(
                        obj.getInt("id"),
                        obj.getString("title"),
                        obj.getString("content"),
                        obj.getString("field"),
                        obj.getString("level"),
                        obj.getString("headcount"),
                        obj.getString("language"),
                        obj.getString("time")
                        );

                projectlist.add(project);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
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
                jsonParsing(getJsonString());
                Project project = new Project(
                        1, "a", "b", "c", "d", "e", "f", "g"
                );
                projectlist.add(project);
                System.out.println(projectlist);
                adapter = new AdapterProject(projectlist);
                binding.projectlistview.setAdapter(adapter);
                binding.refreshProject.setRefreshing(false);
            }
        });

//        binding.searchView
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


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        projectlist.clear();
        jsonParsing(getJsonString());
        binding = FragmentProjectBinding.inflate(getLayoutInflater());
    }

}