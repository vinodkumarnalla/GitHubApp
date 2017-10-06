package com.app.githubsample.modals;

import android.graphics.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepositryResults {
    @SerializedName("total_count")
    private int total_count;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<Repositories> getItems() {
        return items;
    }

    public void setItems(List<Repositories> items) {
        this.items = items;
    }

    @SerializedName("incomplete_results")
    private boolean incomplete_results;
    @SerializedName("items")
    private List<Repositories> items;


}

