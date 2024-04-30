package it.polimi.ingsw.controller;

import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.rmi.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class MainController implements MainControllerInterface, Serializable {
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
