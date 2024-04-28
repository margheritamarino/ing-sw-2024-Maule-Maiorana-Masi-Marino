package it.polimi.ingsw.network;

import it.polimi.ingsw.Messages.Message;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * The {@code ServerImplementation} class represents the server implementation for the game. It handles the communication
 * with clients using both RMI (Remote Method Invocation) and socket protocols.
 * <p>
 * The class implements the {@link Server} interface, which defines the communication methods that clients can invoke.
 * It extends the {@link UnicastRemoteObject} class to enable remote method invocation (RMI) functionality.
 * <p>
 * The {@code ServerImplementation} class maintains a list of connected players (`playingUsernames`) and a map of disconnected
 * players (`disconnectedUsernames`). It also keeps a queue of lobbies waiting to start (`lobbiesWaitingToStart`).
 * <p>
 * The class provides methods to handle incoming messages from clients. The `handleMessage` method processes register
 * messages to connect clients to their respective `GameServer` instances and reconnect messages to reconnect clients
 * to their previous {@link GameServer} instances.
 * <p>
 * The class also provides methods to delete a game, disconnect a player, kick a player from a lobby, and handle player
 * reconnection. These methods ensure the appropriate management of players and game instances.
 * <p>
 * The {@code ServerImplementation} class supports both RMI and socket communication protocols. The `startRMI` method starts
 * the RMI server, while the `startSocket` method starts the socket server. Incoming socket connections are handled
 * by the {@link ClientSkeleton} class.
 * <p>
 * The class includes a singleton pattern to ensure that only one instance of the server exists. The `getInstance`
 * method returns the singleton instance of the server.
 * <p>
 * The `main` method starts the server by creating the singleton instance and starting the RMI and socket server threads.
 * It also sets up the logger to log server activities.
 * <p>
 * Note: The {@code ServerImplementation} class assumes that the game logic and state are managed by the {@link GameServer} class,
 * which is responsible for handling the game lobby and the game itself.
 */
public class ServerImplementation extends UnicastRemoteObject implements Server{

    private static ServerImplementation instance;

    /**
     * Logger used to log Server actions.
     */
    public static final Logger logger = Logger.getLogger("ServerImplementation");

    /**
     * ExecutorService for parallel programming.
     */
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * List containing the active players' usernames (not disconnected)
     */
    private final List<String> playingUsernames;

    /**
     * For each disconnected player's username, stores the GameServer of the game that they were participating in.
     */
    private final Map<String, GameServer> disconnectedUsernames;

    /**
     * Queue of opened lobbies. Players who join an existing lobby will be added to the oldest one.
     */
    private final Queue<GameServer> lobbiesWaitingToStart;

    /**
     * Queue storing the messages until they're effectively handled.
     */
    private final Queue<Tuple<Message, Client>> receivedMessages = new LinkedList<>();

    /**
     * Returns the singleton instance of the server. If the instance does not exist, it creates a new one.
     *
     * @throws RemoteException if a Remote Exception occurred.
     * @return the singleton instance of the {@code ServerImplementation}.
     */
    public static ServerImplementation getInstance() throws RemoteException{
        if( instance == null ) {
            instance = new ServerImplementation();
        }
        return instance;
    }

    public static void main(String[] args) {

    }


    /**
     * Starts the RMI server by creating the RMI registry and binding the server instance to a name in the registry.
     *
     * @throws RemoteException if there is an error in remote method invocation
     */
    private static void startRMI () throws RemoteException {
        LocateRegistry.createRegistry(Config.getInstance().getRmiPort());
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("G26-MyShelfie-Server", getInstance());
    }


    /**
     * Handles the incoming {@code Message} from a {@code Client}. It adds the message to the queue of messages to be processed.
     *
     * @param m      the incoming {@link Message}
     * @param client the {@link Client} object associated with the message
     * @throws RemoteException if there is an error in remote method invocation
     */
    public void handleMessage (Message m, Client client) throws RemoteException {

    }
}



}
