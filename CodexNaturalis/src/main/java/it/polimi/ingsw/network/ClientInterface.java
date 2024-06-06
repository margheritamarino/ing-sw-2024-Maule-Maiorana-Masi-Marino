package it.polimi.ingsw.network;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.CardType;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

//interface for common client actions
public interface ClientInterface  {


    /**
     * Adds the player to the game
     *
     *
     */
    void joinGame(String nick, Color color) throws IOException, InterruptedException, NotBoundException;

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
    void setAsReady(String nickname) throws IOException;

    /*
    /**
     * Pings the server
     *
     * @throws RemoteException

    void ping() throws RemoteException;

*/
    void placeCardInBook(int chosenCard, int rowCell, int columnCell) throws IOException;

    void settingGame(int numPlayers, int GameID, String nick, Color color)throws IOException;

    void setInitialCard(int index, String nickname) throws IOException;
    void setGoalCard(int index, String nickname) throws IOException, NotPlayerTurnException;



    void PickCardFromBoard(CardType cardType, boolean drawFromDeck, int pos) throws IOException;

    void ping () throws RemoteException;


    //void makeGameStart(String nick)throws IOException;

    void sendMessage(Message msg) throws RemoteException;

    void makeGameStart(String nickname)throws IOException;
}


