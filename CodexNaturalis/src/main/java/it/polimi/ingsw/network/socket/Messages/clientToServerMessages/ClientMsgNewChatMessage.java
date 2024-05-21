package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.rmi.RemoteException;

public class ClientMsgNewChatMessage extends ClientGenericMessage{
    private Message msg;

    public ClientMsgNewChatMessage(Message msg){
        this.msg = msg;
        this.nickname = msg.getSender().getNickname();

    }

    @Override
    public void execute(GameListenerInterface lis, GameController gameController) throws RemoteException{
        System.out.println("ClientMsgNewChatMessage executing for listener");
    }

    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        System.out.println("ClientMsgNewChatMessage executing for GameControllerInterface");
        gameController.sentMessage(msg);
    }
}
