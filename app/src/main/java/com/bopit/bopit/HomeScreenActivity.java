package com.bopit.bopit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class HomeScreenActivity extends AppCompatActivity {


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        user = new User("John Doe");

        gameStartButton = findViewById(R.id.button_gamestart);
        gameStartButton.setOnClickListener(usernameSubmitButtonListener);
        statsButton = findViewById(R.id.button_stats);
        statsButton.setOnClickListener(statsButtonListener);

    }

    private View.OnClickListener usernameSubmitButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            Intent startMainActivityIntent = new Intent(HomeScreenActivity.this, GameActivity.class);
            startMainActivityIntent.putExtra("USERNAME_VALUE", user);
            HomeScreenActivity.this.startActivityForResult(startMainActivityIntent, 0);

        }

    };

    private Button statsButton, gameStartButton;

    private View.OnClickListener statsButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            startStatsActivity();

        }

    };
    private View.OnClickListener gameStartButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            startGameActivity();

        }

    };

    private void startStatsActivity() {

        Intent statsIntent = new Intent(HomeScreenActivity.this, StatsActivity.class);
        HomeScreenActivity.this.startActivity(statsIntent);

    }

    private void startGameActivity() {

        Intent gameStartIntent = new Intent(HomeScreenActivity.this, GameActivity.class);
        HomeScreenActivity.this.startActivity(gameStartIntent);

    }

    //TODO: create a method for checking for unique name

}
