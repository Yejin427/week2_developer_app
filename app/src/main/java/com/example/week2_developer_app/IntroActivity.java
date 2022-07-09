package com.example.week2_developer_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class IntroActivity extends AppCompatActivity {
    private Button loginbtn;
    ArrayList<String> userinfo = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginbtn = (Button)findViewById(R.id.button);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(IntroActivity.this)){
                    login();
                }
                else{
                    accountLogin();
                }
            }
        });
    }

    public void login(){
        String TAG = "login()";

        UserApiClient.getInstance().loginWithKakaoTalk(IntroActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void accountLogin(){
        String TAG = "accountLogin()";
        ArrayList<String> tmp = new ArrayList<String>();
        UserApiClient.getInstance().loginWithKakaoAccount(IntroActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void getUserInfo(){
        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {}
            else {
                System.out.println("로그인 완료");
                Log.i(TAG, user.toString());
                {
                    userinfo.add(user.getKakaoAccount().getProfile().getNickname());
                    userinfo.add(user.getKakaoAccount().getEmail());

                    Log.d("str", userinfo.toString());

                    LoginApi login =  RetrofitClient.getClient().create(LoginApi.class);
                    LoginData data = new LoginData(userinfo.get(1));

                    login.userLogin(data).enqueue(new Callback<LoginResponse>(){
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            LoginResponse result = response.body();
//
                            if(result.getCode() == 200){
                                Log.d("tag", "일치하는 아이디가 있습니다.");
                                Toast.makeText(IntroActivity.this, userinfo.get(1)+"님 "+"환영합니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Log.d("tag", "일치하는 아이디가 없습니다.");
                                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                                intent.putExtra("name", userinfo.get(0));
                                intent.putExtra("email", userinfo.get(1));
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(IntroActivity.this, "로그인 에러발생", Toast.LENGTH_SHORT).show();
                            Log.e("로그인 에러발생", t.getMessage());
                            t.printStackTrace();
                            recreate();
                        }
                    });


                }
            }
            return null;
        });
    }
}

interface OnItemClickListener {
    void onItemClick(AdapterProject.ViewHolder holder, View v, int pos);
}