package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.rmi.RemoteException;

public class msgCardPlaced extends ServerGenericMessage {
    private GameImmutable model;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgCardPlaced(GameImmutable model) {
        this.model = model;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.cardPlaced(model);
    }
}
