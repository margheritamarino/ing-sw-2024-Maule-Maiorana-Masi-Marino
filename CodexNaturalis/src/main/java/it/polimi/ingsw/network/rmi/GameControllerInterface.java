package it.polimi.ingsw.network.rmi;


import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.cards.CardType;

import java.rmi.Remote;
import java.rmi.RemoteException;

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
     * This method reconnects a player to a specific game
     * @param lis the GameListener of the player {@link GameListenerInterface}
     * @param nick the nickname of the player
     * @return the GameControllerInterface of the game {@link GameControllerInterface}
     * @throws RemoteException if the connection fails
     */
    void reconnect(GameListenerInterface lis, String nick) throws RemoteException;


    /**
     * This method return the id of the game
     *
     * @return the id of the game
     * @throws RemoteException if the connection fails
     */
    int getGameId() throws RemoteException;



    /**
     * Sends a ping to the server to maintain the connection and confirm the client's presence.
     * This method is used to indicate to the server that the client is still online and active.
     *
     * @param nickname The nickname associated with the client sending the ping.
     * @param me       The GameListenerInterface object representing the client's listener for game events.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    void ping(String nickname, GameListenerInterface me) throws RemoteException;




    /**
     * This method remove a player from the GameListener list {@link GameListenerInterface} and from the game
     *
     * @param lis  the GameListener of the player {@link GameListenerInterface}
     * @param nick the nickname of the player
     * @throws RemoteException if the connection fails
     */
    void leave(GameListenerInterface lis, String nick) throws RemoteException;

    /**
     * Informs the server to set the initial card for the specified player.
     *
     * @param nickname The nickname of the player.
     * @param index    The index of the initial card to be set.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
     void setInitialCard(String nickname, int index)throws RemoteException ;
    /**
     * Informs the server to set the goal card for the specified player.
     *
     * @param nickname The nickname of the player.
     * @param index    The index of the goal card to be set.
     * @throws NotPlayerTurnException If it's not the player's turn to set the goal card.
     * @throws RemoteException       If there is a communication-related issue during the remote method invocation.
     */
     void setGoalCard(String nickname, int index) throws NotPlayerTurnException, RemoteException ;
    /**
     * Informs the server to place a card in the player's book at the specified position.
     *
     * @param nickname    The nickname of the player.
     * @param chosenCard  The chosen card to be placed.
     * @param rowCell     The row position in the book.
     * @param columnCell  The column position in the book.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
     void placeCardInBook(String nickname, int chosenCard, int rowCell, int columnCell)throws RemoteException;
    /**
     * Requests the server to join the game with the specified nickname and attaches the provided listener.
     *
     * @param lis  The listener interface for receiving game events.
     * @param nick The nickname of the player joining the game.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
     void joinGame(GameListenerInterface lis, String nick) throws RemoteException;
    /**
     * Informs the server to pick a card from the board for the specified player.
     *
     * @param nickname     The nickname of the player.
     * @param cardType     The type of card to pick.
     * @param drawFromDeck Indicates whether the card is drawn from the deck or board.
     * @param pos          The position of the card on the board.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    void PickCardFromBoard(String nickname, CardType cardType, boolean drawFromDeck, int pos)throws RemoteException;
    /**
     * Informs the server to set up the game with the specified number of players, game ID, and nickname.
     *
     * @param lis       The listener interface for receiving game events.
     * @param numPlayers The number of players in the game.
     * @param GameID     The ID of the game.
     * @param nick       The nickname of the player setting up the game.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    void settingGame(GameListenerInterface lis,int numPlayers, int GameID, String nick)throws RemoteException;

    /**
     * Sends a message to the server to be distributed to other players or processed as required.
     *
     * @param msg The message object containing the details of the message to be sent.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    void sentMessage(Message msg) throws RemoteException;
    /**
     * Informs the server to start the game for the specified player.
     *
     * @param lis  The listener interface for receiving game events.
     * @param nick The nickname of the player starting the game.
     * @return True if the game is successfully started; false otherwise.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    boolean makeGameStart(GameListenerInterface lis, String nick) throws RemoteException;
}
