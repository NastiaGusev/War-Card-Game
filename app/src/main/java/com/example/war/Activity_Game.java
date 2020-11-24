package com.example.war;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Game extends AppCompatActivity {
    private ImageButton game_IBTN_play;
    private ImageView game_IMG_deck1;
    private ImageView game_IMG_deck2;
    private TextView game_LBL_score1;
    private TextView game_LBL_score2;
    private WarGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__game);

        game_IBTN_play = findViewById(R.id.game_IBTN_play);
        game_IMG_deck1 = findViewById(R.id.game_IMG_deck1);
        game_IMG_deck2 = findViewById(R.id.game_IMG_deck2);
        game_LBL_score1 = findViewById(R.id.game_LBL_score1);
        game_LBL_score2 = findViewById(R.id.game_IMG_score2);
        game = new WarGame();

        //For every click on the play button, activate one move function
        //and check if all the cards have been played with check counter
        game_IBTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game_LBL_score1.setTextColor(getResources().getColor(R.color.black, getResources().newTheme()));
                game_LBL_score2.setTextColor(getResources().getColor(R.color.black, getResources().newTheme()));
                if (game.check() > 0) {
                    move();
                } else {
                    Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_SHORT).show();
                    openWinnerActivity(Activity_Game.this);
                }
            }
        });
    }

    private void openWinnerActivity(Activity activity) {
        Intent myIntent = new Intent(activity, Activity_Winner.class);
        myIntent.putExtra(Activity_Winner.WINNER, game.getWinner());
        myIntent.putExtra(Activity_Winner.SCORE, game.getWinnerScore());
        startActivity(myIntent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void move(){
        int roundWinner = game.oneMove();
        if (roundWinner < -1){
            return;
        }
        game_IMG_deck1.setImageResource(getResources().getIdentifier(game.getCurrentCard(1).getImgName(), "drawable", getPackageName()));
        game_IMG_deck2.setImageResource(getResources().getIdentifier(game.getCurrentCard(2).getImgName(), "drawable", getPackageName()));
        game_LBL_score1.setText(" " + game.getScore(1));
        game_LBL_score2.setText(" " + game.getScore(2));
        if (roundWinner == 1){
            game_LBL_score1.setTextColor(getResources().getColor(R.color.winner_txt2, getResources().newTheme()));
        } else if (roundWinner ==2){
            game_LBL_score2.setTextColor(getResources().getColor(R.color.winner_txt2, getResources().newTheme()));
        }

    }
}
