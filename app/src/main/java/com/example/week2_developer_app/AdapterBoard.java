package com.example.week2_developer_app;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBoard extends RecyclerView.Adapter<AdapterBoard.boardViewHolder> implements OnPersonItemClickListener, Filterable {


    Context context;
    private ArrayList<Board> boards_filtered = new ArrayList<Board>();
    private ArrayList<Board> boards = new ArrayList<Board>();
    private ArrayList<Board> boards_list = new ArrayList<Board>();
    private static OnPersonItemClickListener listener;

    public AdapterBoard(ArrayList<Board> boardList){
        boards = boardList;
        this.boards_list.addAll(boardList);
        this.boards = boardList;
        this.boards_filtered = boardList;
    }

    @NonNull
    @Override
    public boardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        return new boardViewHolder(view, this);
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if(charString.isEmpty()) {
                    boards_filtered = boards_list;
                } else {
                    ArrayList<Board> filteringList = new ArrayList<>();
                    for(Board board : boards_list) {

                        if(board.getTitle().toLowerCase().contains(charString.toLowerCase()) ||
                                board.getContents().toLowerCase().contains(charString.toLowerCase()) ||
                                board.getWriter().toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteringList.add(board);
                        }
                    }
                    boards_filtered = filteringList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = boards_filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                boards.clear();
                boards.addAll((ArrayList<Board>)results.values);
                notifyDataSetChanged();
            }
        };
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
            contents.setText(board.getContents().substring(0,1));
            regDate.setText(parseRegData(board.getRegdata()));
            writer.setText(board.getWriter());
            if(board.getPicture() != null){
                imageView.setImageURI(Uri.parse(board.getPicture()));
            }
        }

        public String parseRegData(String regdata){
            String year = regdata.substring(0, 4);
            String month = regdata.substring(4, 6);
            String day = regdata.substring(6, 8);
            String hour = regdata.substring(8, 10);
            String minute = regdata.substring(10, 12);
            return year+"-"+month+"-"+day+" "+hour+":"+minute;
        }
    }
}
interface OnPersonItemClickListener{
    public void onItemClick(AdapterBoard.boardViewHolder holder, View view, int pos);
}
