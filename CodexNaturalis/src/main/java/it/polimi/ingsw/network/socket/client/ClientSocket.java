package it.polimi.ingsw.network.socket.client;

//import it.polimi.ingsw.model.Chat.Message; (CHAT)
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
//import it.polimi.ingsw.network.HeartbeatSender;
import it.polimi.ingsw.network.ClientInterface;
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
    private Socket clientSoc;
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

   // private final HeartbeatSender socketHeartbeat;
    private Flow flow;

    /**
     * Create a Client Socket
     *
     * @param flow to notify network errors
     */
    public ClientSocket(Flow flow) {
        System.out.println("Sono nel costruttore di ClientSocket");
        this.flow=flow;
        startConnection(DefaultValue.serverIp, DefaultValue.Default_port_Socket);
        modelInvokedEvents = new GameListenersClient(flow);
        this.start();
       // socketHeartbeat = new HeartbeatSender(flow,this);
    }

    /**
     * Reads all the incoming network traffic and execute the requested action
     */
    public void run() {
        System.out.println("Sono nel metodo run della classe ClientSocket");
        while (true) {
            try {
                System.out.println("Sono nel try di ClientSocket");
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
        System.out.println("Sono nel metodo startConnection di ClientSocket");
        boolean retry = false;
        int attempt = 1;
        int i;

        do {
            try {
                clientSoc = new Socket(ip, port);
                System.out.println("Una nuova Socket è stata creata");
                out = new ObjectOutputStream(clientSoc.getOutputStream());
                System.out.println("OutputStream creato");
                in = new ObjectInputStream(clientSoc.getInputStream());
                System.out.println("InputStream creato");
                retry = false;
            } catch (IOException e) {
                if (!retry) {
                    printAsync("[ERROR] CONNECTING TO SOCKET SERVER: \n\t " + e + "\n");
                }
                printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to Socket Server on port: '" + port + "' with ip: '" + ip + "'");

                i = 0;
                while (i < 5) { //second between reconnection
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    printAsyncNoLine(".");
                    i++;
                }
                printAsyncNoLine("\n");

                if (attempt >= 5) { //num_of_attempt_to_connect_toServer_before_giveup
                    printAsyncNoLine("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }
        } while (retry);

    }

    /**
     * Close the connection
     *
     * @throws IOException
     */
    public void stopConnection() throws IOException {
        //TODO
        in.close();
        out.close();
        clientSoc.close();
        /*if(socketHeartbeat.isAlive()) {
            socketHeartbeat.interrupt();
        }*/
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
        System.out.println("clientSocket joinGame");
        nickname = nick;
        //ClientMsgCreateGame prova = new ClientMsgCreateGame(nick);
        //System.out.println("Il messaggio ClientMsgCreateGame è stato creato ");
        out.writeObject(new ClientMsgCreateGame(nick));
        finishSending();
        /*if(!socketHeartbeat.isAlive()) {
            socketHeartbeat.start();
        }*/
    }



    /**
     * Ask the Socket Server to set the player as ready
     * @throws IOException
     */
    @Override
    public void setAsReady() throws IOException {
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
        /*if(socketHeartbeat.isAlive()) {
            socketHeartbeat.interrupt();
        }*/
    }




    /**
     * Send a heartbeat to the Socket Server
     * Now it is not used because the Socket Connection automatically detects disconnections by itself
     */


    @Override
    public void heartbeat() {

        //TODO
        if (out != null) {
            try {
               // out.writeObject(new ClientMsgHeartBeat(nickname));
                finishSending();
            } catch (IOException e) {
                printAsync("Connection lost to the server!! Impossible to send heartbeat...");
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
