package com.example.demo.service;

import com.example.demo.model.Log;
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
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
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
        // deal hole cards
        dealHoleCards();

        // first orbit

        // first decision
        Log log1 = getCurrentPlayer().getAction(gameLog);
        gameLog.addLog(log1);
        // handle the first decision
        if (log1.getAction().equals("fold")){
            return;
        }
        else if (log1.getAction().equals("raise")){
            //handle player 2s action
        }




    }



    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }

}
