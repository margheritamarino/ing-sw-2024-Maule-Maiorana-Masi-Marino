package it.polimi.ingsw.network.socket.client;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
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
    public void playerJoined(GameImmutable model) throws RemoteException {
        flow.playerJoined(model);
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
    public void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException {
        flow.playerReconnected(model, nickPlayerReconnected);
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player triedToJoin) throws RemoteException {
        flow.joinUnableNicknameAlreadyIn(triedToJoin);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        flow.gameIdNotExists(gameid);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        flow.genericErrorWhenEnteringGame(why);
    }

    @Override
    public void playerIsReadyToStart(GameImmutable model, String nickname) throws IOException {
        flow.playerIsReadyToStart(model, nickname);
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
    public void requireInitialReady(GameImmutable model, PlayableCard[] initialCards) throws RemoteException {
        flow.gameEnded(model);
    }

    @Override
    public void requireGoalsReady(GameImmutable model, ArrayList<ObjectiveCard> objectiveCards) throws RemoteException {

    }

    @Override
    public void cardsReady(GameImmutable model) throws RemoteException {

    }

    @Override
    public void cardPlaced(GameImmutable model, Player player, int posCell, int posCard) throws RemoteException {

    }

    @Override
    public void cardDrawn(GameImmutable model) throws RemoteException {

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
}
