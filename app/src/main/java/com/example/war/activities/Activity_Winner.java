package com.example.war.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.war.R;
import com.example.war.objects.Record;
import com.example.war.objects.TopTen;
import com.example.war.utils.MySP;
import com.example.war.utils.MySignal;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class Activity_Winner extends Activity_Base {

    public static final String RECORD = "RECORD";
    private Timer carousalTimer;
    private Record record;

    private ImageView winner_IMG_trophy;
    private TextView winner_LBL_winner;
    private TextView winner_LBL_player;
    private TextView winner_LBL_score;
    private ImageView winner_IMG_winner;
    private Button winner_BTN_topTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__winner);
        findViews();
        displayWinner();
        updateTopTen();
        initView();
    }

    public void findViews() {
        winner_IMG_trophy = findViewById(R.id.winner_IMG_trophy);
        winner_LBL_winner = findViewById(R.id.winner_LBL_winner);
        winner_LBL_player = findViewById(R.id.winner_LBL_player);
        winner_LBL_score = findViewById(R.id.winner_LBL_score);
        winner_IMG_winner = findViewById(R.id.winner_IMG_winner);
        winner_BTN_topTen = findViewById(R.id.winner_BTN_topTen);
    }

    private void initView(){
        winner_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopTen(Activity_Winner.this);
            }
        });
    }

    private void openTopTen(Activity activity) {
        stopPlaying();
        carousalTimer.cancel();
        Intent myIntent = new Intent(activity, Activity_TopTen.class);
        startActivity(myIntent);
        finish();
    }

    private void displayWinner() {
        record = (Record) getIntent().getSerializableExtra("RECORD");
        Log.d("TOPTEN", "record: " + record.getLon() + " " + record.getLat() + " " + record.getName());
        winner_LBL_score.setText("Score:" + record.getScore());
        winner_LBL_player.setText(record.getName());
        winner_IMG_winner.setImageResource(getResources().getIdentifier("drawable/img_player" + record.getPlayerId(), "drawable", getPackageName()));
    }

    private void startTimer() {
        carousalTimer = new Timer();
        carousalTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playSound(Activity_Winner.this, R.raw.snd_game_mystery);
                    }
                });
            }
        }, 0, 128000); // delay
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
        finish();
    }

    private void updateTopTen() {
        //Read from shared preferences top ten
        String stringTopScores = MySP.getInstance().getString(MySP.KEYS.KEY_TOP_TEN_SCORES, null);
        TopTen topScores;

        //If top ten list is empty - make new top ten
        //If there is a list - get list from json
        if (stringTopScores == null) {
            topScores = new TopTen();
        } else {
            topScores = new Gson().fromJson(stringTopScores, TopTen.class);
        }

        //check if record makes top ten
        if (topScores.addRecord(record)) {
            MySignal.getInstance().toast("You're added to top ten!");
            MySignal.getInstance().vibrate();
            String ttJson = new Gson().toJson(topScores);
            MySP.getInstance().putString(MySP.KEYS.KEY_TOP_TEN_SCORES, ttJson);
        }
    }

}