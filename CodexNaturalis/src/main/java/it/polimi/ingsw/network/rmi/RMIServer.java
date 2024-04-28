package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.listener.GameListener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements MainServer{



    @Override
    public GameServer createGame(GameListener lis, String nick) throws RemoteException {
        return null;
    }

    @Override
    public GameServer joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        return null;
    }

    @Override
    public GameServer joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }

    @Override
    public GameServer reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }

    @Override
    public GameServer leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }
}
