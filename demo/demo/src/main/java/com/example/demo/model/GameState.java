package com.example.demo.model;

public class GameState {
    private Board board;
    private Hand hand;
    private GameLog log;
    private int[] legalMoves;

    public GameState(Board b, Hand h, GameLog l, int[] legal) {
        board = b;
        hand = h;
        log = l;
        legalMoves = legal;
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

    public int[] getLegalMoves() {
        return legalMoves;
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

    public void setLegalMoves(int[] moves) {
        legalMoves = moves;
    }

    public String toString() {
        return "The board is " + board.toString() + " The hand is " + hand.toString() + " the log is " + log.toString();
    }
    
    
}
