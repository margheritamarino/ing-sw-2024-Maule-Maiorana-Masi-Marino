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
import static java.rmi.registry.LocateRegistry.getRegistry;

/**
 * RMIServer Class
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game
 * by the RMI Network protocol
 */

// implementa l'Interfaccia GameControllerInterface, contenete i metodi che il Client può invocare sul Server
// Chiamando metodi della GameListenerInterface il Server può notificare al Client degli eventi di gioco (poi il Client reagirà di conseguenza)
public class ServerRMI extends UnicastRemoteObject implements GameControllerInterface { //Capisci se: implements GameControllerInterface
    /**
     * ServerRMI object
     */
     private static ServerRMI serverObject = null;
    /**
     * MainController of all the games
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
            // Assicurati che serverObject sia un singleton
            if (serverObject == null) {
                serverObject = new ServerRMI();
            }

            // Prova a recuperare il registro esistente, se non esiste creane uno nuovo
            try {
                registry = LocateRegistry.getRegistry(DefaultValue.Default_port_RMI);
                registry.list(); // Chiamata per verificare se il registro è effettivamente accessibile
            } catch (RemoteException e) {
                registry = LocateRegistry.createRegistry(DefaultValue.Default_port_RMI);
            }

            // Registra l'oggetto remoto nel registro
            getRegistry().rebind(DefaultValue.Default_servername_RMI, serverObject);

            System.out.println("RMI server started on port " + DefaultValue.Default_port_RMI + ".");
            printAsync("Server RMI ready");

        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
            throw e; // Rilancia l'eccezione per assicurare che il chiamante sia a conoscenza del fallimento
        }
        return getInstance();
    }



    /**
     * @return the istance of the RMI Server
     */
    public synchronized static ServerRMI getInstance() throws RemoteException {
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

    @Override
    public void joinGame(GameListenerInterface lis, String nick) throws RemoteException {
        System.out.println("Sono in joinGame di ServerRMI");
        serverObject.gameController.joinGame(lis, nick); //TRY
        //GameController.getInstance().joinGame(lis, nick);
        //UnicastRemoteObject.exportObject(gameController, 0); //controlla

        printAsync("[RMI] " + nick + " joined to game");

    }

    @Override
    public boolean playerIsReadyToStart(String p) throws RemoteException {
        return serverObject.gameController.playerIsReadyToStart(p);
    }

    @Override
    public boolean isThisMyTurn(String nick) throws RemoteException {
        return serverObject.gameController.isThisMyTurn(nick);
    }

    @Override
    public void disconnectPlayer(String nick, GameListenerInterface listener) throws RemoteException {
        serverObject.gameController.disconnectPlayer(nick, listener);
    }

    @Override
    public int getGameId() throws RemoteException {
        return serverObject.gameController.getGameId();
    }

    @Override
    public void ping(String nickname, GameListenerInterface me) throws RemoteException {
        serverObject.gameController.ping(nickname, me);
    }

    @Override
    public void leave(GameListenerInterface lis, String nick) throws RemoteException {
        serverObject.gameController.leave(lis, nick);
    }

    @Override
    public void setInitialCard(String nickname, int index) throws RemoteException {
        serverObject.gameController.setInitialCard(nickname,index);
    }

    @Override
    public void setGoalCard(String nickname, int index) throws NotPlayerTurnException, RemoteException {
        serverObject.gameController.setGoalCard(nickname,index);
    }

    @Override
    public void placeCardInBook(String nickname, int chosenCard, int rowCell, int columnCell) throws RemoteException {
        serverObject.gameController.placeCardInBook(nickname, chosenCard, rowCell, columnCell);
    }



    @Override
    public void PickCardFromBoard(String nickname, CardType cardType, boolean drawFromDeck, int pos) throws RemoteException {
        serverObject.gameController.PickCardFromBoard(nickname, cardType, drawFromDeck, pos);
    }

    @Override
    public void settingGame(GameListenerInterface lis, int numPlayers, int gameID, String nickname) throws RemoteException {

    }

    /*@Override
    public void createGame(GameListenerInterface lis, int numPlayers, int GameID, String nick) throws RemoteException{
        serverObject.gameController.createGame(lis, numPlayers,GameID, nick);
    }*/
}

