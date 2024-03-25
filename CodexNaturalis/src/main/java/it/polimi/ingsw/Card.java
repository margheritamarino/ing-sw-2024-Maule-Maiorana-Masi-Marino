package it.polimi.ingsw;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public abstract class Card {
    protected int cardID;
    private Corner corner;
    private Map<Corner,Card> cornersMap;
    private  int numCorners;

    private ResourceType mainResource;
    private int numResources;
    private int numSymbols;
    private List<ResourceType> kingdoms;
    private List<SymbolType> symbols;

    //costruttore
    public Card(int cardId){
        this.cardID = cardID;
        this.cornersMap = new HashMap<>();
    }
    public int getCardID() {
        return cardID;
    }

    public int getNumResources() {
        return numResources;
    }

    public int getNumSymbols() {
        return numSymbols;
    }

    public void setNumResources(int numResources) {
        this.numResources = numResources;
    }

    public void setNumSymbols(int numSymbols) {
        this.numSymbols = numSymbols;
    }

    public ResourceType getMainResource() {
        return mainResource;
    }

    public void setMainResource(ResourceType mainResource) {
        this.mainResource = mainResource;
    }

    public int getNumCorners() {
        return numCorners;
    }

    public void setNumCorners(int numCorners) {
        this.numCorners = numCorners;
    }

    public Map<Corner, Card> getCornersMap() {
        return cornersMap;
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
}
