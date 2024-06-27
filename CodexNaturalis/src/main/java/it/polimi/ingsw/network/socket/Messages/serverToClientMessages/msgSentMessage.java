package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;

public class msgSentMessage extends ServerGenericMessage {

    private Message msg;
    private GameImmutable model;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     * @param msg message to send
     */
    public msgSentMessage(GameImmutable model, Message msg){
        this.model = model;
        this.msg = msg;
    }

    /**
     * Executes the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an IO exception
     * @throws InterruptedException if the execution is interrupted
     */
    @Override
    public void execute(GameListenerInterface lis) throws IOException, InterruptedException, FileReadException {
        lis.sentMessage(model, msg);
    }
}
