package it.polimi.ingsw.model.cards;

import java.io.Serializable;

/**
 * This enum represents the different types of labels
 * that can be assigned to the corners of cards in the game.
 */
public enum CornerLabel implements Serializable {
    WithResource,
    WithSymbol,
    Empty,
    NoCorner
}
