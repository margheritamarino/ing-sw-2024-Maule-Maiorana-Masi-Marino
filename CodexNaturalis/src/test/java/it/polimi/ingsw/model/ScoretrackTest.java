package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidPointsException;
import it.polimi.ingsw.exceptions.NoPlayersException;
import it.polimi.ingsw.exceptions.PlayerNotFoundException;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoretrackTest {

    private ScoreTrack scoreTrack;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        scoreTrack = new ScoreTrack();
        player1 = new Player("Player 1",Color.RED);
        player2 = new Player("Player 2",Color.YELLOW);
    }

    @Test
    public void testAddPlayer() {
        scoreTrack.addPlayer(player1);
        assertEquals(0, scoreTrack.getPlayerScore(player1)); //quando si aggiunge un nuovo giocatore il suo punteggio deve essere zero
    }

    @Test
    public void testRemovePlayer() throws PlayerNotFoundException {
        scoreTrack.addPlayer(player1);
        scoreTrack.removePlayer(player1);

        assertFalse(scoreTrack.checkPlayerExists(player1)); // Restituisce false se il giocatore non è più nel tracker dei punteggi
    }

    @Test
    public void testAddPoints() {
        scoreTrack.addPlayer(player1);
        int initialPoints = 5; //punti che il giocatore ha già al turno precedente, anche 0
        scoreTrack.setPlayerScore(player1, initialPoints);
        scoreTrack.addPoints(player1, 10); //aggiungo dei nuovi punti e verifico che vengono aggiunti a quelli precedenti
        assertEquals(initialPoints + 10, scoreTrack.getPlayerScore(player1));
    }

    @Test
    public void testGetPlayerScore() {
        scoreTrack.addPlayer(player1);
        scoreTrack.addPlayer(player2);
        scoreTrack.addPoints(player1, 5);
        scoreTrack.addPoints(player2, 10);
        assertEquals(5, scoreTrack.getPlayerScore(player1));
        assertEquals(10, scoreTrack.getPlayerScore(player2));
    }

    @Test
    public void testSetPlayerScore() {
        scoreTrack.addPlayer(player1);
        scoreTrack.setPlayerScore(player1, 15);
        assertEquals(15, scoreTrack.getPlayerScore(player1));
    }

    @Test
    public void testCheckTo20() {
        scoreTrack.addPlayer(player1);
        scoreTrack.addPlayer(player2);
        scoreTrack.addPoints(player2, 15); //il giocatore 2 non ha raggiunto i 20 punti
        assertFalse(scoreTrack.checkTo20());
        scoreTrack.addPoints(player1, 20);
        assertTrue(scoreTrack.checkTo20());
    }

    @Test
    public void testGetWinner() {
        scoreTrack.addPlayer(player1);
        scoreTrack.addPlayer(player2);
        try {
            scoreTrack.addPoints(player1, 22);
            scoreTrack.addPoints(player2, 20);
            assertEquals(player1, scoreTrack.getWinner());
        } catch (NoPlayersException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
