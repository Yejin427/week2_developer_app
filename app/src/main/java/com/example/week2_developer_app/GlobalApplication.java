package com.example.week2_developer_app;

import android.app.Application;
import android.util.Log;

import com.kakao.sdk.common.KakaoSdk;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, "d0460decc59c9f319e6315af71a94335");
    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.249.19.191:80/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        Log.d("str", "소켓성공");
//        Log.d("str", ""//// "+mSocket.toString());
        return mSocket;
    }
}
