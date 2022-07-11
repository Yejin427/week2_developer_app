package com.example.week2_developer_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.week2_developer_app.databinding.ActivityDetailprojectBinding;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectdetailActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private ImageButton btn_call;
    private ImageButton btn_delete;
    private String name;
    private String email;

    ActivityDetailprojectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailprojectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        int proj_id = intent.getIntExtra("proj_id" , 0);
        //binding.projId.setText(Integer.toString(intent.getIntExtra("proj_id" , 0)));
        binding.writer.setText(intent.getStringExtra("writer"));
        binding.writerEmail.setText(intent.getStringExtra("writer_email"));
        binding.title.setText(intent.getStringExtra("title"));
        binding.content.setText(intent.getStringExtra("content"));
        binding.field.setText(intent.getStringExtra("field"));
        binding.level.setText(Integer.toString(intent.getIntExtra("level", 0)));
        binding.headcount.setText(Integer.toString(intent.getIntExtra("headcount", 0)));
        binding.language.setText(intent.getStringExtra("language"));
        binding.time.setText(intent.getStringExtra("time"));
        binding.regdata.setText(intent.getStringExtra("regdata"));

        if(email.equals(intent.getStringExtra("writer_email")))
            binding.btnDelete.setVisibility(View.VISIBLE);


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Listener listener2 = new Listener() {
                    @Override
                    public void returnyes(String str) {

                        ProjectApi service = RetrofitClient.getClient().create(ProjectApi.class);
                        Project_deleteData data = new Project_deleteData(proj_id);

                        service.deleteProject(data).enqueue(new Callback<ProjectResponse>(){
                            @Override
                            public void onResponse(Call<ProjectResponse> call, Response<ProjectResponse> response) {
                                ProjectResponse result = response.body();
                                if(result.getCode() == 200) {
                                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT);
                                    finish();
                                }
                            }
                            @Override
                            public void onFailure(Call<ProjectResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "에러발생.", Toast.LENGTH_SHORT);
                            }
                        });
                    }
                };

                DialogProjectdelete dialog = new DialogProjectdelete(ProjectdetailActivity.this, listener2);
                dialog.showDialog();
            }
        });
    }
}

