package com.app.githubsample.modals;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {
    @GET("search/repositories")
    Call<RepositryResults> getResults(@Query("q") String search_key);
    @GET
    Call<List<Contributors>> getUsers(@Url String url);
    @GET
    Call<List<UserRepos>> getUserRepos(@Url String url );

}
