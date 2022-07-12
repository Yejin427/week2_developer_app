package com.example.week2_developer_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week2_developer_app.databinding.DetailBoardBinding;
import com.example.week2_developer_app.databinding.FragmentBoardBinding;
import com.example.week2_developer_app.databinding.ItemCommentBinding;

import java.sql.Timestamp;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBoard extends AppCompatActivity {
    DetailBoardBinding binding;
    AdapterComment adapterComment;
    ArrayList<Comment> commentArrayList = new ArrayList<Comment>();
    CommentApi commentApi;

    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        binding = DetailBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String writer = intent.getStringExtra("writer");
        String title = intent.getStringExtra("title");
        String writer_email = intent.getStringExtra("email");
        String contents = intent.getStringExtra("contents");
        String regdata = intent.getStringExtra("regdata");
        String type = intent.getStringExtra("type");
        String picture = intent.getStringExtra("picture");
        String username = intent.getStringExtra("username");
        String useremail = intent.getStringExtra("useremail");
        int likes = intent.getIntExtra("likes", 0);

        binding.writer.setText(writer);
        binding.title.setText(title);
        binding.contents.setText(contents);
        binding.date.setText(regdata);

        if(type.equals("Question")){
            binding.type.setText("질문게시판");
        }
        else if(type.equals("Info")){
            binding.type.setText("정보게시판");
        }

        if(picture != null){
            binding.picture.setImageURI(Uri.parse(picture));
        }
        else{
            //이미지 뷰 안보이게 하기
            FrameLayout.LayoutParams pp = new FrameLayout.LayoutParams(0,0);
            binding.picture.setLayoutParams(pp);
        }
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //본인 게시글 아니면 삭제 불가
        if(!useremail.equals(writer_email)){
            binding.delete.setVisibility(View.GONE);
        }

        BoardApi boardApi = RetrofitClient.getClient().create(BoardApi.class);

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete query....

                Listener listener2 = new Listener() {
                    @Override
                    public void returnyes(String str) {

                        JoinBoardData.DeleteData deleteData = new JoinBoardData.DeleteData(id);
                        boardApi.userDeleteBoard(deleteData).enqueue(new Callback<JoinBoardResponse.DeleteResponse>(){

                            @Override
                            public void onResponse(Call<JoinBoardResponse.DeleteResponse> call, Response<JoinBoardResponse.DeleteResponse> response) {
                                JoinBoardResponse.DeleteResponse result = response.body();
                                if(result.getCode() == 200){
                                    Toast.makeText(DetailBoard.this, "게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    Log.d("tag", "삭제 에러");
                                }
                            }

                            @Override
                            public void onFailure(Call<JoinBoardResponse.DeleteResponse> call, Throwable t) {
                                Toast.makeText(DetailBoard.this, "게시글 삭제 에러", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };
                DialogProjectdelete dialog = new DialogProjectdelete(DetailBoard.this, listener2);
                dialog.showDialog();
            }
        });



        //comment recyclerview set
        commentApi = RetrofitClient.getClient().create(CommentApi.class);
        Comment_myData data = new Comment_myData(id);
        Log.d("여기", "여기");
        commentApi.getComment(data).enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                int a = response.body().size();
                Log.d("str", ""+a);
                commentArrayList.clear();
                commentArrayList.addAll(response.body());
                adapterComment.notifyDataSetChanged();
                adapterComment = new AdapterComment(commentArrayList);
                binding.commentRecyclerview.setAdapter(adapterComment);
                Toast.makeText(getApplicationContext(), "댓글 불러오기 성공", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "댓글 불러오기 실패", Toast.LENGTH_SHORT).show();
                Log.e("error: ", t.getMessage());
            }
        });

//        Log.d("보여주세요.", commentArrayList.toString());
        adapterComment = new AdapterComment(commentArrayList);
        binding.commentRecyclerview.setHasFixedSize(true);
        binding.commentRecyclerview.addItemDecoration(new DividerItemDecoration(this, 1));

        binding.commentRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.commentRecyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.commentRecyclerview.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();

        //댓글 터치하면 edittext에 @작성자 표시
        binding.mycomment.requestFocus();
        adapterComment.setOnItemClicklistener(new OnCommentItemClickListener() {
            @Override
            public void onItemClick(AdapterComment.commentViewHolder holder, View view, int pos) {
                Comment curcomment = commentArrayList.get(pos);
                binding.mycomment.setText("@"+curcomment.getWriter());
                binding.mycomment.setTextColor(0xaa1e6de0);
                //keyboard show
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
        //댓글 전송 버튼 클릭
        binding.addMycomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //keyboard hide
                InputMethodManager immhide= (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String tts = timeToString(timestamp);
                commentApi.addComment(new Comment(0, id, tts, username, useremail, binding.mycomment.getText().toString())).enqueue(new Callback<CommentResponse>() {
                    @Override
                    public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                        CommentResponse result = response.body();
                        if(result.getCode() == 200){
                            Toast.makeText(getApplicationContext(), "댓글 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<CommentResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "댓글 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("error: ", t.getMessage());
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
}
