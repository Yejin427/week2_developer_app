package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class Project {

    @SerializedName("proj_id")
    private int proj_id;
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

    public Project(int proj_id, String writer, String writer_email, String title, String content, String field, int level, int headcount, String language, String time, String regdata){
        this.proj_id = proj_id;
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

    public int getproj_id() {
        return proj_id;
    }
    public String getwriter() {return writer; }
    public String getwriter_email() {return writer_email; }
    public String gettitle() { return title;}
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
    public String getregdata(){return regdata;}

}

interface ProjectApi{
    @GET("/user/getproject")
    Call<List<Project>> getProject();
    @POST("/user/addproject")
    Call<ProjectResponse> addProject(@Body Project project);
    @POST("/user/getmyproject")
    Call<List<Project>> getmyProject(@Body Project_myData project);
    @POST("/user/deleteproject")
    Call<ProjectResponse> deleteProject(@Body Project_deleteData project);
}


class ProjectResponse {

    @SerializedName("code")
    private int code;
    public int getCode() {
        return code;
    }

    @SerializedName("proj_id")
    private int proj_id;
    public int getid() {
        return proj_id;
    }
}

class ProjectResponse_id {

    @SerializedName("id")
    private int id;
    public int getid() {
        return id;
    }
}

class Project_myData{
    @SerializedName("userEmail")
    String userEmail;
    public Project_myData(String userEmail){
        this.userEmail = userEmail;
    }
}

class Project_deleteData{
    @SerializedName("proj_id")
    int proj_id;
    public Project_deleteData(int proj_id){
        this.proj_id = proj_id;
    }
}

