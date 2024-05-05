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
import it.polimi.ingsw.view.Utilities.InputController;
import it.polimi.ingsw.view.Utilities.InputReader;
import it.polimi.ingsw.view.Utilities.InputTUI;
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
    protected InputController inputController;
    protected InputReader inputReader;
    protected List<String> importantEvents;
    private boolean ended = false;

    /**
     * Constructor of the class, based on the connection type it creates the clientActions and initializes the UI,
     * the FileDisconnection, the InputReader and the InputController
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
        //fileDisconnection = new FileDisconnection();
        this.inputReader = new InputTUI();
        this.inputController = new InputController(this.inputReader.getBuffer(), this);

        new Thread(this).start();

    }


    /*

    //Costruttore per la GUI

    public GameFlow(GUIApplication guiApplication, ConnectionType connectionType) {
        //Invoked for starting with GUI
        switch (connectionType) {
            case SOCKET -> clientActions = new ClientSocket(this);
            case RMI -> clientActions = new ClientRMI(this);
        }
        this.inputReader = new inputGUI();

        ui = new GUI(guiApplication, (inputGUI) inputReader);
        importantEvents = new ArrayList<>();
        nickname = "";
        fileDisconnection = new FileDisconnection();

        this.inputController = new InputController(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }
    */


    @Override
    public void run() {
        Event event;
        try {
            //inizializza l'interfaccia utente per mostrare la schermata iniziale
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
                //dopo ogni ciclo il thread dorme per 100 ms
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void statusWait(Event event) throws IOException, InterruptedException{
        String nicknameLastPlayer = event.getModel().getLastPlayer().getNickname();
        switch (event.getType()) {
            case PLAYER_JOINED -> {
                //Se l'evento è di tipo player joined significa che un giocatore si è unito alla lobby
                //verifico che il giocatore in lobby è l'ultimo giocatore ad aver eseguito l'azione
                if (nicknameLastPlayer.equals(nickname)) {
                    ui.show_playerJoined(event.getModel(), nickname);
                    saveGameId(fileDisconnection, nickname, event.getModel().getGameId());
                    askReadyToStart();
                }
            }
        }
    }

    public void statusRunning(Event event) throws IOException, InterruptedException{
        switch (event.getType()) {
            case GAME_STARTED -> {
                ui.show_gameStarted(event.getModel());

                this.inputController.setPlayer(event.getModel().getPlayerEntity(nickname));
                this.inputController.setGameID(event.getModel().getGameId());

            }

            //ALTRI CASE DA AGGIUNGERE

        }

    }


    //metodo chiamato quando un giocatore non viene aggiunto alla partita correttamente
    public void statusNotInAGame(Event event){
        switch (event.getType()) {

            //caso: game non valido -> back to menu
            case BACK_TO_MENU -> {
                //ciclo per chiedere al giocatore di selezionare una partita valida
                boolean selectionGame;
                do {
                    selectionGame = askSelectGame();
                } while (!selectionGame);
            }

            case NICKNAME_ALREADY_IN -> {
                nickname = null;
                events.add(null, EventType.BACK_TO_MENU); //aggiunge evento nullo per tornare al menu principale
                ui.addImportantEvent("ERROR: Nickname already used!");
            }

            case GAME_FULL -> {
                nickname = null;
                events.add(null, EventType.BACK_TO_MENU);
                ui.addImportantEvent("ERROR: Game is full!");
            }

            case GENERIC_ERROR -> {
                nickname = null;
                ui.show_returnToMenuMsg(); //mostra un messaggio di ritorno al menu sull'interfaccia utente
                try {
                    this.inputController.getUnprocessedData().popInputData(); //rimuovo il dato non elaborato dal buffer
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(null, EventType.BACK_TO_MENU);
            }
        }
    }

    public void statusEnded(Event event) throws NotBoundException, IOException {
        switch (event.getType()) {
            case GAME_ENDED -> {
                ui.show_returnToMenuMsg();
                //rimuove tutt i dati non elaborati dal buffer
                this.inputController.getUnprocessedData().popAllData();
                try {
                    this.inputController.getUnprocessedData().popInputData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //il giocatore lascia la partita
                this.leave(nickname, event.getModel().getGameId());
                this.playerLeftForGameEnded(); //notifica l'utente che ha lasciato la partita
            }
        }

    }
    public void playerLeftForGameEnded(){
        ended = true;
        ui.resetImportantEvents();
        events.add(null,EventType.BACK_TO_MENU);

        this.inputController.setPlayer(null); //il giocatore non è più associato al flusso di gioco
        this.inputController.setGameID(null);
    }
    public boolean isEnded(){
        return ended;
    }
    public void setEnded(boolean ended) {
        this.ended = ended;
    }



    /* METODI ASK DA FARE */ //Azioni che il Server richiede al Client di eseguire


    private void askNickname() {
        ui.show_insertNicknameMessage();
        try {
            nickname = this.inputController.getUnprocessedData().popInputData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ui.show_chosenNickname(nickname);
    }

    //metodo per guidare l'utente nel processo di selezione del gioco
    private void askSelectGame() throws NotBoundException, IOException, InterruptedException {
        String gameChosen;
        ended = false;
        ui.show_menuOptions();

        try {
            gameChosen = this.inputController.getUnprocessedData().popInputData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (gameChosen.equals(".")) //se l'input è . il meccanismo di selezione termina
            System.exit(1);
        askNickname();

        switch (gameChosen) {
            case "c" -> createGame(nickname);
            case "j" -> joinFirstAvailable(nickname);
            case "js" -> { //per chiedere di inserire l'ID del gioco a cui vuole aggiungersi
                Integer gameId = askGameId();
                if (gameId == -1)
                    return false;
                else
                    joinGame(nickname, gameId);
            }
            default -> {
                return false;
            }
        }

        return true;
    }

    //metodo per chiedere all'utente l'ID del gioco a cui vuole unirsi
    private Integer askGameId() {
        String temp;
        Integer gameID = null;
        //ciclo do while finché l'ID inserito non è corretto
        do {
            ui.show_insertGameIDMessage();
            try {
                try {
                    temp = this.inputController.getUnprocessedData().popInputData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (temp.equals(".")) {
                    return -1; //processo interrotto
                }
                gameID = Integer.parseInt(temp); //conversione input in integer
            } catch (NumberFormatException e) {
                ui.show_GameIDNotValidMessage(); //messaggio di errore sul gameID inserito
            }

        } while (gameID == null);
        return gameID;
    }






    /* METODI CHE IL SERVER HA RICEVUTO DAL CLIENT */
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
        events.add(null, EventType.NICKNAME_ALREADY_IN);
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
    public void gameEnded(GameImmutable gameImmutable) throws RemoteException {
        events.add(gameImmutable, EventType.GAME_ENDED);
        ended = true;
        ui.show_gameEnded(gameImmutable);
        //TODO quando aggiungiamo la disconnessione fai metodo RESET gioco
    }

    @Override
    public void requireInitialReady(GameImmutable model) throws IOException {
        ui.show_whichInitialCards();
        Integer index;
        do {
            index = Objects.requireNonNullElse(askNum("\t> Choose the Front or the Back with 0 (Front) or 1 (Back) :", model), -1);
            ui.show_temporaryInitialCards();
            if (ended) return;
            if (index < 0 || index >= 2) {
                ui.show_wrongSelectionInitialMsg();
                index = null;
            }
        } while (index == null);
        clientActions.setInitialCard(index); //manda l'indice selezionato per far risalire al Controller la InitialCard selezionata
    }

    @Override
    public void requireGoalsReady(GameImmutable model) throws RemoteException {

    }

    @Override
    public void requireGoalsReady(GameImmutable model, ArrayList<ObjectiveCard> objectiveCards) throws RemoteException {
        ui.show_whichObjectiveCards();
        Integer index;
        do {
            index = Objects.requireNonNullElse(askNum("\t> Choose one of these  Objective Cards selecting 0 or 1:", model), -1);
            ui.show_ObjectiveCards();
            if (ended) return;
            if (index < 0 || index >= 2) {
                ui.show_wrongSelectionObjectiveMsg();
                index = null;
            }
        } while (index == null);
        clientActions.setObjectiveCard(index); //manda l'indice selezionato per far risalire al Controller la InitialCard selezionata
    }


    @Override
    public void cardsReady(GameImmutable model) throws RemoteException {
        //TODO
        //stampare messaggio? mostrare la board?
        //dopo che ho inizializzato le carte per ogni player viene fatto partire il gioco
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
        ui.addImportantEvent("Last circle begin!");
    }

    @Override
    public void setInitialCard(int index) throws IOException {

    }



    /* METODI CHE IL CLIENT RICHIEDE AL SERVER */
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
