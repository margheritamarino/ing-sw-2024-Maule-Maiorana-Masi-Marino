package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;

import it.polimi.ingsw.listener.GameListenerInterface;

import java.rmi.RemoteException;

/**
 * msgGenericErrorWhenEntryingGame class.
 * Extends SocketServerGenericMessage and is used to send a generic error message to the client
 * when entering a game.
 */
public class msgGenericErrorWhenEnteringGame extends ServerGenericMessage {
    private String why;

    /**
     * Constructor of the class.
     * @param why the reason for the error
     */
    public msgGenericErrorWhenEnteringGame(String why) {
        this.why=why;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.errorReconnecting(why);
    }
}
