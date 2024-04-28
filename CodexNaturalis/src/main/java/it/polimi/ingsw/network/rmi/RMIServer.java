package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.listener.GameListener;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.network.PrintAsync.printAsync;

/**
 * RMIServer Class
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game
 * by the RMI Network protocol
 */


public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {
    public final static int Default_port_RMI = 1234;
    public final static String Default_servername_RMI = "CodexNaturalis";
    /**
     * MainController of all the games     */
    private final MainControllerInterface mainController;//è un'istanza di una classe che implementa l'interfaccia MainControllerInterface.
                                                        // cioè istanza  di MainController
    /**
     * RMIServer object
     */
    private static RMIServer serverObject = null;

    /**
     * Registry associated with the RMI Server
     */
    private static Registry registry = null;

    /**
     * Constructor that creates a RMI Server
     * @throws RemoteException
     */
    public RMIServer() throws RemoteException {
        super(0);
        mainController = MainController.getInstance();
    }

    /**
     * @return the istance of the RMI Server
     */
    public synchronized static RMIServer getInstance() {
        if(serverObject == null) {
            try {
                serverObject = new RMIServer();
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
     * Create a RMI Server
     * @return the instance of the server
     */
    public static RMIServer bind() {
        try {
            serverObject = new RMIServer();
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(Default_port_RMI);
            getRegistry().rebind(Default_servername_RMI, serverObject);
            printAsync("Server RMI ready");

        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        return getInstance();
    }

    /**
     * A player requested, through the network, to create a new game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the new created game
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {
        GameControllerInterface ris = serverObject.mainController.createGame(lis, nick);
        try {
            UnicastRemoteObject.exportObject(ris, 0);
        } catch (RemoteException e) {
            // Logga l'errore senza terminare il programma
            System.err.println("[ERROR] Failed to export object: " + e.getMessage());
        }

        System.out.println("[RMI] " + nick + " has created a new game");
        return ris;
    }


    /**
     * A player requested, through the network, to join a random game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the first available game
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        //Return the GameController already existed => not necessary to re-Export Object
        GameControllerInterface ris = serverObject.mainController.joinFirstAvailableGame(lis, nick); //chiamata al metodo del controller
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            }catch (RemoteException e){
                //Already exported, due to another RMI Client running on the same machine
            }
            printAsync("[RMI] " + nick + " joined in first available game");
        }
        return ris;
    }

    /**
     * A player requested, through the network, to join a specific game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @return GameControllerInterface of the specific game
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        GameControllerInterface ris = serverObject.mainController.joinGame(lis, nick, idGame);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            }catch (RemoteException e){
                //Already exported, due to another RMI Client running on the same machine
            };
            printAsync("[RMI] " + nick + " joined to specific game with id: " + idGame);
        }
        return ris;
    }

    @Override
    public GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        //FUNZIONALITA AGGIUNTIVA
        return null;
    }

    /**
     * A player requested, through the network, to leave a game
     *
     * @param lis GameListener of the player
     * @param nick of the player
     * @param idGame of the game to leave
     * @return GameControllerInterface of the game
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        serverObject.mainController.leaveGame(lis,nick,idGame); //chiamata al metodo leaveGame di MainContoller (il controller
        return null;
    }
}

