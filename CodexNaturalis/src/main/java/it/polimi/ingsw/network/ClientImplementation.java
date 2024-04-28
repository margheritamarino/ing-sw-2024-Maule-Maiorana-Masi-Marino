package it.polimi.ingsw.network;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.exceptions.SingletonException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The ClientImplementation class is an implementation of the {@link Client} interface.
 * It extends the {@link UnicastRemoteObject} class and provides methods for updating the client with a {@link Message}.
 */
public class ClientImplementation extends UnicastRemoteObject implements Client {
    private static ClientImplementation instance;

    /**
     * Logger used to log Client actions.
     */
    public static final Logger logger = Logger.getLogger(ClientImplementation.class.getName());

    /**
     * Reference to the view.
     */
    private final View view;

    /**
     * Timer to
     */
    private Timer timer;

    /**
     * Reference to the server to send messages to.
     */
    private Server server;



    /**
     * The main entry point of the client application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    }



    /**
     * Updates the client with the specified {@code Message}.
     *
     * @param m the {@link Message} to update the client with
     * @throws RemoteException if a remote communication error occurs
     */

    @Override
    public void update(Message m) throws RemoteException {
    }
}


