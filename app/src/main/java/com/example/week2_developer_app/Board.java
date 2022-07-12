package com.example.week2_developer_app;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Board {
    @SerializedName("doc_id")
    private int id; //primary key auto increment
    private String title;
    @SerializedName("content")
    private String contents;
    private String writer;
    private String writer_email;
    private ArrayList<Comment> commentList;
    private int likes;
    private String regdata;
    @SerializedName("picture")
    private String picture;     //uri
    @SerializedName("type")
    private String type;

    public Board(String title, String contents, String writer, String writer_email, String regdata){
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.writer_email = writer_email;
        this.regdata = regdata;
        commentList = new ArrayList<>();
        likes = 0;
    }
    public void setPicture(String picture){
        this.picture = picture;
    }
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getContents(){
        return contents;
    }
    public String getWriter(){
        return writer;
    }
    public Comment getComment(int index){
        return commentList.get(index);
    }
    public String getWriter_email(){return writer_email;}
    public int getLikes(){
        return likes;
    }
    public String getRegdata(){
        return regdata;
    }
    public String getPicture(){return picture;}
    public String getType(){return type;}
}
