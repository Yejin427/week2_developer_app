package com.example.week2_developer_app;

import java.sql.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.week2_developer_app.databinding.FragmentBoardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBoard extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private AdapterBoard adapterBoard;
    private LinearLayoutManager layoutManager;
    private ArrayList<Board> boardList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    String name;
    String email;
    FragmentBoardBinding binding;
    //TODO 게시판 데이터 불러오기


    private void getBoardList(){

        boardList.clear();
        BoardApi boardapi = RetrofitClient.getClient().create(BoardApi.class);

        boardapi.getBoard().enqueue(new Callback<ArrayList<Board>>() {
            @Override
            public void onResponse(Call<ArrayList<Board>> call, Response<ArrayList<Board>> response) {
                boardList.clear();
                boardList.addAll(response.body());

                adapterBoard.notifyDataSetChanged();
                adapterBoard = new AdapterBoard(boardList);
                binding.boardlistview.setAdapter(adapterBoard);
            }
            @Override
            public void onFailure(Call<ArrayList<Board>> call, Throwable t) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = binding.getRoot();

        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterBoard.getFilter().filter(newText);
                binding.boardlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.boardlistview.setAdapter(adapterBoard);
                return false;
            }
        });

        binding.refreshBoard.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(requireContext(), "Refeshed", Toast.LENGTH_SHORT).show();
                getBoardList();
                binding.refreshBoard.setRefreshing(false);
            }
        });

        binding.boardlistview.setHasFixedSize(true);
        binding.boardlistview.addItemDecoration(new DividerItemDecoration(rootView.getContext(), 1));

        adapterBoard = new AdapterBoard(boardList);
        binding.boardlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.boardlistview.setItemAnimator(new DefaultItemAnimator());
        binding.boardlistview.setAdapter(adapterBoard);


        //retroclient2를 통해 서버에서 boardlist data를 가져옴
        adapterBoard.setOnItemClicklistener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(AdapterBoard.boardViewHolder holder, View view, int pos) {
                //intent로 게시글 상세 화면으로 넘어감!
                Board curboard = boardList.get(pos);
                Intent intent = new Intent(getActivity(), DetailBoard.class);
                intent.putExtra("id", curboard.getId());
                intent.putExtra("writer", curboard.getWriter());
                intent.putExtra("title", curboard.getTitle());
                intent.putExtra("contents", curboard.getContents());
                intent.putExtra("regdata", curboard.getRegdata());
                intent.putExtra("likes", curboard.getLikes());

                startActivity(intent);

            }
        });

        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditBoard.class);
                intent.putExtra("username", name);
                intent.putExtra("useremail", email);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = FragmentBoardBinding.inflate(getLayoutInflater());
        name = this.getArguments().getString("name");
        email = this.getArguments().getString("email");

    }

    @Override
    public void onResume() {
        super.onResume();
        getBoardList();
    }
}