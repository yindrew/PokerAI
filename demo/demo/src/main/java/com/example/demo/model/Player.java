package com.example.demo.model;

public class Player {
    private Hand hand;
    private GameLog gameLog;
    private int stack;
    private String name;
    

    public Player() {

    }

    public Player(String name) {
        this.name = name;
    }


    public Log getAction(String action) {
        int size;
        if (action.equals("raise")) {
            size = 3;
        }
        else if (action.equals("fold")){
            size = 0;
        }
        else {
            size = 1;
        }
        return new Log(action, size);
    }

    public Log getAction(GameLog log) {

        // change to call the python service to get a action
        return new Log("r", 5);
    }

    public void setHand(Hand h) {
        hand = h;
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

    public void setStack(int s) {
        stack = s;
    }

    public int getStack() {
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
