package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.exceptions.IllegalArgumentException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.cards.CardType;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


//DA FARE
public class GameTest {

    private Game game;

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> {
            Game game = new Game(3);
        });

        // Verifica che il costruttore sollevi un'eccezione per un numero non valido di giocatori
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Game game = new Game(0);
        });
        assertEquals("The number of players must be between 1 and 4.", exception.getMessage());
    }

    @Test
    public void testGetInstance() {
        // Verifica che sia restituita un'istanza non nulla per un numero valido di giocatori
        Game game = Game.getInstance(4);
        assertNotNull(game);

        // Verifica che venga restituita la stessa istanza per lo stesso numero di giocatori
        Game game2 = Game.getInstance(4);
        assertEquals(game, game2);

        // Verifica che venga restituita una nuova istanza per un diverso numero di giocatori
        Game game3 = Game.getInstance(2);
        assertNotEquals(game, game3);
    }

    @Test
    public void testCheckNickname() throws FileNotFoundException, FileReadException, IllegalArgumentException, DeckEmptyException, NicknameAlreadyTaken, MatchFull {
        //Ambiente con due giocatori
        Game game = new Game(2);

        game.addPlayer("Player1");
        // Verifica che il controllo del nickname funzioni correttamente
        assertFalse(game.checkNickname("Player1"));
        // Tentativo di aggiungere un secondo giocatore con lo stesso nickname
        try {
            game.addPlayer("Player1");
            fail("Expected NicknameAlreadyTaken exception to be thrown");
        } catch (NicknameAlreadyTaken e) {
            // Eccezione attesa: il nickname è già stato preso
            assertEquals("Player1", e.getNickname());
        }

         }

    @Test
    public void testIsFull() throws NicknameAlreadyTaken, MatchFull, FileNotFoundException, FileReadException, IllegalArgumentException, DeckEmptyException {
        Game game = new Game(4);

        try {
            game.addPlayer("Player1");
            game.addPlayer("Player2");
            game.addPlayer("Player3");
            game.addPlayer("Player4");
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        // Verifica che il gioco sia pieno
        assertTrue(game.isFull());

        // Tentativo di aggiungere un altro giocatore quando il gioco è già pieno
        try {
            game.addPlayer("Player5");
            fail("Expected MatchFull exception to be thrown");
        } catch (MatchFull e) {
            // Eccezione attesa: il gioco è pieno
            assertEquals("There are already 4 players", e.getMessage());
            System.out.println("MatchFull exception caught: " + e.getMessage());
        } catch (Exception e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }

}
