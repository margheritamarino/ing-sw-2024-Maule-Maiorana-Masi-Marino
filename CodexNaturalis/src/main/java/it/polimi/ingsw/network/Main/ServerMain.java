package it.polimi.ingsw.network.Main;

//import it.polimi.ingsw.network.rmi.ServerRMI;
import it.polimi.ingsw.network.socket.server.ServerTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerMain {

    /**
     * The name of the command to type in the console to stop the server.
     */
    private static final String SHUTDOWN_COMMAND = "exit";
    //private ServerRMI rmiServer;
    private ServerTCP socketServer;

    /**
     * Creates a new server.
     * It initializes both the RMI and the socket servers.
     */
    public ServerMain() throws IOException {
        //this.rmiServer = new ServerRMI();
        this.socketServer = new ServerTCP();
    }

    public static void main(String[] args) throws IOException {
        ServerMain server = new ServerMain();

        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Unable to start the server.");
        }

        // Handle server shutdown
        new Thread(() -> {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line;

            System.out.println("Type " +  SHUTDOWN_COMMAND + " to stop the server.");

            while (true) {
                try {
                    line = in.readLine();
                    if (line.equals(SHUTDOWN_COMMAND)) {
                        server.stop();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Starts both the RMI and the socket servers.
     * It also sends a message to all clients to notify them that the server is up.
     */

    public void start() {
       // rmiServer.start();
        socketServer.start();
        System.out.println("Server started.");

    }

    /**
     * Stops both the RMI and the socket servers.
     * It also sends a message to all clients to notify them that the server is shutting down.
     */

    public void stop() {
        //rmiServer.stop();
        socketServer.stopConnection();
        System.exit(0);
    }
}
