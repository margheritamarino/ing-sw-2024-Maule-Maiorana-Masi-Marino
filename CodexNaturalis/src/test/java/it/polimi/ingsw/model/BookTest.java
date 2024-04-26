package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.*;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import it.polimi.ingsw.model.cards.GoalType;

import static org.junit.Assert.assertEquals;
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
    public void testDecreaseResource() {
        // Creiamo un oggetto Book
        Book book = new Book(4, 4);

        // Aggiungiamo alcune risorse alla mappa delle risorse
        book.getResourceMap().put(ResourceType.Plant, 3);
        book.getResourceMap().put(ResourceType.Animal, 2);
        book.getResourceMap().put(ResourceType.Fungi, 1);
        book.getResourceMap().put(ResourceType.Insect, 20);


        // Chiamiamo il metodo decreaseResource per diminuire il numero di risorse
        book.decreaseResource(ResourceType.Animal);
        book.decreaseResource(ResourceType.Fungi);
        book.decreaseResource(ResourceType.Fungi);


        assertEquals(3, book.getResourceMap().get(ResourceType.Plant).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(20, book.getResourceMap().get(ResourceType.Insect).intValue());
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
        book.increaseResource(ResourceType.Fungi);
        book.increaseResource(ResourceType.Plant);

        // Verifichiamo che il numero di Fungi sia aumentato di uno
        assertEquals(5, book.getResourceMap().get(ResourceType.Fungi).intValue());

        // Verifichiamo che il numero di Animal sia rimasto invariato
        assertEquals(2, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());
    }

    @Test
    public void testClear() {
        // Creiamo un oggetto Book
        Book book = new Book(4, 4);

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
                assertFalse(book.getBookMatrix()[i][j].isAvailable());
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
        ResourceCard card = new ResourceCard(5,3,true, CardType.ResourceCard, CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList,true,SymbolType.Ink);
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
        book.clear();

        //carta 2
        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Plant);
        resourceList1.add(ResourceType.Insect);
        List<ResourceType> centralResources1 = new ArrayList<>();
        resourceList1.add(ResourceType.Insect);
        InitialCard initialCardTest1 = new InitialCard(0,4,true,CardType.InitialCard,CornerLabel.Empty,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.WithResource,centralResources1,1,2,resourceList1);

        book.updateNewCardCorners(initialCardTest1);

        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

        book.clear();
        //carta 3
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Animal);
        GoldCard goldCardTest = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList2,true,false, SymbolType.Quill);

        book.updateNewCardCorners(goldCardTest);
        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(1, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
    }


    @Test
    public void TestUpdateMapInitial(){
        // Creiamo un oggetto Book
        Book book = new Book(4, 4);
        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Plant);
        resourceList1.add(ResourceType.Insect);
        List<ResourceType> centralResources1 = new ArrayList<>();
        centralResources1.add(ResourceType.Insect);
        InitialCard initialCardTest1 = new InitialCard(0,4,true,CardType.InitialCard,CornerLabel.Empty,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.WithResource,centralResources1,1,2,resourceList1);

        book.UpdateMapInitial(initialCardTest1);

        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(2, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
    }



    @Test
    void TestCoverCorner(){
        Book book = new Book(4, 4);

        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Animal);
        GoldCard goldCard = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList2,true,false, SymbolType.Quill);

        book.updateNewCardCorners(goldCard);
        book.coverCorner(goldCard, 2);
        book.coverCorner(goldCard, 0);
        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

        book.clear();

        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        ResourceCard card = new ResourceCard(5,3,true, CardType.ResourceCard, CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList,true,SymbolType.Ink);
        // Chiamiamo il metodo updateNewCardCorners per aggiornare le risorse e i simboli del libro
        book.updateNewCardCorners(card);

        //book.coverCorner(card, 0);
        book.coverCorner(card, 1);
        book.coverCorner(card, 2);
        book.coverCorner(card, 3);

        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
    }

    @Test
    void testUpdateBook(){
        Book book = new Book(5, 5);
        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        ResourceCard card = new ResourceCard(5,3,true, CardType.ResourceCard, CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList,true,SymbolType.Ink);

        Cell myCell = book.getBookMatrix()[2][2];
        book.getBookMatrix()[2][2].setAvailable(false);
        book.getBookMatrix()[2][2].setCardPointer(card);

        assertFalse(book.getBookMatrix()[2][2].isAvailable());
        Assertions.assertEquals(card, book.getBookMatrix()[2][2].getCard());

        book.updateBook(card, myCell);

        assertFalse(book.getBookMatrix()[2][2].isAvailable());
        assertFalse(book.getBookMatrix()[0][0].isAvailable());
        assertFalse(book.getBookMatrix()[0][1].isAvailable());
        assertFalse(book.getBookMatrix()[1][2].isAvailable());
        assertFalse(book.getBookMatrix()[2][1].isAvailable());
        assertFalse(book.getBookMatrix()[2][3].isAvailable());
        assertFalse(book.getBookMatrix()[3][2].isAvailable());

        assertFalse(book.getBookMatrix()[3][2].isWall());

        assertTrue(book.getBookMatrix()[1][1].isAvailable());
        assertTrue(book.getBookMatrix()[1][3].isAvailable());
        assertTrue(book.getBookMatrix()[3][3].isAvailable());

        assertFalse(book.getBookMatrix()[1][1].isWall());
        assertFalse(book.getBookMatrix()[1][3].isWall());
        assertFalse(book.getBookMatrix()[3][3].isWall());


        assertFalse(book.getBookMatrix()[3][1].isAvailable());
        assertTrue(book.getBookMatrix()[3][1].isWall());
        book.clear();
    }

    @Test
    void testShowAvailableCells(){
        Book book = new Book(5, 5);
        book.clear();
        List<Cell> availableCellsList = new ArrayList<>();
        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        ResourceCard card = new ResourceCard(5,3,true, CardType.ResourceCard, CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList,true,SymbolType.Ink);

        Cell myCell = book.getBookMatrix()[2][2];
        book.getBookMatrix()[2][2].setAvailable(false);
        book.getBookMatrix()[2][2].setCardPointer(card);

        book.updateBook(card, myCell);
        availableCellsList = book.showAvailableCells();

        assertTrue(availableCellsList.contains(book.getBookMatrix()[1][1]));
        assertTrue(availableCellsList.contains(book.getBookMatrix()[1][3]));
        assertTrue(availableCellsList.contains(book.getBookMatrix()[3][3]));

        assertFalse(availableCellsList.contains(book.getBookMatrix()[0][0]));
        assertFalse(availableCellsList.contains(book.getBookMatrix()[3][1]));
        assertFalse(availableCellsList.contains(book.getBookMatrix()[1][2]));
        assertFalse(availableCellsList.contains(book.getBookMatrix()[1][0]));
    }

   @Test
   void testUpdateCoveredCorners(){
        Book book = new Book(5, 5);

        //aggiungo carta iniziale al book
        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Plant);
        resourceList1.add(ResourceType.Insect);
        List<ResourceType> centralResources1 = new ArrayList<>();
        centralResources1.add(ResourceType.Insect);
        InitialCard initialCard = new InitialCard(0,4,true,CardType.InitialCard,CornerLabel.Empty,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.WithResource,centralResources1,1,2,resourceList1);

        book.getBookMatrix()[2][2].setAvailable(false);
        book.getBookMatrix()[2][2].setCardPointer(initialCard);
        book.updateBook(initialCard, book.getBookMatrix()[2][2]);
        book.updateNewCardCorners(initialCard);

        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

        //aggiungo carta resource al book
        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        ResourceCard resourceCard = new ResourceCard(5,3,true,CardType.ResourceCard,CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList,true,SymbolType.Ink);

        book.getBookMatrix()[1][3].setAvailable(false);
        book.getBookMatrix()[1][3].setCardPointer(resourceCard);
        book.updateBook(resourceCard, book.getBookMatrix()[1][3]);

        book.updateCoveredCorners(book.getBookMatrix()[1][3]);
        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

        book.updateNewCardCorners(resourceCard);
        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());


        //aggiungo carta goldCard
        List<ResourceType> resourceList3 = new ArrayList<>();
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Animal);
        GoldCard goldCard = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList3,true,false, SymbolType.Quill);

        book.getBookMatrix()[3][3].setAvailable(false);
        book.getBookMatrix()[3][3].setCardPointer(goldCard);
        book.updateBook(goldCard, book.getBookMatrix()[3][3]);

        book.updateCoveredCorners(book.getBookMatrix()[3][3]);

        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

        book.updateNewCardCorners(goldCard);
        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(1, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
   }

    @Test
    void testUpdateMaps(){
        Book book = new Book(5, 5);

        //aggiungo carta iniziale al book
        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Plant);
        resourceList1.add(ResourceType.Insect);
        List<ResourceType> centralResources1 = new ArrayList<>();
        centralResources1.add(ResourceType.Insect);
        InitialCard initialCard = new InitialCard(0,4,true,CardType.InitialCard,CornerLabel.Empty,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.WithResource,centralResources1,1,2,resourceList1);

        book.getBookMatrix()[2][2].setAvailable(false);
        book.getBookMatrix()[2][2].setCardPointer(initialCard);
        book.updateBook(initialCard, book.getBookMatrix()[2][2]);
        book.updateMaps(initialCard, book.getBookMatrix()[2][2]);

        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        ResourceCard resourceCard = new ResourceCard(5,3,true,CardType.ResourceCard,CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList,true,SymbolType.Ink);

        book.getBookMatrix()[1][3].setAvailable(false);
        book.getBookMatrix()[1][3].setCardPointer(resourceCard);
        book.updateBook(resourceCard, book.getBookMatrix()[1][3]);
        book.updateMaps(resourceCard, book.getBookMatrix()[1][3]);

        List<ResourceType> resourceList3 = new ArrayList<>();
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Animal);
        GoldCard goldCard = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList3,true,false, SymbolType.Quill);

        book.getBookMatrix()[3][1].setAvailable(false);
        book.getBookMatrix()[3][1].setCardPointer(goldCard);
        book.updateBook(goldCard, book.getBookMatrix()[3][1]);
        book.updateMaps(goldCard, book.getBookMatrix()[3][1]);

        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(1, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
    }

    @Test
    void testAddInitial(){
        Book book = new Book(70, 70);
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Plant);
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Insect);

        List<ResourceType> centralResources2 = new ArrayList<>();
        InitialCard initialCardTest = new InitialCard(0,4,false,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,centralResources2,0,4,resourceList2);

        book.addInitial(initialCardTest);

        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());


        assertFalse(book.getBookMatrix()[35][35].isAvailable());

        assertTrue(book.getBookMatrix()[34][34].isAvailable());
        assertTrue(book.getBookMatrix()[34][36].isAvailable());
        assertTrue(book.getBookMatrix()[36][34].isAvailable());
        assertTrue(book.getBookMatrix()[36][36].isAvailable());

    }

    @Test
    void testCheckPlacementCondition(){
        Book book = new Book(5, 5);
        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        GoldCard goldCardTest = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList,true,false, SymbolType.Quill);

        book.getResourceMap().put(ResourceType.Fungi, 4);
        book.getResourceMap().put(ResourceType.Animal, 1);
        book.getResourceMap().put(ResourceType.Plant, 2);

        assertTrue(book.checkPlacementCondition(goldCardTest));
    }

    @Test
    void testCheckGoal(){

        List<SymbolType> zeroSymbolList = new ArrayList<>();
        ObjectiveCard zeroObjectiveCard = new ObjectiveCard(0,true,GoalType.DiagonalPlacement,2, ResourceType.Fungi, CornerType.BLCorner, 0, 0, zeroSymbolList, null);
        ObjectiveCard eightObjectiveCard = new ObjectiveCard(8,true,GoalType.ResourceCondition,2, ResourceType.Fungi, null, 3, 0, zeroSymbolList, null);


        assertTrue(zeroObjectiveCard.getGoalType().equals(GoalType.DiagonalPlacement));
        assertTrue(eightObjectiveCard.getGoalType().equals(GoalType.ResourceCondition));

        /*Creazione di una InitialCard fittizia
        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Plant);
        resourceList1.add(ResourceType.Insect);

        List<ResourceType> centralResources1 = new ArrayList<>();
        resourceList1.add(ResourceType.Insect);

        InitialCard initialCardTest1 = new InitialCard(0,4,true,CardType.InitialCard,CornerLabel.Empty,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.WithResource,centralResources1,1,2,resourceList1);
        book.addInitial(initialCardTest1); //ho aggiunto la carta initial al centro del Book


        //Creo una GoldCard fittizia e la aggiungo al Book
        List<ResourceType> resourceList3 = new ArrayList<>();
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Animal);
        GoldCard goldCard = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList3,true,false, SymbolType.Quill);

        book.getBookMatrix()[3][1].setAvailable(false);
        book.getBookMatrix()[3][1].setCardPointer(goldCard);
        book.updateBook(goldCard, book.getBookMatrix()[3][1]);
        book.updateMaps(goldCard, book.getBookMatrix()[3][1]);*/

    }



    @Test
    void testCheckResourceCondition() {

        //creo una istanza una carta Obiettivo che sia di tipo ResourceCondition
        List<SymbolType> zeroSymbolList = new ArrayList<>();
        ObjectiveCard eightObjectiveCard = new ObjectiveCard(8,true,GoalType.ResourceCondition,2, ResourceType.Fungi, null, 3, 0, zeroSymbolList, null);

        //creo una istanza di Book su cui applicare la CheckResourceCondition
        Book book = new Book(70, 70);

        //Creo una InitialCard fittizia e la aggiungo al Book
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Fungi);

        List<ResourceType> centralResources2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);

        InitialCard initialCardTest2 = new InitialCard(1,4,true,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.WithResource,CornerLabel.Empty,centralResources2,1,2,resourceList2);
        book.addInitial(initialCardTest2); //ho aggiunto la carta initial al centro del Book

        book.getBookMatrix()[35][35].setAvailable(false);
        book.getBookMatrix()[35][35].setCardPointer(initialCardTest2);
        book.updateBook(initialCardTest2, book.getBookMatrix()[35][35]);
        book.updateMaps(initialCardTest2, book.getBookMatrix()[35][35]);

        //Creo una ResourceCard fittizia e la aggiungo al Book
        List<ResourceType> resourceList0 = new ArrayList<>();
        resourceList0.add(ResourceType.Fungi);
        resourceList0.add(ResourceType.Fungi);
        ResourceCard resourceCard0 = new ResourceCard(0,3,true,CardType.ResourceCard,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.NoCorner,CornerLabel.WithResource ,ResourceType.Fungi,0,2,resourceList0,false, null);

        book.getBookMatrix()[34][34].setAvailable(false);
        book.getBookMatrix()[34][34].setCardPointer(resourceCard0);
        book.updateBook(resourceCard0, book.getBookMatrix()[34][34]);
        book.updateMaps(resourceCard0, book.getBookMatrix()[34][34]);

        assertEquals(2, book.checkResourceCondition(eightObjectiveCard));


    }

    @Test
    void testCheckSymbolCondition() {
        //creo una istanza una carta Obiettivo che sia di tipo ResourceCondition
        List<SymbolType> symbolList12 = new ArrayList<>();
        symbolList12.add(SymbolType.Quill);
        symbolList12.add(SymbolType.Ink);
        symbolList12.add(SymbolType.Manuscript);
        ObjectiveCard eightObjectiveCard = new ObjectiveCard(12,true,GoalType.SymbolCondition,3, null, null, 0, 3, symbolList12, null);

        //creo una istanza di Book su cui applicare la CheckResourceCondition
        Book book = new Book(70, 70);

        //Creo una InitialCard fittizia e la aggiungo al Book
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Fungi);

        List<ResourceType> centralResources1 = new ArrayList<>();
        centralResources1.add(ResourceType.Fungi);

        InitialCard initialCardTest1 = new InitialCard(0,4,true,CardType.InitialCard,CornerLabel.Empty,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.WithResource,centralResources1,1,2,resourceList2);
        book.addInitial(initialCardTest1); //ho aggiunto la carta initial al centro del Book

        book.getBookMatrix()[35][35].setAvailable(false);
        book.getBookMatrix()[35][35].setCardPointer(initialCardTest1);
        book.updateBook(initialCardTest1, book.getBookMatrix()[35][35]);
        book.updateMaps(initialCardTest1, book.getBookMatrix()[35][35]);

    }

    @Test
    void testCheckDiagonalPlacement() {
        Book book = new Book(5, 5);
    }

    @Test
    void testCheckLPlacement() {
        Book book = new Book(5, 5);
    }





}
