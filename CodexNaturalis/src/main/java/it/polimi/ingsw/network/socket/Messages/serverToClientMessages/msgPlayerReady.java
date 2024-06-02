package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.rmi.RemoteException;

public class msgPlayerReady extends ServerGenericMessage {
    private final GameImmutable model;
    private final String nickname;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgPlayerReady(GameImmutable model, String nickname) {
        this.model = model;
        this.nickname = nickname;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.playerReady(model, nickname);
    }

}

