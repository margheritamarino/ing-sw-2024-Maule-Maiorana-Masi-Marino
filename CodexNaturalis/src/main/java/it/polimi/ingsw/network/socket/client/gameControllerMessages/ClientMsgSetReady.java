package it.polimi.ingsw.network.socket.client.gameControllerMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.rmi.MainControllerInterface;
import it.polimi.ingsw.network.socket.client.ClientGenericMessage;

import java.rmi.RemoteException;

/**
 * SocketClientMessageSetReady class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating that a player is ready to start the game.
 */
public class ClientMsgSetReady extends ClientGenericMessage {
    /**
     * Constructor of the class.
     * @param nickname the player's nickname
     */
    public ClientMsgSetReady(String nickname) {
        this.nickname = nickname;
        this.isMessageForMainController = false;
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
        return null;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {
        gameController.playerIsReadyToStart(this.nickname);
    }
}
