package com.example.week2_developer_app;

import android.app.Application;
import android.util.Log;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class ChatApplication extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://192.249.19.191:80/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        Log.d("str", "소켓성공");
        return mSocket;
    }
}