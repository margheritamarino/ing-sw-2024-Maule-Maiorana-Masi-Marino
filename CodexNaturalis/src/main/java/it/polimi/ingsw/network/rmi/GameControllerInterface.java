package it.polimi.ingsw.network.rmi;


import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.cards.CardType;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Controlla se servono altri Metodi (azioni che può compiere un Player)
/**
 * This interface contains all the action a player can do in a single game */

public interface GameControllerInterface extends Remote {
    /**
     * This method is used to check if the player is ready to start
     *
     * @param p the nickname of the player
     * @return true if the player is ready to start
     * @throws RemoteException if the connection fails
     */
    boolean playerIsReadyToStart(String p) throws RemoteException;


    /**
     * This method checks if it's the turn of the player
     *
     * @param nick the nickname of the player
     * @return true if it's the turn of the player
     * @throws RemoteException if the connection fails
     */
    boolean isThisMyTurn(String nick) throws RemoteException;

    /**
     * This method disconnect a player and remove him from the GameListener list{@link GameListenerInterface}
     *
     * @param nick        the nickname of the player
     * @param lisOfClient the GameListener of the player {@link GameListenerInterface}
     * @throws RemoteException if the connection fails
     */
    void disconnectPlayer(String nick, GameListenerInterface lisOfClient) throws RemoteException;

    /**
     * This method is used to check if the client is connected, every x seconds the server send a ping to the client
     *
     * @param nick the nickname of the player
     * @param me   the GameListener of the player {@link GameListenerInterface}
     * @throws RemoteException if the connection fails
     */
    void heartbeat(String nick, GameListenerInterface me) throws RemoteException;


    /**
     * This method return the id of the game
     *
     * @return the id of the game
     * @throws RemoteException if the connection fails
     */
    int getGameId() throws RemoteException;

    /**
     * This method return the number of the online players
     *
     * @return the number of the online players
     * @throws RemoteException if the connection fails
     */
    int getNumOnlinePlayers() throws RemoteException;

    /**
     * This method remove a player from the GameListener list {@link GameListenerInterface} and from the game
     *
     * @param lis  the GameListener of the player {@link GameListenerInterface}
     * @param nick the nickname of the player
     * @throws RemoteException if the connection fails
     */
    void leave(GameListenerInterface lis, String nick) throws RemoteException;



     void setInitialCard(String nickname, int index) throws NotPlayerTurnException;

     void setGoalCard(String nickname, int index) throws NotPlayerTurnException;

     void placeCardInBook(String nickname, int chosenCard, int rowCell, int columnCell);
     GameControllerInterface joinGame(GameListenerInterface lis, String nick) throws RemoteException;

    void PickCardFromBoard(String nickname, CardType cardType, boolean drawFromDeck, int pos);
}
