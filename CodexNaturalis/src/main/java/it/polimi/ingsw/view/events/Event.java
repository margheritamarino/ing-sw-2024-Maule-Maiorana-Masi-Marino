package it.polimi.ingsw.view.events;

import it.polimi.ingsw.model.game.GameImmutable;


//rappresenta eventi che comunicano
public class Event {
    private GameImmutable model;
    private EventType type;


    public Event(GameImmutable model, EventType type) {
        this.model = model;
        this.type = type;
    }

    public GameImmutable getModel() {
        return model;
    }

    public EventType getType() {
        return type;
    }

}
