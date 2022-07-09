package com.example.week2_developer_app;

import android.os.Bundle;
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

public class FragmentBoard extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private AdapterBoard adapterBoard;
    private LinearLayoutManager layoutManager;
    private ArrayList<Board> boardList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    FragmentBoardBinding binding;
    //TODO 게시판 데이터 불러오기

    private String getJsonString() {
        String json = "";

        try {
            InputStream is = getActivity().getAssets().open("Board.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }
    private void jsonParsing(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray boardArray = jsonObject.getJSONArray("Board List");
            boardList.clear();

            int[] picarr = {R.drawable.icon_add, R.drawable.icon_dot};
            for (int i = 0; i < boardArray.length(); i++) {
                JSONObject boardObject = boardArray.getJSONObject(i);

                Board item = new Board(boardObject.getString("title"), boardObject.getString("contents"),
                        boardObject.getString("writer"), boardObject.getString("regDate"));
                item.setPicture(ContextCompat.getDrawable(getContext(), picarr[i]));
                boardList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                jsonParsing(getJsonString());
                adapterBoard = new AdapterBoard(boardList);
                binding.boardlistview.setAdapter(adapterBoard);
                binding.refreshBoard.setRefreshing(false);
            }
        });


        binding.boardlistview.setHasFixedSize(true);
        binding.boardlistview.addItemDecoration(new DividerItemDecoration(rootView.getContext(), 1));

        adapterBoard = new AdapterBoard(boardList);
        binding.boardlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.boardlistview.setItemAnimator(new DefaultItemAnimator());
        binding.boardlistview.setAdapter(adapterBoard);


        adapterBoard.setOnItemClicklistener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(AdapterBoard.boardViewHolder holder, View view, int pos) {
                //intent로 게시글 상세 화면으로 넘어감!
<<<<<<< Updated upstream
=======
                Intent intent = new Intent(getActivity(), DetailBoard.class);
                intent.putExtra("position", pos);
                startActivity(intent);
>>>>>>> Stashed changes
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        boardList.clear();
        jsonParsing(getJsonString());
        binding = FragmentBoardBinding.inflate(getLayoutInflater());
    }
}