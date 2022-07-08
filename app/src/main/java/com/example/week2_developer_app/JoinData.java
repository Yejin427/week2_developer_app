package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

public class JoinData {

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userName")
    private String userName;

//....................................//

    @SerializedName("userAge")
    private int userAge;

    @SerializedName("userPhone")
    private String userPhone;

    @SerializedName("userLevel")
    private int userLevel;

    @SerializedName("userField")
    private String userField;

    @SerializedName("userMsg")
    private String userMsg;

    public JoinData(String userName, String userEmail, String userPwd, int userAge, String userPhone, int userLevel, String userField, String userMsg) {

        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.userPhone = userPhone;
        this.userLevel = userLevel;
        this.userField = userField;
        this.userMsg = userMsg;
    }
}
