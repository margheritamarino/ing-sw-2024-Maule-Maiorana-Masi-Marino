package it.polimi.ingsw.controller;

//da capire addPlayer e disconnectPlayer
//implementa i metodi dell'interfaccia GameControllerInterface (cartella RMI). chiama i rispettivi metodi del model
//saranno da implementare altri metodi in base agli input della view

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.listener.GameListenerInterface;

import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.Ping;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameStatus;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.ingsw.view.PrintAsync.printAsync;

/**
 * GameController Class
 * Controls a specific Game {@link Game} by allowing a player to perform all actions that can be executed in a game
 * The class can add, remove, reconnect and disconnects players to the game and let players pick and place cards
 * from the Board to them Book. <br>
 * <br>
 * It manages all the game from the beginning (GameStatus.WAIT to the ending {GameStatus.Ended}
 */
public class GameController implements GameControllerInterface, Serializable, Runnable {

    /**
     * The {@link Game} to control
     */
    private Game model;

    /**
     * A random object for implementing pseudo-random choice     */
    private final Random random = new Random();


    /**
     * Singleton Pattern, instance of the class
     */
    private static GameController instance = null;
    private boolean gameCreated;

    private final transient Map<GameListenerInterface, Ping> receivedPings;
    /**GameController Constructor
     * Init a GameModel
     */
    public GameController()  {
        model = new Game();
       receivedPings = new HashMap<>();
       this.gameCreated= false;

        new Thread(this).start();
    }
    /**
     * Singleton Pattern
     *
     * @return the only one instance of the GameController class
     */
    public static synchronized GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
    public synchronized boolean isGameCreated(){
        return gameCreated;
    }


    /**
     * Add a ping to the map of received Pings
     *
     * @param nickname the player's nickname associated to the ping
     * @param me   the player's GameListener associated to the ping
     * @throws RemoteException
     */

    /**
     * this method adds a Ping to the receivedPing map
     * when a ClientMsgPing is sent and calls the method
     * @param nickname nickname of the player who sent the ping message
     * @param me the client who sends ping messages
     */
    @Override
    public synchronized void ping(String nickname, GameListenerInterface me) throws RemoteException {
        synchronized (receivedPings) {
            receivedPings.put(me, new Ping(System.currentTimeMillis(), nickname));
        }
    }

    /**
     * Monitors the ping messages to detect player disconnections.
     * This method runs in a separate thread and continuously checks
     * the status of the game and the received ping messages.
     * If a player is detected as disconnected based on the ping timeout,
     * the player is disconnected and removed from the ping map.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            //checks all the ping messages to detect disconnections
            if (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE) || model.getStatus().equals(GameStatus.ENDED) || model.getStatus().equals(GameStatus.WAIT)) {
                synchronized (receivedPings) {
                    // cerca nella mappa
                    Set<Map.Entry<GameListenerInterface, Ping>> entries = receivedPings.entrySet();
                    // Itera attraverso tutte le coppie chiave-valore nella mappa
                    for (Map.Entry<GameListenerInterface, Ping> entry : entries) {
                        GameListenerInterface listener = entry.getKey();
                        Ping ping = entry.getValue();

                        // Verifica se il giocatore è disconnesso
                        if (System.currentTimeMillis() - ping.getBeat() > DefaultValue.timeout_for_detecting_disconnection) {
                            try {
                                // Disconnette il giocatore
                                disconnectPlayer(ping.getNick(), listener);
                                printAsync("Disconnection detected by ping of player: " + ping.getNick());

                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            // Rimuove il giocatore dalla mappa dei ping
                            entries.remove(entry);
                        }
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Set the @param p player ready to start
     * When all the players are ready to start, the game starts (game status changes to running)
     *
     * @param p Player to set has ready
     */
    @Override
    public synchronized void playerIsReadyToStart( GameListenerInterface lis, String p) {
        model.playerIsReadyToStart(model.getPlayerByNickname(p));
        model.initializeCards( lis, model.getPlayerByNickname(p));

    }
    public boolean makeGameStart(GameListenerInterface lis, String nickname) {
        System.out.println(model.getNumReady());
        if (model.arePlayersReadyToStartAndEnough()) {
            model.chooseOrderPlayers(); //assegna l'ordine ai giocatori nbell'orderArray
            System.out.println(model.getCurrentPlayer().getNickname());
            int[] orderArray= model.getOrderArray();
            System.out.println(model.getPlayers().get(orderArray[0]));
         //   ArrayList<Player> players= model.getPlayers();
           //
           // model.setCurrentPlayer(players.get(orderArray[0]));
            model.initializeBoard();
            model.setInitialStatus();
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public synchronized void placeCardInBook(String playerName, int chosenCard, int rowCell, int colCell){
        Player currentPlayer = model.getPlayerByNickname(playerName);
        if(currentPlayer.equals(model.getCurrentPlayer())){
            int points= model.placeCardTurn(model.getPlayerByNickname(playerName), chosenCard, rowCell, colCell);
            if(points>=0)
                model.addPoints(model.getPlayerByNickname(playerName), points);
        }
    }


    @Override
    public synchronized void PickCardFromBoard(String nickname, CardType cardType, boolean drawFromDeck, int pos){

        Player p = model.getPlayerByNickname(nickname);
        if(p.equals(model.getCurrentPlayer())){
            model.pickCardTurn(p, cardType, drawFromDeck, pos);
        }


        if (model.getStatus().equals(GameStatus.RUNNING) ||model.getStatus().equals(GameStatus.LAST_CIRCLE)) {
            // Trova l'indice dell'attuale currentPlayer in orderArray
            int currentIndex = -1;
            for (int i = 0; i < model.getOrderArray().length; i++) {
                if (model.getPlayers().get(model.getOrderArray()[i]).equals(model.getCurrentPlayer())) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex == model.getNumPlayers() - 1) { //se sono nell'ultimo giocatore del giro
                if (!(model.getStatus().equals(GameStatus.LAST_CIRCLE))) {
                    if (model.getScoretrack().checkTo20()) { //= true -> e un giocatore è arrivato alla fine (chiamo ultimo turno)
                        model.setStatus(GameStatus.LAST_CIRCLE);
                    }
                } else { //se sono nell'ultimo giocatore nell'ultimo ciclo
                    model.lastTurnGoalCheck(); //controllo gli obbiettivo
                }
            }
            try{
                model.nextTurn(currentIndex);
            }catch (GameEndedException e){
                model.setStatus(GameStatus.ENDED);
            }
        }

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
    public void disconnectPlayer(String nick, GameListenerInterface listener) throws RemoteException {
        Player p = model.getPlayerByNickname(nick);
        if(p!=null) {
            model.removeListener(listener);
            model.setPlayerDisconnected(p);
            model.removePlayer(nick); //remove Player from the Game
            //if a player is disconnectes the Game finishes
            if (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE)|| model.getStatus().equals(GameStatus.WAIT) ) {
                model.setStatus(GameStatus.ENDED);
            }
        }

    }



    @Override
    public void setInitialCard(String playerName, int index) throws RemoteException {
        Player currentPlayer = model.getPlayerByNickname(playerName);
        model.setInitialCard(currentPlayer, index);
    }


    /**
     * gets th Game ID of the current Game
     * @return the ID of the game
     *
     */
    @Override
    public int getGameId() throws RemoteException {
        return model.getGameId();
    }

    /**
     * It removes a player by nickname @param nick from the game including the associated listeners
     * If a player leaves the game has to end so GameStatus is set to ENDED.
     *
     * @param lis  The listener (related to the client) to remove
     * @param nick of the player to remove
     */
    @Override
    public synchronized void leave(GameListenerInterface lis, String nick) throws RemoteException {
        model.getPlayerByNickname(nick).removeListener(lis);
        model.removeListener(lis);
        model.removePlayer(nick);
        if (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE)|| model.getStatus().equals(GameStatus.WAIT) ) {
            model.setStatus(GameStatus.ENDED);
        }
    }

    @Override
    public void setGoalCard(String playerName, int index) throws NotPlayerTurnException, RemoteException {
        Player currentPlayer = model.getPlayerByNickname(playerName);
        model.setPlayerGoal(currentPlayer, index);
    }

    /**
     * Create a new game and join to it
     *
     * @param lis GameListener of the player who is creating the game
     * @param nick Nickname of the player who is creating the game
     * @return GameControllerInterface associated to the created game
     * @throws RemoteException
     */
    @Override
    public synchronized void joinGame(GameListenerInterface lis, String nick) throws RemoteException {
        if(!isGameCreated()){
            model.createGame(lis, nick);


        }else{
            model.addPlayer(lis, nick);
            model.getPlayerByNickname(nick).setConnected(true);
        }
    }

    public void setGameCreated(boolean toSet){
        this.gameCreated= toSet;
    }

    public void settingGame(GameListenerInterface lis,int numPlayers, int GameID, String nick) throws RemoteException{
        model.setGameId(GameID);
        model.setPlayersNumber(numPlayers);
        setGameCreated(true);
        model.addPlayer(lis, nick);
        model.getPlayerByNickname(nick).setConnected(true);
    }


}
