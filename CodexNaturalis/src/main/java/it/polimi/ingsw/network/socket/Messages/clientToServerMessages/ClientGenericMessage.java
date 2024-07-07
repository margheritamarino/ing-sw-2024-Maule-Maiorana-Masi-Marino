package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.controller.GameController;
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

    protected String nickname; //nickname associated to the message
    protected boolean isPing =false;
    boolean isJoinGame= false; //attribute for the first message of joining the game (JoinGame)

    /**
     * Executes the corresponding action for the message.
     * @param lis the game listener
     * @param gameController the controller interface
     * @throws RemoteException if there is a remote exception
     */
    public abstract void execute(GameListenerInterface lis, GameController gameController) throws RemoteException;

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
     * @return if it's a ping message
     */
    public boolean isPing(){
        return isPing;
    }


    /**
     * Getter method to check if the client has successfully joined a game.
     *
     * @return {@code true} if the client has successfully joined a game, {@code false} otherwise.
     */
    public boolean isJoinGame() {
        return isJoinGame;
    }

    /**
     * Setter method to set the status of whether the client has joined a game or not.
     *
     * @param joinGame {@code true} to indicate that the client has successfully joined a game, {@code false} otherwise.
     */
    public void setJoinGame(boolean joinGame) {
        isJoinGame = joinGame;
    }
}
