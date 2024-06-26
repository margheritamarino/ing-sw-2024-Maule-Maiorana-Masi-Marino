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
import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * Timer started when only one player is playing (usefull during a Disconnection of a player)
     * it ends the game if no one reconnects within {@link DefaultValue#secondsToWaitReconnection} seconds
     */
    private Thread reconnectionTh;


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
    public void ping(String nickname, GameListenerInterface me) throws RemoteException {
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
                    System.out.println("in GameController - run() --> synchronized (receivedPings)\n");
                    // cerca nella mappa
                    Iterator<Map.Entry<GameListenerInterface, Ping>> pingIter = receivedPings.entrySet().iterator();
                    // Itera attraverso tutte le coppie chiave-valore nella mappa
                    while (pingIter.hasNext()) {
                        Map.Entry<GameListenerInterface, Ping> el = pingIter.next();
                        if (System.currentTimeMillis() - el.getValue().getBeat() > DefaultValue.timeout_for_detecting_disconnection) {
                            try {
                                this.disconnectPlayer(el.getValue().getNick(), el.getKey());
                                printAsync("Disconnection detected by Ping of player: " + el.getValue().getNick());

                                if (model.getNumOfOnlinePlayers() == 0) {
                                    stopReconnectionTimer();
                                    if (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE) || model.getStatus().equals(GameStatus.WAIT)) {
                                        model.setStatus(GameStatus.ENDED);
                                    }
                                    //GameController.getInstance().deleteGame();--> NON serve avendo quell'unico Game
                                    return;
                                }
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
    public synchronized boolean playerIsReadyToStart(GameListenerInterface lis, String player) { //tolto synchronized
        System.out.println("in GameController- playerIsReadyToStart");
        if(model.getPlayerByNickname(player)!=null)
            model.playerIsReadyToStart(model.getPlayerByNickname(player));
        else
            System.err.println("Exception getPlayerByNickname in playerIsReady");
        if (model.arePlayersReadyToStartAndEnough()){
            ArrayList<Player> players= model.getPlayers();
            for(int i=0; i<players.size(); i++){
                if(!players.get(i).isInitialized())
                    model.initializeCards( lis, players.get(i), i);
            }
            return true;

        }else {
            return false;
        }


    }

    public synchronized boolean makeGameStart( GameListenerInterface lis, String nickname) {

        if (model.allPlayersHaveChosenGoals()) {
            model.chooseOrderPlayers(); //assegna l'ordine ai giocatori nbell'orderArray

            model.initializeBoard();
            model.setInitialStatus();
            return true;
        }
        else {
            return false;
        }

    }

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
    public void disconnectPlayer(String nick, GameListenerInterface listener) throws RemoteException { //TOLTO SYNCHRONIZED
        Player p = model.getPlayerByNickname(nick);
        if(p!=null) {
            model.removeListener(listener);
            p.removeListener(listener);

            if (model.getStatus().equals(GameStatus.WAIT)) {
                //The game is in Wait (game not started yet), the player disconnected, so I remove him from the game)
                model.removePlayer(nick); //remove Player from the Game
                 if (model.getStatus().equals(GameStatus.WAIT) && model.getNumPlayers()==0){
                    setGameCreated(false);
                 }
            } else {
                //Tha game is running, so I set him as disconnected (He can reconnect soon)
                model.setAsDisconnected(p.getNickname());
                System.out.println("GameController- player"+ nick + "setted DISCONNECTED");
            }
            //if a player is disconnected the Game finishes
            // if (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE)|| model.getStatus().equals(GameStatus.WAIT) ) {
            //    model.setStatus(GameStatus.ENDED);
            //}
            //Check if there is only one player playing
            if ((model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE)) && model.getNumOfOnlinePlayers() == 1) {
                //Starting a th for waiting until reconnection at least of 1 client to keep playing
                if (reconnectionTh == null) {
                    startReconnectionTimer();
                    printAsync("Starting timer for reconnection waiting: " + DefaultValue.secondsToWaitReconnection + " seconds");
                }
            }
        }

    }

    /**
     * Reconnect a player to the game
     * @param lis the GameListener of the player {@link GameListenerInterface}
     * @param nick the nickname of the player
     * @throws RemoteException
     */
    @Override
    public synchronized void reconnect(GameListenerInterface lis, String nick) throws RemoteException {
        System.out.println("In GameController - reconnect() \n");
        Player disconnectedPlayer;

         disconnectedPlayer = model.getPlayerByNickname(nick);
          //The game exists, check if nickname exists
         if(disconnectedPlayer!=null) {
             disconnectedPlayer.addListener(lis);
             model.addListener(lis);
             this.reconnectPlayer(lis,disconnectedPlayer);
         }
         else{
           //Game exists but the nick no
             printAsync("The nickname used was not connected in a running game");
         }
    }

        /**
         * Starts a timer for detecting the reconnection of a player, if no one reconnects in time, the game is over
         */
    @SuppressWarnings("BusyWait")
    private void startReconnectionTimer() {
        reconnectionTh = new Thread(
                () -> {
                    long startingtimer = System.currentTimeMillis();

                    while (reconnectionTh != null && !reconnectionTh.isInterrupted() && System.currentTimeMillis() - startingtimer < DefaultValue.secondsToWaitReconnection * 1000) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            //Someone called interrupt on this th (no need to keep waiting)
                        }
                    }
                    printAsync("Timer for reconnection ended");

                    if (model.getNumOfOnlinePlayers() == 0) {
                        //No players online: I set GameStatus ENDED
                        if (model.getStatus().equals(GameStatus.RUNNING) || model.getStatus().equals(GameStatus.LAST_CIRCLE)|| model.getStatus().equals(GameStatus.WAIT) ) {
                            model.setStatus(GameStatus.ENDED);
                        }
                        //GameController.getInstance().deleteGame();
                    } else if (model.getNumOfOnlinePlayers() == 1) {
                        printAsync("\tNo player reconnected on time, set game to ended!");
                        model.setStatus(GameStatus.ENDED);
                    } else {
                        printAsync("\tA player reconnected on time");
                        this.reconnectionTh = null;
                    }
                }

        );
        reconnectionTh.start();
    }

    /**
     * It stops the timer (if started) that checks for reconnections of players
     */
    private void stopReconnectionTimer() {
        if (reconnectionTh != null) {
            reconnectionTh.interrupt();
            reconnectionTh = null;
        }
        //else It means that a player reconnected but the timer was not started (ex 3 players and 1 disconnects)
    }

    /**
     * Reconnect the player with the nickname @param to the game
     *
     * @param p Player that want to reconnect
     */
    public void reconnectPlayer(GameListenerInterface lis, Player p) {
        System.out.println("In GameController - reconnectPlayer() \n");
        model.checkPlayerStatus(p);
        boolean outputres = model.reconnectPlayer(lis, p);

        if (outputres && getNumOfOnlinePlayers() > 1) {
            stopReconnectionTimer();
        }
        //else nobody was connected and now one player has reconnected before the timer expires
    }

    /**
     * @return the number of online players that are in the game
     */
    public int getNumOfOnlinePlayers() {
        return model.getNumOfOnlinePlayers();
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
        Player player = model.getPlayerByNickname(playerName);
        model.setPlayerGoal(player, index);

    }

    /**
     * Create a new game and join to it
     *
     * @param lis GameListener of the player who is creating the game
     * @param nick Nickname of the player who is creating the game
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void joinGame(GameListenerInterface lis, String nick) throws RemoteException {
        if(!isGameCreated()){
            System.out.println("GameController - JoinGame: creating a new Game");
            model.createGame(lis, nick);


        }else{
            System.out.println("GameController - JoinGame: Game already existing");
            //synchronized (Color.class) {
            Color randColor = Color.getRandomColor();
            model.addPlayer(lis, nick, randColor);
            //}
            //model.getPlayerByNickname(nick).setConnected(true); //meglio metterlo in game ADD_PLAYER?
           // System.out.println("GameController - JoinGame: Player setted CONNECTED");
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
     * @throws RemoteException if a remote communication error occurs
     */
    public void settingGame(GameListenerInterface lis,int numPlayers, int GameID, String nick) throws RemoteException{
        model.setGameId(GameID);
        model.setPlayersNumber(numPlayers);
        setGameCreated(true);
        //synchronized (Color.class) {
        Color randColor = Color.getRandomColor();
        model.addPlayer(lis, nick, randColor);
        //}

        //model.getPlayerByNickname(nick).setConnected(true);
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