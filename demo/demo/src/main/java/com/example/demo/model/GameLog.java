package com.example.demo.model;
import java.util.ArrayList;
import java.util.List;

public class GameLog {
    private List<Log> gameLog = new ArrayList<>();

    public void addLog(Log log) {
        gameLog.add(log);
    }

    public List<Log> getLogs() {
        return gameLog;
    }

}

