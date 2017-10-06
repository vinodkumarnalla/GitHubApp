package com.app.githubsample.modals;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contributors implements Serializable{

    @SerializedName("avatar_url")
    private String avatar_url;
    @SerializedName("repos_url")
    private String repos_url;

    public Contributors(String avatar_url, String repos_url, String login) {
        this.avatar_url = avatar_url;
        this.repos_url = repos_url;
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;

    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @SerializedName("login")
    private String login;
}
