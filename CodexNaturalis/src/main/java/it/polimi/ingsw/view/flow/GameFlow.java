package it.polimi.ingsw.view.flow;


import it.polimi.ingsw.model.cards.InitialCard;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.game.GameStatus;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.network.rmi.ClientRMI;
import it.polimi.ingsw.network.socket.client.ClientSocket;
import it.polimi.ingsw.view.Utilities.UI;
import it.polimi.ingsw.view.TUI.TUI;
import it.polimi.ingsw.view.events.Event;
import it.polimi.ingsw.view.events.EventList;
import it.polimi.ingsw.view.events.EventType;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Gestisce il flusso di gioco e l'interazione tra client e server
public class GameFlow extends Flow implements Runnable, ClientInterface {
    private String nickname;
    private final EventList events = new EventList();
    private ClientInterface clientActions;
   // private final FileDisconnection fileDisconnection;
    private String lastPlayerReconnected;
    private final UI ui;

    /**
     * InputReader {@link InputReader} to read the input, and add it to the buffer.
     * InputParser {@link InputParser} pops the input from the buffer and parses it
     */
   // protected InputParser inputParser;
   //  protected InputReader inputReader;
    /**
     * Events that always need to be shown on the screen
     */
    protected List<String> importantEvents;
    private boolean ended = false;

    /**
     * Constructor of the class, based on the connection type it creates the clientActions and initializes the UI {@link UI}(TUI)
     * the FileDisconnection {@link FileDisconnection}, the InputReader {@link InputReader} and the InputParser {@link InputParser}
     *
     * @param connectionType the connection type
     */
    public GameFlow(ConnectionType connectionType) {
        //Invoked for starting with TUI
        switch (connectionType) {
            case SOCKET -> clientActions = new ClientSocket(this);
            case RMI -> clientActions = new ClientRMI(this);
        }
        ui = new TUI();

        importantEvents = new ArrayList<>();
        nickname = "";
        fileDisconnection = new FileDisconnection();
        this.inputReader = new inputReaderTUI();
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);

        new Thread(this).start();
    }

    /**
     * Constructor of the class, based on the connection type it creates the clientActions and initializes the UI {@link UI} (GUI)
     *
     * @param guiApplication      the GUI application {@link GUIApplication}
     * @param connectionSelection the connection type {@link ConnectionSelection}
     */
    public GameFlow(GUIApplication guiApplication, ConnectionType connectionType) {
        //Invoked for starting with GUI
        switch (connectionType) {
            case SOCKET -> clientActions = new ClientSocket(this);
            case RMI -> clientActions = new ClientRMI(this);
        }
        this.inputReader = new inputReaderGUI();

        ui = new GUI(guiApplication, (inputReaderGUI) inputReader);
        importantEvents = new ArrayList<>();
        nickname = "";
        fileDisconnection = new FileDisconnection();

        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }


    @Override
    public void run() {
        Event event;
        try {
            ui.show_publisher();
            events.add(null, EventType.BACK_TO_MENU);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (!Thread.interrupted()) {
            if (events.isJoined()) {
                //Get one event
                event = events.pop();
                if (event != null) {
                    //if something happened
                    switch (event.getModel().getStatus()) {
                        case WAIT -> {
                            try {
                                statusWait(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case RUNNING, LAST_CIRCLE -> {
                            try {
                                statusRunning(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case ENDED -> statusEnded(event);
                    }
                }
            } else {
                event = events.pop();
                if (event != null) {
                    statusNotInAGame(event);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void statusWait(Event event){
        String nicknameLastPlayer = event.getModel().getLastPlayer().getNickname();
        switch (event.getType()) {
            case PLAYER_JOINED -> {
                if (nicknameLastPlayer.equals(nickname)) {
                    ui.show_playerJoined(event.getModel(), nickname);
                    saveGameId(fileDisconnection, nickname, event.getModel().getGameId());
                    askReadyToStart();
                }
            }
        }
    }

    public void statusRunning(Event event){

    }

    public void statusEnded(Event event){

    }

    public void statusNotInAGame(Event event){

    }



    /**
     * A player has joined the game
     * @param gameModel game model {@link GameImmutable}
     */
    @Override
    public void playerJoined(GameImmutable gameModel, String nickname) {
        //shared.setLastModelReceived(gameModel);
        events.add(gameModel, EventType.PLAYER_JOINED);

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        ui.show_playerJoined(gameModel, nickname);
        ui.addImportantEvent("[EVENT]: Player " + nickname + " joined the game!");


    }

    @Override
    public void playerLeft(GameImmutable gameImmutable, String nickname) throws RemoteException {
        if (gameImmutable.getStatus().equals(GameStatus.WAIT)) {
            ui.show_playerJoined(gameImmutable, nickname);
        } else {
            ui.addImportantEvent("[EVENT]: Player " + nickname + " decided to leave the game!");
        }

    }

    /**
     * A player wanted to join a game but the game is full
     * @param wantedToJoin player that wanted to join
     * @param gameModel game model {@link GameImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameImmutable gameModel) throws RemoteException {
        events.add(null, EventType.GAME_FULL);
    }


    @Override
    public void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException {

    }

    /**
     * A player wanted to join a game but the nickname is already in
     * @param tryToJoin player that wanted to join {@link Player}
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player tryToJoin) throws RemoteException {
        //System.out.println("[EVENT]: "+ tryToJoin.getNickname() + " has already in");
        events.add(null, NICKNAME_ALREADY_IN);
    }


    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {

    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {

    }

    @Override
    public void playerIsReadyToStart(GameImmutable model, String nick) throws IOException {

    }

    /**
     * The game started
     * @param gameImmutable game model {@link GameImmutable}
     */
    @Override
    public void gameStarted(GameImmutable gameImmutable) throws RemoteException {
        events.add(gameImmutable, EventType.GAME_STARTED);
    }

    @Override
    public void gameEnded(GameImmutable model) throws RemoteException {

    }

    @Override
    public void requireInitialReady(GameImmutable model) throws RemoteException {
        ui.show_WhichInitialCards();
        Integer index;
        do {
            index = Objects.requireNonNullElse(askNum("\t> Choose the Front or the Back with 0 (Front) or 1 (Back) :", model), -1);
            ui.show_TemporaryInitialCards();
            if (ended) return;
            if (index < 0 || index >= 2) {
                ui.show_wrongSelectionInitialMsg();
                index = null;
            }
        } while (index == null);
        setInitialCard(index); //manda l'indice selezionato per far risalire al Controller la InitialCard selezionata
    }



    @Override
    public void requireGoalsReady(GameImmutable model, ArrayList<ObjectiveCard> objectiveCards) throws RemoteException {

    }

    @Override
    public void cardsReady(GameImmutable model) throws RemoteException {

    }

    @Override
    public void cardPlaced(GameImmutable model, Player player, int posCell, int posCard) throws RemoteException {

    }

    @Override
    public void cardDrawn(GameImmutable model) throws RemoteException {

    }

    @Override
    public void nextTurn(GameImmutable model) throws RemoteException {

    }

    @Override
    public void playerDisconnected(GameImmutable model, String nick) throws RemoteException {

    }

    @Override
    public void lastCircle(GameImmutable model) throws RemoteException {

    }

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
    public void heartbeat() throws RemoteException {

    }

    @Override
    public void noConnectionError() {

    }
}
