package it.polimi.ingsw.network.socket.client.messages;

import java.io.IOException;
import java.io.Serializable;
import it.polimi.ingsw.listener.GameListener;


/**
 * SocketServerGenericMessage class.
 * An abstract class that represents a generic message to be sent from the server to the client.
 */
public abstract class MessageServerToClient implements Serializable {
    /**
     * Executes the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an IO exception
     * @throws InterruptedException if the execution is interrupted
     */
    public abstract void execute(GameListener lis) throws IOException, InterruptedException;

}
