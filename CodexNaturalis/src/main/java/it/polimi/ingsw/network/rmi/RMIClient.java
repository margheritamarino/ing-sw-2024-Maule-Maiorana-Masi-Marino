package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.rmi.remoteInterfaces.MainControllerInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements ClientActions {
    static int PORT = 1234;
    public static void main(String args[]) throws Exception {
        System.out.println("Hello from client");
        RMIClient client = new RMIClient();
        try {
            Registry registry= LocateRegistry.getRegistry("127.0.0.1", PORT);
            MainControllerInterface stub = (MainControllerInterface) registry.lookup("MainControllerInterface");
            //CHIAMATA AI METODI
            boolean logged = stub.login("Bob");
        }catch (Exception e){
            System.err.println("Client exception: "+ e.toString());
            e.printStackTrace();
        }
    }

    public RMIClient() throws RemoteException {
    }

}


