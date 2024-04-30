package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.PlacementConditionViolated;
import it.polimi.ingsw.model.cards.*;
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
        Assertions.assertEquals(2, book.getSymbolMap().get(SymbolType.Quill).intValue());

        // Verifichiamo che il numero di INK sia rimasto invariato
        Assertions.assertEquals(2, book.getSymbolMap().get(SymbolType.Ink).intValue());

        // Verifichiamo che il numero di MANUSCRIPT non sia sceso sotto allo 0
        Assertions.assertEquals(0, book.getSymbolMap().get(SymbolType.Manuscript).intValue());
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


        Assertions.assertEquals(3, book.getResourceMap().get(ResourceType.Plant).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(0, book.getResourceMap().get(ResourceType.Fungi).intValue());
        Assertions.assertEquals(20, book.getResourceMap().get(ResourceType.Insect).intValue());
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
        Assertions.assertEquals(5, book.getResourceMap().get(ResourceType.Fungi).intValue());

        // Verifichiamo che il numero di Animal sia rimasto invariato
        Assertions.assertEquals(2, book.getResourceMap().get(ResourceType.Animal).intValue());
        Assertions.assertEquals(1, book.getResourceMap().get(ResourceType.Plant).intValue());
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
    void testAddResourceCard(){
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
    void TestCheckGoldCornerCondition(){
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
    void testCheckGoldPoints(){
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
    void TestAddGoldCard() throws PlacementConditionViolated {
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
    void TestAddCard(){
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
        //creo una istanza di carta Obiettivo12 che sia di tipo ResourceCondition (CASE victoryPoints=3)
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
    void testCheckDiagonalPlacement() {
        //creo una istanza di carta Obiettivo0 che sia di tipo DiagonalPlacementCondition (Diagonale secondaria- Fungi)
        List<SymbolType> symbolList0 = new ArrayList<>();
        ObjectiveCard objectiveCard0 = new ObjectiveCard(0,true,GoalType.DiagonalPlacement,2, ResourceType.Fungi, CornerType.BLCorner, 0, 0, symbolList0, null);

        //creo una istanza di carta Obiettivo1 che sia di tipo DiagonalPlacementCondition (Diagonale principale- Plant)
        ObjectiveCard objectiveCard1 = new ObjectiveCard(1,true,GoalType.DiagonalPlacement,2, ResourceType.Plant, CornerType.BRCorner, 0, 0, symbolList0, null);

        Book book = new Book(70, 70);

        //DIAGONALE SECONDARIA
        //Creo istanza InitialCard0 fittizia e la aggiungo al Book
        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Fungi);
        List<ResourceType> centralResources2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        InitialCard initialCardTest2 = new InitialCard(1,4,true,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.WithResource,CornerLabel.Empty,centralResources2,1,2,resourceList2);
        book.addInitial(initialCardTest2); //ho aggiunto la carta initial al centro del Book

        //Creo ResourceCard0 e la aggiungo al Book
        List<ResourceType> resourceList0 = new ArrayList<>();
        resourceList0.add(ResourceType.Fungi);
        resourceList0.add(ResourceType.Fungi);
        ResourceCard resourceCard0 = new ResourceCard(0,3,true,CardType.ResourceCard,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.NoCorner,CornerLabel.WithResource ,ResourceType.Fungi,0,2,resourceList0,false, null);
        book.addResourceCard(resourceCard0, book.getBookMatrix()[34][36]); //aggiungo al book la resoureCard

        //Creo ResourceBackCard1 e la aggiungo al Book
        List<ResourceType> resourceList1 = new ArrayList<>();
        ResourceCard resourceCard1 = new ResourceCard(1,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList1,false, null);
        //Cell cell1 = new Cell(33,37);
        book.addResourceCard(resourceCard1, book.getBookMatrix()[33][37]); //aggiungo al book la resoureCard

        //Creo ResourceBackCard2 e la aggiungo al Book
        List<ResourceType> resourceList = new ArrayList<>();
        ResourceCard resourceCard2 = new ResourceCard(2,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList,false, null);
        //Cell cell2 = new Cell(32,38);
        book.addResourceCard(resourceCard2, book.getBookMatrix()[32][38]); //aggiungo al book la resoureCard

        assertEquals(2, book.checkDiagonalPlacement(objectiveCard0));

        // Aggiungi le carte sulla diagonale secondaria
        int row = 31; // Parti dalla riga 31
        int col = 39; // Parti dalla colonna 39

        // Aggiungi le carte ResourceBack INSECT una per una sulla diagonale secondaria
        for (int i = 30; i <= 39; i++) {
            // Crea 10 ResourceCardBck con mainResource "Insect" e aggiungila al Book
            List<ResourceType> resourceList3 = new ArrayList<>();
            ResourceCard resourceCard = new ResourceCard(i, 4, false, CardType.ResourceCard, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, ResourceType.Insect, 0, 0, resourceList3, false, null);
            book.addResourceCard(resourceCard, book.getBookMatrix()[row][col]);
            row--; // Sposta la riga verso l'alto
            col++; // Sposta la colonna verso destra
        }

        //Aggiungo una ResourceBack FUNGI
        List<ResourceType> resourceList4 = new ArrayList<>();
        ResourceCard resourceCard3 = new ResourceCard(3,4,false,CardType.ResourceCard,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty,CornerLabel.Empty ,ResourceType.Fungi,0,0,resourceList4,false, null);
        book.addResourceCard(resourceCard3, book.getBookMatrix()[row][col]); //aggiungo al book la resoureCard
        row--; // Sposta la riga verso l'alto
        col++; // Sposta la colonna verso destra

        assertEquals(2, book.checkDiagonalPlacement(objectiveCard0));

        // Aggiungi le carte ResourceBack ANIMAL una per una sulla diagonale secondaria
        for (int j = 20; j <= 29; j++) {
            // Crea 10 ResourceCardBck con mainResource "Animal" e aggiungila al Book
            List<ResourceType> resourceList5 = new ArrayList<>();
            ResourceCard resourceCard = new ResourceCard(j, 4, false, CardType.ResourceCard, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, ResourceType.Animal, 0, 0, resourceList5, false, null);
            book.addResourceCard(resourceCard, book.getBookMatrix()[row][col]);
            row--; // Sposta la riga verso l'alto
            col++; // Sposta la colonna verso destra
        }

        // Aggiungi le restanti carte ResourceBack FUNGI (6) una per una sulla diagonale secondaria
        for (int k = 4; k <= 9; k++) {
            // Crea 10 ResourceCardBck con mainResource "Animal" e aggiungila al Book
            List<ResourceType> resourceList6 = new ArrayList<>();
            ResourceCard resourceCard = new ResourceCard(k, 4, false, CardType.ResourceCard, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, ResourceType.Fungi, 0, 0, resourceList6, false, null);
            book.addResourceCard(resourceCard, book.getBookMatrix()[row][col]);
            row--; // Sposta la riga verso l'alto
            col++; // Sposta la colonna verso destra
        }

        // Aggiungi le carte ResourceBack PLANT una per una sulla diagonale secondaria
        int z=10;
        List<ResourceType> resourceList7 = new ArrayList<>();
        while (row>0 && col<70){ //finchè non arrivo al limite superiore della matrice
            ResourceCard resourceCard = new ResourceCard(z, 4, false, CardType.ResourceCard, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, ResourceType.Plant, 0, 0, resourceList7, false, null);
            book.addResourceCard(resourceCard, book.getBookMatrix()[row][col]);
            z++;
            row--; // Sposta la riga verso l'alto
            col++; // Sposta la colonna verso destra
        }
        assertEquals(row,0); //controllo di essere arrivato sulla riga 0 della matrice
        //aggiungo l'ultima separatamente (RIGA==0)
        ResourceCard resourceCard = new ResourceCard(z, 4, false, CardType.ResourceCard, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, CornerLabel.Empty, ResourceType.Plant, 0, 0, resourceList7, false, null);
        book.addResourceCard(resourceCard, book.getBookMatrix()[row][col]);

        assertEquals(6, book.checkDiagonalPlacement(objectiveCard0));


    }

    @Test
    void testCheckLPlacement() {
        Book book = new Book(5, 5);
    }





}
