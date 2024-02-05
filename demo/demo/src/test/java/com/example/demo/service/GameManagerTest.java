package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


import org.junit.jupiter.api.Test;

import com.example.demo.model.Player;

public class GameManagerTest {
    GameManager gameManager;


    @Test
    void testDealHoleCards() {
        gameManager = new GameManager();
        Player[] players = gameManager.getPlayers();
        assertNull(players[0].getHand());
        assertNull(players[1].getHand());
        gameManager.dealHoleCards();
        assertNotNull(players[0].getHand());
        assertNotNull(players[1].getHand());
    }
    
    @Test
    void testSwitchingPlayer() {
        gameManager = new GameManager();
        assertEquals(gameManager.getCurrentPlayer(), gameManager.getPlayers()[0]);
        gameManager.nextTurn();
        assertEquals(gameManager.getCurrentPlayer(), gameManager.getPlayers()[1]);
        gameManager.nextTurn();
        assertEquals(gameManager.getCurrentPlayer(), gameManager.getPlayers()[0]);
    }


    @Test
    void testHandlePreflop() throws Exception {
        gameManager = new GameManager();
        gameManager.setUpGame();
        gameManager.advanceGame();
        
    }
}
