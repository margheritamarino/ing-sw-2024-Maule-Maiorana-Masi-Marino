package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.CornerType;
import it.polimi.ingsw.model.cards.GoalType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
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
    void testSetGoal() {
        // Creating a sample ObjectiveCard
        ObjectiveCard objectiveCard = new ObjectiveCard(
                1, true, GoalType.ResourceCondition, 10, ResourceType.Fungi, CornerType.BRCorner,
                3, 3, new ArrayList<>(),
                ResourceType.Animal);
        // numResources, numSymbols, symbols, secondResource);
        //
        // Setting the goal for the player
        player.setGoal(objectiveCard);

        // Retrieving the goal and checking if it matches the one set
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

