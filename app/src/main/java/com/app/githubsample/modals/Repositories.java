package com.app.githubsample.modals;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Repositories implements Serializable{
    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("watchers_count")
    private int watchers_count;
    @SerializedName("forks_count")
    private int  commits_count;
    @SerializedName("description")
    private String description;
    @SerializedName("html_url")
    private String html_url;

    public Repositories(String name, String full_name, int watchers_count, int commits_count, String description, String html_url, Owner owner, String language, String contributors_url) {
        this.name = name;
        this.full_name = full_name;
        this.watchers_count = watchers_count;
        this.commits_count = commits_count;
        this.description = description;
        this.html_url = html_url;
        this.owner = owner;
        this.language = language;
        this.contributors_url = contributors_url;
    }

    @SerializedName("owner")

    private Owner owner;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @SerializedName("language")
    private String language;

    public String getName() {
        return name;

    }

    public int getWatchers_count() {
        return watchers_count;
    }

    public void setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
    }

    public int getCommits_count() {
        return commits_count;
    }

    public void setCommits_count(int commits_count) {
        this.commits_count = commits_count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getContributors_url() {
        return contributors_url;
    }

    public void setContributors_url(String contributors_url) {
        this.contributors_url = contributors_url;
    }

    @SerializedName("contributors_url")

    private String contributors_url;
}

