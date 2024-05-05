package it.polimi.ingsw.network.socket.client.clientToServerMessages;

import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.rmi.MainControllerInterface;
import it.polimi.ingsw.network.socket.client.ClientGenericMessage;

import java.rmi.RemoteException;

public class ClientMsgHeartBeat extends ClientGenericMessage {
    public ClientMsgHeartBeat(String nick) {
        this.nickname = nickname;
        this.isMessageForMainController = false;
        this.isHeartbeat=true;
    }


    @Override
    public GameControllerInterface execute(GameListenerInterface lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }


    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        return;
    }
}
