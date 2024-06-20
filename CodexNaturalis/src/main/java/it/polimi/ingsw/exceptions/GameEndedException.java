package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when an operation is attempted on a game that has already ended.
 */
public class GameEndedException extends Exception{

    /**
     * Constructs a new GameEndedException with no detail message.
     */
    public GameEndedException() {
        super();
    }
}
