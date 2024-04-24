package it.polimi.ingsw.modelTest;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.CornerType;
import it.polimi.ingsw.model.CornerLabel;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.CornerLabel;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.PlayableCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PlayableCardTest {
    private Deck goldCardsDeck;
    private  Deck resourceCardsDeck;
    private  Deck initialCardsDeck;
    private ArrayList<PlayableCard> goldCardsFront = new ArrayList<>();
    private ArrayList<PlayableCard> goldCardsBack = new ArrayList<>();
    private ArrayList<PlayableCard> resourceCardsFront = new ArrayList<>();
    private ArrayList<PlayableCard> resourceCardsBack = new ArrayList<>();
    private ArrayList<PlayableCard> initialCardsFront = new ArrayList<>();
    private ArrayList<PlayableCard> initialCardsBack = new ArrayList<>();

    @Test
    void testInitializeGoldDeck() throws FileNotFoundException, FileReadException {
        // Creazione di un oggetto Deck
        goldCardsDeck= new Deck(CardType.GoldCard);
        goldCardsFront = goldCardsDeck.getFrontCards();
        goldCardsBack = goldCardsDeck.getBackCards();

        //verifico il front della carta GoldCard 0
        assertTrue(goldCardsFront.get(0).getCardID() == 0);
        assertTrue(goldCardsFront.get(0).isFront());
        assertTrue(goldCardsFront.get(0).getCardType() == CardType.GoldCard);
        assertTrue(goldCardsFront.get(0).getVictoryPoints() == 1);
        assertTrue(goldCardsFront.get(0).getMainResource() == ResourceType.Fungi);
        assertTrue(goldCardsFront.get(0).getNumCorners() == 3);
        assertTrue(goldCardsFront.get(0).hasSymbol());
        assertTrue(goldCardsFront.get(0).getSymbol() == SymbolType.Quill);
        assertTrue(goldCardsFront.get(0).getTLCorner()== CornerLabel.NoCorner);
        assertTrue(goldCardsFront.get(0).getTRCorner() == CornerLabel.Empty);
        assertTrue(goldCardsFront.get(0).getBRCorner() == CornerLabel.WithSymbol);
        assertTrue(goldCardsFront.get(0).getBLCorner() == CornerLabel.Empty);
        assertTrue(goldCardsFront.get(0).getPlacementCondition().get(0) == ResourceType.Fungi);
        assertTrue(goldCardsFront.get(0).getPlacementCondition().get(1) == ResourceType.Fungi);
        assertTrue(goldCardsFront.get(0).getPlacementCondition().get(2) == ResourceType.Animal);
        assertTrue(goldCardsFront.get(0).isPointsCondition());
        assertTrue(goldCardsFront.get(0).getSymbolCondition() == SymbolType.Quill);
        assertTrue(!goldCardsFront.get(0).isCornerCondition());


        //verifico il back della GoldCard 0
        assertTrue(goldCardsBack.get(0).getCardID() == 0);
        assertFalse(goldCardsBack.get(0).isFront());
        assertTrue(goldCardsBack.get(0).getCardType() == CardType.GoldCard);
        assertTrue(goldCardsBack.get(0).getVictoryPoints() == 0);
        assertTrue(goldCardsBack.get(0).getMainResource() == ResourceType.Fungi);
        assertTrue(goldCardsBack.get(0).getNumCorners() == 4);
        assertTrue(!goldCardsBack.get(0).hasSymbol());
        assertTrue(goldCardsBack.get(0).getSymbol() == null);
        assertTrue(goldCardsBack.get(0).getTLCorner()== CornerLabel.Empty);
        assertTrue(goldCardsBack.get(0).getTRCorner() == CornerLabel.Empty);
        assertTrue(goldCardsBack.get(0).getBRCorner() == CornerLabel.Empty);
        assertTrue(goldCardsBack.get(0).getBLCorner() == CornerLabel.Empty);
        assertTrue(goldCardsBack.get(0).getPlacementCondition() == null);
        assertTrue(!goldCardsBack.get(0).isPointsCondition());
        assertTrue(goldCardsBack.get(0).getSymbolCondition() == null);
        assertTrue(!goldCardsBack.get(0).isCornerCondition());
    }

    @Test
    void testInitializeResourceDeck() throws FileNotFoundException, FileReadException {
        // Creazione di un oggetto Deck
        resourceCardsDeck= new Deck(CardType.ResourceCard);
        resourceCardsFront = resourceCardsDeck.getFrontCards();
        resourceCardsBack = resourceCardsDeck.getBackCards();

        //verifico il front della carta resource Card 0
        assertTrue(resourceCardsFront.get(0).getCardID() == 0);
        assertTrue(resourceCardsFront.get(0).isFront());
        assertTrue(resourceCardsFront.get(0).getCardType() == CardType.ResourceCard);
        assertTrue(resourceCardsFront.get(0).getVictoryPoints() == 0);
        assertTrue(resourceCardsFront.get(0).getNumCorners() == 3);
        assertTrue(resourceCardsFront.get(0).getMainResource() == ResourceType.Fungi);

        ResourceType actualResource = resourceCardsFront.get(0).getResourceList().get(0); //verifica
        ResourceType expectedResource = ResourceType.Fungi; //verifica
        assertEquals(expectedResource, actualResource, "Expected resource: " + expectedResource + ", Actual resource: " + actualResource); //verifica

        assertTrue(resourceCardsFront.get(0).getNumResources() == 2);
        assertTrue(resourceCardsFront.get(0).getResourceList().get(0) == ResourceType.Fungi);
        assertTrue(resourceCardsFront.get(0).getResourceList().get(1) == ResourceType.Fungi);
        assertTrue(!resourceCardsFront.get(0).hasSymbol());
        assertTrue(resourceCardsFront.get(0).getSymbol() == null);
        assertTrue(resourceCardsFront.get(0).getTLCorner()== CornerLabel.WithResource);
        assertTrue(resourceCardsFront.get(0).getTRCorner() == CornerLabel.Empty);
        assertTrue(resourceCardsFront.get(0).getBRCorner() == CornerLabel.NoCorner);
        assertTrue(resourceCardsFront.get(0).getBLCorner() == CornerLabel.WithResource);


        //verifico il back della Resource Card 0
        assertTrue(resourceCardsBack.get(0).getCardID() == 0);
        assertTrue(!resourceCardsBack.get(0).isFront());
        assertTrue(resourceCardsBack.get(0).getCardType() == CardType.ResourceCard);
        assertTrue(resourceCardsBack.get(0).getVictoryPoints() == 0);
        assertTrue(resourceCardsBack.get(0).getNumCorners() == 4);
        assertTrue(resourceCardsBack.get(0).getMainResource() == ResourceType.Fungi);
        assertTrue(resourceCardsBack.get(0).getNumResources() == 0);
        assertTrue(resourceCardsBack.get(0).getResourceList() == null);
        assertTrue(!resourceCardsBack.get(0).hasSymbol());
        assertTrue(resourceCardsBack.get(0).getSymbol() == null);
        assertTrue(resourceCardsBack.get(0).getTLCorner()== CornerLabel.Empty);
        assertTrue(resourceCardsBack.get(0).getTRCorner() == CornerLabel.Empty);
        assertTrue(resourceCardsBack.get(0).getBRCorner() == CornerLabel.Empty);
        assertTrue(resourceCardsBack.get(0).getBLCorner() == CornerLabel.Empty);
    }

    @Test
    void testInitializeInitialDeck() throws FileNotFoundException, FileReadException {
        // Creazione di un oggetto Deck
        initialCardsDeck= new Deck(CardType.InitialCard);
        initialCardsFront = initialCardsDeck.getFrontCards();
        initialCardsBack = initialCardsDeck.getBackCards();

        //verifico il front della carta INITIAL Card 0
        assertTrue(initialCardsFront.get(0).getCardID() == 0);
        assertTrue(initialCardsFront.get(0).isFront());
        assertTrue(initialCardsFront.get(0).getCardType() == CardType.InitialCard);
        assertTrue(initialCardsFront.get(0).getNumCorners() == 4);
        assertTrue(initialCardsFront.get(0).getTLCorner()== CornerLabel.Empty);
        assertTrue(initialCardsFront.get(0).getTRCorner() == CornerLabel.WithSymbol);
        assertTrue(initialCardsFront.get(0).getBRCorner() == CornerLabel.Empty);
        assertTrue(initialCardsFront.get(0).getBLCorner() == CornerLabel.WithSymbol);
        assertTrue(initialCardsFront.get(0).getResourceList().get(0) == ResourceType.Plant);
        assertTrue(initialCardsFront.get(0).getResourceList().get(1) == ResourceType.Insect);
        assertTrue(initialCardsFront.get(0).getNumCentralResources() == 1);
        assertTrue(initialCardsFront.get(0).getNumResources() == 2);
        assertTrue(initialCardsFront.get(0).getCentralResources().get(0) == ResourceType.Insect);

        //verifico il back della INITIAL Card 0
        assertTrue(initialCardsBack.get(0).getCardID() == 0);
        assertTrue(!initialCardsBack.get(0).isFront());
        assertTrue(initialCardsBack.get(0).getCardType() == CardType.InitialCard);
        assertTrue(initialCardsBack.get(0).getNumCorners() == 4);
        assertTrue(initialCardsBack.get(0).getTLCorner()== CornerLabel.WithResource);
        assertTrue(initialCardsBack.get(0).getTRCorner() == CornerLabel.WithResource);
        assertTrue(initialCardsBack.get(0).getBRCorner() == CornerLabel.WithResource);
        assertTrue(initialCardsBack.get(0).getBLCorner() == CornerLabel.WithResource);
        assertTrue(initialCardsBack.get(0).getResourceList().get(0) == ResourceType.Fungi);
        assertTrue(initialCardsBack.get(0).getResourceList().get(1) == ResourceType.Plant);
        assertTrue(initialCardsBack.get(0).getResourceList().get(2) == ResourceType.Animal);
        assertTrue(initialCardsBack.get(0).getResourceList().get(3) == ResourceType.Insect);
        assertTrue(initialCardsBack.get(0).getNumCentralResources() == 0);
        assertTrue(initialCardsBack.get(0).getNumResources() == 4);
        assertTrue(initialCardsBack.get(0).getCentralResources() == null);
    }


}
