package com.bopit.bopit;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

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

    private String installID;
    private double installAverage;
    private double installBest;
    private int totalHits;
    private double previousAverage;
    private double previousBest;
    private double percentile;

    public InstallProfile() {

        this.installAverage = 3000;
        this.installBest = 3000;
        this.previousAverage = 3000;
        this.previousBest = 3000;
        this.percentile = 0;
        this.totalHits = 0;

    //    installProfileDocumentRef = mFirestoreInstance.collection("installProfiles").document(getInstallID());
    //    firestoreStatsRef = mFirestoreInstance.collection("firestoreStats").document("stats");

    }

    public void createAndSetInstallID() { setInstallID(UUID.randomUUID().toString()); }

    public String getInstallID() { return installID; }

    public void setInstallID(String installUUID) { this.installID = installUUID; }

    public double getPercentile() {
        return percentile;
    }

    public void setPercentile(double percentile) {
        this.percentile = percentile;
    }

    public double getInstallAverage() { return installAverage; }

    public void setInstallAverage(double average) { this.installAverage = average;}

    public double getInstallBest() { return installBest; }

    public void setInstallBest(double best) { this.installBest = best;}

    public double getPreviousAverage() { return previousAverage; }

    public void setPreviousAverage (double average) { this.previousAverage = average;}

    public double getPreviousBest() { return previousBest; }

    public void setPreviousBest(double best) { this.previousBest = best;}

    public void updateStatsUponGameCompletion(double average, double best, int hits) {

        updateNewHits(hits);
        checkAndSetAverage(average);
        checkAndSetBest(best);

    }

    public void checkAndSetAverage(double average) {

        setPreviousAverage(average);
        if(getInstallAverage() > average) {setInstallAverage(average);}

    }

    public void checkAndSetBest(double best) {

        if(getPreviousBest() > best){ setPreviousBest(best); }
        if(getInstallBest() > best){ setInstallBest(best); }

    }

    public void updateNewHits(int hits) {

        totalHits = totalHits + hits;

    }

    ///////////////////////////////////////
    // saving or reading this mess offline
    //////////////////////////////////////

}
