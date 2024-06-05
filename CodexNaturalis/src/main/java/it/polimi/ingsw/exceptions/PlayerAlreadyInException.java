package it.polimi.ingsw.exceptions;

/**
 * This exception is thrown when a player is already in the game
 */
public class PlayerAlreadyInException extends Exception {
    public PlayerAlreadyInException() {
        super("Player already in exception");
    }
}