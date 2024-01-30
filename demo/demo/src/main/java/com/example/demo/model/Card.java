package com.example.demo.model;

public class Card {
    private char suit;
    private char value;
    static char[] values = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};

    public Card(char s, char v){
        suit = s;
        value = v;
    }

    public Card(String c){
        value = c.charAt(0);
        suit = c.charAt(1);
    }

    public Card(char suitIndex, int valueIndex) throws Exception {
        // ADDED
        try {
            this.suit = suitIndex;
            this.value = values[valueIndex];
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("Not a Real Card");
        }

    }

    public char getValue() {
        return value;
    }

    public char getSuit() {
        return suit;
    }


    public void setValue(char v){
        value = v;
    }

    public void setSuit(char s) {
        suit = s;
    }

    public boolean isSameSuit(Card otherCard) {
        return this.getSuit() == otherCard.getSuit();
    }



    public int getCardValueDifference(Card otherCard) {
        return this.getCardVal() - otherCard.getCardVal();
    }

    public int getCardVal() {
        if (value == 'A'){
            return 14;
        }
        else if (value == 'K'){
            return 13;
        }
        else if (value == 'Q'){
            return 12;
        }
        else if (value == 'J'){
            return 11;
        }
        else if (value == 'T'){
            return 10;
        }
        else {
            return value - '0';
        }
    }


    public String toString() {
        return ("" + value + suit);
    }

    public boolean equals(Card otherCard) {
        return this.isSameSuit(otherCard) && this.getCardValueDifference(otherCard) == 0;
    }

    
}
