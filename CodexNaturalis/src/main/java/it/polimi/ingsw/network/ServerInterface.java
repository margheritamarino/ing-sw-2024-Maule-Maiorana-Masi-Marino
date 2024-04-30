package it.polimi.ingsw.network;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Defines the methods that a server must implement independently of the
 * communication protocol used.
 */
public interface ServerInterface {
    /**
     * Starts the server.
     *
     * @throws RemoteException       if a connection error occurs
     * @throws MalformedURLException if the url of the server is not valid
     */
    void start() throws RemoteException, MalformedURLException;

    /**
     * Stops the server.
     *
     * @throws RemoteException   if a connection error occurs
     * @throws NotBoundException if the server is not bound
     */
    void stop() throws RemoteException, NotBoundException;
}
