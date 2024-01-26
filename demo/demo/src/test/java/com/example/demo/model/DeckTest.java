package com.example.demo.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    static Deck deck;
    @BeforeEach
    void beforeEach() throws Exception {
        deck = new Deck();
    }

    @Test
    void newDeck() throws Exception {
        deck.newDeck();

        assertEquals(52, deck.getDeckSize());

    }

    @Test
    void shuffle() {
        //no need to test as it uses Collections.shuffle()
    }

    @Test
    void removeSpecificCard() {
        assertTrue(deck.removeSpecificCard(new Card("7s")));
        assertFalse(deck.removeSpecificCard(new Card("7s")));
    }

    @Test
    void dealHand() {
        Card[] dealtHand = deck.dealHand();
        assertEquals(50, deck.getDeckSize());
        assertFalse(dealtHand[0].isSameCard(dealtHand[1]));
    }

    @Test
    void getOneCard() {
        Card card1 = deck.getCard();
        assertEquals(51, deck.getDeckSize());
        Card card2 = deck.getCard();
        assertEquals(50, deck.getDeckSize());
        assertFalse(card1.isSameCard(card2));

    }

    @Test
    void getDeckSize() {
        deck.getCard();
        assertEquals(51, deck.getDeckSize());
        deck.getCard();
        assertEquals(50, deck.getDeckSize());
    }

    @Test
    void getDeck() {
        assertEquals(52, deck.getDeck().size());
        deck.getCard();
        assertEquals(51, deck.getDeck().size());
    }

    @Test
    void testToString() {
        String deckString = deck.toString();
        assertEquals(52, deckString.split("\\|").length);
        deck.getCard();
        deckString = deck.toString();
        assertEquals(51, deckString.split("\\|").length);
    }
}