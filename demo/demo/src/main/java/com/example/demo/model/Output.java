package com.example.demo.model;

public class Output {
    private Board board;
    private Hand hand; 
    private double playerwon;
    private GameLog gameLog;

    public Output(Board b, Hand h, double a1, GameLog gl) {
        board = b;
        hand = h;
        playerwon = a1;
        gameLog = gl;

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

    public double getAmountWon() {
        return playerwon;
    }
    
}
