package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

public class JoinResponse {

    @SerializedName("code")
    private int code;

    public int getCode() {
        return code;
    }
}