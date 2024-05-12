package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.rmi.RemoteException;

public class ClientMsgSetInitial extends ClientGenericMessage {
    int index;

    public ClientMsgSetInitial(String nickname, int index) {
        this.nickname = nickname;
        this.index = index;
    }


    @Override
    public void execute(GameListenerInterface lis, GameController gameController) throws RemoteException {
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {
        gameController.setInitialCard(nickname, index);
    }
}
