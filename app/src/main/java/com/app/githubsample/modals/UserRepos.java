package com.app.githubsample.modals;


import com.google.gson.annotations.SerializedName;

public class UserRepos {
    public String getHtml_url() {
        return html_url;
    }

    public UserRepos(String html_url) {
        this.html_url = html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    @SerializedName("html_url")

    private String html_url;
}
