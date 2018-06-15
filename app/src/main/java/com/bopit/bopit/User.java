package com.bopit.bopit;

import java.io.Serializable;

public class User implements Serializable {

    private String userKey;
    private String username;
    private String phoneId;
    private float pbAverage;
    private float pbBest;
    private float previousAverage;
    private float previousBest;

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

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public float getPbAverage() {
        return pbAverage;
    }

    public void setPbAverage(float pbAverage) {
        this.pbAverage = pbAverage;
    }

    public float getPbBest() {
        return pbBest;
    }

    public void setPbBest(float pbBest) {
        this.pbBest = pbBest;
    }

    public float getPreviousAverage() {
        return previousAverage;
    }

    public void setPreviousAverage(float previousAverage) {
        this.previousAverage = previousAverage;
    }

    public float getPreviousBest() {
        return previousBest;
    }

    public void setPreviousBest(float previousBest) {
        this.previousBest = previousBest;
    }
}
