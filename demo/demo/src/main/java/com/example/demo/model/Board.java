package com.example.demo.model;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the 5 cards on the board
 */
public class Board implements Cloneable{
    private ArrayList<Card> boardCards;

    //constructor
    public Board(Card[] cards) {
        boardCards = new ArrayList<Card>();
        if (cards != null) {
            Collections.addAll(boardCards, cards);
        }
    }


    public void addCard(Card newCard) {
        boardCards.add(newCard);
    }

    public void addCards(Card[] cards) {
        Collections.addAll(boardCards, cards);
    }

    /**
     * getter
     */
    public ArrayList<Card> getBoardCards() {
        return boardCards;
    }

    /**
     * toString
     */
    public String toString() {
        StringBuilder infoString = new StringBuilder();
        for (int x = 0; x < boardCards.size(); x ++) {
            infoString.append(boardCards.get(x).toString()).append(" ");
        }
        return infoString.toString();
    }

}
