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

    //Non-Button Views, I declared Buttons above their Listeners
    private TextView gameCountdownTextView;
    private TextView completedGameTextView;

    // tells timer which random time to pull and counts each test
    private int rTTIter = 0;
    private int gameStartCountdownTime = 3;

    //properties related to running game
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

            if (rTTIter > rTT.getNumberOfTimesToHit()-1) {

                completedGame();

            }
            if (reactionButton.getVisibility() == View.INVISIBLE && rTT.getMillis() > rTT.getHitStartTime()) {

                // if clock's ms is greater than the randomized time plus 
                rTT.setHitStartTime(rTTIter);
                setRandomButtonLocation();
                reactionButton.setVisibility(View.VISIBLE);

            }

            if (reactionButton.getVisibility() == View.VISIBLE && rTT.getMillis() > rTT.getHitStartTime() + 2000) {

                rTT.setPreviousRoundEndTime(rTT.getMillis());
                rTT.recordMiss();
                rTT.addReactionTime();
                Log.i("iter", Integer.toString(rTTIter));
                Log.i("miss", "miss");
                reactionButton.setVisibility(View.INVISIBLE);
                rTTIter++;

            }

            //end of gameStats


        }
    };

    //Needed separate runnable for game start countdown
    Runnable gameStartCountdownRunnable = new Runnable() {

        @Override
        public void run() {

            rTT.tickTocker();
            // ticks every 1000ms
            timerHandler.postDelayed(this, 1000);

            if(gameCountdownTextView.getVisibility() == View.INVISIBLE) {

                gameCountdownTextView.setVisibility(View.VISIBLE);

            } else if(gameStartCountdownTime == 0) {

                gameCountdownTextView.setVisibility(View.INVISIBLE);
                timerHandler.removeCallbacks(gameStartCountdownRunnable);
                rTT.setRandomTimesToHit();
                timerHandler.post(gameTimerRunnable);
                gameStartCountdownTime = 3;

            }

            gameCountdownTextView.setText(Integer.toString(gameStartCountdownTime));
            gameStartCountdownTime--;

        }

    };

    //Buttons section
    private Button restartGameButton, reactionButton, statsActivityButton;
    private RelativeLayout reactionButtonLayout;
    private FrameLayout.LayoutParams newButtonLayoutParam;

    private View.OnClickListener restartGameButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            restartGame();

        }

    };

    private View.OnClickListener reactionButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            rTT.addReactionTime();
            rTTIter++;
            rTT.setHitStartTime(rTTIter);
            Log.i("iter", Integer.toString(rTTIter));
            Log.i("clicked", "button clicked");
            rTT.recordHit();
            reactionButton.setVisibility(View.INVISIBLE);
            setRandomButtonLocation();

        }

    };

    private View.OnClickListener statsActvityGameButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            startStatsActivity();

        }

    };

    //ready cameras action
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initializing the views
        completedGameTextView = findViewById(R.id.textview_completedgame);
        gameCountdownTextView = findViewById(R.id.textview_startcountdown);
        restartGameButton = findViewById(R.id.button_restartgame);
        reactionButton = findViewById(R.id.button_reaction);
        statsActivityButton = findViewById(R.id.button_stats);

        restartGameButton.setOnClickListener(restartGameButtonListener);
        reactionButton.setOnClickListener(reactionButtonListener);
        statsActivityButton.setOnClickListener(statsActvityGameButtonListener);

        reactionButtonLayout = findViewById(R.id.parent_layout);

        //Initialize gameStats engine
        rTT = new ReactionTimeTracker();
        startGame();

    }


    private void startStatsActivity() {

        Intent statsIntent = new Intent(GameActivity.this, StatsActivity.class);
        statsIntent.putExtra("average", rTT.getAverageReactionTime());
        GameActivity.this.startActivity(statsIntent);

    }

    private void startGame() {

        rTT.setNumberOfTimesToHit(20);
        rTT.setStartTime(System.currentTimeMillis());
        timerHandler.post(gameStartCountdownRunnable);

    }

    private void restartGame() {

        restartGameButton.setVisibility(View.INVISIBLE);
        completedGameTextView.setVisibility(View.INVISIBLE);
        statsActivityButton.setVisibility(View.INVISIBLE);
        resetLayout();
        startGame();

    }

    private void completedGame() {

        timerHandler.removeCallbacks(gameTimerRunnable);
        rTT.calculateMeanReactionTime();
        Log.i("avg", Double.toString(rTT.getAverageReactionTime()));
        resetLayout();
        rTTIter = 0;
        restartGameButton.setVisibility(View.VISIBLE);
        completedGameTextView.setVisibility(View.VISIBLE);
        statsActivityButton.setVisibility(View.VISIBLE);
    }

    private void setRandomButtonLocation() {

        randomButtonLocationGenerator = new RandomButtonLocationGenerator(reactionButtonLayout, reactionButton);
        newButtonLayoutParam = new FrameLayout.LayoutParams(reactionButtonLayout.getLayoutParams());
        randomButtonLocationGenerator.generateRandomMarginsForButton();
        newButtonLayoutParam.setMargins(randomButtonLocationGenerator.getRandomLeftMargin(), randomButtonLocationGenerator.getRandomTopMargin(),0,0);
        reactionButtonLayout.setLayoutParams(newButtonLayoutParam);

    }

    private void resetLayout() {

        newButtonLayoutParam.setMargins(0,0,0,0);
        reactionButtonLayout.setLayoutParams(newButtonLayoutParam);

    }

}

// TODO: Drawables class
// TODO: Cloudside Functions class