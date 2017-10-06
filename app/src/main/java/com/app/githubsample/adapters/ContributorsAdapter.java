package com.app.githubsample.adapters;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.githubsample.R;
import com.app.githubsample.modals.Contributors;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsAdapter.MyViewHolder>{
    private final OnClickListener mOnClickListener;
    private ArrayList<Contributors> contributors;
    public ContributorsAdapter(ArrayList<Contributors> contributors, OnClickListener mOnClickListener){
        this.contributors=contributors;
        this.mOnClickListener=mOnClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countributor, parent, false);
        itemView.setOnClickListener(mOnClickListener);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(contributors.get(position).getLogin());
        Uri uri = Uri.parse(contributors.get(position).getAvatar_url());
        holder.imageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return contributors.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;
        TextView name;

        public MyViewHolder(View view) {
            super(view);
            imageView = (SimpleDraweeView ) view.findViewById(R.id.contributorsimage);
            name = (TextView) view.findViewById(R.id.contributorsname);
        }
    }
}
