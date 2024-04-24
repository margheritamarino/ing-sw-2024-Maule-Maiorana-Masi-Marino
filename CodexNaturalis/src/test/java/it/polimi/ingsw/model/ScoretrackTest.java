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
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
    }

    @Test
    public void testAddPlayer() {
        scoreTrack.addPlayer(player1);
        assertEquals(0, scoreTrack.getPlayerScore(player1));
    }

    @Test
    public void testRemovePlayer() throws PlayerNotFoundException {
        scoreTrack.addPlayer(player1);
        scoreTrack.removePlayer(player1);

        assertFalse(scoreTrack.checkPlayerExists(player1)); // Verifica che il giocatore sia stato rimosso correttamente
    }

    @Test
    public void testAddPoints() {
        scoreTrack.addPlayer(player1);
        try {
            scoreTrack.addPoints(player1, 10);
            assertEquals(10, scoreTrack.getPlayerScore(player1));
        } catch (PlayerNotFoundException | InvalidPointsException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetPlayerScore() {
        scoreTrack.addPlayer(player1);
        scoreTrack.addPlayer(player2);
        try {
            scoreTrack.addPoints(player1, 5);
            scoreTrack.addPoints(player2, 10);
            assertEquals(5, scoreTrack.getPlayerScore(player1));
            assertEquals(10, scoreTrack.getPlayerScore(player2));
        } catch (PlayerNotFoundException | InvalidPointsException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
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
        try {
            scoreTrack.addPoints(player1, 20);
            assertTrue(scoreTrack.checkTo20());
        } catch (PlayerNotFoundException | InvalidPointsException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetWinner() {
        scoreTrack.addPlayer(player1);
        scoreTrack.addPlayer(player2);
        try {
            scoreTrack.addPoints(player1, 15);
            scoreTrack.addPoints(player2, 10);
            assertEquals(player1, scoreTrack.getWinner());
        } catch (PlayerNotFoundException | InvalidPointsException | NoPlayersException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
