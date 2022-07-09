package com.example.week2_developer_app;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Board {
    private int id; //primary key auto increment
    private String title;
    private String contents;
    private String writer;
    private ArrayList<Comment> commentList;
    private int likes;
    private String regData;
    private Drawable picture;

    public Board(String title, String contents, String writer, String regData){
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.regData = regData;
        commentList = new ArrayList<>();
        likes = 0;
    }
    public void setPicture(Drawable picture){
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
    public int getLikes(){
        return likes;
    }
    public String getRegDate(){
        return regData;
    }
    public Drawable getPicture(){
        return picture;
    }
}
