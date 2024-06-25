package it.polimi.ingsw.network;


import com.sun.javafx.scene.traversal.Direction;
import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.socket.client.ClientSocket;
import it.polimi.ingsw.network.socket.server.ServerTCP;
import it.polimi.ingsw.view.flow.GameFlow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static it.polimi.ingsw.network.ConnectionType.SOCKET;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientSocketTest {

    private static GameFlow gameFlow;
    private static ClientSocket clientSocket;
    private static ServerTCP serverSocket;

    @BeforeEach
    void startServer() throws IOException {
        serverSocket = new ServerTCP();
        serverSocket.start(DefaultValue.Default_port_Socket);
        gameFlow = new GameFlow(SOCKET);
        clientSocket = new ClientSocket(gameFlow);
    }
    @AfterEach
    void tearDown() throws IOException {
        clientSocket.stopConnection();
        serverSocket.stopConnection();
    }

    @Test
    void createGameTest() throws IOException {
        clientSocket.settingGame(2,1,"TestPlayer");
        assertEquals(clientSocket.getState(), Thread.State.RUNNABLE);
    }

    @Test
    void joinGameTest() throws IOException {
        clientSocket.joinGame("TestPlayer");
    }

    @Test
    void ReconnectTest() throws IOException, InterruptedException {
        int id = (int) clientSocket.getId();
        clientSocket.leave("TestPlayer");
        clientSocket.reconnect("TestPlayer", id);
        Assertions.assertEquals(clientSocket.getState(), Thread.State.RUNNABLE);
        assertEquals(clientSocket.getId(), id);
    }



}
