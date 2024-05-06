package it.polimi.ingsw.network.socket.Messages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.exceptions.GameEndedException;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * SocketClientGenericMessage class.
 * An abstract class that represents a generic message to be sent from the client to the server.
 */
public abstract class ClientGenericMessage implements Serializable{
    protected String nickname; //soprannome associato al messaggio
    protected boolean isHeartbeat=false;

    /**
     * Executes the corresponding action for the message.
     * @param lis the game listener
     * @param gameController the main controller interface
     * @return the game controller interface
     * @throws RemoteException if there is a remote exception
     */
    public abstract GameControllerInterface execute(GameListenerInterface lis, GameController gameController) throws RemoteException;

    /**
     * Executes the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is a remote exception
     * @throws GameEndedException if the game has ended
     */
    public abstract void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException;

    /**
     * Returns the nickname associated with the message.
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return if it's a heartbeat message
     */
    public boolean isHeartbeat(){
        return isHeartbeat;
    }


}
