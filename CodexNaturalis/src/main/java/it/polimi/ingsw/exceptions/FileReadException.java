package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when an error occurs while reading a file.
 */
public class FileReadException extends Exception {

    /**
     * Constructs a new FileReadException with the specified detail message.
     *
     * @param message the detail message
     */
    public FileReadException(String message) {
        super(message);
    }
}
