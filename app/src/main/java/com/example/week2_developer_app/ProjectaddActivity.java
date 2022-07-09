package com.example.week2_developer_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectaddActivity extends AppCompatActivity {

    private Button projectaddbtn;
    private String name;
    private String email;


    private String convertTimestampTodate(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = sdf.format(time);
        return date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        projectaddbtn = (Button) findViewById(R.id.projectaddbtn);

        projectaddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String writer = name;
                String writer_email = email;
                String title = ((EditText)findViewById(R.id.title)).getText().toString();
                String content = ((EditText)findViewById(R.id.content)).getText().toString();
                String field = ((EditText)findViewById(R.id.field)).getText().toString();
                int level = Integer.parseInt(((EditText)findViewById(R.id.level)).getText().toString());
                int headcount = Integer.parseInt(((EditText)findViewById(R.id.headcount)).getText().toString());
                String language = ((EditText)findViewById(R.id.language)).getText().toString();
                String time = ((EditText)findViewById(R.id.time)).getText().toString();
                String regdata = convertTimestampTodate(System.currentTimeMillis());


                ProjectApi service =  RetrofitClient.getClient().create(ProjectApi.class);

                Project data = new Project(0, writer, writer_email, title, content, field, level, headcount, language, time, regdata);

                service.addProject(data).enqueue(new Callback<ProjectResponse>(){
                    @Override
                    public void onResponse(Call<ProjectResponse> call, Response<ProjectResponse> response) {
                        ProjectResponse result = response.body();
                        if(result.getCode() == 200) {
                            Toast.makeText(getApplicationContext(), "추가되었습니다!", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProjectResponse> call, Throwable t) {

                    }
                });
            }
        });
    }


}
