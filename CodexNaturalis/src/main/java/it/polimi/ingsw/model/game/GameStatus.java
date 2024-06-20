package it.polimi.ingsw.model.game;

import java.io.Serializable;

/**
 * This enum represents the game status
 */
public enum GameStatus implements Serializable {
    WAIT,
    RUNNING,
    LAST_CIRCLE,
    ENDED

}
