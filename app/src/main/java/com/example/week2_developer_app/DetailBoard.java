package com.example.week2_developer_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.week2_developer_app.databinding.DetailBoardBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBoard extends AppCompatActivity {
    DetailBoardBinding binding;
    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        binding = DetailBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String writer = intent.getStringExtra("writer");
        String title = intent.getStringExtra("title");
        String contents = intent.getStringExtra("contents");
        String regdata = intent.getStringExtra("regdata");
        int likes = intent.getIntExtra("likes", 0);

        binding.writer.setText(writer);
        binding.title.setText(title);
        binding.contents.setText(contents);
        binding.date.setText(regdata);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        BoardApi boardApi = RetrofitClient.getClient().create(BoardApi.class);

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete query....
                JoinBoardData.DeleteData deleteData = new JoinBoardData.DeleteData(id);
                boardApi.userDeleteBoard(deleteData).enqueue(new Callback<JoinBoardResponse.DeleteResponse>(){

                    @Override
                    public void onResponse(Call<JoinBoardResponse.DeleteResponse> call, Response<JoinBoardResponse.DeleteResponse> response) {
                        JoinBoardResponse.DeleteResponse result = response.body();
                        if(result.getCode() == 200){
                            Log.d("tag", "삭제에 성공");
                            //삭제 되었습니다 메시지....??
                            finish();
                        }
                        else{
                            Log.d("tag", "삭제 에러");
                        }
                    }

                    @Override
                    public void onFailure(Call<JoinBoardResponse.DeleteResponse> call, Throwable t) {
                        Toast.makeText(DetailBoard.this, "게시글 삭제 에러", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
