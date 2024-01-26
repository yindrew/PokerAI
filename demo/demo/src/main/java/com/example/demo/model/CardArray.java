package com.example.demo.model;
import java.util.ArrayList;

public class CardArray {

    private ArrayList<Card> cardArray;

    public CardArray() {
        cardArray = new ArrayList<Card>();
    }

    public ArrayList<Card> getCardArray() {

        return cardArray;
    }

    public void setCardArray(ArrayList<Card> cardArray) {
        this.cardArray = cardArray;
    }
}
