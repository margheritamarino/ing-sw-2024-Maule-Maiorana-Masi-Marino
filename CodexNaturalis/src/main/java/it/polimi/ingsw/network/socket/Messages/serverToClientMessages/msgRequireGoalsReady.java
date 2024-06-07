package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.rmi.RemoteException;

public class msgRequireGoalsReady extends ServerGenericMessage {
    private GameImmutable model;
    private int index;

    public msgRequireGoalsReady(GameImmutable model, int index) {
        this.model = model;
        this.index=index;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is a remote exception
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.requireGoalsReady(model, index);
    }
}
