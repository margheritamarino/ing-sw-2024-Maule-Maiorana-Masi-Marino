package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;

public class msgWrongChooseCell extends ServerGenericMessage{
    private GameImmutable model;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgWrongChooseCell(GameImmutable model) {
        this.model = model;
    }

    @Override
    public void execute(GameListenerInterface lis) throws IOException, InterruptedException {
        lis.wrongChooseCell(model);
    }
}
