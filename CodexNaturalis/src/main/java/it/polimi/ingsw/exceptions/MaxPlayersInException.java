package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when the maximum number of players in a game is reached.
 */
public class MaxPlayersInException extends Exception {

    /**
     * Constructs a new MaxPlayersInException with a default detail message indicating that the maximum number of players has been reached.
     */
    public MaxPlayersInException() {
        super("Max number of players is reached");
    }
}
