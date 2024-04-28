package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.listener.GameListener;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMIServer Class<
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game
 * by the RMI Network protocol
 */
public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {


    /**
     * MainController of all the games     */
    private final MainControllerInterface mainController;
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


    @Override
    public GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }
}
