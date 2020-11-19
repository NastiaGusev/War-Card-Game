package com.example.war;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cards {

    public final static int DECK_SIZE = 52;
    private Map<Integer, Integer> deck;
    private boolean[] used;


    public Cards() {
        deck = new <Integer, Integer>HashMap();
        used = new boolean[DECK_SIZE+1];
        Arrays.fill(this.used, false);
    }

    public void addCard(Cards cards, int key, int value) {
        cards.deck.put(key, value);
    }

    public int getCardValue(Cards cards, int key) {
        return cards.deck.get(key);
    }

    public int getRandomCard(Cards cards) {
        int key = (int) ((Math.random()) * DECK_SIZE ) + 1;
        while (cards.used[key] == true) {
            key = (int) ((Math.random()) * DECK_SIZE) + 1;
        }
        cards.used[key] = true;
        return key;
    }

    public int compareCards(Cards cards, int key1, int key2) {
        if (cards.getCardValue(cards, key1) > cards.getCardValue(cards, key2)) {
            return 1;
        }else if (cards.getCardValue(cards,key1) < cards.getCardValue(cards, key2)) {
            return -1;
        }else {
            return 0;
        }
    }


}
