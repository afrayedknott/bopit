package com.bopit.bopit;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by redna on 3/6/2018.
 */

public class ReactionTimeTracker{

    private double startTime = 0; //start of the game instance
    private double millis; //system ms
    private int seconds; // system sec
    private int minutes; // system minutea
    private double previousRoundEndTime = 0;
    private int randomTime; // random time generated to get random difference between start of next round and previous round end
    private double hitStartTime; // actual start of next round time
    private int numberOfTimesToHit; // integer of total rounds
    private double averageReactionTime; // average reaction time of user
    private ArrayList<String> missRecordArrayList = new ArrayList<>(); //hit-miss record array list
    private ArrayList<Double> randomTimesToHitArrayList = new ArrayList<>();
    private ArrayList<Double> startOfRoundArrayList = new ArrayList<>();
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

            randomTime = r.nextInt(3000) + 1000;
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

    public void setHitStartTime(int randomTimeIter){

        hitStartTime = getRandomTime(randomTimeIter)+getPreviousRoundEndTime();
        recordStartOfRound();

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

        //account for user missing button clicks

        int trackSuccesses = 0;
        double calcMeanSum = reactionTimesArrayList.get(0) - startOfRoundArrayList.get(0);
        for(int rTListIter = 1; rTListIter < reactionTimesArrayList.size(); rTListIter++){

            if(missRecordArrayList.get(rTListIter) == "hit"){
                calcMeanSum = calcMeanSum + reactionTimesArrayList.get(rTListIter) - startOfRoundArrayList.get(rTListIter-1);
                trackSuccesses++;
            } else {

            }

        }
        calcMeanSum = calcMeanSum/trackSuccesses;
        setAverageReactionTime(calcMeanSum);

    }

    public double getAverageReactionTime() {

        return averageReactionTime;

    }

    public void setAverageReactionTime(double averageReactionTime) {

        this.averageReactionTime = averageReactionTime;

    }

    public ArrayList<String> getMissRecordArrayList() {

        return missRecordArrayList;

    }

    public void setMissRecordArrayList(ArrayList<String> missRecordArrayList) {

        this.missRecordArrayList = missRecordArrayList;

    }

    public void recordMiss() {

        missRecordArrayList.add("miss");

    }

    public void recordHit() {

        missRecordArrayList.add("hit");

    }

    public ArrayList<Double> getStartOfRoundArrayList() {
        return startOfRoundArrayList;
    }

    public void setStartOfRoundArrayList(ArrayList<Double> startOfRoundArrayList) {
        this.startOfRoundArrayList = startOfRoundArrayList;
    }

    public void recordStartOfRound () {

        startOfRoundArrayList.add(hitStartTime);

    }

}
