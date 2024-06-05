package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;
import java.rmi.RemoteException;

public class msgRequireInitialReady extends ServerGenericMessage {
    private GameImmutable model;

    public msgRequireInitialReady(GameImmutable model) {
        this.model = model;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is a remote exception
     */
    @Override
    public void execute(GameListenerInterface lis) throws IOException, FileReadException {
        lis.requireInitialReady(model);
    }
}
