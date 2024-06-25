package it.polimi.ingsw.network.socket.client;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.PingSender;
import it.polimi.ingsw.network.socket.Messages.clientToServerMessages.*;
import it.polimi.ingsw.network.socket.Messages.serverToClientMessages.ServerGenericMessage;
import it.polimi.ingsw.model.DefaultValue;

import it.polimi.ingsw.view.flow.Flow;
import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
import static it.polimi.ingsw.view.TUI.PrintAsync.printAsyncNoLine;

//All CLIENT'S ACTIONS
/**
 * ClientSocket Class<br>
 * Handle all the network communications between ClientSocket and ClientHandler<br>
 * From the first connection, to the creation, joining, leaving, grabbing and positioning messages through the network<br>
 * by the Socket Network Protocol
 */
public class ClientSocket extends Thread implements ClientInterface {
    /**
     * Socket that represents the Client
     */
    private Socket clientSocket;
    /**
     * ObjectOutputStream out
     */
    private ObjectOutputStream out;
    /**
     * ObjectInputStream in
     */
    private ObjectInputStream in;


    /**
     * GameListener on which to perform all actions requested by the Socket Server
     */
    private final GameListenersClient modelInvokedEvents;
    /**
     * The nickname associated with the ClientSocket communication
     */
    private String nickname;

    private final PingSender pingSender;
    private final Flow flow;

    /**
     * Create a Client Socket
     *
     * @param flow to notify network errors
     */
    public ClientSocket(Flow flow) {
        this.flow=flow;
        startConnection(DefaultValue.serverIp, DefaultValue.Default_port_Socket);
        modelInvokedEvents = new GameListenersClient(flow);
        this.start();
        pingSender = new PingSender(this.flow, this);


    }

    /**
     * Reads all the incoming network traffic and execute the requested action
     */
    public void run() {
        while (true) {
            try {
                ServerGenericMessage msg = (ServerGenericMessage) in.readObject();
                msg.execute(modelInvokedEvents);

            } catch (IOException | ClassNotFoundException | InterruptedException | FileReadException e) {
                printAsync("[ERROR] Connection to server lost! " + e);
                try {
                    System.in.read();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(-1);
            }
        }
    }


    /**
     * Start the Connection to the Socket Server
     *
     * @param ip of the Socket server to connect
     * @param port of the Socket server to connect
     */
    private void startConnection(String ip, int port) {
        boolean connectionEstablished = false;
        int attempt = 1;
        int i;

        do {
            try {
                clientSocket = new Socket(ip, port);
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                connectionEstablished = true;

            } catch (IOException e) {
                if (attempt == 1) {
                    printAsync("[ERROR] CONNECTING TO SOCKET SERVER:\t " + e + "\n");
                }
                printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to Socket Server on port: '" + port + "' with ip: '" + ip );

                try {
                    Thread.sleep(DefaultValue.secondsToReconnection * 1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                i = 0;
                while (i < DefaultValue.secondsToReconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    i++;
                }
                printAsyncNoLine("\n");

                if (attempt >= DefaultValue.maxAttemptsBeforeGiveUp) {
                    printAsyncNoLine("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                attempt++;
            }
        } while  (!connectionEstablished);

    }

    /**
     * Close the connection
     *
     * @throws IOException
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
       if(pingSender.isAlive()) {
            pingSender.interrupt();
       }
    }

    /**
     * Sends a message to the server to set up the game with the specified number of players, game ID, and nickname.
     *
     * @param numPlayers The number of players in the game.
     * @param GameID     The ID of the game.
     * @param nick       The nickname of the player setting up the game.
     * @throws IOException If there is an I/O error while sending the message.
     */
    @Override
    public void settingGame(int numPlayers, int GameID, String nick) throws IOException {
        out.writeObject(new ClientMsgCreateGame( numPlayers, GameID, nickname));
        finishSending();
    }
    /**
     * Sends a message to the server to set the initial card for the specified player.
     *
     * @param index    The index of the initial card to be set.
     * @param nickname The nickname of the player.
     * @throws IOException If there is an I/O error while sending the message.
     */
    @Override
    public void setInitialCard(int index, String nickname) throws IOException {
        out.writeObject(new ClientMsgSetInitial(nickname, index));
        finishSending();
    }
    /**
     * Sends a message to the server to set the goal card for the specified player.
     *
     * @param index    The index of the goal card to be set.
     * @param nickname The nickname of the player.
     * @throws IOException If there is an I/O error while sending the message.
     */
    @Override
    public void setGoalCard(int index, String nickname) throws IOException {
        out.writeObject(new ClientMsgSetObjective(nickname, index));
        finishSending();
    }
    /**
     * Sends a message to the server to place a card in the player's book at the specified position.
     *
     * @param chosenCard  The chosen card to be placed.
     * @param rowCell     The row position in the book.
     * @param columnCell  The column position in the book.
     * @throws IOException If there is an I/O error while sending the message.
     */
    @Override
    public void placeCardInBook(int chosenCard, int rowCell, int columnCell) throws IOException {
        out.writeObject(new ClientMsgPlaceCard(nickname, chosenCard, rowCell, columnCell));
        finishSending();
    }
    /**
     * Sends a message to the server to pick a card from the board for the specified player.
     *
     * @param cardType     The type of card to pick.
     * @param drawFromDeck Indicates whether the card is drawn from the deck or board.
     * @param pos          The position of the card on the board.
     * @throws IOException If there is an I/O error while sending the message.
     */
    @Override
    public void PickCardFromBoard(CardType cardType, boolean drawFromDeck, int pos) throws IOException {
        out.writeObject(new ClientMsgPickCard(nickname, cardType, drawFromDeck, pos));
        finishSending();
    }
    /**
     * Ask the Socket Server to join a specific game
     *
     * @param nick of the player
     * @throws IOException
     */
    @Override
    public void joinGame(String nick) throws IOException {
        nickname = nick;
        out.writeObject(new ClientMsgJoinGame(nick));
        finishSending();
       if(!pingSender.isAlive()) {
            pingSender.start();
       }
    }

    /**
     * The client asks the server to reconnect to a specific game
     *
     * @param nick   nickname of the player
     */
    @Override
    public void reconnect(String nick, int idGame) throws IOException {
        System.out.println("ClientSocket- reconnect()\n ");
        nickname = nick;
        out.writeObject(new ClientMsgReconnect(this.nickname));
        finishSending();
        if(pingSender.isAlive()) {
            pingSender.start();
        }

    }
    /**
     * Ask the Socket Server to set the player as ready
     * @throws IOException
     * */
    @Override
    public void setAsReady(String nickname) throws IOException {
        out.writeObject(new ClientMsgSetReady(nickname));
        finishSending();
    }

    /**
     * Ask the Socket Server to leave a specific game
     * @param nick of the player
     * @throws IOException
     */
    @Override
    public void leave(String nick) throws IOException {
        out.writeObject(new ClientMessageLeave(nick));
        finishSending();
        nickname=null;
        if(pingSender.isAlive()) {
            pingSender.interrupt();
        }
    }


    /**
     * Sends a ping message to the server to maintain the connection and check its status.
     * This method is used to verify if the connection to the server is active.
     * If the connection is lost or an I/O error occurs while sending the ping message,
     * an error message is printed asynchronously.
     */
    @Override
    public void ping()  {
        if (out != null) {
            try {
               out.writeObject(new ClientMsgPing(nickname));
                finishSending();
            } catch (IOException e) {
                flow.noConnectionError();
                printAsync("Connection lost to the server! Impossible to send ping()...");
            }
        }
    }


    /**
     * Sends a message to the server requesting to start the game with the specified nickname.
     *
     * @param nick The nickname of the player initiating the game start.
     * @throws IOException If there is an I/O error while sending the message.
     */
   @Override
    public void makeGameStart(String nick) throws IOException {
        this.nickname=nick;
        out.writeObject(new ClientMsgStartGame(nickname));
        finishSending();
    }

    /**
     * Makes sure the message has been sent
     * @throws IOException
     */
    private void finishSending() throws IOException {
        out.flush();
        out.reset();
    }

    /**
     * Sends a chat message from the client to the server.
     *
     * @param msg The message object containing the chat message to be sent.
     * @throws RuntimeException If there is an I/O error while sending the message.
     */
    @Override
    public void sendMessage(Message msg)throws IOException  {
        System.out.println("ClientSocket sending message: " + msg.getText());
        out.writeObject(new ClientMsgNewChatMessage(msg));
        finishSending();
    }


}
