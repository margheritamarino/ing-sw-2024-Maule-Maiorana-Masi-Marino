package it.polimi.ingsw.listener;

import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Contains methods to NOTIFY the CLIENT about different game events
 */
public interface GameListenerInterface extends Remote {

    /**
     * This method is used to notify the client that a player has joined the game
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void playerJoined(GameImmutable model, String nickname) throws RemoteException;

    /**
     * This method is used to notify the client that a player has left the game
     * @param model is the game model {@link GameImmutable}
     * @param nickname is the nickname of the player that has left
     * @throws RemoteException if the reference could not be accessed
     */
    void playerLeft(GameImmutable model, String nickname) throws RemoteException;

    /**
     * This method is used to notify the client that a player has tried to join the game but the game is full
     * @param triedToJoin is the player that has tried to join the game
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableGameFull(Player triedToJoin, GameImmutable model) throws RemoteException;

    /**
     * This method is used to notify the client that a player has reconnected to the game
     * @param model is the game model
     * @param nickPlayerReconnected is the nickname of the player that has reconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException;

    /**
     * This method is used to notify the client that a player has tried to join the game but the nickname is already in use
     * @param triedToJoin is the player that has tried to join the game
     * @throws RemoteException if the reference could not be accessed
     */
    void joinUnableNicknameAlreadyIn(Player triedToJoin) throws RemoteException;

    /**
     * This method is used to notify the client that the game id does not exist
     * @param gameid is the id of the game
     * @throws RemoteException if the reference could not be accessed
     */
    void gameIdNotExists(int gameid) throws RemoteException;

    /**
     * This is a generic error that can happen when a player is entering the game
     * @param why is the reason why the error happened
     * @throws RemoteException if the reference could not be accessed
     */
    void genericErrorWhenEnteringGame(String why) throws RemoteException;

    /**
     * This method is used to notify that the player is ready to start the game
     * @param model is the game model
     * @param nick is the nickname of the player that is ready to start
     * @throws IOException if the reference could not be accessed
     */
    void playerIsReadyToStart(GameImmutable model, String nick) throws IOException;


    /**
     * This method is used to notify the client that the game has started
     * @param model is the game model {@link GameImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void gameStarted(GameImmutable model) throws RemoteException;

    /**
     * This method is used to notify the client that the game has ended
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void gameEnded(GameImmutable model) throws RemoteException;

    /**
     * Notifies the listeners that initial cards are ready to be displayed.
     *
     * @param model       is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void requireInitialReady(GameImmutable model) throws RemoteException;

    /**
     * Notifies the listeners that objective cards are ready
     * @param model is the game model
     * @param objectiveCards An array list of objective cards
     * @throws RemoteException if the reference could not be accessed
     */
    void requireGoalsReady(GameImmutable model, ArrayList<ObjectiveCard> objectiveCards) throws RemoteException;

    /**
     * Notifies the listeners that all cards are ready
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void cardsReady(GameImmutable model) throws RemoteException;

    /**
     * This method is used to notify that a card has been placed on the book
     * @param model is the game model
     * @param player is the Player who placed the card
     * @throws RemoteException if the reference could not be accessed
     */
    void cardPlaced(GameImmutable model, Player player, int posCell, int posCard) throws RemoteException;

    /**
     * This method is used to notify that a card has been drawn
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void cardDrawn(GameImmutable model) throws RemoteException;


    /**
     * This method is used to notify that the next turn triggered
     * @param model is the game model
     * @throws RemoteException if the reference could not be accessed
     */
    void nextTurn(GameImmutable model) throws RemoteException;

    /**
     * This method is used to notify that a player has disconnected
     * @param model is the game model
     * @param nick is the nickname of the player that has disconnected
     * @throws RemoteException if the reference could not be accessed
     */
    void playerDisconnected(GameImmutable model, String nick) throws RemoteException;

    /**
     * This method is used to notify that the last circle has started
     * @param model is the game model {@link GameImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    void lastCircle(GameImmutable model) throws RemoteException;



}
