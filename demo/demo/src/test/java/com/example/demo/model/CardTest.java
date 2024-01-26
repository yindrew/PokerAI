package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    static Card card7s;
    static Card card7sSame;
    static Card cardKd;
    
    @BeforeEach
    void beforeEach() {
        card7s = new Card("7s");
        card7sSame = new Card("7s");
        cardKd = new Card("Kd");
    }

    @Test
    void isSameSuit() {
        assertEquals(true, card7s.isSameSuit(card7sSame));
        assertEquals(false, card7s.isSameSuit(cardKd));

    }

    @Test
    void isSameCard() {
        Card card7d = new Card("7d");
        Card card8s = new Card("8s");

        assertEquals(true, card7s.isSameCard(card7sSame));
        assertEquals(false, card7s.isSameCard(card7d));
        assertEquals(false, card7s.isSameCard(card8s));

    }

    @Test
    void getCardValueDifference() {
        assertEquals(0, card7s.getCardValueDifference(card7sSame));
        assertEquals(-6, card7s.getCardValueDifference(cardKd));
    }

    @Test
    void getCardVal() {
        assertEquals(7, card7s.getCardVal());
        assertEquals(13, cardKd.getCardVal());
        assertEquals(11, new Card("Jh").getCardVal());

    }

    @Test
    void getValue() {
        assertEquals('7', card7s.getValue());
        assertEquals('K', cardKd.getValue());
    }

    @Test
    void getSuit() {
        assertEquals('s', card7s.getSuit());
        assertEquals('d', cardKd.getSuit());
    }


}