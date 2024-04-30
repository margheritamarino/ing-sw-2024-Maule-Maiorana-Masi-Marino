package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.Chat.Message;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.HeartbeatSender;
import it.polimi.ingsw.network.socket.client.GameListenersHandlerClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Flow;

import static it.polimi.ingsw.view.PrintAsync.printAsync;
import static javax.management.remote.JMXConnectorFactory.connect;


// CONTROLLA

/**
 * RMIClient Class <br>
 * Handle all the network communications between RMIClient and RMIServer <br>
 * by the RMI Network Protocol
 */
public class ClientRMI implements ClientInterface {
    private static final int PORT_RMI =1099 ;

    /**
     * The remote object returned by the registry that represents the main controller
     */
    private static MainControllerInterface requests;
    /**
     * The remote object returned by the RMI server that represents the connected game
     */
    private GameControllerInterface gameController = null;
    /**
     * The remote object on which the server will invoke remote methods
     */
    private static GameListener modelInvokedEvents;
    /**
     * The nickname associated to the client (!=null only when connected in a game)
     */
    private String nickname;
    /**
     * The remote object on which the server will invoke remote methods
     */
    private final GameListenersHandlerClient gameListenersHandler;
    /**
     * Registry of the RMI
     */
    private Registry registry;

    /**
     * Flow to notify network error messages
     */
    private Flow flow;

    private HeartbeatSender rmiHeartbeat;


    /**
     * Create, start and connect a RMI Client to the server
     *
     * @param flow for visualising network error messages
     */
    public ClientRMI(Flow flow) {
        super();
        gameListenersHandler = new GameListenersHandlerClient(flow);
        connect();


        this.flow=flow;

        rmiHeartbeat = new HeartbeatSender(flow,this);
        rmiHeartbeat.start();
    }



    public void connect() {
        boolean retry = false;
        int attempt = 1;
        int i;


        try {
            registry = LocateRegistry.getRegistry("127.0.0.1", PORT_RMI);
            requests = (MainControllerInterface) registry.lookup("ServerCommunicationInterface");

            modelInvokedEvents = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);

            printAsync("Client RMI ready");

        } catch (Exception e) {

        }

    }
    @Override
    public void createGame(String nick) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void joinFirstAvailable(String nick) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void reconnect(String nick, int idGame) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {

    }

    @Override
    public void setAsReady() throws IOException {

    }

    @Override
    public boolean isMyTurn() throws RemoteException {
        return false;
    }


    @Override
    public void heartbeat() throws RemoteException {

    }
}
