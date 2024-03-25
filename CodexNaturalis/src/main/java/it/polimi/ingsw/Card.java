package it.polimi.ingsw;
import java.util.*;

public abstract class Card {
    protected int cardID;
    private Map<Corner,Card> cornersMap;
    private  int numCorners;
    //private Corner corner;
    private ResourceType mainResource;
    private int numResources;
    private int numSymbols;
    private List<ResourceType> kingdoms;
    private List<SymbolType> symbols;

    //costruttore
    public Card(int cardId){
        this.cardID = cardID;
        this.mainResource = setRandomMainResource();
        this.cornersMap = new HashMap<>();
    }
    public int getCardID() {
        return cardID;
    }


    public void setCornersMap(Map<Corner, Card> cornersMap) {
        this.cornersMap = cornersMap;
    }

    public Corner createCorner(CornerType cornerType,){
        corner = new Corner(cornerType);
        return corner;
    }

    // Aggiungi una carta collegata a un angolo specifico
    public void addConnectedCard(Corner corner, Card connectedCard) {
        cornersMap.put(corner, connectedCard);
    }
    public Card getConnectedCard(Corner corner) {
        return cornersMap.get(corner);
    }


    /**
     * @author Margherita Marino, Martina Maiorana
     * given the number of corners, this method creates the required number of corners, with a randomic CornerType
     * @param numCorners number of corners in card
     */

    private void setRandomCornersMap(int numCorners) {
        List<CornerType> cornerTypes = new ArrayList<>();
        Collections.addAll(cornerTypes, CornerType.values());
        Collections.shuffle(cornerTypes);
        int i;
        for (i = 1; i <= numCorners; i++) {
            this.cornersMap.put(new Corner(cornerTypes.get(i), null, null), null);
        }
    }



    private ResourceType setRandomMainResource() {
        List<ResourceType> resourceTypes = new ArrayList<>();
        Collections.addAll(resourceTypes, ResourceType.values());
        Collections.shuffle(resourceTypes);
        return resourceTypes.get(0);
    }

    public ResourceType getMainResource() {
        return mainResource;
    }
}
