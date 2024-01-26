package com.example.demo.model;

public class Card {
    private char suit;
    private char value;

    public Card(char s, char v){
        suit = s;
        value = v;
    }

    public Card(String c){
        value = c.charAt(0);
        suit = c.charAt(1);
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



    
}
