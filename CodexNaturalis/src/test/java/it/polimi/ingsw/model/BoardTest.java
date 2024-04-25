package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
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
        try {
            board = new Board();
        } catch (FileNotFoundException| DeckEmptyException | FileReadException e) {
            e.printStackTrace(); // Gestisci l'eccezione in modo appropriato nel tuo codice reale
        }
    }

    @Test
    public void testInitialization() {
        assertNotNull(board);

        assertTrue(board.verifyGoldCardsNumber()); //2 GoldCards on the Board
        assertTrue(board.verifyResourceCardsNumber()); //2 ResourceCards on the Board
        assertTrue(board.verifyObjectiveCardsNumber()); //2 ObjectiveCards on the Board
    }

    @Test
    public void testTakeObjectiveCard() throws DeckEmptyException {
        assertNotNull(board.takeObjectiveCard());
    }

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
    public void testVerifyDeckSize() {
        // Assumendo un certo numero di giocatori, verifica le dimensioni dei mazzi di carte
        int playersNumber = 4; // Modifica questo valore a seconda del numero di giocatori

        assertTrue(board.verifyGoldDeckSize(playersNumber));
        assertTrue(board.verifyResourceDeckSize(playersNumber));
        assertTrue(board.verifyObjectiveDeckSize(playersNumber));
    }
}
