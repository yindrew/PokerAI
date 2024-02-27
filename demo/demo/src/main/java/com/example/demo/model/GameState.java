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


    public void setBoard(Board b) {
        board = b;
    }

    public void setHand(Hand h) {
        hand = h;
    }

    public void setLog(GameLog l) {
        log = l;
    }

    public String toString() {
        return "The board is " + board.toString() + " The hand is " + hand.toString() + " the log is " + log.toString();
    }
    
    
}
