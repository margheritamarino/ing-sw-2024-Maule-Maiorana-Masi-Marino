package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.GoldCard;
import it.polimi.ingsw.model.cards.ResourceCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void testBookConstructor() {
        // Definiamo il numero di righe e colonne per il libro
        int rows = 20;
        int columns = 20;

        // Creiamo un oggetto Book
        Book book = new Book(rows, columns);

        // Verifichiamo che le mappe di risorse e simboli siano inizializzate correttamente
        assertNotNull(book.getResourceMap());
        assertNotNull(book.getSymbolMap());

        // Verifichiamo che la matrice del libro sia stata inizializzata correttamente
        Cell[][] bookMatrix = book.getBookMatrix();
        assertEquals(rows, bookMatrix.length);
        assertEquals(columns, bookMatrix[0].length);

        // Verifichiamo che ogni cella della matrice sia stata inizializzata correttamente
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = bookMatrix[i][j];
                assertNotNull(cell);
                assertEquals(i, cell.getRow());
                assertEquals(j, cell.getColumn());
                assertFalse(cell.isAvailable());
            }
        }
    }

    @Test
    public void testDecreaseSymbol() {
        // Creiamo un oggetto Book
        Book book = new Book(4, 4);

        // Aggiungiamo alcuni simboli alla mappa dei simboli
        book.getSymbolMap().put(SymbolType.Quill, 3);
        book.getSymbolMap().put(SymbolType.Ink, 2);
        book.getSymbolMap().put(SymbolType.Manuscript, 1);

        // Chiamiamo il metodo decreaseSymbol per diminuire il numero di SYMBOL_A
        book.decreaseSymbol(SymbolType.Quill);
        book.decreaseSymbol(SymbolType.Manuscript);
        book.decreaseSymbol(SymbolType.Manuscript);

        // Verifichiamo che il numero di QUILL sia diminuito di uno
        assertEquals(2, book.getSymbolMap().get(SymbolType.Quill).intValue());

        // Verifichiamo che il numero di INK sia rimasto invariato
        assertEquals(2, book.getSymbolMap().get(SymbolType.Ink).intValue());

        // Verifichiamo che il numero di MANUSCRIPT non sia sceso sotto allo 0
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
    }

    @Test
    public void testIncreaseResource() {
        // Creiamo un oggetto Book
        Book book = new Book(4, 4);

        // Aggiungiamo alcuni tipi di risorse alla mappa delle risorse
        book.getResourceMap().put(ResourceType.Fungi, 3);
        book.getResourceMap().put(ResourceType.Animal, 2);

        // Chiamiamo il metodo increaseResource per incrementare il numero di Fungi
        book.increaseResource(ResourceType.Fungi);

        // Verifichiamo che il numero di Fungi sia aumentato di uno
        assertEquals(4, book.getResourceMap().get(ResourceType.Fungi).intValue());

        // Verifichiamo che il numero di Animal sia rimasto invariato
        assertEquals(2, book.getResourceMap().get(ResourceType.Animal).intValue());
    }

    @Test
    public void testClear() {
        // Creiamo un oggetto Book
        Book book = new Book(3, 4);

        // Creiamo alcuni oggetti Card da aggiungere al libro
        GoldCard card1 = new GoldCard();
        ResourceCard card2 = new ResourceCard();
        ResourceCard card3 = new ResourceCard();

        // Aggiungiamo le carte alle celle del libro
        book.getBookMatrix()[0][0].setCardPointer(card1);
        book.getBookMatrix()[1][1].setCardPointer(card2);
        book.getBookMatrix()[2][2].setCardPointer(card3);

        // Impostiamo alcune celle come non disponibili e alcune come muro
        book.getBookMatrix()[0][0].setAvailable(false);
        book.getBookMatrix()[1][1].setWall(true);

        // Chiamiamo il metodo clear per eliminare tutte le carte dal libro
        book.clear();

        // Verifichiamo che tutte le carte siano state rimosse correttamente
        for (int i = 0; i < book.getBookMatrix().length; i++) {
            for (int j = 0; j < book.getBookMatrix()[i].length; j++) {
                assertNull(book.getBookMatrix()[i][j].getCardPointer());
                assertTrue(book.getBookMatrix()[i][j].isAvailable());
                assertFalse(book.getBookMatrix()[i][j].isWall());
            }
        }
    }

    @Test
    public void testUpdateNewCardCorners() {
        // Creiamo un oggetto Book
        Book book = new Book(4, 4);

        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        ResourceCard card = new ResourceCard(5,3,true, CardType.ResourceCard,CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList,true,SymbolType.Ink);


        // Chiamiamo il metodo updateNewCardCorners per aggiornare le risorse e i simboli del libro
        book.updateNewCardCorners(card);

        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
    }




}
