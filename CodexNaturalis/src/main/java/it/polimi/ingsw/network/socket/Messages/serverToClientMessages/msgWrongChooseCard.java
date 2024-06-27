package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;

public class msgWrongChooseCard extends ServerGenericMessage{
    private GameImmutable model;
    private String msg;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgWrongChooseCard(GameImmutable model, String msg) {
        this.model = model;
        this.msg= msg;
    }

    /**
     * Executes the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an IO exception
     * @throws InterruptedException if the execution is interrupted
     */
    @Override
    public void execute(GameListenerInterface lis) throws IOException, InterruptedException {
        lis.wrongChooseCard(model, msg);
    }
}
