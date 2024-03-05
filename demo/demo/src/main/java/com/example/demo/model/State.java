package com.example.demo.model;

public class State {
    private Board board;
    private Hand hand;
    private GameLog gameLog;
    private int position;

    public State(Board b, Hand h, GameLog l, int pos) {
        board = b;
        hand = h;
        gameLog = l;
        position = pos;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        position = pos;
    }

    public Board getBoard() {
        return board;
    }

    public Hand getHand() {
        return hand;
    }

    public GameLog getGameLog() {
        return gameLog;
    }



    public void setBoard(Board b) {
        board = b;
    }

    public void setHand(Hand h) {
        hand = h;
    }

    public void setLog(GameLog l) {
        gameLog = l;
    }

    public String toString() {
        return "The board is " + board.toString() + " The hand is " + hand.toString() + " the log is " + gameLog.toString();
    }
    
    
}
