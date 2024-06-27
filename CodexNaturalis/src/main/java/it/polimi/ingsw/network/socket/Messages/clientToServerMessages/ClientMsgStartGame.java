package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.rmi.RemoteException;

public class ClientMsgStartGame extends ClientGenericMessage {

    /**
     * Constructor of the class.
     * @param nickname the player's nickname
     */
    public ClientMsgStartGame(String nickname) {
        this.nickname = nickname;
        this.isJoinGame= true;
    }

    /**
     * Executes the corresponding action for the message.
     * @param lis the game listener
     * @param gameController the main controller interface
     * @throws RemoteException if there is a remote exception
     */
    @Override
    public void execute(GameListenerInterface lis, GameController gameController) throws RemoteException {
        gameController.makeGameStart(lis, nickname);

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
