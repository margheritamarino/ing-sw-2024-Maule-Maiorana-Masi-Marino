package it.polimi.ingsw.network.rmi;


import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.CardType;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Interfaccia Remota che definisce i metodi che possono essere invocati dal Client sul Server
/**
 * This interface contains all the action a player can do in a single game */

public interface GameControllerInterface extends Remote {
    /**
     * This method is used to check if the player is ready to start
     *
     * @param p the nickname of the player
     * @return
     * @throws RemoteException if the connection fails
     */
    boolean playerIsReadyToStart(GameListenerInterface lis, String p ) throws RemoteException;



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
     * @param listener the GameListener of the player {@link GameListenerInterface}
     * @throws RemoteException if the connection fails
     */
    void disconnectPlayer(String nick, GameListenerInterface listener) throws RemoteException;



    /**
     * This method return the id of the game
     *
     * @return the id of the game
     * @throws RemoteException if the connection fails
     */
    int getGameId() throws RemoteException;


    void ping(String nickname, GameListenerInterface me) throws RemoteException;




    /**
     * This method remove a player from the GameListener list {@link GameListenerInterface} and from the game
     *
     * @param lis  the GameListener of the player {@link GameListenerInterface}
     * @param nick the nickname of the player
     * @throws RemoteException if the connection fails
     */
    void leave(GameListenerInterface lis, String nick) throws RemoteException;




     void setInitialCard(String nickname, int index)throws RemoteException ;

     void setGoalCard(String nickname, int index) throws NotPlayerTurnException, RemoteException ;

     void placeCardInBook(String nickname, int chosenCard, int rowCell, int columnCell)throws RemoteException;
     void joinGame(GameListenerInterface lis, String nick, Color color) throws RemoteException;

    void PickCardFromBoard(String nickname, CardType cardType, boolean drawFromDeck, int pos)throws RemoteException;

    void settingGame(GameListenerInterface lis,int numPlayers, int GameID, String nick, Color color)throws RemoteException;

   // boolean makeGameStart(GameListenerInterface lis, String nickname)throws RemoteException;

    void sentMessage(Message msg) throws RemoteException;
}
