package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.player.Player;

import java.rmi.RemoteException;

/**
 * msgJoinUnableNicknameAlreadyIn class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a player was unable to join the game because the nickname is already in use.
 */
public class msgJoinUnableNicknameAlreadyIn extends ServerGenericMessage {
    private Player triedToJoin;

    /**
     * Constructor of the class.
     * @param triedToJoin the player who wanted to join the game
     */
    public msgJoinUnableNicknameAlreadyIn(Player triedToJoin) {
        this.triedToJoin = triedToJoin;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.joinUnableNicknameAlreadyIn(triedToJoin);
    }
}
