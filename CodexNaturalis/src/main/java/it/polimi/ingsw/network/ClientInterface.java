package it.polimi.ingsw.network;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Interface defining common actions that a client can perform in a game.
 */
public interface ClientInterface  {


    /**
     * Joins the game with the specified nickname.
     *
     * @param nick The nickname of the player joining the game.
     * @throws IOException        If there is an I/O error during communication.
     * @throws InterruptedException If the thread is interrupted while waiting.
     * @throws NotBoundException   If the server is not bound or available.
     */
    void joinGame(String nick) throws IOException, InterruptedException, NotBoundException;

    /**
     * Leaves the game with the specified nickname.
     *
     * @param nick The nickname of the player leaving the game.
     * @throws IOException      If there is an I/O error during communication.
     * @throws NotBoundException If the server is not bound or available.
     */
    void leave(String nick) throws IOException, NotBoundException;

    /**
     * Sets the player identified by the nickname as ready.
     *
     * @param nickname The nickname of the player setting as ready.
     * @throws IOException If there is an I/O error during communication.
     */
    void setAsReady(String nickname) throws IOException;

    /**
     * Places a card in the player's book at the specified position.
     *
     * @param chosenCard  The index of the chosen card to place.
     * @param rowCell     The row position in the book where the card will be placed.
     * @param columnCell  The column position in the book where the card will be placed.
     * @throws IOException If there is an I/O error during communication.
     */
    void placeCardInBook(int chosenCard, int rowCell, int columnCell) throws IOException;

    /**
     * Sets up the game with the specified number of players, game ID, and player's nickname.
     *
     * @param numPlayers The number of players in the game.
     * @param GameID     The ID of the game.
     * @param nick       The nickname of the player setting up the game.
     * @throws IOException If there is an I/O error during communication.
     */
    void settingGame(int numPlayers, int GameID, String nick)throws IOException;

    /**
     * Sets the initial card for the player with the specified index.
     *
     * @param index    The index of the initial card to be set.
     * @param nickname The nickname of the player.
     * @throws IOException If there is an I/O error during communication.
     */
    void setInitialCard(int index, String nickname) throws IOException;

    /**
     * Sets the goal card for the player with the specified index.
     *
     * @param index    The index of the goal card to be set.
     * @param nickname The nickname of the player.
     * @throws IOException           If there is an I/O error during communication.
     * @throws NotPlayerTurnException If it's not the player's turn to set the goal card.
     */
    void setGoalCard(int index, String nickname) throws IOException, NotPlayerTurnException;

    /**
     * Picks a card from the board for the player.
     *
     * @param cardType     The type of card to pick.
     * @param drawFromDeck Indicates whether the card is drawn from the deck or board.
     * @param pos          The position of the card on the board.
     * @throws IOException If there is an I/O error during communication.
     */
    void PickCardFromBoard(CardType cardType, boolean drawFromDeck, int pos) throws IOException;

    /**
     * Sends a ping to the server to check connectivity.
     *
     * @throws RemoteException If there is an error in remote communication.
     */
    void ping () throws RemoteException;

    /**
     * Sends a message to the server.
     *
     * @param msg The message to be sent.
     * @throws RemoteException If there is an error in remote communication.
     */
    void sendMessage(Message msg) throws RemoteException, IOException;

    /**
     * Notifies the server to start the game with the specified nickname.
     *
     * @param nickname The nickname of the player requesting to start the game.
     * @throws IOException If there is an I/O error during communication.
     */
    void makeGameStart(String nickname)throws IOException;

    /**
     * Reconnect the player to the game
     *
     * @param nick
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void reconnect(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

}


