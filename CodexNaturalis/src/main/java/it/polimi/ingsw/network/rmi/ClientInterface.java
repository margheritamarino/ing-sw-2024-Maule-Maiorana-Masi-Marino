package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.Chat.*;
import it.polimi.ingsw.network.Chat.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

//COMMON CLIENT ACTIONS
public interface ClientInterface {
    /**
     * Creates a new game
     *
     * @param nick
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void createGame(String nick) throws IOException, InterruptedException, NotBoundException;

    /**
     * Joins the first game found in the list of games
     *
     * @param nick
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void joinFirstAvailable(String nick) throws IOException, InterruptedException, NotBoundException;

    /**
     * Adds the player to the game
     *
     * @param nick
     * @param idGame
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void joinGame(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

    /**
     * Reconnect the player to the game
     *
     * @param nick
     * @param idGame
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void reconnect(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

    /**
     * Leaves the game
     *
     * @param nick
     * @param idGame
     * @throws IOException
     * @throws NotBoundException
     */
    void leave(String nick, int idGame) throws IOException, NotBoundException;

    /**
     * Sets the invoker as ready
     *
     * @throws IOException
     */
    void setAsReady() throws IOException;

    /**
     * Checks if it's the invoker's turn
     *
     * @return
     * @throws RemoteException
     */
    boolean isMyTurn() throws RemoteException;

    /**
     * Sends a message in chat
     *
     * @param msg message
     * @throws RemoteException
     */
    void sendMessage(Message msg) throws RemoteException;


    /**
     * Pings the server
     *
     * @throws RemoteException
     */
    void heartbeat() throws RemoteException;
}
