package com.bopit.bopit;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by redna on 3/6/2018.
 */

public class ReactionTimeTracker{

    private double startTime = 0;
    private double millis;
    private int seconds;
    private int minutes;
    private double previousRoundEndTime = 0;
    private int finalMinute = 5;
    private int randomTime;
    private double hitStartTime;
    private int numberOfTimesToHit;
    private double averageReactionTime;
    private ArrayList<Double> randomTimesToHitArrayList = new ArrayList<>();
    private ArrayList<Double> reactionTimesArrayList = new ArrayList<>();

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
            randomTimesToHitArrayList.add((double) randomTime);
        }

    }

    public double getRandomTime(int randomTimeIter){

        return randomTimesToHitArrayList.get(randomTimeIter);

    }

    public ArrayList<Double> getRandomTimeArrayList(){

        return randomTimesToHitArrayList;

    }

    public void setNumberOfTimesToHit(int timesToHit){

        numberOfTimesToHit = timesToHit;

    }

    public int getNumberOfTimesToHit() {

        return numberOfTimesToHit;

    }

    public double getStartTime(){

        return startTime;

    }

    public void setStartTime(double startTimeInput){

        startTime = startTimeInput;

    }

    public double getMillis(){

        return millis;

    }

    public void setMillis(double millisInput){

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

    public double getPreviousRoundEndTime(){

        return previousRoundEndTime;

    }

    public void setPreviousRoundEndTime(double inputPreviousRoundEndTime){

        previousRoundEndTime = inputPreviousRoundEndTime;

    }

    public int getFinalMinute(){

        return finalMinute;

    }

    public void setHitStartTime(int randomTimeIter){

        hitStartTime = getRandomTime(randomTimeIter)+getPreviousRoundEndTime();

    }

    public double getHitStartTime(){

        return hitStartTime;

    }

    public ArrayList<Double> getReactionTimeArrayList(){

        return reactionTimesArrayList;

    }

    public void addReactionTime(){

        reactionTimesArrayList.add(getMillis());

    }

    public void calculateMeanReactionTime(){

        double calcMeanSum = reactionTimesArrayList.get(0);
        for(int rTListIter = 0; rTListIter < reactionTimesArrayList.size(); rTListIter++){
            calcMeanSum = calcMeanSum + reactionTimesArrayList.get(rTListIter);
        }
        calcMeanSum = calcMeanSum/reactionTimesArrayList.size();
        setAverageReactionTime(calcMeanSum);

    }

    public double getAverageReactionTime() {
        return averageReactionTime;
    }

    public void setAverageReactionTime(double averageReactionTime) {
        this.averageReactionTime = averageReactionTime;
    }
}
