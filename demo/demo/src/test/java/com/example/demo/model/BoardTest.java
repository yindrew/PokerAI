package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

class BoardTest {

    Board board;
    Card card7s;
    Card cardKd;
    ArrayList<Card> cardsArrayList;


    @BeforeEach
    void beforeEach() {
        card7s = new Card("7s");
        cardKd = new Card("Kd");
        cardsArrayList = new ArrayList<Card>();
        cardsArrayList.add(card7s);
        Card[] arr = new Card[5];
        arr[0] = card7s;
        arr[1] = cardKd;
        board = new Board(arr);

    }


    @Test
    void testEmptyDeck() {
        board = new Board(null);
    }

    @Test
    void addCard() {
        assertTrue(board.getBoardCards().get(0).equals(card7s));
        board.addCard(cardKd);
        assertTrue(board.getBoardCards().get(1).equals(cardKd));
    }

    @Test
    void testClone() {
        // don't need to test this as we are using super.clone()
    }

    @Test
    void getBoardCards() {
        //don't need to test this as it is tested at addCard() test.
    }

    @Test
    void testToString() {
        System.out.println(board.getBoardCards().toString());
    }
}
