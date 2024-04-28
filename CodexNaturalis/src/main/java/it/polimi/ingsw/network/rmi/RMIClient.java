package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.network.Chat.Message;


import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * RMIClient Class <br>
 * Handle all the network communications between RMIClient and RMIServer <br>
 * From the first connection, to the creation, joining, leaving, grabbing and positioning messages through the network<br>
 * by the RMI Network Protocol
 */
public class RMIClient implements ClientInterface {


    @Override
    public void createGame(String nick) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void joinFirstAvailable(String nick) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void joinGame(String nick, int idGame) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void reconnect(String nick, int idGame) throws IOException, InterruptedException, NotBoundException {

    }

    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {

    }

    @Override
    public void setAsReady() throws IOException {

    }

    @Override
    public boolean isMyTurn() throws RemoteException {
        return false;
    }

    @Override
    public void sendMessage(Message msg) throws RemoteException {

    }

    @Override
    public void heartbeat() throws RemoteException {

    }
}
