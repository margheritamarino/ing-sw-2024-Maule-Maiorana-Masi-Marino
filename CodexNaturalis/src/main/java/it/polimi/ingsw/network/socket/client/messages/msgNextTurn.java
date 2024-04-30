package it.polimi.ingsw.network.socket.client.messages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.rmi.RemoteException;

/**
 * msgNextTurn class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that it is the next turn in the game.
 */
public class msgNextTurn extends MessageServerToClient {
    private GameImmutable model;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgNextTurn(GameImmutable model) {
        this.model = model;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.nextTurn(model);
    }
}
