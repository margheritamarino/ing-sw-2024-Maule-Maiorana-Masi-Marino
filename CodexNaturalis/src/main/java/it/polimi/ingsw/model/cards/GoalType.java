package it.polimi.ingsw.model.cards;

import java.io.Serializable;

/**
 *  This enum represents the different types of goals
 *  that players can achieve in the game.
 */
public enum GoalType implements Serializable {
    ResourceCondition,
    SymbolCondition,
    DiagonalPlacement,
    LPlacement
}
