package it.polimi.ingsw.model.interfaces;

import it.polimi.ingsw.model.SymbolType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.cards.GoalType;
import it.polimi.ingsw.model.cards.CornerType;
import java.util.*;

public interface ObjectiveCardIC {

     int getCardID();

     boolean isFront();

     GoalType getGoalType();


     int getVictoryPoints();

     int getNumSymbols();

     List<SymbolType> getSymbols();

     int getNumResources();

     ResourceType getMainResource();

     CornerType getDirection();

     ResourceType getSecondResource();



}
