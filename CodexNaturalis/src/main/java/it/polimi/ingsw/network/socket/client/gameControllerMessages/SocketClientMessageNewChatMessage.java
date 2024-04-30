package it.polimi.ingsw.network.socket.client.gameControllerMessages;

import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.Chat.Message;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.rmi.MainControllerInterface;
import it.polimi.ingsw.network.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

/**
 * SocketClientMessageNewChatMessage class.
 * Extends SocketClientGenericMessage and is used to send a new chat message from the client to the server.
 */
public class SocketClientMessageNewChatMessage extends SocketClientGenericMessage {
    private Message msg;

    /**
     * Constructor of the class.
     * @param msg the chat message to be sent
     */
    public SocketClientMessageNewChatMessage(Message msg) {
        this.msg = msg;
        this.nickname = msg.getSender().getNickname();
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
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     * @throws GameEndedException if the game has ended
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.sentMessage(msg);
    }
}
