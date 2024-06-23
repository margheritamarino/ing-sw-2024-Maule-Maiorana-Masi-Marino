package it.polimi.ingsw.model.game;

import java.io.Serializable;


/**
 * Enum representing the status of the game.
 */
public enum GameStatus implements Serializable {
    WAIT,         // Waiting for players to join or waiting for the game to start
    RUNNING,      // Game is currently running
    LAST_CIRCLE,  // Last round or last turn of the game
    ENDED         // Game has ended

}
