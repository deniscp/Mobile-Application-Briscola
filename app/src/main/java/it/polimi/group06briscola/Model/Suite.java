package it.polimi.group06briscola.Model;

/**
 * @author Timo Zandonella
 */
public enum Suite {
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

