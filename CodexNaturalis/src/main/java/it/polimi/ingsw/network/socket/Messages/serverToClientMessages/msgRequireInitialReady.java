package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;
import java.rmi.RemoteException;

public class msgRequireInitialReady extends ServerGenericMessage {
    private GameImmutable model;
    private int index;

    public msgRequireInitialReady(GameImmutable model, int index) {
        this.model = model;
        this.index=index;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is a remote exception
     */
    @Override
    public void execute(GameListenerInterface lis) throws IOException {
        lis.requireInitialReady(model, index);
    }
}
