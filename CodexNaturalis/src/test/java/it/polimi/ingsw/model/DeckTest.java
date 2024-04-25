package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.PlayableCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertThrows;

class DeckTest {

    private Deck deckGold;
    private Deck deckResource;
    private Deck deckInitial;

    @BeforeEach
    void setup() throws FileNotFoundException, FileReadException {
        deckGold = new Deck(CardType.GoldCard);
        deckResource = new Deck(CardType.ResourceCard);
        deckInitial = new Deck(CardType.InitialCard);
    }

    @Test
    void testInitializeDeck() {
        // Verifica che il mazzo sia inizializzato correttamente
        assertEquals(40, deckGold.getNumCards());
        assertEquals(40, deckResource.getNumCards());
        assertEquals(6, deckInitial.getNumCards());
        assertNotNull(deckGold.getFrontCards());
        assertNotNull(deckGold.getBackCards());
        assertNotNull(deckResource.getFrontCards());
        assertNotNull(deckResource.getBackCards());
        assertNotNull(deckInitial.getFrontCards());
        assertNotNull(deckInitial.getBackCards());
    }

    @Test
    void testReturnCard() throws DeckEmptyException {
        // Verifica che le carte vengano estratte correttamente
        PlayableCard[] cardsGold = deckGold.returnCard();
        PlayableCard[] cardsResource = deckResource.returnCard();
        PlayableCard[] cardsInitial = deckInitial.returnCard();

        assertNotNull(cardsGold);
        assertNotNull(cardsResource);
        assertNotNull(cardsInitial);
        assertEquals(39, deckGold.getNumCards());
        assertEquals(39, deckResource.getNumCards());
        assertEquals(5, deckInitial.getNumCards());
    }

    @Test
    void testReturnCardDeckEmptyException() {
        // Scarica il mazzo Gold e verifica che venga lanciata l'eccezione DeckEmptyException
        while (!deckGold.checkEndDeck()) {
            try {
                deckGold.returnCard();
            } catch (DeckEmptyException e) {
                fail("DeckEmptyException should not be thrown before the deck is empty.");
            }
        }

        assertThrows(DeckEmptyException.class, () -> {
            deckGold.returnCard();
        });
    }

    @Test
    void testCheckEndDeck() {
        // Verifica che il mazzo finisca correttamente
        assertFalse(deckGold.checkEndDeck());
        assertFalse(deckResource.checkEndDeck());
        assertFalse(deckInitial.checkEndDeck());

        // Estrai tutte le carte dal mazzo Gold
        while (!deckGold.checkEndDeck()) {
            try {
                deckGold.returnCard();
            } catch (DeckEmptyException e) {
                fail("DeckEmptyException should not be thrown before the deck is empty.");
            }
        }
        assertTrue(deckGold.checkEndDeck());
    }
}
