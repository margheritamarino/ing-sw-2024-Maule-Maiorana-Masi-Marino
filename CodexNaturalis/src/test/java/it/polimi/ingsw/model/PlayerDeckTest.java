package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.DeckFullException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.PlayerDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PlayerDeckTest {


    private Deck goldDeck;

    PlayerDeck playerDeck;
    @BeforeEach
    void setup() throws FileNotFoundException, FileReadException {
        playerDeck = new PlayerDeck();
        goldDeck = new Deck(CardType.GoldCard);
    }

    @Test
    public void testAddCardValid() throws FileNotFoundException, FileReadException, DeckEmptyException, DeckFullException {
        PlayableCard[] newCard = goldDeck.returnCard();
        PlayableCard frontCard = newCard[0];
        PlayableCard backCard = newCard[1];

        if (playerDeck.getNumCards() < 6)
            playerDeck.addCard(newCard);
        else
            throw new DeckFullException("PlayerDeck is full");


        // Verifica che la carta sia stata aggiunta correttamente
        assertEquals(2, playerDeck.getNumCards());
        assertEquals(frontCard, playerDeck.getMiniDeck().get(0)[0]);
        assertEquals(backCard, playerDeck.getMiniDeck().get(0)[1]);
    }


    @Test
    public void testAddCardDeckFull() throws FileNotFoundException, FileReadException, DeckEmptyException, DeckFullException {
        PlayableCard[] newCard = goldDeck.returnCard();
        PlayableCard[] newCard1 = goldDeck.returnCard();
        PlayableCard[] newCard2 = goldDeck.returnCard();
        PlayableCard[] newCard3 = goldDeck.returnCard();

        // Aggiungi tre carte per riempire il mazzo
        playerDeck.addCard(newCard);
        playerDeck.addCard(newCard1);
        playerDeck.addCard(newCard2);

        // Verifica che il mazzo sia pieno
        assertEquals(6, playerDeck.getNumCards());

        // Prova ad aggiungere una carta quando il mazzo è pieno
        assertThrows(DeckFullException.class, () -> playerDeck.addCard(newCard3));
    }

    @Test
    public void testRemoveCardValid() throws DeckEmptyException, FileNotFoundException, FileReadException, DeckFullException {
        PlayerDeck deck = new PlayerDeck();
        Deck goldDeck = new Deck(CardType.GoldCard);
        PlayableCard[] newCard = goldDeck.returnCard();

        // Aggiungi una carta al mazzo
        deck.addCard(newCard);
        assertEquals(2, deck.getNumCards());

        // Rimuovi la carta dal mazzo
        deck.removeCard(1);
        assertEquals(0, deck.getNumCards());
    }

    @Test
    public void testRemoveMoreCards() throws DeckEmptyException, FileNotFoundException, FileReadException, DeckFullException {
        // Aggiungi una carta al mazzo
        PlayableCard[] newCard1 = goldDeck.returnCard();
        playerDeck.addCard(newCard1);

        // Aggiungi una seconda carta al mazzo
        PlayableCard[] newCard2 = goldDeck.returnCard();
        playerDeck.addCard(newCard2);



        //Verifica eccezioni per posizioni fuori limite
        assertThrows(IndexOutOfBoundsException.class, () -> playerDeck.removeCard(6));
        assertThrows(IndexOutOfBoundsException.class, () -> playerDeck.removeCard(8));

        // Rimuovi carte alle posizioni 2-3 (frontCard2 e backCard2)
        playerDeck.removeCard(2);
        assertEquals(2, playerDeck.getNumCards());
        assertEquals(1, playerDeck.getMiniDeck().size());

        // Rimuovi carte alle posizioni 0-1 (frontCard1 e backCard1)
        playerDeck.removeCard(1);
        assertEquals(0, playerDeck.getNumCards());
        assertTrue(playerDeck.getMiniDeck().isEmpty());


    }

}