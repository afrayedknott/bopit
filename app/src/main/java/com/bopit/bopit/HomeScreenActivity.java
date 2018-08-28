package com.bopit.bopit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeScreenActivity extends AppCompatActivity {

    private TextView appTitle;
    private ImageButton gameStartButton, statsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        this.getSupportActionBar().hide();

        gameStartButton = findViewById(R.id.button_gamestart);
        gameStartButton.setOnClickListener(gameStartButtonListener);
        statsButton = findViewById(R.id.button_stats);
        statsButton.setOnClickListener(statsButtonListener);

    }

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