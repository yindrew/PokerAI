package com.example.demo.model;

import java.util.Arrays;

public class GameState extends State {

    private int[] legalMoves;

    public GameState(Board b, Hand h, GameLog l, int[] legal) {
        super(b, h, l);
        legalMoves = legal;
    }

    public int[] getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(int[] moves) {
        legalMoves = moves;
    }

    public String toString() {
        return super.toString() + " Legal actions are " + Arrays.toString(legalMoves);
    }

}
