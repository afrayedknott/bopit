package com.bopit.bopit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GameActivity extends AppCompatActivity {

    private TextView gameCountdownTextView;
    private User user;

    // tells timer which random time to pull and counts each test
    private int rTTIter = 0;
    private int countdownTick = 3;

    private FirebaseFirestore firestoreDatabase;
    private CollectionReference userCollectionRef;
    private CollectionReference statsEntryCollectionRef;
    private Game game;
    private ReactionTimeTracker rTT;
    private RandomButtonLocationGenerator randomButtonLocationGenerator;

    // Clock running in background
    Handler timerHandler = new Handler();
    Runnable gameTimerRunnable = new Runnable() {

        @Override
        public void run() {

            rTT.tickTocker();
            // ticks every 1000ms
            timerHandler.postDelayed(this, 0);

            if (reactionButton.getVisibility() == View.INVISIBLE && rTT.getMillis() > rTT.getRandomTime(rTTIter) + rTT.getPreviousRoundEndTime()) {

                // if clock's ms is greater than the randomized time plus 
                rTT.setHitStartTime(rTTIter);
                setRandomButtonLocation();
                reactionButton.setVisibility(View.VISIBLE);

            }

            if (reactionButton.getVisibility() == View.VISIBLE && rTT.getMillis() > rTT.getHitStartTime() + 3000) {

                rTT.getRandomTime(rTTIter);
                rTT.setPreviousRoundEndTime(rTT.getMillis());
                reactionButton.setVisibility(View.INVISIBLE);

            }

            //end of game
            if (rTTIter >= 19) {

                timerHandler.removeCallbacks(gameTimerRunnable);
                restartGameButton.setText(R.string.str_button_restartgame);
                game.setAverage(rTT.calculateMeanReactionTime());
                statsEntryCollectionRef.document().set(game);
                rTTIter = 0;
                restartGameButton.setVisibility(View.VISIBLE);

            }

        }
    };


    Runnable gameStartCountdownRunnable = new Runnable() {

        @Override
        public void run() {

            rTT.tickTocker();
            // ticks every 1000ms
            timerHandler.postDelayed(this, 1000);

            if(gameCountdownTextView.getVisibility() == View.INVISIBLE) {

                gameCountdownTextView.setVisibility(View.VISIBLE);

            } else if(countdownTick == 0) {

                gameCountdownTextView.setVisibility(View.INVISIBLE);
                timerHandler.removeCallbacks(gameStartCountdownRunnable);
                rTT.setRandomTimesToHit();
                timerHandler.post(gameTimerRunnable);

            }

            gameCountdownTextView.setText(Integer.toString(countdownTick));
            countdownTick--;
            Log.i("text", gameCountdownTextView.getText().toString());


        }

    };

    private Button restartGameButton, reactionButton;
    private RelativeLayout reactionButtonLayout;
    private FrameLayout.LayoutParams newButtonLayoutParam;

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
        gameCountdownTextView = findViewById(R.id.textview_startcountdown);
        restartGameButton = findViewById(R.id.button_restartgame);
        reactionButton = findViewById(R.id.button_reaction);

        restartGameButton.setOnClickListener(restartGameButtonListener);
        reactionButton.setOnClickListener(reactionButtonListener);

        reactionButtonLayout = findViewById(R.id.parent_layout);

        //Initialize game engine
        rTT = new ReactionTimeTracker();


        //Initialize db conn
        FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();
        statsEntryCollectionRef = firestoreDatabase.collection("highscores");

        startGame();
    }

    private void startStatsActivity() {

        Intent statsIntent = new Intent(GameActivity.this, StatsActivity.class);
        GameActivity.this.startActivity(statsIntent);

    }

    private void startGame() {

        rTT.setStartTime(System.currentTimeMillis());
        timerHandler.postDelayed(gameStartCountdownRunnable, 0);

    }

    private void setRandomButtonLocation() {

        randomButtonLocationGenerator = new RandomButtonLocationGenerator(reactionButtonLayout, reactionButton);
        newButtonLayoutParam = new FrameLayout.LayoutParams(reactionButtonLayout.getLayoutParams());
        randomButtonLocationGenerator.generateRandomMarginsForButton();
        newButtonLayoutParam.setMargins(randomButtonLocationGenerator.getRandomLeftMargin(), randomButtonLocationGenerator.getRandomTopMargin(),0,0);
        reactionButtonLayout.setLayoutParams(newButtonLayoutParam);

    }

}

// TODO: Drawables class
// TODO: Cloudside Functions class