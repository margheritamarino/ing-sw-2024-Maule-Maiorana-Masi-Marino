package it.polimi.ingsw.exceptions;


/**
 * Exception thrown when an attempt is made to add a player who is already in the game.
 */
public class PlayerAlreadyInException extends Exception {

    /**
     * Constructs a new PlayerAlreadyInException with a default detail message indicating that the player is already in the game.
     */
    public PlayerAlreadyInException() {
        super("Player already in exception");
    }
}