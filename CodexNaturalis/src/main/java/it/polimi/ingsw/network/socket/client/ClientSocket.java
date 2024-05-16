package it.polimi.ingsw.network.socket.client;

//import it.polimi.ingsw.model.Chat.Message; (CHAT)
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
//import it.polimi.ingsw.network.HeartbeatSender;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.PingSender;
import it.polimi.ingsw.network.socket.Messages.clientToServerMessages.*;
import it.polimi.ingsw.network.socket.Messages.serverToClientMessages.ServerGenericMessage;
import it.polimi.ingsw.model.DefaultValue;

import it.polimi.ingsw.view.flow.Flow;
import java.io.*;
import java.net.Socket;

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
        pingSender = new PingSender(this.flow, this);
        this.start();

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
        } while  (!connectionEstablished && attempt <= DefaultValue.maxAttemptsBeforeGiveUp);

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
       /* if(pingSender.isAlive()) {
            pingSender.interrupt();
        }*/
    }

    @Override
    public void settingGame(int numPlayers, int GameID) throws IOException {
        out.writeObject(new ClientMsgCreateGame( numPlayers, GameID, nickname));
        finishSending();
    }

    @Override
    public void setInitialCard(int index) throws IOException {
        out.writeObject(new ClientMsgSetInitial(nickname, index));
        finishSending();
    }

    @Override
    public void setGoalCard(int index) throws IOException {
        out.writeObject(new ClientMsgSetObjective(nickname, index));
        finishSending();
    }

    @Override
    public void placeCardInBook(int chosenCard, int rowCell, int columnCell) throws IOException {
        out.writeObject(new ClientMsgPlaceCard(nickname, chosenCard, rowCell, columnCell));
        finishSending();
    }

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
       /* if(!pingSender.isAlive()) {
            pingSender.start();
        }*/
    }


    /**
     * Ask the Socket Server to set the player as ready
     * @throws IOException
     */
    @Override
    public void setAsReady() throws IOException {
        System.out.println("sono in ClientSocket--> setAsReady ");
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



    @Override
    public void ping()  {

        if (out != null) {
            try {
               out.writeObject(new ClientMsgPing(nickname));
                finishSending();
            } catch (IOException e) {
                printAsync("Connection lost to the server! Impossible to send ping()...");
            }
        }
    }

    /**
     * Makes sure the message has been sent
     * @throws IOException
     */
    private void finishSending() throws IOException {
        out.flush();
        out.reset();
    }


}
