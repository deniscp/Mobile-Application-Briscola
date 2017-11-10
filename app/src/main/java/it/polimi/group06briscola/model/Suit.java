package it.polimi.group06briscola.model;

/**
 * This class holds the 4 suits Batons, Sowrds, Cups and Golds and is used in the Card class.
 * @author Timo Zandonella
 */
public enum Suit {
    Batons,
    Swords,
    Cups,
    Golds;

    @Override
    public String toString() {
        switch(this) {
            case Batons: return "B";
            case Swords: return "S";
            case Cups: return "C";
            case Golds: return "G";
            default: throw new IllegalArgumentException();
        }
    }
}

