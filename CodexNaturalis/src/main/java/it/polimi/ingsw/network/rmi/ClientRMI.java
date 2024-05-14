package it.polimi.ingsw.network.rmi;


//import it.polimi.ingsw.model.Chat.Message; (CHAT)
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.CardType;
//import it.polimi.ingsw.network.HeartbeatSender;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.socket.Messages.clientToServerMessages.*;
import it.polimi.ingsw.network.socket.Messages.serverToClientMessages.ServerGenericMessage;
import it.polimi.ingsw.model.DefaultValue;

import it.polimi.ingsw.network.socket.client.GameListenersClient;
import it.polimi.ingsw.view.flow.Flow;
import java.io.*;
import java.net.Socket;
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
 * by the RMI Network Protocol
 */

public class ClientRMI implements ClientInterface {

    /**
     * The remote object returned by the registry that represents the game controller
     */
    private static GameListenerInterface gameController;

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

    // private HeartbeatSender rmiHeartbeat;


    /**
     * Create, start and connect a RMI Client to the server
     * @param flow for visualising network error messages
     */
    public ClientRMI(Flow flow) {
        super();
        gameListenersHandler = new GameListenersClient(flow);
        connect();

        this.flow = flow;

        //  rmiHeartbeat = new HeartbeatSender(flow,this);
        //  rmiHeartbeat.start();
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
                registry.lookup(DefaultValue.Default_servername_RMI);
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

                if (attempt >= DefaultValue.maxAttemptsBeforeGiveUp) { //se supera il numero massimo di tentativi di connessione al server senza riuscire
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


    @Override
    public void setInitialCard(int index) throws IOException {
        GameController.getInstance().setInitialCard(nickname, index);
    }

    @Override
    public void setGoalCard(int index) throws IOException, NotPlayerTurnException {
        GameController.getInstance().setGoalCard(nickname, index);
    }

    @Override
    public void placeCardInBook(int chosenCard, int rowCell, int columnCell) throws IOException {
        GameController.getInstance().placeCardInBook(nickname, chosenCard, rowCell, columnCell );
    }

    @Override
    public void PickCardFromBoard(CardType cardType, boolean drawFromDeck, int pos) throws IOException {
        GameController.getInstance().PickCardFromBoard(nickname, cardType, drawFromDeck, pos);
    }


    /**
     * Ask the Socket Server to join a specific game
     *
     * @param nick of the player
     * @throws IOException
     */
    @Override
    public void joinGame(String nick) throws IOException, NotBoundException {
        System.out.println("Into Client_RMI joinGame");
        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
        registry.lookup(DefaultValue.Default_servername_RMI);
        GameController.getInstance().joinGame(modelInvokedEvents, nick);

    }


    /**
     * Ask the Socket Server to set the player as ready
     * @throws IOException
     */
    @Override
    public void setAsReady() throws IOException {
        if ( GameController.getInstance() != null) {
            GameController.getInstance().playerIsReadyToStart(nickname);
        }
    }

    //TODO
    @Override
    public void ping() {

    }

    /**
     * Ask the Socket Server to leave a specific game
     * @param nick of the player
     * @throws IOException
     */
    //TODO: Method LEAVE for RMI Disconnection
   @Override
    public void leave(String nick) throws IOException, NotBoundException {
//        registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
//        registry.lookup(DefaultValue.Default_servername_RMI);
//
//        GameController.getInstance().leaveGame(modelInvokedEvents, nick, idGame);
//        gameController = null;
//        nickname = null;
    }




    /**
     * Send a heartbeat to the Socket Server
     * Now it is not used because the Socket Connection automatically detects disconnections by itself
     */

    //TODO: HEARTBEAT METHOD
    /*@Override
    public void heartbeat() {

        //TODO
        if (GameController.getInstance() != null) {
            GameController.getInstance().heartbeat(nickname, modelInvokedEvents);
        }
    }*/





}