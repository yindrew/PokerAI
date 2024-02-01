package com.example.demo.service;
import java.util.*;

import com.example.demo.model.Board;
import com.example.demo.model.Card;
import com.example.demo.model.Hand;

public class HandRanking {
    private final int NUMVALUES = 13;
    private List<Character> values = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
    private ArrayList<Card> allCards = new ArrayList<Card>();
    private Hand hand;
    private Board board;
    private int[] cardVals = new int[7];
    private Card[] bestFive = new Card[5];
    private Card[] allCardsArr = new Card[7];
    private PairFinder pairFinder;
    private String handType;
    
    public int handVal(String handType) {
        switch (handType) {
            case "Straight Flush":
                return 9;
            case "Quads":
                return 8;
            case "Full House":
                return 7;
            case "Flush":
                return 6;
            case "Straight":
                return 5;
            case "Trips":
                return 4;
            case "Two Pair":
                return 3;
            case "One Pair":
                return 2;
            default:
                return 1;
        }
    }
    public HandRanking(Hand hand, Board board) throws Exception {
        this.hand = hand;
        this.board = board;
        pairFinder = new PairFinder(hand, board);
        Arrays.fill(cardVals, -2);
        Arrays.fill(allCardsArr, new Card("-2m"));

        for (int x = 0; x < hand.getHand().length; x++) {
            allCards.add(hand.getHand()[x]);
            cardVals[x] = hand.getHand()[x].getCardVal();
            allCardsArr[x] = hand.getHand()[x];

        }

        for (int x = 2; x < 2 + board.getBoardCards().size(); x++) {
            allCards.add(board.getBoardCards().get(x - 2));
            cardVals[x] = board.getBoardCards().get(x - 2).getCardVal();
            allCardsArr[x] = board.getBoardCards().get(x - 2);
        }
        handType = this.handType();
    }


    public void updateBoard(Board newBoard) throws Exception {
        this.board = newBoard;
        allCards.clear();
        for (int x = 0; x < hand.getHand().length; x++) {
            allCards.add(hand.getHand()[x]);
            cardVals[x] = hand.getHand()[x].getCardVal();
            allCardsArr[x] = hand.getHand()[x];

        }

        for (int x = 2; x < 2 + board.getBoardCards().size(); x++) {
            allCards.add(board.getBoardCards().get(x - 2));
            cardVals[x] = board.getBoardCards().get(x - 2).getCardVal();
            allCardsArr[x] = board.getBoardCards().get(x - 2);

        }
        pairFinder = new PairFinder(hand, newBoard);


    }

    public String handType() throws Exception {
        if (board.getBoardCards().size() == 0) {
            return "No Board";
        }
        ArrayList<Card> tempFive = pairFinder.returnBestFive();
        String handType = pairFinder.returnHandRanking();

        if (straightFlush()) {
            Arrays.sort(bestFive, Comparator.comparingInt(Card::getCardVal).reversed());
            return "Straight Flush";
        }
        if (handType.equals("quads")) {
            for (int x = 0; x < 5; x++) {
                bestFive[x] = tempFive.get(x);
            }
            return "Quads";
        }
        if (handType.equals("full house")) {
            for (int x = 0; x < 5; x++) {
                bestFive[x] = tempFive.get(x);
            }
            return "Full House";
        }
        if (flush()) {
            return "Flush";
        }
        if (straight()) {
            largestConsecutiveSequenceIndices(cardVals);
            Arrays.sort(bestFive, Comparator.comparingInt(Card::getCardVal).reversed());
            isLowStraight();
            return "Straight";
        }
        if (handType.equals("trips")) {
            for (int x = 0; x < 5; x++) {
                bestFive[x] = tempFive.get(x);
            }
            return "Trips";
        }
        if (handType.equals("two pair")) {
            for (int x = 0; x < 5; x++) {
                bestFive[x] = tempFive.get(x);
            }
            return "Two Pair";
        }
        if (handType.equals("one pair")) {
            for (int x = 0; x < 5; x++) {
                bestFive[x] = tempFive.get(x);
            }
            return "One Pair";
        }
        for (int x = 0; x < 5; x++) {
            bestFive[x] = tempFive.get(x);
        }
        return "No Pair";
    }

    public Card[] getBestFive() {
        return bestFive;
    }
    public void printBestFive() throws Exception {
        if (allCards.size() > 2){
            String handType = handType();
            Card[] bestFive = getBestFive();
    
            System.out.println("Hand Type: " + handType);
            System.out.print("The best five card combination is: ");
            for (int x = 0; x < 5; x++) {
                System.out.print(bestFive[x] + " ");
            }
            System.out.println();

        }
        else {
            System.out.println("Hand: " + hand.toString());
        }


    }

    private void isLowStraight() {
        Card[] copyArray = bestFive.clone();
        Arrays.sort(copyArray, Comparator.comparingInt(Card::getCardVal));
        int[] lowStraight = { 2, 3, 4, 5, 14 };
        boolean loStraight = true;
        for (int x = 0; x < copyArray.length; x++) {
            if (copyArray[x].getCardVal() != lowStraight[x]) {
                loStraight = false;
            }
        }
        if (loStraight){
            for (int x = 0; x < 4; x++) {
                Card temp = bestFive[x];
                bestFive[x] = bestFive[x+1];
                bestFive[x+1] = temp;
            }
        }

    }

    // getting 5 card combo for straight and sets into into HandCombo
    public int largestConsecutiveSequenceIndices(int[] arr) {
        Card[] copyArray = allCardsArr.clone();
        Arrays.sort(copyArray, Comparator.comparingInt(Card::getCardVal));
        int[] lowStraight = { 2, 3, 4, 5, 14 };
        int index = 0;

        for (int x = 0; x < copyArray.length; x++) {
            if ((index != 5) && (copyArray[x].getCardVal() == lowStraight[index])) {
                lowStraight[index] = x;
                index++;
            }
        }

        if (index == 5) {
            for (int x = 0; x < 5; x++) {
                bestFive[x] = copyArray[lowStraight[x]];
            }            
        }

        Set<Integer> set = new HashSet<>();
        for (int num : arr) {
            set.add(num);
        }

        int maxLen = 0;
        List<Integer> maxLenIndices = new ArrayList<>();

        for (int num : set) {
            if (!set.contains(num - 1)) {
                int currentLen = 1;
                int currentEnd = num;
                while (set.contains(currentEnd + 1)) {
                    currentLen++;
                    currentEnd++;
                }
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                    maxLenIndices.clear();
                    for (int i = num; i <= currentEnd; i++) {
                        maxLenIndices.add(getIndex(arr, i));
                    }
                } else if (currentLen == maxLen) {
                    for (int i = num; i <= currentEnd; i++) {
                        maxLenIndices.add(getIndex(arr, i));
                    }
                }
            }
        }

        int size = maxLenIndices.size();

        if (size == 5) {
            for (int x = 0; x < maxLenIndices.size(); x++) {
                bestFive[x] = allCardsArr[maxLenIndices.get(x)];

            }
        }
        if (size == 6) {
            for (int x = 1; x < maxLenIndices.size(); x++) {
                bestFive[x - 1] = allCardsArr[maxLenIndices.get(x)];

            }

        }
        if (size == 7) {
            for (int x = 2; x < maxLenIndices.size(); x++) {
                bestFive[x - 2] = allCardsArr[maxLenIndices.get(x)];

            }

        }
        return size;

    }

    // helper method for the consecutive integer function
    private static int getIndex(int[] arr, int num) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == num) {
                return i;
            }
        }
        return -1;
    }

    // if you have straight flush or not
    public boolean straightFlush() throws Exception {
        if (flush() && straight()) {
            int[] flushCounter = { 0, 0, 0, 0 };
            for (int x = 0; x < allCards.size(); x++) {
                char suit = allCards.get(x).getSuit();

                switch (suit) {
                    case 'c':
                        flushCounter[0] += 1;
                        break;
                    case 'd':
                        flushCounter[1] += 1;
                        break;
                    case 'h':
                        flushCounter[2] += 1;
                        break;
                    case 's':
                        flushCounter[3] += 1;
                        break;
                    default:
                        break;

                }
            }

            // getting the dominant suit
            char[] suits = { 'c', 'd', 'h', 's' };
            char suit = suits[0];
            int max = flushCounter[0];
            for (int x = 0; x < 4; x++) {
                if (flushCounter[x] > max) {
                    max = flushCounter[x];
                    suit = suits[x];
                }
            }

            // getting values of cards from the dominant suit
            int[] cards = new int[7];
            for (int x = 0; x < allCards.size(); x++) {
                if (allCards.get(x).getSuit() == (suit)) {
                    cards[x] = allCards.get(x).getCardVal();
                }
            }

            // running alg to see if there is a sequence of 5 consecutive numbers
            if (longestConsecutiveSequence(cards) >= 5) {
                largestConsecutiveSequenceIndices(cards);
                return true;

            }

        }
        return false;

    }

    boolean straight() {
        return longestConsecutiveSequence(cardVals) >= 5;
    }

    boolean flush() throws Exception {
        int[] flushCounter = { 0, 0, 0, 0 };
        for (int x = 0; x < allCards.size(); x++) {
            char suit = allCards.get(x).getSuit();

            switch (suit) {
                case 'c':
                    flushCounter[0] += 1;
                    break;
                case 'd':
                    flushCounter[1] += 1;
                    break;
                case 'h':
                    flushCounter[2] += 1;
                    break;
                case 's':
                    flushCounter[3] += 1;
                    break;
                default:
                    break;

            }
        }

        char[] suits = { 'c', 'd', 'h', 's' };
        for (int x = 0; x < flushCounter.length; x++) {
            if (flushCounter[x] >= 5) {
                char domSuit = suits[x];
                Card[] suited = new Card[flushCounter[x]];
                int suitedInc = 0;

                for (int y = 0; y < allCards.size(); y++) {
                    if (allCards.get(y).getSuit() == (domSuit)) {
                        suited[suitedInc] = allCards.get(y);
                        suitedInc++;
                    }

                }
                setHandComboFlush(suited, domSuit);
                return true;
            }
        }
        return false;

    }

    public void setHandComboFlush(Card[] arr, char suit) throws Exception {
        int[] cardVals = new int[arr.length];
        for (int x = 0; x < arr.length; x++) {
            cardVals[x] = arr[x].getCardVal();
        }
        Arrays.sort(cardVals);
        if (arr.length == 5) {
            for (int x = 0; x < arr.length; x++) {
                bestFive[x] = new Card(suit, cardVals[cardVals.length - 1 - x] - 2);
            }

        }

        if (arr.length == 6) {
            for (int x = 1; x < arr.length; x++) {
                bestFive[x - 1] = new Card(suit, cardVals[cardVals.length - x] - 2);
            }

        }

        if (arr.length == 7) {
            for (int x = 2; x < arr.length; x++) {
                bestFive[x - 2] = new Card(suit, cardVals[cardVals.length + 1 - x] - 2);
            }

        }

    }

    private int longestConsecutiveSequence(int[] cardVals) {
        Set<Integer> set = new HashSet<>();
        int maxLen = 0;

        for (int num : cardVals) {
            set.add(num);
            if (num == 14) {
                set.add(1);
            }
        }

        for (int num : set) {
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentLen = 1;

                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentLen++;
                }

                maxLen = Math.max(maxLen, currentLen);
            }
        }

        return maxLen;
    }


    public int getNUMVALUES() {
        return NUMVALUES;
    }

    public List<Character> getValues() {
        return values;
    }

    public void setValues(List<Character> values) {
        this.values = values;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public void setAllCards(ArrayList<Card> allCards) {
        this.allCards = allCards;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int[] getCardVals() {
        return cardVals;
    }

    public void setCardVals(int[] cardVals) {
        this.cardVals = cardVals;
    }

    public void setBestFive(Card[] bestFive) {
        this.bestFive = bestFive;
    }

    public Card[] getAllCardsArr() {
        return allCardsArr;
    }

    public void setAllCardsArr(Card[] allCardsArr) {
        this.allCardsArr = allCardsArr;
    }

    public PairFinder getPairFinder() {
        return pairFinder;
    }

    public void setPairFinder(PairFinder pairFinder) {
        this.pairFinder = pairFinder;
    }

    public String getHandType() {
        return handType;
    }
}
