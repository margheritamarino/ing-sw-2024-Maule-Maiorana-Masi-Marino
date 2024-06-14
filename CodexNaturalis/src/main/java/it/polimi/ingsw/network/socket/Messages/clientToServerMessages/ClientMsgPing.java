package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.rmi.RemoteException;

public class ClientMsgPing extends ClientGenericMessage{
    public ClientMsgPing(String nick) {
        this.nickname = nick;
        this.isPing=true;
    }

    @Override
    public void execute(GameListenerInterface lis, GameController gameController) throws RemoteException {
        return ;
    }



    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {
        return;
    }
}
