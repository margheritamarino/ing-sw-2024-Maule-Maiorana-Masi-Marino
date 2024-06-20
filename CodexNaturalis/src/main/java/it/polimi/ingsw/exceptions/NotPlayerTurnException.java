package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when a player attempts to take an action outside their turn.
 */
public class NotPlayerTurnException extends Exception {

    /**
     * Constructs a new NotPlayerTurnException with the specified detail message.
     *
     * @param message the detail message
     */
    public NotPlayerTurnException(String message) {
        super(message);
    }
}
