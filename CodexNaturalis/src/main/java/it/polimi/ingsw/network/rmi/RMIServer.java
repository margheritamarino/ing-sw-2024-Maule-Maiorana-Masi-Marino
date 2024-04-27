package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.rmi.remoteInterfaces.MainControllerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {
    //private final List<RMIClient> clients; //lista di riferimenti remoti al client

    public RMIServer() throws RemoteException {
       // clients = new ArrayList<>(); //lista di client collegati
    }
    static int PORT = 1234; //porta su cui è in ascolto il server
    public static void main(String args[]) throws Exception {
        System.out.println("Hello from server");

        RMIServer server = new RMIServer();
        MainControllerInterface stub=null;
        try {
             stub = (MainControllerInterface) UnicastRemoteObject.exportObject(server, PORT);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        //creo il REGISTRY
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(PORT); //PORTA di default del REGISTRY
        }catch (RemoteException e){
            e.printStackTrace();
        }
        try {
            //BIND: scrive una riga nel registry (associa il nome all'oggetto remoto)
            registry.bind("MainControllerInterface", stub); //nota: l'oggetto deve implementare l'INTERFACCIA REMOTA (ChatServer)
        }catch (RemoteException e){
            e.printStackTrace();
        }catch (AlreadyBoundException e){
            e.printStackTrace();
        }

        System.out.println("Server bound and ready");


    }
    @Override
    public boolean login (String nick) throws RemoteException{
        return false;
    }
    /*JOIN/ LEAVE: aggiungo/ rimuovo client alla lista
    //Server ha lista di riferimenti ai Client Collegati
    // client passa per riferimento oggetti al Server che vengono invocati cosi
    public synchronized void join(RMIClient client) throws RemoteException {
        clients.add(client);
    }

    public synchronized void leave(RMIClient client) throws RemoteException {
        clients.remove(client);
    }

    /*una funzione può essere usata da più thread per i vari client -> uso synchronized
    public void sendMsg(MyMessage msg) throws RemoteException {
        List<RMIClient> clientsCopy ;
        synchronized (this) {
            clientsCopy = new ArrayList<>(clients); //creo una copia
        }
        for (RMIClient c : clientsCopy) {
            c.printMsg(msg);
        }
    }*/


}
