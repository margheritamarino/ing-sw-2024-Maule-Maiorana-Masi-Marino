package it.polimi.ingsw.network.socket.client.serverToClientGenericMessages;

import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;

/**
 * msgPlayerReconnected class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player has reconnected to the game.
 */
public class msgPlayerReconnected extends SocketServerGenericMessage{
    private GameImmutable model;
    private String nickPlayerReconnected;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     * @param nickPlayerReconnected the nickname of the player who reconnected
     */
    public msgPlayerReconnected(GameImmutable model, String nickPlayerReconnected) {
        this.model = model;
        this.nickPlayerReconnected = nickPlayerReconnected;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws IOException if there is an IO exception
     * @throws InterruptedException if the execution is interrupted
     */
    @Override
    public void execute(GameListener lis) throws IOException, InterruptedException {
        lis.playerReconnected(model, nickPlayerReconnected);
    }
}
