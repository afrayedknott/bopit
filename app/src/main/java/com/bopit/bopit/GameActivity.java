package com.bopit.bopit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GameActivity extends AppCompatActivity {

    private TextView gameCountdown;
    private User user;

    // tells timer which random time to pull and counts each test
    private int rTTIter = 0;

    private FirebaseFirestore firestoreDatabase;
    private CollectionReference userCollectionRef;
    private CollectionReference statsEntryCollectionRef;
    private StatsEntry statsEntry;
    private ReactionTimeTracker rTT;
    private DisplayMetricsManager dMM;
    private DisplayMetrics displayMetrics;

    // Clock running in background
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {

            rTT.tickTocker();
            // ticks every 1000ms
            timerHandler.postDelayed(this, 1000);

            if (rTT.getMillis() > rTT.getRandomTime(rTTIter) + rTT.getPreviousRoundEndTime()) {

                // if clock's ms is greater than the randomized time plus 
                rTT.setHitStartTime(rTTIter);
                setRandomButtonLocation();
                reactionButton.setVisibility(View.VISIBLE);

            }

            if (reactionButton.getVisibility() == View.VISIBLE && (rTT.getMillis() > rTT.getHitStartTime() + 3000)) {

                rTT.getRandomTime(rTTIter);
                rTT.setPreviousRoundEndTime(rTT.getMillis());
                reactionButton.setVisibility(View.INVISIBLE);

            }

            //end of game
            if (rTTIter >= 19) {

                timerHandler.removeCallbacks(timerRunnable);
                restartGameButton.setText(R.string.str_button_restartgame);
                statsEntry.setMeanReactionTime(rTT.calculateMeanReactionTime());
                statsEntryCollectionRef.document().set(statsEntry);
                rTTIter = 0;
                restartGameButton.setVisibility(View.VISIBLE);

            }


        }
    };

    private Button restartGameButton, reactionButton;
    private LinearLayout reactionButtonLayout;
    private LinearLayout.LayoutParams newButtonLayoutParam;

    private View.OnClickListener restartGameButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            startGame();

        }

    };

    private View.OnClickListener reactionButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            rTTIter++;
            rTT.setPreviousRoundEndTime(rTT.getMillis());
            rTT.recordReactionTime();
            reactionButton.setVisibility(View.INVISIBLE);
            setRandomButtonLocation();

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initialize user data
        Intent receiveUserIntent = this.getIntent();
        user = (User) receiveUserIntent.getSerializableExtra("USERNAME_VALUE");

        //Initializing the views
        restartGameButton = findViewById(R.id.button_restartgame);
        reactionButton = findViewById(R.id.button_reaction);

        restartGameButton.setOnClickListener(restartGameButtonListener);
        reactionButton.setOnClickListener(reactionButtonListener);

        reactionButtonLayout = findViewById(R.id.layout_reactionbutton);

        //Initialize game engine
        rTT = new ReactionTimeTracker();
        startDisplayMetricsManager();

        //Initialize db conn
        FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();
        statsEntryCollectionRef = firestoreDatabase.collection("highscores");

        startGame();
    }

    private void startStatsActivity() {

        Intent statsIntent = new Intent(GameActivity.this, StatsActivity.class);
        GameActivity.this.startActivity(statsIntent);

    }

    private void startDisplayMetricsManager() {

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        dMM = new DisplayMetricsManager(displayMetrics, reactionButton);

    }

    private void startGame() {

        rTT.setStartTime(System.currentTimeMillis());
        timerHandler.postDelayed(timerRunnable, 0);
        rTT.setRandomTimesToHit();
        rTTIter = 0;
        restartGameButton.setVisibility(View.INVISIBLE);

    }

    private void setRandomButtonLocation() {

        newButtonLayoutParam = new LinearLayout.LayoutParams(reactionButtonLayout.getWidth(), reactionButtonLayout.getHeight());
        dMM.generateRandomMarginsForButton();
        newButtonLayoutParam.setMargins(0,0,0,0);
        reactionButton.setLayoutParams(newButtonLayoutParam);
        Log.d("new width", Boolean.toString(reactionButtonLayout.getWidth() == newButtonLayoutParam.width));

    }

}

// TODO: randomize button location
// TODO: Drawables class
// TODO: Cloudside Functions class