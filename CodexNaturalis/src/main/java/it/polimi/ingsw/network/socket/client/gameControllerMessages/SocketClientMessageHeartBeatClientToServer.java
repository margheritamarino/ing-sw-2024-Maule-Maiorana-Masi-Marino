package it.polimi.ingsw.network.socket.client.gameControllerMessages;

import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.rmi.MainControllerInterface;
import it.polimi.ingsw.network.socket.client.MessageClientToServer;

import java.rmi.RemoteException;

public class SocketClientMessageHeartBeatClientToServer extends MessageClientToServer {
    public SocketClientMessageHeartBeatClientToServer(String nick) {
        this.nickname = nickname;
        this.isMessageForMainController = false;
        this.isHeartbeat=true;
    }


    @Override
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }


    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        return;
    }
}
