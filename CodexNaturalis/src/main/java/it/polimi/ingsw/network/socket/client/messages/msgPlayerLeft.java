package it.polimi.ingsw.network.socket.client.messages;


import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.game.GameImmutable;

import java.rmi.RemoteException;

/**
 * msgPlayerLeft class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has left the game.
 */
public class msgPlayerLeft extends MessageServerToClient {
    private GameImmutable model;
    private String nickname;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     * @param nickname the nickname of the player who left
     */
    public msgPlayerLeft(GameImmutable model,String nickname) {
        this.model = model;
        this.nickname=nickname;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is a remote exception
     */
    @Override
    public void execute(GameListener lis) throws RemoteException {
        lis.playerLeft(model,nickname);
    }

}
