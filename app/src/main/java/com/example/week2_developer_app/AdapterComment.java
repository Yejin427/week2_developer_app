package com.example.week2_developer_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week2_developer_app.databinding.ItemCommentBinding;

import java.util.ArrayList;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.commentViewHolder> implements OnCommentItemClickListener{

    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private static OnCommentItemClickListener listener;

    public AdapterComment(ArrayList<Comment> commentList){
        comments = commentList;
    }

    @NonNull
    @Override
    public commentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new commentViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(commentViewHolder holder, int position){
        holder.onBind(comments.get(position));
    }
    @Override
    public int getItemCount(){
        return comments.size();
    }

    public void setOnItemClicklistener(OnCommentItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onItemClick(commentViewHolder holder, View view, int pos){
        if(listener != null){
            listener.onItemClick(holder, view, pos);
        }
    }
    public class commentViewHolder extends RecyclerView.ViewHolder{

        public commentViewHolder(View itemview, final OnCommentItemClickListener listener){
            super(itemview);

            itemview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(commentViewHolder.this, v, pos);
                    }
                }
            });
        }
        void onBind(Comment comment){
            ((TextView)itemView.findViewById(R.id.contents)).setText(comment.getContents());
            ((TextView)itemView.findViewById(R.id.date)).setText(parseRegData(comment.getRegData()));
            ((TextView)itemView.findViewById(R.id.writer)).setText(comment.getWriter());
        }


    }

    public String parseRegData(String regdata){
        String year = regdata.substring(0, 4);
        String month = regdata.substring(4, 6);
        String day = regdata.substring(6, 8);
        String hour = regdata.substring(8, 10);
        String minute = regdata.substring(10, 12);
        return month+"-"+day+" "+hour+":"+minute;
    }
}
interface OnCommentItemClickListener{
    void onItemClick(AdapterComment.commentViewHolder holder, View view, int pos);
}
