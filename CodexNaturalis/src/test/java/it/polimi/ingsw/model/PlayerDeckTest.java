package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.DeckFullException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.PlayerDeck;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class PlayerDeckTest {

    @Test
    public void testAddCardValid() throws FileNotFoundException, FileReadException, DeckEmptyException, DeckFullException {
        PlayerDeck playerDeck = new PlayerDeck();
        Deck goldDeck = new Deck(CardType.GoldCard);

        PlayableCard[] newCard = goldDeck.returnCard();
        PlayableCard frontCard = newCard[0];
        PlayableCard backCard = newCard[1];

        if (playerDeck.getNumCards() < 6)
            playerDeck.addCard(newCard);
        else
            throw new DeckFullException("PlayerDeck is full");


        // Verifica che la carta sia stata aggiunta correttamente
        assertEquals(2, playerDeck.getNumCards());
        assertEquals(frontCard, playerDeck.getMiniDeck().get(0));
        assertEquals(backCard, playerDeck.getMiniDeck().get(1));
    }

    @Test
    public void testAddCardDeckFull() throws FileNotFoundException, FileReadException, DeckEmptyException, DeckFullException {
        PlayerDeck deck = new PlayerDeck();
        Deck goldDeck = new Deck(CardType.GoldCard);
        PlayableCard[] newCard = goldDeck.returnCard();

        // Aggiungi tre carte per riempire il mazzo
        deck.addCard(newCard);
        deck.addCard(newCard);
        deck.addCard(newCard);

        // Verifica che il mazzo sia pieno
        assertEquals(6, deck.getNumCards());

        // Prova ad aggiungere una carta quando il mazzo è pieno
        assertThrows(DeckFullException.class, () -> deck.addCard(newCard));
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
        PlayerDeck deck = new PlayerDeck();

        // Aggiungi una carta al mazzo
        Deck goldDeck = new Deck(CardType.GoldCard);
        PlayableCard[] newCard = goldDeck.returnCard();
        deck.addCard(newCard);
        PlayableCard frontCard = newCard[0];
        PlayableCard backCard = newCard[1];
        assertEquals(frontCard, deck.getMiniDeck().get(0));
        assertEquals(backCard, deck.getMiniDeck().get(1));

        assertEquals(2, deck.getNumCards());
        assertEquals(2, deck.getMiniDeck().size() );
        deck.addCard(newCard);
        assertEquals(4, deck.getNumCards());
        assertEquals(4, deck.getMiniDeck().size() );

        assertEquals(frontCard, deck.getMiniDeck().get(2));
        assertEquals(backCard, deck.getMiniDeck().get(3));


       assertThrows(IndexOutOfBoundsException.class, () -> deck.removeCard(6));
       assertThrows(IndexOutOfBoundsException.class, () -> deck.removeCard(8));

     deck.removeCard(2);
     assertEquals(2, deck.getNumCards());
     assertEquals(frontCard, deck.getMiniDeck().get(0));
     assertEquals(backCard, deck.getMiniDeck().get(1));

     deck.removeCard(1);
     assertTrue(deck.getMiniDeck().isEmpty());

    }
}