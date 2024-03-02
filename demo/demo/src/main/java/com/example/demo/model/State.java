package com.example.demo.model;

public class State {
    private Board board;
    private Hand hand;
    private GameLog gameLog;

    public State(Board b, Hand h, GameLog l) {
        board = b;
        hand = h;
        gameLog = l;
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
