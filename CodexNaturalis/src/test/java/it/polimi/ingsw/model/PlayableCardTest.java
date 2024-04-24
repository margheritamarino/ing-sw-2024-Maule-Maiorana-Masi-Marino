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
        assertTrue(goldCardsBack.get(0).getPlacementCondition().isEmpty());
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
        assertTrue(resourceCardsBack.get(0).getResourceList().isEmpty());
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
