package com.app.githubsample.adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.githubsample.R;
import com.app.githubsample.activities.RepoDetails;
import com.app.githubsample.modals.Repositories;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class RepoAdapters  extends RecyclerView.Adapter<RepoAdapters.ViewHolder> {
    private List<Repositories> repositories;
    private int rowLayout;
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;
        TextView name;
        TextView full_name;
        TextView watchers_count;
        TextView commits_count;


        public ViewHolder(View v) {
            super(v);
            imageView = (SimpleDraweeView ) v.findViewById(R.id.profile_image);
            name = (TextView) v.findViewById(R.id.name);
            full_name = (TextView) v.findViewById(R.id.full_name);
            watchers_count = (TextView) v.findViewById(R.id.watcher_count);
            commits_count = (TextView) v.findViewById(R.id.commit_count);
        }
    }
        public RepoAdapters(List<Repositories> repositories, Context context) {
            this.repositories = repositories;
            this.context = context;
        }
        @Override
        public RepoAdapters.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item_layout, parent, false);
            view.setOnClickListener(new mOnClickListener());
            return new ViewHolder(view);
        }

    private class mOnClickListener implements OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(view.getContext(), RepoDetails.class);
            intent.putExtra("repo",repositories.get((Integer) view.findViewById(R.id.name).getTag()));
            intent.putExtra("owner",repositories.get((Integer) view.findViewById(R.id.name).getTag()).getOwner());
            view.getContext().startActivity(intent);
        }
    };

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.name.setText(repositories.get(position).getName());
            holder.name.setTag(position);
            holder.full_name.setText(repositories.get(position).getFull_name());
            holder.watchers_count.setText(repositories.get(position).getWatchers_count()+" ");
            holder.commits_count.setText(repositories.get(position).getCommits_count()+" ");
            Uri uri = Uri.parse(repositories.get(position).getOwner().getAvatar_url());
            holder.imageView.setImageURI(uri);



        }

        @Override
        public int getItemCount() {
            return repositories.size();
        }

    public void updateList(List<Repositories> list){
        repositories = list;
        notifyDataSetChanged();
    }

}
