package it.polimi.ingsw.exceptions;


/**
 * Exception thrown when an action is attempted on a cell that is not available.
 */
public class CellNotAvailableException extends Exception {

    /**
     * Constructs a new CellNotAvailableException with the specified detail message.
     *
     * @param message the detail message
     */
    public CellNotAvailableException(String message) {
        super(message);
    }
}
