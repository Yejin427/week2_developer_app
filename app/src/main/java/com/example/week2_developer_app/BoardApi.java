package com.example.week2_developer_app;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface BoardApi {
    @POST("/user/addboard")
    Call<JoinBoardResponse.AddResponse> userAddBoard(@Body JoinBoardData.AddData data);
    @GET("/user/returnboards")
    Call<ArrayList<Board>> getBoard();
    @POST("/user/deleteboard")
    Call<JoinBoardResponse.DeleteResponse> userDeleteBoard(@Body JoinBoardData.DeleteData data);
    @Multipart
    @POST("/user/imageadd")
    Call<JoinBoardResponse.PictureResponse> uploadImage(@Part MultipartBody.Part file);
}
