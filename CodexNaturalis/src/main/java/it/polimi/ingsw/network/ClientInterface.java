package it.polimi.ingsw.network;

import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.network.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

//interface for common client actions
public interface ClientInterface  {


    /**
     * Adds the player to the game
     *
     *
     */
    void joinGame(String nick) throws IOException, InterruptedException, NotBoundException;

    //FUNZIONALITA AGGIUNTIVA


    /**
     * Leaves the game
     *
     *
     */
    void leave(String nick) throws IOException, NotBoundException;

    /**
     * Sets the invoker as ready
     *
     * @throws IOException
     */
    void setAsReady() throws IOException;

    /*
    /**
     * Pings the server
     *
     * @throws RemoteException

    void ping() throws RemoteException;

*/
    void placeCardInBook(int chosenCard, int rowCell, int columnCell) throws IOException;

    void settingGame(int numPlayers, int GameID, String nick)throws IOException;

    void setInitialCard(int index) throws IOException;
    void setGoalCard(int index) throws IOException, NotPlayerTurnException;



    void PickCardFromBoard(CardType cardType, boolean drawFromDeck, int pos) throws IOException;

    void ping () throws RemoteException;


    void makeGameStart(String nick)throws IOException;
}


