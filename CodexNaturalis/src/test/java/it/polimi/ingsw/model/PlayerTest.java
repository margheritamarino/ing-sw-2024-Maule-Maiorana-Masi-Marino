package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.CornerType;
import it.polimi.ingsw.model.cards.GoalType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private Board board;
    private Game game;

    @BeforeEach
    void setUp() throws FileNotFoundException, FileReadException, DeckEmptyException {
        game = new Game();  // Assumiamo che tu abbia una classe Game con un costruttore senza argomenti
        player = new Player("TestPlayer", Color.RED);  // Assumiamo che Color.RED sia un valore valido
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
    void testSetGoal() {
        // Creiamo una ObjectiveCard di esempio
        ObjectiveCard objectiveCard = new ObjectiveCard(
                1, true, GoalType.ResourceCondition, 10, ResourceType.Fungi, CornerType.BRCorner,
                3, 3, new ArrayList<>(), ResourceType.Animal);

        // Impostiamo l'obiettivo per il giocatore
        player.setGoal(objectiveCard);

        // Recuperiamo l'obiettivo e verifichiamo se corrisponde a quello impostato
        assertEquals(objectiveCard, player.getGoal());
    }



//TODO
    /*@Test
    void testPlaceCard() {
        // Assuming the player's deck has at least one card
        // Assuming the player's book has available cells

        // Testing placing a card in the player's book
        assertDoesNotThrow(() -> player.placeCard(0, 0));

        // Testing placing a card in an invalid cell position
        assertThrows(IndexOutOfBoundsException.class, () -> player.placeCard(-1, 0));

        // Testing placing a card in an invalid card position
        assertThrows(IndexOutOfBoundsException.class, () -> player.placeCard(0, -1));
    }*/

    // Add more tests as needed
}

