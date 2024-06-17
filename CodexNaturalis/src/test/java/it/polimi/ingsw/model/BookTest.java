package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CellNotAvailableException;
import it.polimi.ingsw.exceptions.PlacementConditionViolated;
import it.polimi.ingsw.model.cards.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import it.polimi.ingsw.model.cards.GoalType;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class BookTest {

    private Book book;
    private Map<SymbolType, Integer> symbolMap;
    private Map<ResourceType, Integer> resourceMap;


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
        Assertions.assertEquals(rows, bookMatrix.length);
        Assertions.assertEquals(columns, bookMatrix[0].length);

        // Verifichiamo che ogni cella della matrice sia stata inizializzata correttamente
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = bookMatrix[i][j];
                assertNotNull(cell);
                Assertions.assertEquals(i, cell.getRow());
                Assertions.assertEquals(j, cell.getColumn());
                assertFalse(cell.isAvailable());
            }
        }
    }


    @BeforeEach
    void setUp() {
        // Creiamo un oggetto Book 10x10
        book = new Book(10, 10);

        // Inizializziamo la mappa dei simboli
        symbolMap = book.getSymbolMap();
        symbolMap.put(SymbolType.Quill, 3);
        symbolMap.put(SymbolType.Ink, 2);
        symbolMap.put(SymbolType.Manuscript, 1);

        // Inizializziamo la mappa delle risorse
        resourceMap = book.getResourceMap();
        resourceMap.put(ResourceType.Fungi, 4);
        resourceMap.put(ResourceType.Animal, 3);
        resourceMap.put(ResourceType.Plant, 2);
        resourceMap.put(ResourceType.Insect, 1);
    }

    @Test
    public void testDecreaseSymbol() {
        // Chiamiamo il metodo decreaseSymbol per diminuire il numero di simboli
        book.decreaseSymbol(SymbolType.Quill);
        book.decreaseSymbol(SymbolType.Manuscript);
        book.decreaseSymbol(SymbolType.Manuscript);
        book.decreaseSymbol(SymbolType.Manuscript);

        // Verifichiamo che il numero di QUILL sia diminuito di uno
        Assertions.assertEquals(2, book.getSymbolMap().get(SymbolType.Quill).intValue());

        // Verifichiamo che il numero di INK sia rimasto invariato
        Assertions.assertEquals(2, book.getSymbolMap().get(SymbolType.Ink).intValue());


        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
    }

    @Test
    public void testDecreaseResource() {
        // Chiamiamo il metodo decreaseResource per diminuire il numero di risorse
        book.decreaseResource(ResourceType.Fungi);
        book.decreaseResource(ResourceType.Animal);
        book.decreaseResource(ResourceType.Insect);
        book.decreaseResource(ResourceType.Insect);

        // Verifichiamo che il numero di Plant sia rimasto invariato
        Assertions.assertEquals(2, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che il numero di Fungi sia diminuito di uno
        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Fungi).intValue());

        // Verifichiamo che il numero di Insect non sia sceso sotto allo 0
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());

    }

    @Test
    public void testIncreaseResource() {
        // Chiamiamo il metodo increaseResource per incrementare il numero di Risorse
        book.increaseResource(ResourceType.Fungi);
        book.increaseResource(ResourceType.Fungi);
        book.increaseResource(ResourceType.Plant);

        // Verifichiamo che il numero di Fungi sia aumentato di due
        Assertions.assertEquals(6, book.getResourceMap().get(ResourceType.Fungi).intValue());

        // Verifichiamo che il numero di Animal sia rimasto invariato
        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Animal).intValue());

        // Verifichiamo che il numero di Plant sia aumentato di uno
        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Plant).intValue());
    }

    @Test
    public void testClear() {
        // Creiamo un oggetto Book
        Book book = new Book(4, 4);
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Animal);
        // Creiamo alcuni oggetti Card da aggiungere al libro
        GoldCard card1 = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList2,true,false, SymbolType.Quill);

        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        ResourceCard card2 = new ResourceCard(5,3,true, CardType.ResourceCard, CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList,true,SymbolType.Ink);

        List<ResourceType> resourceList3 = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        ResourceCard card3 = new ResourceCard(5,3,true, CardType.ResourceCard, CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList3,true,SymbolType.Ink);


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
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
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
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

        book.clear();
        //carta 3
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Animal);
        GoldCard goldCardTest = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList2,true,false, SymbolType.Quill);

        book.updateNewCardCorners(goldCardTest);
        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
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
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(2, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
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
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

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
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
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
        List<Cell> availableCellsList;
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
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());

        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

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
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

        book.updateNewCardCorners(resourceCard);
        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());


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
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());

        book.updateNewCardCorners(goldCard);
        // Verifichiamo che le risorse e i simboli nel libro siano stati aggiornati correttamente
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
   }

    @Test
    void testAddResourceCard() throws CellNotAvailableException {
        Book book = new Book(10,10);
        int points;
        //resourceCard1
        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Fungi);
        resourceList1.add(ResourceType.Animal);
        ResourceCard resourceCard1 = new ResourceCard(5,3,true,CardType.ResourceCard,CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList1,true,SymbolType.Ink);


        //resourceCard2
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Animal);
        ResourceCard resourceCard2 = new ResourceCard(6,3,true,CardType.ResourceCard,CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.WithResource,ResourceType.Animal,1,1,resourceList2,false,null);

        //resourceCard3
        List<ResourceType> resourceList3 = new ArrayList<>();
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Plant);
        ResourceCard resourceCard3 = new ResourceCard(7,3,true,CardType.ResourceCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithSymbol,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList3,true,SymbolType.Ink);

        book.getBookMatrix()[3][3].setAvailable(true);
        points = book.addResourceCard(resourceCard1, book.getBookMatrix()[3][3]);

        assertEquals(0,book.getBookMatrix()[3][3].getPlacementOrder());

        assertFalse(book.getBookMatrix()[2][3].isAvailable());
        assertFalse(book.getBookMatrix()[3][4].isAvailable());
        assertFalse(book.getBookMatrix()[3][2].isAvailable());
        assertTrue(book.getBookMatrix()[2][4].isAvailable());
        assertFalse(book.getBookMatrix()[3][3].isAvailable());
        assertTrue(book.getBookMatrix()[2][2].isAvailable());
        assertTrue(book.getBookMatrix()[4][4].isAvailable());
        assertFalse(book.getBookMatrix()[4][3].isAvailable());
        assertFalse(book.getBookMatrix()[4][2].isAvailable());
        assertTrue(book.getBookMatrix()[4][2].isWall());

        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        Assertions.assertEquals(0, points);


        points = book.addResourceCard(resourceCard3, book.getBookMatrix()[2][4]);

        assertEquals(1,book.getBookMatrix()[2][4].getPlacementOrder());
        assertFalse(book.getBookMatrix()[2][4].isAvailable());
        assertFalse(book.getBookMatrix()[2][3].isAvailable());
        assertFalse(book.getBookMatrix()[1][4].isAvailable());
        assertFalse(book.getBookMatrix()[2][5].isAvailable());
        assertFalse(book.getBookMatrix()[3][4].isAvailable());
        assertTrue(book.getBookMatrix()[1][3].isAvailable());
        assertTrue(book.getBookMatrix()[1][5].isAvailable());
        assertTrue(book.getBookMatrix()[3][5].isAvailable());
        assertTrue(book.getBookMatrix()[3][3].isWall());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());
        Assertions.assertEquals(2, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
        Assertions.assertEquals(0, points);

        points = book.addResourceCard(resourceCard2, book.getBookMatrix()[4][4]);
        assertEquals(2,book.getBookMatrix()[4][4].getPlacementOrder());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());
        Assertions.assertEquals(2, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
        Assertions.assertEquals(1, points);
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
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
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
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());
        // Verifichiamo che non ci siano cambiamenti nei simboli
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());


        assertFalse(book.getBookMatrix()[35][35].isAvailable());

        assertTrue(book.getBookMatrix()[34][34].isAvailable());
        assertTrue(book.getBookMatrix()[34][36].isAvailable());
        assertTrue(book.getBookMatrix()[36][34].isAvailable());
        assertTrue(book.getBookMatrix()[36][36].isAvailable());
        assertEquals(0,book.getBookMatrix()[35][35].getPlacementOrder());

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
    void testCheckGoldSymbolCondition(){
        Book book = new Book(5, 5);
        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);
        GoldCard goldCardTest = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList,true,false, SymbolType.Quill);

        book.getSymbolMap().put(SymbolType.Quill, 3);
        book.getBookMatrix()[2][2].setAvailable(false);
        book.getBookMatrix()[2][2].setCardPointer(goldCardTest);
        book.updateBook(goldCardTest, book.getBookMatrix()[2][2]);
        book.updateNewCardCorners(goldCardTest);

        int points = book.checkGoldSymbolCondition(goldCardTest);
        Assertions.assertEquals(4, points);
    }

    @Test
    void TestCheckGoldCornerCondition() throws CellNotAvailableException {
        Book book = new Book(10,10);
        int points;
        //resourceCard1
        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Fungi);
        resourceList1.add(ResourceType.Animal);
        ResourceCard resourceCard1 = new ResourceCard(5,3,true,CardType.ResourceCard,CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList1,true,SymbolType.Ink);

        //resourceCard2
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Animal);
        ResourceCard resourceCard2 = new ResourceCard(6,3,true,CardType.ResourceCard,CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.WithResource,ResourceType.Animal,1,1,resourceList2,false,null);

        //resourceCard3
        List<ResourceType> resourceList3 = new ArrayList<>();
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Plant);
        ResourceCard resourceCard3 = new ResourceCard(7,3,true,CardType.ResourceCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithSymbol,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList3,true,SymbolType.Ink);

        //goldCard1(corner)
        List<ResourceType> placementList1 = new ArrayList<>();
        placementList1.add(ResourceType.Animal);
        placementList1.add(ResourceType.Animal);
        placementList1.add(ResourceType.Animal);
        placementList1.add(ResourceType.Insect);
        GoldCard goldCard1 = new GoldCard(0,3,true,CardType.GoldCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.NoCorner,ResourceType.Animal,false,null,2,placementList1,true,true,null);

        book.getBookMatrix()[3][3].setAvailable(true);
        book.addResourceCard(resourceCard1, book.getBookMatrix()[3][3]);
        book.addResourceCard(resourceCard3,book.getBookMatrix()[2][4]);
        book.addResourceCard(resourceCard2,book.getBookMatrix()[4][4]);



        book.getResourceMap().put(ResourceType.Insect,1);
        book.getResourceMap().put(ResourceType.Animal, 3);
        book.getBookMatrix()[3][5].setCardPointer(goldCard1);
        book.getBookMatrix()[3][5].setAvailable(false);
        book.updateBook(goldCard1, book.getBookMatrix()[3][5]);
        book.updateMaps(goldCard1,book.getBookMatrix()[3][5]);

        points = book.checkGoldCornerCondition(book.getBookMatrix()[3][5]);
        Assertions.assertEquals(4, points);
    }

    @Test
    void testCheckGoldPoints() throws CellNotAvailableException {
        Book book = new Book(10,10);
        int points;
        //resourceCard1
        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Fungi);
        resourceList1.add(ResourceType.Animal);
        ResourceCard resourceCard1 = new ResourceCard(5,3,true,CardType.ResourceCard,CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList1,true,SymbolType.Ink);

        //resourceCard2
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Animal);
        ResourceCard resourceCard2 = new ResourceCard(6,3,true,CardType.ResourceCard,CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.WithResource,ResourceType.Animal,1,1,resourceList2,false,null);

        //resourceCard3
        List<ResourceType> resourceList3 = new ArrayList<>();
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Plant);
        ResourceCard resourceCard3 = new ResourceCard(7,3,true,CardType.ResourceCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithSymbol,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList3,true,SymbolType.Ink);

        //goldCard1(corner)
        List<ResourceType> placementList1 = new ArrayList<>();
        placementList1.add(ResourceType.Animal);
        placementList1.add(ResourceType.Animal);
        placementList1.add(ResourceType.Animal);
        placementList1.add(ResourceType.Insect);
        GoldCard goldCard1 = new GoldCard(0,3,true,CardType.GoldCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.NoCorner,ResourceType.Animal,false,null,2,placementList1,true,true,null);

        book.getBookMatrix()[3][3].setAvailable(true);
        book.addResourceCard(resourceCard1, book.getBookMatrix()[3][3]);
        book.addResourceCard(resourceCard3,book.getBookMatrix()[2][4]);
        book.addResourceCard(resourceCard2,book.getBookMatrix()[4][4]);

        book.getResourceMap().put(ResourceType.Insect,1);
        book.getResourceMap().put(ResourceType.Animal, 3);
        book.getBookMatrix()[3][5].setCardPointer(goldCard1);
        book.getBookMatrix()[3][5].setAvailable(false);
        book.updateBook(goldCard1, book.getBookMatrix()[3][5]);
        book.updateMaps(goldCard1,book.getBookMatrix()[3][5]);

        points = book.checkGoldPoints(goldCard1,book.getBookMatrix()[3][5]);
        Assertions.assertEquals(4, points);

        //goldCard (symbol)
        List<ResourceType> placementList2 = new ArrayList<>();
        placementList2.add(ResourceType.Fungi);
        placementList2.add(ResourceType.Fungi);
        placementList2.add(ResourceType.Animal);
        GoldCard goldCard2 = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,placementList2,true,false, SymbolType.Quill);

        book.getResourceMap().put(ResourceType.Fungi,2);
        book.getSymbolMap().put(SymbolType.Quill,2);

        book.getBookMatrix()[5][5].setCardPointer(goldCard1);
        book.getBookMatrix()[5][5].setAvailable(false);
        book.updateBook(goldCard2, book.getBookMatrix()[3][5]);
        book.updateMaps(goldCard2,book.getBookMatrix()[3][5]);
        points = book.checkGoldPoints(goldCard2,book.getBookMatrix()[5][5]);
        Assertions.assertEquals(3, points);
    }

    @Test
    void TestAddGoldCard() throws PlacementConditionViolated, CellNotAvailableException {
        Book book = new Book(10,10);
        int points;

        List<ResourceType> placementList1 = new ArrayList<>();
        placementList1.add(ResourceType.Animal);
        placementList1.add(ResourceType.Animal);
        placementList1.add(ResourceType.Animal);
        placementList1.add(ResourceType.Insect);
        GoldCard goldCard1 = new GoldCard(0,3,true,CardType.GoldCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.NoCorner,ResourceType.Animal,false,null,2,placementList1,true,true,null);


        List<ResourceType> placementList2 = new ArrayList<>();
        placementList2.add(ResourceType.Fungi);
        placementList2.add(ResourceType.Fungi);
        placementList2.add(ResourceType.Animal);
        GoldCard goldCard2 = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,placementList2,true,false, SymbolType.Quill);


        List<ResourceType> placementList3 = new ArrayList<>();
        placementList3.add(ResourceType.Fungi);
        placementList3.add(ResourceType.Fungi);
        placementList3.add(ResourceType.Fungi);
        GoldCard goldCard3 = new GoldCard(10,2,true,CardType.GoldCard,CornerLabel.Empty,CornerLabel.NoCorner,CornerLabel.NoCorner,CornerLabel.WithSymbol,ResourceType.Fungi,true,SymbolType.Ink,3,placementList3,false,false,null);


        book.getSymbolMap().put(SymbolType.Quill,3);
        //inizializzo mappe per consentire il piazzamento
        book.getResourceMap().put(ResourceType.Fungi, 3);
        book.getResourceMap().put(ResourceType.Animal,3);
        book.getResourceMap().put(ResourceType.Insect,1);

        book.getBookMatrix()[3][3].setAvailable(true); //setto la prima cella ad available
        points = book.addGoldCard(goldCard3,book.getBookMatrix()[3][3]);


        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(3, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
        Assertions.assertEquals(3, points);


        assertFalse(book.getBookMatrix()[3][3].isAvailable());
        assertFalse(book.getBookMatrix()[2][4].isAvailable());
        assertFalse(book.getBookMatrix()[4][4].isAvailable());
        assertTrue(book.getBookMatrix()[2][4].isWall());
        assertTrue(book.getBookMatrix()[4][4].isWall());
        assertTrue(book.getBookMatrix()[2][2].isAvailable());
        assertTrue(book.getBookMatrix()[4][2].isAvailable());
        Assertions.assertEquals(goldCard3,book.getBookMatrix()[3][3].getCard());

        points = book.addGoldCard(goldCard2,book.getBookMatrix()[2][2]);

        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        Assertions.assertEquals(1, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(4, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
        Assertions.assertEquals(4, points);

        assertFalse(book.getBookMatrix()[2][2].isAvailable());
        assertTrue(book.getBookMatrix()[1][1].isWall());
        assertTrue(book.getBookMatrix()[4][4].isWall());
        assertTrue(book.getBookMatrix()[2][4].isWall());
        assertTrue(book.getBookMatrix()[1][3].isAvailable());
        assertTrue(book.getBookMatrix()[4][2].isAvailable());
        assertFalse(book.getBookMatrix()[3][1].isWall());
        assertFalse(book.getBookMatrix()[3][3].isAvailable());
        assertTrue(book.getBookMatrix()[3][1].isAvailable());
        Assertions.assertEquals(goldCard2,book.getBookMatrix()[2][2].getCard());

        points = book.addGoldCard(goldCard1,book.getBookMatrix()[4][2]);

        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Insect).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Plant).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Ink).intValue());
        Assertions.assertEquals(4, book.getSymbolMap().get(SymbolType.Quill).intValue());
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
        Assertions.assertEquals(2, points);

        assertFalse(book.getBookMatrix()[4][2].isAvailable());
        assertTrue(book.getBookMatrix()[4][2].isWall());
        assertTrue(book.getBookMatrix()[1][1].isWall());
        assertTrue(book.getBookMatrix()[5][1].isWall());
        assertTrue(book.getBookMatrix()[4][4].isWall());
        assertTrue(book.getBookMatrix()[2][4].isWall());
        assertTrue(book.getBookMatrix()[1][3].isAvailable());
        assertFalse(book.getBookMatrix()[3][1].isWall());
        assertFalse(book.getBookMatrix()[3][3].isAvailable());
        assertTrue(book.getBookMatrix()[3][3].isWall());
        assertFalse(book.getBookMatrix()[2][2].isAvailable());
        assertTrue(book.getBookMatrix()[2][2].isWall());
        assertTrue(book.getBookMatrix()[3][1].isAvailable());
        assertTrue(book.getBookMatrix()[5][3].isAvailable());
        Assertions.assertEquals(goldCard1,book.getBookMatrix()[4][2].getCard());
    }

    @Test
    void TestAddCard() throws CellNotAvailableException {
        Book book = new Book(10,10);
        int points;
        //resourceCard1
        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Fungi);
        resourceList1.add(ResourceType.Animal);
        ResourceCard resourceCard1 = new ResourceCard(5,3,true,CardType.ResourceCard,CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList1,true,SymbolType.Ink);

        //resourceCard2
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Animal);
        ResourceCard resourceCard2 = new ResourceCard(6,3,true,CardType.ResourceCard,CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.WithResource,ResourceType.Animal,1,1,resourceList2,false,null);

        //resourceCard3
        List<ResourceType> resourceList3 = new ArrayList<>();
        resourceList3.add(ResourceType.Fungi);
        resourceList3.add(ResourceType.Plant);
        ResourceCard resourceCard3 = new ResourceCard(7,3,true,CardType.ResourceCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithSymbol,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList3,true,SymbolType.Ink);

        book.getBookMatrix()[9][0].setAvailable(true);
        points = book.addResourceCard(resourceCard1, book.getBookMatrix()[9][0]);

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

        //creo le istanze di carte Obiettivo di tipo ResourceCondition
        List<SymbolType> zeroSymbolList = new ArrayList<>();
        ObjectiveCard eightObjectiveCard = new ObjectiveCard(8,true,GoalType.ResourceCondition,2, ResourceType.Fungi, null, 3, 0, zeroSymbolList, null);
        ObjectiveCard objectiveCard9 = new ObjectiveCard(9,true,GoalType.ResourceCondition,2, ResourceType.Plant, null, 3, 0, zeroSymbolList, null);
        ObjectiveCard objectiveCard10 = new ObjectiveCard(10,true,GoalType.ResourceCondition,2, ResourceType.Animal, null, 3, 0, zeroSymbolList, null);
        ObjectiveCard objectiveCard11 = new ObjectiveCard(11,true,GoalType.ResourceCondition,2, ResourceType.Insect, null, 3, 0, zeroSymbolList, null);

        //creo una istanza di Book su cui applicare la CheckResourceCondition (Fungi)
        Book book = new Book(70, 70);
        book.getResourceMap().put(ResourceType.Animal, 1);
        book.getResourceMap().put(ResourceType.Plant, 0);
        book.getResourceMap().put(ResourceType.Insect, 15);
        book.getResourceMap().put(ResourceType.Fungi, 6);

        assertEquals(4, book.checkResourceCondition(eightObjectiveCard));

        //creo una istanza Book0 su cui applicare la CheckResourceCondition (Fungi)
        Book book0 = new Book(70, 70);
        book0.getResourceMap().put(ResourceType.Animal, 1);
        book0.getResourceMap().put(ResourceType.Plant, 0);
        book0.getResourceMap().put(ResourceType.Insect, 15);
        book0.getResourceMap().put(ResourceType.Fungi, 7);

        assertEquals(4, book0.checkResourceCondition(eightObjectiveCard));

        //creo una istanza Book1 su cui applicare la CheckResourceCondition (Plant)
        Book book1 = new Book(70, 70);
        book1.getResourceMap().put(ResourceType.Animal, 1);
        book1.getResourceMap().put(ResourceType.Plant, 11);
        book1.getResourceMap().put(ResourceType.Insect, 15);
        book1.getResourceMap().put(ResourceType.Fungi, 0);

        assertEquals(6, book1.checkResourceCondition(objectiveCard9));

        //creo una istanza Book2 su cui applicare la CheckResourceCondition (Animal)
        Book book2 = new Book(70, 70);
        book2.getResourceMap().put(ResourceType.Animal, 1);
        book2.getResourceMap().put(ResourceType.Plant, 11);
        book2.getResourceMap().put(ResourceType.Insect, 15);
        book2.getResourceMap().put(ResourceType.Fungi, 0);

        assertEquals(0, book2.checkResourceCondition(objectiveCard10));

        //creo una istanza Book3 su cui applicare la CheckResourceCondition (Insect)
        Book book3 = new Book(70, 70);
        book3.getResourceMap().put(ResourceType.Animal, 1);
        book3.getResourceMap().put(ResourceType.Plant, 11);
        book3.getResourceMap().put(ResourceType.Insect, 23);
        book3.getResourceMap().put(ResourceType.Fungi, 23);

        assertEquals(14, book3.checkResourceCondition(objectiveCard11));


    }

    @Test
    void testCheckSymbolCondition() {
        //creo una istanza di carta Obiettivo che sia di tipo ResourceCondition (CASE victoryPoints=3)
        List<SymbolType> symbolList12 = new ArrayList<>();
        symbolList12.add(SymbolType.Quill);
        symbolList12.add(SymbolType.Ink);
        symbolList12.add(SymbolType.Manuscript);
        ObjectiveCard objectiveCard12 = new ObjectiveCard(12,true,GoalType.SymbolCondition,3, null, null, 0, 3, symbolList12, null);

        //creo una seconda istanza di carta Obiettivo13 che sia di tipo ResourceCondition (CASE victoryPoints=2)
        List<SymbolType> symbolList13 = new ArrayList<>();
        symbolList13.add(SymbolType.Manuscript);
        symbolList13.add(SymbolType.Manuscript);
        ObjectiveCard objectiveCard13 = new ObjectiveCard(13,true,GoalType.SymbolCondition,2, null, null, 0, 2, symbolList13, null);

        //creo una istanza di Book su cui applicare la CheckSymbolCondition (CASE victoryPoints=3)
        Book book = new Book(70, 70);
        book.getSymbolMap().put(SymbolType.Quill, 1);
        book.getSymbolMap().put(SymbolType.Ink, 1);
        book.getSymbolMap().put(SymbolType.Manuscript, 1);

        assertEquals(3, book.checkSymbolCondition(objectiveCard12));

        //creo una seconda istanza di Book su cui applicare la CheckSymbolCondition (CASE victoryPoints=3)
        Book book1 = new Book(70, 70);
        book1.getSymbolMap().put(SymbolType.Quill, 4);
        book1.getSymbolMap().put(SymbolType.Ink, 10);
        book1.getSymbolMap().put(SymbolType.Manuscript, 6);

        assertEquals(12, book1.checkSymbolCondition(objectiveCard12));

        //creo una terza istanza di Book su cui applicare la CheckSymbolCondition (CASE victoryPoints=2)
        Book book2 = new Book(70, 70);
        book2.getSymbolMap().put(SymbolType.Quill, 6);
        book2.getSymbolMap().put(SymbolType.Ink, 0);
        book2.getSymbolMap().put(SymbolType.Manuscript, 5);

        assertEquals(4, book2.checkSymbolCondition(objectiveCard13));

    }


    @Test
    void testCheckDiagonalPlacementSimpleCase() throws CellNotAvailableException, PlacementConditionViolated {
        //creo una istanza di carta Obiettivo che sia di tipo DiagonalPlacementCondition
        List<SymbolType> symbolList0 = new ArrayList<>();
        ObjectiveCard objectiveCard0 = new ObjectiveCard(0,true,GoalType.DiagonalPlacement,2, ResourceType.Fungi, CornerType.BLCorner, 0, 0, symbolList0, null);

        //creo una istanza di carta Obiettivo1 che sia di tipo DiagonalPlacementCondition (Diagonale principale- Plant)
        //ObjectiveCard objectiveCard1 = new ObjectiveCard(1,true,GoalType.DiagonalPlacement,2, ResourceType.Plant, CornerType.BRCorner, 0, 0, symbolList0, null);

        Book book = new Book(70, 70);

        //Creo istanza InitialCard0 fittizia e la aggiungo al Book
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Plant);
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Insect);

        List<ResourceType> centralResources2 = new ArrayList<>();
        InitialCard initialCard = new InitialCard(0,4,false,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,centralResources2,0,4,resourceList2);
        book.addInitial(initialCard);
        Assertions.assertEquals(initialCard, book.getBookMatrix()[35][35].getCard());

        //Creo ResourceCard0 e la aggiungo al Book
        List<ResourceType> resourceList0 = new ArrayList<>();
        resourceList0.add(ResourceType.Fungi);
        resourceList0.add(ResourceType.Fungi);
        ResourceCard resourceCard0 = new ResourceCard(0,3,true,CardType.ResourceCard,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.NoCorner,CornerLabel.WithResource ,ResourceType.Fungi,0,2,resourceList0,false, null);
        book.addCard(resourceCard0, book.getBookMatrix()[34][36]); //aggiungo al book la resoureCard

        //Creo ResourceBackCard1 e la aggiungo al Book
        List<ResourceType> resourceList1 = new ArrayList<>();
        ResourceCard resourceCard1 = new ResourceCard(1,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList1,false, null);
        book.addCard(resourceCard1, book.getBookMatrix()[33][37]); //aggiungo al book la resoureCard

        //Creo ResourceBackCard2 e la aggiungo al Book
        List<ResourceType> resourceList = new ArrayList<>();
        ResourceCard resourceCard2 = new ResourceCard(2,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList,false, null);
        book.addCard(resourceCard2, book.getBookMatrix()[32][38]); //aggiungo al book la resoureCard

        assertEquals(2, book.checkDiagonalPlacement(objectiveCard0));
    }

    @Test
    void checkDiagonalPlacementMoreThan3Cards() throws PlacementConditionViolated, CellNotAvailableException {
        //creo una istanza di carta Obiettivo che sia di tipo DiagonalPlacementCondition
        List<SymbolType> symbolList0 = new ArrayList<>();
        ObjectiveCard objectiveCard0 = new ObjectiveCard(0,true,GoalType.DiagonalPlacement,2, ResourceType.Fungi, CornerType.BLCorner, 0, 0, symbolList0, null);

        Book book = new Book(70, 70);

        //Creo istanza InitialCard0 fittizia e la aggiungo al Book
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Plant);
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Insect);
        List<ResourceType> centralResources2 = new ArrayList<>();
        InitialCard initialCard = new InitialCard(0,4,false,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,centralResources2,0,4,resourceList2);
        book.addInitial(initialCard);
        Assertions.assertEquals(initialCard, book.getBookMatrix()[35][35].getCard());

        //Creo ResourceCard0 e la aggiungo al Book
        List<ResourceType> resourceList0 = new ArrayList<>();
        resourceList0.add(ResourceType.Fungi);
        resourceList0.add(ResourceType.Fungi);
        ResourceCard resourceCard0 = new ResourceCard(0,3,true,CardType.ResourceCard,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.NoCorner,CornerLabel.WithResource ,ResourceType.Fungi,0,2,resourceList0,false, null);
        book.addCard(resourceCard0, book.getBookMatrix()[34][36]); //aggiungo al book la resoureCard

        //Creo ResourceBackCard1 e la aggiungo al Book
        List<ResourceType> resourceList1 = new ArrayList<>();
        ResourceCard resourceCard1 = new ResourceCard(1,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList1,false, null);
        book.addCard(resourceCard1, book.getBookMatrix()[33][37]); //aggiungo al book la resoureCard

        //Creo ResourceBackCard2 e la aggiungo al Book
        List<ResourceType> resourceList = new ArrayList<>();
        ResourceCard resourceCard2 = new ResourceCard(2,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList,false, null);
        book.addCard(resourceCard2, book.getBookMatrix()[32][38]); //aggiungo al book la resoureCard

        //aggiungo una quarta carta alla diagonale
        //Creo ResourceBackCard3 e la aggiungo al Book
        ResourceCard resourceCard3 = new ResourceCard(2,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList,false, null);
        book.addCard(resourceCard3, book.getBookMatrix()[31][39]); //aggiungo al book la resoureCard

        /*
        //aggiungo una quinta carta alla diagonale
        //Creo ResourceBackCard4 e la aggiungo al Book
        ResourceCard resourceCard4 = new ResourceCard(2,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList,false, null);
        book.addCard(resourceCard4, book.getBookMatrix()[30][40]); //aggiungo al book la resoureCard

        //aggiungo una sesta carta alla diagonale
        //Creo ResourceBackCard6 e la aggiungo al Book
        ResourceCard resourceCard6 = new ResourceCard(2,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList,false, null);
        book.addCard(resourceCard6, book.getBookMatrix()[29][41]); //aggiungo al book la resoureCard
         */

        assertEquals(2, book.checkDiagonalPlacement(objectiveCard0));
    }



    @Test
    void testCheckLPlacementTLCorner() throws PlacementConditionViolated, CellNotAvailableException {
        Book book = new Book(70, 70);
        //creo carta objectiveCard
        List<SymbolType> objectiveList = new ArrayList<>();
        ObjectiveCard objectiveCard = new ObjectiveCard(4,true,GoalType.LPlacement,3,ResourceType.Fungi,CornerType.TLCorner,0,0,objectiveList,ResourceType.Plant);

        //Creo istanza InitialCard0 fittizia e la aggiungo al Book
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Plant);
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Insect);
        List<ResourceType> centralResources2 = new ArrayList<>();
        InitialCard initialCard = new InitialCard(0,4,false,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,centralResources2,0,4,resourceList2);
        book.addInitial(initialCard);
        Assertions.assertEquals(initialCard, book.getBookMatrix()[35][35].getCard());


        //Creo carta FUNGI e la aggiungo al Book
        List<ResourceType> resourceList = new ArrayList<>();
        ResourceCard resourceCard2 = new ResourceCard(2,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList,false, null);
        book.addCard(resourceCard2, book.getBookMatrix()[34][36]); //aggiungo al book la resoureCard

        //creo carta FUNGI e la aggiungo al book
        ResourceCard resourceCard3 = new ResourceCard(1,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList,false, null);
        book.addCard(resourceCard3, book.getBookMatrix()[36][36]); //aggiungo al book la resoureCard

        //creo carta PLANT e la aggiungo al book
        ResourceCard resourceCard4 = new ResourceCard(10,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Plant,0,0,resourceList,false, null);
        book.addCard(resourceCard4, book.getBookMatrix()[37][37]); //aggiungo al book la resoureCard

        assertEquals(3, book.checkLPlacement(objectiveCard));
    }

    @Test
    void testCheckLPlacementTRCorner() throws PlacementConditionViolated, CellNotAvailableException {
        Book book = new Book(70, 70);
        //creo carta objectiveCard
        List<SymbolType> objectiveList = new ArrayList<>();
        ObjectiveCard objectiveCard = new ObjectiveCard(5,true,GoalType.LPlacement,3,ResourceType.Plant,CornerType.TRCorner,0,0,objectiveList,ResourceType.Insect);

        //Creo istanza InitialCard0 fittizia e la aggiungo al Book
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Plant);
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Insect);
        List<ResourceType> centralResources2 = new ArrayList<>();
        InitialCard initialCard = new InitialCard(0,4,false,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,centralResources2,0,4,resourceList2);
        book.addInitial(initialCard);
        Assertions.assertEquals(initialCard, book.getBookMatrix()[35][35].getCard());

        //creo carta PLANT e la aggiungo al book
        List<ResourceType> resourceList = new ArrayList<>();
        ResourceCard resourceCard4 = new ResourceCard(10,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Plant,0,0,resourceList,false, null);
        book.addCard(resourceCard4, book.getBookMatrix()[36][34]); //aggiungo al book la resoureCard

        //creo carta PLANT e la aggiungo al book
        ResourceCard resourceCard5 = new ResourceCard(11,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Plant,0,0,resourceList,false, null);
        book.addCard(resourceCard5, book.getBookMatrix()[34][34]); //aggiungo al book la resoureCard

        //creo carta INSECT e la aggiungo al book
        ResourceCard resourceCard6 = new ResourceCard(30,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Insect,0,0,resourceList,false, null);
        book.addCard(resourceCard6, book.getBookMatrix()[37][33]); //aggiungo al book la resoureCard

        assertEquals(3, book.checkLPlacement(objectiveCard));
    }

    @Test
    void testCheckLPlacementBLCorner() throws PlacementConditionViolated, CellNotAvailableException {
        Book book = new Book(70, 70);
        //creo carta objectiveCard
        List<SymbolType> objectiveList = new ArrayList<>();
        ObjectiveCard objectiveCard = new ObjectiveCard(6,true,GoalType.LPlacement,3,ResourceType.Animal,CornerType.BLCorner,0,0,objectiveList,ResourceType.Fungi);

        //Creo istanza InitialCard0 fittizia e la aggiungo al Book
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Plant);
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Insect);
        List<ResourceType> centralResources2 = new ArrayList<>();
        InitialCard initialCard = new InitialCard(0,4,false,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,centralResources2,0,4,resourceList2);
        book.addInitial(initialCard);
        Assertions.assertEquals(initialCard, book.getBookMatrix()[35][35].getCard());

        //creo carta ANIMAL e la aggiungo al book
        List<ResourceType> resourceList = new ArrayList<>();
        ResourceCard resourceCard4 = new ResourceCard(20,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Animal,0,0,resourceList,false, null);
        book.addCard(resourceCard4, book.getBookMatrix()[36][36]); //aggiungo al book la resoureCard

        //creo carta ANIMAL e la aggiungo al book
        ResourceCard resourceCard5 = new ResourceCard(20,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Animal,0,0,resourceList,false, null);
        book.addCard(resourceCard5, book.getBookMatrix()[34][36]); //aggiungo al book la resoureCard

        //creo carta FUNGI e la aggiungo al book
        ResourceCard resourceCard3 = new ResourceCard(1,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList,false, null);
        book.addCard(resourceCard3, book.getBookMatrix()[33][37]); //aggiungo al book la resoureCard

        assertEquals(3, book.checkLPlacement(objectiveCard));

    }

    @Test
    void testCheckLPlacementBRCorner() throws PlacementConditionViolated, CellNotAvailableException {
        Book book = new Book(70, 70);
        //creo carta objectiveCard
        List<SymbolType> objectiveList = new ArrayList<>();
        ObjectiveCard objectiveCard = new ObjectiveCard(7,true,GoalType.LPlacement,3,ResourceType.Insect,CornerType.BRCorner,0,0,objectiveList,ResourceType.Animal);

        //Creo istanza InitialCard0 fittizia e la aggiungo al Book
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Plant);
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Insect);
        List<ResourceType> centralResources2 = new ArrayList<>();
        InitialCard initialCard = new InitialCard(0,4,false,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,centralResources2,0,4,resourceList2);
        book.addInitial(initialCard);
        Assertions.assertEquals(initialCard, book.getBookMatrix()[35][35].getCard());

        //creo carta INSECT e la aggiungo al book
        List<ResourceType> resourceList = new ArrayList<>();
        ResourceCard resourceCard6 = new ResourceCard(30,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Insect,0,0,resourceList,false, null);
        book.addCard(resourceCard6, book.getBookMatrix()[36][36]); //aggiungo al book la resoureCard

        //creo carta INSECT e la aggiungo al book
        ResourceCard resourceCard5 = new ResourceCard(30,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Insect,0,0,resourceList,false, null);
        book.addCard(resourceCard5, book.getBookMatrix()[34][36]); //aggiungo al book la resoureCard

        //creo carta ANIMAL e la aggiungo al book
        ResourceCard resourceCard4 = new ResourceCard(20,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Animal,0,0,resourceList,false, null);
        book.addCard(resourceCard4, book.getBookMatrix()[33][35]); //aggiungo al book la resoureCard

        assertEquals(3, book.checkLPlacement(objectiveCard));

    }





}
