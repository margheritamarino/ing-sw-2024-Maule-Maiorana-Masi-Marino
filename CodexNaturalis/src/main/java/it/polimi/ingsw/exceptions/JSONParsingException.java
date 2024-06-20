package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when an error occurs during JSON parsing.
 * Extends FileReadException, indicating it is related to file reading operations.
 */
public class JSONParsingException  extends FileReadException {

    /**
     * Constructs a new JSONParsingException with the specified detail message.
     *
     * @param message the detail message
     */
    public JSONParsingException(String message) {
        super(message);
    }
}
