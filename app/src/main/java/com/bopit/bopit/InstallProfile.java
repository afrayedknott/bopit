package com.bopit.bopit;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private double betterThanInstallProfilesCount;
    private double allInstallProfilesCount;
    private double percentile;

    private FirebaseFirestore mFirestoreInstance;
    private DocumentReference installProfileDocumentRef;
    private DocumentReference firestoreStatsRef;

    public InstallProfile(Context context, FirebaseFirestore mFirestoreInstance) {

        File file = new File(context.getFilesDir(), INSTALL_PROFILE_FILE_NAME);
        if(file.exists()) {
            readDataInternal(context);
        } else {
            createAndSetInstallID();
            installAverage = 3000;
            installBest = 3000;
            previousAverage = 3000;
            previousBest = 3000;
            percentile = 0;
        }

        installProfileDocumentRef = mFirestoreInstance.collection("installProfiles").document(getInstallID());
        firestoreStatsRef = mFirestoreInstance.collection("firestoreStats").document("stats");

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

    public void updateStatsUponGameCompletion(double average, double best) {

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

    public void writeToFirestore(){

        installProfileDocumentRef.collection("installProfiles").document(getInstallID()).set(this);

    }

    public void getFirestoreStats(){

        firestoreStatsRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setAllInstallProfilesCount(documentSnapshot.getDouble("totalInstallProfilesCount"));
            }
        });

        installProfileDocumentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setBetterThanInstallProfilesCount(documentSnapshot.toObject(InstallProfile.class).betterThanInstallProfilesCount);
            }
        });

        setPercentile(getBetterThanInstallProfilesCount()/getAllInstallProfilesCount());

    }

    public double getBetterThanInstallProfilesCount() {
        return betterThanInstallProfilesCount;
    }

    public void setBetterThanInstallProfilesCount(double betterThanInstallProfilesCount) {
        this.betterThanInstallProfilesCount = betterThanInstallProfilesCount;
    }

    public double getAllInstallProfilesCount() {
        return allInstallProfilesCount;
    }

    public void setAllInstallProfilesCount(double allInstallProfilesCount) {
        this.allInstallProfilesCount = allInstallProfilesCount;
    }

    /*public Task<Void> writeAndReadInstallProfile(){

        mFirestoreInstance.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(installProfileDocumentRef);
                setPercentile(snapshot.getDouble("installPercentile"));
                setInstallAverage(snapshot.getDouble("installAverage"));

                transaction.update(installProfileDocumentRef,);

                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                    }
                });

    }*/

}
