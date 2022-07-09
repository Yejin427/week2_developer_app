package com.example.week2_developer_app;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class DialogProjectdelete {

    private Context context;
    private Dialog dialog;
    private Listener listener;
    private ImageButton button_back;
    private ImageButton button_ok;

    public DialogProjectdelete(Context context, Listener listener){

        this.context = context;
        this.listener = listener;
        this.dialog = new Dialog(context);
    }


    public void showDialog(){

        dialog.setContentView(R.layout.dialog_projectdelete);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        button_ok = dialog.findViewById(R.id.btn_ok);
        button_back = dialog.findViewById(R.id.btn_back);

        dialog.show();

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.returnyes("yes");
                }
                dialog.dismiss();
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}

