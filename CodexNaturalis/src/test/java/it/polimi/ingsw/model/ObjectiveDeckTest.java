package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;

public class ObjectiveDeckTest {

    private ObjectiveDeck objectiveDeck;

    @Before
    public void setUp() throws FileReadException, FileNotFoundException {
        objectiveDeck = new ObjectiveDeck();
    }

    @Test
    public void testInitializeDeck() throws FileReadException, FileNotFoundException {
        // Assicurati che il mazzo sia stato inizializzato correttamente
        assertNotNull(objectiveDeck.getFrontCards());
        assertFalse(objectiveDeck.getFrontCards().isEmpty());
    }

    @Test
    public void testCheckEndDeck() {
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
    public void testReturnCard() {
        // Controlla che il metodo returnCard restituisca una carta valida
        ObjectiveCard card = objectiveDeck.returnCard();
        assertNotNull(card);
        assertFalse(objectiveDeck.getFrontCards().contains(card));

        // Il numero di carte dovrebbe diminuire di 1
        int initialCardCount = objectiveDeck.getNumCards();
        objectiveDeck.returnCard();
        assertEquals(initialCardCount - 1, objectiveDeck.getNumCards());
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileNotFoundException() throws FileReadException, FileNotFoundException {
        // Se il file non viene trovato, dovrebbe essere lanciata FileNotFoundException
        new ObjectiveDeck(); // In questo caso, assicurati che il file non esista per lanciare l'eccezione
    }

    @Test(expected = FileReadException.class)
    public void testFileReadException() throws FileReadException, FileNotFoundException {
        // Se ci sono problemi di lettura del file, dovrebbe essere lanciata FileReadException
        new ObjectiveDeck(); // Assicurati che ci sia un problema di lettura del file
    }
}
