package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
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
        // pesca dal mazzo
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
        assertEquals(2, goldCards.length, "The length of the drawn gold cards should be 2");
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

/*

    @Test
    public void testTakeCardFromBoard() throws DeckEmptyException {
        //pesca dal mazzo
        assertNotNull(board.takeCardfromBoard(CardType.GoldCard, true, 0));
        assertNotNull(board.takeCardfromBoard(CardType.ResourceCard, true, 0));

        //pesca dall'array
        PlayableCard[] cards = board.takeCardfromBoard(CardType.GoldCard, false, 0);
        assertNotNull(cards);
        assertEquals(2, cards.length);
        assertEquals(CardType.GoldCard, cards[0].getCardType());
        assertEquals(CardType.GoldCard, cards[1].getCardType());


        //posizione non valida nell'array
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.takeCardfromBoard(CardType.GoldCard, false, 5); // posizione non valida
        });

        //cardtype non valido
        assertThrows(IllegalArgumentException.class, () -> {
            board.takeCardfromBoard(CardType.InitialCard, true, 0); // Tipo di carta non valido
        });

    }

    @Test
    public void testVerifyCardsNumber() {
        assertTrue(board.verifyGoldCardsNumber());
        assertTrue(board.verifyObjectiveCardsNumber());
        assertTrue(board.verifyResourceCardsNumber());
    }

    @Test
    public void testVerifyDeckSize() {
        // Assumendo un certo numero di giocatori, verifico le dimensioni dei mazzi di carte
        int playersNumber = 3;
        // assertTrue(board.verifyGoldDeckSize(playersNumber));
        assertTrue(board.verifyResourceDeckSize(playersNumber));
      //  assertTrue(board.verifyObjectiveDeckSize(playersNumber));
    }
*/


    }
}
