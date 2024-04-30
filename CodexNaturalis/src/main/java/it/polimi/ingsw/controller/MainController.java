package it.polimi.ingsw.controller;

//funzionalità aggiuntive: gestione di più game in contemporanea

import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.rmi.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MainController implements MainControllerInterface, Serializable {

    //Singleton
    /**
     * Singleton Pattern, instance of the class
     */
    private static MainController instance = null;

    /**
     * List of running games
     * For implementing AF: "Multiple games"
     */
    private List<GameController> runningGames;


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
