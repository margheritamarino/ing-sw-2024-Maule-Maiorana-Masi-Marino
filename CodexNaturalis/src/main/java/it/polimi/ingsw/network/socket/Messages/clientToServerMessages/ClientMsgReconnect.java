package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * SocketClientMessageReconnect class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating the request to reconnect to the last game joined.
 */
public class ClientMsgReconnect extends ClientGenericMessage {


    /**
     * Constructor of the class.
     * @param nick the player's nickname
     */
    public ClientMsgReconnect(String nick) {
        this.nickname = nick;
        this.isJoinGame= true; //?? giusto??
    }


    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @param gameController the main controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis, GameController gameController) throws RemoteException {
        System.out.println("MSG: ClientMsgReconnect - execute()\n ");
        gameController.reconnect(lis, this.nickname);
    }


    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {

    }
}
