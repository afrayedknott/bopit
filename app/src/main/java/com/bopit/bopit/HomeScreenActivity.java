package com.bopit.bopit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        gameStartButton = findViewById(R.id.button_gamestart);
        gameStartButton.setOnClickListener(gameStartButtonListener);
        statsButton = findViewById(R.id.button_stats);
        statsButton.setOnClickListener(statsButtonListener);

    }

    private Button gameStartButton, statsButton;

    private View.OnClickListener gameStartButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            startGameActivity();

        }

    };

    private View.OnClickListener statsButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            startStatsActivity();

        }

    };

    private void startGameActivity() {

        Intent gameStartIntent = new Intent(HomeScreenActivity.this, GameActivity.class);
        HomeScreenActivity.this.startActivity(gameStartIntent);

    }

    private void startStatsActivity() {

        Intent statsIntent = new Intent(HomeScreenActivity.this, StatsActivity.class);
        HomeScreenActivity.this.startActivity(statsIntent);

    }

}

//TODO: create a method for checking for unique name
//TODO: add trigger on stats button to recalculate percentile because other users might move percentile around