package it.polimi.ingsw;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.game.GameStatus;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.rmi.RemoteException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DisconnectionTest {
    GameController gameController;
    //IntRecord[] matrix2D = new IntRecord[29];
    //IntRecord[] matrix3D = new IntRecord[36];


    //Initialized like this because I don't want to show anything on TUI or GUI
    GameListenerInterface lis1 = new GameListenerInterface() {
        @Override
        public void playerJoined(GameImmutable model, String nickname, Color playerColor) throws RemoteException {

        }

        @Override
        public void requireNumPlayersGameID(GameImmutable model) throws RemoteException {

        }

        @Override
        public void wrongChooseCard(GameImmutable model, String msg) throws RemoteException {

        }

        @Override
        public void pointsAdded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerLeft(GameImmutable model, String nickname) throws RemoteException {

        }

        @Override
        public void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException {

        }

        @Override
        public void joinUnableGameFull(Player triedToJoin, GameImmutable model) throws RemoteException {

        }

        @Override
        public void joinUnableNicknameAlreadyIn(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void AskForReconnection(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void gameStarted(GameImmutable model) throws RemoteException {

        }

        @Override
        public void gameEnded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void requireInitialReady(GameImmutable model, int index) throws IOException, FileReadException {

        }

        @Override
        public void requireGoalsReady(GameImmutable model, int index) throws RemoteException {

        }

        @Override
        public void cardsReady(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardPlaced(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardDrawn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void nextTurn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerDisconnected(GameImmutable model, String nick) throws RemoteException {

        }

        @Override
        public void errorReconnecting(String why) throws RemoteException {

        }

        @Override
        public void lastCircle(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerReady(GameImmutable gameImmutable, String nickname) throws RemoteException {

        }

        @Override
        public void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

        }

        @Override
        public void sentMessage(GameImmutable model, Message msg) throws RemoteException {

        }

    };
    GameListenerInterface lis2 = new GameListenerInterface() {
        @Override
        public void playerJoined(GameImmutable model, String nickname, Color playerColor) throws RemoteException {

        }

        @Override
        public void requireNumPlayersGameID(GameImmutable model) throws RemoteException {

        }

        @Override
        public void wrongChooseCard(GameImmutable model, String msg) throws RemoteException {

        }

        @Override
        public void pointsAdded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerLeft(GameImmutable model, String nickname) throws RemoteException {

        }

        @Override
        public void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException {

        }

        @Override
        public void joinUnableGameFull(Player triedToJoin, GameImmutable model) throws RemoteException {

        }

        @Override
        public void joinUnableNicknameAlreadyIn(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void AskForReconnection(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void gameStarted(GameImmutable model) throws RemoteException {

        }

        @Override
        public void gameEnded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void requireInitialReady(GameImmutable model, int index) throws IOException, FileReadException {

        }

        @Override
        public void requireGoalsReady(GameImmutable model, int index) throws RemoteException {

        }

        @Override
        public void cardsReady(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardPlaced(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardDrawn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void nextTurn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerDisconnected(GameImmutable model, String nick) throws RemoteException {

        }

        @Override
        public void errorReconnecting(String why) throws RemoteException {

        }

        @Override
        public void lastCircle(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerReady(GameImmutable gameImmutable, String nickname) throws RemoteException {

        }

        @Override
        public void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

        }

        @Override
        public void sentMessage(GameImmutable model, Message msg) throws RemoteException {

        }

    };
    GameListenerInterface lis3 = new GameListenerInterface() {
        @Override
        public void playerJoined(GameImmutable model, String nickname, Color playerColor) throws RemoteException {

        }

        @Override
        public void requireNumPlayersGameID(GameImmutable model) throws RemoteException {

        }

        @Override
        public void wrongChooseCard(GameImmutable model, String msg) throws RemoteException {

        }

        @Override
        public void pointsAdded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerLeft(GameImmutable model, String nickname) throws RemoteException {

        }

        @Override
        public void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException {

        }

        @Override
        public void joinUnableGameFull(Player triedToJoin, GameImmutable model) throws RemoteException {

        }

        @Override
        public void joinUnableNicknameAlreadyIn(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void AskForReconnection(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void gameStarted(GameImmutable model) throws RemoteException {

        }

        @Override
        public void gameEnded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void requireInitialReady(GameImmutable model, int index) throws IOException, FileReadException {

        }

        @Override
        public void requireGoalsReady(GameImmutable model, int index) throws RemoteException {

        }

        @Override
        public void cardsReady(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardPlaced(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardDrawn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void nextTurn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerDisconnected(GameImmutable model, String nick) throws RemoteException {

        }

        @Override
        public void errorReconnecting(String why) throws RemoteException {

        }

        @Override
        public void lastCircle(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerReady(GameImmutable gameImmutable, String nickname) throws RemoteException {

        }

        @Override
        public void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

        }

        @Override
        public void sentMessage(GameImmutable model, Message msg) throws RemoteException {

        }

    };

    GameListenerInterface lis4 = new GameListenerInterface() {
        @Override
        public void playerJoined(GameImmutable model, String nickname, Color playerColor) throws RemoteException {

        }

        @Override
        public void requireNumPlayersGameID(GameImmutable model) throws RemoteException {

        }

        @Override
        public void wrongChooseCard(GameImmutable model, String msg) throws RemoteException {

        }

        @Override
        public void pointsAdded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerLeft(GameImmutable model, String nickname) throws RemoteException {

        }

        @Override
        public void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException {

        }

        @Override
        public void joinUnableGameFull(Player triedToJoin, GameImmutable model) throws RemoteException {

        }

        @Override
        public void joinUnableNicknameAlreadyIn(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void AskForReconnection(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void gameStarted(GameImmutable model) throws RemoteException {

        }

        @Override
        public void gameEnded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void requireInitialReady(GameImmutable model, int index) throws IOException, FileReadException {

        }

        @Override
        public void requireGoalsReady(GameImmutable model, int index) throws RemoteException {

        }

        @Override
        public void cardsReady(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardPlaced(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardDrawn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void nextTurn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerDisconnected(GameImmutable model, String nick) throws RemoteException {

        }

        @Override
        public void errorReconnecting(String why) throws RemoteException {

        }

        @Override
        public void lastCircle(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerReady(GameImmutable gameImmutable, String nickname) throws RemoteException {

        }

        @Override
        public void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

        }

        @Override
        public void sentMessage(GameImmutable model, Message msg) throws RemoteException {

        }

    };

    GameListenerInterface lis5 = new GameListenerInterface() {
        @Override
        public void playerJoined(GameImmutable model, String nickname, Color playerColor) throws RemoteException {

        }

        @Override
        public void requireNumPlayersGameID(GameImmutable model) throws RemoteException {

        }

        @Override
        public void wrongChooseCard(GameImmutable model, String msg) throws RemoteException {

        }

        @Override
        public void pointsAdded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerLeft(GameImmutable model, String nickname) throws RemoteException {

        }

        @Override
        public void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException {

        }

        @Override
        public void joinUnableGameFull(Player triedToJoin, GameImmutable model) throws RemoteException {

        }

        @Override
        public void joinUnableNicknameAlreadyIn(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void AskForReconnection(Player triedToJoin, GameImmutable gameModel) throws RemoteException {

        }

        @Override
        public void gameStarted(GameImmutable model) throws RemoteException {

        }

        @Override
        public void gameEnded(GameImmutable model) throws RemoteException {

        }

        @Override
        public void requireInitialReady(GameImmutable model, int index) throws IOException, FileReadException {

        }

        @Override
        public void requireGoalsReady(GameImmutable model, int index) throws RemoteException {

        }

        @Override
        public void cardsReady(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardPlaced(GameImmutable model) throws RemoteException {

        }

        @Override
        public void cardDrawn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void nextTurn(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerDisconnected(GameImmutable model, String nick) throws RemoteException {

        }

        @Override
        public void errorReconnecting(String why) throws RemoteException {

        }

        @Override
        public void lastCircle(GameImmutable model) throws RemoteException {

        }

        @Override
        public void playerReady(GameImmutable gameImmutable, String nickname) throws RemoteException {

        }

        @Override
        public void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

        }

        @Override
        public void sentMessage(GameImmutable model, Message msg) throws RemoteException {

        }

    };


    Player p1 = new Player("Marti", Color.GREEN);
    Player p2 = new Player("Iri", Color.RED);
    Player p3 = new Player("Sofi", Color.BLUE);
    Player p4 = new Player("Meg", Color.YELLOW);
    Player p5 = new Player("Pino", Color.YELLOW);


    @Test
    @DisplayName("Disconnection with 4 players")
    void joinFirst() throws RemoteException {
        gameController = new GameController(); // GameController.getInstance()
        gameController.settingGame(lis1, 4 , 0, p1.getNickname());
        gameController.setGameCreated(true);
        //gameController.joinGame(lis1, p1.getNickname());
        gameController.joinGame(lis2, p2.getNickname());
        gameController.joinGame(lis3, p3.getNickname());
        gameController.joinGame(lis4, p4.getNickname());
        gameController.playerIsReadyToStart(lis1, p1.getNickname());
        gameController.playerIsReadyToStart(lis2, p2.getNickname());
        gameController.playerIsReadyToStart(lis3, p3.getNickname());
        gameController.playerIsReadyToStart(lis4, p4.getNickname());

        boolean allReady = true;
        for (Player player : gameController.getModel().getPlayers() ) {
            if (!player.getReadyToStart()) {
                allReady = false;
                break;
            }
        }
        if (allReady) {
            gameController.getModel().setStatus(GameStatus.RUNNING);
        }

        //disconnessione semplice
        gameController.disconnectPlayer(p2.getNickname(), lis2);
        assertEquals(3, gameController.getNumOfOnlinePlayers());
        gameController.reconnect(lis2, p2.getNickname());
        assertEquals(4, gameController.getNumOfOnlinePlayers());

        //player che non era connesso alla partita
        gameController.disconnectPlayer(p2.getNickname(), lis2);
        assertEquals(3, gameController.getNumOfOnlinePlayers());
        gameController.reconnect(lis4, p4.getNickname());
        assertEquals(3, gameController.getNumOfOnlinePlayers());
        gameController.reconnect(lis5, p5.getNickname());
        assertEquals(4, gameController.getNumOfOnlinePlayers());

        //timer disconnessione--> Controlla gestione corretta del timer di disconnessione
        gameController.disconnectPlayer(p1.getNickname(), lis1);
        gameController.disconnectPlayer(p2.getNickname(), lis2);
        gameController.disconnectPlayer(p3.getNickname(), lis3);
        assertEquals(1, gameController.getNumOfOnlinePlayers());
        gameController.reconnect(lis1, p1.getNickname());
        gameController.reconnect(lis2, p2.getNickname());
        gameController.reconnect(lis3, p3.getNickname());
        assertEquals(4, gameController.getNumOfOnlinePlayers());
        gameController.disconnectPlayer(p3.getNickname(), lis3);
        assertEquals(3, gameController.getNumOfOnlinePlayers());
        gameController.reconnect(lis3, p3.getNickname());
        assertEquals(4, gameController.getNumOfOnlinePlayers());

    }
}
/*

    @Test
    @DisplayName("Disconnection and finshed game")

    void Disc() throws RemoteException, GameEndedException {
        matrix2D[0] = new IntRecord(1, 3);
        matrix2D[1] = new IntRecord(1, 4);
        matrix2D[2] = new IntRecord(2, 3);
        matrix2D[3] = new IntRecord(2, 4);
        matrix2D[4] = new IntRecord(2, 5);
        matrix2D[5] = new IntRecord(3, 2);
        matrix2D[6] = new IntRecord(3, 3);
        matrix2D[7] = new IntRecord(3, 4);
        matrix2D[8] = new IntRecord(3, 5);
        matrix2D[9] = new IntRecord(3, 6);
        matrix2D[10] = new IntRecord(4, 1);
        matrix2D[11] = new IntRecord(4, 2);
        matrix2D[12] = new IntRecord(4, 3);
        matrix2D[13] = new IntRecord(4, 4);
        matrix2D[14] = new IntRecord(4, 5);
        matrix2D[15] = new IntRecord(4, 6);
        matrix2D[16] = new IntRecord(4, 7);
        matrix2D[17] = new IntRecord(5, 1);
        matrix2D[18] = new IntRecord(5, 2);
        matrix2D[19] = new IntRecord(5, 3);
        matrix2D[20] = new IntRecord(5, 4);
        matrix2D[21] = new IntRecord(5, 5);
        matrix2D[22] = new IntRecord(5, 6);
        matrix2D[23] = new IntRecord(6, 3);
        matrix2D[24] = new IntRecord(6, 4);
        matrix2D[25] = new IntRecord(6, 5);
        matrix2D[26] = new IntRecord(7, 4);
        matrix2D[27] = new IntRecord(7, 5);
        matrix2D[28] = new IntRecord(3, 7);

        for (int i = 0; i < 28; i++) {
            matrix3D[i] = new IntRecord(matrix2D[i].row(), matrix2D[i].col());
        }

        matrix3D[28] = new IntRecord(0, 3);
        matrix3D[29] = new IntRecord(2, 6);
        matrix3D[30] = new IntRecord(3, 8);
        matrix3D[31] = new IntRecord(5, 0);
        matrix3D[32] = new IntRecord(6, 2);
        matrix3D[33] = new IntRecord(6, 6);
        matrix3D[34] = new IntRecord(8, 5);

        gameController = new GameController();
        mainController = MainController.getInstance();
        Message message = new Message("Test", p1);
        int control=0;
        int i = 0;
        int index = 0;
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        gameController.addPlayer(p1);
        //Check if the player is correctly added to the game
        assert (gameController.getPlayers().size() == 1);
        gameController.playerIsReadyToStart(p1.getNickname());
        //check if the player is ready
        gameController.addPlayer(p2);
        //Check if the player is correctly added to the game
        assert (gameController.getPlayers().size() == 2);
        gameController.getPlayer(p2.getNickname());

        gameController.playerIsReadyToStart(p2.getNickname());

        //Check that the game status is running, otherwise fail the test
        assert (gameController.getStatus().equals(GameStatus.RUNNING));
        gameController.isThisMyTurn(p1.getNickname());
        int indexFirstPlayer = gameController.getIndexCurrentPlaying();

        while (gameController.getStatus().equals(GameStatus.RUNNING) || gameController.getStatus().equals(GameStatus.LAST_CIRCLE)) {

            do {
                gameController.grabTileFromPlayground(gameController.whoIsPlaying().getNickname(), matrix2D[index].row(), matrix2D[index].col(), Direction.DOWN, 1);
                index++;
                if (index == 28) {
                    index = 0;
                }
            } while (gameController.whoIsPlaying().getInHandTile().size() == 0);

            //check if the tile is correctly added to the player's hand
            assert (gameController.whoIsPlaying().getInHandTile().size() == 1);
            if (i == 5) {
                i = 0;
            }
            Player p = gameController.whoIsPlaying();
            int freeSpace = p.getShelf().getFreeSpace();
            gameController.positionTileOnShelf(gameController.whoIsPlaying().getNickname(), i, gameController.whoIsPlaying().getInHandTile().get(0).getType());

            //check if the tile is correctly added to the shelf
            assert (p.getShelf().getFreeSpace() == freeSpace - 1);
            i = i + 1;
            if(gameController.getStatus().equals(GameStatus.LAST_CIRCLE)){
                gameController.disconnectPlayer(p2.getNickname(),lis2);
                assertEquals(1, gameController.getNumOfOnlinePlayers());
            }
        }
        if(indexFirstPlayer==0){
            assert (p1.getShelf().getFreeSpace() == 0);
            assert (p2.getShelf().getFreeSpace() == 1);
        }else{
            assert (p1.getShelf().getFreeSpace() == 1);
            assert (p2.getShelf().getFreeSpace() == 0);
        }


        assert (gameController.getStatus().equals(GameStatus.ENDED));
        //mainController.reconnect(lis2,p2.getNickname(),gameController.getGameId());
        // control = gameController.getNumOnlinePlayers();
        //assertEquals(1, control);
    }
}
*/
