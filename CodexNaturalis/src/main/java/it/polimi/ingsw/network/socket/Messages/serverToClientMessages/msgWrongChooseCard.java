package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;
import java.rmi.RemoteException;

public class msgWrongChooseCard extends ServerGenericMessage{
    private GameImmutable model;
    private String msg;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgWrongChooseCard(GameImmutable model, String msg) {
        this.model = model;
        this.msg= msg;
    }

    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.wrongChooseCard(model, msg);
    }
}
