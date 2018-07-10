package com.bopit.bopit;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by redna on 3/6/2018.
 */

public class Timer {

    private double millis; //system ms
    private int seconds; // system sec
    private int minutes; // system minutea

    public Timer(){

    }

    //runs without a timer by reposting this handler at the end of the runnable

    public void tickTocker(){

        setMillis(System.currentTimeMillis());
        setSeconds((int) getMillis() / 1000);
        setMinutes(getSeconds() / 60);
        setSeconds(getSeconds() % 60);

    };

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

}
