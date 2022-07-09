package com.example.week2_developer_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.week2_developer_app.databinding.ActivitySignupBinding;
import com.example.week2_developer_app.databinding.FragmentProjectBinding;
import com.kakao.auth.Session;
import com.kakao.sdk.user.UserApi;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Url;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private Button signupbtn;
    private String name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        Log.d(name, email);
        signupbtn = (Button)findViewById(R.id.signupbtn);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int age = Integer.parseInt(((EditText)findViewById(R.id.age)).getText().toString());
                String gender = ((EditText)findViewById(R.id.gender)).getText().toString();
                String phonenum = ((EditText)findViewById(R.id.phonenum)).getText().toString();
                String field = ((EditText)findViewById(R.id.field)).getText().toString();
                int level = Integer.parseInt(((EditText)findViewById(R.id.level)).getText().toString());
                String msg = ((EditText)findViewById(R.id.msg)).getText().toString();

                Log.d("d", gender);
                Log.d("d", phonenum);
                Log.d("d", field);
                Log.d("d", msg);

                LoginApi signup =  RetrofitClient.getClient().create(LoginApi.class);
                SignupData data = new SignupData(email, name, age, gender, phonenum, field, level, msg);

                signup.userSignup(data).enqueue(new Callback<SignupResponse>(){
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                        SignupResponse result = response.body();
                        if (result.getCode() == 200) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                            startActivity(intent);
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {
                        Toast.makeText(SignupActivity.this, "로그인 에러발생", Toast.LENGTH_SHORT).show();
                        Log.e("로그인 에러발생", t.getMessage());
                        t.printStackTrace();
                        recreate();
                    }
                });
            }
        });
    }


}