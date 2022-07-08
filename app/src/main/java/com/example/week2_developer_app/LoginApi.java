package com.example.week2_developer_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);
    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);
}
