package it.polimi.ingsw.network.socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.socket.client.MessageClientToServer;

import static it.polimi.ingsw.network.PrintAsync.printAsync;

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
    private ObjectInputStream in;
    /**
     * ObjectOutputStream out
     */
    private ObjectOutputStream out;


    /**
     * GameController associated with the game
     */
    private GameControllerInterface gameController;

    /**
     * The GameListener of the ClientSocket for notifications
     */
    private GameListenersHandlerSocket gameListenersHandlerSocket;

    /**
     * Nickname of the SocketClient
     */
    private String nickname = null;

    private final BlockingQueue<MessageClientToServer> processingQueue = new LinkedBlockingQueue<>();

    /**
     * Handle all the network requests performed by a specific ClientSocket
     *
     * @param soc the socket to the client
     * @throws IOException
     */
    public ClientHandler(Socket soc) throws IOException {
        this.clientSocket = soc;
        this.in = new ObjectInputStream(soc.getInputStream());
        this.out = new ObjectOutputStream(soc.getOutputStream());
        gameListenersHandlerSocket = new GameListenersHandlerSocket(out);
    }

    /**
     * Stop the thread
     */
    public void interruptThread() {
        this.interrupt();
    }


    /**
     * Receive all the actions sent by the player, execute them on the specific controller required
     * It detects client network disconnections by catching Exceptions
     * {@link MainController} or {@link GameControllerInterface}
     */

    @Override
    public void run() {
        var th = new Thread(this::runGameLogic);
        th.start();

        try {
            MessageClientToServer temp;
            while (!this.isInterrupted()) {
                try {
                    temp = (MessageClientToServer) in.readObject();

                    try {
                        //it's a heartbeat message I handle it as a "special message"
                        if (temp.isHeartbeat() && !temp.isMessageForMainController()) {
                            if (gameController != null) {
                                gameController.heartbeat(temp.getNickname(), gameListenersHandlerSocket);
                            }
                        } else {
                            processingQueue.add(temp);
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
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

    //gestione funzionalità aggiuntiva
    private void runGameLogic() {
        MessageClientToServer temp;

        try {
            while (!this.isInterrupted()) {
                temp = processingQueue.take();

                if (temp.isMessageForMainController()) {
                    gameController = temp.execute(gameListenersHandlerSocket, MainController.getInstance());
                    nickname = gameController != null ? temp.getNickname() : null;

                } else if (!temp.isHeartbeat()) {
                    temp.execute(gameController);
                }
            }
        } catch (RemoteException | GameEndedException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException ignored) {}
    }



}
