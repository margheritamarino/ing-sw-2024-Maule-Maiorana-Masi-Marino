package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.PlayableCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

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
    void testInitializeDeck() throws FileNotFoundException, FileReadException {
        // Verifica che il mazzo sia inizializzato correttamente
//        Assertions.assertEquals(40, deckGold.getNumCards());
//        Assertions.assertEquals(40, deckResource.getNumCards());
//        Assertions.assertEquals(6, deckInitial.getNumCards());
//        Assertions.assertNotNull(deckGold.getFrontCards());
//        Assertions.assertNotNull(deckGold.getBackCards());
//        Assertions.assertNotNull(deckResource.getFrontCards());
//        Assertions.assertNotNull(deckResource.getBackCards());
//        Assertions.assertNotNull(deckInitial.getFrontCards());
//        Assertions.assertNotNull(deckInitial.getBackCards());
        // Creazione di un oggetto Deck
        Deck deck = new Deck(CardType.InitialCard);

            // Inizializzazione del mazzo con le carte di tipo InitialCard
            deck.initializeDeck(CardType.InitialCard);

            // Verifica che il mazzo contenga solo carte di tipo InitialCard
            List<PlayableCard> frontCards = deck.getFrontCards();
            List<PlayableCard> backCards = deck.getBackCards();
            for (PlayableCard card : frontCards) {
                Assertions.assertEquals(CardType.InitialCard, card.getCardType());
            }
            for (PlayableCard card : backCards) {
                Assertions.assertEquals(CardType.InitialCard, card.getCardType());
            }

    }

    @Test
    void testReturnCard() throws DeckEmptyException {
        // Verifica che le carte vengano estratte correttamente
        PlayableCard[] cardsGold = deckGold.returnCard();
        PlayableCard[] cardsResource = deckResource.returnCard();
        PlayableCard[] cardsInitial = deckInitial.returnCard();

        Assertions.assertNotNull(cardsGold);
        Assertions.assertNotNull(cardsResource);
        Assertions.assertNotNull(cardsInitial);
        Assertions.assertEquals(39, deckGold.getNumCards());
        Assertions.assertEquals(39, deckResource.getNumCards());
        Assertions.assertEquals(5, deckInitial.getNumCards());
    }

    @Test
    void testReturnCardDeckEmptyException() {
        // Scarica il mazzo Gold e verifica che venga lanciata l'eccezione DeckEmptyException
        while (!deckGold.checkEndDeck()) {
            try {
                deckGold.returnCard();
            } catch (DeckEmptyException e) {
                Assertions.fail("DeckEmptyException should not be thrown before the deck is empty.");
            }
        }

        assertThrows(DeckEmptyException.class, () -> deckGold.returnCard());
    }

    @Test
    void testCheckEndDeck() {
        // Verifica che il mazzo finisca correttamente
        Assertions.assertFalse(deckGold.checkEndDeck());
        Assertions.assertFalse(deckResource.checkEndDeck());
        Assertions.assertFalse(deckInitial.checkEndDeck());

        // Estrai tutte le carte dal mazzo Gold
        while (!deckGold.checkEndDeck()) {
            try {
                deckGold.returnCard();
            } catch (DeckEmptyException e) {
                Assertions.fail("DeckEmptyException should not be thrown before the deck is empty.");
            }
        }
        Assertions.assertTrue(deckGold.checkEndDeck());
    }
}
