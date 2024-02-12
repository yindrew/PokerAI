package com.example.demo.model;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the 5 cards on the board
 */
public class Board implements Cloneable{
    private ArrayList<Card> boardCards;
    private int size;

    //constructor
    public Board(Card[] cards) {
        boardCards = new ArrayList<Card>();
        size = 0; 
        if (cards != null) {
            Collections.addAll(boardCards, cards);
            size = boardCards.size();
        }
    }

    public void clearBoard() {
        boardCards.clear();
        size = 0;
    }

    public void addCard(Card newCard) {
        boardCards.add(newCard);
        size += 1;
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

    public int getSize() {
        return size;
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
