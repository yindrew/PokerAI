package com.example.demo.model;

/**
 * The final state of a hand. along with the number of chips won or lost throughout the hand
 */
public class FinalState extends State {
    private double playerwon;

    public FinalState(Board b, Hand h, GameLog gl, double a1, int pos) {
        super(b, h, gl, pos);
        playerwon = a1;
    }

    public void setAmountWon(double amount) {
        playerwon = amount;
    }

    public double getAmountWon() {
        return playerwon;
    }

}
