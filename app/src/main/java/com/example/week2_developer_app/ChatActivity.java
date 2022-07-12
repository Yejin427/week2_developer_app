package com.example.week2_developer_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.week2_developer_app.databinding.ActivityChatmsgBinding;
import com.example.week2_developer_app.databinding.ActivityDetailprojectBinding;
import com.example.week2_developer_app.databinding.FragmentChatroomBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.kakao.sdk.user.UserApiClient;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends Activity {


    private ArrayList<Chatmsg> chatmsglist = new ArrayList<>();
    private AdapterChatmsg adapter;
    private String name;
    private String email;
    private int chat_id;
    private String chat_name;
    private String type;
    private String regdata;
    private Socket mSocket;
    private ActivityChatmsgBinding binding;
    private ArrayList<String> connectedlist = new ArrayList<>();

    private void getChatmsglist(){

        chatmsglist.clear();
        ChatmsgApi service = RetrofitClient.getClient().create(ChatmsgApi.class);
        Call<List<Chatmsg>> call = service.getChatmsg(new Chatmsg_myData(chat_id));
        call.enqueue(new Callback<List<Chatmsg>>() {
            @Override
            public void onResponse(Call<List<Chatmsg>> call, Response<List<Chatmsg>> response) {

                chatmsglist.clear();
                for(int i=0; i < response.body().size() ;i++){

                    if(response.body().get(i).getSender_name().equals(name))
                        response.body().get(i).setviewType(1);
                    if(connectedlist.contains(response.body().get(i).getSender_name())) {
                        response.body().get(i).setConnected(1);
                    }
                    chatmsglist.add(response.body().get(i));
                }
                adapter.notifyDataSetChanged();
                adapter = new AdapterChatmsg(chatmsglist);
                binding.chatmsglistview.setAdapter(adapter);
                binding.chatmsglistview.scrollToPosition(adapter.getItemCount() - 1);
            }
            @Override
            public void onFailure(Call<List<Chatmsg>> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatmsgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        chat_id = intent.getIntExtra("chat_id", 2);
        chat_name = intent.getStringExtra("chat_name");
        type = intent.getStringExtra("type");
        regdata = intent.getStringExtra("regdata");


        binding.topAppBarName.setTitle(chat_name);

        getChatmsglist();

        GlobalApplication app = (GlobalApplication)getApplication();
        mSocket = app.getSocket();
        mSocket.on("connection", onConnection);
        mSocket.on("disconnection", onDisConnection);
        mSocket.on("new message", onNewMessage);
//        mSocket.on("typing", onTyping);

        mSocket.connect();
        mSocket.emit("connection", chat_id, name, email);

        if(type.equals("new"))
            mSocket.emit("new message", "안녕하세요, "+name+ "입니다.", chat_id, name, regdata);

        //이후 서버에서 io.emit("new message", ,msg객체);

//        mSocket.on(Socket.EVENT_CONNECT, onConnect);
//        mSocket.on(Socket.EVENT_CONNECT, onConnect);



//채팅방제목  binding.topAppBarName.setTitle();
        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                binding.chatmsglistview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.chatmsglistview.setAdapter(adapter);
                return false;
            }
        });

        binding.refreshChatmsg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Refeshed", Toast.LENGTH_SHORT).show();
                getChatmsglist();
                binding.refreshChatmsg.setRefreshing(false);
                Log.d("refresh", chatmsglist.toString());
            }
        });



        binding.chatmsglistview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if(i3 < i7){
                    binding.chatmsglistview.postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            if(chatmsglist.size() > 0)
                                binding.chatmsglistview.scrollToPosition(adapter.getItemCount() - 1);
                        }
                    }, 100);
                }
            }
        });

        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addmsg = binding.addmsg.getText().toString();
                String regdata = convertTimestampTodate(System.currentTimeMillis());
                mSocket.emit("new message",addmsg, chat_id, name, regdata);
                binding.addmsg.setText("");
            }
        });

        binding.chatmsglistview.setHasFixedSize(true);
//        binding.chatmsglistview.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
        adapter = new AdapterChatmsg(chatmsglist);
        binding.chatmsglistview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.chatmsglistview.setItemAnimator(new DefaultItemAnimator());
        binding.chatmsglistview.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSocket.emit("disconnection", chat_id, name, email);
        mSocket.disconnect();
        mSocket.off("connection", onConnection);
        mSocket.off("disconnection", onDisConnection);
        mSocket.off("new message", onNewMessage);

        finish();   //현재 액티비티 종료
    }
    private String convertTimestampTodate(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = sdf.format(time);
        return date;
    }


    private Emitter.Listener onConnection = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String username = (String) args[0];
                    String useremail = (String) args[1];
                    JSONArray connectedlistjson = (JSONArray) args[2];

                    connectedlist.clear();
                    for(int i=0 ; i<connectedlistjson.length() ; i++){
                        try {
                            connectedlist.add(connectedlistjson.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    getChatmsglist();
                    Log.d("접속중인 사람 확인", connectedlist.toString());
                    Log.d("접속", username);
                    Log.d("접속", useremail);
                }
            });
        }
    };

    private Emitter.Listener onDisConnection = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String username = (String) args[0];
                    String useremail = (String) args[1];
                    JSONArray connectedlistjson = (JSONArray) args[2];

                    connectedlist.clear();
                    for(int i=0 ; i<connectedlistjson.length() ; i++){
                        try {
                            connectedlist.add(connectedlistjson.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    getChatmsglist();
                    Log.d("접속중인 사람 확인", connectedlist.toString());
                    Log.d("접속해제", username);
                    Log.d("접속해제", useremail);
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getChatmsglist();
                }
            });
        }
    };


}

class connectiondata{

    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("chat_id")
    private int chat_id;

    public connectiondata(int chat_id, String email, String name){
        this.chat_id = chat_id;
        this.email = email;
        this.name = name;
    }

}