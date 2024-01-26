package com.example.demo.model;

public class Hand {
    private Card[] hand = new Card[2];

	public Hand(String cards) {
		hand[0] = new Card(cards.substring(0, 2));
		hand[1] = new Card(cards.substring(2));
	}

    public Hand(Card[] cards) {
		hand = cards;
	}

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] h) {
        hand = h;
    }
    
}
