package com.example.week2_developer_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import com.google.android.material.tabs.TabLayout;




import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends FragmentActivity {

    TabLayout tabs;

    private String name;
    private String email;

    FragmentProject fragmentProject;
    FragmentBoard fragmentBoard;
    FragmentChatroom fragmentChatroom;

    String getName(){
        return name;
    }
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        getHashKey();
        fragmentProject = new FragmentProject();
        fragmentBoard = new FragmentBoard();

        fragmentChatroom = new FragmentChatroom();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("email", email);
        fragmentProject.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragmentProject).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("홈"));
        tabs.addTab(tabs.newTab().setText("게시판"));
        tabs.addTab(tabs.newTab().setText("채팅"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragmentProject;
                else if(position == 1)
                    selected = fragmentBoard;
                else if(position == 2)
                    selected = fragmentChatroom;

                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("email", email);
                selected.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
