package com.app.githubsample.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.app.githubsample.R;
import com.app.githubsample.adapters.ReposListAdapter;
import com.app.githubsample.modals.ApiClient;
import com.app.githubsample.modals.ApiInterface;
import com.app.githubsample.modals.UserRepos;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Contributors extends AppCompatActivity {
    private static final String TAG = Contributors.class.getSimpleName();
    private RecyclerView recycle_view;
    private ApiInterface apiService;
    private ArrayList<UserRepos> list;
    private SimpleDraweeView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycle_view= (RecyclerView) findViewById(R.id.reposList);
        imageView= (SimpleDraweeView) findViewById(R.id.profile_image);
        list=new ArrayList<>();
        list.clear();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recycle_view.setLayoutManager(llm);
        recycle_view.setHasFixedSize(true);
        if(getIntent()!=null&&getIntent().hasExtra("user")){
            com.app.githubsample.modals.Contributors user= (com.app.githubsample.modals.Contributors) getIntent().getExtras().get("user");
            ((TextView)findViewById(R.id.full_name)).setText(user.getLogin());
            getRepos(user.getRepos_url());
            Uri uri = Uri.parse(user.getAvatar_url());
            imageView.setImageURI(uri);
        }
    }
    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int itemPosition = recycle_view.getChildLayoutPosition(view);
            Intent intent=new Intent(view.getContext(), com.app.githubsample.activities.WebViewActivity.class);
            intent.putExtra("url",list.get((itemPosition)).getHtml_url());
            view.getContext().startActivity(intent);
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void getRepos(String Url){
        Call<List<UserRepos>> call = apiService.getUserRepos(Url);
        call.enqueue(new Callback<List<UserRepos>>() {
            @Override
            public void onResponse(Call<List<UserRepos>> call, Response<List<UserRepos>> response) {
                if(response!=null&&response.body()!=null) {
                    list.clear();
                    list.addAll(response.body());
                    recycle_view.setAdapter(new ReposListAdapter(list,  mOnClickListener));

                }
            }

            @Override
            public void onFailure(Call<List<UserRepos>>call, Throwable t) {
                // Log error here since request failed

            }
        });
    }
}
