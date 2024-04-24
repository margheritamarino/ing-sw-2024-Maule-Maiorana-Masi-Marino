package it.polimi.ingsw.model.game;

import java.io.Serializable;

public enum GameStatus implements Serializable {
    /**
     * This enum represents the game status
     */

    WAIT,
    RUNNING,
    LAST_CIRCLE,
    ENDED

}
