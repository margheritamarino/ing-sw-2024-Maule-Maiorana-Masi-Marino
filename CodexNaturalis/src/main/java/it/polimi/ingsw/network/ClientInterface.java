package it.polimi.ingsw.network;

import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.network.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

//interface for common client actions
public interface ClientInterface  {



    /**
     * Adds the player to the game
     *
     * @param nick
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void joinGame(String nick) throws IOException, InterruptedException, NotBoundException;

    //FUNZIONALITA AGGIUNTIVA
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
     * Pings the server
     *
     * @throws RemoteException
     */
    void heartbeat() throws RemoteException;


    void placeCardInBook(int chosenCard, int rowCell, int columnCell) throws IOException;

    void setInitialCard(Integer index) throws IOException;
    void setGoalCard(Integer index) throws IOException;
}


