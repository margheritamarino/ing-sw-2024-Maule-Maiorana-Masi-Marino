package it.polimi.ingsw.network.socket.client.messages;


import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;

/**
 * msgPlayerJoined class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has joined the game.
 */
public class msgPlayerJoined extends MessageServerToClient {
    private GameImmutable model;
    private String nickname;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     */
    public msgPlayerJoined(GameImmutable model) {
        this.model = model;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an I/O error
     * @throws InterruptedException if the execution is interrupted
     */
    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerJoined(model, nickname);
    }
}
