package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Comment {
    private int comment_id;  //primary key auto increment
    private String content;
    private String writer;
    private String writer_email;
    private String regdata;
    private int doc_id;  //document id foreign key on delete cascade
    //todo maybe comment - comment

    public Comment(String content, String writer, String regdata, int doc_id){
        this.content = content;
        this.writer = writer;
        this.regdata = regdata;
        this.doc_id = doc_id;
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
}
interface CommentApi{
    @POST("/user/addcomment")
    Call<CommentResponse> addComment(@Body Comment comment);
    @POST("/user/getcomment")
    Call<ArrayList<Comment>> getComment();
}


class CommentResponse {
    @SerializedName("code")
    private int code;
    public int getCode() {
        return code;
    }
}
