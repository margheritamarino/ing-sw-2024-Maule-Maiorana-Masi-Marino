package it.polimi.ingsw.network.socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.network.socket.Messages.clientToServerMessages.ClientGenericMessage;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
import it.polimi.ingsw.controller.GameController;

/**
 * ClientHandler Class<br>
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game<br>
 * by the Socket Network protocol
 */
public class ClientHandler extends Thread{

    /**
     * Socket associated with the Client
     */
    private final Socket clientSocket;

    /**
     * ObjectInputStream in
     */
    private final ObjectInputStream in;

    /**
     * ObjectOutputStream out
     */
    private final ObjectOutputStream out;

    /**
     * The GameListener of the ClientSocket for notifications
     */
    private GameListenersServer gameListenersServer;

    /**
     * Blocking queue to elaborate messages from the client
     */
    private final BlockingQueue<ClientGenericMessage> processingQueue = new LinkedBlockingQueue<>();

    /**
     * Handle all the network requests performed by a specific ClientSocket
     * Initialize the INPUT Stream and the OUTPUT Stream with the specific Client
     * @param soc the socket to the client
     * @throws IOException error with Input/Output operations
     */
    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
        this.gameListenersServer = new GameListenersServer(out);

    }

    /**
     * Stop the thread
     */
    public void interruptThread() {
        this.interrupt();
    }


    /**
     * This method runs in a separate thread and handles incoming messages,
     * including ping messages, which are treated as special cases.
     * If a ping message is received, it is handled by the game controller.
     * All other messages are added to a processing queue for further handling.
     */
    @Override
    public void run() {
        var th = new Thread(this::runGameLogic);
        th.start();

        try {
            ClientGenericMessage temp; //preparing to manage any message received from Client during Thread
            while (!this.isInterrupted()) {
                try {
                    temp = (ClientGenericMessage) in.readObject(); //to read messages sent from the client

                    //if it's a ping message I handle it as a "special message"
                    if (temp.isPing()) {
                        if (GameController.getInstance() != null) {
                            GameController.getInstance().ping(temp.getNickname(), gameListenersServer);
                        }
                    } else {
                        processingQueue.add(temp);
                    }

                } catch (IOException | ClassNotFoundException e) {
                    printAsync("ClientSocket dies because cannot communicate no more with the client");
                    return;
                }
            }
        } finally {
            th.interrupt();
        }
    }

    private void runGameLogic() {
        ClientGenericMessage temp;

        try {
            while (!this.isInterrupted()) {
                temp = processingQueue.take();

                if(temp.isJoinGame()){
                    temp.execute(gameListenersServer, GameController.getInstance());
                } else temp.execute(GameController.getInstance());
            }
        } catch (RemoteException | GameEndedException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException ignored) {}
    }

}
