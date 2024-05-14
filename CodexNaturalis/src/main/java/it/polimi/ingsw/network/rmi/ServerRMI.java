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


public class ServerRMI extends UnicastRemoteObject implements GameControllerInterface { //Capisci se: implements GameControllerInterface
    /**
     * ServerRMI object
     */
    private static ServerRMI serverObject = null;

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
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(DefaultValue.Default_port_RMI); //crea il registro
            getRegistry().rebind(DefaultValue.Default_servername_RMI, serverObject); // Registra l'oggetto remoto nel registro
            System.out.println("RMI server started on port " + DefaultValue.Default_port_RMI + ".");
            printAsync("Server RMI ready");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
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
            e.printStackTrace();
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
        } catch (NotBoundException e) {
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getInstance();
    }


    @Override
    public boolean playerIsReadyToStart(String p) throws RemoteException {
        return false;
    }

    @Override
    public boolean isThisMyTurn(String nick) throws RemoteException {
        return false;
    }

    @Override
    public void disconnectPlayer(String nick, GameListenerInterface listener) throws RemoteException {

    }

    @Override
    public int getGameId() throws RemoteException {
        return 0;
    }

    @Override
    public void ping(String nickname, GameListenerInterface me) throws RemoteException {

    }

    @Override
    public void leave(GameListenerInterface lis, String nick) throws RemoteException {

    }

    @Override
    public void setInitialCard(String nickname, int index) throws RemoteException {

    }

    @Override
    public void setGoalCard(String nickname, int index) throws RemoteException, NotPlayerTurnException {

    }

    @Override
    public void placeCardInBook(String nickname, int chosenCard, int rowCell, int columnCell) throws RemoteException{

    }

    @Override
    public void joinGame(GameListenerInterface lis, String nick) throws RemoteException {

    }

    @Override
    public void PickCardFromBoard(String nickname, CardType cardType, boolean drawFromDeck, int pos) throws RemoteException {

    }
}

