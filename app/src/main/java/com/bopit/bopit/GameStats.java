package com.bopit.bopit;

public class GameStats {

    private String gameKey;
    private String username;
    private double avg;
    private double best;

    public GameStats(String name, double avg, double best){

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

    public String getGameKey() {
        return gameKey;
    }

    public void setGameKey(String statsKey) {
        this.gameKey = statsKey;
    }
}