package com.bopit.bopit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class StatsActivity extends AppCompatActivity {

    //data properties
    private InstallProfile installProfile;
    private final String INSTALL_PROFILE_FILE_NAME = "installprofile.txt";

    //data saving tools as properties


    // Views
    private Button resetStatsButton;
    private ImageButton restartGameButton;
    private TextView installPercentileTV, previousBestTV, previousAverageTV, installBestTV,
            installAverageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        this.getSupportActionBar().hide();

        File file = new File(this.getFilesDir(), INSTALL_PROFILE_FILE_NAME);
        InstallProfile tempInstallProfile = readDataInternal();
        if(tempInstallProfile.getInstallID() == null) {
            installProfile = new InstallProfile();
            installProfile.createAndSetInstallID();
        } else {
            installProfile = tempInstallProfile;
        }

        //bring in the Intent data if it exists
        if(getIntent().getExtras() != null) {

            double tempAverage = getIntent().getDoubleExtra("average", 3000);
            double tempBest = getIntent().getDoubleExtra("best", 3000);
            int tempHits = getIntent().getIntExtra("hits", 0);
            installProfile.updateStatsUponGameCompletion(tempAverage, tempBest, tempHits);
            saveJSONInternalData(installProfile, INSTALL_PROFILE_FILE_NAME);
            CloudFirestore cloudFirestore = new CloudFirestore(installProfile);
            cloudFirestore.writeToFirestore();
            cloudFirestore.getFirestoreStats(new CloudFirestore.helperCallback() {
                @Override
                public void onCallback(double percentile) {
                    installProfile.setPercentile(percentile);
                    installPercentileTV.setText(String.format("%2.0f%%", (installProfile.getPercentile())));

                }
            });

        } else {

            CloudFirestore cloudFirestore = new CloudFirestore(installProfile);
            cloudFirestore.writeToFirestore();
            cloudFirestore.getFirestoreStats(new CloudFirestore.helperCallback() {
                @Override
                public void onCallback(double percentile) {
                    installProfile.setPercentile(percentile);
                    installPercentileTV.setText(String.format("%2.0f%%", (installProfile.getPercentile())));

                }
            });

        }

        //initialize views
        resetStatsButton = findViewById(R.id.button_resetstats);
        resetStatsButton.setOnClickListener(resetStatsButtonListener);
        restartGameButton = findViewById(R.id.button_gamerestart);
        restartGameButton.setOnClickListener(restartGameButtonListener);

        installPercentileTV = findViewById(R.id.textview_stats_pct);
        previousAverageTV = findViewById(R.id.textview_previous_avg_time);
        previousBestTV = findViewById(R.id.textview_previous_best_time);
        installAverageTV = findViewById(R.id.textview_pb_avg_time);
        installBestTV = findViewById(R.id.textview_pb_best_time);

        installPercentileTV.setText(String.format("%2.0f%%", (installProfile.getPercentile())));
        previousAverageTV.setText(String.format("%.4f", (installProfile.getPreviousAverage()/1000)));
        previousBestTV.setText(String.format("%.4f", (installProfile.getPreviousBest()/1000)));
        installAverageTV.setText(String.format("%.4f", (installProfile.getInstallAverage()/1000)));
        installBestTV.setText(String.format("%.4f", (installProfile.getInstallBest()/1000)));

        overrideFonts(this, findViewById(android.R.id.content));

    }


    //View related

    private View.OnClickListener resetStatsButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            resetStats();

        }

    };

    private View.OnClickListener restartGameButtonListener = new View.OnClickListener() {

        public void onClick(View V) {

            startGameActivity();

        }

    };

    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView ) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.otf"));
            }
        } catch (Exception e) {
        }
    }

    //data related
    private void resetStats() {

        installProfile = new InstallProfile();
        saveJSONInternalData(installProfile, INSTALL_PROFILE_FILE_NAME);

    }

    public InstallProfile readDataInternal(){

        StringBuilder sb = new StringBuilder();

        try {
            FileInputStream fis = this.openFileInput("installprofile.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

        String json = sb.toString();
        Gson gson = new Gson();
        InstallProfile temp = gson.fromJson(json, InstallProfile.class);
        return temp;

    }

    private void saveJSONInternalData(InstallProfile iP, String fileName) {

        Gson gson = new Gson();
        String s = gson.toJson(iP);
        FileOutputStream outputStream;

        try {
            outputStream = this.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUpFirestoreDB() {



    }

    public void getFirestoreStats(){


    }

    // Intents like in camping

    private void startGameActivity() {

        Intent gamesStartIntent = new Intent(StatsActivity.this, GameActivity.class);
        StatsActivity.this.startActivity(gamesStartIntent);

    }

}