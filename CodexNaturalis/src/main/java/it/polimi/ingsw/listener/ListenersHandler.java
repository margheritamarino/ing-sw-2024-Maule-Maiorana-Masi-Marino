package it.polimi.ingsw.listener;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
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

//ASCOLTATORI DEL CLIENT E DEL SERVER
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
     * @param listener the listener to add
     */
    public void addListener(GameListenerInterface listener){
        listeners.add(listener);
        System.out.println("Client correttamente aggiunto come LISTENER del Server");
    }

    public synchronized void notify_requireNumPlayersGameID(GameListenerInterface listener,Game model ){
        System.out.println("ListenersHandler: notify_requireNumPlayersGameID");
        try {
            listener.requireNumPlayersGameID(new GameImmutable(model));
        }catch (RemoteException e){
            printAsync("During notification of notify_askCreateGame, a disconnection has been detected before heartbeat");
        }

    }
    /**
     * Removes a new GameListener from the list.
     * @param listener the listener to remove
     */
    public void removeListener(GameListenerInterface listener){
        listeners.remove(listener);
    }

    /**
     * Method getter that returns the list of GameListener
     * @return the list of GameListeners
     */
    public List<GameListenerInterface> getListeners() {
        return listeners;
    }

    /**
     * The notifyPlayerJoined method notifies the view that a player has joined the game
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_PlayerJoined(Game model, String nickname) {
        System.out.println("ListenersHandler: notify_PlayerJoined");
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.playerJoined(new GameImmutable(model), nickname);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerJoined, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_NotCorrectChosenCard method notifies that a player chose a card that cannot place in his Book
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_NotCorrectChosenCard(Game model){
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.wrongChooseCard( new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_NotCorrectChoosenCard, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }




    /**
     * The notify_PlayerLeft method notifies that a player has left the game
     * @param model is the Game to pass as a new GameModelImmutable
     * @param nickname is the nickname of the player that has left
     */
    public synchronized void notify_PlayerLeft(Game model, String nickname) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.playerLeft(new GameImmutable(model), nickname);
            } catch (RemoteException e) {
                printAsync("During notification of notify_PlayerLeft, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    /**
     * The notify_JoinUnableGameFull method notifies that a player cannot join the game because the game is full
     * @param triedToJoin is the player that wanted to join the game <br>
     * @param model is the GameModel to pass as a new GameModelImmutable
     */
    public synchronized void notify_JoinUnableGameFull(GameListenerInterface lis, Player triedToJoin, Game model) {
        try {
            lis.joinUnableGameFull(triedToJoin, new GameImmutable(model));
        } catch (RemoteException e) {
            printAsync("During notification of notify_JoinUnableGameFull, a disconnection has been detected before heartbeat");
        }

    }

    /**
     * The notify_JoinUnableNicknameAlreadyIn method notifies that a player cannot join the game because the nickname is already in use

     */
    public synchronized void notify_JoinUnableNicknameAlreadyIn(GameListenerInterface lis, Player triedToJoin ) {
        try {
            lis.joinUnableNicknameAlreadyIn(triedToJoin);
        } catch (RemoteException e) {
            printAsync("During notification of notify_JoinUnableGameFull, a disconnection has been detected before heartbeat");
        }
    }



    /**
     * The notify_GameStarted method notifies that the game has started
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_GameStarted(Game model) {
        Iterator<GameListenerInterface> i = listeners.iterator(); //attraverso la lista e accedo agli elementi uno per volta

        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.gameStarted(new GameImmutable(model)); //ogni listener riceve una copia immutabile del game model
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
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.gameEnded(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameEnded, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }



        /** The notify_CardPlaced method notifies that a card has been placed on the board
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_CardPlaced(Game model) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.cardPlaced(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_CardPlaced, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_CardDrawn method notifies that a card has been drawn
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_CardDrawn(Game model) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.cardDrawn(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_CardDrawn, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_nextTurn method notifies that the next turn has started
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_nextTurn(Game model) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.nextTurn(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_nextTurn, a disconnection has been detected before heartbeat");
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
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.playerDisconnected(new GameImmutable(gamemodel), nick);
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
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.lastCircle(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_LastCircle, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    public void notify_PointsAdded(Game model) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.pointsAdded(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_LastCircle, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


}
