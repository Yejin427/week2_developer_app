package com.example.week2_developer_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBoard extends RecyclerView.Adapter<AdapterBoard.boardViewHolder> implements OnPersonItemClickListener {
    private ArrayList<Board> boards = new ArrayList<Board>();
    private OnPersonItemClickListener listener;

    public AdapterBoard(ArrayList<Board> boardList){
        boards = boardList;
    }

    @NonNull
    @Override
    public boardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        return new boardViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(boardViewHolder holder, int position){
        holder.onBind(boards.get(position));
    }
    @Override
    public int getItemCount(){
        return boards.size();
    }

    public void setOnItemClicklistener(OnPersonItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onItemClick(boardViewHolder holder, View view, int pos){
        if(listener != null){
            listener.onItemClick(holder, view, pos);
        }
    }
    public static class boardViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView contents;
        TextView regDate;
        TextView writer;
        ImageView imageView;

        public boardViewHolder(View itemview, final OnPersonItemClickListener listener){
            super(itemview);
            title = (TextView) itemview.findViewById(R.id.title);
            contents = (TextView) itemview.findViewById(R.id.contents);
            regDate = (TextView) itemview.findViewById(R.id.date);
            writer = (TextView) itemview.findViewById(R.id.writer);
            imageView = (ImageView) itemview.findViewById(R.id.boardimage);

            itemview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(boardViewHolder.this, v, pos);
                    }
                }
            });
        }
        void onBind(Board board){
            title.setText(board.getTitle());
            contents.setText(board.getContents());
            regDate.setText(board.getRegDate());
            writer.setText(board.getWriter());
            imageView.setImageDrawable(board.getPicture());
        }
    }
}
interface OnPersonItemClickListener{
    public void onItemClick(AdapterBoard.boardViewHolder holder, View view, int pos);
}
