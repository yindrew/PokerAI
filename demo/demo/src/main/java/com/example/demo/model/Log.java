package com.example.demo.model;



public class Log {
    private Action action;
    private double size;

    public Log(Action action, double size) {
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

    public double getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

