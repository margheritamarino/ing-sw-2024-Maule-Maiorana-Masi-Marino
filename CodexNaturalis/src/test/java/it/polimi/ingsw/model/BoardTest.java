package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.socket.client.GameListenersClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInitialization() {
        assertNotNull(board);

        assertTrue(board.verifyGoldCardsNumber(), "Number of gold cards on the board should be 2");
        assertTrue(board.verifyResourceCardsNumber(), "Number of resource cards on the board should be 2");
        assertTrue(board.verifyObjectiveCardsNumber(), "Number of objective cards on the board should be 2");
    }


    @Test
    public void testTakeObjectiveCard() {
        assertDoesNotThrow(() -> {
            ObjectiveCard card = board.takeObjectiveCard();
            assertNotNull(card, "The drawn objective card should not be null");
        });
    }

    @Test
    public void testTakeCardFromBoard() throws DeckEmptyException {
        // pesca dal mazzo del giocatore
        PlayableCard[] drawnGoldCard = null;
        PlayableCard[] drawnResourceCard = null;
        try {
            drawnGoldCard = board.takeCardfromBoard(CardType.GoldCard, true, 0);
            drawnResourceCard = board.takeCardfromBoard(CardType.ResourceCard, true, 0);

        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
        assertNotNull(drawnGoldCard, "The drawn gold card from the deck should not be null");
        assertNotNull(drawnResourceCard, "The drawn resource card from the deck should not be null");

        // pesca dall'array
        PlayableCard[] goldCards = null;
        PlayableCard[] resourceCards = null;
        try {
            goldCards = board.takeCardfromBoard(CardType.GoldCard, false, 0);
            resourceCards = board.takeCardfromBoard(CardType.ResourceCard, false, 0);
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
        assertNotNull(goldCards, "The drawn gold card from the board should not be null");
        assertNotNull(resourceCards, "The drawn resource card from the deck should not be null");
        assertEquals(2, goldCards.length, "The length of the drawn gold cards should be 2");
        assertEquals(2, resourceCards.length, "The length of the drawn gold cards should be 2");
        assertEquals(CardType.GoldCard, goldCards[0].getCardType(), "The type of the first drawn gold card should be GoldCard");
        assertEquals(CardType.GoldCard, goldCards[1].getCardType(), "The type of the second drawn gold card should be GoldCard");


        // posizione non valida nell'array
        try {
            board.takeCardfromBoard(CardType.GoldCard, false, 5); // posizione non valida
            fail("IndexOutOfBoundsException expected but not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }

        // cardtype non valido
        try {
            board.takeCardfromBoard(CardType.InitialCard, true, 0); // Tipo di carta non valido
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }
//Tutti i metodi 'verify---DeckSize li verifichi nel metodo di Game 'checkBoard' (serve avere tutto il gioco inizializzato per poter verificare i Deck)
@Test
public void testVerifyGoldCardsNumber() {
    assertTrue(board.verifyGoldCardsNumber(), "Number of gold cards on the board should be 2");
}

    @Test
    public void testVerifyResourceCardsNumber() {
        assertTrue(board.verifyResourceCardsNumber(), "Number of resource cards on the board should be 2");
    }

    @Test
    public void testVerifyObjectiveCardsNumber() {
        assertTrue(board.verifyObjectiveCardsNumber(), "Number of objective cards on the board should be 2");
    }

    @Test
    public void testToString() {
        String boardString = board.toString();
        assertNotNull(boardString, "toString method should not return null");
        assertFalse(boardString.isEmpty(), "toString method should not return an empty string");
    }

    @Test
    public void testCardsObjectiveToString() {
        String objectiveCardsString = board.cardsObjectiveToString();
        assertNotNull(objectiveCardsString, "cardsObjectiveToString method should not return null");
        assertFalse(objectiveCardsString.isEmpty(), "cardsObjectiveToString method should not return an empty string");
    }

    @Test
    public void testCardsGoldToString() {
        String goldCardsString = board.cardsGoldToString();
        assertNotNull(goldCardsString, "cardsGoldToString method should not return null");
        assertFalse(goldCardsString.isEmpty(), "cardsGoldToString method should not return an empty string");
    }

    @Test
    public void testCardsResourceToString() {
        String resourceCardsString = board.cardsResourceToString();
        assertNotNull(resourceCardsString, "cardsResourceToString method should not return null");
        assertFalse(resourceCardsString.isEmpty(), "cardsResourceToString method should not return an empty string");
    }

    @Test
    public void testCardsVisibleGoldToString() {
        String visibleGoldCardsString = board.cardsVisibleGoldToString();
        assertNotNull(visibleGoldCardsString, "cardsVisibleGoldToString method should not return null");
        assertFalse(visibleGoldCardsString.isEmpty(), "cardsVisibleGoldToString method should not return an empty string");
    }

    @Test
    public void testCardsVisibleResourceToString() {
        String visibleResourceCardsString = board.cardsVisibleResourceToString();
        assertNotNull(visibleResourceCardsString, "cardsVisibleResourceToString method should not return null");
        assertFalse(visibleResourceCardsString.isEmpty(), "cardsVisibleResourceToString method should not return an empty string");
    }



}
