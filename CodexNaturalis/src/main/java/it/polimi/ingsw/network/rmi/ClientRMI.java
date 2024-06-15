package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;

import it.polimi.ingsw.model.Color;
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

//Il Client invoca metodi sul Server tramite l'interfaccia remota GameController

public class ClientRMI implements ClientInterface {

    /**
     * The remote object returned by the registry that represents the main controller
     */
    //private static MainControllerInterface requests; //aggiunto

    /**
     * The remote object returned by the registry that represents the game controller
     */
    private static GameControllerInterface gameController; //INIZIALIZZALO + AGGIUNGI AI METODI DI MAINOìCONTROLLER CHE RITORNINO UN GAMECONTROLLER


    /**
     * The remote object on which the server will invoke remote methods
     */
    private static GameListenerInterface modelInvokedEvents;

    /**
     * The nickname associated to the client (!=null only when connected in a game)
     */
    private String nickname;

    private Color playerColor;

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
    private final PingSender pingSender;


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
        if(!pingSender.isAlive()) {
           pingSender.start();
        }
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
                gameController = (GameControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI); //ClientRMI si connette al server RMI e riceve il riferimento al GameController
                modelInvokedEvents = (GameListenerInterface) UnicastRemoteObject.exportObject(gameListenersHandler, 0); //ClientRMI registra un listener per ricevere aggiornamenti dal ServerRMI: è usato dal Server al Client per notificare gli aggiornamenti del gioco
                                                                                                                             //Il ClientRMI invia comandi al GameController tramite il ServerRMI per eseguire azioni nel gioco.
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

   /* //CAPISCI SE SERVE:
    /**
     * Send pings to the RMI server
     * If sending a message takes more than {@link DefaultValue#timeoutConnection_millis} millis, the client
     * will be considered no longer connected to the server
     */
/*
@SuppressWarnings("BusyWait") //CAPISCI SE LASCIARE
public void run() {
    //For the ping
    while (!Thread.interrupted()) {
        try {
            Timer timer = new Timer();
            TimerTask task = new TaskOnNetworkDisconnection(flow);
            timer.schedule(task, DefaultValue.timeoutConnection_millis);

            //send ping so the server knows I am still online
            ping();

            timer.cancel();
        } catch (RemoteException e) {
            return;
        }
        try {
            Thread.sleep(DefaultValue.secondToWaitToSend_ping);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

*/

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
     * Comunicate to server the chosen objective card
     * @param index
     * @param nickname
     * @throws IOException
     * @throws NotPlayerTurnException
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
     * Ask the server to place a card in client's book
     * @param chosenCard
     * @param rowCell
     * @param columnCell
     * @throws IOException
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
     * Ask the server to set the game
     * @param numPlayers
     * @param GameID
     * @param nick
     * @throws IOException
     */
    @Override
    public void settingGame(int numPlayers, int GameID, String nick) throws IOException {
        System.out.println("ClientRMI- settingGame");
        try {
            gameController.settingGame(modelInvokedEvents, numPlayers, GameID, nick);
        } catch (RemoteException e) {
            throw new IOException(e);
        }
    }

    /**
     * Ask the server to pick a card from board
     * @param cardType
     * @param drawFromDeck
     * @param pos
     * @throws IOException
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
     * Ask the Socket Server to join a specific game
     *
     * @param nick of the player
     * @throws IOException
     */
    @Override
    public void joinGame(String nick) throws IOException, NotBoundException {

        try {
            // Ottieni il registro all'indirizzo IP e porta specificati
            System.out.println("Attempting to get the registry...");
            registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
            System.out.println("Registry obtained.");

            System.out.println("Looking up the remote object...");
            gameController = (GameControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);System.out.println("Remote object found.");
            this.nickname = nick;

            // Unisciti al gioco
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
     * Client asks the Server to set the player as ready
     * @throws IOException
     */
    @Override
    public void setAsReady(String nickname) throws IOException {
        System.out.println("in ClientRMI - setAsReady");
        try {
            gameController.playerIsReadyToStart(modelInvokedEvents, nickname);
        } catch (RemoteException e) {
            throw new IOException(e);
        }
    }


    /**
     * Send a ping to the server
     * @throws RemoteException
     */
    @Override
    public void ping() throws RemoteException {
        if (gameController != null) {
            gameController.ping(nickname, modelInvokedEvents);
        }
    }

    /**
     * Let the game begin
     * @param nick of the player
     * @throws IOException
     */
    @Override
    public void makeGameStart(String nick) throws IOException {
          gameController.makeGameStart(modelInvokedEvents, nickname);
    }

    /**
     * Ask the  Server to leave the game
     * @param nick of the player
     * @throws IOException
     */
   @Override
    public void leave(String nick) throws IOException, NotBoundException {

       registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
       gameController= (GameControllerInterface) registry.lookup(DefaultValue.Default_servername_RMI);

       gameController.leave(modelInvokedEvents, nick);
       gameController = null;
       nickname = null;
    }

    /**
     * The Client wants to send a message to the Server
     * @param msg message to send
     * @throws RemoteException
     */
    @Override
    public void sendMessage(Message msg) throws RemoteException {

        System.out.println("ClientRMI sending message from player: " + msg.getSender().getNickname());
        gameController.sentMessage(msg);
    }


}