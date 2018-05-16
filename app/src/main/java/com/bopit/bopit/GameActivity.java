package com.bopit.bopit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private ReactionTimeTracker rTT = new ReactionTimeTracker();

    // Clock running in background
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {

            rTT.tickTocker();
            // ticks every 1000ms
            timerHandler.postDelayed(this, 1000);

            if(rTT.getMillis() > rTT.getRandomTime(rTTIter)+rTT.getPreviousRoundEndTime()){

                // if clock's ms is greater than the randomized time plus 
                rTT.setHitStartTime(rTTIter);
                reactionButton.setText(R.string.reactionbutton_hit);

            }

            if(reactionButton.getText().equals("HIT!") && (rTT.getMillis() > rTT.getHitStartTime()+3000)) {

                rTT.getRandomTime(rTTIter);
                rTT.setPreviousRoundEndTime(rTT.getMillis());
                reactionButton.setText(R.string.reactionbutton_stay);

            }

            if(rTTIter >= 19){

                timerHandler.removeCallbacks(timerRunnable);
                restartGameButton.setText(R.string.gostopbutton_go);
                statsEntry.setMeanReactionTime(rTT.calculateMeanReactionTime());
                statsEntryCollectionRef.document().set(statsEntry);
                rTTIter = 0;

            }


        }
    };

    private Button restartGameButton, reactionButton;

    private View.OnClickListener restartGameButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            if (restartGameButton.getText().equals("STOP!")) {

                timerHandler.removeCallbacks(timerRunnable);
                restartGameButton.setText(R.string.gostopbutton_go);
                rTTIter = 0;

            } else {

                rTT.setStartTime(System.currentTimeMillis());
                timerHandler.postDelayed(timerRunnable, 0);
                rTT.setRandomTimesToHit();
                restartGameButton.setText(R.string.gostopbutton_stop);
                reactionButton.setText(R.string.reactionbutton_stay);

            }
        }
    };

    private View.OnClickListener reactionButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            if (reactionButton.getText().equals("HIT!")) {

                rTTIter++;
                rTT.setPreviousRoundEndTime(rTT.getMillis());
                rTT.recordReactionTime();
                reactionButton.setText(R.string.reactionbutton_stay);

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent receiveUserIntent = this.getIntent();
        user = (User) receiveUserIntent.getSerializableExtra("USERNAME_VALUE");
        statsEntry = new StatsEntry(user.getUsername(), Long.valueOf(100000));

        //Initializing the views
        restartGameButton = findViewById(R.id.button_restartgame);
        reactionButton = findViewById(R.id.button_reaction);



        restartGameButton.setOnClickListener(restartGameButtonListener);
        reactionButton.setOnClickListener(reactionButtonListener);

        FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();
        statsEntryCollectionRef = firestoreDatabase.collection("highscores");

    }

    private void startStatsActivity() {

        Intent statsIntent = new Intent(GameActivity.this, StatsActivity.class);
        GameActivity.this.startActivity(statsIntent);

    }

}

// TODO: Drawables class
// TODO: Cloudside Functions class