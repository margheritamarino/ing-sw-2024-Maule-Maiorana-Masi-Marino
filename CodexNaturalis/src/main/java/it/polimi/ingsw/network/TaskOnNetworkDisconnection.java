package it.polimi.ingsw.network;

import java.util.TimerTask;


import it.polimi.ingsw.view.flow.*;

/**
 * Task scheduled to run when a network disconnection timeout occurs.
 * It invokes the {@code noConnectionError()} method on the provided {@link Flow} instance.
 */
public class TaskOnNetworkDisconnection extends TimerTask {
    private Flow flow;

    /**
     * Constructs a new {@code TaskOnNetworkDisconnection} with the specified {@link Flow} instance.
     *
     * @param flow The Flow instance to notify on network disconnection.
     */
    public TaskOnNetworkDisconnection(Flow flow){
        this.flow=flow;
    }

    /**
     * Executes the task action by invoking {@code noConnectionError()} on the associated Flow instance.
     */
    public void run() {
        flow.noConnectionError();
    }

}
