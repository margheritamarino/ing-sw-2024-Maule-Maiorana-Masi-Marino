package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when there is an error in setting up the game board.
 */
public class BoardSetupException extends Exception{

    /**
     * Constructs a new BoardSetupException with the specified detail message.
     *
     * @param message the detail message
     */
    public BoardSetupException(String message) {
        super(message);
    }
}
