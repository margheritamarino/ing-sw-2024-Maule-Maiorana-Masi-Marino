package it.polimi.ingsw;
import java.util.Map;
import java.util.List;

public abstract class Card {
    private int CardID;
    private Map<Corner,Card> cornersMap;
    private int numResources;
    private int numSymbols;
    private List<Resource> kingdom;
    private List<Symbol> symbols;


    public void setCardID(int cardID) {
        CardID = cardID;
    }

    public int getCardID() {
        return CardID;
    }

    public int getNumResources() {
        return numResources;
    }

    public int getNumSymbols() {
        return numSymbols;
    }

}
