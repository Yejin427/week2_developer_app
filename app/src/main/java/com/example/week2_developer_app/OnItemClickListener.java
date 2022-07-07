package com.example.week2_developer_app;

import android.view.View;

public interface OnItemClickListener {
    void onItemClick(AdapterProject.ViewHolder holder, View v, int pos);
}