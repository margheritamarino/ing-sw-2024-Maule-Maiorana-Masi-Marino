package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when a specified player is not found.
 */
public class PlayerNotFoundException extends Exception {

    /**
     * Constructs a new PlayerNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
