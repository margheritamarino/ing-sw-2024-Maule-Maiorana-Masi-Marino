package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;

import java.rmi.RemoteException;

/**
 * msgJoinUnableGameFull class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player was unable to join the game because it is full.
 */
public class msgJoinUnableGameFull extends ServerGenericMessage {
    private Player player;
    private GameImmutable model;

    /**
     * Constructor of the class.
     * @param player the player who was unable to join the game
     * @param model the immutable game model
     */
    public msgJoinUnableGameFull(Player player, GameImmutable model) {
        this.player = player;
        this.model = model;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {

    }
}
