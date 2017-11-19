package it.polimi.group06.domain;

/**
 * This class holds all information about a card - suit, rank, point and value. For a comparison between the 3 variables value, rank and point, please see the explanation/card.png
 * Value: The value of a card that is used for the configurations.
 * Suit: The suit of a card (can be Batons, Swords, Cups, Golds).
 * Rank: The Rank is the order of a card, meaning the card with rank 1 (Ace) is > rank 2 (Card 3) > rank 3 (King) and so on. Rank exists for comparing whose card has one a round.
 * Point: The amount of points a card is worth when calculating the winning player at the end of a game.
 * @author Timo Zandonella
 */
public class Card {

    private int value;
    private Suit suit;
    private int rank;
    private int point;

    /**
     * Constructor for a Card only with the value and the suit of a card. The other two Card attributes can get derived from the Value
     * @param value
     * @param suit
     */
    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.rank = this.setRank(value);
        this.point = this.setPoints(value);
    }

    public Suit getSuit() { return suit; }

    public int getRank() { return rank; }

    public int getPoint() { return point; }

    public int getValue() { return value; }

    /**
     * This method converts the value of a card to a rank.
     * See explanation/card.png for more information
     * @param value
     * @return rank
     */
    public int setRank(int value){
        switch(value){
            case 1: return 1; //A
            case 2: return 10;
            case 3: return 2; //3
            case 4: return 9;
            case 5: return 8;
            case 6: return 7;
            case 7: return 6;
            case 8: return 5; //J
            case 9: return 4; //H
            case 10: return 3; //K
            default: throw new IllegalArgumentException();
        }
    }

    /**
     * This method converts the value of a card to the corresponding points. Only the top 5 ranked cards, so cards with value 1,3,8,9 and 10 get points.
     * See explanation/card.png for more information
     * @param value
     * @return points
     */
    public int setPoints(int value){
        switch(value){
            case 1: return 11;
            case 2: return 0;
            case 3: return 10;
            case 4: return 0;
            case 5: return 0;
            case 6: return 0;
            case 7: return 0;
            case 8: return 2;
            case 9: return 3;
            case 10: return 4;
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        switch(value) {
            case 1: return "1" + suit;
            case 2: return "2" + suit;
            case 3: return "3" + suit;
            case 4: return "4" + suit;
            case 5: return "5" + suit;
            case 6: return "6" + suit;
            case 7: return "7" + suit;
            case 8: return "J" + suit;
            case 9: return "H" + suit;
            case 10: return "K" + suit;

            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null) return false;
        if (otherObject == this) return true;
        if (!(otherObject instanceof Card))return false;
        Card otherCard = (Card) otherObject;

        if (this.value == otherCard.value && this.suit == otherCard.suit)
            return true;
        else
            return false;
    }
}