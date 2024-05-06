package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.rmi.RemoteException;

/**
 * msgPlayerDisconnected class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has been disconnected from the game.
 */
public class msgPlayerDisconnected extends ServerGenericMessage {
    private String nickname;
    private GameImmutable model;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     * @param nickname the nickname of the disconnected player
     */
    public msgPlayerDisconnected(GameImmutable model,String nickname) {
        this.nickname = nickname;
        this.model=model;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.playerDisconnected(model,nickname);
    }
}
