package com.example.demo.service;

import com.example.demo.model.Log;;
import com.example.demo.model.Board;
import com.example.demo.model.Deck;
import com.example.demo.model.GameLog;
import com.example.demo.model.Player;

public class GameManager {
    private Player player1;
    private Player player2;
    private GameLog gameLog;
    private int potSize;
    private Deck deck;
    private Board board;
    public boolean button = true; // if button is true, player1 is IP and first to act
    private Player[] players;
    private int currentPlayerIndex;
    

    public GameManager() {
        player1 = new Player();
        player2 = new Player();
        gameLog = new GameLog();
        potSize = 0;
        deck = new Deck();
        board = new Board(null);
        players = new Player[]{player1, player2};
        currentPlayerIndex = 0;
    }

    public void dealHoleCards() {
        player1.setHand(deck.dealHand());
        player2.setHand(deck.dealHand());
    }
    
    public void deal() {
        board.addCard(deck.getCard());
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }


    public void preflop() {
        dealHoleCards();
        if (currentPlayerIndex == 0) {
            currentPlayerIndex = 1;
        }
        else {
            currentPlayerIndex = 0;
        }

        // first orbit
        Log log1 = getCurrentPlayer().getAction(gameLog);
        gameLog.addLog(log1);
        nextTurn();
        if (log1.getAction().equals("fold")){
            return;
        }

    }
}
