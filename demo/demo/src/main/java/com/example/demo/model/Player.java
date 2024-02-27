package com.example.demo.model;

import java.util.Arrays;

import com.example.demo.controller.DecisionController;

public class Player {
    private Hand hand;
    private GameLog gameLog;
    private double stack;
    private String name;
    private DecisionController decisionController;
    private GameState gameState;

    public Player() {
        gameState = new GameState(null, null, null);

    }

    public Player(String name) {
        this.name = name;
        this.stack = 100;
        decisionController = new DecisionController();
        gameState = new GameState(null, null, null);
        hand = new Hand("AdAh");

    }


    public Log getAction(String action, double size) {
        Action newAction = Action.valueOf(action);
        return new Log(newAction, size);
    }

    public Log getAction(GameLog log, Board board) {

        // update the board
        gameState.setBoard(board);
        gameState.setLog(log);

        String action = decisionController.sendGameState(gameState);
        action = action.replace(" ", "_").replace("\"", "").trim();

        return new Log(Action.valueOf(action), 0);

    }

    public void setHand(Hand h) {
        hand = h;
        gameState.setHand(h);
    } 

    public Hand getHand() {
        return hand;
    }

    public void setLog(GameLog g){
        gameLog = g;
    }

    public GameLog getLog() {
        return gameLog;
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
