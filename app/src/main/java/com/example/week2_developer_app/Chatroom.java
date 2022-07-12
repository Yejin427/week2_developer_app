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
    @SerializedName("chat_name")
    private String chat_name;
    @SerializedName("lastchat")
    private String lastchat;
    @SerializedName("regdata")
    private String regdata;

    public Chatroom(int chat_id, String chat_name, String lastchat, String regdata){
        this.chat_id = chat_id;
        this.chat_name = chat_name;
        this.lastchat = lastchat;
        this.regdata = regdata;
    }

    public int getChat_id() { return chat_id; }
    public String getChat_name() {return chat_name;}
    public String getLastchat() { return lastchat;}
    public String getRegdata() { return regdata;}
}


interface ChatroomApi{
    @POST("/user/getchatroom")
    Call<List<Chatroom>> getChatroom(@Body Chatroom_myData data);
    @POST("/user/addchatroommember")
    Call<ChatroomResponse> addChatroommember(@Body Chatroom_memberData data);
}

class Chatroom_myData{
    @SerializedName("email")
    String email;
    public Chatroom_myData(String email){
        this.email = email;
    }
}

class Chatroom_memberData{
    @SerializedName("chat_id")
    int chat_id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("regdata")
    String regdata;

    public Chatroom_memberData(int chat_id, String name, String email, String regdata){
        this.chat_id = chat_id;
        this.name = name;
        this.email = email;
        this.regdata = regdata;
    }
}

class ChatroomResponse {
    @SerializedName("code")
    private int code;
    public int getCode() {
        return code;
    }
}
