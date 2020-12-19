package com.example.war.objects;

public class Player {
    private int score =0;
    private String name = "";
    private int id = 0;
    private Card currentCard;
    private boolean winner = false;

    public Player() { }

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Player(String name, int id, int score) {
        this(name, id);
        this.score = score;
    }

    public void setScore() {
       score++;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public void setWinner() {
        winner = true;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public boolean isWinner() {
        return winner;
    }

    public int getId(){
        return id;
    }
}
