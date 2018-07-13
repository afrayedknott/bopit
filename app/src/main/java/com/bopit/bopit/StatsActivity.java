package com.bopit.bopit;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class StatsActivity extends AppCompatActivity {

    //data properties
    private InstallProfile installProfile;

    //data saving tools as properties
    private FirebaseFirestore firestoreDatabase;
    private CollectionReference userCollectionRef;
    private CollectionReference gameStatsCollectionRef;

    // Views
    private TextView previousBestTV;
    private TextView previousAverageTV;
    private TextView installBestTV;
    private TextView installAverageTV;

    //View related
    private RecyclerView highScoresRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //bring in the Intent data if it exists
        installProfile = new InstallProfile(this);

        if(getIntent().getExtras() != null) {

            int tempTotalHits = getIntent().getIntExtra("hits", 0);
            double tempAverage = getIntent().getDoubleExtra("average", 3000);
            double tempBest = getIntent().getDoubleExtra("best", 3000);
            installProfile.compileStatsUponCompletedGame(tempTotalHits, tempAverage, tempBest);
            installProfile.saveDataInternal(this);

        }

        //initialize views
        resetStatsButton = findViewById(R.id.button_resetstats);
        resetStatsButton.setOnClickListener(resetStatsButtonListener);

        previousAverageTV = findViewById(R.id.textview_previous_avg_time);
        previousBestTV = findViewById(R.id.textview_previous_best_time);
        installAverageTV = findViewById(R.id.textview_pb_avg_time);
        installBestTV = findViewById(R.id.textview_pb_best_time);

        previousAverageTV.setText(String.format("%.4f", (installProfile.getPreviousAverage()/1000)));
        previousBestTV.setText(String.format("%.4f", (installProfile.getPreviousBest()/1000)));
        installAverageTV.setText(String.format("%.4f", (installProfile.getInstallAverage()/1000)));
        installBestTV.setText(String.format("%.4f", (installProfile.getInstallBest()/1000)));

        //save to cloud
        setUpFirestoreDB();
//        loadFirestoreData();
/*        generateTestData();*/
/*        setUpRecyclerView();*/

    }

    private Button resetStatsButton;

    private View.OnClickListener resetStatsButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            resetStats();

        }

    };

    private void resetStats() {

        installProfile.setInstallAverage(3000);
        installProfile.setInstallBest(3000);
        installProfile.setPreviousAverage(3000);
        installProfile.setPreviousBest(3000);
        installProfile.saveDataInternal(this);

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