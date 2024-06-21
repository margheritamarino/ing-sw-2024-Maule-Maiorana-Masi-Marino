package it.polimi.ingsw.network.socket.Messages.clientToServerMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.network.rmi.GameControllerInterface;

import java.rmi.RemoteException;

public class ClientMsgPlaceCard extends ClientGenericMessage {
    int chosenCard;
    int rowCell;
    int columnCell;


    /**
     * Constructs a new ClientMsgPlaceCard instance.
     *
     * @param nickname   The nickname of the player requesting to place the card.
     * @param chosenCard The index of the card to be placed.
     * @param rowCell    The row position in the player's book where the card will be placed.
     * @param columnCell The column position in the player's book where the card will be placed.
     */
    public ClientMsgPlaceCard(String nickname, int chosenCard, int rowCell, int columnCell) {
        this.nickname = nickname;
        this.chosenCard = chosenCard;
        this.rowCell = rowCell;
        this.columnCell = columnCell;
    }


    /**
     * Executes the action corresponding to this message on the server-side game listener and controller.
     *
     * @param lis           The game listener interface on the server.
     * @param gameController The game controller handling the game logic.
     * @throws RemoteException If there is an error in remote communication.
     */
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
        gameController.placeCardInBook(nickname,chosenCard,rowCell,columnCell);
    }
}
