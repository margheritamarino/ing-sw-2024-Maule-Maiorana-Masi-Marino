package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectiveCardTest {

    private ObjectiveCard objectiveCard;
    private int cardID = 0;
    private boolean isFront = true;
    private GoalType goalType = GoalType.DiagonalPlacement; // Sostituisci con un valore valido di GoalType
    private int victoryPoints =2;
    private int numSymbols = 0;
    private List<SymbolType> symbols = new ArrayList<>();
    private int numResources = 0;
    private ResourceType mainResource = ResourceType.Fungi; // Sostituisci con un valore valido di ResourceType
    private CornerType direction = CornerType.BLCorner; // Sostituisci con un valore valido di CornerType
    private ResourceType secondResource = null; // Sostituisci con un valore valido di ResourceType

    @Before
    public void setUp() {
        // Inizializza un oggetto ObjectiveCard con valori di esempio
        objectiveCard = new ObjectiveCard(cardID, isFront, goalType, victoryPoints, mainResource, direction, numResources, numSymbols, symbols, secondResource);
        }

    @Test
    public void testConstructor() {
        // Verifica che il costruttore abbia inizializzato correttamente gli attributi
        assertEquals(cardID, objectiveCard.getCardID());
        assertEquals(isFront, objectiveCard.isFront());
        assertEquals(goalType, objectiveCard.getGoalType());
        assertEquals(victoryPoints, objectiveCard.getVictoryPoints());
        assertEquals(numSymbols, objectiveCard.getNumSymbols());
        assertEquals(symbols, objectiveCard.getSymbols());
        assertEquals(numResources, objectiveCard.getNumResources());
        assertEquals(mainResource, objectiveCard.getMainResource());
        assertEquals(direction, objectiveCard.getDirection());
        assertEquals(secondResource, objectiveCard.getSecondResource());
    }
    @Test
    public void testInitializeObjectiveDeck() throws FileNotFoundException, FileReadException {
        // Creazione di un oggetto Deck
        ObjectiveDeck deck = new ObjectiveDeck();
        ArrayList<ObjectiveCard> frontCards= deck.getFrontCards();

        //verifico il front della carta GoldCard 0
        assertTrue(frontCards.get(0).getCardID() == 0);
        assertTrue(frontCards.get(0).isFront());
        assertTrue(frontCards.get(0).getVictoryPoints() == 2);
        assertTrue(frontCards.get(0).getGoalType() == GoalType.DiagonalPlacement);
        assertTrue(frontCards.get(0).getMainResource() == ResourceType.Fungi);
        assertTrue(frontCards.get(0).getDirection() == CornerType.BLCorner);
        assertTrue(frontCards.get(0).getNumResources() == 0);
        assertTrue(frontCards.get(0).getNumSymbols() == 0);

        assertTrue(frontCards.get(0).getSymbols().isEmpty());
        assertTrue(frontCards.get(0).getSecondResource() == null);

    }
    }

