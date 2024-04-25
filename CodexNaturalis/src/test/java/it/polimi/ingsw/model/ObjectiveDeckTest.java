package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

public class ObjectiveDeckTest {

    private ObjectiveDeck objectiveDeck;

    @Before
    public void setUp() throws FileReadException, FileNotFoundException {
        objectiveDeck = new ObjectiveDeck();
    }

    @Test
    public void testInitializeDeck()  {
        // Assicurati che il mazzo sia stato inizializzato correttamente
        assertNotNull(objectiveDeck.getFrontCards());
        assertFalse(objectiveDeck.getFrontCards().isEmpty());
    }

    @Test
    public void testCheckEndDeck() throws DeckEmptyException {
        // Inizialmente, il mazzo non dovrebbe essere vuoto
        assertFalse(objectiveDeck.checkEndDeck());

        // Prendi tutte le carte dal mazzo per svuotarlo
        while (!objectiveDeck.checkEndDeck()) {
            objectiveDeck.returnCard();
        }

        // Ora il mazzo dovrebbe essere vuoto
        assertTrue(objectiveDeck.checkEndDeck());
    }
    @Test
    public void testReturnCardDeckEmptyException() {
        // Scarica il mazzo Gold e verifica che venga lanciata l'eccezione DeckEmptyException
        while (!objectiveDeck.checkEndDeck()) {
            try {
                objectiveDeck.returnCard();
            } catch (DeckEmptyException e) {
                Assertions.fail("DeckEmptyException should not be thrown before the deck is empty.");
            }
        }

        Assertions.assertThrows(DeckEmptyException.class, () -> objectiveDeck.returnCard());
    }


    @Test
    public void testReturnCard() throws DeckEmptyException {
        // Controlla che il metodo returnCard restituisca una carta valida
        ObjectiveCard card = objectiveDeck.returnCard();
        assertNotNull(card);
        assertFalse(objectiveDeck.getFrontCards().contains(card));

        // Il numero di carte dovrebbe diminuire di 1
        int initialCardCount = objectiveDeck.getNumCards();
        objectiveDeck.returnCard();
        assertEquals(initialCardCount - 1, objectiveDeck.getNumCards());
    }

}
