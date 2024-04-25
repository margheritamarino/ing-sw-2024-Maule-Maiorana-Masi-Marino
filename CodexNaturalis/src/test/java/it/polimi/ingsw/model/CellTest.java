package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.PlayableCard;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class CellTest {

    private Cell cell;
    private Board board;
    private PlayableCard[] playableCard; // Crea un oggetto PlayableCard per testare l'aggiornamento

    @Before
    public void setUp() throws FileNotFoundException, FileReadException, DeckEmptyException {
        board = new Board();
        cell = new Cell(0, 0);
        playableCard = board.getGoldCards().get(0);
    }

    @Test
    public void testConstructor() {
        // Verifica i valori iniziali impostati dal costruttore
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getColumn());
        assertFalse(cell.isAvailable());
        assertTrue(cell.isWall());
        assertNull(cell.getCard());
    }

    @Test
    public void testGetterAndSetter() {
        // Testa il metodo getter e setter per `row`
        cell.setRow(2);
        assertEquals(2, cell.getRow());

        // Testa il metodo getter e setter per `column`
        cell.setColumn(3);
        assertEquals(3, cell.getColumn());

        // Testa il metodo getter e setter per `available`
        cell.setAvailable(true);
        assertTrue(cell.isAvailable());

        // Testa il metodo getter e setter per `wall`
        cell.setWall(false);
        assertFalse(cell.isWall());

        // Testa il metodo getter e setter per `cardPointer`
        cell.setCardPointer(playableCard[0]);
        assertEquals(playableCard[0], cell.getCard());
    }

    @Test
    public void testUpdateCell() {
        // Testa il metodo `updateCell` con un oggetto PlayableCard
        cell.updateCell(playableCard[0]);
        assertFalse(cell.isAvailable());
        assertEquals(playableCard[0], cell.getCard());
    }

    @Test
    public void testSize() {
        // Testa il metodo `size` con valori di input diversi
        int size = cell.size(3, 4);
        assertEquals(12, size); // 3 * 4 = 12
    }
}
