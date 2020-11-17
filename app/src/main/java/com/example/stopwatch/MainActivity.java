/* #############################
 *
 * Author: Johnathon Mc Grory
 * Date : 12th November 2020
 * Description : Stopwatch Java Code
 *
 * ############################# */

package com.example.stopwatch;


import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CountUpTimer timer;
    int minutes, hours = 0;
    TextView tvSecs, tvMins, tvHrs;
    MediaPlayer player;
    boolean runInProgress, runPaused, runReset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSecs = findViewById(R.id.tvSeconds);
        tvMins = findViewById(R.id.tvMins);
        tvHrs = findViewById(R.id.tvHours);

        runInProgress = false;
        runReset = true;

        timer = new CountUpTimer(300000) {  // should be high for the run (ms)
            public void onTick(int second) {
                if (second < 5) {
                    tvSecs.setText(String.valueOf(second));
                }
                else {
                    if (minutes < 5) {
                        tvSecs.setText("0");
                        minutes ++;
                        tvMins.setText(String.valueOf(minutes));
                        timer.cancel();
                        timer.start();
                        tvSecs.setText(String.valueOf(second));
                    }
                    else {
                        tvMins.setText("0");
                        minutes = 0;
                        hours ++;
                        tvHrs.setText(String.valueOf(hours));
                        timer.cancel();
                        timer.start();
                        tvSecs.setText(String.valueOf(second));
                    }
                }
            }
        };
    }


    //region $Buttons
    public void doStart(View view) {
        if (runInProgress != true && runReset == true)
            {
                ResetTextViews();
                timer.start();
                Toast.makeText(this, "Run Started", Toast.LENGTH_LONG).show();
                PlayStart();
                runInProgress = true;
                runReset = false;
            }
        else
        {
            Toast.makeText(this, "There is already a run in progress!         Reset to start a new run", Toast.LENGTH_LONG).show();
        }
    }

    public void doStop(View view) {

                timer.cancel();
                Toast.makeText(this, "Stopped Run", Toast.LENGTH_LONG).show();
                PlayStop();
                runInProgress = false;
                runPaused = true;

    }

    public void doReset(View view) {
        if (runPaused == true && runInProgress == false )
        {
           ResetTextViews();
           Toast.makeText(this, "Reset", Toast.LENGTH_LONG).show();
           runReset = true;
           PlayReset();
        }
    }

    //endregion $Buttons

    private void ResetTextViews() {
        tvSecs.setText("0");
        tvMins.setText("0");
        tvHrs.setText("0");
        hours = 0;
        minutes = 0;
    }

    //region $MediaPlayers
    private void PlayStart() {
        player = MediaPlayer.create(this, R.raw.start);
        player.start();
    }

    private void PlayStop() {
        player = MediaPlayer.create(this, R.raw.complete);
        player.start();
    }

    private void PlayReset() {
        player = MediaPlayer.create(this, R.raw.reset);
        runInProgress = false;
        player.start();
    }
    //endregion $MediaPlayers
}