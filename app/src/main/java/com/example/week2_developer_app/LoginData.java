package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface LoginApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);
    @POST("/user/signup")
    Call<SignupResponse> userSignup(@Body SignupData data);
}

public class LoginData {

    @SerializedName("userEmail")
    String userEmail;

    public LoginData(String userEmail){
        this.userEmail = userEmail;
    }
}

class LoginResponse {

    @SerializedName("code")
    private int code;

    public int getCode() {
        return code;
    }
}


