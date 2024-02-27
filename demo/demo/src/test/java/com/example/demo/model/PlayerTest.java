package com.example.demo.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    static Player player;
    @BeforeEach
    void beforeEach() throws Exception {
        player = new Player("Andrew");
    }

    @Test
    void testGetAction() throws Exception {
        // initializing the log 
        Log l1 = new Log(Action.BET_MEDIUM, 3);
        Log l2 = new Log(Action.BET_MEDIUM, 10);
        Log l3 = new Log(Action.CALL, 10);
        Log l4 = new Log(Action.CHECK, 0);
        Log l5 = new Log(Action.BET_SMALL, 15);
        Log l6 = new Log(Action.BET_ALL_IN, 90);

        GameLog log = new GameLog();
        log.addLog(l1);
        log.addLog(l2);
        log.addLog(l3);
        log.addLog(l4);
        log.addLog(l5);
        log.addLog(l6);

        // initializing the board and hand
        Card deckCard1 = new Card("9h");
        Card deckCard2 = new Card("Td");
        Card deckCard3 = new Card("Ks");
        Card handCard1 = new Card("Ad");
        Card handCard2 = new Card("As");
        Board board = new Board(new Card[]{deckCard1, deckCard2, deckCard3});
        Hand hand = new Hand(new Card[] {handCard1, handCard2});

        player.setHand(hand);

       Log action = player.getAction(log, board);
       System.out.println(action);


    }

}