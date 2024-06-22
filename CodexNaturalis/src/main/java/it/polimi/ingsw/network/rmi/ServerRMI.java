package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.cards.CardType;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.network.PrintAsync.printAsync;

/**
 * RMIServer Class
 * Handle all the incoming network requests that clients can require to join or leave a game
 * by the RMI Network protocol
 */
public class ServerRMI extends UnicastRemoteObject implements GameControllerInterface {
    /**
     * ServerRMI object
     */
     private static ServerRMI serverObject = null;

    /**
     * Controller of out Game
     */
    private final GameControllerInterface gameController;


    /**
     * Registry associated with the RMI Server
     */
    private static Registry registry = null;

    /**
     * Create a RMI Server
     * @return the instance of the server
     */
    public static ServerRMI bind() throws RemoteException {
        try {
            serverObject = new ServerRMI();

            try {
                registry = LocateRegistry.getRegistry(DefaultValue.Default_port_RMI);
                registry.list();
            } catch (RemoteException e) {
                registry = LocateRegistry.createRegistry(DefaultValue.Default_port_RMI);
            }

            getRegistry().rebind(DefaultValue.Default_servername_RMI, serverObject);

            System.out.println("RMI server started on port " + DefaultValue.Default_port_RMI + ".");
            printAsync("Server RMI ready");

        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
            throw e;
        }
        return getInstance();
    }




    /**
     * @return the istance of the RMI Server
     */
    public synchronized static ServerRMI getInstance() {
        if(serverObject == null) {
            try {
                serverObject = new ServerRMI();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return serverObject;
    }

    /**
     * @return the registry associated with the RMI Server
     * @throws RemoteException
     */
    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }

    /**
     * Constructor that creates a RMI Server
     * @throws RemoteException
     */
    public ServerRMI() throws RemoteException {
        super();
        gameController= GameController.getInstance();
    }


    /**
     * Close the RMI Server
     * Used only for testing purposes
     *
     * @return RMI Server
     */
    public static ServerRMI unbind() throws RemoteException{
        try {
            getRegistry().unbind(DefaultValue.Default_servername_RMI);
            UnicastRemoteObject.unexportObject(getRegistry(), true);
            printAsync("Server RMI correctly closed");
        } catch (RemoteException e) {
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getInstance();
    }

    /**
     * Requests to join the game with the specified nickname and attaches the provided listener for game events.
     *
     * @param lis  The listener interface for receiving game events.
     * @param nick The nickname of the player joining the game.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
   @Override
   public void joinGame(GameListenerInterface lis, String nick) throws RemoteException {

           serverObject.gameController.joinGame(lis, nick);
           /*try {
               UnicastRemoteObject.exportObject(serverObject.gameController,0);
           } catch (RemoteException e) {
               // Already exported, due to another RMI Client running on the same machine
           }*/
           printAsync("[RMI] " + nick + " joined to game");
   }

    /**
     * Informs the server that the player associated with the provided listener and nickname is leaving the game.
     *
     * @param lis  The listener interface for receiving game events.
     * @param nick The nickname of the player leaving the game.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void leave(GameListenerInterface lis, String nick) throws RemoteException {
        //gameController.leave(lis, nick);
        serverObject.gameController.leave(lis, nick);
    }


    /**
     * Requests to reconnect the player with the specified listener and nickname to the current game session.
     *
     * @param lis  The listener interface for receiving game events.
     * @param nick The nickname of the player reconnecting to the game.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void reconnect(GameListenerInterface lis, String nick) throws RemoteException{
        serverObject.gameController.reconnect(lis,nick);
    }


    /**
     * Checks if the player associated with the provided listener and nickname is ready to start the game.
     *
     * @param lis The listener interface for receiving game events.
     * @param p   The nickname of the player.
     * @return True if the player is ready to start the game; false otherwise.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    public boolean  playerIsReadyToStart(GameListenerInterface lis, String p) throws RemoteException { //siamo sicuri serva Server RMI? FORSE DEVI PASSARE DIRETTA AL GameController
        System.out.println("in ServerRMI- playerIsReadyToStart");
        //return gameController.playerIsReadyToStart(lis, p);
         return serverObject.gameController.playerIsReadyToStart(lis, p);
        //return false;
    }


    /**
     * Checks if it is the turn of the player associated with the provided nickname.
     *
     * @param nick The nickname of the player.
     * @return True if it is the player's turn; false otherwise.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public boolean isThisMyTurn(String nick) throws RemoteException {
        //return gameController.isThisMyTurn(nick);
        return serverObject.gameController.isThisMyTurn(nick);
    }


    /**
     * Requests to disconnect the player associated with the provided nickname and listener from the game.
     *
     * @param nick     The nickname of the player to disconnect.
     * @param listener The listener interface associated with the player.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void disconnectPlayer(String nick, GameListenerInterface listener) throws RemoteException {
        //gameController.disconnectPlayer(nick, listener);
       serverObject.gameController.disconnectPlayer(nick, listener);
    }


    /**
     * Retrieves the ID of the current game session.
     *
     * @return The ID of the game session.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public int getGameId() throws RemoteException {
        //return gameController.getGameId();
        return serverObject.gameController.getGameId();
    }


    //TODO DA CONTROLLARE PING:
    /**
     * Sends a ping message to the server to maintain the connection and check responsiveness.
     *
     * @param nickname The nickname of the player sending the ping.
     * @param me       The listener interface for receiving game events.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void ping(String nickname, GameListenerInterface me) throws RemoteException {
        //gameController.ping(nickname, me);
        serverObject.gameController.ping(nickname, me);
    }


    /**
     * Informs the server to set the initial card for the specified player.
     *
     * @param nickname The nickname of the player.
     * @param index    The index of the initial card to be set.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void setInitialCard(String nickname, int index) throws RemoteException {
        //gameController.setInitialCard(nickname,index);
        serverObject.gameController.setInitialCard(nickname,index);
    }


    /**
     * Informs the server to set the goal card for the specified player.
     *
     * @param nickname The nickname of the player.
     * @param index    The index of the goal card to be set.
     * @throws NotPlayerTurnException If it's not the player's turn to set the goal card.
     * @throws RemoteException       If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void setGoalCard(String nickname, int index) throws NotPlayerTurnException, RemoteException, NotPlayerTurnException {
        //gameController.setGoalCard(nickname,index);
        serverObject.gameController.setGoalCard(nickname,index);
    }


    /**
     * Informs the server to place a card in the player's book at the specified position.
     *
     * @param nickname    The nickname of the player.
     * @param chosenCard  The chosen card to be placed.
     * @param rowCell     The row position in the book.
     * @param columnCell  The column position in the book.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void placeCardInBook(String nickname, int chosenCard, int rowCell, int columnCell) throws RemoteException {
        //gameController.placeCardInBook(nickname, chosenCard, rowCell, columnCell);
        serverObject.gameController.placeCardInBook(nickname, chosenCard, rowCell, columnCell);
    }

    /**
     * Informs the server to pick a card from the board for the specified player.
     *
     * @param nickname     The nickname of the player.
     * @param cardType     The type of card to pick.
     * @param drawFromDeck Indicates whether the card is drawn from the deck or board.
     * @param pos          The position of the card on the board.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void PickCardFromBoard(String nickname, CardType cardType, boolean drawFromDeck, int pos) throws RemoteException {
        //gameController.PickCardFromBoard(nickname, cardType, drawFromDeck, pos);
        serverObject.gameController.PickCardFromBoard(nickname, cardType, drawFromDeck, pos);
    }


    /**
     * Informs the server to set up the game with the specified number of players, game ID, and nickname.
     *
     * @param lis       The listener interface for receiving game events.
     * @param numPlayers The number of players in the game.
     * @param gameID     The ID of the game.
     * @param nickname   The nickname of the player setting up the game.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void settingGame(GameListenerInterface lis, int numPlayers, int gameID, String nickname) throws RemoteException {
        //gameController.settingGame(lis, numPlayers, gameID, nickname, color);
        serverObject.gameController.settingGame(lis, numPlayers, gameID, nickname);
    }


    /**
     * Informs the server to start the game for the specified player.
     *
     * @param lis      The listener interface for receiving game events.
     * @param nickname The nickname of the player starting the game.
     * @return True if the game is successfully started; false otherwise.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public boolean makeGameStart(GameListenerInterface lis, String nickname) throws RemoteException {
        //return gameController.makeGameStart(lis, nickname);
       return serverObject.gameController.makeGameStart(lis, nickname);
    }


    /**
     * Sends a message to the server to be distributed to other players or processed as required.
     *
     * @param msg The message object containing the details of the message to be sent.
     * @throws RemoteException If there is a communication-related issue during the remote method invocation.
     */
    @Override
    public void sentMessage(Message msg) throws RemoteException { //messaggi da sincronizzare (?) Se più giocatori insieme accedono alla risorsa
        //gameController.sentMessage(msg);
        serverObject.gameController.sentMessage(msg);
    }


}

