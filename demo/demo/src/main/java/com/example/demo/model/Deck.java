package com.example.demo.model;

import java.util.*;

public class Deck {
    private ArrayList<Card> deck;
    static char[] suits = {'c', 'd', 'h', 's'};
    static char[] values = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};

    public Deck() {
        deck = new ArrayList<Card>(52);
        this.newDeck();
    }

    public void newDeck() {
        deck.clear();

        int suitCount = 4;
        int numberCount = 13;

        for (int x = 0; x < suitCount; x++) {
            for (int y = 0; y < numberCount; y++) {
                deck.add(new Card(suits[x], values[y]));
            }
        }

        this.shuffle();

    }
    
    public void shuffle() {
        Collections.shuffle(deck);
    }

    public boolean removeSpecificCard(Card newCard) {
        for (int x = 0; x < deck.size(); x++) {
            if (deck.get(x).equals(newCard)){
                deck.remove(x);
                return true;
            }
        }
        return false;
    }

    public Hand dealHand() {
        Card[] hand = new Card[2];
        hand[0] = getCard();
        hand[1] = getCard();

        return new Hand(hand);
    }

    public Card deal() {
        return getCard();
    }

    public Card getCard() {
        return deck.remove(deck.size() - 1);
    }

    public int getDeckSize() {
        return deck.size();
    }

    /**
     * getter
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * toString
     */
    public String toString() {
        StringBuilder infoString = new StringBuilder();
        for (Card c : deck) {
            infoString.append(c).append("|");
        }
        return infoString.toString();
    }

}
