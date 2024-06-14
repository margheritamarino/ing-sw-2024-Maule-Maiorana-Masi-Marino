package it.polimi.ingsw.view.flow;


import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.network.rmi.ClientRMI;
import it.polimi.ingsw.network.socket.client.ClientSocket;
import it.polimi.ingsw.view.GUI.GUI;
import it.polimi.ingsw.view.GUI.GUIApplication;
import it.polimi.ingsw.view.Utilities.*;
import it.polimi.ingsw.view.TUI.TUI;
import it.polimi.ingsw.view.events.Event;
import it.polimi.ingsw.view.events.EventList;
import it.polimi.ingsw.view.events.EventType;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;


//Capire come parte il pescaggio delle carte nel 1°Truno di gioco

//Gestisce il flusso di gioco e l'interazione tra client e server
public class GameFlow extends Flow implements Runnable, ClientInterface {
    private String nickname;
    public Color color;
    private final EventList events = new EventList();
    private ClientInterface clientActions;
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

        this.inputReader = new InputTUI();
        this.inputController = new InputController(this.inputReader.getBuffer(), this);

        new Thread(this).start();

    }

    public GameFlow(GUIApplication guiApplication, ConnectionType connectionType){
        switch (connectionType){
            case SOCKET -> clientActions = new ClientSocket(this);
            case RMI -> clientActions = new ClientRMI(this);
        }
        this.inputReader = new InputGUI();
        ui = new GUI(guiApplication, (InputGUI) inputReader);
        importantEvents = new ArrayList<>();
        nickname = "";

        this.inputController = new InputController(this.inputReader.getBuffer(), this);

        new Thread(this).start();

    }


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
                        case ENDED -> {
                            try {
                                statusEnded(event);
                            } catch (IOException | NotBoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            } else {
                event = events.pop();
                if (event != null) {
                    try {
                        statusNotInAGame(event);
                    } catch (NotBoundException | IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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
                ui.show_playerJoined(event.getModel(), this.nickname, this.color);
                if (nicknameLastPlayer.equals(nickname)) {
                    //Se l'evento è di tipo player joined significa che un giocatore si è unito alla lobby
                    //verifico che il giocatore in lobby è l'ultimo giocatore ad aver eseguito l'azione

                    askReadyToStart(event.getModel(), nickname);
                }
            }
            case PLAYER_READY -> {
                ui.show_youAreReady(event.getModel());
                ui.show_askForChat(event.getModel(), nickname);
            }
            case CARDS_READY -> {
                makeGameStart(nickname);
            }
        }
    }



    public void statusRunning(Event event) throws IOException, InterruptedException{
        switch (event.getType()) {
            case GAME_STARTED -> {
                ui.closeWaitPopUp();
                ui.show_gameStarted(event.getModel());
                this.inputController.setPlayer(event.getModel().getPlayerByNickname(nickname));
                this.inputController.setGameID(event.getModel().getGameId());
                if(event.getModel().getCurrentPlayer().getNickname().equals(nickname)){
                    ui.show_CurrentTurnMsg(event.getModel());
                    askPlaceCards(event.getModel(), nickname);
                  //  ui.show_alwaysShow(event.getModel(), nickname);
                }
                else{
                    ui.show_WaitTurnMsg(event.getModel(), nickname);
                }
            }
            case NEXT_TURN -> {
                ui.show_nextTurnMsg(event.getModel());
                if(event.getModel().getCurrentPlayer().getNickname().equals(nickname)){
                    ui.show_CurrentTurnMsg(event.getModel());
                    askPlaceCards(event.getModel(), nickname);
                }
                else{
                    ui.show_WaitTurnMsg(event.getModel(), nickname);
                }
            }

            case CARD_PLACED ->{
                ui.show_cardPlacedMsg(event.getModel());
                ui.show_pointsAddedMsg(event.getModel(), nickname);

                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)){
                    askPickCard(event.getModel());
                }else
                    ui.closeWaitPopUp();;

            }
            case SENT_MESSAGE -> {
                ui.show_sentMessage(event.getModel(), nickname);
            }

            case CARD_PLACED_NOT_CORRECT -> {//ask the Player to choose again
                ui.show_playerHasToChooseAgain(event.getModel(), nickname);
                askPlaceCards(event.getModel(), nickname);
            }
            case CARD_DRAWN -> {
                ui.show_cardDrawnMsg(event.getModel(), nickname);

            }

        }

    }


    //metodo chiamato quando un giocatore non viene aggiunto alla partita correttamente
    public void statusNotInAGame(Event event) throws NotBoundException, IOException, InterruptedException {
        switch (event.getType()) {

            //caso: game non valido -> back to menu
            case BACK_TO_MENU -> {
                if(ended)
                    ui.show_returnToMenuMsg();
                else{
                    askNickname();
                    joinGame(nickname);
                }
                //ciclo per chiedere al giocatore di selezionare una partita valida
            }

            case NICKNAME_ALREADY_IN -> {
                nickname = null;
                Color.addColor(this.color);
                this.color= null;

                events.add(null, EventType.BACK_TO_MENU); //aggiunge evento nullo per tornare al menu principale
                ui.addImportantEvent("ERROR: Nickname already used!");
            }

            case GAME_FULL -> {
                nickname = null;
                this.color=null;
                ui.addImportantEvent("ERROR: Game is Full, you can't play!");
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
                ui.show_gameEnded(event.getModel());
                //rimuove tutt i dati non elaborati dal buffer
                this.inputController.getUnprocessedData().popAllData();
                try {
                    this.inputController.getUnprocessedData().popInputData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //il giocatore lascia la partita
                this.leave(nickname);
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



    /* METODI ASK DA FARE */
    //Azioni che il Server richiede al Client di eseguire

    private int askNumPlayers() {
        String temp;
        int numPlayers;
        do {
            try {
                ui.show_askNumPlayersMessage(); // "Inserire il numero di giocatori nella partita:"

                try {
                    temp = this.inputController.getUnprocessedData().popInputData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                numPlayers = Integer.parseInt(temp); // Traduce il numero in intero

                // Verifica che il numero sia nel range accettabile
                if ( numPlayers < DefaultValue.minNumOfPlayer || numPlayers > DefaultValue.MaxNumOfPlayer) {
                    ui.show_notValidMessage(); // Mostra un messaggio di errore
                    numPlayers = -1; // Resetta numPlayers per ripetere il ciclo
                }
            } catch (NumberFormatException e) {
                ui.show_notValidMessage(); // Mostra un messaggio di errore per input non numerico
                numPlayers = -1; // Resetta numPlayers per ripetere il ciclo
            }
        } while (numPlayers == -1);

        return numPlayers;
    }

    private int askGameID(){
        String temp;
        int GameID= -1;
        do {
            try {
                ui.show_askGameIDMessage();
                try {
                    temp = this.inputController.getUnprocessedData().popInputData();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                GameID = Integer.parseInt(temp); //traduco il numero in integer
                if(GameID < 0)
                    ui.show_notValidMessage();
            } catch ( NumberFormatException e) {
                ui.show_notValidMessage();
            }
        } while (GameID < 0 );
        return GameID;
    }
    public void askNickname() {
        ui.show_insertNicknameMessage();
        try {
            String nick = this.inputController.getUnprocessedData().popInputData();
            setNickname(nick);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ui.show_chosenNickname(this.nickname);
    }

    public void setNickname(String nick){
        this.nickname=nick;
    }
    public void setColor(Color color){
        this.color=color;
    }
    /**
     * The method repeatedly checks for user input until the user confirms they're ready by entering "y".
     * If any other input is received, the method continues to wait for the correct input.

     * Once the user confirms their readiness, the `setAsReady()` method is called to proceed with the next step.
     */
    public void askReadyToStart(GameImmutable model, String nick){
        String answer = null;
        do {
            try {
                ui.show_readyToStart(model,nick);
                try {
                    answer = this.inputController.getUnprocessedData().popInputData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }catch(InputMismatchException e) {
                ui.show_notValidMessage();
            }
        } while (!Objects.equals(answer, "y"));
        System.out.println("GameFlow: askReadyToStart");
        setAsReady(nick);
    }

    /**
     * Sets the client as ready by calling the server methods.
     */
    @Override
    public void setAsReady(String nickname) {
        try {
            clientActions.setAsReady(nickname);

        } catch (IOException e){
            noConnectionError();
        }
    }
    @Override
    public void makeGameStart(String nickname){
        try {
            clientActions.makeGameStart(nickname);

        } catch (IOException e){
            noConnectionError();
        }
    }
    //metodo per chiedere il numero della carta (scelta fronte o retro, o carta 1 o 2)
    private Integer askNum(String message, GameImmutable model){
        String temp;
        int num = -1;
        do {
            try {
                ui.show_askNum(message, model, nickname);
                //System.out.flush();

                try {
                    temp = this.inputController.getUnprocessedData().popInputData();
                    System.out.println("Input received: " + temp);
                    if (ended) return null; //il giocatore non può fare mosse
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                num = Integer.parseInt(temp); //traduco il numero in integer
                if(num < 0 || num>1){
                    ui.show_wrongSelectionMsg();
                }
            } catch (InputMismatchException | NumberFormatException e) {
                ui.show_notValidMessage();
            }
        } while (num < 0 || num>1);
        return num;
    }


    public void askPlaceCards(GameImmutable model, String nickname){
        String temp;
        ui.show_askPlaceCardsMainMsg(model);
        ui.show_alwaysShow(model, nickname);
        int posChosenCard;
        do {
            try {
                ui.show_askNum("Choose which CARD you want to place:", model, nickname);
                temp = this.inputController.getUnprocessedData().popInputData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            posChosenCard = Integer.parseInt(temp); //traduco il numero in integer
            if (posChosenCard < 0 || posChosenCard > 6) {
                ui.show_wrongCardSelMsg();
                posChosenCard = -1;
            }
        }while (posChosenCard<0);

        int rowCell;
        ui.show_askWhichCellMsg(model);
        ui.show_askNum("Choose the ROW of the cell:", model, nickname);
        do {
            try {
                temp = this.inputController.getUnprocessedData().popInputData();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            rowCell = Integer.parseInt(temp); //traduco il numero in integer
            if (rowCell < DefaultValue.BookSizeMin || rowCell > DefaultValue.BookSizeMax) {
                ui.show_wrongCellSelMsg();
                rowCell = -1;
            }
        }while (rowCell<0);

        int colCell=-1;
        ui.show_askNum("Choose the COLUMN of the cell:", model, nickname);
        do {
            try {
                temp = this.inputController.getUnprocessedData().popInputData();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            colCell = Integer.parseInt(temp); //traduco il numero in integer
            if (colCell < DefaultValue.BookSizeMin || colCell > DefaultValue.BookSizeMax) {
                ui.show_wrongCellSelMsg();
                colCell = -1;
            }
        }while (colCell<0);

        placeCardInBook(posChosenCard, rowCell, colCell );
    }


    public void placeCardInBook( int chosenCard, int rowCell, int columnCell ){
        try {
            clientActions.placeCardInBook(chosenCard, rowCell, columnCell);

        } catch (IOException e) {
            noConnectionError();
        }
    }




    public synchronized void askPickCard (GameImmutable model) {
        int pos=0;
        ui.show_PickCardMsg(model); //messaggio "è il tuo turno di pescare una carta"

        // Chiedi all'utente il tipo di carta che vuole pescare
        CardType cardType = askCardType(model);
        boolean drawFromDeck = askDrawFromDeck( model);

        if(!drawFromDeck){
            ui.show_visibleCardsBoard(model, cardType);
            pos= Objects.requireNonNullElse(askNum("\t> Choose [0] for the first card or [1] for the second card:", model), -1);
        }
        PickCardFromBoard(cardType, drawFromDeck, pos);
    }

    public void PickCardFromBoard( CardType cardType, boolean drawFromDeck, int pos){
        try {
            clientActions.PickCardFromBoard(cardType, drawFromDeck, pos);

        } catch (IOException e) {
            noConnectionError();
        }
    }
    public CardType askCardType(GameImmutable model) {
        String temp;
        CardType cardType = null;
        do {
            try {
                // Mostra un messaggio all'utente per chiedere il tipo di carta desiderato ("R" per Resource, "G" per Gold)
                ui.show_askCardType(model, nickname);

                // Ottieni l'input dell'utente
                try {
                    System.out.println("waiting input askCardType");
                    temp = this.inputController.getUnprocessedData().popInputData();
                    System.out.println("input received: "+ temp);
                    if (ended) return null; // Se il gioco è finito, ritorna null
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // Converti l'input dell'utente in un valore di CardType
                cardType = convertToCardType(temp);

            } catch (IllegalArgumentException e) {
                ui.show_notValidMessage();
            }
        } while (cardType == null);
        return cardType;
    }

    private CardType convertToCardType(String cardTypeStr) {
        // Prova a convertire la stringa cardTypeStr in un valore di CardType
        if ("R".equalsIgnoreCase(cardTypeStr)||"r".equalsIgnoreCase(cardTypeStr)) { //player richiede una ResourceCard
            return CardType.ResourceCard;
        } else if ("G".equalsIgnoreCase(cardTypeStr)||"g".equalsIgnoreCase(cardTypeStr)) { //player richiede una GoldCard
            return CardType.GoldCard;
        } else {
            // Se la stringa non corrisponde a nessun valore valido di CardType
            throw new IllegalArgumentException("Invalid card type: " + cardTypeStr);
        }
    }

    public boolean askDrawFromDeck( GameImmutable model) {
        String temp;
        boolean drawFromDeck = false;
        boolean isValidInput = false;

        // Loop finché non viene fornito un input valido
        do {
            try {
                ui.show_askDrawFromDeck(model, nickname);

                // Ottieni l'input dell'utente
                try {
                    //System.out.println("waiting for input askDrawFromDeck ");
                    temp = this.inputController.getUnprocessedData().popInputData();
                    System.out.println("input received: "+ temp);
                    if (ended) return false; // Se il gioco è finito, ritorna false
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // checks the input and convert to boolean
                if (temp.equalsIgnoreCase("yes") || temp.equalsIgnoreCase("y")) {
                    drawFromDeck = true;
                    isValidInput = true;
                } else if (temp.equalsIgnoreCase("no") || temp.equalsIgnoreCase("n")) {
                    isValidInput = true;
                } else {
                    ui.show_notValidMessage();
                }

            } catch (Exception e) {
                ui.show_notValidMessage();
            }
        } while (!isValidInput); // Continua a chiedere finché l'input non è valido
        return drawFromDeck;
    }

    @Override
    public void settingGame(int numPlayers, int GameID, String nick){
        //System.out.println("GameFlow: settingGame");
        try {
            clientActions.settingGame(numPlayers, GameID, nick);
        } catch (IOException e) {
            noConnectionError();
        }
    }
  /*  @Override
    public void makeGameStart(String nick){
        try {
            clientActions.makeGameStart(nick);
        } catch (IOException e) {
            noConnectionError();
        }
    }*/

    @Override
    public void setInitialCard(int index, String nickname) {
        try {
            clientActions.setInitialCard(index, nickname);

        } catch (IOException e) {
            noConnectionError();
        }
    }

    @Override
    public void setGoalCard(int index, String nickname) throws IOException {
        try {
            clientActions.setGoalCard(index, nickname);

        } catch (IOException e) {
            noConnectionError();
        } catch (NotPlayerTurnException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void wrongChooseCard(GameImmutable model){
        events.add(model, EventType.CARD_PLACED_NOT_CORRECT);

    }


    /* METODI CHE IL SERVER HA RICEVUTO DAL CLIENT */
    /**
     * A player has joined the game
     * @param gameModel game model {@link GameImmutable}
     */
    @Override
    public void playerJoined(GameImmutable gameModel, String nickname, Color playerColor) {
        setColor(playerColor);
        events.add(gameModel, EventType.PLAYER_JOINED);
        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
       // ui.show_playerJoined(gameModel, nickname);
        ui.addImportantEvent("[EVENT]: Player " + nickname + " joined the game!");

    }

    /**
     * Handles the event when a player leaves the game passing a different message to the event
     * whether a player is connected or not
     *
     * @param gameImmutable the immutable state of the game
     * @param nickname the nickname of the player who left
     * @throws RemoteException if there is an issue with remote communication
     */
    @Override
    public void playerLeft(GameImmutable gameImmutable, String nickname) throws RemoteException {
         ui.addImportantEvent("[EVENT]: Player " + nickname + " decided to leave the game!");

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

    /**
     * A player wanted to join a game but the nickname is already in
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player triedToJoin) throws RemoteException {
        events.add(null, EventType.NICKNAME_ALREADY_IN);
    }

   @Override
    public void playerReady(GameImmutable model, String nickname)throws RemoteException{

        events.add(model, EventType.PLAYER_READY);
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

        //TODO quando aggiungiamo la disconnessione fai metodo RESET gioco
    }
    @Override
    public void requireNumPlayersGameID(GameImmutable model)throws RemoteException {
        int numPlayers=askNumPlayers();
        int GameID= askGameID();
        settingGame(numPlayers, GameID, nickname);

    }


    /** Prompts the user to choose between the front or back of the available Initial Cards.
     *   The method continues prompting the user for a valid choice until one is provided.
     * If an invalid choice is made, an error message is displayed and the user is prompted again.
     *  Once a valid choice is made, the selected card side (front or back)
     *  is sent to the server using `clientActions.setInitialCard(index)`.
     *
     * @param model       is the game model
     * @throws IOException //GESTIRE
     * @throws FileReadException //GESTIRE
     */
    @Override
    public void requireInitialReady(GameImmutable model, int indexPlayer) throws IOException, FileReadException {

        ui.show_temporaryInitialCards(model, indexPlayer);
        Integer index;
        do {
            index = Objects.requireNonNullElse(askNum("\t> Insert number:", model), -1);
            if (ended) return;
            if (index < 0 || index >= 2) {
                index = null;
            }
        } while (index == null);
        setInitialCard(index, nickname); //manda l'indice selezionato per far risalire al Controller la InitialCard selezionata
    }


    /**
     * This method requires the user to choose
     *  between two Objective Cards by displaying them, and then asks the user to select one by entering either 0 or 1.
     *  If the user's selection is invalid, an error message is displayed and the user is prompted again until
     *  a valid choice is made. Once a valid choice is made, the selected card is sent to the server using
     *  clientActions.setGoalCard(index)`.
     * @param model is the game model
     * @throws RemoteException IF THERE IS A PROBLEM IN THE NETWORK
     */
    @Override
    public void requireGoalsReady(GameImmutable model, int indexPlayer) throws RemoteException {
        //ui.show_whichObjectiveCards();
        ui.show_ObjectiveCards(model, indexPlayer);
        Integer index;
        do {
            index = Objects.requireNonNullElse(askNum("\t> Insert [0] for the first card or [1] for the second card:", model), -1);
            if (ended) return;
            if (index < 0 || index >= 2) {
                index = null;
            }
        } while (index == null);
        try {
            setGoalCard(index, nickname); //manda l'indice selezionato per far risalire al Controller la ObjectiveCard selezionata
            ui.show_personalObjective(model, indexPlayer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void cardsReady(GameImmutable model) throws RemoteException{
        events.add(model, EventType.CARDS_READY);
    }

    /**
     * A card has been placed on the book
     * @param model is the game model
     * @throws RemoteException if there is  problem in the network
     */
    @Override
    public void cardPlaced(GameImmutable model) throws RemoteException {
       // ui.show_cardPlacedMsg(model);
        events.add(model, EventType.CARD_PLACED);
    }

    @Override
    public void pointsAdded(GameImmutable model) throws RemoteException {
        ui.show_pointsAddedMsg(model, nickname);
    }


    @Override
    public void cardDrawn(GameImmutable model) throws RemoteException {

        events.add(model, EventType.CARD_DRAWN);
    }

    @Override
    public void nextTurn(GameImmutable model) throws RemoteException {
        events.add(model, EventType.NEXT_TURN);
        this.inputController.getUnprocessedData().popAllData();
    }

    @Override
    public void lastCircle(GameImmutable model) throws RemoteException {
        ui.addImportantEvent("LAST CIRCLE BEGIN!");
    }



    @Override
    public void joinGame(String nickname )  {
        ui.show_joiningToGameMsg(nickname, color);
        try {
            clientActions.joinGame(nickname);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }


    @Override
    public void leave(String nick) {
        try {
            clientActions.leave(nick);
        } catch (IOException | NotBoundException e) {
            noConnectionError();
        }
    }

    @Override
    public void playerDisconnected(GameImmutable model, String nick) throws RemoteException {
        //TODO
    }

    /**
     * Throws a message if there is an error within connection
     */
    @Override
    public void noConnectionError() {
        ui.show_noConnectionError();
    }


    @Override
    public void ping()  {
    //TODO
    }

    //gestisce i messaggi inviati (pubblici o privati)
    @Override
    public void sentMessage(GameImmutable model, Message msg) throws RemoteException {
        //mostra il messaggio solo se è destinato a tutti(*), al destinatario corrente o è stato mandato dal giocatore corrente
        if (msg.whoIsReceiver().equals("*") || msg.whoIsReceiver().equalsIgnoreCase(nickname) || msg.getSender().getNickname().equalsIgnoreCase(nickname)) {
            ui.addMessage(msg, model);
            events.add(model, EventType.SENT_MESSAGE);
            msg.setText("[PRIVATE]: " + msg.getText());
        }
    }


    //client invia i messaggi al server
    @Override
    public void sendMessage(Message msg) {
        try {
            clientActions.sendMessage(msg);
            System.out.println("Message sent: " + msg.getText());
        } catch (RemoteException e) {
            noConnectionError();
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }





}
