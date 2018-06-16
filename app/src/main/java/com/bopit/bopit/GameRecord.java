package com.bopit.bopit;

public class GameRecord {

    private String statsKey;
    private String username;
    private double avg;
    private double best;

    public GameRecord(String name, double avg, double best){

        this.username = name;
        this.avg = avg;
        this.best = best;

    }

    public double getMeanReactionTime() {
        return avg;
    }

    public void setMeanReactionTime(double highScore) {
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