package it.polimi.ingsw.listener;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameModelImmutable;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.rmi.RemoteException;

import static polimi.ingsw.networking.PrintAsync.printAsync;

/**
 * ListenersHandler class is responsible for managing a List of GameListener objects
 * and for notifying the view when a change occurs in the GameModel
 */
public class ListenersHandler {
    private List<GameListener> listeners;

    /**
     * Constructor
     */
    public ListenersHandler() {
        listeners = new ArrayList<>();
    }

    /**
     * Adds a new GameListener to the list of GameListeners
     * @param listener the listener to add
     */
    public void addListener(GameListener listener){
        listeners.add(listener);
    }

    /**
     * Removes a new GameListener from the list.
     * @param listener the listener to remove
     */
    public void removeListener(GameListener listener){
        listeners.remove(listener);
    }

    /**
     * Method getter that returns the list of GameListener
     * @return the list of GameListeners
     */
    public List<GameListener> getListeners() {
        return listeners;
    }

    /**
     * The notifyPlayerJoined method notifies the view that a player has joined the game
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notifyPlayerJoined(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerJoined(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerJoined, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerReconnected method notifies the view that a player has reconnected to the game
     * @param model is the Game to pass as a new GameModelImmutable
     * @param nickPlayerReconnected is the nickname of the player that has left the game and now is reconnected
     */
    public synchronized void notify_playerReconnected(Game model, String nickPlayerReconnected) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerReconnected(new GameModelImmutable(model), nickPlayerReconnected);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerReconnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    /**
     * The notify_JoinUnableGameFull method notifies that a player cannot join the game because the game is full
     * @param playerWantedToJoin is the player that wanted to join the game <br>
     * @param model is the GameModel to pass as a new GameModelImmutable
     */
    public synchronized void notify_JoinUnableGameFull(Player playerWantedToJoin, Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.joinUnableGameFull(playerWantedToJoin, new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_JoinUnableGameFull, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_JoinUnableNicknameAlreadyIn method notifies that a player cannot join the game because the nickname is already in use
     * @param playerWantedToJoin is the player that wanted to join the game
     */
    public synchronized void notify_JoinUnableNicknameAlreadyIn(Player playerWantedToJoin) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.joinUnableNicknameAlreadyIn(playerWantedToJoin);
            } catch (RemoteException e) {
                printAsync("During notification of notify_JoinUnableNicknameAlreadyIn, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_PlayerIsReadyToStart method notifies that a player is ready to start the game
     * @param model is the GameModel to pass as a new GameModelImmutable {@link GameModelImmutable}
     * @param nick is the nickname of the player that is ready to start the game
     */
    public synchronized void notify_PlayerIsReadyToStart(Game model, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerIsReadyToStart(new GameModelImmutable(model), nick);
            } catch (IOException e) {
                printAsync("During notification of notify_PlayerIsReadyToStart, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    /**
     * The notify_GameStarted method notifies that the game has started
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_GameStarted(Game model) {
        Iterator<GameListener> i = listeners.iterator(); //attraverso la lista e accedo agli elementi uno per volta

        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.gameStarted(new GameModelImmutable(model)); //ogni listener riceve una copia immutabile del game model
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameStarted, a disconnection has been detected before heartbeat");
                i.remove(); //metodo remove dell'iteratore che rimuove i listener che si sono disconnessi
            }
        }
    }

    /**
     * The notify_GameEnded method notifies that the game has ended
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_GameEnded(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.gameEnded(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameEnded, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_extractedCommonCard method notifies that a common card has been extracted
     * @param gamemodel is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_extractedCommonCard(Game gamemodel) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.commonCardsExtracted(new GameModelImmutable(gamemodel));
            } catch (RemoteException e) {
                printAsync("During notification of notify_extractedCommonCard, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerDisconnected method notifies that a player has disconnected
     * @param gamemodel is the Game to pass as a new GameModelImmutable
     * @param nick is the nickname of the player that has disconnected
     */
    public synchronized void notify_playerDisconnected(Game gamemodel, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerDisconnected(new GameModelImmutable(gamemodel), nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerDisconnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_lastCircle method notifies that the last circle has started
     * @param model is the GameModel to pass as a new GameModelImmutable
     */
    public void notify_LastCircle(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.lastCircle(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_LastCircle, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }




}
