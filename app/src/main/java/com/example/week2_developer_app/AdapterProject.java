package com.example.week2_developer_app;

import android.content.Context;
import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterProject extends RecyclerView.Adapter<AdapterProject.ViewHolder> implements OnItemClickListener, Filterable{

    Context context;
    private ArrayList<Project> projects_filtered = new ArrayList<Project>();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<Project> projects_list = new ArrayList<Project>();
    private static OnItemClickListener listener;
    int viewtype = 0;

    public AdapterProject(ArrayList<Project> myData){
        this.context = context;
        this.projects_filtered = myData;
        this.projects = myData;
        this.projects_list.addAll(myData);
        this.viewtype = 0;
    }

    public void setViewtype(int type){
        this.viewtype = type;
    }
    public int getViewtype(){
        return this.viewtype;
    }

    @NonNull
    @Override
    public AdapterProject.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view,this);
    }


    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if(charString.isEmpty()) {
                    projects_filtered = projects_list;
                } else {
                    ArrayList<Project> filteringList = new ArrayList<>();
                    for(Project project : projects_list) {

                        if(project.gettitle().toLowerCase().contains(charString.toLowerCase()) ||
                                project.getcontent().toLowerCase().contains(charString.toLowerCase()) ||
                                project.getfield().toLowerCase().contains(charString.toLowerCase()) ||
                                Integer.toString(project.getlevel()).toLowerCase().contains(charString.toLowerCase()) ||
                                Integer.toString(project.getheadcount()).toLowerCase().contains(charString.toLowerCase()) ||
                                project.getlanguage().toLowerCase().contains(charString.toLowerCase()) ||
                                project.gettime().toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteringList.add(project);
                        }
                    }
                    projects_filtered = filteringList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = projects_filtered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                projects.clear();
                projects.addAll((ArrayList<Project>)results.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.onBind(projects.get(position), viewtype);
    }

    public void setFriendList(ArrayList<Project> list){
        this.projects = list;
        notifyDataSetChanged();
    }

    public void add(Project project){
        projects.add(project);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void setOnItemClicklistener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }
    @Override
    public int getItemViewType(int position){
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView project_id;
        TextView field;
        TextView language;
        TextView level;
        TextView headcount;
        ImageButton imagebtn;
        TextView viewType;
        TextView regdata;
        TextView writer;

        public ViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            project_id = (TextView) itemView.findViewById(R.id.proj_id);
            viewType = (TextView) itemView.findViewById(R.id.viewType);
            imagebtn = (ImageButton) itemView.findViewById(R.id.imagebtn);
            title = (TextView) itemView.findViewById(R.id.title);
            field = (TextView) itemView.findViewById(R.id.field);
            language = (TextView) itemView.findViewById(R.id.language);
            level = (TextView) itemView.findViewById(R.id.level);
            headcount = (TextView) itemView.findViewById(R.id.headcount);
            regdata = (TextView) itemView.findViewById(R.id.regdata);
            writer = (TextView) itemView.findViewById(R.id.writer);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //동작 호출 (onItemClick 함수 호출)
                        if(listener != null){
                            listener.onItemClick(ViewHolder.this, v, pos);
                        }
                    }
                }
            });
        }

        void onBind(Project project, int viewtype) {

            if(viewtype == 1) {
                imagebtn.setVisibility(View.VISIBLE);
                viewType.setText(Integer.toString(viewtype));
            }
            project_id.setText(Integer.toString(project.getproj_id()));
            title.setText(project.gettitle());
            field.setText("개발분야 : " + project.getfield());
            language.setText("개발언어 : " + project.getlanguage());
            level.setText("개발 수준 : " + Integer.toString(project.getlevel()));
            headcount.setText("모집 인원 : " + Integer.toString(project.getheadcount()) + "명");
            String prd = parseRegData(project.getregdata());
            regdata.setText(prd);
            writer.setText("작성자 : " + project.getwriter());
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
    public void setItems(ArrayList<Project> projects){
        this.projects = projects;
    }
    public Project getItem(int position){
        return projects.get(position);
    }
    public void setItem(int position, Project project){
        projects.set(position, project);
    }

}

