package it.polimi.group06.domain;

import java.util.ArrayList;

import static it.polimi.group06.domain.Constants.FIRSTPLAYER;
import static it.polimi.group06.domain.Constants.NUMBEROFPLAYERS;
import static it.polimi.group06.domain.Constants.SECONDPLAYER;

/**
 * Class used to parse a configuration from a String, it needs to be instantiated to be used.
 *
 * @author denis
 */

public class Parser {

    private String currentPlayer;
    private String briscolaSuit;
    private String deckString;
    private String cardOnSurface;
    private String cardInHandP0;
    private String cardInHandP1;
    private String pileP0;
    private String pileP1;

    public Parser(String configuration) {
        int i,j;
        this.currentPlayer = configuration.substring(0, 1);
        this.briscolaSuit = configuration.substring(1, 2);

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
        this.pileP1 = configuration.substring(j, configuration.length());
    }

    public int startingPlayer(){
        if(cardOnSurface.length() /2 == 1) //if one card is already on the table
            return (currentPlayer() + 1) % NUMBEROFPLAYERS ;
        else
            return currentPlayer();
    }

    public int currentPlayer(){
        return Integer.parseInt(this.currentPlayer);
    }

    public Suit briscolaSuit(){
        switch (briscolaSuit){
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
        ArrayList<Card>[] hand = new ArrayList[NUMBEROFPLAYERS];
        hand[FIRSTPLAYER] = parseCards(cardInHandP0);
        hand[SECONDPLAYER] = parseCards(cardInHandP1);
        return hand;
    }


    public ArrayList<Card>[] piles(){
        ArrayList<Card>[] pile = new ArrayList[NUMBEROFPLAYERS];
        pile[FIRSTPLAYER] = parseCards(pileP0);
        pile[SECONDPLAYER] = parseCards(pileP1);
        return pile;
    }

    public int round(){
        return ( this.pileP0.length()/2 + this.pileP1.length()/2 )  /2  +1;
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
        stringBuilder.append("\nBriscola suit : " + this.briscolaSuit());
        stringBuilder.append("\nDeck : " + this.deck());
        stringBuilder.append("\nBriscola : " + this.briscola());
        stringBuilder.append("\nCards on surface : " + this.surface());
        stringBuilder.append("\nCards in hand of player0 : " + this.hands()[FIRSTPLAYER]);
        stringBuilder.append("\nCards in hand of player1 : " + this.hands()[SECONDPLAYER]);
        stringBuilder.append("\nPile of player0 : " + this.piles()[FIRSTPLAYER] + " (" + this.piles()[FIRSTPLAYER].size() + " cards)");
        stringBuilder.append("\nPile of player1 : " + this.piles()[SECONDPLAYER] + " (" + this.piles()[SECONDPLAYER].size() + " cards)");
        return stringBuilder.toString();
    }
}