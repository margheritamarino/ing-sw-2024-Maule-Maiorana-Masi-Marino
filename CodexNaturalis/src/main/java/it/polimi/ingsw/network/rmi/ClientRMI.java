package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;

import it.polimi.ingsw.model.cards.CardType;

import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.network.PingSender;
import it.polimi.ingsw.network.socket.client.GameListenersClient;
import it.polimi.ingsw.view.flow.Flow;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
import static it.polimi.ingsw.view.TUI.PrintAsync.printAsyncNoLine;



/**
 * RMIClient Class <br>
 * Handle all the network communications between RMIClient and RMIServer <br>
 * From the first connection, to the creation, joining, leaving, grabbing and positioning messages through the network<br>
 * by the RMI Network Protocol
 */
public class ClientRMI implements ClientInterface {

    /**
     * The remote object returned by the registry that represents the game controller
     */
    private static GameControllerInterface gameController;

    /**
     * The remote object on which the server will invoke remote methods
     */
    private static GameListenerInterface modelInvokedEvents;

    /**
     * The nickname associated to the client (!=null only when connected in a game)
     */
    private String nickname;

    /**
     * Client listeners of the game
     * The remote object on which the server will invoke remote methods
     */
    private final GameListenersClient gameListenersHandler;

    /**
     * Registry of the RMI
     */
    private Registry registry;

    /**
     * Flow to notify network error messages
     */
    private Flow flow;

    /**
     * to send Ping
     */
    private PingSender pingSender;

    /**
     * Create, start and connect a RMI Client to the server
     * @param flow for visualising network error messages
     */
    public ClientRMI(Flow flow) {
        super();
        gameListenersHandler = new GameListenersClient(flow);
        connect();
        this.flow = flow;
        pingSender = new PingSender(flow, this);
        pingSender.start();

    }

    /**
     * Connect the client to the RMI server using the Default Port and the Default IP
     */
    public void connect() {
        boolean retry = false;
        int attempt = 1;
        int i;
        do{
            try {
                registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
                gameController = (GameControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
                modelInvokedEvents = (GameListenerInterface) UnicastRemoteObject.exportObject(gameListenersHandler, 0);

                printAsync("Client RMI ready");
                retry = false;

            } catch (Exception e) {
                if (!retry) {
                    printAsync("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + DefaultValue.Default_port_RMI + "' with name: '" + DefaultValue.Default_servername_RMI + "'");

                i = 0;
                while (i < DefaultValue.secondsToReconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    printAsyncNoLine(".");
                    i++;
                }
                printAsyncNoLine("\n");

                if (attempt >= DefaultValue.maxAttemptsBeforeGiveUp) {
                    printAsyncNoLine("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }
        } while (retry) ;

    }


    /**
     * Comunicate to server the chosen initial card
     * @param index
     * @param nickname
     * @throws IOException
     */
    @Override
    public void setInitialCard(int index, String nickname) throws IOException {
        try {
            gameController.setInitialCard(nickname, index);
        } catch (RemoteException e) {
            throw new IOException(e);
        }
    }

    /**
     * Sets the goal card for the player with the specified index.
     *
     * @param index The index of the goal card to be set.
     * @param nickname The nickname of the player.
     * @throws IOException If there is an I/O error during communication.
     * @throws NotPlayerTurnException If it's not the player's turn to set the goal card.
     */
    @Override
    public void setGoalCard(int index, String nickname) throws IOException, NotPlayerTurnException {
        try {
            gameController.setGoalCard(nickname, index);
        } catch (RemoteException e) {
            throw new IOException(e);
        }
    }

    /**
     * Places a card in the player's book at the specified position.
     *
     * @param chosenCard  The index of the chosen card to place.
     * @param rowCell     The row position in the book where the card will be placed.
     * @param columnCell  The column position in the book where the card will be placed.
     * @throws IOException If there is an I/O error during communication.
     */
    @Override
    public void placeCardInBook(int chosenCard, int rowCell, int columnCell) throws IOException {
        try {
            gameController.placeCardInBook(nickname, chosenCard, rowCell, columnCell);
        } catch (RemoteException e) {
            throw new IOException(e);
        }
    }

    /**
     * Sets up the game with the specified number of players, game ID, and player's nickname.
     *
     * @param numPlayers The number of players in the game.
     * @param GameID The ID of the game.
     * @param nick The nickname of the player setting up the game.
     * @throws IOException If there is an I/O error during communication.
     */
    @Override
    public void settingGame(int numPlayers, int GameID, String nick) throws IOException {
        try {
            gameController.settingGame(modelInvokedEvents, numPlayers, GameID, nick);
        } catch (RemoteException e) {
            throw new IOException(e);
        }
    }

    /**
     * Picks a card from the board for the player.
     *
     * @param cardType     The type of card to pick.
     * @param drawFromDeck Indicates whether the card is drawn from the deck or board.
     * @param pos          The position of the card on the board.
     * @throws IOException If there is an I/O error during communication.
     */
    @Override
    public void PickCardFromBoard(CardType cardType, boolean drawFromDeck, int pos) throws IOException {
        try {
            gameController.PickCardFromBoard(nickname, cardType, drawFromDeck, pos);
        } catch (RemoteException e) {
            throw new IOException(e);
        }
    }


    /**
     * Joins the game with the specified nickname.
     *
     * @param nick The nickname of the player joining the game.
     * @throws IOException        If there is an I/O error during communication.
     * @throws NotBoundException   If the server is not bound or available.
     */
    @Override
    public void joinGame(String nick) throws IOException, NotBoundException {

        try {
            if(!pingSender.isAlive()) {
                pingSender.start();
            }
            System.out.println("Attempting to get the registry...");
            registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
            System.out.println("Registry obtained.");

            System.out.println("Looking up the remote object...");
            gameController = (GameControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);System.out.println("Remote object found.");
            this.nickname = nick;

            System.out.println("Joining the game...");
            gameController.joinGame(modelInvokedEvents, nickname);
            System.out.println("Joined the game successfully.");


        } catch (RemoteException e) {
            System.err.println("RemoteException: " + e.getMessage());
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("NotBoundException: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.err.println("ClassCastException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Client asks the server to reconnect player the player to the game
     *
     * @param nick of the player who is trying to reconnect
     * @throws IOException If there is an I/O error during communication.
     * @throws NotBoundException If the server is not bound or available.
     */
    @Override
    public void reconnect(String nick, int idGame) throws IOException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        gameController = (GameControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);
        gameController.reconnect(modelInvokedEvents, nick);

        nickname = nick;
        if(!pingSender.isAlive()) {
            pingSender.start();
        }
    }


    /**
     * Sets the player identified by the nickname as ready.
     *
     * @param nickname The nickname of the player setting as ready.
     * @throws IOException If there is an I/O error during communication.
     */
    @Override
    public void setAsReady(String nickname) throws IOException {
        try {
            gameController.playerIsReadyToStart(modelInvokedEvents, nickname);
        } catch (RemoteException e) {
            throw new IOException(e);
        }
    }


    /**
     * Sends a ping to the server to check connectivity.
     *
     * @throws RemoteException If there is an error in remote communication.
     */
    @Override
    public void ping() throws RemoteException {
        try {
            if (gameController != null) {
                gameController.ping(nickname, modelInvokedEvents);
            }
        } catch (RemoteException e) {
            flow.noConnectionError();
            printAsync("Connection lost to the server! Impossible to send ping()...");
            if (pingSender != null && pingSender.isAlive()) {
                pingSender.interrupt();
            }
            throw e;
        }

    }

    /**
     * Notifies the server to start the game with the specified nickname.
     *
     * @param nick The nickname of the player requesting to start the game.
     * @throws IOException If there is an I/O error during communication.
     */
    @Override
    public void makeGameStart(String nick) throws IOException {
          gameController.makeGameStart(modelInvokedEvents, nickname);
    }

    /**
     * Leaves the game with the specified nickname.
     *
     * @param nick The nickname of the player leaving the game.
     * @throws IOException      If there is an I/O error during communication.
     * @throws NotBoundException If the server is not bound or available.
     */
   @Override
    public void leave(String nick) throws IOException, NotBoundException {

       registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
       gameController= (GameControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);

       gameController.leave(modelInvokedEvents, nick);
       gameController = null;
       nickname = null;

       if(pingSender.isAlive()) {
           pingSender.interrupt();
       }
    }

    /**
     * Sends a message to the server.
     *
     * @param msg The message to be sent.
     * @throws RemoteException If there is an error in remote communication.
     */
    @Override
    public void sendMessage(Message msg) throws RemoteException {
        System.out.println("ClientRMI sending message from player: " + msg.getSender().getNickname());
        gameController.sentMessage(msg);
    }


}