package it.polimi.ingsw;
import java.util.Map;
import java.util.List;

public abstract class Card {
    private int cardID;
    private Map<Corner,Card> cornersMap;
    private  int numCorners;
    private ResourceType mainResource;
    private int numResources;
    private int numSymbols;
    private List<ResourceType> kingdoms;
    private List<SymbolType> symbols;


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

    public List<Resource> getKingdom() {
        return kingdoms;
    }

}
