package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;
import it.polimi.ingsw.network.socket.Messages.ClientGenericMessage;

import java.rmi.RemoteException;

public class ClientMsgPlaceCard extends ClientGenericMessage {
    int chosenCard;
    int rowCell;
    int columnCell;


    public ClientMsgPlaceCard(String nickname, int chosenCard, int rowCell, int columnCell) {
        this.nickname = nickname;
        this.chosenCard = chosenCard;
        this.rowCell = rowCell;
        this.columnCell = columnCell;
        this.isMessageForMainController = false;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @param mainController the main controller of the application
     * @return the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public GameControllerInterface execute(GameListenerInterface lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, NotPlayerTurnException {
        gameController.placeCardInBook(nickname,chosenCard,rowCell,columnCell);
    }
}
