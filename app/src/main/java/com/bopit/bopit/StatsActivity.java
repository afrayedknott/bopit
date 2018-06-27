package com.bopit.bopit;

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
    private User user;
    private GameStats gameStats;
    private FirebaseFirestore firestoreDatabase;
    private CollectionReference userCollectionRef;
    private CollectionReference gameStatsCollectionRef;
    private StatsRecyclerViewAdapter statsRecyclerViewAdapter;
    private ArrayList<GameStats> gameStatsArrayList = new ArrayList<GameStats>(0);
    private RecyclerView highScoresRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        setUpFirestoreDB();
        loadFirestoreData();
/*        generateTestData();*/
/*        setUpRecyclerView();*/

    }

/*    private void setUpRecyclerView() {

        highScoresRecyclerView = findViewById(R.id.highscores_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        highScoresRecyclerView.setLayoutManager(mLayoutManager);
        attachRecyclerViewAdapter();

    }*/

    private void setUpFirestoreDB() {

        firestoreDatabase = FirebaseFirestore.getInstance();
        gameStatsCollectionRef = firestoreDatabase.collection("gamestats");
        userCollectionRef = firestoreDatabase.collection("users");

    }

    private void loadFirestoreData() {

        gameStatsCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for(DocumentSnapshot querySnapshot : task.getResult()) {

                    GameStats gameStats =
                            new GameStats(
                                    querySnapshot.getString("username"),
                                    querySnapshot.getLong("avg"),
                                    querySnapshot.getLong("best"));
                    gameStats.setGameKey(querySnapshot.getId());
                    gameStatsArrayList.add(gameStats);

                }

                /*attachRecyclerViewAdapter();*/

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

        statsRecyclerViewAdapter = new StatsRecyclerViewAdapter(StatsActivity.this, gameStatsArrayList);
        highScoresRecyclerView.setAdapter(statsRecyclerViewAdapter);

    }

    private void generateTestData() {

        Random rNG = new Random();

        for(int testDataIter = 0; testDataIter < 20; testDataIter++) {

            gameStats = new GameStats("h", (double) 0.596, (double) 0.437);
            gameStats.setUsername("joel");
            gameStats.setAverage(Long.valueOf(rNG.nextInt(10000)));
            gameStatsArrayList.add(gameStats);

            Log.i("arraylist size", Integer.toString(gameStatsArrayList.size()));

        }

    }

}