package com.example.demo.model;

public class GameState {
    private Board board;
    private Hand hand;
    private GameLog log;

    public GameState(Board b, Hand h, GameLog l) {
        board = b;
        hand = h;
        log = l;
    }


    public Board getBoard() {
        return board;
    }

    public Hand getHand() {
        return hand;
    }

    public GameLog getLog() {
        return log;
    }
    
    
}
