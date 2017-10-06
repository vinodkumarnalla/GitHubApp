package com.app.githubsample.adapters;


import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.githubsample.R;
import com.app.githubsample.modals.UserRepos;

import java.util.ArrayList;

public class ReposListAdapter  extends RecyclerView.Adapter<ReposListAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repos_list_item, parent, false);
        itemView.setOnClickListener(mOnClickListener);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.repo_url.setText(repos.get(position).getHtml_url());

    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView repo_url;

        public ViewHolder(View v) {
            super(v);
            repo_url = (TextView) v.findViewById(R.id.repo_url);
            repo_url.setPaintFlags(repo_url.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        }
    }
    private final OnClickListener mOnClickListener;
    private ArrayList<UserRepos> repos;
    public ReposListAdapter(ArrayList<UserRepos> repos, OnClickListener mOnClickListener){
        this.repos=repos;
        this.mOnClickListener=mOnClickListener;
    }
}
