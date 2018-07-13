package com.bopit.bopit;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.UUID;

//This used to be called User, but I decided I want to minimize complexity for myself and users by
// just tracking stats per install. It's not necessary to track across reinstalls or separate
// devices for my purpose or user experience.
public class InstallProfile implements Serializable {

    private final String INSTALL_PROFILE_FILE_NAME = "installprofile.txt";
    private String installID;
    private double installAverage;
    private double installBest;
    private double previousAverage;
    private double previousBest;
    private double lifetimeAverage;
    private int successfulRoundsPlayed;

    public InstallProfile(Context context) {

        File file = new File(context.getFilesDir(), INSTALL_PROFILE_FILE_NAME);
        if(file.exists()) {
            readDataInternal(context);
        } else {
            createAndSetInstallID();
            installAverage = 3000;
            installBest = 3000;
            previousAverage = 3000;
            previousBest = 3000;
            successfulRoundsPlayed = 0;
        }

    }

    public void createAndSetInstallID() { setInstallID(UUID.randomUUID().toString()); }

    public String getInstallID() { return installID; }

    public void setInstallID(String installUUID) { this.installID = installUUID; }

    public double getInstallAverage() { return installAverage; }

    public void setInstallAverage(double average) { this.installAverage = average;}

    public double getInstallBest() { return installBest; }

    public void setInstallBest(double best) { this.installBest = best;}

    public double getPreviousAverage() { return previousAverage; }

    public void setPreviousAverage (double average) { this.previousAverage = average;}

    public double getPreviousBest() { return previousBest; }

    public void setPreviousBest(double best) { this.previousBest = best;}

    public int getSuccessfulRoundsPlayed() { return successfulRoundsPlayed; }

    public void setSuccessfulRoundsPlayed(int successfulRoundsPlayed) {

        this.successfulRoundsPlayed = this.successfulRoundsPlayed + successfulRoundsPlayed;

    }

    public void compileStatsUponCompletedGame(int totalHits, double average, double best) {

        sumAndsetTotalHits(totalHits);
        checkAndSetAverage(average);
        checkAndSetBest(best);

    }

    public void sumAndsetTotalHits(int totalHits){

        this.successfulRoundsPlayed = this.successfulRoundsPlayed + totalHits;

    }

    public void checkAndSetAverage(double average) {

        setPreviousAverage(average);
        if(getInstallAverage() > average) {setInstallAverage(average);}

    }

    public void checkAndSetBest(double best) {

        if(getPreviousBest() > best){ setPreviousBest(best); }
        if(getInstallBest() > best){ setInstallBest(best); }

    }

    ///////////////////////////////////////
    // saving or reading this mess offline
    //////////////////////////////////////

    public void saveDataInternal(Context context) {

        String filename = "installprofile.txt";

        Gson gson = new Gson();
        String s = gson.toJson(this);

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readDataInternal(Context context){

        StringBuilder sb = new StringBuilder();

        try {
            FileInputStream fis = context.openFileInput("installprofile.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

        String json = sb.toString();
        Gson gson = new Gson();
        InstallProfile temp = gson.fromJson(json, InstallProfile.class);

        //assign all temp properties to actual object properties
        setInstallID(temp.getInstallID());
        setInstallAverage(temp.getInstallAverage());
        setInstallBest(temp.getInstallBest());
        setPreviousAverage(temp.getPreviousAverage());
        setPreviousBest(temp.getPreviousBest());

    }

}
