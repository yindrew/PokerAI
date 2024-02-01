package com.example.demo.service;

import com.example.demo.model.Log;
import com.example.demo.model.Board;
import com.example.demo.model.Deck;
import com.example.demo.model.GameLog;
import com.example.demo.model.Player;

enum GameState {
    PREFLOP,
    FLOP,
    TURN,
    RIVER,
    SHOWDOWN,
    GAMEOVER
}


public class GameManager {
    private Player player1;
    private Player player2;
    private Player[] players;
    private GameLog gameLog;
    private int potSize;
    private Deck deck;
    private int currentPlayerIndex;
    private Board board;
    private GameState currentState;
    private int flopx = 0;
    
    

    public GameManager() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        gameLog = new GameLog();
        potSize = 0;
        deck = new Deck();
        board = new Board(null);
        players = new Player[]{player1, player2};
        currentPlayerIndex = 0;
        currentState = GameState.PREFLOP;

    }

    public void setUpPot() {
        changeStack(-1, player1);
        changeStack(-1, player1);
        potSize = 2;
    }

    public void changeStack(int value, Player player){
        int currentStack = player.getStack();
        player.setStack(currentStack + value);
    }

    public void dealHoleCards() {
        player1.setHand(deck.dealHand());
        player2.setHand(deck.dealHand());
    }
    
    public void deal() {
        board.addCard(deck.getCard());
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }



    public void setUpGame() {
        setUpPot();
        deck.newDeck();
        dealHoleCards();
        board.clearBoard();
        gameLog.clearLog();
        currentState = GameState.PREFLOP;

    }

    // Method to advance the game based on player actions
    public void advanceGame() {
        switch (currentState) {
            case PREFLOP:
                handlePreFlop();
                break;
            case FLOP:
                handleFlop();
                break;
            case TURN:
                handleTurn();
                break;
            case RIVER:
                handleRiver();
                break;
            case SHOWDOWN:
                determineWinner();
                currentState = GameState.GAMEOVER;
                break;
            case GAMEOVER:
                System.out.println("Hand is over");
                break;
        }
    }

    private void handlePreFlop() {
        Log currentAction = players[currentPlayerIndex].getAction(gameLog);

        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);
                currentState = GameState.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                if (gameLog.getLogs().size() == 1){
                    currentState = GameState.PREFLOP;
                } 
                else {
                    currentState = GameState.FLOP;
                }
                break;
            case RAISE:
                handleRaise(currentAction);
                currentState = GameState.PREFLOP;
                break;
            case CHECK:
                handleCheck(currentAction);
                currentState = GameState.FLOP;
                break;
            default:
                break;
        }
        advanceGame();
    }
    
    private void handleRaise(Log action) {
        gameLog.addLog(action);
        changeStack(-action.getSize(), players[currentPlayerIndex]);
        nextTurn();
    }

    private void handleCheck(Log action) {
        gameLog.addLog(action);
        changeStack(-action.getSize(), players[currentPlayerIndex]);
        nextTurn();

    }

    private void handleCall(Log action) {
        gameLog.addLog(action);
        changeStack(-action.getSize(), players[currentPlayerIndex]);
        nextTurn();

    }

    private void handleFold(Log action) {
        gameLog.addLog(action);
        changeStack(-action.getSize(), players[currentPlayerIndex]);
    }

    
    

    private void handleFlop() {
        Log currentAction = players[currentPlayerIndex].getAction(gameLog);

        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);
                currentState = GameState.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                currentState = GameState.TURN;
                break;
            case RAISE:
                handleRaise(currentAction);
                currentState = GameState.FLOP;
                break;
            case CHECK:
                handleCheck(currentAction);
                flopx += 1;
                if (flopx == 2) {
                    currentState = GameState.TURN;
                }
                else{
                    currentState = GameState.FLOP;
                }
                break;
            default:
                break;
        }
        advanceGame();
        // Handle Flop actions
    }

    // Similar methods for Turn and River
    private void handleTurn() { 
        throw new UnsupportedOperationException("Unimplemented method 'handleFold'");

        /* ... */ 
    }

    private void handleRiver() { 
        throw new UnsupportedOperationException("Unimplemented method 'handleFold'");

        /* ... */ 

    }

    private void determineWinner() {
        // Determine the winner and handle showdown logic
    }



    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getPotSize() {
        return potSize;
    }


    public static void main(String[] args) {
        GameManager GM = new GameManager();
        GM.setUpGame();
        GM.advanceGame();


     }

}
