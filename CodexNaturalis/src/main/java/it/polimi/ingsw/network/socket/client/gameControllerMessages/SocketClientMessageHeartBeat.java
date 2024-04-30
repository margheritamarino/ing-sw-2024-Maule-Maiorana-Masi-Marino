package it.polimi.ingsw.network.socket.client.gameControllerMessages;

import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.network.rmi.ServerCommunicationInterface;
import it.polimi.ingsw.network.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

public class SocketClientMessageHeartBeat extends SocketClientGenericMessage {
    public SocketClientMessageHeartBeat(String nick) {
        this.nickname = nickname;
        this.isMessageForMainController = false;
        this.isHeartbeat=true;
    }


    @Override
    public ServerCommunicationInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }


    @Override
    public void execute(ServerCommunicationInterface gameController) throws RemoteException, GameEndedException {
        return;
    }
}
