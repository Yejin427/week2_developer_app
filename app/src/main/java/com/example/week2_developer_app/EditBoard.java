package com.example.week2_developer_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.week2_developer_app.databinding.AddBoardBinding;
import com.example.week2_developer_app.databinding.FragmentBoardBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class EditBoard extends AppCompatActivity {
    AddBoardBinding binding;
    private String pictureuri = null;   //image maybe added at board
    private Uri photoUri = null;
    String mediaPath;
    private static final int GET_GALLERY_IMAGE = 0;
    BoardApi boardapi;
    @Override
    public void onCreate(Bundle SavedInstanceState){
        binding = AddBoardBinding.inflate(getLayoutInflater());

        super.onCreate(SavedInstanceState);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        String email = intent.getStringExtra("useremail");
        //회원 정보 받기
        boardapi = RetrofitClient.getClient().create(BoardApi.class);
        final String[] type = {null};
        //String picture = null;
        Drawable selected = (Drawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundbutton_selected);
        Drawable notselected = (Drawable) ContextCompat.getDrawable(getApplicationContext(), R.drawable.roundbutton_notselected);
        //질문 게시글 선택
        binding.question.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                type[0] = "Question";
                binding.question.setBackground(selected);
                binding.info.setBackground(notselected);
            }
        });
        //정보 게시글 선택
        binding.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type[0] = "Info";
                binding.question.setBackground(notselected);
                binding.info.setBackground(selected);
            }
        });
        //나가기 버튼
        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //이미지 추가 버튼 클릭
        binding.imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                //image server에 추가 -> 원격 이미지 주소 불러와서 객체에 저장

            }
        });

        //게시글 등록 버튼
        binding.sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String tts = timeToString(timestamp);
                JoinBoardData.AddData addData = new JoinBoardData.AddData(email, name, type[0], binding.title.getText().toString(), binding.contents.getText().toString(), tts, pictureuri);


                boardapi.userAddBoard(addData).enqueue(new Callback<ArrayList<JoinBoardData.DeleteData>>(){
                    @Override
                    public void onResponse(Call<ArrayList<JoinBoardData.DeleteData>> call, Response<ArrayList<JoinBoardData.DeleteData>> response) {

                        ArrayList<JoinBoardData.DeleteData> result = new ArrayList<>();
                        result.addAll(response.body());
                        response.body();
                        Log.d("str", ""+result.get(0).getDoc_id());

                        if(photoUri != null) {
                            Log.d("확인" , mediaPath);
                            File file = new File(mediaPath);

                            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                            MultipartBody.Part img = MultipartBody.Part.createFormData("picture", "photo" + result.get(0).getDoc_id() + ".jpg", requestFile);

                            boardapi.uploadImage(img).enqueue(new Callback<JoinBoardResponse.PictureResponse>() {
                                @Override
                                public void onResponse(Call<JoinBoardResponse.PictureResponse> call, Response<JoinBoardResponse.PictureResponse> response) {
//                                    JoinBoardResponse.PictureResponse result = response.body();
//                                    pictureuri = result.getPictureUri();
//                                    binding.picture.setImageURI(Uri.parse(pictureuri));
//                                    binding.picture.setVisibility(View.VISIBLE);
                                }
                                @Override
                                public void onFailure(Call<JoinBoardResponse.PictureResponse> call, Throwable t) {
                                    Toast.makeText(EditBoard.this, "게시글 업로드 에러", Toast.LENGTH_SHORT).show();
                                    Log.e("게시글 업로드 에러", t.getMessage());
                                    t.printStackTrace();
                                }
                            });
                        }
                        finish();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<JoinBoardData.DeleteData>> call, Throwable t) {
                        Toast.makeText(EditBoard.this, "게시글 업로드 에러", Toast.LENGTH_SHORT).show();
                        Log.e("게시글 업로드 에러", t.getMessage());
                        t.printStackTrace();
                    }
                });
            }
        });
    }
    public String timeToString(Timestamp timestamp){
        String ts = timestamp.toString();
        String year = ts.substring(0, 4);
        String month = ts.substring(5, 7);
        String day = ts.substring(8, 10);
        String hour = ts.substring(11, 13);
        String minute = ts.substring(14, 16);
        String sec = ts.substring(17, 19);
        return year+month+day+hour+minute+sec;
    }
    //갤러리 접근 ACTIVITY, 사진 선택
    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_GALLERY_IMAGE){
            if(resultCode == RESULT_OK){
                Bitmap bitmap = null;
                photoUri = data.getData();
                Log.d("확인", photoUri.toString());
                String[] proj = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(photoUri, proj, null, null, null);
                cursor.moveToNext();
                mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                cursor.close();
//                Cursor cursor = getContentResolver().query(Uri.parse(photoUri.toString()), null, null, null, null);
//                assert cursor != null;
//                cursor.moveToFirst();
//                mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));

                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    binding.imageadd.setImageBitmap(bitmap);

                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }

    }
}
