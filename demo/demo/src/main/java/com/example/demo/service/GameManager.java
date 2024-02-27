package com.example.demo.service;

import com.example.demo.model.Log;
import com.example.demo.model.Board;
import com.example.demo.model.Deck;
import com.example.demo.model.GameLog;
import com.example.demo.model.Player;
import com.example.demo.model.Action;

import java.util.Scanner;

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
    private double potSize;
    private Deck deck;
    private int currentPlayerIndex;
    private Board board;
    private GameState currentState;
    private int flopx;
    private int turnx;
    private int riverx;
    private HandRanking handRanking1;
    private HandRanking handRanking2;

    public GameManager() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        gameLog = new GameLog();
        potSize = 0;
        deck = new Deck();
        board = new Board(null);
        players = new Player[] { player1, player2 };
        currentPlayerIndex = 0;
        currentState = GameState.PREFLOP;

    }


    public double properSize(Action action){
        if (currentState == GameState.PREFLOP) {
            if (action == Action.FOLD) {
                return 0;
            }
            else if (action == Action.CALL) {
                if (gameLog.getSize() == 1) {
                    return 1.5;
                }
                else if (gameLog.getSize() == 2) {
                    return 7.5;
                }
                else if (gameLog.getSize() == 3){
                    return 14;
                } 
                else {
                    return 76;
                }
            }
            else {
                if (gameLog.getSize() == 0) {
                    return 2;
                }
                if (gameLog.getSize() == 1) {
                    return 9;
                }
                else if (gameLog.getSize() == 2) {
                    return 21.5;
                }
                else {
                    return 90;
                }
            }
        }
        return 0;

    }


    // returns all legal moves based on the last action that happened
    public int[] legalActions() {
        if (gameLog.getSize() == 0){
            return new int[]{1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
        }
        if (currentState == GameState.PREFLOP) {
            return new int[]{1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0};
        }
        Log lastMove = gameLog.getLogs().get(gameLog.getSize() - 1);
        int[] legalMoves = new int[12];

        switch (lastMove.getAction()) {
            case FOLD:
                legalMoves = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                break;
            case CHECK: 
            case CALL:
                legalMoves = new int[]{0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0};
                break;
            case BET_SMALL:
            case BET_MEDIUM:
            case BET_BIG:
            case BET_ALL_IN:
            case RAISE_SMALL:
            case RAISE_MEDIUM:
            case RAISE_BIG:
            case RAISE_ALL_IN:
                legalMoves = new int[]{1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1};
                break;
        }

        return legalMoves;
    }

    public boolean isLegalMove(Action action, int[] legalMoves) {
        int index = getIndexForAction(action);
        return index >= 0 && index < legalMoves.length && legalMoves[index] == 1;
    }

    public static int getIndexForAction(Action action) {
        Action[] values = Action.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == action) {
                return i;
            }
        }
        return -1; // Return -1 if action not found
    }

    public String getLegalActionsString(int[] legalMoves) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < legalMoves.length; i++) {
            if (legalMoves[i] == 1) {
                result.append(Action.values()[i]).append(" ");
            }
        }
        return result.toString().trim();
    }

    public void setUpPot() {
        changeStack(-.5, player1);
        changeStack(-1, player2);
        potSize = 1.5;
    }

    public void changeStack(double value, Player player) {
        player.setStack(player.getStack() + value);
        potSize -= value;
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
        flopx = 0;
        turnx = 0;
        riverx = 0;

    }

    // Method to advance the game based on player actions
    public void advanceGame() throws Exception {
        switch (currentState) {
            case PREFLOP:
                handlePreFlop();
                break;
            case FLOP:
                if (board.getBoardCards().size() != 3) {
                    deal();
                    deal();
                    deal();
                    currentPlayerIndex = 0;
                }
                statusUpdate();
                handleFlop();
                break;
            case TURN:
                if (board.getBoardCards().size() != 4) {
                    deal();
                    currentPlayerIndex = 0;

                }
                statusUpdate();
                handleTurn();
                break;
            case RIVER:
                if (board.getBoardCards().size() != 5) {
                    deal();
                    currentPlayerIndex = 0;

                }
                statusUpdate();
                handleRiver();
                break;
            case SHOWDOWN:
                currentState = GameState.GAMEOVER;
                Player temp = determineWinner();
                if (temp == null) {
                    changeStack(potSize / 2, player1);
                    changeStack(potSize / 2, player2);
                } else if (temp.equals(player1)) {
                    changeStack(potSize, player1);
                } else {
                    changeStack(potSize, player2);
                }
                advanceGame();
                break;
            case GAMEOVER:
                statusUpdate();
                break;
        }
    }

    private void statusUpdate() {
        System.out.println();
        System.out.println("The board: " + board.toString());
        System.out.println("We are currently on this street:" + currentState);
        System.out.println(player1.getName() + " has " + player1.getHand().toString() + " " + player1.getStack());
        System.out.println(player2.getName() + " has " + player2.getHand().toString() + " " + player2.getStack());
        System.out.println("Pot size is " + potSize);
        System.out.println("The current GameLog " + gameLog.toString());

    }




    private void handlePreFlop() throws Exception {
        System.out.println("-----PREFLOP-----");
        int[] legalMoves = legalActions();
        Scanner scanner = new Scanner(System.in);
        System.out.println(players[currentPlayerIndex].getName() + ", enter your action (" + getLegalActionsString(legalMoves) + ")");
        String inputAction = scanner.nextLine().toUpperCase();
        while (!isLegalMove(Action.valueOf(inputAction), legalMoves)) {
            System.out.println("illegal action, please try again" + ", enter your action (" + getLegalActionsString(legalMoves) + ")");
            inputAction = scanner.nextLine().toUpperCase();
        }
        // System.out.println(players[currentPlayerIndex].getName() + ", enter your size: ");
        // double inputSize = Double.parseDouble(scanner.nextLine());
        double inputSize = properSize(Action.valueOf(inputAction));


        Log currentAction = players[currentPlayerIndex].getAction(inputAction, inputSize);


        //Log currentAction = players[currentPlayerIndex].getAction(gameLog, board);




        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);

                // if the current player folds, we switch players and award the pot to the other
                // player
                nextTurn();
                changeStack(potSize, players[currentPlayerIndex]);

                currentState = GameState.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                if (gameLog.getLogs().size() == 1) {
                    currentState = GameState.PREFLOP;
                } else {
                    currentState = GameState.FLOP;
                }
                break;
            case RAISE_ALL_IN:
            case RAISE_BIG:
            case RAISE_MEDIUM:
            case RAISE_SMALL:
            case BET_ALL_IN:
            case BET_BIG:
            case BET_MEDIUM:
            case BET_SMALL:
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

    private void handleFlop() throws Exception {
        int[] legalMoves = legalActions();

        Scanner scanner = new Scanner(System.in);
        System.out.println(players[currentPlayerIndex].getName() + ", enter your action (" + getLegalActionsString(legalMoves) + ")");
        String inputAction = scanner.nextLine().toUpperCase();
        while (!isLegalMove(Action.valueOf(inputAction), legalMoves)) {
            System.out.println("illegal action, please try again" + ", enter your action (" + getLegalActionsString(legalMoves) + ")");
            inputAction = scanner.nextLine().toUpperCase();
        }
        System.out.println(players[currentPlayerIndex].getName() + ", enter your size: ");
        double inputSize = Double.parseDouble(scanner.nextLine());
        Log currentAction = players[currentPlayerIndex].getAction(inputAction, inputSize);

        // Log currentAction = players[currentPlayerIndex].getAction(gameLog);

        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);

                nextTurn();
                changeStack(potSize, players[currentPlayerIndex]);

                currentState = GameState.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                currentState = GameState.TURN;
                break;
            case RAISE_ALL_IN:
            case RAISE_BIG:
            case RAISE_MEDIUM:
            case RAISE_SMALL:
            case BET_ALL_IN:
            case BET_BIG:
            case BET_MEDIUM:
            case BET_SMALL:                
            handleRaise(currentAction);
                currentState = GameState.FLOP;
                break;
            case CHECK:
                handleCheck(currentAction);
                flopx += 1;
                if (flopx == 2) {
                    currentState = GameState.TURN;
                } else {
                    currentState = GameState.FLOP;
                }
                break;
            default:
                break;
        }
        advanceGame();
        scanner.close();
        // Handle Flop actions
    }

    // Similar methods for Turn and River
    private void handleTurn() throws Exception {
        int[] legalMoves = legalActions();

        Scanner scanner = new Scanner(System.in);
        System.out.println(players[currentPlayerIndex].getName() + ", enter your action (" + getLegalActionsString(legalMoves) + ")");
        String inputAction = scanner.nextLine().toUpperCase();
        while (!isLegalMove(Action.valueOf(inputAction), legalMoves)) {
            System.out.println("illegal action, please try again" + ", enter your action (" + getLegalActionsString(legalMoves) + ")");
            inputAction = scanner.nextLine().toUpperCase();
        }
        System.out.println(players[currentPlayerIndex].getName() + ", enter your size: ");
        double inputSize = Double.parseDouble(scanner.nextLine());
        Log currentAction = players[currentPlayerIndex].getAction(inputAction, inputSize);

        // Log currentAction = players[currentPlayerIndex].getAction(gameLog);

        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);

                nextTurn();
                changeStack(potSize, players[currentPlayerIndex]);

                currentState = GameState.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                currentState = GameState.RIVER;
                break;
            case RAISE_ALL_IN:
            case RAISE_BIG:
            case RAISE_MEDIUM:
            case RAISE_SMALL:
            case BET_ALL_IN:
            case BET_BIG:
            case BET_MEDIUM:
            case BET_SMALL: 
                handleRaise(currentAction);
                currentState = GameState.TURN;
                break;
            case CHECK:
                handleCheck(currentAction);
                turnx += 1;
                if (turnx == 2) {
                    currentState = GameState.RIVER;
                } else {
                    currentState = GameState.TURN;
                }
                break;
            default:
                break;
        }
        advanceGame();
        scanner.close();


    }

    private void handleRiver() throws Exception {
        int[] legalMoves = legalActions();

        Scanner scanner = new Scanner(System.in);
        System.out.println(players[currentPlayerIndex].getName() + ", enter your action (" + getLegalActionsString(legalMoves) + ")");
        String inputAction = scanner.nextLine().toUpperCase();
        while (!isLegalMove(Action.valueOf(inputAction), legalMoves)) {
            System.out.println("illegal action, please try again" + ", enter your action (" + getLegalActionsString(legalMoves) + ")");
            inputAction = scanner.nextLine().toUpperCase();
        }
        System.out.println(players[currentPlayerIndex].getName() + ", enter your size: ");
        double inputSize = Double.parseDouble(scanner.nextLine());
        Log currentAction = players[currentPlayerIndex].getAction(inputAction, inputSize);

        // Log currentAction = players[currentPlayerIndex].getAction(gameLog);

        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);

                nextTurn();
                changeStack(potSize, players[currentPlayerIndex]);

                currentState = GameState.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                currentState = GameState.SHOWDOWN;
                break;
            case RAISE_ALL_IN:
            case RAISE_BIG:
            case RAISE_MEDIUM:
            case RAISE_SMALL:
            case BET_ALL_IN:
            case BET_BIG:
            case BET_MEDIUM:
            case BET_SMALL: 
                handleRaise(currentAction);
                currentState = GameState.RIVER;
                break;
            case CHECK:
                handleCheck(currentAction);
                riverx += 1;
                if (riverx == 2) {
                    currentState = GameState.SHOWDOWN;
                } else {
                    currentState = GameState.RIVER;
                }
                break;
            default:
                break;
        }
        advanceGame();
        scanner.close();
    }

    private void handleRaise(Log action) throws Exception {
        if (players[currentPlayerIndex].getStack() < action.getSize()) {
            throw new Exception("Not enough chips");
        }
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
    }

    private Player determineWinner() throws Exception {
        handRanking1 = new HandRanking(player1.getHand(), board);
        handRanking2 = new HandRanking(player2.getHand(), board);
        int val = handRanking1.compare(handRanking2);
        if (val == 1) {
            return player1;
        } else if (val == -1) {
            return player2;
        } else {
            return null;
        }

    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }

    public double getPotSize() {
        return potSize;
    }

    public static void main(String[] args) throws Exception {
        GameManager GM = new GameManager();
        GM.setUpGame();
        GM.advanceGame();
    }

}
