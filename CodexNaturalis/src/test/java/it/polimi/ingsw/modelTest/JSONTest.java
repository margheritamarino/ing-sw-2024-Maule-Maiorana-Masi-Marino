package it.polimi.ingsw.modelTest;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.exceptions.FileReadException;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class JSONTest {

    @Test
    public void testInitializeDeck() {
        try {
            Deck deck = new Deck(CardType.GoldCard);

            // Verifica che il deck non sia nullo
            assertNotNull(deck);

            // Verifica che le liste di carte frontali e posteriori non siano vuote
            assertFalse(deck.getFrontCards().isEmpty());
            assertFalse(deck.getBackCards().isEmpty());

            // Puoi eseguire ulteriori test per verificare che le carte siano caricate correttamente
            // Ad esempio, controlla se il numero di carte corrisponde a quello atteso

        } catch (FileNotFoundException | FileReadException e) {
            fail("Eccezione non attesa durante l'inizializzazione del deck: " + e.getMessage());
        }
    }

    @Test
    public void testNumberOfCardsInDeck() {
        try {
            Deck deck = new Deck(CardType.GoldCard);

            // Controlla se il numero di carte nel mazzo corrisponde a quello atteso
            int expectedNumberOfCards = 40; // Numero di carte atteso per le GoldCard (puoi modificare questo valore se necessario)
            assertEquals(expectedNumberOfCards, deck.getFrontCards().size());
            assertEquals(expectedNumberOfCards, deck.getBackCards().size());

        } catch (FileNotFoundException | FileReadException e) {
            fail("Eccezione non attesa durante l'inizializzazione del deck: " + e.getMessage());
        }
    }

}

