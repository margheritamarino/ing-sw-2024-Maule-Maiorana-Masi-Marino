package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.listener.GameListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface contains the events regarding the list of games */
public interface MainServer extends Remote {
    /**
     * This method creates a new game and add it to the GameListener list
     * @param lis the GameListener of the player {@link GameListener}
     * @param nick the nickname of the player
     * @return the GameControllerInterface of the game {@link GameServer}
     * @throws RemoteException if the connection fails
     */
    GameServer createGame(GameListener lis, String nick) throws RemoteException;

    /**
     * This method joins the first available game
     * @param lis the GameListener of the player {@link GameListener}
     * @param nick the nickname of the player
     * @return the GameControllerInterface of the game {@link GameServer}
     * @throws RemoteException if the connection fails
     */
    GameServer joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException;

    /**
     * This method joins a specific game
     * @param lis the GameListener of the player {@link GameListener}
     * @param nick the nickname of the player
     * @param idGame the id of the game
     * @return the GameControllerInterface of the game {@link GameServer}
     * @throws RemoteException if the connection fails
     */
    GameServer joinGame(GameListener lis, String nick, int idGame) throws RemoteException;

    /**
     * This method reconnects a player to a specific game
     * @param lis the GameListener of the player {@link GameListener}
     * @param nick the nickname of the player
     * @param idGame the id of the game
     * @return the GameControllerInterface of the game {@link GameServer}
     * @throws RemoteException if the connection fails
     */
    GameServer reconnect(GameListener lis, String nick, int idGame) throws RemoteException;


    /**
     * This method leaves a specific game
     * @param lis the GameListener of the player {@link GameListener}
     * @param nick the nickname of the player
     * @param idGame the id of the game
     * @return the GameControllerInterface of the game {@link GameServer}
     * @throws RemoteException if the connection fails
     */
    GameServer leaveGame(GameListener lis, String nick, int idGame) throws RemoteException;
}
