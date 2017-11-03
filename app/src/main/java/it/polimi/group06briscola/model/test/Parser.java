package it.polimi.group06briscola.model.test;

import java.util.PriorityQueue;

import it.polimi.group06briscola.model.Card;
import it.polimi.group06briscola.model.DeckOfCards;
import it.polimi.group06briscola.model.Suit;

/**
 * Created by denis on 31/10/17.
 */

public class Parser {

    public static void main(String[] argv){

        String test0 = "0B5S4G6S2C5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B..JCKG2B.1CKS3G..";

        Parser parser = new Parser(test0);

//        System.out.println("Print: " + new Parser().parseToCard("7G").toString());
//        System.out.println("Print deck: " + new Parser().parseToDeck("JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS"));
//        System.out.println("Print deck: "+ Parser.parseToDeck("JBHBKB1C2C3C4C5C6C7CJCHCKC1GKB"));
    }

    private String startingPlayer;
    private String trumpSuit;
    private String deck;
    private String trumpCard;
    private String cardOnSurface;
    private String cardInHandP0;
    private String cardInHandP1;
    private String pileP0;
    private String pileP1;

    public Parser(String configuration) {
        int i,j;
        this.startingPlayer = configuration.substring(0, 1);
        this.trumpSuit = configuration.substring(1, 2);

        for (i = 2; configuration.charAt(i) != '.'; i++) ;
        this.deck = configuration.substring(2,i);

        System.out.println("First i: "+i);
        System.out.println("Deck: '"+this.deck+"'");


        for(j=++i; configuration.charAt(i) != '.' && i<configuration.length(); i++);
        System.out.println("New i: "+i);
        this.cardOnSurface = configuration.substring(j,i);
        System.out.println("Card on surface: '"+this.cardOnSurface+"'");

        for(j=++i; configuration.charAt(i) != '.' && i<configuration.length(); i++);
        System.out.println("New i: "+i);
        this.cardInHandP0 = configuration.substring(j,i);
        System.out.println("Card in hand of Player0: '"+this.cardInHandP0+"'");

        for(j=++i; configuration.charAt(i) != '.' && i<configuration.length(); i++);
        System.out.println("New i: "+i);
        this.cardInHandP1 = configuration.substring(j,i);
        System.out.println("Card in hand of Player1: '"+this.cardInHandP1+"'");

        for(j=++i; configuration.charAt(i) != '.' && i<configuration.length(); i++);
        System.out.println("New i: "+i);
        this.pileP0 = configuration.substring(j,i);
        System.out.println("Pile of Player0: '"+this.pileP0+"'");

        j=++i;
        System.out.println("New i: "+i);
        this.pileP1 = configuration.substring(j,configuration.length());
        System.out.println("Pile of Player1: '"+this.pileP1+"'");



        System.out.println("Starting player: "+this.startingPlayer);
        System.out.println("Trump suit: "+this.trumpSuit);


    }



    static public DeckOfCards parseToDeck(String stringDeck) {
        DeckOfCards deck = new DeckOfCards();
        int size = stringDeck.length();
        if (size % 2 != 0)
            throw new IllegalArgumentException("Configuration string is odd");
        for (int i = 0; i < size; i += 2)
            deck.addCard(parseToCard(stringDeck.substring(i, i + 2)));
        return deck;
    }


    /**
     * Takes a String, return an object card from a String
     * @param stringCard string representing a card, e.g. "1C","KG", "7B"
     * @return new Card object represented by the input string
     */
    static public Card parseToCard(String stringCard){
        if(stringCard.length()!=2)
            throw new IllegalArgumentException("Illegal string card parameter: length not acceptable");

        int value;
        switch (stringCard.substring(0,1)){
            case "K":
                value = 10;
                break;
            case "H":
                value = 9;
                break;
            case "J":
                value = 8;
                break;
            case "7":
                value = 7;
                break;
            case "6":
                value = 6;
                break;
            case "5":
                value = 5;
                break;
            case "4":
                value = 4;
                break;
            case "3":
                value = 3;
                break;
            case "2":
                value = 2;
                break;
            case "1":
                value = 1;
                break;
            default:
                throw new IllegalArgumentException("Illegal string card parameter: value substring not acceptable");
        }

        Suit suit;

        switch (stringCard.substring(1)){
            case "B":
                suit = Suit.Batons;
                break;
            case "C":
                suit = Suit.Cups;
                break;
            case "G":
                suit=Suit.Golds;
                break;
            case "S":
                suit=Suit.Swords;
                break;
            default:
                throw new IllegalArgumentException("Illegal string card parameter: suit substring not acceptable");
        }
        return new Card(value, suit);
    }
}
