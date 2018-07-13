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

public class GameActivity extends AppCompatActivity {

    //Non-Button Views, I declared Buttons above their Listeners
    private TextView gameCountdownTextView;
    private TextView completedGameTextView;

    // tells timer which random time to pull and counts each test
    private int roundIter = 0;
    private int gameStartCountdownTime = 3;

    //properties related to running game
    private Timer timer;
    private GameEngine gameEngine;
    private RandomButtonLocationGenerator randomButtonLocationGenerator;

    // Clock running in background
    Handler timerHandler = new Handler();
    Runnable gameTimerRunnable = new Runnable() {

        @Override
        public void run() {

            timer.tickTocker();
            // ticks every 1000ms
            timerHandler.postDelayed(this, 5);

            //end of game
            if (roundIter > gameEngine.getNumberOfTimesToHit()-1) {

                Log.i("finished", "fin");
                completedGame();

            } //start a round
            else if (reactionButton.getVisibility() == View.INVISIBLE && System.currentTimeMillis() > gameEngine.getStartOfRound(roundIter)) {

                // if clock's ms is greater than the randomized time plus
                setRandomButtonLocation();
                reactionButton.setVisibility(View.VISIBLE);

            }

            //end a round in 2s if not already ended
            else if (reactionButton.getVisibility() == View.VISIBLE && System.currentTimeMillis() > gameEngine.getEndOfRound(roundIter)) {

                gameEngine.recordMiss();
                gameEngine.addReactionTime(0);

                Log.i("start", Double.toString(gameEngine.getStartOfRound(roundIter)));
                Log.i("end", Double.toString(gameEngine.getEndOfRound(roundIter)));

                Log.i("iter", Integer.toString(roundIter));
                Log.i("miss", "miss");
                reactionButton.setVisibility(View.INVISIBLE);
                roundIter++;

            }

            //end of gameStats


        }
    };

    //Needed separate runnable for game start countdown
    Runnable gameStartCountdownRunnable = new Runnable() {

        @Override
        public void run() {

            timer.tickTocker();
            // ticks every 1000ms
            timerHandler.postDelayed(this, 1000);

            if(gameCountdownTextView.getVisibility() == View.INVISIBLE) {

                gameCountdownTextView.setVisibility(View.VISIBLE);

            } else if(gameStartCountdownTime == 0) {

                gameCountdownTextView.setVisibility(View.INVISIBLE);
                timerHandler.removeCallbacks(gameStartCountdownRunnable);
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

            gameEngine.addReactionTime(System.currentTimeMillis());
            roundIter++;
            Log.i("iter", Integer.toString(roundIter));
            Log.i("clicked", "button clicked");
            gameEngine.recordHit();
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
        timer = new Timer();
        startGame();

    }

    //without this, timer continues to run after task manager closed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandler.removeCallbacks(gameTimerRunnable);
    }

    private void startGame() {

        gameEngine = new GameEngine();
        gameEngine.setNumberOfTimesToHit(5);
        gameEngine.setGameStartTime(System.currentTimeMillis());
        gameEngine.setRandomTimesToHit();
        timerHandler.post(gameStartCountdownRunnable);

    }

    private void restartGame() {

        restartGameButton.setVisibility(View.INVISIBLE);
        completedGameTextView.setVisibility(View.INVISIBLE);
        statsActivityButton.setVisibility(View.INVISIBLE);
        resetLayout();
        roundIter = 0;
        startGame();

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

    private void completedGame() {

        Log.i("finished", "fin");
        gameEngine.calculateTotalHits();
        gameEngine.calculateMeanReactionTime();
        gameEngine.calculateBestReactionTime();
        Log.i("avg", Double.toString(gameEngine.getAverageReactionTime()));
        resetLayout();
        timerHandler.removeCallbacks(gameTimerRunnable);
        restartGameButton.setVisibility(View.VISIBLE);
        completedGameTextView.setVisibility(View.VISIBLE);
        statsActivityButton.setVisibility(View.VISIBLE);
    }

    private void startStatsActivity() {

        Intent statsIntent = new Intent(GameActivity.this, StatsActivity.class);
        statsIntent.putExtra("average", gameEngine.getAverageReactionTime());
        statsIntent.putExtra("best", gameEngine.getBestReactionTime());
        statsIntent.putExtra("hits", gameEngine.getTotalHits());
        GameActivity.this.startActivity(statsIntent);

    }

}

// TODO: Drawables class
// TODO: Cloudside Functions class