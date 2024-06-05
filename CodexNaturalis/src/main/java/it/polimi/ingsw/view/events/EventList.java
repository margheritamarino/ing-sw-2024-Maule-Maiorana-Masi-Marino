package it.polimi.ingsw.view.events;

import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.game.GameStatus;

import java.util.ArrayDeque;
import java.util.Queue;

public class EventList {

    private Queue<Event> lists;
    private boolean joined = false;

    /**
     * Init
     */
    public EventList() {
        lists = new ArrayDeque<>();
    }

    /**
     * Adds a new event to the list
     * @param model
     * @param type
     */
    public synchronized void add(GameImmutable model, EventType type) {
        lists.add(new Event(model, type));

        if (type.equals(EventType.PLAYER_JOINED) || (model != null && (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE)) ))
            joined = true;

        if(type.equals(EventType.BACK_TO_MENU))
            joined = false;
    }


    public synchronized Event pop() {
        return lists.poll();
    }

    public synchronized int size() {
        return lists.size();
    }


    /**
     *
     * @return true if the player has joined the game, false if not
     */
    public synchronized boolean isJoined() {
        return joined;
    }
}
