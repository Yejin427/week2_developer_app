package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Chatmsg {

    @SerializedName("msg_id")
    private int msg_id;
    @SerializedName("msg")
    private String msg;
    @SerializedName("chat_id")
    private int chat_id;
    @SerializedName("sender_name")
    private String sender_name;
    @SerializedName("regdata")
    private String regdata;
    private int viewType;

    public Chatmsg(int msg_id, String msg, int chat_id, String sender_name, String regdata){
        this.msg_id = msg_id;
        this.msg = msg;
        this.chat_id = chat_id;
        this.sender_name = sender_name;
        this.regdata = regdata;
        this.viewType = 0;
    }

    public int getMsg_id() { return msg_id; }
    public int getChat_id() { return chat_id; }
    public String getSender_name() { return sender_name;}
    public String getMsg() { return msg;}
    public String getRegdata() { return regdata;}
    public int getviewType() { return viewType;}
    public void setviewType(int viewtype){
        this.viewType = viewtype;
    }
}

interface ChatmsgApi{
    @POST("/user/getchatmsg")
    Call<List<Chatmsg>> getChatmsg(@Body Chatmsg_myData data);
    @POST("/user/addchatmsg")
    Call<ChatmsgResponse> addChatmsg(@Body Chatmsg chatmsg);
}

class Chatmsg_myData{
    @SerializedName("chat_id")
    int chat_id;
    public Chatmsg_myData(int chat_id){
        this.chat_id = chat_id;
    }
}
class ChatmsgResponse {
    @SerializedName("code")
    private int code;
    public int getCode() {
        return code;
    }
}