package it.polimi.ingsw.view.events;

import it.polimi.ingsw.model.game.GameImmutable;


/**
 * Represents an event that communicates changes or actions in the game.
 * Each event includes a reference to the immutable game model and the type of event.
 */
public class Event {
    private GameImmutable model;
    private EventType type;

    /**
     * Constructs an Event object with the provided game model and event type.
     *
     * @param model The immutable game model associated with the event.
     * @param type  The type of the event.
     */
    public Event(GameImmutable model, EventType type) {
        this.model = model;
        this.type = type;
    }

    /**
     * Retrieves the immutable game model associated with the event.
     *
     * @return The immutable game model.
     */
    public GameImmutable getModel() {
        return model;
    }

    /**
     * Retrieves the type of the event.
     *
     * @return The event type.
     */
    public EventType getType() {
        return type;
    }

}
