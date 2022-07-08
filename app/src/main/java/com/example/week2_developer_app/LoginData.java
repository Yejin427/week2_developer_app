package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("userEmail")
    String userEmail;

    public LoginData(String userEmail){
        this.userEmail = userEmail;
    }
}
