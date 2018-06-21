package com.bopit.bopit;

public class Game {

    private String statsKey;
    private String username;
    private double avg;
    private double best;

    public Game(String name, double avg, double best){

        this.username = name;
        this.avg = avg;
        this.best = best;

    }

    public double getAverage() {
        return avg;
    }

    public void setAverage(double highScore) {
        this.avg = highScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String highScorerName) {
        this.username = highScorerName;
    }

    public String getStatsKey() {
        return statsKey;
    }

    public void setStatsKey(String statsKey) {
        this.statsKey = statsKey;
    }
}