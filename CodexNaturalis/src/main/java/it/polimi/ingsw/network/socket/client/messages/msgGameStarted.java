package it.polimi.ingsw.network.socket.client.messages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.rmi.RemoteException;

/**
 * msgGameStarted class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that the game has started.
 */
public class msgGameStarted extends MessageServerToClient {
    private GameImmutable model;


    /**
     * Empty constructor of the class.
     * Used when the game model is not provided.
     */
    public msgGameStarted() {

    }

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgGameStarted(GameImmutable model) {
        this.model = model;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.gameStarted(model);
    }
}
