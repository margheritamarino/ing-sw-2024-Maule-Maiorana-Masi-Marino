package it.polimi.ingsw.listener;

import java.util.ArrayList;
import java.util.List;


/**
 * ListenersHandler class is responsible for managing a List of GameListener objects
 * and for notifying the view when a change occurs in the GameModel
 */
public class ListenersHandler {
    private List<GameListener> listeners;

    /**
     * Constructor
     */
    public ListenersHandler() {
        listeners = new ArrayList<>();
    }



}
