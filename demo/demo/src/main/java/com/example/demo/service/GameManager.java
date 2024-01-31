package com.example.demo.service;

import com.example.demo.model.Log;
import com.example.demo.model.Board;
import com.example.demo.model.Deck;
import com.example.demo.model.GameLog;
import com.example.demo.model.Player;

enum GameState {
    PreFlop,
    Flop,
    Turn,
    River,
    Showdown,
    GameOver
}

// Enum for player actions
enum Action {
    Check,
    Bet,
    Call,
    Raise,
    Fold
}

public class GameManager {
    private Player player1;
    private Player player2;
    private GameLog gameLog;
    private int potSize;
    private Deck deck;
    public boolean button = true; // if button is true, player1 is IP and first to act
    private Player[] players;
    private int currentPlayerIndex;
    private Board board;
    private GameState currentState;
    

    public GameManager() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        gameLog = new GameLog();
        potSize = 0;
        deck = new Deck();
        board = new Board(null);
        players = new Player[]{player1, player2};
        currentPlayerIndex = 0;
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

    }

    // Method to advance the game based on player actions
    public void advanceGame() {
        switch (currentState) {
            case PreFlop:
                handlePreFlop();
                break;
            case Flop:
                handleFlop();
                break;
            case Turn:
                handleTurn();
                break;
            case River:
                handleRiver();
                break;
            case Showdown:
                determineWinner();
                currentState = GameState.GameOver;
                break;
            case GameOver:
                // Game over logic
                break;
        }
    }

    private void handlePreFlop() {

        while (!preFlopRoundEnded()) {
            Log currentAction = players[currentPlayerIndex].getAction(gameLog);
            Action action = Action.valueOf(currentAction.getAction());
            switch (action) {
                case Fold:
                    handleFold(action);
                    break;
                case Call:
                    handleCall(action);
                    break;
                case Raise:
                    handleRaise(action);
                    break;
                // Additional cases like Check, if applicable
                default:
                    break;
            }
    
        }
    
        currentState = GameState.Flop;
    }
    
    private void handleRaise(Action action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleRaise'");
    }

    private void handleCall(Action action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleCall'");
    }

    private void handleFold(Action action) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleFold'");
    }

    private boolean preFlopRoundEnded() {
        return false;
        // Logic to determine if all players have acted and the round can end
        // This typically checks if all remaining players have matched the current highest bet or folded
    }
    
    

    private void handleFlop() {
        throw new UnsupportedOperationException("Unimplemented method 'handleFold'");

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

}
