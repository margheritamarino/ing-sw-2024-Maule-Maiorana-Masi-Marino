package it.polimi.ingsw.exceptions;


/**
 * Exception thrown when an operation requires players but none are available.
 */
public class NoPlayersException extends Exception {

    /**
     * Constructs a new NoPlayersException with the specified detail message.
     *
     * @param message the detail message
     */
    public NoPlayersException(String message) {
        super(message);
    }
}