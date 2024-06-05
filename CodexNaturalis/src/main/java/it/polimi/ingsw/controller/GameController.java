package it.polimi.ingsw.controller;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.listener.GameListenerInterface;

import it.polimi.ingsw.model.Color;
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
    private final Game model;


    /**
     * Singleton Pattern, instance of the class
     */
    private static GameController instance = null;
    private boolean gameCreated;
    private final transient Map<GameListenerInterface, Ping> receivedPings;

    /**GameController Constructor
     * Initializes a GameModel
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

    /**
     * Checks if the game is created.
     *
     * @return true if the game is created, false otherwise
     */
    public synchronized boolean isGameCreated(){
        return gameCreated;
    }

    /**
     * Adds a Ping to the receivedPing map when a ClientMsgPing is sent and calls the method.
     *
     * @param nickname the nickname of the player who sent the ping message
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
                    Iterator<Map.Entry<GameListenerInterface, Ping>> pingIter = receivedPings.entrySet().iterator();
                    // Itera attraverso tutte le coppie chiave-valore nella mappa
                    while (pingIter.hasNext()) {
                        Map.Entry<GameListenerInterface, Ping> el =  pingIter.next();
                        if (System.currentTimeMillis() - el.getValue().getBeat() > DefaultValue.timeout_for_detecting_disconnection) {
                            try {
                                this.disconnectPlayer(el.getValue().getNick(), el.getKey());
                                printAsync("Disconnection detected by Ping of player: "+el.getValue().getNick());

                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }

                            pingIter.remove();
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
     * @param lis    the GameListenerInterface of the player
     * @param player the nickname of the player
     * @return true if the game starts, false otherwise
     */
    @Override
    public synchronized boolean playerIsReadyToStart(GameListenerInterface lis, String player) {
        model.playerIsReadyToStart(model.getPlayerByNickname(player));
        if (model.arePlayersReadyToStartAndEnough()){
            ArrayList<Player> players= model.getPlayers();
            for(Player p: players){
                model.initializeCards( lis, p);
            }

            model.chooseOrderPlayers();
            model.initializeBoard();
            model.setInitialStatus();
            return true;

        }else {
            return false;
        }


    }

 /* public boolean makeGameStart(GameListenerInterface lis, String nickname) {
        System.out.println(model.getNumReady());
        if (model.arePlayersReadyToStartAndEnough()) {
            model.chooseOrderPlayers(); //assegna l'ordine ai giocatori nbell'orderArray
            //System.out.println(model.getCurrentPlayer().getNickname());
            int[] orderArray= model.getOrderArray();
           // System.out.println(model.getPlayers().get(orderArray[0]));
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

    }*/

    /**
     * Places a card in the book.
     *
     * @param playerName the name of the player placing the card
     * @param chosenCard the chosen card to place
     * @param rowCell    the row position to place the card
     * @param colCell    the column position to place the card
     */
    @Override
    public synchronized void placeCardInBook(String playerName, int chosenCard, int rowCell, int colCell){
        Player currentPlayer = model.getPlayerByNickname(playerName);
        if(currentPlayer.equals(model.getCurrentPlayer())){
            int points= model.placeCardTurn(model.getPlayerByNickname(playerName), chosenCard, rowCell, colCell);
            if(points>=0)
                model.addPoints(model.getPlayerByNickname(playerName), points);
        }
    }

    /**
     * Picks a card from the board.
     *
     * @param nickname   the nickname of the player picking the card
     * @param cardType   the type of card to pick
     * @param drawFromDeck indicates if the card should be drawn from the deck
     * @param pos        the position to pick the card from
     */
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

    /**
     * Disconnects a player from the game.
     *
     * @param nick     the nickname of the player to disconnect
     * @param listener the listener associated with the player
     * @throws RemoteException if there is a connection error (RMI)
     */
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


    /**
     * Sets the initial card for the specified player.
     *
     * @param playerName the name of the player
     * @param index the index of the card to set as the initial card
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void setInitialCard(String playerName, int index) throws RemoteException {
        Player currentPlayer = model.getPlayerByNickname(playerName);
        model.setInitialCard(currentPlayer, index);
    }


    /**
     * Gets th Game ID of the current Game.
     *
     * @return the ID of the game
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public int getGameId() throws RemoteException {
        return model.getGameId();
    }

    /**
     * Removes a player by nickname @param nick from the game including the associated listeners
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

    /**
     * Sets the objective card for the specified player.
     *
     * @param playerName the name of the player
     * @param index the index of the card to set as the objective card
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void setGoalCard(String playerName, int index) throws RemoteException {
        Player currentPlayer = model.getPlayerByNickname(playerName);
        model.setPlayerGoal(currentPlayer, index);
    }

    /**
     * Create a new game and join to it
     *
     * @param lis GameListener of the player who is creating the game
     * @param nick Nickname of the player who is creating the game
     * @param color the color chosen by the player
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void joinGame(GameListenerInterface lis, String nick, Color color) throws RemoteException {
        if(!isGameCreated()){
            model.createGame(lis, nick);


        }else{
            synchronized (Color.class) {
                Color randColor = Color.getRandomColor();
                model.addPlayer(lis, nick, randColor);
            }
            model.getPlayerByNickname(nick).setConnected(true);
        }
    }

    /**
     * Sets the game creation status.
     *
     * @param toSet the status to set
     */
    public void setGameCreated(boolean toSet){
        this.gameCreated= toSet;
    }

    /**
     * Sets up the game with the specified parameters.
     *
     * @param lis the GameListener of the player
     * @param numPlayers the number of players in the game
     * @param GameID the ID of the game
     * @param nick the nickname of the player
     * @param color the color chosen by the player
     * @throws RemoteException if a remote communication error occurs
     */
    public void settingGame(GameListenerInterface lis,int numPlayers, int GameID, String nick, Color color) throws RemoteException{
        model.setGameId(GameID);
        model.setPlayersNumber(numPlayers);
        setGameCreated(true);
        synchronized (Color.class) {
            Color randColor = Color.getRandomColor();
            model.addPlayer(lis, nick, randColor);
        }

        model.getPlayerByNickname(nick).setConnected(true);
    }

    /**
     * Adds a message to the chat list.
     *
     * @param msg the message to add
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void sentMessage(Message msg) throws RemoteException{
        System.out.println("GameController forwarding message: " + msg.getText());
        model.sentMessage(msg);
    }


}
