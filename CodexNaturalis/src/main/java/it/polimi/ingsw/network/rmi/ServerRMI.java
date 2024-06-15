package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Color;
//import it.polimi.ingsw.network.HeartbeatSender;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.cards.CardType;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
import static it.polimi.ingsw.view.TUI.PrintAsync.printAsyncNoLine;
import static java.rmi.registry.LocateRegistry.getRegistry;

/**
 * RMIServer Class
 * Handle all the incoming network requests that clients can require to join or leave a game
 * by the RMI Network protocol
 */

// implementa l'Interfaccia GameControllerInterface, contenete i metodi che il Client può invocare sul Server
// Chiamando metodi della GameListenerInterface il Server può notificare al Client degli eventi di gioco (poi il Client reagirà di conseguenza)
public class ServerRMI extends UnicastRemoteObject implements GameControllerInterface { //Provo a togliere che implementa GameControllerInterface (lo faccio implementare dal solo GameController)
    /**
     * ServerRMI object
     */
     private static ServerRMI serverObject = null;

    /**
     * Controller of out Game
     */
    private final GameControllerInterface gameController;


    /**
     * Registry associated with the RMI Server
     */
    private static Registry registry = null;

    /**
     * Create a RMI Server
     * @return the instance of the server
     */
    public static ServerRMI bind() throws RemoteException {
        try {
            serverObject = new ServerRMI();

           // Prova a recuperare il registro esistente, se non esiste creane uno nuovo
            try {
                registry = LocateRegistry.getRegistry(DefaultValue.Default_port_RMI);
                registry.list(); // Chiamata per verificare se il registro è effettivamente accessibile
            } catch (RemoteException e) {
                registry = LocateRegistry.createRegistry(DefaultValue.Default_port_RMI);
            }

            // Registra l'oggetto remoto nel registro
            getRegistry().rebind(DefaultValue.Default_servername_RMI, serverObject);

            System.out.println("RMI server started on port " + DefaultValue.Default_port_RMI + ".");
            printAsync("Server RMI ready");

        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
            throw e; // Rilancia l'eccezione per assicurare che il chiamante sia a conoscenza del fallimento
        }
        return getInstance();
    }




    /**
     * @return the istance of the RMI Server
     */
    public synchronized static ServerRMI getInstance() {
        if(serverObject == null) {
            try {
                serverObject = new ServerRMI();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return serverObject;
    }

    /**
     * @return the registry associated with the RMI Server
     * @throws RemoteException
     */
    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }

    /**
     * Constructor that creates a RMI Server
     * @throws RemoteException
     */
    public ServerRMI() throws RemoteException {
        super(); //tolto 0
        gameController= GameController.getInstance();
    }


    /**
     * Close the RMI Server
     * Used only for testing purposes
     *
     * @return RMI Server
     */

    public static ServerRMI unbind() throws RemoteException{
        try {
            getRegistry().unbind(DefaultValue.Default_servername_RMI);
            UnicastRemoteObject.unexportObject(getRegistry(), true);
            printAsync("Server RMI correctly closed");
        } catch (RemoteException e) {
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("[ERROR] CLOSING RMI SERVER: \n\tServer RMI exception: " + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getInstance();
    }

   /* @Override
    public void joinGame(GameListenerInterface lis, String nick, Color color) throws RemoteException {
        //gameController.joinGame(lis, nick, color);
         serverObject.gameController.joinGame(lis, nick, color); //TRY

        printAsync("[RMI] " + nick + " joined to game");
    }*/
   @Override
   public void joinGame(GameListenerInterface lis, String nick, Color color) throws RemoteException {

           serverObject.gameController.joinGame(lis, nick, color);
           /*try {
               UnicastRemoteObject.exportObject(serverObject.gameController,0);
           } catch (RemoteException e) {
               // Already exported, due to another RMI Client running on the same machine
           }*/
           printAsync("[RMI] " + nick + " joined to game");
   }

    @Override
    public void leave(GameListenerInterface lis, String nick) throws RemoteException {
        //gameController.leave(lis, nick);
        serverObject.gameController.leave(lis, nick);
    }

   //DA QUI PROVA A ELIMINARE TUTTO

    public boolean  playerIsReadyToStart(GameListenerInterface lis, String p) throws RemoteException { //siamo sicuri serva Server RMI? FORSE DEVI PASSARE DIRETTA AL GameController
        System.out.println("in ServerRMI- playerIsReadyToStart");
        //return gameController.playerIsReadyToStart(lis, p);
         return serverObject.gameController.playerIsReadyToStart(lis, p);
        //return false;
    }

    @Override
    public boolean isThisMyTurn(String nick) throws RemoteException {
        //return gameController.isThisMyTurn(nick);
        return serverObject.gameController.isThisMyTurn(nick);
    }

    @Override
    public void disconnectPlayer(String nick, GameListenerInterface listener) throws RemoteException {
        //gameController.disconnectPlayer(nick, listener);
       serverObject.gameController.disconnectPlayer(nick, listener);
    }

    @Override
    public int getGameId() throws RemoteException {
        //return gameController.getGameId();
        return serverObject.gameController.getGameId();
    }

    @Override
    public void ping(String nickname, GameListenerInterface me) throws RemoteException {
        //gameController.ping(nickname, me);
        serverObject.gameController.ping(nickname, me);
    }



    @Override
    public void setInitialCard(String nickname, int index) throws RemoteException {
        //gameController.setInitialCard(nickname,index);
        serverObject.gameController.setInitialCard(nickname,index);
    }

    @Override
    public void setGoalCard(String nickname, int index) throws NotPlayerTurnException, RemoteException, NotPlayerTurnException {
        //gameController.setGoalCard(nickname,index);
        serverObject.gameController.setGoalCard(nickname,index);
    }

    @Override
    public void placeCardInBook(String nickname, int chosenCard, int rowCell, int columnCell) throws RemoteException {
        //gameController.placeCardInBook(nickname, chosenCard, rowCell, columnCell);
        serverObject.gameController.placeCardInBook(nickname, chosenCard, rowCell, columnCell);
    }


    @Override
    public void PickCardFromBoard(String nickname, CardType cardType, boolean drawFromDeck, int pos) throws RemoteException {
        //gameController.PickCardFromBoard(nickname, cardType, drawFromDeck, pos);
        serverObject.gameController.PickCardFromBoard(nickname, cardType, drawFromDeck, pos);
    }

    @Override
    public void settingGame(GameListenerInterface lis, int numPlayers, int gameID, String nickname, Color color) throws RemoteException {
        //gameController.settingGame(lis, numPlayers, gameID, nickname, color);
        serverObject.gameController.settingGame(lis, numPlayers, gameID, nickname, color);
    }

    @Override
    public boolean makeGameStart(GameListenerInterface lis, String nickname) throws RemoteException {
        //return gameController.makeGameStart(lis, nickname);
       return serverObject.gameController.makeGameStart(lis, nickname);
    }

    @Override
    public void sentMessage(Message msg) throws RemoteException { //messaggi da sincronizzare (?) Se più giocatori insieme accedono alla risorsa
        //gameController.sentMessage(msg);
        serverObject.gameController.sentMessage(msg);
    }


}

