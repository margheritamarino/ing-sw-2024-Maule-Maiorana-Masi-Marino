package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PlayableCardTest {
    @Test
    void testInitializeGoldDeck() throws FileNotFoundException, FileReadException {
        Deck goldCardsDeck;
        ArrayList<PlayableCard> goldCardsFront;
        ArrayList<PlayableCard> goldCardsBack;
        // Creazione di un oggetto Deck
        goldCardsDeck= new Deck(CardType.GoldCard);
        goldCardsFront = goldCardsDeck.getFrontCards();
        goldCardsBack = goldCardsDeck.getBackCards();

        //verifico il front della carta GoldCard 0
        assertEquals(0, goldCardsFront.get(0).getCardID());
        assertTrue(goldCardsFront.get(0).isFront());
        assertSame(goldCardsFront.get(0).getCardType(), CardType.GoldCard);
        assertEquals(1, goldCardsFront.get(0).getVictoryPoints());
        assertSame(goldCardsFront.get(0).getMainResource(), ResourceType.Fungi);
        assertEquals(3, goldCardsFront.get(0).getNumCorners());
        assertTrue(goldCardsFront.get(0).hasSymbol());
        assertSame(goldCardsFront.get(0).getSymbol(), SymbolType.Quill);
        assertSame(goldCardsFront.get(0).getTLCorner(), CornerLabel.NoCorner);
        assertSame(goldCardsFront.get(0).getTRCorner(), CornerLabel.Empty);
        assertSame(goldCardsFront.get(0).getBRCorner(), CornerLabel.WithSymbol);
        assertSame(goldCardsFront.get(0).getBLCorner(), CornerLabel.Empty);
        assertSame(goldCardsFront.get(0).getPlacementCondition().get(0), ResourceType.Fungi);
        assertSame(goldCardsFront.get(0).getPlacementCondition().get(1), ResourceType.Fungi);
        assertSame(goldCardsFront.get(0).getPlacementCondition().get(2), ResourceType.Animal);
        assertTrue(goldCardsFront.get(0).isPointsCondition());
        assertSame(goldCardsFront.get(0).getSymbolCondition(), SymbolType.Quill);
        assertFalse(goldCardsFront.get(0).isCornerCondition());


        //verifico il back della GoldCard 0
        assertEquals(0, goldCardsBack.get(0).getCardID());
        assertFalse(goldCardsBack.get(0).isFront());
        assertSame(goldCardsBack.get(0).getCardType(), CardType.GoldCard);
        assertEquals(0, goldCardsBack.get(0).getVictoryPoints());
        assertSame(goldCardsBack.get(0).getMainResource(), ResourceType.Fungi);
        assertEquals(4, goldCardsBack.get(0).getNumCorners());
        assertFalse(goldCardsBack.get(0).hasSymbol());
        assertNull(goldCardsBack.get(0).getSymbol());
        assertSame(goldCardsBack.get(0).getTLCorner(), CornerLabel.Empty);
        assertSame(goldCardsBack.get(0).getTRCorner(), CornerLabel.Empty);
        assertSame(goldCardsBack.get(0).getBRCorner(), CornerLabel.Empty);
        assertSame(goldCardsBack.get(0).getBLCorner(), CornerLabel.Empty);
        assertTrue(goldCardsBack.get(0).getPlacementCondition().isEmpty());
        assertFalse(goldCardsBack.get(0).isPointsCondition());
        assertNull(goldCardsBack.get(0).getSymbolCondition());
        assertFalse(goldCardsBack.get(0).isCornerCondition());
    }

    @Test
    void testInitializeResourceDeck() throws FileNotFoundException, FileReadException {
        Deck resourceCardsDeck;
        ArrayList<PlayableCard> resourceCardsFront;
        ArrayList<PlayableCard> resourceCardsBack;
        // Creazione di un oggetto Deck
        resourceCardsDeck= new Deck(CardType.ResourceCard);
        resourceCardsFront = resourceCardsDeck.getFrontCards();
        resourceCardsBack = resourceCardsDeck.getBackCards();

        //verifico il front della carta resource Card 0
        assertEquals(0, resourceCardsFront.get(0).getCardID());
        assertTrue(resourceCardsFront.get(0).isFront());
        assertSame(resourceCardsFront.get(0).getCardType(), CardType.ResourceCard);
        assertEquals(0, resourceCardsFront.get(0).getVictoryPoints());
        assertEquals(3, resourceCardsFront.get(0).getNumCorners());
        assertSame(resourceCardsFront.get(0).getMainResource(), ResourceType.Fungi);

        ResourceType actualResource = resourceCardsFront.get(0).getResourceList().get(0); //verifica
        ResourceType expectedResource = ResourceType.Fungi; //verifica
        assertEquals(expectedResource, actualResource, "Expected resource: " + expectedResource + ", Actual resource: " + actualResource); //verifica

        assertEquals(2, resourceCardsFront.get(0).getNumResources());
        assertSame(resourceCardsFront.get(0).getResourceList().get(0), ResourceType.Fungi);
        assertSame(resourceCardsFront.get(0).getResourceList().get(1), ResourceType.Fungi);
        assertFalse(resourceCardsFront.get(0).hasSymbol());
        assertNull(resourceCardsFront.get(0).getSymbol());
        assertSame(resourceCardsFront.get(0).getTLCorner(), CornerLabel.WithResource);
        assertSame(resourceCardsFront.get(0).getTRCorner(), CornerLabel.Empty);
        assertSame(resourceCardsFront.get(0).getBRCorner(), CornerLabel.NoCorner);
        assertSame(resourceCardsFront.get(0).getBLCorner(), CornerLabel.WithResource);


        //verifico il back della Resource Card 0
        assertEquals(0, resourceCardsBack.get(0).getCardID());
        assertFalse(resourceCardsBack.get(0).isFront());
        assertSame(resourceCardsBack.get(0).getCardType(), CardType.ResourceCard);
        assertEquals(0, resourceCardsBack.get(0).getVictoryPoints());
        assertEquals(4, resourceCardsBack.get(0).getNumCorners());
        assertSame(resourceCardsBack.get(0).getMainResource(), ResourceType.Fungi);
        assertEquals(0, resourceCardsBack.get(0).getNumResources());
        assertTrue(resourceCardsBack.get(0).getResourceList().isEmpty());
        assertFalse(resourceCardsBack.get(0).hasSymbol());
        assertNull(resourceCardsBack.get(0).getSymbol());
        assertSame(resourceCardsBack.get(0).getTLCorner(), CornerLabel.Empty);
        assertSame(resourceCardsBack.get(0).getTRCorner(), CornerLabel.Empty);
        assertSame(resourceCardsBack.get(0).getBRCorner(), CornerLabel.Empty);
        assertSame(resourceCardsBack.get(0).getBLCorner(), CornerLabel.Empty);
    }

    @Test
    void testInitializeInitialDeck() throws FileNotFoundException, FileReadException {
        Deck initialCardsDeck;
        ArrayList<PlayableCard> initialCardsFront;
        ArrayList<PlayableCard> initialCardsBack;
        // Creazione di un oggetto Deck
        initialCardsDeck= new Deck(CardType.InitialCard);
        initialCardsFront = initialCardsDeck.getFrontCards();
        initialCardsBack = initialCardsDeck.getBackCards();

        //verifico il front della carta INITIAL Card 0
        assertEquals(0, initialCardsFront.get(0).getCardID());
        assertTrue(initialCardsFront.get(0).isFront());
        assertSame(initialCardsFront.get(0).getCardType(), CardType.InitialCard);
        assertEquals(4, initialCardsFront.get(0).getNumCorners());
        assertSame(initialCardsFront.get(0).getTLCorner(), CornerLabel.Empty);
        assertSame(initialCardsFront.get(0).getTRCorner(), CornerLabel.WithSymbol);
        assertSame(initialCardsFront.get(0).getBRCorner(), CornerLabel.Empty);
        assertSame(initialCardsFront.get(0).getBLCorner(), CornerLabel.WithSymbol);
        assertSame(initialCardsFront.get(0).getResourceList().get(0), ResourceType.Plant);
        assertSame(initialCardsFront.get(0).getResourceList().get(1), ResourceType.Insect);
        assertEquals(1, initialCardsFront.get(0).getNumCentralResources());
        assertEquals(2, initialCardsFront.get(0).getNumResources());
        assertSame(initialCardsFront.get(0).getCentralResources().get(0), ResourceType.Insect);

        //verifico il back della INITIAL Card 0
        assertEquals(0, initialCardsBack.get(0).getCardID());
        assertFalse(initialCardsBack.get(0).isFront());
        assertSame(initialCardsBack.get(0).getCardType(), CardType.InitialCard);
        assertEquals(4, initialCardsBack.get(0).getNumCorners());
        assertSame(initialCardsBack.get(0).getTLCorner(), CornerLabel.WithResource);
        assertSame(initialCardsBack.get(0).getTRCorner(), CornerLabel.WithResource);
        assertSame(initialCardsBack.get(0).getBRCorner(), CornerLabel.WithResource);
        assertSame(initialCardsBack.get(0).getBLCorner(), CornerLabel.WithResource);
        assertSame(initialCardsBack.get(0).getResourceList().get(0), ResourceType.Fungi);
        assertSame(initialCardsBack.get(0).getResourceList().get(1), ResourceType.Plant);
        assertSame(initialCardsBack.get(0).getResourceList().get(2), ResourceType.Animal);
        assertSame(initialCardsBack.get(0).getResourceList().get(3), ResourceType.Insect);
        assertEquals(0, initialCardsBack.get(0).getNumCentralResources());
        assertEquals(4, initialCardsBack.get(0).getNumResources());
        assertTrue(initialCardsBack.get(0).getCentralResources().isEmpty());
    }

    @Test
    void testGetCornerContentGold() {
        // Creazione di una GoldCard fittizia

        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);


        GoldCard goldCardTest = new GoldCard(0,3,true,CardType.GoldCard, CornerLabel.NoCorner,CornerLabel.Empty,CornerLabel.WithSymbol,CornerLabel.Empty, ResourceType.Fungi,true,SymbolType.Quill,1,resourceList,true,false, SymbolType.Quill);

        assertEquals("NoCorner", goldCardTest.getCornerContent(0));
        assertEquals("Empty", goldCardTest.getCornerContent(1));
        assertEquals("Quill", goldCardTest.getCornerContent(2));
        assertEquals("Empty", goldCardTest.getCornerContent(3));
    }

    @Test
    void testGetCornerContentResource() {
        // Creazione di una resourceCard fittizia

        List<ResourceType> resourceList = new ArrayList<>();
        resourceList.add(ResourceType.Fungi);
        resourceList.add(ResourceType.Animal);

        ResourceCard resourceCardTest = new ResourceCard(5,3,true,CardType.ResourceCard,CornerLabel.WithSymbol,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.NoCorner,ResourceType.Fungi,0,2,resourceList,true,SymbolType.Ink);

        assertEquals("Ink", resourceCardTest.getCornerContent(0));
        assertEquals("Fungi", resourceCardTest.getCornerContent(1));
        assertEquals("Animal", resourceCardTest.getCornerContent(2));
        assertEquals("NoCorner", resourceCardTest.getCornerContent(3));
    }

    @Test
    void testGetCornerContentInitial() {
        // Creazione di una initialCard fittizia

        List<ResourceType> resourceList1 = new ArrayList<>();
        resourceList1.add(ResourceType.Plant);
        resourceList1.add(ResourceType.Insect);

        List<ResourceType> centralResources1 = new ArrayList<>();
        resourceList1.add(ResourceType.Insect);

        InitialCard initialCardTest1 = new InitialCard(0,4,true,CardType.InitialCard,CornerLabel.Empty,CornerLabel.WithResource,CornerLabel.Empty,CornerLabel.WithResource,centralResources1,1,2,resourceList1);

        assertEquals("Empty", initialCardTest1.getCornerContent(0));
        assertEquals("Plant", initialCardTest1.getCornerContent(1));
        assertEquals("Empty", initialCardTest1.getCornerContent(2));
        assertEquals("Insect", initialCardTest1.getCornerContent(3));


        List<ResourceType> resourceList2 = new ArrayList<>();
        resourceList2.add(ResourceType.Fungi);
        resourceList2.add(ResourceType.Plant);
        resourceList2.add(ResourceType.Animal);
        resourceList2.add(ResourceType.Insect);

        List<ResourceType> centralResources2 = new ArrayList<>();
        InitialCard initialCardTest2 = new InitialCard(0,4,false,CardType.InitialCard,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,CornerLabel.WithResource,centralResources2,0,4,resourceList2);

        assertEquals("Fungi", initialCardTest2.getCornerContent(0));
        assertEquals("Plant", initialCardTest2.getCornerContent(1));
        assertEquals("Animal", initialCardTest2.getCornerContent(2));
        assertEquals("Insect", initialCardTest2.getCornerContent(3));
    }


}
