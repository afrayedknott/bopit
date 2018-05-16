package com.bopit.bopit;

import java.util.ArrayList;
import java.util.HashMap;

public class StatsEntry {

    private String highscoreKey;
    private String username;
    private long meanReactionTime;

    public StatsEntry(String name, Long mRT){

        this.username = name;
        this.meanReactionTime = mRT;

    }

    public long getMeanReactionTime() {
        return meanReactionTime;
    }

    public void setMeanReactionTime(long highScore) {
        this.meanReactionTime = highScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String highScorerName) {
        this.username = highScorerName;
    }

    public String getHighscoreKey() {
        return highscoreKey;
    }

    public void setHighscoreKey(String highscoreKey) {
        this.highscoreKey = highscoreKey;
    }
}