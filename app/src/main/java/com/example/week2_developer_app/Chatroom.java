package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class Chatroom {

    @SerializedName("chat_id")
    private int chat_id;
    @SerializedName("email_1")
    private String email_1;
    @SerializedName("name_1")
    private String name_1;
    @SerializedName("email_2")
    private String email_2;
    @SerializedName("name_2")
    private String name_2;
    @SerializedName("lastchat")
    private String lastchat;
    @SerializedName("regdata")
    private String regdata;

    public Chatroom(int chat_id, String email_1, String name_1, String email_2, String name_2,String lastchat, String regdata){
        this.chat_id = chat_id;
        this.email_1 = email_1;
        this.name_1 = name_1;
        this.email_2 = email_2;
        this.name_2 = name_2;
        this.lastchat = lastchat;
        this.regdata = regdata;
    }

    public int getChat_id() { return chat_id; }
    public String getEmail_1() {return email_1; }
    public String getName_1() {return name_1; }
    public String getEmail_2() {return email_2; }
    public String getName_2() {return name_2; }
    public String getLastchat() { return lastchat;}
    public String getRegdata() { return regdata;}
}


interface ChatroomApi{
    @POST("/user/getchatroom")
    Call<List<Chatroom>> getChatroom(@Body Chatroom_myData data);
    @POST("/user/addchatroom")
    Call<ChatroomResponse> addChatroom(@Body Chatroom chatroom);
}

class Chatroom_myData{
    @SerializedName("email")
    String email;
    public Chatroom_myData(String email){
        this.email = email;
    }
}
class ChatroomResponse {
    @SerializedName("code")
    private int code;
    public int getCode() {
        return code;
    }
}

class Chatroom_deleteData{
    @SerializedName("chat_id")
    int chat_id;
    public Chatroom_deleteData(int chat_id){this.chat_id = chat_id;}
}