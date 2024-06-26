package it.polimi.ingsw.view.flow;


import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.game.GameStatus;
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

import static it.polimi.ingsw.view.events.EventType.*;
import static it.polimi.ingsw.view.events.EventType.PLAYER_RECONNECTED;

/**
 * Manages the flow of the game and interaction between client and server.
 */
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

    private String msgNotCorrect;

    /**
     * The last player that reconnected
     */
    private String lastPlayerReconnected;


    /**
     * Nickname of the disconnected player
     */
    private String playerDisconnected =" ";

    /**
     * FileDisconnection {@link FileDisconnection} to handle the disconnection
     */
    private final FileDisconnection fileDisconnection;




    /**
     * Constructor of the class, based on the connection type it creates the clientActions and initializes the UI,
     * the FileDisconnection, the InputReader and the InputController
     * @param connectionType the connection type
     */
    public GameFlow(ConnectionType connectionType) {
        switch (connectionType) {
            case SOCKET -> clientActions = new ClientSocket(this);
            case RMI -> clientActions = new ClientRMI(this);

        }
        ui = new TUI();

        importantEvents = new ArrayList<>();
        nickname = "";

        this.inputReader = new InputTUI();
        this.inputController = new InputController(this.inputReader.getBuffer(), this);
        fileDisconnection = new FileDisconnection();

        new Thread(this).start();

    }

    /**
     * Constructor for initializing the game flow with GUI.
     *
     * @param guiApplication The GUI application instance.
     * @param connectionType The type of connection
     */
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
        fileDisconnection = new FileDisconnection();
        new Thread(this).start();

    }

    /**
     * Main execution thread for managing game events and UI updates.
     */
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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Handles game status when it's in the WAIT state.
     *
     * @param event The event triggering the status change.
     * @throws IOException          If there's an IO issue.
     * @throws InterruptedException If the thread is interrupted.
     */
    public void statusWait(Event event) throws IOException, InterruptedException{
        String nicknameLastPlayer = event.getModel().getLastPlayer().getNickname();
        switch (event.getType()) {
            case PLAYER_JOINED -> {
                ui.show_playerJoined(event.getModel(), this.nickname, this.color);
                if (nicknameLastPlayer.equals(nickname)) {
                    saveGameId(fileDisconnection, nickname, event.getModel().getGameId());
                    askReadyToStart(event.getModel(), nickname);
                }
            }
            case PLAYER_READY -> {
                ui.show_youAreReady(event.getModel());
                ui.show_askForChat(event.getModel(), nickname);
            }
            case CARDS_READY -> makeGameStart(nickname);
        }
    }

    /**
     * Handles game status when it's in the RUNNING or LAST_CIRCLE state.
     *
     * @param event The event triggering the status change.
     * @throws IOException          If there's an IO issue.
     * @throws InterruptedException If the thread is interrupted.
     */
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
                  }
                else{
                    ui.show_WaitTurnMsg(event.getModel(), nickname);
                }
            }
            case NEXT_TURN->{
                ui.show_nextTurnMsg(event.getModel());
                if(event.getModel().getCurrentPlayer().getNickname().equals(nickname)){
                    ui.show_CurrentTurnMsg(event.getModel());
                    askPlaceCards(event.getModel(), nickname);
                }
                else{
                    ui.show_WaitTurnMsg(event.getModel(), nickname);
                }
            }
            case PLAYER_RECONNECTED -> {
                ui.show_PlayerReconnectedMsg(event.getModel(), nickname, lastPlayerReconnected);
                if(lastPlayerReconnected.equals(nickname)) {
                    this.inputController.setPlayer(event.getModel().getPlayerByNickname(nickname));
                    this.inputController.setGameID(event.getModel().getGameId());
                }

            }

            case CARD_PLACED ->{
                ui.show_cardPlacedMsg(event.getModel());
                ui.show_pointsAddedMsg(event.getModel(), nickname);

                if (event.getModel().getNicknameCurrentPlaying().equals(nickname)){
                    askPickCard(event.getModel());
                }else
                    ui.closeWaitPopUp();

            }
            case SENT_MESSAGE -> ui.show_sentMessage(event.getModel(), nickname);

            case CARD_PLACED_NOT_CORRECT -> {
                ui.show_playerHasToChooseAgain(event.getModel(), nickname, this.msgNotCorrect);
                askPlaceCards(event.getModel(), nickname);
            }
            case CARD_DRAWN -> ui.show_cardDrawnMsg(event.getModel(), nickname);

        }

    }


    /**
     * Handles scenarios when a player is not correctly added to a game
     *
     * @param event The event triggering the status change.
     * @throws NotBoundException   If there's an issue with binding.
     * @throws IOException         If there's an IO issue.
     * @throws InterruptedException If the thread is interrupted.
     */
    public void statusNotInAGame(Event event) throws NotBoundException, IOException, InterruptedException {
        switch (event.getType()) {
            case BACK_TO_MENU -> {
                if(ended)
                    ui.show_returnToMenuMsg();
                else{
                        askNickname();
                        System.out.println("Nickname correctly asked and returned to GameFlow\n");
                        joinGame(nickname);
                }
            }

            case NICKNAME_ALREADY_IN -> {
                System.out.println("in case NICKNAME_ALREADY_IN \n ");
                    nickname = null;
                    Color.addColor(this.color);
                    this.color = null;

                    events.add(null, EventType.BACK_TO_MENU);
                    ui.addImportantEvent("ERROR: Nickname already used!");
            }

            case NICKNAME_TO_RECONNECT -> {
                System.out.println("GameFlow- in case NICKNAME_TO_RECONNECT \n ");
                if (askingForReconnection()){ //Se il giocatore sta chiedendo una riconnessione
                    reconnect(nickname, fileDisconnection.getGameId(nickname)); //chiama la riconnessione
                } else {//NON HA SCHIACCIATO YES  in askingForReconnection
                    nickname = null;
                    Color.addColor(this.color);
                    this.color = null;
                    events.add(null, ERROR_RECONNECTING);
                    //joinGame(nickname);

                    //events.add(null, EventType.BACK_TO_MENU);
                }
            }

            case GAME_FULL -> {
                nickname = null;
                this.color=null;
                ui.addImportantEvent("ERROR: Game is Full, you can't play!");
            }

            case GENERIC_ERROR, ERROR_RECONNECTING -> {
                nickname = null;
                ui.show_returnToMenuMsg();
                try {
                    this.inputController.getUnprocessedData().popInputData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(null, EventType.BACK_TO_MENU);
            }
        }

    }

    /**
     * If a Client connected and inserted a name that is already in the game, we ask if it's a Reconnection
     * @return true if he's trying to reconnect
     *
     */
    public boolean askingForReconnection()  {
        String isReconnection; // salvo l'input del Client (se sta tentando una riconnessione oppure no)
        ui.show_askForReconnection();
        this.inputController.getUnprocessedData().popAllData();
        try {
            isReconnection = this.inputController.getUnprocessedData().popInputData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return isReconnection.equalsIgnoreCase("YES"); //true se è proprio YES
    }

    /**
     * Handles game status when it has ended.
     *
     * @param event The event object representing the end of the game.
     * @throws NotBoundException   If a binding exception occurs.
     * @throws IOException         If an I/O error occurs.
     */
    public void statusEnded(Event event) throws NotBoundException, IOException {
        switch (event.getType()) {
            case GAME_ENDED -> {
                ui.show_gameEnded(event.getModel());
                this.inputController.getUnprocessedData().popAllData();
                try {
                    this.inputController.getUnprocessedData().popInputData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                this.leave(nickname);
                this.playerLeftForGameEnded();
            }
        }

    }

    /**
     * Notifies that the player has left the game due to game end.
     */
    public void playerLeftForGameEnded(){
        ended = true;

        ui.resetImportantEvents();
        events.add(null,EventType.BACK_TO_MENU);

        this.inputController.setPlayer(null);
        this.inputController.setGameID(null);
    }

    /**
     * Checks if the game has ended.
     *
     * @return true if the game has ended, false otherwise.
     */
    public boolean isEnded(){
        return ended;
    }

    /**
     * Sets the ended status of the game.
     *
     * @param ended true to set the game as ended, false otherwise.
     */
    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    /**
     * Prompts the user to enter the number of players until a valid input within the specified range is received.
     *
     * @return The number of players chosen by the user.
     */
    private int askNumPlayers() {
        String temp;
        int numPlayers;
        do {
            try {
                ui.show_askNumPlayersMessage();

                try {
                    temp = this.inputController.getUnprocessedData().popInputData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                numPlayers = Integer.parseInt(temp);

                if ( numPlayers < DefaultValue.minNumOfPlayer || numPlayers > DefaultValue.MaxNumOfPlayer) {
                    ui.show_notValidMessage();
                    numPlayers = -1;
                }
            } catch (NumberFormatException e) {
                ui.show_notValidMessage();
                numPlayers = -1;
            }
        } while (numPlayers == -1);

        return numPlayers;
    }

    /**
     * Prompts the user to enter the game ID until a valid positive integer input is received.
     *
     * @return The game ID entered by the user.
     */
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
                GameID = Integer.parseInt(temp);
                if(GameID < 0)
                    ui.show_notValidMessage();
            } catch ( NumberFormatException e) {
                ui.show_notValidMessage();
            }
        } while (GameID < 0 );
        return GameID;
    }

    /**
     * Prompts the user to enter their nickname and sets it.
     */
    public void askNickname() {
        ui.show_insertNicknameMessage();
        try {
            String nick = this.inputController.getUnprocessedData().popInputData();
            setNickname(nick);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } //else this.joinUnableNicknameAlreadyIn(model.getPlayerByNickname(nick), model);

        ui.show_chosenNickname(this.nickname);
    }

    /**
     * Sets the nickname for the player.
     *
     * @param nick The nickname to set.
     */
    public void setNickname(String nick){ //nickname non deve essere settato prima di aver chiesto se si sta riconnettendo!! Altrimenti sarà sempre4 GIA presente!!
        this.nickname=nick;
        System.out.println("GameFlow- setNickname: nickname setted");
    }

    /**
     * Sets the color for the player.
     *
     * @param color The color to set.
     */
    public void setColor(Color color){
        this.color=color;
    }
    /**
     * The method repeatedly checks for user input until the user confirms they're ready by entering "y".
     * If any other input is received, the method continues to wait for the correct input.
     * Once the user confirms their readiness, the `setAsReady()` method is called to proceed with the next step.
     *
     * @param model The game model.
     * @param nick The nickname of the player.
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
     *
     * @param nickname The nickname of the player.
     */
    @Override
    public void setAsReady(String nickname) {
        try {
            clientActions.setAsReady(nickname);

        } catch (IOException e){
            noConnectionError();
        }
    }

    /**
     * Initiates the game start for the player.
     *
     * @param nickname The nickname of the player.
     */
    @Override
    public void makeGameStart(String nickname){
        try {
            clientActions.makeGameStart(nickname);
        } catch (IOException e){
            noConnectionError();
        }
    }

    /**
     * Prompts the user to enter a number until a valid input within a specified range is received.
     * Returns null if the game has ended.
     *
     * @param message The message to display when asking for input.
     * @param model The game model.
     * @return The number entered by the user, or null if the game has ended.
     */
    private Integer askNum(String message, GameImmutable model){
        String temp;
        int num = -1;
        do {
            try {
                ui.show_askNum(message, model, nickname);

                try {
                    temp = this.inputController.getUnprocessedData().popInputData();
                    System.out.println("Input received: " + temp);
                    if (ended) return null;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                num = Integer.parseInt(temp);
                if(num < 0 || num>1){
                    ui.show_wrongSelectionMsg();
                }
            } catch (InputMismatchException | NumberFormatException e) {
                ui.show_notValidMessage();
            }
        } while (num < 0 || num>1);
        return num;
    }

    /**
     * Prompts the user to place a card in a book by entering the card position and cell coordinates.
     *
     * @param model The game model.
     * @param nickname The nickname of the player.
     */
    public void askPlaceCards(GameImmutable model, String nickname){
        String temp;
        ui.show_askPlaceCardsMainMsg(model);
        ui.show_alwaysShow(model, nickname);

        ui.show_askNum("Choose which CARD you want to place:", model, nickname);
        int posChosenCard=getIndex(true);

        ui.show_askWhichCellMsg(model);
        ui.show_askNum("Choose the ROW of the cell:", model, nickname);
        int rowCell = getIndex(false);


        ui.show_askNum("Choose the COLUMN of the cell:", model, nickname);
        int colCell = getIndex(false);

        placeCardInBook(posChosenCard, rowCell, colCell );
    }

    /**
     * Gets the index for either a card or a cell position based on user input.
     *
     * @param card boolean indicating if the index is for a card (true) or for a cell (false).
     * @return the index entered by the user.
     */
    private int getIndex(boolean card) {
        String temp;
        int min, max;
        int index;
        if(card){
            min=0;
            max=6;
        }else{
            min=DefaultValue.BookSizeMin ;
            max= DefaultValue.BookSizeMax;
        }
        do {
            try {
                temp = this.inputController.getUnprocessedData().popInputData();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            index = Integer.parseInt(temp);
            if (index < min || index > max) {
                ui.show_wrongSelMsg(min, max);
                index = -1;
            }
        }while (index<0);
        return index;
    }

    /**
     * Sends a request to the server to place a card in a book with specified parameters.
     *
     * @param chosenCard The index of the chosen card.
     * @param rowCell The row of the cell in which to place the card.
     * @param columnCell The column of the cell in which to place the card.
     */
    public void placeCardInBook( int chosenCard, int rowCell, int columnCell ){
        try {
            clientActions.placeCardInBook(chosenCard, rowCell, columnCell);

        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * Prompts the user to pick a card from the board, either from visible cards or the deck.
     * Calls `PickCardFromBoard()` method with the chosen parameters.
     *
     * @param model The game model.
     */
    public void askPickCard (GameImmutable model) {
        int pos=0;
        ui.show_PickCardMsg(model);

        CardType cardType = askCardType(model);
        boolean drawFromDeck = askDrawFromDeck( model);

        if(!drawFromDeck){
            ui.show_visibleCardsBoard(model, cardType);
            pos= Objects.requireNonNullElse(askNum("\t> Choose [0] for the first card or [1] for the second card:", model), -1);
        }
        PickCardFromBoard(cardType, drawFromDeck, pos);
    }

    /**
     * Sends a request to the server to pick a card from the board with specified parameters.
     *
     * @param cardType The type of card to pick (ResourceCard or GoldCard).
     * @param drawFromDeck True if picking from the deck, false if picking from visible cards.
     * @param pos The position of the card to pick (0 or 1).
     */
    public void PickCardFromBoard( CardType cardType, boolean drawFromDeck, int pos){
        try {
            clientActions.PickCardFromBoard(cardType, drawFromDeck, pos);

        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * Prompts the user to select the type of card they want to draw (ResourceCard or GoldCard).
     *
     * @param model The game model.
     * @return The CardType selected by the user.
     */
    public CardType askCardType(GameImmutable model) {
        String temp;
        CardType cardType = null;
        do {
            try {
                ui.show_askCardType(model, nickname);
                try {
                    System.out.println("waiting input askCardType");
                    temp = this.inputController.getUnprocessedData().popInputData();
                    System.out.println("input received: "+ temp);
                    if (ended) return null;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cardType = convertToCardType(temp);

            } catch (IllegalArgumentException e) {
                ui.show_notValidMessage();
            }
        } while (cardType == null);
        return cardType;
    }

    /**
     * Converts a string representation of card type ("R" for ResourceCard, "G" for GoldCard) to CardType enum.
     *
     * @param cardTypeStr The string representation of card type.
     * @return The corresponding CardType enum.
     * @throws IllegalArgumentException If the string does not match any valid CardType.
     */
    private CardType convertToCardType(String cardTypeStr) {
        if ("R".equalsIgnoreCase(cardTypeStr)||"r".equalsIgnoreCase(cardTypeStr)) { //player richiede una ResourceCard
            return CardType.ResourceCard;
        } else if ("G".equalsIgnoreCase(cardTypeStr)||"g".equalsIgnoreCase(cardTypeStr)) { //player richiede una GoldCard
            return CardType.GoldCard;
        } else {
            throw new IllegalArgumentException("Invalid card type: " + cardTypeStr);
        }
    }

    /**
     * Prompts the user to decide whether to draw a card from the deck.
     *
     * @param model The game model.
     * @return True if the user chooses to draw from the deck, false otherwise.
     */
    public boolean askDrawFromDeck( GameImmutable model) {
        String temp;
        boolean drawFromDeck = false;
        boolean isValidInput = false;
        do {
            try {
                ui.show_askDrawFromDeck(model, nickname);
                try {
                    temp = this.inputController.getUnprocessedData().popInputData();
                    System.out.println("input received: "+ temp);
                    if (ended) return false;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

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
        } while (!isValidInput);
        return drawFromDeck;
    }

    /**
     * Sends a request to the server to set up the game with specified parameters.
     *
     * @param numPlayers The number of players in the game.
     * @param GameID The ID of the game.
     * @param nick The nickname of the player.
     */
    @Override
    public void settingGame(int numPlayers, int GameID, String nick){
        System.out.println("GameFlow: settingGame");
        try {
            clientActions.settingGame(numPlayers, GameID, nick);
        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * Sends a request to the server to set the initial card for the player.
     *
     * @param index The index of the initial card.
     * @param nickname The nickname of the player.
     */
    @Override
    public void setInitialCard(int index, String nickname) {
        try {
            clientActions.setInitialCard(index, nickname);

        } catch (IOException e) {
            noConnectionError();
        }
    }

    /**
     * Sends a request to the server to set the goal card for the player.
     *
     * @param index The index of the goal card.
     * @param nickname The nickname of the player.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Notifies the user that the card chosen is not correct and adds an event to the game events.
     *
     * @param model The game model.
     * @param msg The message indicating the reason for the wrong card choice.
     */
    @Override
    public void wrongChooseCard(GameImmutable model,String msg){
        this.msgNotCorrect=msg;
        events.add(model, EventType.CARD_PLACED_NOT_CORRECT);

    }



    /**
     * A player has joined the game
     * @param gameModel game model {@link GameImmutable}
     */
    @Override
    public void playerJoined(GameImmutable gameModel, String nickname, Color playerColor) {
        System.out.println("GameFlow- in playerJoined--> EventType.PLAYER_JOINED");
        setColor(playerColor);
        events.add(gameModel, EventType.PLAYER_JOINED);
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
        System.out.println("In GameFlow - Player Left \n ");
         ui.addImportantEvent("[EVENT]: Player " + nickname + " decided to leave the game!");
         gameImmutable.getPlayerByNickname(nickname).setConnected(false); //aggiunto

    }

    /**
     * Signals that a player attempted to join a game, but the game was already full.
     *
     * @param wantedToJoin The player who attempted to join.
     * @param gameModel The game model {@link GameImmutable}.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameImmutable gameModel) throws RemoteException {
        events.add(null, EventType.GAME_FULL);
    }

    /**
     * Signals that a player attempted to join a game with a nickname that is already in use.
     *
     * @param triedToJoin The player who attempted to join.
     * @param gameModel The game model {@link GameImmutable}.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player triedToJoin, GameImmutable gameModel) throws RemoteException {
            events.add(null, EventType.NICKNAME_ALREADY_IN);
            System.out.println("in joinUnableNicknameAlreadyIn - CONNESSO --> nickname già presente \n");
    }

    /**
     * Signals that a player attempted to reconnect to a game using an existing nickname.
     *
     * @param triedToJoin The player who attempted to reconnect.
     * @param gameModel The game model {@link GameImmutable}.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public void AskForReconnection(Player triedToJoin, GameImmutable gameModel) throws RemoteException{
        System.out.println("in joinUnableNicknameAlreadyIn - NON CONNESSO --> chiama askReconnection \n");
        this.nickname=triedToJoin.getNickname();
        events.add(null, EventType.NICKNAME_TO_RECONNECT);
    }

    /**
     * Signals that a player is ready to start the game.
     *
     * @param model The game model {@link GameImmutable}.
     * @param nickname The nickname of the player who is ready.
     * @throws RemoteException If there is an issue with remote communication.
     */
   @Override
    public void playerReady(GameImmutable model, String nickname)throws RemoteException{
        events.add(model, EventType.PLAYER_READY);
    }

    /**
     * Signals that the game has started.
     *
     * @param gameImmutable The game model {@link GameImmutable}.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public void gameStarted(GameImmutable gameImmutable) throws RemoteException {
        events.add(gameImmutable, EventType.GAME_STARTED);
    }

    /**
     * Signals that the game has ended.
     *
     * @param gameImmutable The game model {@link GameImmutable}.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public void gameEnded(GameImmutable gameImmutable) throws RemoteException {
        events.add(gameImmutable, EventType.GAME_ENDED);
        ended = true;
        ui.show_gameEnded(gameImmutable);
        resetGameId(fileDisconnection, gameImmutable);
    }

    /**
     * Requests the number of players and game ID to set up the game.
     *
     * @param model The game model {@link GameImmutable}.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public void requireNumPlayersGameID(GameImmutable model)throws RemoteException {
        System.out.println("GameFlow- requireNumPlayersGameID ");
        int numPlayers=askNumPlayers();
        int GameID= askGameID();
        settingGame(numPlayers, GameID, nickname);

    }

    /**
     * Prompts the user to choose between the front or back of the available Initial Cards.
     * The method continues prompting the user for a valid choice until one is provided.
     * If an invalid choice is made, an error message is displayed and the user is prompted again.
     * Once a valid choice is made, the selected card side (front or back)
     * is sent to the server using `clientActions.setInitialCard(index)`.
     *
     * @param model The game model {@link GameImmutable}.
     * @param indexPlayer The index of the player making the initial card selection.

     */
    @Override
    public void requireInitialReady(GameImmutable model, int indexPlayer) {

        ui.show_temporaryInitialCards(model, indexPlayer);
        Integer index;
        do {
            index = Objects.requireNonNullElse(askNum("\t> Insert number:", model), -1);
            if (ended) return;
            if (index < 0 || index >= 2) {
                index = null;
            }
        } while (index == null);
        setInitialCard(index, nickname);
    }

    /**
     * Requires the user to choose between two Objective Cards by displaying them,
     * and then asks the user to select one by entering either 0 or 1.
     * If the user's selection is invalid, an error message is displayed and the user is prompted again until
     * a valid choice is made. Once a valid choice is made, the selected card is sent to the server using
     * {@code clientActions.setGoalCard(index)}.
     *
     * @param model The game model {@link GameImmutable}.
     * @param indexPlayer The index of the player making the choice.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void requireGoalsReady(GameImmutable model, int indexPlayer) throws RemoteException {
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
            setGoalCard(index, nickname);
            ui.show_personalObjective(model, indexPlayer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Signals that the player's cards are ready.
     *
     * @param model The game model {@link GameImmutable}.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void cardsReady(GameImmutable model) throws RemoteException{
        events.add(model, EventType.CARDS_READY);
    }

    /**
     * Signals that a card has been successfully placed.
     *
     * @param model The game model {@link GameImmutable}.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void cardPlaced(GameImmutable model) throws RemoteException {
        events.add(model, EventType.CARD_PLACED);
    }

    /**
     * Signals that points have been added to the player's score.
     *
     * @param model The game model {@link GameImmutable}.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void pointsAdded(GameImmutable model) throws RemoteException {
        ui.show_pointsAddedMsg(model, nickname);
    }

    /**
     * Signals that a card has been drawn.
     *
     * @param model The game model {@link GameImmutable}.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void cardDrawn(GameImmutable model) throws RemoteException {
        events.add(model, EventType.CARD_DRAWN);
    }

    /**
     * Signals the start of the next turn in the game.
     *
     * @param model The game model {@link GameImmutable}.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void nextTurn(GameImmutable model) throws RemoteException {
        events.add(model, EventType.NEXT_TURN);
        this.inputController.getUnprocessedData().popAllData();
    }

    /**
     * Signals the beginning of the last circle of the game.
     *
     * @param model The game model {@link GameImmutable}.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void lastCircle(GameImmutable model) throws RemoteException {
        ui.addImportantEvent("LAST CIRCLE BEGIN!");
    }

    /**
     * Initiates the process for a player to join a game.
     *
     * @param nickname The nickname of the player joining the game.
     */
    @Override
    public void joinGame(String nickname)  {
        ui.show_joiningToGameMsg(nickname,color);
        try {
            clientActions.joinGame(nickname);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * Initiates the process for a player to leave the game.
     *
     * @param nick The nickname of the player leaving.
     */
    @Override
    public void leave(String nick) {
        try {
            clientActions.leave(nick);
        } catch (IOException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * Signals that a player has been disconnected from the game.
     *
     * @param model The game model {@link GameImmutable}.
     * @param nick The nickname of the player who disconnected.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void playerDisconnected(GameImmutable model, String nick) {
        ui.addImportantEvent("Player " + nick + " has just disconnected");
        this.setPlayerDisconnected(nick); //to save the nickname of the disconnected player

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        if (model.getStatus().equals(GameStatus.WAIT)) {
            Color colorP = model.getPlayerByNickname(nick).getPlayerColor();
            ui.show_playerJoined(model, nickname, colorP );
        }
    }

    /**
     * Signals that a player has successfully reconnected to the game.
     *
     * @param model The game model {@link GameImmutable}.
     * @param nickPlayerReconnected The nickname of the player who has reconnected.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void playerReconnected(GameImmutable model, String nickPlayerReconnected) throws RemoteException{
        System.out.println("GameFlow - playerReconnected ()");
        this.setPlayerDisconnected(" ");
        lastPlayerReconnected = nickPlayerReconnected;
        if(lastPlayerReconnected.equals(nickname)) {
            events.add(model, PLAYER_RECONNECTED);
        }
        ui.addImportantEvent("[EVENT]: Player reconnected!");
    }


    /**
     * Initiates the process for a player to reconnect to a specific game.
     *
     * @param nick The nickname of the player.
     * @param idGame The ID of the game to reconnect to.
     * @throws IOException If there is an I/O issue.
     * @throws InterruptedException If the thread is interrupted.
     * @throws NotBoundException If the game is not bound.
     */
    @Override
    public void reconnect(String nick, int idGame) throws IOException, InterruptedException, NotBoundException{
        System.out.println("in GameFlow --> RECONNECT()");
        //ui.show_joiningToGameMsg(nick, color);
        try {
            clientActions.reconnect(nickname, idGame);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * Signals an error during the reconnection process.
     *
     * @param why The reason why the error occurred.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void errorReconnecting(String why) throws RemoteException{
        ui.show_failedReconnectionMsg(why);
        try {
            this.inputController.getUnprocessedData().popInputData(); //rimuovo il dato non elaborato dal buffer
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        events.add(null, ERROR_RECONNECTING);

    }

    /**
     * Saves the latest game ID.
     *
     * @param fileDisconnection The file to write the game ID to.
     * @param nick The nickname of the player.
     * @param gameId The ID of the game to save.
     */
    public void saveGameId(FileDisconnection fileDisconnection, String nick, int gameId) {
        fileDisconnection.setLastGameId(nick, gameId);
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

    }

    /**
     * Signals that only one player is currently connected to the game.
     *
     * @param gameModel The game model {@link GameImmutable}.
     * @param secondsToWaitUntilGameEnded The number of seconds to wait until the game ends.
     */
    @Override
    public void onlyOnePlayerConnected(GameImmutable gameModel, int secondsToWaitUntilGameEnded) {
        ui.show_onlyOnePlayerConnected(secondsToWaitUntilGameEnded);
    }

    /**
     * Sends a message from the server to the client.
     *
     * @param model The game model {@link GameImmutable}.
     * @param msg The message to send.
     * @throws RemoteException If there is a network issue.
     */
    @Override
    public void sentMessage(GameImmutable model, Message msg) throws RemoteException {
        if (msg.whoIsReceiver().equals("*") || msg.whoIsReceiver().equalsIgnoreCase(nickname) || msg.getSender().getNickname().equalsIgnoreCase(nickname)) {
            ui.addMessage(msg, model);
            events.add(model, EventType.SENT_MESSAGE);
            msg.setText(msg.getText());
        }
    }

    /**
     * Sends a message from the client to the server.
     *
     * @param msg The message to send.
     */
    @Override
    public void sendMessage(Message msg) throws IOException {
        try {
            clientActions.sendMessage(msg);
            System.out.println("Message sent: " + msg.getText());
        } catch (RemoteException e) {
            noConnectionError();
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    /**
     * Getter of getPlayerDisconnected()
     * @return the disconnected player
     */
    public String getPlayerDisconnected() {
        return playerDisconnected;
    }

    /**
     * Sets the nickname of disconnected player
     */
    public void setPlayerDisconnected(String playerDisconnected) {
        this.playerDisconnected = playerDisconnected;
    }





}
