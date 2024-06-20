package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when an action is attempted by a player who is not part of the game.
 */
public class ActionByAPlayerNotInTheGameException extends RuntimeException{
    /**
     * Constructs a new ActionByAPlayerNotInTheGameException with no detail message.
     */
    public ActionByAPlayerNotInTheGameException(){
        super();
    }
}
