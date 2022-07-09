package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class ProjectData {

    @SerializedName("proj_id")
    int proj_id;
    @SerializedName("writer")
    String writer;
    @SerializedName("title")
    String title;
    @SerializedName("content")
    String content;
    @SerializedName("field")
    String field;
    @SerializedName("level")
    int level;
    @SerializedName("headcount")
    int headcount;
    @SerializedName("language")
    String language;
    @SerializedName("time")
    String time;
    @SerializedName("regdata")
    String regdata;

    @Override
    public String toString(){
        return "ProjectData{" +
            "proj_id = " + proj_id +
            "writer = " + writer +
                "title = " + title +
                "content = " + content +
                "field = " + field +
                "level = " + level +
                "headcount = " + headcount +
                "language = " + language +
                "time = " + time +
                "regdata = " + regdata +
            "}";
    }

    public ProjectData(int id, String title, String content, String field, int level, int headcount, String language, String time){
        this.proj_id = id;
        this.title = title;
        this.content = content;
        this.field = field;
        this.level = level;
        this.headcount = headcount;
        this.language = language;
        this.time = time;
    }

    public int getid() {
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
