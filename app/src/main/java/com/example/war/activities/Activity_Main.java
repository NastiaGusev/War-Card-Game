package com.example.war.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.war.R;

import java.util.Timer;
import java.util.TimerTask;

public class Activity_Main extends Activity_Base {

    private Timer carousalTimer;
    private Button main_BTN_start;
    private Button main_BTN_topTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initView();
    }

    private void findViews() {
        main_BTN_start = findViewById(R.id.main_BTN_start);
        main_BTN_topTen = findViewById(R.id.main_BTN_topTen);
    }

    private void initView(){
        main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewGame(Activity_Main.this);
            }
        });

        main_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopTen(Activity_Main.this);
            }
        });
    }

    private void startTimer() {
        carousalTimer = new Timer(); // At this line a new Thread will be created
        carousalTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playSound(Activity_Main.this,R.raw.snd_opening);
                    }
                });
            }
        }, 0, 27000); // delay
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlaying();
        carousalTimer.cancel();
    }

    private void openNewGame(Activity activity) {
        stopPlaying();
        carousalTimer.cancel();
        Intent myIntent = new Intent(activity, Activity_Game.class);
        startActivity(myIntent);
    }

    private void openTopTen(Activity activity) {
        stopPlaying();
        carousalTimer.cancel();
        Intent myIntent = new Intent(activity, Activity_TopTen.class);
        startActivity(myIntent);
    }

}


