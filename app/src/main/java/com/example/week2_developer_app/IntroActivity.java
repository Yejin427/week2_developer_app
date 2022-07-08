package com.example.week2_developer_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

        Log.d("str", userinfo.toString());
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
                    userinfo.add(user.getId().toString());
                    userinfo.add(user.getKakaoAccount().getProfile().getNickname());
                    userinfo.add(user.getKakaoAccount().getEmail());

                    Log.d("str", userinfo.toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
            return null;
        });
    }
}