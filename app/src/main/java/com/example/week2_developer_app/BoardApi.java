package com.example.week2_developer_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BoardApi {
    @POST("/user/addboard")
    Call<JoinBoardResponse.AddResponse> userAddBoard(@Body JoinBoardData.AddData data);
    @GET("/user/returnboards")
    Call<JoinBoardResponse.getResponse> getBoard();
    @POST("/user/deleteboard")
    Call<JoinBoardResponse.DeleteResponse> userDeleteBoard(@Body JoinBoardData.DeleteData data);
}
