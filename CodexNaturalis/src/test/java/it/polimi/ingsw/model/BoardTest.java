package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        try {
            board = new Board();
        } catch (FileNotFoundException | FileReadException e) {
            e.printStackTrace(); // Gestisci l'eccezione in modo appropriato nel tuo codice reale
        }
    }

    @Test
    public void testInitialization() {
        assertNotNull(board);

        assertTrue(board.verifyGoldCardsNumber());
        assertTrue(board.verifyResourceCardsNumber());
        assertTrue(board.verifyObjectiveCardsNumber());
    }

    @Test
    public void testTakeObjectiveCard() {
        assertNotNull(board.takeObjectiveCard());
    }

    @Test
    public void testTakeCardFromBoard() {
        assertNotNull(board.takeCardfromBoard(CardType.GoldCard, true, 0));
        assertNotNull(board.takeCardfromBoard(CardType.ResourceCard, true, 0));
    }

    @Test
    public void testVerifyDeckSize() {
        // Assumendo un certo numero di giocatori, verifica le dimensioni dei mazzi di carte
        int playersNumber = 4; // Modifica questo valore a seconda del numero di giocatori

        assertTrue(board.verifyGoldDeckSize(playersNumber));
        assertTrue(board.verifyResourceDeckSize(playersNumber));
        assertTrue(board.verifyObjectiveDeckSize(playersNumber));
    }
}
