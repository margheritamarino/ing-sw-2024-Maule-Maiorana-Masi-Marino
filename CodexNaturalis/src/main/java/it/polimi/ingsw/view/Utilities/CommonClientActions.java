package it.polimi.ingsw.view.Utilities;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.network.Chat.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


//interface for common client actions
public interface CommonClientActions {

    /**
     * Creates a new game
     * @param nickname of the player
     * @throws IOException exception
     * @throws NotBoundException exception
     * @throws InterruptedException exception
     */
    void createGame(String nickname) throws IOException, InterruptedException, NotBoundException;

    /**
     * Joins the first game
     * @param nickname of the player
     * @throws IOException exception
     * @throws NotBoundException exception
     * @throws InterruptedException exception
     */
    void joinFirstAvailable(String nickname) throws IOException, NotBoundException, InterruptedException;

    /**
     * Adds the player to the game
     *
     * @param nickname of the player
     * @param idGame of the game
     * @throws IOException exception
     * @throws InterruptedException exception
     * @throws NotBoundException exception
     */
    void joinGame(String nickname, int idGame) throws IOException, InterruptedException, NotBoundException;

    /**
     * Reconnect the player to the game
     *
     * @param nickname of the player
     * @param idGame of the game
     * @throws IOException exception
     * @throws InterruptedException exception
     * @throws NotBoundException exception
     */
    void reconnect(String nickname, int idGame) throws IOException, InterruptedException, NotBoundException;

    /**
     * Leaves the game
     *
     * @param nickname of the player
     * @param idGame of the game
     * @throws IOException exception
     * @throws NotBoundException exception
     */
    void leave(String nickname, int idGame) throws IOException, NotBoundException;

    /**
     * Sets the invoker as ready
     *
     * @throws IOException exception
     */
    void setAsReady() throws IOException;

    /**
     * Checks if it's the invoker's turn
     *
     * @return true if it's the turn of player
     * @throws RemoteException exception
     */
    boolean isMyTurn() throws RemoteException;

    /**
     * Picks a card from the board
     *
     * @param board        the board from which to pick the card
     * @param cardType     the type of card to pick
     * @param drawFromDeck true if the card should be drawn from the deck, false if it's from the board
     * @param pos          the position from which to pick the card
     * @throws IOException exception
     * @throws InterruptedException exception
     * @throws NotBoundException exception
     */
    void pickCardFromBoard(Board board, CardType cardType, boolean drawFromDeck, int pos)
            throws IOException, InterruptedException, NotBoundException;

    /**
     * Places a card in the book
     *
     * @param posCell  the position of the cell in the book
     * @param posCard  the position of the card in the playerDeck
     * @throws IOException exception
     * @throws NotBoundException exception
     */
    void placeCardOnBook(int posCell, int posCard) throws IOException, NotBoundException;


    /**
     * Sends a message in chat
     *
     * @param msg message
     * @throws RemoteException exception
     */
    void sendMessage(Message msg) throws RemoteException;


    //serve a pingare il server, ovvero il client invia un segnale al server per controllare se è ancora in esecuzione
    //se il server riceve il ping correttamente può considerare anfcora il client connesso e funzionante
    /**
     * Pings the server
     * @throws RemoteException exception
     */
    void heartbeat() throws RemoteException;


}
