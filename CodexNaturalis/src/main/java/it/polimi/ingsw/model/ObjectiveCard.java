package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Card;

import java.util.List;

public class ObjectiveCard {
    private int cardID;
    private boolean isFront;
    private CardType cardType;
    private GoalType goalType;
    private int victoryPoints;
    private int numSymbols;
    private List<SymbolType> symbols;
    private int numResources;
    private ResourceType mainResource;
    private CornerType direction;
    private ResourceType secondResource;

}
