package it.polimi.ingsw.network.socket.client.clientToServerMessages;

import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.rmi.MainControllerInterface;
import it.polimi.ingsw.network.socket.client.ClientGenericMessage;

import java.rmi.RemoteException;

public class ClientMsgSetInitial extends ClientGenericMessage {
    int index;

    public ClientMsgSetInitial(String nickname, int index) {
        this.nickname = nickname;
        this.index = index;
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
    public void execute(GameControllerInterface gameController) throws RemoteException, NotPlayerTurnException {
        gameController.setInitialCard(this.nickname, this.index);
    }
}
