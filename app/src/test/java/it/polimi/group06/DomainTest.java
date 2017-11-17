package it.polimi.group06;

import org.junit.Test;

import it.polimi.group06.domain.DeckOfCards;

import static org.junit.Assert.assertEquals;

/**
 * Created by Timo Zandonella on 17.11.2017.
 */

public class DomainTest {

    @Test
    public void DeckOfCardsTest() throws Exception{
        String sorteddeck = "1B2B3B4B5B6B7BJBHBKB1C2C3C4C5C6C7CJCHCKC1G2G3G4G5G6G7GJGHGKG1S2S3S4S5S6S7SJSHSKS";
        assertEquals(sorteddeck, new DeckOfCards(false).toString());
    }

    @Test
    public void Card() throws Exception{
        String threeofbatons = "3B";
        
    }
}
