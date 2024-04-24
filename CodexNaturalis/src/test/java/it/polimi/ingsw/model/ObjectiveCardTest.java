package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.CornerType;
import it.polimi.ingsw.model.cards.GoalType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class ObjectiveCardTest {

    private ObjectiveCard objectiveCard;
    private int cardID = 1;
    private boolean isFront = true;
    private GoalType goalType = GoalType.ResourceCondition; // Sostituisci con un valore valido di GoalType
    private int victoryPoints = 5;
    private int numSymbols = 3;
    private List<SymbolType> symbols = Arrays.asList(SymbolType.Quill, SymbolType.Ink, SymbolType.Manuscript); // Sostituisci con valori validi di SymbolType
    private int numResources = 2;
    private ResourceType mainResource = ResourceType.Plant; // Sostituisci con un valore valido di ResourceType
    private CornerType direction = CornerType.TRCorner; // Sostituisci con un valore valido di CornerType
    private ResourceType secondResource = ResourceType.Fungi; // Sostituisci con un valore valido di ResourceType

    @Before
    public void setUp() {
        // Inizializza un oggetto ObjectiveCard con valori di esempio
        objectiveCard = new ObjectiveCard(cardID, isFront, goalType, victoryPoints, numSymbols, symbols, numResources, mainResource, direction, secondResource);
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
}

