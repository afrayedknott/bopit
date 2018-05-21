package com.bopit.bopit;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

public class DisplayMetricsManager {

    private DisplayMetrics displayMetrics;
    private Button button;
    private float screenPixelScale;
    private int height;
    private int width;
    private int randomLeftMargin;
    private int randomTopMargin;
    private int randomRightMargin;
    private int randomBottomMargin;
    private Random random;


    public DisplayMetricsManager(DisplayMetrics dM, Button b){

        displayMetrics = dM;
        button = b;
        screenPixelScale = displayMetrics.density;
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
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
        int buttonVerticalPixelsDeduction = button.getHeight();
        int buttonHorizontalPixelsDeduction = button.getWidth();

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

    public float getScreenPixelScale() {
        return screenPixelScale;
    }

    public void setScreenPixelScale(float screenPixelScale) {
        this.screenPixelScale = displayMetrics.density;
    }
}
