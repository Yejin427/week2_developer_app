package com.example.week2_developer_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class Project {
    private int id;
    private String title;
    private String content;
    private String field;
    private int level; //프로젝트 수준
    private int headcount; //인원
    private String language; // 개발언어
    private String time;    // 선호시간

    public Project(int id, String title, String content, String field, int level, int headcount, String language, String time){
        this.id = id;
        this.title = title;
        this.content = content;
        this.field = field;
        this.level = level;
        this.headcount = headcount;
        this.language = language;
        this.time = time;
    }

    public int getid() {
        return id;
    }
    public String gettitle() {
        return title;
    }
    public String getcontent() {
        return content;
    }
    public String getfield() {
        return field;
    }

    public int getlevel() {
        return level;
    }
    public int getheadcount() {
        return headcount;
    }
    public String getlanguage() {
        return language;
    }
    public String gettime() {
        return time;
    }

}

interface GetProject{
    @GET("/user/getproject")
    Call<List<Project>> getProject();
}