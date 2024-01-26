package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        Log l1 = new Log("bet", 5);
        Log l2 = new Log("fold", 0);
        GameLog log = new GameLog();
        log.addLog(l1);
        log.addLog(l2);

        // initializing the board and hand
        Card deckCard1 = new Card("9h");
        Card deckCard2 = new Card("10d");
        Card deckCard3 = new Card("Ks");
        Card handCard1 = new Card("Ad");
        Card handCard2 = new Card("As");
        Board board = new Board(new Card[]{deckCard1, deckCard2, deckCard3});
        Hand hand = new Hand(new Card[] {handCard1, handCard2});

        GameState gameState = new GameState(board, hand, log);


        DecisionController controller = new DecisionController();
        controller.sendGameState(gameState);






    }

}
