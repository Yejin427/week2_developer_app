package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

public class SignupData {

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userName")
    private String userName;

//....................................//

    @SerializedName("userAge")
    private int userAge;

    @SerializedName("userGender")
    private String userGender;

    @SerializedName("userPhonenum")
    private String userPhonenum;

    @SerializedName("userLevel")
    private int userLevel;

    @SerializedName("userField")
    private String userField;

    @SerializedName("userMsg")
    private String userMsg;

    public SignupData(String userEmail, String userName, int userAge, String userGender, String userPhonenum, String userField, int userLevel, String userMsg) {

        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userPhonenum = userPhonenum;
        this.userLevel = userLevel;
        this.userField = userField;
        this.userMsg = userMsg;
    }
}
