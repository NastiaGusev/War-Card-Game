package com.example.war.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.war.R;

public class Activity_Winner extends Activity_Base {

    public static final String WINNER = "WINNER";
    public static final String SCORE = "SCORE";

    private ImageView winner_IMG_trophy;
    private TextView winner_LBL_winner;
    private TextView winner_LBL_player;
    private TextView winner_LBL_score;
    private ImageView winner_IMG_winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__winner);

        winner_IMG_trophy = findViewById(R.id.winner_IMG_trophy);
        winner_LBL_winner = findViewById(R.id.winner_LBL_winner);
        winner_LBL_player = findViewById(R.id.winner_LBL_player);
        winner_LBL_score = findViewById(R.id.winner_LBL_score);
        winner_IMG_winner = findViewById(R.id.winner_IMG_winner);
        displayWinner();
    }

    private void displayWinner() {
        int winner = getIntent().getIntExtra(WINNER, -1);
        int score = getIntent().getIntExtra(SCORE, -1);
        winner_LBL_score.setText("Score:" + score);

        if(winner == 1){
            winner_IMG_winner.setImageResource(getResources().getIdentifier("drawable/img_queen", "drawable", getPackageName()));
        } else if (winner == 2) {
            winner_LBL_player.setText("Player 2");
            winner_IMG_winner.setImageResource(getResources().getIdentifier("drawable/img_king1", "drawable", getPackageName()));
        }else{
            winner_LBL_player.setText("");
            winner_LBL_winner.setText("Draw!");
            winner_IMG_trophy.setImageResource(getResources().getIdentifier("drawable/img_conflict", "drawable", getPackageName()));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}