package it.polimi.ingsw.exceptions;


/**
 * Exception thrown when a placement condition is violated during gameplay.
 */
public class PlacementConditionViolated extends Exception{

    /**
     * Constructs a new PlacementConditionViolated exception with the specified detail message.
     *
     * @param message the detail message
     */
    public PlacementConditionViolated(String message) {
        super(message);
    }
}
