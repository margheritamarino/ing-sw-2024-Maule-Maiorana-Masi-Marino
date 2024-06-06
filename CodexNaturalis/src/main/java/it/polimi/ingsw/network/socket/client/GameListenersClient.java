package it.polimi.ingsw.network.socket.client;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.flow.Flow;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameListenersClient implements GameListenerInterface, Serializable {

    private Flow flow;

    public GameListenersClient(Flow gui) {
        this.flow = gui;
    }


    @Override
    public void playerJoined(GameImmutable model, String nickname, Color playerColor) throws RemoteException {
        flow.playerJoined(model, nickname, playerColor);
    }

    @Override
    public void requireNumPlayersGameID(GameImmutable model)throws RemoteException{
            flow.requireNumPlayersGameID(model);
    }
    @Override
    public void wrongChooseCard(GameImmutable model) throws RemoteException{
        flow.wrongChooseCard(model);
    }


    @Override
    public void playerLeft(GameImmutable model, String nickname) throws RemoteException {
        flow.playerLeft(model,nickname);
    }

    @Override
    public void joinUnableGameFull(Player triedToJoin, GameImmutable model) throws RemoteException {
        flow.joinUnableGameFull(triedToJoin, model);
    }


    @Override
    public void joinUnableNicknameAlreadyIn(Player triedToJoin) throws RemoteException {
        flow.joinUnableNicknameAlreadyIn(triedToJoin);
    }



    @Override
    public void gameStarted(GameImmutable model) throws RemoteException {
        flow.gameStarted(model);
    }

    @Override
    public void gameEnded(GameImmutable model) throws RemoteException {
        flow.gameEnded(model);
    }

    @Override
    public void requireInitialReady(GameImmutable model) throws IOException, FileReadException {
        flow.requireInitialReady(model);
    }

    @Override
    public void requireGoalsReady(GameImmutable model) throws RemoteException {
        flow.requireGoalsReady(model);
    }
    @Override
    public void cardsReady(GameImmutable model) throws RemoteException {
        flow.cardsReady(model);
    }
    @Override
    public void cardPlaced(GameImmutable model) throws RemoteException {
        flow.cardPlaced(model );
    }
    @Override
    public void pointsAdded(GameImmutable model) throws RemoteException {
        flow.pointsAdded(model );
    }


    @Override
    public void cardDrawn(GameImmutable model) throws RemoteException {
        flow.cardDrawn(model);
    }

    @Override
    public void nextTurn(GameImmutable model) throws RemoteException {
        flow.nextTurn(model);
    }

    @Override
    public void playerDisconnected(GameImmutable model, String nickname) throws RemoteException {
        flow.playerDisconnected(model, nickname);
    }

    @Override
    public void lastCircle(GameImmutable model) throws RemoteException {
        flow.lastCircle(model);
    }

  @Override
    public void playerReady(GameImmutable model, String nickname) throws RemoteException {
        flow.playerReady(model, nickname);
    }

    @Override
    public void sentMessage(GameImmutable model, Message msg) throws RemoteException {
        flow.sentMessage(model, msg);
    }

}
