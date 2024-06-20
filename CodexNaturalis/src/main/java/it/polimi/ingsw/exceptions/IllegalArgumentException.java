package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when an illegal or inappropriate argument is passed to a method.
 */
public class IllegalArgumentException extends RuntimeException{

    /**
     * Constructs a new IllegalArgumentException with the specified detail message.
     *
     * @param message the detail message
     */
    public IllegalArgumentException(String message) {
        super(message);
    }
}
