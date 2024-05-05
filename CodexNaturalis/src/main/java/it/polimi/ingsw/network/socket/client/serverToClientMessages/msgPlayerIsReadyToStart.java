package it.polimi.ingsw.network.socket.client.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;

/**
 * msgPlayerIsReadyToStart class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player is ready to start the game.
 */
public class msgPlayerIsReadyToStart extends SocketServerGenericMessage {
    private GameImmutable model;
    private String nickname;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     * @param nickname the nickname of the player who is ready to start
     */
    public msgPlayerIsReadyToStart(GameImmutable model, String nickname) {
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
        lis.playerIsReadyToStart(model, nickname);
    }
}
