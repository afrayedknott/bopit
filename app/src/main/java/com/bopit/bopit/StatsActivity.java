package com.bopit.bopit;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class StatsActivity extends AppCompatActivity {

    //data properties
    private InstallProfile installProfile;

    //data saving tools as properties
    private FirebaseFirestore firestoreDatabase;

    // Views
    private TextView installPercentileTV;
    private TextView previousBestTV;
    private TextView previousAverageTV;
    private TextView installBestTV;
    private TextView installAverageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        this.getSupportActionBar().hide();

        setUpFirestoreDB();

        installProfile = new InstallProfile(this, firestoreDatabase); //includes reading internal

        //bring in the Intent data if it exists
        if(getIntent().getExtras() != null) {

            double tempAverage = getIntent().getDoubleExtra("average", 3000);
            double tempBest = getIntent().getDoubleExtra("best", 3000);
            installProfile.updateStatsUponGameCompletion(tempAverage, tempBest);
            installProfile.saveDataInternal(this);
            installProfile.writeToFirestore();
            installProfile.getFirestoreStats();

        }

        //initialize views
        resetStatsButton = findViewById(R.id.button_resetstats);
        resetStatsButton.setOnClickListener(resetStatsButtonListener);

        installPercentileTV = findViewById(R.id.textview_stats_pct);
        previousAverageTV = findViewById(R.id.textview_previous_avg_time);
        previousBestTV = findViewById(R.id.textview_previous_best_time);
        installAverageTV = findViewById(R.id.textview_pb_avg_time);
        installBestTV = findViewById(R.id.textview_pb_best_time);

        installPercentileTV.setText(String.format("%2.2f%%", (installProfile.getPercentile()/1000)));
        previousAverageTV.setText(String.format("%.4f", (installProfile.getPreviousAverage()/1000)));
        previousBestTV.setText(String.format("%.4f", (installProfile.getPreviousBest()/1000)));
        installAverageTV.setText(String.format("%.4f", (installProfile.getInstallAverage()/1000)));
        installBestTV.setText(String.format("%.4f", (installProfile.getInstallBest()/1000)));

        overrideFonts(this, findViewById(android.R.id.content));
        //save to cloud
        installProfile.getFirestoreStats();

//        loadFirestoreData();
/*        generateTestData();*/
/*        setUpRecyclerView();*/

    }


    //View related
    private Button resetStatsButton;

    private View.OnClickListener resetStatsButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            resetStats();

        }

    };

    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView ) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.otf"));
            }
        } catch (Exception e) {
        }
    }

    //data related
    private void resetStats() {

        installProfile.setInstallAverage(3000);
        installProfile.setInstallBest(3000);
        installProfile.setPreviousAverage(3000);
        installProfile.setPreviousBest(3000);
        installProfile.setPercentile(0);
        installProfile.saveDataInternal(this);

    }

    private void setUpFirestoreDB() {

        firestoreDatabase = FirebaseFirestore.getInstance();

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