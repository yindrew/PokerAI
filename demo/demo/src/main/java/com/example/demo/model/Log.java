package com.example.demo.model;



public class Log {
    private Action action;
    private int size;

    public Log(Action action, int size) {
        this.action = action;
        this.size = size;
    }

    // Getters and setters
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

