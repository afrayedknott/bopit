package com.bopit.bopit;

import java.io.Serializable;

public class User implements Serializable {

    private String userKey;
    private String username;
    private String phoneId;

    public User(String name) {

        this.username = name;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
