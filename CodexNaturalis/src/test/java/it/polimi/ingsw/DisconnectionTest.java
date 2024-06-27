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


    //Initialization (showing nothing on TUI or GUI):
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
        gameController = new GameController();
        gameController.settingGame(lis1, 4 , 0, p1.getNickname());
        gameController.setGameCreated(true);
        gameController.joinGame(lis2, p2.getNickname());
        gameController.joinGame(lis3, p3.getNickname());
        gameController.joinGame(lis4, p4.getNickname());
        gameController.playerIsReadyToStart(lis1, p1.getNickname());
        gameController.playerIsReadyToStart(lis2, p2.getNickname());
        gameController.playerIsReadyToStart(lis3, p3.getNickname());
        gameController.playerIsReadyToStart(lis4, p4.getNickname());
        gameController.getModel().getPlayerByNickname(p1.getNickname()).setConnected(true);
        gameController.getModel().getPlayerByNickname(p2.getNickname()).setConnected(true);
        gameController.getModel().getPlayerByNickname(p3.getNickname()).setConnected(true);
        gameController.getModel().getPlayerByNickname(p4.getNickname()).setConnected(true);
        gameController.getModel().setCurrentPlayer(p1);
        assertEquals(4,gameController.getModel().getPlayers().size() );
        gameController.getModel().chooseOrderPlayers();

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
