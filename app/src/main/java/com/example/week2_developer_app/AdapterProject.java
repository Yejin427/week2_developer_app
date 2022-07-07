package com.example.week2_developer_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class AdapterProject extends RecyclerView.Adapter<AdapterProject.ViewHolder> implements OnItemClickListener, Filterable{


    Context context;
    private ArrayList<Project> projects_filtered = new ArrayList<Project>();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<Project> projects_list = new ArrayList<Project>();
    private static OnItemClickListener listener;

    public AdapterProject(ArrayList<Project> myData){
        this.context = context;
        this.projects_filtered = myData;
        this.projects = myData;
        this.projects_list.addAll(myData);
    }

    @NonNull
    @Override
    public AdapterProject.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view,this);
    }

    public void filterList(ArrayList<Project> filteredList){
        projects = filteredList;
        notifyDataSetChanged();
    }




    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if(charString.isEmpty()) {
                    projects_filtered = projects_list\;
                } else {
                    ArrayList<Project> filteringList = new ArrayList<>();
                    for(Project project : projects_list) {

                        if(project.gettitle().toLowerCase().contains(charString.toLowerCase()) ||
                                project.getcontent().toLowerCase().contains(charString.toLowerCase()) ||
                                project.getfield().toLowerCase().contains(charString.toLowerCase()) ||
                                project.getlevel().toLowerCase().contains(charString.toLowerCase()) ||
                                project.getheadcount().toLowerCase().contains(charString.toLowerCase()) ||
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
        holder.onBind(projects.get(position));
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
        TextView information;

        public ViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            information = (TextView) itemView.findViewById(R.id.information);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(listener != null){
                            listener.onItemClick(ViewHolder.this, v,pos);
                        }
                    }
                }
            });
        }
        void onBind(Project project) {
            title.setText(project.gettitle());
            System.out.println("binding" + project.gettitle());

            String str = "분야 : " + project.getfield()
                    + " / 언어 : " + project.getlanguage()
                    + " / 예상수준 : " + project.getlevel()
                    + " / 모집인원 : " + project.getheadcount() ;

            information.setText(str);
            System.out.println("binding" + project.getcontent());

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
