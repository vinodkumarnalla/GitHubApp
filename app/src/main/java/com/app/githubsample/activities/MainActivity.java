package com.app.githubsample.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.githubsample.R;
import com.app.githubsample.adapters.RepoAdapters;
import com.app.githubsample.modals.ApiClient;
import com.app.githubsample.modals.ApiInterface;
import com.app.githubsample.modals.Repositories;
import com.app.githubsample.modals.RepositryResults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.githubsample.R.menu.search;

public class MainActivity extends AppCompatActivity implements OnQueryTextListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private  ApiInterface apiService;
    private RecyclerView recycle_view;
    private ProgressBar progressBar;
    private TextView no_results;
    private  List<Repositories> repos;
    private RepoAdapters adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         apiService =
                ApiClient.getClient().create(ApiInterface.class);
        recycle_view= (RecyclerView) findViewById(R.id.recycle_view);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        no_results= (TextView) findViewById(R.id.no_results);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recycle_view.setLayoutManager(llm);
        no_results.setVisibility(View.VISIBLE);
        recycle_view.setVisibility(View.GONE);
        repos=new ArrayList<>();
        adapter=new RepoAdapters(repos,  getApplicationContext());
        recycle_view.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.filter){
            showOptions();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(search, menu);
        MenuItem filter = menu.findItem(R.id.filter);
        Drawable newIcon = (Drawable)filter.getIcon();
        newIcon.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        filter.setIcon(newIcon);
        MenuItem searchItem = menu.findItem(R.id.search_item);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }
    public void search(String value){
        progressBar.setVisibility(View.VISIBLE);
        Call<RepositryResults> call = apiService.getResults(value);
        call.enqueue(new Callback<RepositryResults>() {
            @Override
            public void onResponse(Call<RepositryResults> call, Response<RepositryResults> response) {
                if(response!=null&&response.body()!=null) {
                    if(repos!=null&&repos.size()>0)
                        repos.clear();
                    repos = response.body().getItems();
                    if(repos!=null&&repos.size()>0) {
                        Collections.sort(repos,new RepoComparator());
                        repos=repos.subList(Math.max(repos.size() - 10, 0), repos.size());
                        progressBar.setVisibility(View.GONE);
                        adapter.updateList(repos);
                        no_results.setVisibility(View.GONE);
                        recycle_view.setVisibility(View.VISIBLE);
                    }else{
                        no_results.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        recycle_view.setVisibility(View.GONE);
                    }
                }else{
                    no_results.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    recycle_view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RepositryResults>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                no_results.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                recycle_view.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if(query.trim().length()>0)
            search(query.trim());
        return true;
    }
    public class RepoComparator implements Comparator<Repositories> {
        @Override
        public int compare(Repositories u1, Repositories u2) {
            return Integer.compare(u1.getWatchers_count(), u2.getWatchers_count());
        }
    }
    public void  filter(String language){
        List<Repositories> temp = new ArrayList();
        if(!language.equals("Reset")) {
            for (Repositories d : repos) {
                if (d.getLanguage()!=null&&d.getLanguage().equals(language)) {
                    temp.add(d);
                }
            }
            adapter.updateList(temp);
        }else{
            adapter.updateList(repos);
        }

    }
    private void showOptions() {
        final String language[] = new String[]{"Java", "Shell", "JavaScript", "Swift", "HTML", "Assembly", "Lua", "C++", "Reset"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pick a language");
            builder.setItems(language, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on colors[which]
                    if (repos != null && repos.size() > 0) {
                        filter(language[which]);
                    }else {
                        Toast.makeText(MainActivity.this, "Please search for repositories", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();
        }


}
