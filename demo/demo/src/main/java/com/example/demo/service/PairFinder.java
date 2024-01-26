package com.example.demo.service;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.demo.model.Board;
import com.example.demo.model.Card;
import com.example.demo.model.CardArray;
import com.example.demo.model.Hand;

public class PairFinder {
    private ArrayList<Card> allCards = new ArrayList<Card>();
    private ArrayList<CardArray> cardArrayArrayList = new ArrayList<CardArray>();

    // {no pair, one pair, trips, quads}
    private int[] cardPairArray = new int[4];

    public PairFinder(Hand hand, Board board) throws Exception {

        allCards.addAll(Arrays.asList(hand.getHand()));
        allCards.addAll(board.getBoardCards());

        for (int i = 0; i < 4; i++) {
            cardArrayArrayList.add(new CardArray());
        }

        doCalculation();

    }

    private void doCalculation() throws Exception {
        ArrayList<Card> deepCopyOfAllCards = new ArrayList<Card>();
        deepCopyOfAllCards.addAll(allCards);
        int count = 0;
        ArrayList<Card> significantCardArrayList = new ArrayList<Card>();

        while (deepCopyOfAllCards.size() != 0) {
            significantCardArrayList.clear();
            count = 0;
            Card thisCard = deepCopyOfAllCards.remove(0);
            significantCardArrayList.add(thisCard);
            for (int i = 0; i < deepCopyOfAllCards.size(); i++) {
                if (thisCard.getValue() == deepCopyOfAllCards.get(i).getValue()) {
                    count++;
                    significantCardArrayList.add(deepCopyOfAllCards.get(i));
                    deepCopyOfAllCards.remove(deepCopyOfAllCards.get(i));
                    i--;

                }
            }
            cardPairArray[count]++;
            for (Card c : significantCardArrayList) {
                cardArrayArrayList.get(count).getCardArray().add(c);
            }

            for (CardArray ca : cardArrayArrayList) {
                sortArrayListWithout(ca.getCardArray(), new ArrayList<Card>());
            }

        }

    }

    public ArrayList<Card> returnBestFive() throws Exception {
        String handRanking = returnHandRanking();
        if (handRanking.equals("quads")) {
            ArrayList<Card> fiveCard = cardArrayArrayList.get(3).getCardArray();
            ArrayList<Card> sortedCards = sortArrayListWithout(allCards, fiveCard);
            fiveCard.add(sortedCards.get(0));
            return fiveCard;
        } else if (handRanking.equals("full house") || handRanking.equals("trips")) {
            ArrayList<Card> fiveCard = new ArrayList<Card>();
            // {trips + a}
            for (int i = 0; i < 3; i++) {
                fiveCard.add(cardArrayArrayList.get(2).getCardArray().get(i));
            }
            boolean isDoubleTrips = false;
            // {fullhouse -> trips + trips}
            for (int i = 3; i < cardArrayArrayList.get(2).getCardArray().size(); i++) {
                isDoubleTrips = true;
                fiveCard.add(cardArrayArrayList.get(2).getCardArray().get(i));
                if (fiveCard.size() == 5) {
                    break;
                }
            }
            boolean isFullHouse = false;
            // {full house -> trips + pair}
            if (!isDoubleTrips) {
                for (int i = 0; i < cardArrayArrayList.get(1).getCardArray().size(); i++) {
                    isFullHouse = true;
                    fiveCard.add(cardArrayArrayList.get(1).getCardArray().get(i));
                    if (fiveCard.size() == 5) {
                        break;
                    }
                }
            }
            // {trips}
            if (!isFullHouse) {
                if (fiveCard.size() != 5) {
                    ArrayList<Card> sortedCards = sortArrayListWithout(allCards, fiveCard);
                    fiveCard.add(sortedCards.get(0));
                    fiveCard.add(sortedCards.get(1));
                }

            }
            return fiveCard;
        } else if (handRanking.equals("two pair") || handRanking.equals("one pair")) {
            ArrayList<Card> fiveCard = new ArrayList<Card>();
            for (int i = 0; i < cardArrayArrayList.get(1).getCardArray().size(); i++) {
                fiveCard.add(cardArrayArrayList.get(1).getCardArray().get(i));
                if (fiveCard.size() == 4) {
                    break;
                }
            }
            ArrayList<Card> sortedCards = sortArrayListWithout(allCards, fiveCard);
            int fiveCardCurrentSize = fiveCard.size();
            for (int i = 0; i < 5 - fiveCardCurrentSize; i++) {
                fiveCard.add(sortedCards.get(i));
            }
            return fiveCard;
        } else {
            ArrayList<Card> fiveCard = new ArrayList<Card>();
            ArrayList<Card> sortedCards = sortArrayListWithout(allCards, new ArrayList<Card>());
            for (int i = 0; i < 5; i++) {
                fiveCard.add(sortedCards.get(i));
            }
            return fiveCard;
        }
    }

    public String returnHandRanking() {
        if (cardPairArray[3] == 1) {
            return "quads";
        } else if (cardPairArray[2] >= 1) {
            if (cardPairArray[1] >= 1 || cardPairArray[2] >= 2) {
                return "full house";
            } else {
                return "trips";
            }
        } else if (cardPairArray[1] >= 2) {
            return "two pair";
        } else if (cardPairArray[1] >= 1) {
            return "one pair";
        } else {
            return "no pair";
        }
    }

    public int[] getCardPairArray() {
        return cardPairArray;
    }

    private ArrayList<Card> sortArrayListWithout(ArrayList<Card> toBeSorted, ArrayList<Card> withoutCards)
            throws Exception {
        for (Card c : withoutCards) {
            toBeSorted.remove(c);
        }

        char[] values = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
        

        int n = toBeSorted.size();
        Card temp = new Card("2c");
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                int indexFirstCard = new String(values).indexOf(toBeSorted.get(j - 1).getValue());
                int indexSecondCard = new String(values).indexOf(toBeSorted.get(j).getValue());

                if (indexFirstCard < indexSecondCard) {
                    temp = toBeSorted.get(j - 1);
                    toBeSorted.set(j - 1, toBeSorted.get(j));
                    toBeSorted.set(j, temp);
                }

            }

        }
        return toBeSorted;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public void setAllCards(ArrayList<Card> allCards) {
        this.allCards = allCards;
    }

    public ArrayList<CardArray> getCardArrayArrayList() {
        return cardArrayArrayList;
    }

    public void setCardArrayArrayList(ArrayList<CardArray> cardArrayArrayList) {
        this.cardArrayArrayList = cardArrayArrayList;
    }

    public void setCardPairArray(int[] cardPairArray) {
        this.cardPairArray = cardPairArray;
    }
}
