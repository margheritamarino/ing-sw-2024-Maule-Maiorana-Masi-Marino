package it.polimi.ingsw.network.socket.client.mainControllerMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.rmi.MainControllerInterface;
import it.polimi.ingsw.network.socket.client.ClientGenericMessage;

import java.rmi.RemoteException;

/**
 * SocketClientMessageJoinFirst class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating the request to join the first available game.
 */
public class SocketClientMessageJoinFirst extends ClientGenericMessage {
    /**
     * Constructor of the class.
     * @param nickname the player's nickname
     */
    public SocketClientMessageJoinFirst(String nickname) {
        this.nickname = nickname;
        this.isMessageForMainController = true;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @param mainController the main controller of the application
     * @return the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public GameControllerInterface execute(GameListenerInterface lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.joinFirstAvailableGame(lis, nickname);
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param mainController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
