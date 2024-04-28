package it.polimi.ingsw.controller;

import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.rmi.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * MainController Class
 * Is the Controller of the controllers, it manages all the available games that are running {@link GameController}<br>
 * Allowing players to create, join, reconnect, leave and delete games
 *
 * Therefore, the MainController is unique across the app and thus implements the Singleton Pattern
 */
public class MainController implements MainControllerInterface, Serializable {
    private static MainController instance = null;
    /**
     * List of running games
     * For implementing AF: "Multiple games"     */
    private final List<GameController> runningGames;


    /**
     * Init an empty List of GameController
     * For implementing AF: "Multiple games"
     */
    private MainController() {
        runningGames = new ArrayList<>();
    }

    /**
     * Singleton Pattern
     *
     * @return the only one instance of the MainController class
     */
    public synchronized static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    @Override
    public GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {
        //IMPLEMENTA
        return null;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        //IMPLEMENTA
        return null;
    }

    @Override
    public GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        //IMPLEMENTA
        return null;
    }

    @Override
    public GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        //FUNZIONALITA AGGIUNTIVA
        return null;
    }

    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        //IMPLEMENTA
        return null;
    }
}
