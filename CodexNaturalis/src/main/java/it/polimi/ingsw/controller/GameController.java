package it.polimi.ingsw.controller;

//da capire addPlayer e disconnectPlayer
//implementa i metodi dell'interfaccia GameControllerInterface (cartella RMI). chiama i rispettivi metodi del model
//saranno da implementare altri metodi in base agli input della view

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.listener.GameListenerInterface;

import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameStatus;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

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
     * A random object for implementing pseudo-random choice     */
    private final Random random = new Random();


    /**
     * Singleton Pattern, instance of the class
     */
    private static GameController instance = null;

    /**GameController Constructor
     * Init a GameModel
     */
    public GameController()  {
        //Map<GameListenerInterface, Heartbeat> heartbeats;
       // this.heartbeats = heartbeats;
        model = new Game();
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
     * @return the number of online players that are in the game
     */
    @Override
    public int getNumOnlinePlayers() throws RemoteException{
        return model.getNumPlayersOnline();
    }

    /**
     * Set the @param p player ready to start
     * When all the players are ready to start, the game starts (game status changes to running)
     * @param p Player to set has ready
     * @return true if the game has started, false else            */
    @Override
    public synchronized boolean playerIsReadyToStart(String p) {
        model.playerIsReadyToStart(model.getPlayerByNickname(p));
        model.initializeCards(model.getPlayerByNickname(p));

        //La partita parte automaticamente quando tutti i giocatori sono pronti
        if (model.arePlayersReadyToStartAndEnough()) {
            model.chooseOrderPlayers(); //assegna l'ordine ai giocatori nbell'orderArray
            ArrayList<Player> players= model.getPlayers();
            int[] orderArray= model.getOrderArray();
            model.setCurrentPlayer(players.get(orderArray[0]));

            model.initializeBoard();
            model.setInitialStatus();
            return true;
        }
        return false;//Game non started yet
    }


    @Override
    public synchronized void placeCardInBook(String playerName, int chosenCard, int rowCell, int colCell){
        Player currentPlayer = model.getPlayerByNickname(playerName);
        if(currentPlayer.equals(model.getCurrentPlayer())){
            int points= model.placeCardTurn(model.getPlayerByNickname(playerName), chosenCard, rowCell, colCell);
            model.addPoints(model.getPlayerByNickname(playerName), points);

        }
    }

    @Override
    public synchronized void PickCardFromBoard(String nickname, CardType cardType, boolean drawFromDeck, int pos){

        //TODO
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
    public void disconnectPlayer(String nick, GameListenerInterface lisOfClient) throws RemoteException {
        //TODO
    }



    @Override
    public synchronized void setInitialCard(String playerName, int index) {
        Player currentPlayer = model.getPlayerByNickname(playerName);
        model.setInitialCard(currentPlayer, index);
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

    /**
     * It removes a player by nickname @param nick from the game including the associated listeners
     *
     * @param lis  GameListener to remove
     * @param nick of the player to remove
     * @return
     * @throws RemoteException
     */
    @Override
    public synchronized void leave(GameListenerInterface lis, String nick) throws RemoteException {
        model.removeListener(lis);
        model.removePlayer(nick);
        //return null;
    }

    @Override
    public synchronized void setGoalCard(String playerName, int index) throws NotPlayerTurnException {
        Player currentPlayer = model.getPlayerByNickname(playerName);
        if(currentPlayer.equals(model.getCurrentPlayer())){
            model.setPlayerGoal(currentPlayer, index);
        }else{
            throw new NotPlayerTurnException("ERROR: not the Player's turn");
        }
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
    public void joinGame(GameListenerInterface lis, String nick) throws RemoteException {
        System.out.println("Metodo execute del 1° messaggio è stato chiamato: sono entrato nel metodo JoinGame in GameController");
        if(model.getNumPlayers()==0){

            model.setGameId(1);
        }
        System.out.println("gameController joinGame");
        model.addListener(lis);
        model.addPlayer(nick);
        //return getInstance();

    }



    @Override
    public void heartbeat(String nick, GameListenerInterface me) throws RemoteException {
        //TODO
    }
    @Override
    public void run() {
        //TODO
    }

}
