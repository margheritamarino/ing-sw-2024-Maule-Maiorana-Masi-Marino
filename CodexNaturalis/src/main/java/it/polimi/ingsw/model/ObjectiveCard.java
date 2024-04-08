package it.polimi.ingsw.model;
import java.util.*;
import it.polimi.ingsw.model.Card;

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
