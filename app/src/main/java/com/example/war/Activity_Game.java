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

public class Activity_Game extends AppCompatActivity {
    private TextView game_LBL_title;
    private ImageButton game_IBTN_play;
    private ImageView game_IMG_deck1;
    private ImageView game_IMG_deck2;
    private TextView game_LBL_score1;
    private TextView game_LBL_score2;
    int check = 0;
    private Cards deck;
    private int score1 = 0;
    private int score2 = 0;
    private int bestScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__game);

        game_LBL_title = findViewById(R.id.game_LBL_title);
        game_IBTN_play = findViewById(R.id.game_IBTN_play);
        game_IMG_deck1 = findViewById(R.id.game_IMG_deck1);
        game_IMG_deck2 = findViewById(R.id.game_IMG_deck2);
        game_LBL_score1 = findViewById(R.id.game_LBL_score1);
        game_LBL_score2 = findViewById(R.id.game_IMG_score2);

        deck = new Cards();
        initDeck(deck);

        game_IBTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game_LBL_score1.setTextColor(getResources().getColor(R.color.black, getResources().newTheme()));
                game_LBL_score2.setTextColor(getResources().getColor(R.color.black, getResources().newTheme()));

                if (check == 0) {
                    game_LBL_title.setText("");
                }
                if (check != (Cards.DECK_SIZE / 2)) {
                    oneMove(deck);
                } else {
                    openWinnerActivity(Activity_Game.this);
                    finish();
                }
            }
        });
    }

    private void openWinnerActivity(Activity activity) {
        Intent myIntent = new Intent(activity, Activity_Winner.class);
        myIntent.putExtra(Activity_Winner.WINNER, getWinner());
        myIntent.putExtra(Activity_Winner.SCORE, bestScore);
        startActivity(myIntent);
        finish();
    }

    public int getWinner() {
        if (score1 > score2) {
            bestScore = score1;
            return 1;
        } else if (score2 > score1) {
            bestScore = score2;
            return 2;
        }
        bestScore = score1;
        return 0;
    }

    private void initDeck(Cards deck) {
        int value = 2;
        for (int i = 1; i < Cards.DECK_SIZE + 1; i++) {
            deck.addCard(deck, getIMGName(i), value);
            if ((i % 4) == 0) {
                value++;
            }
        }
    }

    private int getIMGName(int index) {
        String path = "drawable/" + "card_" + index;
        return getResources().getIdentifier(path, "drawable", getPackageName());
    }

    private void oneMove(Cards deck) {
        int key1 = getIMGName(deck.getRandomCard(deck));
        int key2 = getIMGName(deck.getRandomCard(deck));
        game_IMG_deck1.setImageResource(key1);
        game_IMG_deck2.setImageResource(key2);
        setScore(deck, key1, key2);
        check++;
    }

    private void setScore(Cards deck, int key1, int key2) {
        int res = deck.compareCards(deck, key1, key2);
        if (res > 0) {
            score1++;
            game_LBL_score1.setText(" " + score1);
            game_LBL_score1.setTextColor(getResources().getColor(R.color.winner_txt2, getResources().newTheme()));
        } else if (res < 0) {
            score2++;
            game_LBL_score2.setText(" " + score2);
            game_LBL_score2.setTextColor(getResources().getColor(R.color.winner_txt2, getResources().newTheme()));
        }
    }


}

