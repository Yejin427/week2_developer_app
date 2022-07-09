package com.example.week2_developer_app;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BoardApi {
    @POST("/user/addboard")
    Call<JoinBoardResponse.AddResponse> userAddBoard(@Body JoinBoardData.AddData data);
    @GET("/user/returnboards")
    Call<ArrayList<Board>> getBoard();
    @POST("/user/deleteboard")
    Call<JoinBoardResponse.DeleteResponse> userDeleteBoard(@Body JoinBoardData.DeleteData data);
}
