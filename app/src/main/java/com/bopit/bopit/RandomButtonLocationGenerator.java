package com.bopit.bopit;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Random;

public class RandomButtonLocationGenerator {

    private RelativeLayout relativeLayout;
    private Button button;
    private int height;
    private int width;
    private int randomLeftMargin;
    private int randomTopMargin;
    private int randomRightMargin;
    private int randomBottomMargin;
    private Random random;


    public RandomButtonLocationGenerator(RelativeLayout rL, Button b){

        relativeLayout = rL;
        button = b;
        height = rL.getHeight();
        width = rL.getWidth();
        random = new Random();

    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void generateRandomMarginsForButton(){

        //button measurements to account for
        int buttonHorizontalPixelsDeduction = button.getWidth();
        int buttonVerticalPixelsDeduction = button.getHeight();

        //randomized location coordinates
        setRandomLeftMargin(random.nextInt(width - buttonHorizontalPixelsDeduction));
        setRandomTopMargin(random.nextInt(height - buttonVerticalPixelsDeduction));

    }

    public int getRandomBottomMargin() {
        return randomBottomMargin;
    }

    public void setRandomBottomMargin(int randomBottomMargin) {
        this.randomBottomMargin = randomBottomMargin;
    }

    public int getRandomRightMargin() {
        return randomRightMargin;
    }

    public void setRandomRightMargin(int randomRightMargin) {
        this.randomRightMargin = randomRightMargin;
    }

    public int getRandomLeftMargin() {
        return randomLeftMargin;
    }

    public void setRandomLeftMargin(int randomLeftMargin) {
        this.randomLeftMargin = randomLeftMargin;
    }

    public int getRandomTopMargin() {
        return randomTopMargin;
    }

    public void setRandomTopMargin(int randomTopMargin) {
        this.randomTopMargin = randomTopMargin;
    }

}
