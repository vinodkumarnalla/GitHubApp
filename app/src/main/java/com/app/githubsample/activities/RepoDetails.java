package com.app.githubsample.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.app.githubsample.R;
import com.app.githubsample.adapters.ContributorsAdapter;
import com.app.githubsample.modals.ApiClient;
import com.app.githubsample.modals.ApiInterface;
import com.app.githubsample.modals.Contributors;
import com.app.githubsample.modals.Owner;
import com.app.githubsample.modals.Repositories;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoDetails extends AppCompatActivity implements OnClickListener {
    private SimpleDraweeView imageView;
    private TextView name;
    private TextView desc;
    private TextView url;
    private RecyclerView recycle_view;
    private ApiInterface apiService;
    private ArrayList<Contributors>list;
    private Repositories repo;
    private ContributorsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiService =ApiClient.getClient().create(ApiInterface.class);
        name= (TextView) findViewById(R.id.name);
        desc= (TextView) findViewById(R.id.desc);
        url= (TextView) findViewById(R.id.project_link);
        url.setPaintFlags(url.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        url.setOnClickListener(this);
        recycle_view= (RecyclerView) findViewById(R.id.contributorslist);
        list=new ArrayList<>();
        list.clear();
        recycle_view.setLayoutManager(new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false));;
        adapter=new ContributorsAdapter(list,  mOnClickListener);
        recycle_view.setAdapter(adapter);
        imageView= (SimpleDraweeView) findViewById(R.id.profile_image);
        if(getIntent()!=null&&getIntent().hasExtra("repo")&&getIntent().hasExtra("owner")){
            repo= (Repositories) getIntent().getExtras().get("repo");
            Owner owner= (Owner) getIntent().getExtras().get("owner");
            name.setText(repo.getName());
            desc.setText(repo.getDescription());
            url.setText(repo.getHtml_url());
            getUsers(repo.getContributors_url());
            if(owner!=null) {
                Uri uri = Uri.parse(owner.getAvatar_url());
                imageView.setImageURI(uri);

            }

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
         if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int itemPosition = recycle_view.getChildLayoutPosition(view);
            Intent intent=new Intent(view.getContext(), com.app.githubsample.activities.Contributors.class);
            intent.putExtra("user",list.get((itemPosition)));
            view.getContext().startActivity(intent);
        }
    };
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null&&intent.hasExtra("repo")&&intent.hasExtra("owner")){
            repo= (Repositories) intent.getExtras().get("repo");
            Owner owner= (Owner) intent.getExtras().get("owner");
            name.setText(repo.getName());
            desc.setText(repo.getDescription());
            url.setText(repo.getHtml_url());
            getUsers(repo.getContributors_url());
            if(owner!=null) {
                Uri uri = Uri.parse(owner.getAvatar_url());
                imageView.setImageURI(uri);
            }
        }
    }
    private void getUsers(String Url){

        Call<List<Contributors>> call = apiService.getUsers(Url);
        call.enqueue(new Callback<List<Contributors>>() {
            @Override
            public void onResponse(Call<List<Contributors>> call, Response<List<Contributors>> response) {
                if(response!=null&&response.body()!=null) {
                    list.clear();
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Contributors>>call, Throwable t) {
                // Log error here since request failed

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(view.getContext(), com.app.githubsample.activities.WebViewActivity.class);
        intent.putExtra("url",repo.getHtml_url());
        view.getContext().startActivity(intent);
    }
}
