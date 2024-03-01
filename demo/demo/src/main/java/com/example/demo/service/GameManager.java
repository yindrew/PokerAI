package com.example.demo.service;

import com.example.demo.model.Log;
import com.example.demo.model.Output;
import com.example.demo.model.Board;
import com.example.demo.model.Deck;
import com.example.demo.model.GameLog;
import com.example.demo.model.Player;
import com.example.demo.model.Action;
import com.example.demo.model.GameState;

import java.util.Scanner;

enum Street {
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
    private Street currentState;
    private int flopx;
    private int turnx;
    private int riverx;
    private HandRanking handRanking1;
    private HandRanking handRanking2;

    public void setGameState(Street gs) {
        currentState = gs;
    }

    public void setGameLog(GameLog gl) {
        gameLog = gl;
    }

    public GameManager() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        gameLog = new GameLog();
        potSize = 0;
        deck = new Deck();
        board = new Board(null);
        players = new Player[] { player1, player2 };
        currentPlayerIndex = 0;
        currentState = Street.PREFLOP;

    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private int[] betOverPot(int[] legalMoves) {
        for (int i = 0; i < legalMoves.length; i++) {
            if (legalMoves[i] == 1) {
                Action action = Action.values()[i];
                if (properSize(action) > players[currentPlayerIndex].getStack()){
                    legalMoves[i] = 0;
                }
            }
        }

        return legalMoves;
    }


    public double properSize(Action action){
        if (action == Action.RAISE_ALL_IN) {
            return players[currentPlayerIndex].getStack();
        }
        if (currentState == Street.PREFLOP) {
            if (action == Action.FOLD) {
                return 0;
            }
            else if (action == Action.CALL) {
                if (gameLog.getLogs().get(gameLog.getSize() - 1).getAction() == Action.RAISE_ALL_IN){
                    return players[currentPlayerIndex].getStack();
                }
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
        else{
            Log lastAction;
            switch (action) {
                case FOLD:
                case CHECK: 
                    return 0;
                case CALL:
                    lastAction = gameLog.getLogs().get(gameLog.getSize() - 1);
                    double otherPlayerStack = players[(currentPlayerIndex + 1) % players.length].getStack();
                    return players[currentPlayerIndex].getStack() - otherPlayerStack;
                case BET_SMALL:
                    return potSize / 4;
                case BET_MEDIUM:
                    return potSize * 3 / 4;
                case BET_BIG:
                    return potSize * 2;
                case BET_ALL_IN:
                    return players[currentPlayerIndex].getStack();
                case RAISE_SMALL:
                    lastAction = gameLog.getLogs().get(gameLog.getSize() - 1);
                    return roundvalues(lastAction.getSize() * 2.2);
                case RAISE_MEDIUM:
                    lastAction = gameLog.getLogs().get(gameLog.getSize() - 1);
                    return roundvalues(lastAction.getSize() * 3.5);
                case RAISE_BIG:
                    lastAction = gameLog.getLogs().get(gameLog.getSize() - 1);
                    return roundvalues(lastAction.getSize() * 4.4);
                case RAISE_ALL_IN:
                    return players[currentPlayerIndex].getStack();
            }
        }
        return 0;
    }

    public static double roundvalues(double value) {
        return Math.round(value * 2) / 2;
    }

    // returns all legal moves based on the last action that happened
    public int[] legalActions() {
        Log lastMove;
        if (gameLog.getSize() == 0){
            return new int[]{1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
        }
        lastMove = gameLog.getLogs().get(gameLog.getSize() - 1);
        if (currentState == Street.PREFLOP) {
            if (lastMove.getAction() == Action.RAISE_ALL_IN) {
                return new int[]{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
            }
            if (gameLog.getSize() == 3) {
                return new int[] {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1};
            }
            return new int[]{1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1};
        }
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
                legalMoves = new int[]{1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1};
                    break;
            case BET_ALL_IN:
                legalMoves = new int[]{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
                break;
            case RAISE_SMALL:
            case RAISE_MEDIUM:
            case RAISE_BIG:
                legalMoves = new int[]{1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1};
                break;
            case RAISE_ALL_IN:
                legalMoves = new int[]{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
                break;
        }
        legalMoves = betOverPot(legalMoves);
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


    // public String getAction() {
    //     int[] legalMoves = legalActions();
    //     // Scanner prompts player for a legal action. 
    //     Scanner scanner = new Scanner(System.in);
    //     System.out.println(players[currentPlayerIndex].getName() + ", enter your action (" + getLegalActionsString(legalMoves) + ")");
    //     int inputAction = scanner.nextInt();
    //     int counter = 0;
    //     String action = "";
    //     for (int i= 0; i < legalMoves.length; i++){
    //         if (legalMoves[i] == 1) {
    //             counter += 1;
    //             if (counter == inputAction) {
    //                 action = Action.values()[i].toString();
    //             }
    //         }
    //     }

    //     return action;

    // }


    public String getAction() {
        Player currentPlayer = players[currentPlayerIndex];
        GameState gameState = new GameState(board, currentPlayer.getHand(), gameLog, legalActions());
        currentPlayer.setGameState(gameState);
        Log action = currentPlayer.getAction();
        return action.getAction().toString();
    }


    public void setUpGame() {
        setUpPot();
        deck.newDeck();
        dealHoleCards();
        board.clearBoard();
        gameLog.clearLog();
        currentState = Street.PREFLOP;
        flopx = 0;
        turnx = 0;
        riverx = 0;
        player1.setStack(100);
        player2.setStack(100);
    }



    // Method to advance the game based on player actions
    public void advanceGame() throws Exception {
        switch (currentState) {
            case PREFLOP:
                statusUpdate();
                handlePreFlop();
                break;
            case FLOP:
                if (board.getBoardCards().size() != 3) {
                    deal();deal();deal();
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
                while (board.getBoardCards().size() != 5) {
                    deal();
                }
                // determine the winning player and distribute the pot
                Player temp = determineWinner();
                if (temp == null) {
                    changeStack(potSize / 2, player1);
                    changeStack(potSize / 2, player2);
                } else if (temp.equals(player1)) {
                    changeStack(potSize, player1);
                } else {
                    changeStack(potSize, player2);
                }
                currentState = Street.GAMEOVER;
                advanceGame();
                break;
            case GAMEOVER:
                statusUpdate();
                Scanner scanner = new Scanner(System.in);
                System.out.println("New Hand?");
                int inputAction = scanner.nextInt();
                if (inputAction == 1) {
                    Output output0 = finalReturn(0);
                    Output output1 = finalReturn(1);
                    player1.sendFinalState(output0);
                    setUpGame();
                    advanceGame();
                }

                
                break;
        }
    }

    public Output finalReturn(int playerIndex) {
        return new Output(board, players[playerIndex].getHand(), players[playerIndex].getStack() - 100, gameLog);
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

    public boolean playersAllIn() {
        return players[0].getStack() == 0 && players[1].getStack() == 0;
    }


    private void handlePreFlop() throws Exception {

        String inputAction = getAction();
        double inputSize = properSize(Action.valueOf(inputAction));
        Log currentAction = players[currentPlayerIndex].getAction(inputAction, inputSize);


        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);
                currentState = Street.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                currentState = Street.FLOP;

                if (playersAllIn()) {
                    currentState = Street.SHOWDOWN;
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
                break;
            case CHECK:
            default:
                break;
        }
        advanceGame();
    }

    private void handleFlop() throws Exception {

        String inputAction = getAction();
        double inputSize = properSize(Action.valueOf(inputAction));
        Log currentAction = players[currentPlayerIndex].getAction(inputAction, inputSize);

        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);
                currentState = Street.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                currentState = Street.TURN;
                if (playersAllIn()) {
                    currentState = Street.SHOWDOWN;
                }
                break;
            case RAISE_ALL_IN:
            case BET_ALL_IN:
            case RAISE_BIG:
            case RAISE_MEDIUM:
            case RAISE_SMALL:
            case BET_BIG:
            case BET_MEDIUM:
            case BET_SMALL:                
            handleRaise(currentAction);
                currentState = Street.FLOP;
                break;
            case CHECK:
                handleCheck(currentAction);
                flopx += 1;
                if (flopx == 2) {
                    currentState = Street.TURN;
                } else {
                    currentState = Street.FLOP;
                }
                break;
            default:
                break;
        }
        advanceGame();
        // Handle Flop actions
    }

    // Similar methods for Turn and River
    private void handleTurn() throws Exception {

        String inputAction = getAction();

        double inputSize = properSize(Action.valueOf(inputAction));
        
        Log currentAction = players[currentPlayerIndex].getAction(inputAction, inputSize);

        // Log currentAction = players[currentPlayerIndex].getAction(gameLog);

        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);
                currentState = Street.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                currentState = Street.RIVER;
                if (playersAllIn()) {
                    currentState = Street.SHOWDOWN;
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
                break;
            case CHECK:
                handleCheck(currentAction);
                turnx += 1;
                currentState = (turnx == 2) ? Street.RIVER : Street.TURN;
                break;
            default:
                break;
        }
        advanceGame();


    }

    private void handleRiver() throws Exception {

        String inputAction = getAction();
        double inputSize = properSize(Action.valueOf(inputAction));
        Log currentAction = players[currentPlayerIndex].getAction(inputAction, inputSize);

        // Log currentAction = players[currentPlayerIndex].getAction(gameLog);

        // based on the action handle the action.
        switch (currentAction.getAction()) {
            case FOLD:
                handleFold(currentAction);
                currentState = Street.GAMEOVER;
                break;
            case CALL:
                handleCall(currentAction);
                currentState = Street.SHOWDOWN;
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
                break;
            case CHECK:
                handleCheck(currentAction);
                riverx += 1;
                currentState = (riverx == 2) ? Street.SHOWDOWN : Street.RIVER;
                break;
            default:
                break;
        }
        advanceGame();
    }


    private void handleRaise(Log action) throws Exception {
        gameLog.addLog(action);
        changeStack(-action.getSize(), players[currentPlayerIndex]);
        nextTurn();
    }

    private void handleCheck(Log action) {
        gameLog.addLog(action);
        nextTurn();

    }

    private void handleCall(Log action) {
        gameLog.addLog(action);
        changeStack(-action.getSize(), players[currentPlayerIndex]);
        nextTurn();

    }

    private void handleFold(Log action) {
        gameLog.addLog(action);
        nextTurn();
        changeStack(potSize, players[currentPlayerIndex]);
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
