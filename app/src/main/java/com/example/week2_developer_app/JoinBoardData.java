package com.example.week2_developer_app;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

import java.util.ArrayList;

public class JoinBoardData {
    public static class AddData{
        @SerializedName("writer_email")
        private String writer_email;

        @SerializedName("writer")
        private String writer;

        @SerializedName("type")
        private String type;

        @SerializedName("title")
        private String title;

        @SerializedName("content")
        private String content;

        @SerializedName("regdata")
        private String regdata;

//        @SerializedName("picture")
//        private String picture;

        public AddData(String writer_email, String writer, String type, String title, String content, String regdata) {
            this.writer_email = writer_email;
            this.writer = writer;
            this.type = type;
            this.title = title;
            this.content = content;
            this.regdata = regdata;
            //this.picture = picture;
        }
    }
    public static class GetData{
        public GetData(){}
    }
    public static class DeleteData{
        @SerializedName("doc_id")
        private int doc_id;

        public DeleteData(int doc_id){
            this.doc_id = doc_id;
        }
    }
}

class JoinBoardResponse {

    public class AddResponse{
        @SerializedName("code")
        private int code;

        public int getCode() {
            return code;
        }
    }

    public class DeleteResponse{
        @SerializedName("code")
        private int code;

        public int getCode() {
            return code;
        }
    }

}