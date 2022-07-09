package com.example.week2_developer_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.sql.*;
import android.widget.Button;
import android.widget.Toast;

import com.example.week2_developer_app.databinding.AddBoardBinding;
import com.example.week2_developer_app.databinding.FragmentBoardBinding;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBoard extends AppCompatActivity {
    AddBoardBinding binding;
    @Override
    public void onCreate(Bundle SavedInstanceState){
        binding = AddBoardBinding.inflate(getLayoutInflater());

        super.onCreate(SavedInstanceState);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        //회원 정보 받기
        BoardApi boardapi = RetrofitClient.getClient().create(BoardApi.class);
        final String[] type = {null};
        //String picture = null;
        //질문 게시글 선택
        binding.question.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                type[0] = "Question";
            }
        });
        //정보 게시글 선택
        binding.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type[0] = "Information";
            }
        });
        //나가기 버튼
        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //갤러리 연결
                //이미지 추가........^^^^^^^
            }
        });
        //게시글 등록 버튼
        binding.sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String tts = timeToString(timestamp);
                JoinBoardData.AddData addData = new JoinBoardData.AddData("writer", type[0], binding.title.getText().toString(), binding.contents.getText().toString(), tts);
                boardapi.userAddBoard(addData).enqueue(new Callback<JoinBoardResponse.AddResponse>(){
                    @Override
                    public void onResponse(Call<JoinBoardResponse.AddResponse> call, Response<JoinBoardResponse.AddResponse> response) {
                        JoinBoardResponse.AddResponse result = response.body();
                        Toast.makeText(EditBoard.this, result.getCode(), Toast.LENGTH_SHORT).show();
                        if(result.getCode() == 200){
                            Log.d("tag", "업로드에 성공");
                            //업로드 성공 창....??
                            finish();
                        }
                        else{
                            Log.d("tag", "게시글 업로드 에러");
                        }
                    }

                    @Override
                    public void onFailure(Call<JoinBoardResponse.AddResponse> call, Throwable t) {
                        Toast.makeText(EditBoard.this, "게시글 업로드 에러", Toast.LENGTH_SHORT).show();
                        Log.e("게시글 업로드 에러", t.getMessage());
                        t.printStackTrace();
                    }
                });
            }
        });
    }
    public String timeToString(Timestamp timestamp){
        String ts = timestamp.toString();
        String year = ts.substring(0, 4);
        String month = ts.substring(5, 7);
        String day = ts.substring(8, 10);
        String hour = ts.substring(11, 13);
        String minute = ts.substring(14, 16);
        String sec = ts.substring(17, 19);
        return year+month+day+hour+minute+sec;
    }
}
