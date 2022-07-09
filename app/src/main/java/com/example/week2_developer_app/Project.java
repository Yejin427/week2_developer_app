package com.example.week2_developer_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class Project {
<<<<<<< Updated upstream
    private int id;
=======
    @SerializedName("proj_id")
    private int proj_id;
    @SerializedName("writer")
    private String writer;
    @SerializedName("writer_email")
    private String writer_email;
    @SerializedName("title")
>>>>>>> Stashed changes
    private String title;
    private String content;
    private String field;
    private int level; //프로젝트 수준
    private int headcount; //인원
    private String language; // 개발언어
    private String time;    // 선호시간

<<<<<<< Updated upstream
    public Project(int id, String title, String content, String field, int level, int headcount, String language, String time){
        this.id = id;
=======
    public Project(int proj_id, String writer, String writer_email, String title, String content, String field, int level, int headcount, String language, String time, String regdata){
        this.proj_id = proj_id;
        this.writer = writer;
        this.writer_email = writer_email;
>>>>>>> Stashed changes
        this.title = title;
        this.content = content;
        this.field = field;
        this.level = level;
        this.headcount = headcount;
        this.language = language;
        this.time = time;
    }

    public int getproj_id() {
        return proj_id;
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
<<<<<<< Updated upstream
=======
    @POST("/user/getmyproject")
    Call<List<Project>> getmyProject(@Body Project_myData project);
    @POST("/user/addproject")
    Call<ProjectResponse> addProject(@Body Project project);
    @POST("/user/deleteproject")
    Call<ProjectResponse> deleteProject(@Body Project_deleteData project);
}

class ProjectResponse {

    @SerializedName("code")
    private int code;
    public int getCode() {
        return code;
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
>>>>>>> Stashed changes
}