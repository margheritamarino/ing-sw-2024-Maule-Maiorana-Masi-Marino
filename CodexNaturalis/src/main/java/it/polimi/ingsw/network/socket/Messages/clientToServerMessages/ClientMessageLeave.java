package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.rmi.RemoteException;

/**
 * SocketClientMessageLeave class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating the request to leave a game.
 */
public class ClientMessageLeave extends ClientGenericMessage {

    String nickname;

    /**
     * Constructor of the class.
     * @param nickname the player's nickname
     */
    public ClientMessageLeave(String nickname) {
        this.nickname = nickname;
        this.isJoinGame= true;
    }


    @Override
    public void execute(GameListenerInterface lis, GameController gameController) throws RemoteException {
        gameController.leave(lis, nickname);
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {

    }

}
