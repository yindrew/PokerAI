package com.example.demo.service;
import org.junit.jupiter.api.Test;

import com.example.demo.model.Board;
import com.example.demo.model.Card;
import com.example.demo.model.Hand;

public class HandRankingTest {
    Board board;
    Hand hand;
    HandRanking handRanking;

    @Test
    public void testingSF() throws Exception {

        // STRAIGHT FLUSH #1
        Card[] sf1 = new Card[] { new Card("9c"), new Card("Kc"), new Card("Qc"), new Card("Qd"), new Card("6s") };
        hand = new Hand("JcTc");
        board = new Board(sf1);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();

    }

    @Test
    public void testingQuads() throws Exception {

        // QUAD #1
        Card[] quadCard1 = new Card[] { new Card("2c"), new Card("2h"), new Card("2d"), new Card("2s"),
                new Card("As") };
        hand = new Hand("AcAd");
        board = new Board(quadCard1);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();

        // QUAD #2
        Card[] quadCard2 = new Card[] { new Card("2h"), new Card("7h"), new Card("4d"), new Card("3s"),
                new Card("2s") };
        hand = new Hand("2c2h");
        board = new Board(quadCard2);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();

    }

    @Test
    public void testingFullHouse() throws Exception {
        // FULL HOUSE #1
        Card[] fullHouseCard1 = new Card[] { new Card("2h"), new Card("2s"), new Card("2d"), new Card("As"),
                new Card("3c") };

        hand = new Hand("3c3h");
        board = new Board(fullHouseCard1);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();

    }


    @Test
    public void testingFlush() throws Exception {
        // FLUSH #1
        Card[] flush = new Card[] { new Card("2h"), new Card("3h"), new Card("9h"), new Card("As"),
                new Card("3c") };

        hand = new Hand("AhTh");
        board = new Board(flush);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();

    }

    @Test 
    public void testingErrorStraight() throws Exception {
            // Straight #3
            Card[] tempBoard = new Card[] {new Card("9c"), new Card("Ts"), new Card("Jc")};
            Board board = new Board(tempBoard);
            hand = new Hand("KhQh");
            handRanking = new HandRanking(hand, board);
    
            handRanking.printBestFive();
    }

    @Test
    public void testingStraight() throws Exception {
        // Straight #1
        Card[] straight = new Card[] { new Card("2h"), new Card("3h"), new Card("4h"), new Card("As"), new Card("3c") };

        hand = new Hand("5d8s");
        board = new Board(straight);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();

        // Straight #2
        straight = new Card[] { new Card("2h"), new Card("3h"), new Card("4h"), new Card("As"), new Card("3c") };

        hand = new Hand("5d6s");
        board = new Board(straight);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();

    }

    @Test
    public void testingTrips() throws Exception {
        Card[] tripsC = new Card[] { new Card("3h"), new Card("6s"), new Card("Td"), new Card("5s"), new Card("9c") };
        
        hand = new Hand("3c3h");
        board = new Board(tripsC);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();
    }


    
    @Test
    public void testingTwoPair() throws Exception {
        Card[] twoPairC = new Card[] { new Card("8h"), new Card("8s"), new Card("2d"), new Card("9s"), new Card("9c") };
        
        hand = new Hand("AcAh");
        board = new Board(twoPairC);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();

    }


    @Test
    public void testingOnePair() throws Exception {
        Card[] onePairC = new Card[] { new Card("2h"), new Card("8s"), new Card("4d"), new Card("9s"), new Card("3c") };
        
        hand = new Hand("AcAh");
        board = new Board(onePairC);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();

    }


    @Test
    public void testingNoPair() throws Exception {
        Card[] onePairC = new Card[] { new Card("2h"), new Card("8s"), new Card("4d"), new Card("9s"), new Card("3c") };
        
        hand = new Hand("As7h");
        board = new Board(onePairC);
        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();
    }

    @Test
    public void testingUpdatingBoardRiver() throws Exception {
        Card[] onePairC = new Card[] { new Card("2h"), new Card("8s"), new Card("4d"), new Card("9s")};
        
        hand = new Hand("As7h");
        board = new Board(onePairC);

        handRanking = new HandRanking(hand, board);

        handRanking.printBestFive();
        System.out.println();

        board.addCard(new Card("Ah"));
        handRanking.updateBoard(board);
        handRanking.printBestFive();

        System.out.println();

        System.out.println(board.toString());



    }

    @Test
    public void testingChangingBoardPreflop() throws Exception {
        Hand h1 = new Hand("AcQc");
        Card[] tempBoard = new Card[] {new Card("9c"), new Card("Qs"), new Card("Jc"), new Card("2s")};
        Board board = new Board(tempBoard);

        HandRanking hR = new HandRanking(h1, board);
        hR.printBestFive();
        board.addCard(new Card("Qd"));

        hR.updateBoard(board);
        hR.printBestFive();
        System.out.println();


    }


}
