package com.example.demo.model;
import java.util.ArrayList;
import java.util.List;

public class GameLog {
    private List<Log> gameLog = new ArrayList<>();
    private double potSize = 0;

    public void addLog(Log log) {
        gameLog.add(log);
        potSize += log.getSize();
    }

    public List<Log> getLogs() {
        return gameLog;
    }

    public void clearLog() {
        gameLog.clear();
        potSize = 0;
    }

    public int getSize() {
        return gameLog.size();
    }

    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        for (Log log : gameLog){
            sBuilder.append(log.toString() + " ");
        }
        return sBuilder.toString();
    }

    public double getPotSize() {
        return potSize;
    }

}

