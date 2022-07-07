package com.example.week2_developer_app;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Board {
    private String title;
    private String contents;
    private String writer;
    private ArrayList<Comment> commentList;
    private int likes;
    private String regDate;
    private Drawable picture;

    public Board(String title, String contents, String writer, String regDate){
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.regDate = regDate;
        commentList = new ArrayList<>();
        likes = 0;
    }
    public void setPicture(Drawable picture){
        this.picture = picture;
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
        return regDate;
    }
    public Drawable getPicture(){
        return picture;
    }
}
