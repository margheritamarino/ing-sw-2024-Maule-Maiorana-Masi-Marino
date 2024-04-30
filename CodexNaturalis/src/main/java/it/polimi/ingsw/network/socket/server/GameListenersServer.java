package it.polimi.ingsw.network.socket.server;

import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.socket.client.serverToClientGenericMessages.*;


import java.io.Serializable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameListenersServer implements GameListenerInterface, Serializable {
    private final ObjectOutputStream out;

    /**
     * This constructor creates a GameListenersHandlerSocket
     * @param out the ObjectOutputStream
     */
    public GameListenersServer(ObjectOutputStream out) {
        this.out = out;
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has joined the game
     * @param model is the game model {@link GameImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerJoined(GameImmutable model) throws RemoteException {
        try {
            out.writeObject(new msgPlayerJoined(model));
            finishSending();
        } catch (IOException e) {
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has left the game
     * @param model is the game model {@link GameImmutable}
     * @param nickname is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerLeft(GameImmutable model, String nickname) throws RemoteException {
        try {
            out.writeObject(new msgPlayerLeft(model, nickname));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player is unable to join the game because it is full
     * @param player is the player that has tried to join the game {@link Player}
     * @param model is the game model {@link GameImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void joinUnableGameFull(Player player, GameImmutable model) throws RemoteException {
        try {
            out.writeObject(new msgJoinUnableGameFull(player,model));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has reconnected to the game
     * @param model is the game model {@link GameImmutable}
     * @param nickPlayerReconnected is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException {
        //System.out.println(nickNewPlayer +" by socket");
        try {
            out.writeObject(new msgPlayerReconnected(model, nickPlayerReconnected));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player is unable to join the game because the nickname is already in use
     * @param wantedToJoin is the player that has tried to join the game {@link Player}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        try {
            out.writeObject(new msgJoinUnableNicknameAlreadyIn(wantedToJoin));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that the gameID is not valid
     * @param gameid is the id of the game
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        try {
            out.writeObject(new msgGameIdNotExists(gameid));
            finishSending();
        } catch (IOException e) {
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream a generic error when entering the game
     * @param why is the reason why the error happened
     * @throws RemoteException if the connection fails
     */
    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        try {
            out.writeObject(new msgGenericErrorWhenEntryingGame(why));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that player is ready to start
     * @param model is the game model {@link GameImmutable}
     * @param nickname is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerIsReadyToStart(GameImmutable model, String nickname) throws IOException {
        //System.out.println(nickname +" ready to start by socket");
        try {
            out.writeObject(new msgPlayerIsReadyToStart(model, nickname));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the game started
     * @param model is the game model {@link GameImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameStarted(GameImmutable model) throws RemoteException {
        //System.out.println(gamemodel.getGameId() +" game started by socket");
        try {
            out.writeObject(new msgGameStarted(model));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the game ended
     * @param model is the game model {@link GameImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameEnded(GameImmutable model) throws RemoteException {
        try {
            out.writeObject(new msgGameEnded(model));
            finishSending();
        } catch (IOException e) {

        }
    }

    @Override
    public void requireInitialReady(GameImmutable model, PlayableCard[] initialCards) throws RemoteException {
        //TODO
    }

    @Override
    public void requireGoalsReady(GameImmutable model, ArrayList<ObjectiveCard> objectiveCards) throws RemoteException {
        //TODO
    }

    @Override
    public void cardsReady(GameImmutable model) throws RemoteException {
        //TODO
    }

    @Override
    public void cardPlaced(GameImmutable model, Player player, int posCell, int posCard) throws RemoteException {
        //TODO
    }

    @Override
    public void cardDrawn(GameImmutable model) throws RemoteException {
        //TODO
    }

    /**
     * This method is used to write on the ObjectOutputStream that the next turn is started
     * @param model is the game model {@link GameImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void nextTurn(GameImmutable model) throws RemoteException {
        try {
            out.writeObject(new msgNextTurn(model));
            finishSending();
        } catch (IOException e) {
        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that a player has disconnected
     * @param model is the game model {@link GameImmutable}
     * @param nickname is the nickname of the player that has disconnected
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerDisconnected(GameImmutable model, String nickname) throws RemoteException {
        try {
            out.writeObject(new msgPlayerDisconnected(model,nickname));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the last circle is started
     * @param model is the game model {@link GameImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void lastCircle(GameImmutable model) throws RemoteException {
        try {
            out.writeObject(new msgLastCircle(model));
            finishSending();
        } catch (IOException e) {

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
