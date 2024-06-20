package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when an invalid cast is attempted while reading a file.
 */
public class FileCastException extends FileReadException{

    /**
     * Constructs a new FileCastException with the specified detail message.
     *
     * @param message the detail message
     */
    public FileCastException(String message) {
        super(message);
    }
}
