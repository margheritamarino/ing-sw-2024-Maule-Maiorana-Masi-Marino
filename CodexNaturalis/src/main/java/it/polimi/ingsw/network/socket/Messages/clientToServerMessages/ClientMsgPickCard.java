package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.rmi.RemoteException;

public class ClientMsgPickCard extends ClientGenericMessage {
    CardType cardType;
    boolean drawFromDeck;
    int pos;


    public ClientMsgPickCard(String nickname, CardType cardType, boolean drawFromDeck, int pos) {
        this.nickname = nickname;
        this.cardType = cardType ;
        this.drawFromDeck = drawFromDeck;
        this.pos = pos;
    }


    @Override
    public void execute(GameListenerInterface lis, GameController gameController) throws RemoteException {

    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {
        gameController.PickCardFromBoard(nickname, cardType, drawFromDeck, pos);
    }
}
