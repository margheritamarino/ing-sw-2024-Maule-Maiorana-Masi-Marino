package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when invalid points are encountered in a game or calculation.
 */
public class InvalidPointsException extends Exception{

    /**
     * Constructs a new InvalidPointsException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidPointsException(String message){
        super(message);
    }
}