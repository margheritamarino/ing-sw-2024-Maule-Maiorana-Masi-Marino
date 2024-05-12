package it.polimi.ingsw.network.socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.socket.Messages.clientToServerMessages.ClientGenericMessage;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
import it.polimi.ingsw.controller.GameController;
/**
 * ClientHandler Class<br>
 * Handle all the incoming network requests that clients can require to create,join,leave or reconnect to a game<br>
 * by the Socket Network protocol
 */
public class ClientHandler extends Thread{

    private final Socket clientSocket; //socket associato a un Client

    private final ObjectInputStream in; //per la lettura degli oggetti in INGRESSO dal CLient
    private final ObjectOutputStream out; //per l'INVIO di oggetti al Client

    //private GameControllerInterface gameController; //controller associato alla partita

    private GameListenersServer gameListenersServer;
    /**
     * The GameListener of the ClientSocket for notifications
     */
    private GameListenersServer gameListenersHandlerSocket;//per inviare NOTIFICHE al Client
    private String nickname = null; //soprannome (nickname) del SocketClient

    private final BlockingQueue<ClientGenericMessage> processingQueue = new LinkedBlockingQueue<>(); //coda bloccante per elaborare i messaggi in arrivo dal client.

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
        gameListenersServer = new GameListenersServer(out);
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
     */

    @Override
    public void run() {
        var th = new Thread(this::runGameLogic); //avvio thread
        th.start();

        try {
            ClientGenericMessage temp; //si prepara a gestire qualsiasi messaggio che gli arriva dal Client(sottoclasse di ClientGenericMessage) durante il Thread
            while (!this.isInterrupted()) {
                try {
                    temp = (ClientGenericMessage) in.readObject(); //legge msg in arrivo dal Client

                    try {
                        //it's a heartbeat message I handle it as a "special message"
                        if (temp.isHeartbeat()) {
                            if (gameController != null) {
                                gameController.heartbeat(temp.getNickname(), gameListenersServer);
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

    private void runGameLogic() {
        ClientGenericMessage temp;

        try {
            while (!this.isInterrupted()) {
                System.out.println("Sono nel SERVER \n Sono dentro al metodo GameLogic di Client Handler");
                temp = processingQueue.take();

                if(temp.isJoinGame()){
                    temp.execute(gameListenersHandlerSocket, GameController.getInstance());
                } else temp.execute(GameController.getInstance());

            }
        } catch (RemoteException | GameEndedException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException ignored) {}
    }



}
