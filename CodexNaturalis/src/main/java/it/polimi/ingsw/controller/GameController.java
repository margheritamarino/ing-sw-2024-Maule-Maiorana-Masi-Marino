package it.polimi.ingsw.controller;
//da capire addPlayer e disconnectPlayer

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.exceptions.MatchFull;
import it.polimi.ingsw.exceptions.NicknameAlreadyTaken;
import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Chat.Message;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

/**
 * GameController Class
 * Controls a specific Game {@link Game} by allowing a player to perform all actions that can be executed in a game
 * The class can add, remove, reconnect and disconnects players to the game and let players grab and position tiles
 * from the playground to the shelf. <br>
 * <br>
 * It manages all the game from the beginning (GameStatus.WAIT to the ending {GameStatus.Ended}
 */
public class GameController implements GameControllerInterface, Serializable, Runnable {

    /**
     * The {@link GameImmutable} to control
     */
    private final Game model;

    /**
     * A random object for implementing pseudo-random choice     */
    private final Random random = new Random();



    /**GameController Constructor
     * Init a GameModel
     */
    public GameController() throws FileNotFoundException, FileReadException, DeckEmptyException {
        model = new Game();
    }
    /** @return the list of the players currently playing in the Game (online and offline)     */
    public ArrayList<Player> getPlayers() {
        return model.getPlayers();
    }
    /**
     * Returns num of current players that are in the game (online and offline)
     *
     * @return num of current players
     */
    public int getNumOfPlayers() {
        return model.getNumPlayers();
    }

    /**
     * @return the number of online players that are in the game
     */
    @Override
    public int getNumOnlinePlayers() throws RemoteException{
        return model.getNumPlayersOnline();
    }

    public ObjectiveCard getGoalCard() {
        return model.getCurrentPlayerGoal();
    }


    @Override
    public boolean playerIsReadyToStart(String p) throws RemoteException {
        return false;
    }

    /**
     * Check if it's your turn
     *
     * @param nick the nickname of the player
     * @return true if it's your turn, false else
     * @throws RemoteException if there is a connection error (RMI)
     */
    @Override
    public synchronized boolean isThisMyTurn(String nick) throws RemoteException {
        return model.getCurrentPlayer().getNickname().equals(nick);
    }


    @Override
    public void disconnectPlayer(String nick, GameListener lisOfClient) throws RemoteException {
        model.setPlayerDisconnected(nick);
    }


//DA CAPIRE MODEL.ADDPLAYER
    /**
     * Adds a new player to the match.
     *
     * @param nick the nickname of the player to add.
     * @throws MatchFull if the match is already full and no more players can be added.
     * @throws NicknameAlreadyTaken if the specified nickname is already in use by another player.
     */
    public void addPlayer(String nick) throws MatchFull, NicknameAlreadyTaken {
        model.addPlayer(nick);
    }

    /**
     * Add a message to the chat list
     * @param msg to add
     * @throws RemoteException     */
    @Override
    public synchronized void sentMessage(Message msg) throws RemoteException {
        model.sentMessage(msg);
    }

    /**
     * gets th Game ID of the current Game
     * @return
     * @throws RemoteException
     */
    @Override
    public int getGameId() throws RemoteException {
        return model.getGameId();
    }



    @Override
    public void leave(GameListener lis, String nick) throws RemoteException {
        //IMPLEMENTA
    }
    @Override
    public void heartbeat(String nick, GameListener me) throws RemoteException {

    }
    @Override
    public void run() {
       // IMPLEMENTA
    }
}
