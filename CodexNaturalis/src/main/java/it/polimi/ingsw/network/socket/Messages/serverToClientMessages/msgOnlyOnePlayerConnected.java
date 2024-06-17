package it.polimi.ingsw.network.socket.Messages.serverToClientMessages;


import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.game.GameImmutable;

import java.rmi.RemoteException;

/**
 * msgOnlyOnePlayerConnected class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that only one player is currently connected to the game.
 */
public class msgOnlyOnePlayerConnected extends ServerGenericMessage {
    private GameImmutable model;
    private int secondsToWaintUntilGameEnded;

    /**
     * Constructor of the class.
     * @param model the immutable game model
     * @param secondsToWaintUntilGameEnded the number of seconds to wait until the game is ended
     */
    public msgOnlyOnePlayerConnected(GameImmutable model,int secondsToWaintUntilGameEnded) {
        this.model = model;
        this.secondsToWaintUntilGameEnded=secondsToWaintUntilGameEnded;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameListenerInterface lis) throws RemoteException {
        lis.onlyOnePlayerConnected(model,secondsToWaintUntilGameEnded);
    }
}
