package com.bopit.bopit;

import java.io.Serializable;

public class User implements Serializable {

    private String userKey;
    private String username;
    private String phoneId;
    private double pbAverage;
    private double pbBest;
    private double previousAverage;
    private double previousBest;

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

    public double getPbAverage() {
        return pbAverage;
    }

    public void setPbAverage(double pbAverage) {
        this.pbAverage = pbAverage;
    }

    public double getPbBest() {
        return pbBest;
    }

    public void setPbBest(double pbBest) {
        this.pbBest = pbBest;
    }

    public double getPreviousAverage() {
        return previousAverage;
    }

    public void setPreviousAverage(double previousAverage) {
        this.previousAverage = previousAverage;
    }

    public double getPreviousBest() {
        return previousBest;
    }

    public void setPreviousBest(double previousBest) {
        this.previousBest = previousBest;
    }
}
