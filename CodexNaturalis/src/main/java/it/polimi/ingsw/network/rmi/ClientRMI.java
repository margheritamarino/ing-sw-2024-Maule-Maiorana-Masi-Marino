package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.model.Chat.Message;


import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * RMIClient Class <br>
 * Handle all the network communications between RMIClient and RMIServer <br>
 * by the RMI Network Protocol
 */
public class ClientRMI implements ClientInterface {


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
