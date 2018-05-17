package com.bopit.bopit;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

    private User user;
    private StatsEntry statsEntry;
    private FirebaseFirestore firestoreDatabase;
    private CollectionReference userCollectionRef;
    private CollectionReference highScoreEntryCollectionRef;
    private StatsRecyclerViewAdapter statsRecyclerViewAdapter;
    private ArrayList<StatsEntry> statsEntryArrayList = new ArrayList<StatsEntry>(0);
    private RecyclerView highScoresRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        setUpFirestoreDB();
        loadFirestoreData();
/*        generateTestData();*/
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {

        highScoresRecyclerView = findViewById(R.id.highscores_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        highScoresRecyclerView.setLayoutManager(mLayoutManager);
        attachRecyclerViewAdapter();

    }

    private void setUpFirestoreDB() {

        FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();
        highScoreEntryCollectionRef = firestoreDatabase.collection("highscores");
        userCollectionRef = firestoreDatabase.collection("users");

    }

    private void loadFirestoreData() {

        highScoreEntryCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for(DocumentSnapshot querySnapshot : task.getResult()) {

                    StatsEntry statsEntry =
                            new StatsEntry(
                                    querySnapshot.getString("username"),
                                    querySnapshot.getLong("meanReactionTime"));
                    statsEntry.setHighscoreKey(querySnapshot.getId());
                    statsEntryArrayList.add(statsEntry);

                }

                attachRecyclerViewAdapter();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(StatsActivity.this, "Problem?", Toast.LENGTH_SHORT).show();
                Log.w("?", e.getMessage());

            }

        });

    }

    private void attachRecyclerViewAdapter() {

        statsRecyclerViewAdapter = new StatsRecyclerViewAdapter(StatsActivity.this, statsEntryArrayList);
        highScoresRecyclerView.setAdapter(statsRecyclerViewAdapter);

    }

    private void generateTestData() {

        Random rNG = new Random();

        for(int testDataIter = 0; testDataIter < 20; testDataIter++) {

            statsEntry = new StatsEntry("h", (long) 500);
            statsEntry.setUsername("joael");
            statsEntry.setMeanReactionTime(Long.valueOf(rNG.nextInt(10000)));
            statsEntryArrayList.add(statsEntry);

            Log.i("arraylist size", Integer.toString(statsEntryArrayList.size()));

        }

    }

}