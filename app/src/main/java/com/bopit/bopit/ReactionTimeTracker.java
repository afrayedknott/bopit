package com.bopit.bopit;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by redna on 3/6/2018.
 */

public class ReactionTimeTracker{

    private long startTime = 0;
    private long millis;
    private int seconds;
    private int minutes;
    private long previousRoundEndTime = 0;
    private int finalMinute = 5;
    private int randomTime;
    private long hitStartTime;
    private int numberOfTimesToHit = 20;
    private ArrayList<Long> randomTimesToHitArrayList = new ArrayList<>();
    private ArrayList<Long> reactionTimesArrayList = new ArrayList<>();

    Random r = new Random();

    public ReactionTimeTracker(){

    }

    //runs without a timer by reposting this handler at the end of the runnable

    public void tickTocker(){

        setMillis(System.currentTimeMillis() - getStartTime());
        setSeconds((int) getMillis() / 1000);
        setMinutes(getSeconds() / 60);
        setSeconds(getSeconds() % 60);

    };

    public void setRandomTimesToHit(){

        for(int randomTimeToHitIter =0; randomTimeToHitIter < numberOfTimesToHit; randomTimeToHitIter++) {
            randomTime = r.nextInt(6000) + 2000;
            randomTimesToHitArrayList.add((long) randomTime);
        }

    }

    public long getRandomTime(int randomTimeIter){

        return randomTimesToHitArrayList.get(randomTimeIter);

    }

    public ArrayList<Long> getRandomTimeArrayList(){

        return randomTimesToHitArrayList;

    }

    public long getStartTime(){

        return startTime;

    }

    public void setStartTime(long startTimeInput){

        startTime = startTimeInput;

    }

    public long getMillis(){

        return millis;

    }

    public void setMillis(long millisInput){

        millis = millisInput;

    }

    public int getSeconds(){

        return seconds;

    }

    public void setSeconds(int secondsInput){

        seconds = secondsInput;

    }


    public int getMinutes(){

        return minutes;

    }

    public void setMinutes(int minutesInput){

        minutes = minutesInput;

    }

    public long getPreviousRoundEndTime(){

        return previousRoundEndTime;

    }

    public void setPreviousRoundEndTime(long inputPreviousRoundEndTime){

        previousRoundEndTime = inputPreviousRoundEndTime;

    }

    public int getFinalMinute(){

        return finalMinute;

    }

    public void setHitStartTime(int randomTimeIter){

        hitStartTime = getRandomTime(randomTimeIter)+getPreviousRoundEndTime();

    }

    public long getHitStartTime(){

        return hitStartTime;

    }

    public void recordReactionTime(){

        reactionTimesArrayList.add(getMillis());

    }

    public long calculateMeanReactionTime(){

        long calcMeanSum = reactionTimesArrayList.get(0);
        for(int rTListIter = 1; rTListIter < reactionTimesArrayList.size(); rTListIter++){
            calcMeanSum = calcMeanSum + reactionTimesArrayList.get(rTListIter);
        }

        return calcMeanSum;

    }

}
