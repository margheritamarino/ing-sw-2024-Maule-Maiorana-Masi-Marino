package it.polimi.ingsw.network;


/**
 * The {@code GameServer} class represents the server component responsible for managing the game lobby and the game itself.
 * It acts as a lobby before the game starts, allowing players to join and wait for the game to begin. Once the game starts,
 * it serves as the server for the ongoing game, handling player actions and managing the game state.
 * <p>
 * The {@code GameServer} implements the {@link Server} interface, which defines the communication protocol between the server and clients.
 * It extends the `UnicastRemoteObject` class to enable remote method invocation (RMI) functionality.
 * <p>
 * The {@code GameServer} maintains a reference to the {@link Controller} responsible for managing the game logic and state. It also holds
 * references to the {@link ServerImplementation} and acts as a mediator between the game and the server.
 * <p>
 * The {@code GameServer} keeps track of the players in the lobby using the `playingUsernames` list and the disconnected players using
 * the `disconnectedUsernames` list. The `playingUsernames` list contains the usernames of players who are currently connected
 * and actively participating in the game. The `disconnectedUsernames` map stores the usernames of players who were previously
 * connected but got disconnected. The map holds the username as the key and the corresponding {@code GameServer} object as the value,
 * allowing for easy reconnection of players.
 * <p>
 * The {@code GameServer} class provides methods to handle incoming messages from clients. It distinguishes between turn action messages
 * and disconnect messages. For turn action messages, it calls the `doTurn` method to process the player's turn. For disconnect
 * messages, it triggers the `disconnect` method to handle the player's disconnection.
 * <p>
 * The class also provides methods to check if the game has started, delete the game and remove players from the game, reconnect
 * disconnected players, add new players to the lobby, and retrieve the number of active players in the game.
 * <p>
 * Note: The {@code GameServer} class is meant to be used in a distributed environment and supports both RMI and socket communication
 * protocols. It acts as a server for RMI-based clients and uses the {@link it.polimi.ingsw.Network.Middleware.ClientSkeleton} class to handle socket-based clients.
 * The {@link ServerImplementation} class is responsible for managing the server-side logic and communication protocols.
 */
public class GameServer extends UnicastRemoteObject implements Server {
}
