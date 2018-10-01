package com.bopit.bopit;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

public class CloudFirestore {

    private InstallProfile installProfile;
    private FirebaseFirestore firestoreDatabase;
    private CollectionReference appStatsFirestoreDBCollRef;
    private DocumentReference percentileCalcStatsFirestoreDBDocRef;
    private CollectionReference installProfileFirestoreDBCollRef;
    private DocumentReference installProfileFirestoreDBDocRef;

    public CloudFirestore(InstallProfile iP) {

        installProfile = iP;
        setUpFirestoreDB(installProfile.getInstallID());

    }

    public void setUpFirestoreDB(String installID) {

        firestoreDatabase = FirebaseFirestore.getInstance();
        appStatsFirestoreDBCollRef = firestoreDatabase.collection("firestoreStats");
        percentileCalcStatsFirestoreDBDocRef = appStatsFirestoreDBCollRef.document("percentileCalcStats");
        installProfileFirestoreDBCollRef = firestoreDatabase.collection("installProfiles");
        installProfileFirestoreDBDocRef = installProfileFirestoreDBCollRef.document(installID);

    }

    public void writeToFirestore() {

        installProfileFirestoreDBDocRef.set(installProfile);

    }

    public void getFirestoreStats(final helperCallback callback) {

        Query installProfilePercentileQuery =
                percentileCalcStatsFirestoreDBDocRef.collection("percentileGroups").
                whereGreaterThanOrEqualTo("min", (installProfile.getInstallAverage()*.001)).
                orderBy("min", Query.Direction.ASCENDING).limit(1);

        installProfilePercentileQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        installProfile.setPercentile(Double.parseDouble(document.getId()));
                        Log.i("docid percentile", document.getId());
                        Log.i("docid into percentile", Double.toString(installProfile.getPercentile()));
                    }
                    Log.i("percentile retrieved", Double.toString(installProfile.getPercentile()));
                } else {
                    Log.i(TAG, "Error getting documents: ", task.getException());
                }
                callback.onCallback(installProfile.getPercentile());
            }
        });



    }

    public interface helperCallback{
        void onCallback(double percentile);
    }
}