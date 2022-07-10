package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Comment {
    @SerializedName("comment_id")
    private int comment_id;  //primary key auto increment
    @SerializedName("doc_id")
    private int doc_id;  //document id foreign key on delete cascade
    @SerializedName("regdata")
    private String regdata;
    @SerializedName("writer")
    private String writer;
    @SerializedName("writer_email")
    private String writer_email;
    @SerializedName("content")
    private String content;


    //todo maybe comment - comment

    public Comment(int comment_id, int doc_id, String regdata, String writer, String writer_email, String content){
        this.comment_id = comment_id;
        this.doc_id = doc_id;
        this.regdata = regdata;
        this.writer = writer;
        this.writer_email = writer_email;
        this.content = content;
    }

    public void setContent(String content){
        this.content = content;
    }
    public void setWriter(String writer){
        this.writer = writer;
    }
    public void setRegData(String regdata){
        this.regdata = regdata;
    }
    public void setWriter_email(String writer_email){
        this.writer_email = writer_email;
    }
    public void setDoc_id(int doc_id){
        this.doc_id = doc_id;
    }
    public String getContents(){return content;}
    public String getWriter(){return writer;}
    public String getRegData(){return regdata;}
    public String getWriter_email(){return writer_email;}
    public int getDoc_id(){return doc_id;}
    public int getComment_id(){return comment_id;}
}
interface CommentApi{
    @POST("/user/addcomment")
    Call<CommentResponse> addComment(@Body Comment comment);
    @POST("/user/getcomment")
    Call<ArrayList<Comment>> getComment(@Body Comment_myData data);
}

class Comment_myData{
    @SerializedName("doc_id")
    private int doc_id;
    public Comment_myData(int doc_id){
        this.doc_id = doc_id;
    }
}

class CommentResponse {
    @SerializedName("code")
    private int code;
    public int getCode() {
        return code;
    }
}
