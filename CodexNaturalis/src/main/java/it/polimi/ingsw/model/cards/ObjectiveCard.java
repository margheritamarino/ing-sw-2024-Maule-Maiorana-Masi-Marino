package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.interfaces.ObjectiveCardIC;


import java.util.List;

public class ObjectiveCard implements ObjectiveCardIC {
    private int cardID;
    private boolean isFront;
    private GoalType goalType;
    private int victoryPoints;
    private ResourceType mainResource;
    private CornerType direction;
    private int numResources;
    private int numSymbols;
    private List<SymbolType> symbols;
    private ResourceType secondResource;

    public int getCardID() {
        return cardID;
    }

    public boolean isFront() {
        return isFront;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getNumSymbols() {
        return numSymbols;
    }

    public List<SymbolType> getSymbols() {
        return symbols;
    }

    public int getNumResources() {
        return numResources;
    }

    public ResourceType getMainResource() {
        return mainResource;
    }

    public CornerType getDirection() {
        return direction;
    }

    public ResourceType getSecondResource() {
        return secondResource;
    }


    public ObjectiveCard(int cardID, boolean isFront, GoalType goalType, int victoryPoints, ResourceType mainResource, CornerType direction, int numResources, int numSymbols, List<SymbolType> symbols, ResourceType secondResource) {
        this.cardID = cardID;
        this.isFront = isFront;
        this.goalType = goalType;
        this.victoryPoints = victoryPoints;
        this.numSymbols = numSymbols;
        this.symbols = symbols;
        this.numResources = numResources;
        this.mainResource = mainResource;
        this.direction = direction;
        this.secondResource = secondResource;
    }
}
