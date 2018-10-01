package com.bopit.bopit;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class GameEngine {

    //values
    private double gameStartTime; //start of the game instance
    private int randomizedTimeTillButtonVisible; // random time generated to get random difference between start of next round and previous round end
    private int numberOfTimesToHit; // integer of total rounds
    private double averageReactionTime; // average reaction time of user for the round
    private double bestReactionTime; // best reaction time of user for the round
    private int totalHits;
    private ArrayList<String> missRecordArrayList = new ArrayList<>(); //hit-miss record array list
    private ArrayList<Double> startOfRoundArrayList = new ArrayList<>();
    private ArrayList<Double> endOfRoundArrayList = new ArrayList<>();
    private ArrayList<Double> reactionTimesArrayList = new ArrayList<>();

    //
    Random r;

    public GameEngine(){

        r = new Random();

    }

    /////////////////////////////////////////////////////////
    //section important for setting up or beginning of game
    ////////////////////////////////////////////////////////

    public void setNumberOfTimesToHit(int timesToHit){

        numberOfTimesToHit = timesToHit;

    }

    public int getNumberOfTimesToHit() {

        return numberOfTimesToHit;

    }

    public void setRandomTimesToHit(){

        // first round as seed for arraylist
        randomizedTimeTillButtonVisible = r.nextInt(2000) + 2000;
        startOfRoundArrayList.add((double) randomizedTimeTillButtonVisible + System.currentTimeMillis());
        endOfRoundArrayList.add(startOfRoundArrayList.get(0) + 2000);

        //loop thereafter
        for(int randomTimeToHitIter = 1; randomTimeToHitIter < numberOfTimesToHit; randomTimeToHitIter++) {

            randomizedTimeTillButtonVisible = r.nextInt(2000) + 2000;
            startOfRoundArrayList.add(endOfRoundArrayList.get(randomTimeToHitIter-1) + (double) randomizedTimeTillButtonVisible);
            endOfRoundArrayList.add(startOfRoundArrayList.get(randomTimeToHitIter) + 2000);

        }

    }

    ////////////////////////
    // running body of game
    ////////////////////////

    public double getStartOfRound(int roundIter) {

        return startOfRoundArrayList.get(roundIter);

    }

    public double getEndOfRound(int roundIter) {

        return endOfRoundArrayList.get(roundIter);

    }


    public double getGameStartTime(){

        return gameStartTime;

    }

    public void setGameStartTime(double startTimeInput){

        gameStartTime = startTimeInput;

    }

    public void addReactionTime(double millis){

        reactionTimesArrayList.add(millis);

    }

    public double getReactionTime(int roundIter){

        return reactionTimesArrayList.get(roundIter);

    }

    /////////////////////////////
    // tallying up performance
    /////////////////////////////

    public void calculateMeanReactionTime(){

        //account for user missing button clicks

        int trackSuccesses = 0;
        double calcMeanSum = 0;
        if(missRecordArrayList.get(0) == "hit"){
            calcMeanSum = reactionTimesArrayList.get(0) - startOfRoundArrayList.get(0);
            trackSuccesses++;
        }

        for(int rTListIter = 1; rTListIter < reactionTimesArrayList.size(); rTListIter++){

            if(missRecordArrayList.get(rTListIter) == "hit"){
                calcMeanSum = calcMeanSum + reactionTimesArrayList.get(rTListIter) - startOfRoundArrayList.get(rTListIter);
                trackSuccesses++;
            }

        }
        calcMeanSum = calcMeanSum/trackSuccesses;
        setAverageReactionTime(calcMeanSum);

    }

    public void calculateBestReactionTime(){

        double best = 3000;
        double maybeBest = 3000;

        for(int rTListIter = 0; rTListIter < reactionTimesArrayList.size(); rTListIter++){

            maybeBest = reactionTimesArrayList.get(rTListIter) - startOfRoundArrayList.get(rTListIter);

            if(missRecordArrayList.get(rTListIter) == "hit" && maybeBest < best){ best = maybeBest; }

        }

        setBestReactionTime(best);

    }

    public void calculateTotalHits(){

        int trackSuccesses = 0;

        if(missRecordArrayList.get(0) == "hit"){
            trackSuccesses++;
        }

        for(int rTListIter = 1; rTListIter < reactionTimesArrayList.size(); rTListIter++){

            if(missRecordArrayList.get(rTListIter) == "hit"){
                trackSuccesses++;
            }

        }

        totalHits = trackSuccesses;

    }

    public double getBestReactionTime() {
        return bestReactionTime;
    }

    public void setBestReactionTime(double bestReactionTime) {
        this.bestReactionTime = bestReactionTime;
    }

    public double getAverageReactionTime() {

        return averageReactionTime;

    }

    public void setAverageReactionTime(double averageReactionTime) {

        this.averageReactionTime = averageReactionTime;

    }

    /////////////////////
    // miss counting
    //////////////////////

    public void recordMiss() {

        missRecordArrayList.add("miss");

    }

    public void recordHit() {

        missRecordArrayList.add("hit");

    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }
}
