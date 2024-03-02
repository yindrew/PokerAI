package com.example.demo.model;

import java.util.Arrays;

import com.example.demo.controller.DecisionController;

public class Player {
    private Hand hand;
    private double stack;
    private String name;
    private DecisionController decisionController;

    public Player(String name) {
        this.name = name;
        this.stack = 100;
        decisionController = new DecisionController();

    }

    public Log getAction(GameState gameState) {
        String action = decisionController.sendGameState(gameState);
        action = action.replace(" ", "_").replace("\"", "").trim();
        return new Log(Action.valueOf(action), 0);
    }

    public void sendFinalState(FinalState gameState) {
        decisionController.sendFinalState(gameState);

    }

    public void setHand(Hand h) {
        hand = h;
    }

    public Hand getHand() {
        return hand;
    }

    public void setStack(double s) {
        stack = s;
    }

    public double getStack() {
        return stack;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName();
    }

}
