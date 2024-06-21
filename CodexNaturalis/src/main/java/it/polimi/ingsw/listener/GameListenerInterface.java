package it.polimi.ingsw.listener;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is used to notify the client about the changes in the game
 */
public interface GameListenerInterface extends Remote {

    /**
     * This method is used to notify the client that a player has joined the game
     *
     * @param model is the game model
     * @param nickname the nickname of the player who joined
     * @param playerColor the color chosen by the player
     * @throws RemoteException if the reference could not be accessed
     */
    void playerJoined(GameImmutable model, String nickname, Color playerColor) throws RemoteException;

    /**
     * Requests the number of players and the game ID from the client.
     *
     * @param model the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void requireNumPlayersGameID(GameImmutable model)throws RemoteException;

    /**
     * This method is used to notify the client that a player has to choose again the CARD
     *
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void wrongChooseCard(GameImmutable model, String msg) throws RemoteException;

    /**
     * Notifies the client that points have been added.
     *
     * @param model the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void pointsAdded(GameImmutable model)throws RemoteException;

    /**
     * This method is used to notify the client that a player has left the game
     *
     * @param model is the game model
     * @param nickname is the nickname of the player that has left
     * @throws RemoteException if the reference could not be accessed
     */
    void playerLeft(GameImmutable model, String nickname) throws RemoteException;

    /**
     * This method is used to notify the client that a player has reconnected to the game
     * @param model is the game model {@link GameImmutable}
     * @param nickPlayerReconnected is the nickname of the player that has reconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerReconnected(GameImmutable model,  String nickPlayerReconnected) throws RemoteException;

    /**
     * This method is used to notify the client that a player has tried to join the game but the game is full
     *
     * @param triedToJoin is the player that has tried to join the game
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableGameFull(Player triedToJoin, GameImmutable model) throws RemoteException;

    /**
     * This method is used to notify the client that a player has tried to join the game but the nickname is already in use
     *
     * @param gameModel is the game model
     * @param triedToJoin the player who tried to join
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableNicknameAlreadyIn(Player triedToJoin, GameImmutable gameModel) throws RemoteException;

    /**
     * This method is used to notify the client to ask for reconnection if the player is trying to reconnect.
     *
     * @param triedToJoin the player who is trying to reconnect
     * @param gameModel the current state of the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void AskForReconnection(Player triedToJoin, GameImmutable gameModel) throws RemoteException; //if the client is trying to reconnect

    /**
     * This method is used to notify the client that the game has started
     *
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void gameStarted(GameImmutable model) throws RemoteException;

    /**
     * This method is used to notify the client that the game has ended
     *
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void gameEnded(GameImmutable model) throws RemoteException;

    /**
     * Notifies the listeners that initial cards are ready to be displayed.
     *
     * @param model the game model
     * @throws IOException         if an I/O error occurs
     * @throws FileReadException   if there is an error reading the file
     * @throws RemoteException     if the reference could not be accessed
     */
    void requireInitialReady(GameImmutable model, int index) throws IOException, FileReadException;

    /**
     * Notifies the listeners that objective cards are ready to be chosen.
     *
     * @param model the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void requireGoalsReady(GameImmutable model, int index) throws RemoteException;

    /**
     * Notifies the listeners that objective cards are chosen.
     *
     * @param model the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void cardsReady(GameImmutable model) throws RemoteException;

    /**
     * Notifies the client that a card has been placed on the board.
     *
     * @param model the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void cardPlaced(GameImmutable model) throws RemoteException;

    /**
     * Notifies the client that a card has been drawn.
     *
     * @param model the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void cardDrawn(GameImmutable model) throws RemoteException;

    /**
     * Notifies the client that the next turn has been triggered.
     *
     * @param model the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void nextTurn(GameImmutable model) throws RemoteException;

    /**
     * Notifies the client that a player has disconnected.
     *
     * @param model the game model
     * @param nick  the nickname of the player who disconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerDisconnected(GameImmutable model, String nick) throws RemoteException;

    /**
     * This is a generic error that can happen when a player is entering the game
     * @param why is the reason why the error happened
     * @throws RemoteException if the reference could not be accessed
     */
    void errorReconnecting(String why) throws RemoteException;

    /**
     * Notifies the client that the last circle has started.
     *
     * @param model the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void lastCircle(GameImmutable model) throws RemoteException;

    /**
     * Notifies the client that a player is ready.
     *
     * @param gameImmutable the game model
     * @param nickname      the nickname of the player who is ready
     * @throws RemoteException if the reference could not be accessed
     */
    void playerReady(GameImmutable gameImmutable, String nickname)throws RemoteException;

    /**
     * This method is used to notify that only one player is connected
     *
     * @param gameModel is the game model {@link GameImmutable}
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     * @throws RemoteException if the reference could not be accessed
     */
    void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException;

    /**
     * Sends a message to the client.
     *
     * @param model the game model
     * @param msg   the message to send
     * @throws RemoteException if the reference could not be accessed
     */
    void sentMessage(GameImmutable model, Message msg) throws RemoteException;


}
