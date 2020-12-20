package com.example.war.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.war.R;
import com.example.war.objects.Player;
import com.example.war.objects.Record;
import com.example.war.objects.WarGame;
import com.example.war.utils.MyDialog;
import com.example.war.utils.MyLocation;
import com.example.war.utils.MySignal;

import java.util.Timer;
import java.util.TimerTask;

public class Activity_Game extends Activity_Base implements MyDialog.MyDialogListener {
    private ImageButton game_IBTN_play;
    private ImageView game_IMG_deck1;
    private ImageView game_IMG_deck2;
    private TextView game_LBL_score1;
    private TextView game_LBL_score2;
    private ProgressBar game_progressbar;
    private TextView game_TXT_player1;
    private TextView game_TXT_player2;
    private WarGame game;
    private Timer gameTimer;
    private MyLocation myLocation;
    private boolean locationAccepted = true;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game);
        isDoublePressToClose = true;
        getLocation();
        findViews();
        openDialog();
        initView();
    }

    public void findViews() {
        game_IBTN_play = findViewById(R.id.game_IBTN_play);
        game_IMG_deck1 = findViewById(R.id.game_IMG_deck1);
        game_IMG_deck2 = findViewById(R.id.game_IMG_deck2);
        game_LBL_score1 = findViewById(R.id.game_LBL_score1);
        game_LBL_score2 = findViewById(R.id.game_IMG_score2);
        game_progressbar = findViewById(R.id.game_progressbar);
        game_TXT_player1 = findViewById(R.id.game_TXT_player1);
        game_TXT_player2 = findViewById(R.id.game_TXT_player2);
    }

    private void initView() {
        game_IBTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySignal.getInstance().vibrate();
                playSound(Activity_Game.this, R.raw.snd_shuffle);
                MySignal.getInstance().toast("Start Game!");
                startTimer();
                game_IBTN_play.setClickable(false);
            }
        });
    }

    public void getLocation() {
        myLocation = new MyLocation(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == myLocation.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myLocation.getLocation();
            }
        } else {
            locationAccepted = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (myLocation.getLon() == 0 || myLocation.getLat() == 0)
            myLocation.getLocation();
        if (!game_IBTN_play.isClickable()) {
            startTimer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        myLocation.stopLocationUpdates();
    }

    private void openDialog() {
        dialog = new MyDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String player1, String player2) {
        String name1 = player1;
        String name2 = player2;
        if (player1.length() == 0)
            name1 = "Player1";
        if (player2.length() == 0)
            name2 = "Player2";

        game = new WarGame(name1, name2);
        game_TXT_player1.setText(name1);
        game_TXT_player2.setText(name2);
    }

    private void setLayout() {
        playSound(Activity_Game.this, R.raw.snd_dealing_card);
        playSound(Activity_Game.this, R.raw.snd_dealing_card);
        game_IMG_deck1.setImageResource(getResources().getIdentifier(game.getPlayer1().getCurrentCard().getImgName(), "drawable", getPackageName()));
        game_IMG_deck2.setImageResource(getResources().getIdentifier(game.getPlayer2().getCurrentCard().getImgName(), "drawable", getPackageName()));
        game_LBL_score1.setText(" " + game.getPlayer1().getScore());
        game_LBL_score2.setText(" " + game.getPlayer2().getScore());
        if (game.getRoundWinner() == 1) {
            game_LBL_score1.setTextColor(getResources().getColor(R.color.winner_txt2, getResources().newTheme()));
        } else if (game.getRoundWinner() == 2) {
            game_LBL_score2.setTextColor(getResources().getColor(R.color.winner_txt2, getResources().newTheme()));
        }
    }

    private void move() {
        game_LBL_score1.setTextColor(getResources().getColor(R.color.black, getResources().newTheme()));
        game_LBL_score2.setTextColor(getResources().getColor(R.color.black, getResources().newTheme()));
        if (game.oneMove() > 0) {
            setLayout();
            game_progressbar.setProgress(game.getCheck());
        } else {
            gameTimer.cancel();
            game_progressbar.setProgress(game_progressbar.getMax());
            MySignal.getInstance().toast("GAME OVER");
            openWinnerActivity(Activity_Game.this);
        }
    }

    private void startTimer() {
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        move();
                    }
                });
            }
        }, 0, 1000); // delay
    }

    private Record makeRecord(){
        Player winner = game.getWinner();
        double lon = 0, lat = 0;
        if (locationAccepted) {
            lon = myLocation.getLon();
            lat = myLocation.getLat();
        }
        return new Record(winner.getName(), winner.getScore(), lat, lon, winner.getId());
    }

    private void openWinnerActivity(Activity activity) {
        Intent myIntent = new Intent(activity, Activity_Winner.class);
        myIntent.putExtra(Activity_Winner.RECORD, makeRecord());
        startActivity(myIntent);
        finish();
    }
}
