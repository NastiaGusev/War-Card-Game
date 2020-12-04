package com.example.war.warCardGame;

import com.example.war.warCardGame.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class WarGame {

    public final static int DECK_SIZE = 52;
    private List<Card> deck;
    private ListIterator<Card> deckIterator;
    private Card card1;
    private Card card2;
    private int score1 = 0;
    private int score2 = 0;
    private int roundWinner;
    private int winnerScore = 0;
    private int check = 0;
    private boolean gameOver = false;

    public WarGame() {
        initDeck();
        Collections.shuffle(deck);
        deckIterator = deck.listIterator();
    }

    private void initDeck() {
        deck = new ArrayList<Card>();
        int value = 2;
        String path;
        for (int i = 1; i < DECK_SIZE + 1; i++) {
            path = "drawable/img_card_" + i;
            deck.add(new Card(path, value));
            if ((i % 4) == 0) {
                value++;
            }
        }
    }

    private Card getNextCard() {
        if (deckIterator.hasNext()) {
            return deckIterator.next();
        }else {
            return null;
        }
    }

    //Set the score for one round - and return the winner of the round
    private int setScore(Card card1, Card card2) {
        int res = card1.compareCards(card2);
        if (res > 0) {
            score1++;
            return 1;
        } else if (res < 0) {
            score2++;
            return 2;
        }
        return 0;
    }

    //Check if all cards have been played
    private void checkGameOver(){
        check++;
        if (check == (DECK_SIZE/2) ){
            gameOver = true;
        }
    }

    //One move- display 2 cards and check who is the winner for the round
    public int oneMove() {
        checkGameOver();
        if (gameOver) {
            return -1;
        }
        card1 = getNextCard();
        card2 = getNextCard();
        if (card1 == null || card2 == null) {
            return -1;
        }
        roundWinner = setScore(card1, card2);
        return 1;
    }

    public int getScore(int player) {
        if (player == 1){
            return score1;
        } else if (player ==2 ){
            return score2;
        } else {
            return 0;
        }
    }

    public Card getCurrentCard(int card){
        if (card == 1){
            return card1;
        }else if (card ==2 ){
            return card2;
        }else {
            return null;
        }
    }

    public int getWinner() {
        if (score1 > score2) {
            winnerScore = score1;
            return 1;
        } else if (score2 > score1) {
            winnerScore = score2;
            return 2;
        }
        winnerScore = score1;
        return 0;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public int getRoundWinner(){
        return roundWinner;
    }
}
