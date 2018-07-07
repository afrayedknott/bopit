package com.bopit.bopit;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class StatsActivity extends AppCompatActivity {

    //data properties
    private AppInstall appInstall;

    //data saving tools as properties
    private SharedPreferences.Editor editor;
    private FirebaseFirestore firestoreDatabase;
    private CollectionReference userCollectionRef;
    private CollectionReference gameStatsCollectionRef;

    //View related
    private RecyclerView highScoresRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //check SharedPreferences for UUID of app install
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        if(pref.getString("appUUID","")==""){
            appInstall = new AppInstall();
            editor.putString("appUUID", appInstall.getInstallID());
            editor.apply();
        } else {
            appInstall.setInstallID(pref.getString("appUUID",""));
        }

        //check if StatsActivity came from Game or Home
        if(getIntent()!=null) {
            appInstall.setPreviousAverage(getIntent().getDoubleExtra("average",0));
        } else {
        }


        //save to cloud
        setUpFirestoreDB();
//        loadFirestoreData();
/*        generateTestData();*/
/*        setUpRecyclerView();*/

    }

    private void setUpFirestoreDB() {

        firestoreDatabase = FirebaseFirestore.getInstance();
        gameStatsCollectionRef = firestoreDatabase.collection("gamestats");
        userCollectionRef = firestoreDatabase.collection("users");

    }

/*    private void loadFirestoreData() {

        gameStatsCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for(DocumentSnapshot querySnapshot : task.getResult()) {

                    GameStats gameStats =
                            new GameStats(
                                    querySnapshot.getString("username"),
                                    querySnapshot.getDouble("avg"),
                                    querySnapshot.getDouble(("best"));
                    gameStats.setGameKey(querySnapshot.getId());
                    gameStatsArrayList.add(gameStats);

                }

                *//*attachRecyclerViewAdapter();*//*

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(StatsActivity.this, "Problem?", Toast.LENGTH_SHORT).show();
                Log.w("?", e.getMessage());

            }

        });

    }*/

}