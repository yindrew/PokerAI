package com.example.demo.model;

public class Log {
    private String action;
    private int size;

    public Log(String action, int size) {
        this.action = action;
        this.size = size;
    }

    // Getters and setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

