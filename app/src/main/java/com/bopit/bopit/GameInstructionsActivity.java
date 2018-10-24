package com.bopit.bopit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameInstructionsActivity extends AppCompatActivity {

    private ImageButton reactionInstructionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_instructions);
        this.getSupportActionBar().hide();

        reactionInstructionsButton = findViewById(R.id.button_reaction_instructions);

        reactionInstructionsButton.setOnClickListener(reactionInstructionsButtonListener);

        overrideFonts(this, findViewById(android.R.id.content));


    }

    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.otf"));
            }
        } catch (Exception e) {
        }
    }

    //Buttons section

    private void startGameActivity() {

        Intent gamesStartIntent = new Intent(GameInstructionsActivity.this, GameActivity.class);
        GameInstructionsActivity.this.startActivity(gamesStartIntent);

    }

    private View.OnClickListener reactionInstructionsButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            startGameActivity();

        }

    };

}
