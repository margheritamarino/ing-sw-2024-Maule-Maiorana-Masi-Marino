package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.rmi.RemoteException;

public class ClientMsgCreateGame extends ClientGenericMessage {
    int numPlayers;
    int GameID;
    Color color;
    /**
     * Constructor of the class.
     * @param nickname the player's nickname
     */
    public ClientMsgCreateGame(int numPlayers, int GameID, String nickname, Color color) {
        this.numPlayers= numPlayers;
        this.GameID=GameID;
        this.nickname = nickname;
        this.color=color;
        this.isJoinGame= true;
    }

    @Override
    public void execute(GameListenerInterface lis, GameController gameController) throws RemoteException {
        gameController.settingGame(lis,numPlayers, GameID, this.nickname, this.color);
    }


    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {

    }


}


