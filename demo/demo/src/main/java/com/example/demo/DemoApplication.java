package com.example.demo;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.Action;
import com.example.demo.model.Board;
import com.example.demo.model.Card;
import com.example.demo.model.GameLog;
import com.example.demo.model.GameState;
import com.example.demo.model.Log;
import com.example.demo.model.Hand;
import com.example.demo.controller.DecisionController;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        // initializing the log 
        Log l1 = new Log(Action.BET, 3);
        Log l6 = new Log(Action.BET, 10);

        Log l2 = new Log(Action.CALL, 10);
        Log l3 = new Log(Action.CHECK, 0);
        Log l4 = new Log(Action.BET, 15);
        Log l5 = new Log(Action.ALL_IN, 90);

        GameLog log = new GameLog();
        log.addLog(l1);
        log.addLog(l6);
        log.addLog(l2);
        log.addLog(l3);
        log.addLog(l4);
        log.addLog(l5);


        // initializing the board and hand
        Card deckCard1 = new Card("9h");
        Card deckCard2 = new Card("Td");
        Card deckCard3 = new Card("Ks");
        Card handCard1 = new Card("Ad");
        Card handCard2 = new Card("As");
        Board board = new Board(new Card[]{deckCard1, deckCard2, deckCard3});
        Hand hand = new Hand(new Card[] {handCard1, handCard2});

        GameState gameState = new GameState(board, hand, log);


        DecisionController controller = new DecisionController();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("send")) {

                controller.sendGameState(gameState);
            }
        }






    }

}
