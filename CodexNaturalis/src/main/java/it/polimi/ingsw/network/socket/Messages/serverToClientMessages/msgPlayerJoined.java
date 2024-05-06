package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;


import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;

/**
 * msgPlayerJoined class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has joined the game.
 */
public class msgPlayerJoined extends ServerGenericMessage {
    private GameImmutable model;
    private String nickname;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgPlayerJoined(GameImmutable model, String nickname) {
        this.model = model;
        this.nickname = nickname;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an I/O error
     * @throws InterruptedException if the execution is interrupted
     */
    @Override
    public void execute(GameListenerInterface lis) throws IOException, InterruptedException {
        lis.playerJoined(model, nickname);
    }
}
