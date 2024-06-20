package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when an attempt is made to add a card to a full deck.
 */
public class DeckFullException extends Exception{
    /**
     * Constructs a new DeckFullException with the specified detail message.
     *
     * @param message the detail message
     */
    public DeckFullException(String message) {
        super(message);
    }
}
