package com.example.week2_developer_app;

public class Project {
    private int id;
    private String title;
    private String content;
    private String field;
    private String level; //프로젝트 수준
    private String headcount; //인원
    private String language; // 개발언어
    private String time;    // 선호시간

    public Project(int id, String title, String content, String field, String level, String headcount, String language, String time){
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

    public String getlevel() {
        return level;
    }
    public String getheadcount() {
        return headcount;
    }
    public String getlanguage() {
        return language;
    }
    public String gettime() {
        return time;
    }
//    public void setName(String name)
//    {
//        this.name = name;
//    }
//    public String getNum(String name){
//        return number;
//    }
//    public void setNum(String number)
//    {
//        this.number=number;
//    }
}
