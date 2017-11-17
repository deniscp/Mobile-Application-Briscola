package it.polimi.group06.moveTest;

import java.util.ArrayList;

import it.polimi.group06.domain.Card;
import it.polimi.group06.domain.Suit;

/**
 * Created by denis on 31/10/17.
 */

class Parser {

    static void main(String[] argv){

        String test0 = "0BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B..1B3B5B.2B4B6B..";
        String test1 = "0B5S4G6S2C5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B.KS.JCKG2B.1C3G..";
        String test2 = "1B5GKB7B6CHCHB1GKC5C4B1BHG7C6BJS6G7G4C3C7SJBHS2S3S4S1S2G3BJG5B.KG.4G6S.KS5S2C.3G2B.JC1C";
        String test3 = "0B..1B3B5B.2B4B6B..JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B";
        String test4 = "0B.....JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B1B3B5B2B4B6B";
        String test5 = "1B.5B..6B.4B3B.JBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS7B7BKS2B1B"; // 42 cards!
        String test6 = "0BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKSKS7B..1B3B5B.2B4B6B..";   // 41 cards!

//        Parser parser = new Parser(test0);
        Parser parser = new Parser(test6);

        System.out.println(parser.toString());

//        Parser parser1= new Parser(test1);
//        System.out.println("Print: " + parser1.parseToCard("7G").toString());
    }

    private String currentPlayer;
    private String trumpS;
    private String deckString;
    private String trumpCard;
    private String cardOnSurface;
    private String cardInHandP0;
    private String cardInHandP1;
    private String pileP0;
    private String pileP1;

    public Parser(String configuration) {
        int i,j;
        this.currentPlayer = configuration.substring(0, 1);
        this.trumpS = configuration.substring(1, 2);

        for (i = 2; configuration.charAt(i) != '.'; i++) ;
        this.deckString = configuration.substring(2,i);

        for(j=++i; configuration.charAt(i) != '.' && i<configuration.length(); i++);
        this.cardOnSurface = configuration.substring(j,i);

        for(j=++i; configuration.charAt(i) != '.' && i<configuration.length(); i++);
        this.cardInHandP0 = configuration.substring(j,i);

        for(j=++i; configuration.charAt(i) != '.' && i<configuration.length(); i++);
        this.cardInHandP1 = configuration.substring(j,i);

        for(j=++i; configuration.charAt(i) != '.' && i<configuration.length(); i++);
        this.pileP0 = configuration.substring(j,i);

        j=++i;
        this.pileP1 = configuration.substring(j,configuration.length());


//        System.out.println("Current player: "+this.currentPlayer);
//        System.out.println("Trump suit: "+this.trumpS);
//        System.out.println("Deck: '"+this.deckString +"'");
//        System.out.println("Card on surface: '"+this.cardOnSurface+"'");
//        System.out.println("Card in hand of Player0: '"+this.cardInHandP0+"'");
//        System.out.println("Card in hand of Player1: '"+this.cardInHandP1+"'");
//        System.out.println("Pile of Player0: '"+this.pileP0+"'");
//        System.out.println("Pile of Player1: '"+this.pileP1+"'");
    }

    public int startingPlayer(){
        if(cardOnSurface.length() /2 == 1) //if one card is already on the table
            return (currentPlayer() + 1) %2 ;
        else
            return currentPlayer();
    }

    public int currentPlayer(){
        return Integer.parseInt(this.currentPlayer);
    }

    public Suit trumpSuit(){
        switch (trumpS){
            case "B":
                return Suit.Batons;
            case "C":
                return Suit.Cups;
            case "G":
                return Suit.Golds;
            case "S":
                return Suit.Swords;
            default:
                throw new IllegalArgumentException("Illegal string card parameter: briscola suit not acceptable");
        }
    }

    public ArrayList<Card> deck() {
        String temp;
        if (deckString.length() >= 2)
            temp = deckString.substring(0, deckString.length() - 2);
        else
            temp = "";
        return parseCards(temp);
    }

    public Card briscola(){
        if(deckString.length()>=2)
            return parseToCard(deckString.substring(deckString.length()-2));
        else
            return null;
    }

    public ArrayList<Card> surface(){
        return parseCards(cardOnSurface);
    }

    public ArrayList<Card>[] hands(){
        ArrayList<Card>[] hand = new ArrayList[2];
        hand[0] = parseCards(cardInHandP0);
        hand[1] = parseCards(cardInHandP1);
        return hand;
    }


    public ArrayList<Card>[] piles(){
        ArrayList<Card>[] pile = new ArrayList[2];
        pile[0] = parseCards(pileP0);
        pile[1] = parseCards(pileP1);
        return pile;
    }

    public int round(){
        return (this.pileP0.length()/2 + this.pileP1.length()/2)/2 +1;
    }


    private ArrayList<Card> parseCards(String cardString){
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < cardString.length(); i += 2)
            cards.add(parseToCard(cardString.substring(i, i + 2)));
        return cards;
    }

    /**
     * Takes a String, return an object card from a String
     * @param stringCard string representing a card, e.g. "1C","KG", "7B"
     * @return new Card object represented by the input string
     */
    private Card parseToCard(String stringCard){
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
                suit = Suit.Golds;
                break;
            case "S":
                suit = Suit.Swords;
                break;
            default:
                throw new IllegalArgumentException("Illegal string card parameter: suit substring not acceptable");
        }
        return new Card(value, suit);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Round : " + this.round());
        stringBuilder.append("\nStarting player : " + this.startingPlayer());
        stringBuilder.append("\nTrump suit : " + this.trumpSuit());
        stringBuilder.append("\nDeck : " + this.deck());
        stringBuilder.append("\nBriscola : " + this.briscola());
        stringBuilder.append("\nCards on surface : " + this.surface());
        stringBuilder.append("\nCards in hand of player0 : " + this.hands()[0]);
        stringBuilder.append("\nCards in hand of player1 : " + this.hands()[1]);
        stringBuilder.append("\nPile of player0 : " + this.piles()[0] + " (" + this.piles()[0].size() + " cards)");
        stringBuilder.append("\nPile of player1 : " + this.piles()[1] + " (" + this.piles()[1].size() + " cards)");
        return stringBuilder.toString();
    }
}