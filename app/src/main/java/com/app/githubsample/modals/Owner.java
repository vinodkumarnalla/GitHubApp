package com.app.githubsample.modals;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Owner implements Serializable {
    @SerializedName("avatar_url")
    private String avatar_url;

    public String getAvatar_url() {
        return avatar_url;
    }

    public Owner(String avatar_url, String login_name) {
        this.avatar_url = avatar_url;
        this.login_name = login_name;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;

    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    @SerializedName("login")
    private String login_name;
}
