package it.polimi.ingsw.listener;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameImmutable;

import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.rmi.RemoteException;

import static it.polimi.ingsw.network.PrintAsync.printAsync;

/**
 * ListenersHandler class is responsible for managing a List of GameListener objects
 * and for notifying the view when a change occurs in the GameModel
 */
public class ListenersHandler {
    private ArrayList<GameListenerInterface> listeners;

    /**
     * Constructor
     */
    public ListenersHandler() {
        listeners = new ArrayList<>();
    }

    /**
     * Adds a new GameListener to the list of GameListeners
     *
     * @param listener the listener to add
     */
    public void addListener(GameListenerInterface listener){ //tolgo SYNCHRONIZED
        listeners.add(listener);
        System.out.println("Client correttamente aggiunto come LISTENER del Server");
    }

    /**
     * Notifies the listener to require the number of players and game ID.
     *
     * @param listener the listener to notify
     * @param model    the game model
     */
    public void notify_requireNumPlayersGameID(GameListenerInterface listener,Game model ){ //tolgo SYNCHRONIZED
        System.out.println("ListenersHandler: notify_requireNumPlayersGameID");
        try {
            listener.requireNumPlayersGameID(new GameImmutable(model));
        }catch (RemoteException e){
            printAsync("During notification of notify_askCreateGame, a disconnection has been detected before ping");
        }

    }

    /**
     * Removes a new GameListener from the list.
     *
     * @param listener the listener to remove
     */
    public synchronized void removeListener(GameListenerInterface listener){
        listeners.remove(listener);
    }

    /**
     * Gets the list of GameListeners.
     *
     * @return the list of GameListeners
     */
    public synchronized List<GameListenerInterface> getListeners() {
        return listeners;
    }

    /**
     * Notifies the view that a player has joined the game.
     *
     * @param model       the game model to pass as a new GameModelImmutable
     * @param nickname    the nickname of the player who joined
     * @param playerColor the color of the player who joined
     */
    public void notify_PlayerJoined(Game model, String nickname, Color playerColor) { //tolgo synchronized
        System.out.println("ListenersHandler: notify_PlayerJoined");
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.playerJoined(new GameImmutable(model), nickname, playerColor);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerJoined, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies that a player has left the game.
     *
     * @param model    the game model to pass as a new GameModelImmutable
     * @param nickname the nickname of the player who left
     */
    public void notify_PlayerLeft(Game model, String nickname) { //tolgo SYNCHRONIZED
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.playerLeft(new GameImmutable(model), nickname);
            } catch (RemoteException e) {
                printAsync("During notification of notify_PlayerLeft, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies that a player cannot join the game because the game is full.
     *
     * @param lis         the listener to notify
     * @param triedToJoin the player who wanted to join the game
     * @param model       the game model to pass as a new GameModelImmutable
     */
    public void notify_JoinUnableGameFull(GameListenerInterface lis, Player triedToJoin, Game model) { //tolgo SYNCHRONIZED
        try {
            lis.joinUnableGameFull(triedToJoin, new GameImmutable(model));
        } catch (RemoteException e) {
            printAsync("During notification of notify_JoinUnableGameFull, a disconnection has been detected before ping");
        }

    }

    /**
     * Notifies that a player cannot join the game because the nickname is already in use.
     *
     * @param lis         the listener to notify
     * @param triedToJoin the player who wanted to join the game
     */
    public void notify_JoinUnableNicknameAlreadyIn(GameListenerInterface lis, Player triedToJoin, Game model ) { //tolgo SYNCHRONIZED
        try {
            lis.joinUnableNicknameAlreadyIn(triedToJoin,new GameImmutable(model));
        } catch (RemoteException e) {
            printAsync("During notification of notify_JoinUnableGameFull, a disconnection has been detected before ping");
        }
    }

    /**
     * Notifies that the game has started.
     *
     * @param model the game model to pass as a new GameModelImmutable
     */
    public void notify_GameStarted(Game model) { //tolgo SYNCHRONIZED
        Iterator<GameListenerInterface> i = listeners.iterator(); //attraverso la lista e accedo agli elementi uno per volta

        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.gameStarted(new GameImmutable(model)); //ogni listener riceve una copia immutabile del game model
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameStarted, a disconnection has been detected before ping");
                i.remove(); //metodo remove dell'iteratore che rimuove i listener che si sono disconnessi
            }
        }
    }

    /**
     * Notifies that the game has ended.
     *
     * @param model the game model to pass as a new GameModelImmutable
     */
    public void notify_GameEnded(Game model) { //tolgo SYNCHRONIZED
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.gameEnded(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameEnded, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies that the next turn has started.
     *
     * @param model the game model to pass as a new GameModelImmutable
     */
    public void notify_nextTurn(Game model) { //tolgo SYNCHRONIZED
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.nextTurn(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_nextTurn, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies that a player has disconnected.
     *
     * @param gamemodel the game model to pass as a new GameModelImmutable
     * @param nick      the nickname of the player who disconnected
     */
    public void notify_playerDisconnected(Game gamemodel, String nick) { //tolgo SYNCHRONIZED
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.playerDisconnected(new GameImmutable(gamemodel), nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerDisconnected, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies that the last circle has started.
     *
     * @param model the game model to pass as a new GameModelImmutable
     */
    public void notify_LastCircle(Game model) { //tolgo SYNCHRONIZED
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.lastCircle(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_LastCircle, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies that points have been added.
     *
     * @param currentPlayerLis the list of listeners for the current player
     * @param model            the game model to pass as a new GameModelImmutable
     */
    public void notify_PointsAdded(ArrayList<GameListenerInterface> currentPlayerLis, Game model) { //tolgo SYNCHRONIZED
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                if (!currentPlayerLis.contains(l)) {
                    l.pointsAdded(new GameImmutable(model));
                }else{
                    l.cardPlaced(new GameImmutable(model));

                }
            } catch (RemoteException e) {
                printAsync("During notification of notify_PointsAdded, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies listeners of a new message sent in the game chat.
     *
     * @param gameModel the game model to pass as a new GameModelImmutable
     * @param msg       the message that was sent
     */
    public synchronized void notify_SentMessage(Game gameModel, Message msg) {
        System.out.println("Notifying listeners of new message: " + msg.getText());
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.sentMessage(new GameImmutable(gameModel), msg);
                System.out.println("Listener notified: " + l.toString());
            } catch (RemoteException e) {
                printAsync("During notification of notify_SentMessage, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerReconnected method notifies the view that a player has reconnected to the game
     *
     * @param model is the GameModel {@link Game} to pass as a new GameModelImmutable {@link GameImmutable}
     * @param nickPlayerReconnected is the nickname of the player that has left the game and now is reconnected
     */
    public void notify_playerReconnected(Game model, String nickPlayerReconnected) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.playerReconnected(new GameImmutable(model), nickPlayerReconnected);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerReconnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }
    public void notify_ReconnectionFailed(String msg) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.errorReconnecting(msg);
            } catch (RemoteException e) {
                printAsync("During notification of notify_ReconnectionFailed, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }
    /**
     * Notifies the view to ask for reconnection if a player is trying to reconnect.
     *
     * @param lis the GameListenerInterface to notify
     * @param triedToJoin the player who is trying to reconnect
     * @param model the current game model to pass as a new GameModelImmutable {@link GameImmutable}
     */
    public void notify_AskForReconnection(GameListenerInterface lis, Player triedToJoin, Game model ){
        try {
            lis.AskForReconnection(triedToJoin,new GameImmutable(model));
        } catch (RemoteException e) {
            printAsync("During notification of notify_JoinUnableGameFull, a disconnection has been detected before ping");
        }
    }

    /**
     * The notify_onlyOnePlayerConnected method notifies that only one player is connected <br>
     * @param model is the GameModel {@link Game} to pass as a new GameModelImmutable {@link GameImmutable} <br>
     * @param secondsToWaitUntillGameEnded is the number of seconds to wait untill the game ends
     */
    public synchronized void notify_onlyOnePlayerConnected(Game model, int secondsToWaitUntillGameEnded) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.onlyOnePlayerConnected(new GameImmutable(model), secondsToWaitUntillGameEnded);
            } catch (RemoteException e) {
                printAsync("During notification of notify_onlyOnePlayerConnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }




}
