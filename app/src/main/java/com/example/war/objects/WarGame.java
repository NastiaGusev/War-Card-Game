package com.example.war.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class WarGame {

    public final static int DECK_SIZE = 52;
    private List<Card> deck;
    private ListIterator<Card> deckIterator;
    private int check = 0;
    private boolean gameOver = false;

    private Player player1;
    private Player player2;
    private int roundWinner;

    public WarGame() {
        initDeck();
        Collections.shuffle(deck);
        deckIterator = deck.listIterator();
    }

    public WarGame(String name1, String name2) {
        this();
        player1 = new Player(name1, 1);
        player2 = new Player(name2,2);
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
        } else {
            return null;
        }
    }

    //Check if all cards have been played
    private void checkGameOver(){
        check++;
        if (check == (DECK_SIZE/2) ){
            gameOver = true;
            setWinner();
        }
    }

    private void setWinner() {
        if (player1.getScore() > player2.getScore()) {
            player1.setWinner();
        } else if (player2.getScore() > player1.getScore()) {
            player2.setWinner();
        }
    }

    //Set the score for one round - and return the winner of the round
    private void setScore() {
        int res = player1.getCurrentCard().compareCards(player2.getCurrentCard());
        if (res > 0) {
            player1.setScore();
            roundWinner = 1;
        } else if (res < 0) {
            player2.setScore();
            roundWinner = 2;
        }else {
            roundWinner = 0;
        }
    }

    //One move- display 2 cards and check who is the winner for the round
    public int oneMove() {
        checkGameOver();
        if (gameOver) {
            return -1;
        }
        player1.setCurrentCard(getNextCard());
        player2.setCurrentCard(getNextCard());
        if (player1.getCurrentCard() == null || player2.getCurrentCard() == null) {
            return -1;
        }
        setScore();
        return 1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        if (player1.isWinner()){
            return player1;
        }else if (player2.isWinner()){
            return player2;
        }
       return new Player("Draw", 0, player1.getScore());
    }

    public int getRoundWinner(){
        return roundWinner;
    }

    public int getCheck(){
        return check;
    }
}
