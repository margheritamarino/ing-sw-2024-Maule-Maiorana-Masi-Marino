package it.polimi.ingsw.view.events;

import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.game.GameStatus;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Represents a list of events that can be queued and retrieved for processing.
 * It also tracks whether a player has joined the game based on certain events.
 */
public class EventList {

    private Queue<Event> lists;
    private boolean joined = false;

    /**
     * Initializes an empty event list using a {@code Queue}.
     */
    public EventList() {
        lists = new ArrayDeque<>();
    }

    /**
     * Adds a new event to the list.
     *
     * @param model The immutable game model associated with the event.
     * @param type  The type of the event to add.
     */
    public synchronized void add(GameImmutable model, EventType type) {
        lists.add(new Event(model, type));

        if (type.equals(EventType.PLAYER_JOINED) || (model != null && (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE)) ))
            joined = true;

        if(type.equals(EventType.BACK_TO_MENU))
            joined = false;
    }

    /**
     * Retrieves and removes the next event from the list.
     *
     * @return The next event in the list, or {@code null} if the list is empty.
     */
    public synchronized Event pop() {
        return lists.poll();
    }

    /**
     * Retrieves the number of events currently in the list.
     *
     * @return The number of events in the list.
     */
    public synchronized int size() {
        return lists.size();
    }

    /**
     * Checks if a player has joined the game based on recent events.
     *
     * @return {@code true} if the player has joined the game, {@code false} if not.
     */
    public synchronized boolean isJoined() {
        return joined;
    }
}
