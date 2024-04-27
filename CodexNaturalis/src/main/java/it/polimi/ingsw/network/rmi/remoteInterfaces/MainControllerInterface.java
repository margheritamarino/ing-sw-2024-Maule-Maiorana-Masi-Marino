package it.polimi.ingsw.network.rmi.remoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainControllerInterface extends Remote {
    boolean login (String nick)throws RemoteException;

}
