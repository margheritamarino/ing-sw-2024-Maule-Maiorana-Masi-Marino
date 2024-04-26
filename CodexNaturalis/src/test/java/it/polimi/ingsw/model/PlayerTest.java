package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.DeckFullException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerDeck;
import it.polimi.ingsw.model.player.PlayerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private Board board;

    @BeforeEach
    void setUp() throws FileNotFoundException, FileReadException, DeckEmptyException {
        player = new Player("TestPlayer");
        board = new Board();

    }

    @Test
    void testGetNickname() {
        assertEquals("TestPlayer", player.getNickname());
    }

    @Test
    void testPlayerInitialState() {
        assertEquals(PlayerState.Start, player.getPlayerState());
    }

    @Test
    void testConnectionStatus() {
        Player player = new Player("Player1");
        assertFalse(player.isConnected());
        player.setConnected(true);
        assertTrue(player.isConnected());
    }

    @Test
    void testSetGoal() throws FileNotFoundException, FileReadException, DeckEmptyException {
        // Creating a sample ObjectiveCard
        ObjectiveDeck deck = new ObjectiveDeck();
        ObjectiveCard objCard = deck.returnCard();


        // Setting the goal for the player
        player.setGoal(objCard);

        // Retrieving the goal and checking if it matches the one set
        assertEquals(objCard, player.getGoal());
    }

    @Test
    void testPlayerBookAndDeck() {
        Player player = new Player("Player1");
        assertNotNull(player.getPlayerBook());
        assertNotNull(player.getPlayerDeck());
    }

    @Test
    void testPickCard() throws FileNotFoundException, FileReadException, DeckEmptyException {
        // Inizializza gli oggetti necessari per il test
        Board board = new Board();
        CardType cardType = CardType.ResourceCard; // Assicurati di inizializzare correttamente cardType
        boolean drawFromDeck = true;
        int pos = 0;

        // Testing picking a card from the board
        assertDoesNotThrow(() -> player.pickCard(board, CardType.GoldCard, true, 0));

        // Testing picking a card from an invalid position
        assertThrows(IndexOutOfBoundsException.class, () -> player.pickCard(board, CardType.ResourceCard, false, -1));

        // Verifica che il metodo lanci IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            player.pickCard(board, CardType.InitialCard, false, 0);
        });
    }
    @Test
    public void testPickCardDeckEmpty() throws FileNotFoundException, FileReadException, DeckEmptyException, DeckFullException {
        Board board = new Board();
        while (!board.getResourcesCardsDeck().checkEndDeck())
            board.getResourcesCardsDeck().returnCard();

        // Verifica che il metodo lanci DeckEmptyException
        assertThrows(DeckEmptyException.class, () -> {
            player.pickCard(board, CardType.ResourceCard, true, 0);
        });
    }
    @Test
    public void testPickCardDeckFull() throws FileNotFoundException, FileReadException, DeckEmptyException, DeckFullException {
        Board board = new Board();

        // Riempie il mazzo del giocatore fino al limite massimo
        while (player.getPlayerDeck().getNumCards()<6){
            player.pickCard(board, CardType.ResourceCard, true, 0);
        }

        // Verifica che il metodo lanci DeckFullException
        assertThrows(DeckFullException.class, () -> {
            player.pickCard(board, CardType.ResourceCard, true, 0);
        });
    }
    @Test
    void testPlaceCard() throws DeckEmptyException, DeckFullException, FileNotFoundException, FileReadException {
        // Setup - Creazione del giocatore e delle dipendenze necessarie
        Player player = new Player("Player1");
        Book book = player.getPlayerBook();
        PlayerDeck playerDeck = player.getPlayerDeck();
        Deck InitialDeck = new Deck(CardType.InitialCard);
        PlayableCard[] initCard= InitialDeck.returnCard();
        book.addInitial(initCard[1]);
    // Aggiungi celle disponibili alla Book
        ArrayList<Cell> availableCells = book.showAvailableCells();
        assertEquals(initCard[1].getNumCorners(), availableCells.size());

        // Aggiungi carte al deck del giocatore
        PlayableCard[] card1 = board.takeCardfromBoard(CardType.GoldCard, true, 0);
        PlayableCard[] card2 = board.takeCardfromBoard(CardType.ResourceCard, true, 0);
        PlayableCard[] card3 = board.takeCardfromBoard(CardType.ResourceCard, true, 0);
        playerDeck.addCard(card1); //0 fronte, 1: retro
        playerDeck.addCard(card2); //2 fronte - 3 retro
        playerDeck.addCard(card3); //4 fronte - 6 retro

        //caso 1 posizionare la prima carta (fronte - posCard=0 ) nella prima cella
        int posCell1 = 0; //scelgo la cella in posizione 0 dell'array AvailableCells
        Cell chosenCell= availableCells.get(posCell1);
        int posCard1 = 0;
        PlayableCard chosenCard  = playerDeck.getMiniDeck().get(posCard1);

        player.placeCard(posCell1, posCard1);

        // Verifica che la carta sia stata rimossa dal deck del giocatore
        assertFalse(playerDeck.getMiniDeck().contains(card1));
        assertEquals(4, playerDeck.getNumCards());


        // Caso 3: verificare che venga lanciata un'eccezione per posizioni di cella o carta non valide
        assertThrows(IndexOutOfBoundsException.class, () -> {
            player.placeCard(-1, 0); // posizione della cella non valida
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            player.placeCard(0, -1); // posizione della carta non valida
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            player.placeCard(availableCells.size() + 1, 0); // posizione della cella fuori gamma
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            player.placeCard(0, playerDeck.getMiniDeck().size() + 1); // posizione della carta fuori gamma
        });
    }
}

