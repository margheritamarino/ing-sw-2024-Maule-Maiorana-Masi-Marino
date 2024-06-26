package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;
import java.rmi.RemoteException;

public class msgSentMessage extends ServerGenericMessage {

    private Message msg;
    private GameImmutable model;

    public msgSentMessage(GameImmutable model, Message msg){
        this.model = model;
        this.msg = msg;
    }
    @Override
    public void execute(GameListenerInterface lis) throws IOException, InterruptedException, FileReadException {
        lis.sentMessage(model, msg);
    }
}
