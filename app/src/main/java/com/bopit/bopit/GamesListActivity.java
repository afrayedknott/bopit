package com.bopit.bopit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GamesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameslist);
        this.getSupportActionBar().hide();
    }
}
