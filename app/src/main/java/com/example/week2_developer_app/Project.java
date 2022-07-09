package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class Project {
    @SerializedName("id")
    private int id;
    @SerializedName("writer")
    private String writer;
    @SerializedName("writer_email")
    private String writer_email;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("field")
    private String field;
    @SerializedName("level")
    private int level; //프로젝트 수준
    @SerializedName("headcount")
    private int headcount; //인원
    @SerializedName("language")
    private String language; // 개발언어
    @SerializedName("time")
    private String time;    // 선호시간
    @SerializedName("regdata")
    private String regdata;

    public Project(int id, String writer, String writer_email, String title, String content, String field, int level, int headcount, String language, String time, String regdata){
        this.id = id;
        this.writer = writer;
        this.writer_email = writer_email;
        this.title = title;
        this.content = content;
        this.field = field;
        this.level = level;
        this.headcount = headcount;
        this.language = language;
        this.time = time;
        this.regdata = regdata;
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
    public String regdata(){return regdata;}

}

interface ProjectApi{
    @GET("/user/getproject")
    Call<List<Project>> getProject();
    @POST("/user/addproject")
    Call<ProjectaddResponse> addProject(@Body Project project);
}

class ProjectaddResponse {

    @SerializedName("code")
    private int code;
    public int getCode() {
        return code;
    }
}

