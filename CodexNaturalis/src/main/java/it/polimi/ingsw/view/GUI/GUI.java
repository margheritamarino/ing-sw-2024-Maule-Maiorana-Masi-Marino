package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.NoPlayersException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.view.GUI.controllers.*;
import it.polimi.ingsw.view.GUI.scenes.SceneType;
import it.polimi.ingsw.view.Utilities.InputGUI;
import it.polimi.ingsw.view.Utilities.UI;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import java.util.ArrayList;


/**
 * GUI class.
 * This class is the GUI implementation of the UI abstract class and it manages all the GUI-related operations.
 */
public class GUI extends UI {

    private GUIApplication guiApplication;
    private  InputGUI inputGUI;

    private String nickname;
    boolean showedPublisher = false;
    public boolean alreadyShowedLobby = false;

    /**
     * Constructor of the class.
     *
     * @param guiApplication the GUI application
     * @param inputGUI the input reader
     */
    public GUI(GUIApplication guiApplication, InputGUI inputGUI) {
        this.guiApplication = guiApplication;
        this.inputGUI = inputGUI;
        nickname = null;
        init();
    }


    /**
     * The init method is used to initialize the GUI.
     */
    @Override
    public void init() {
        eventsToShow = new ArrayList<>();
    }

    /**
     * Executes a Runnable on the JavaFX Application Thread using Platform.runLater().
     *
     * @param r The Runnable to execute.
     */
    public void callPlatformRunLater(Runnable r) {
        Platform.runLater(r);
    }

    /**
     * Shows the Publisher scene and then displays the insertNicknameMessage after a delay.
     */
    @Override
    public void show_publisher()  {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.PUBLISHER));

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(DefaultValue.time_publisher_showing_seconds));
        pauseTransition.setOnFinished(event -> {
            showedPublisher = true;
            this.show_insertNicknameMessage();
        });
        pauseTransition.play();
    }

    /**
     * Shows the insertNicknameMessage scene if the Publisher scene has been shown.
     */
    @Override
    public void show_insertNicknameMessage() {
        if(showedPublisher) {
            callPlatformRunLater(() -> this.guiApplication.setInputReaderGUItoAllControllers(this.inputGUI));
            callPlatformRunLater(() -> this.guiApplication.createNewWindowWithStyle());
            callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.NICKNAME));

        }
    }

    /**
     * Shows a popup with nickname information.
     *
     * @param nick The nickname to display.
     * @param text The information text to display.
     */
    public void show_popupInfoAndNickname(String nick, String text) {
        callPlatformRunLater(() -> ((NicknamePopUpController) this.guiApplication.getController(SceneType.NICKNAME_POPUP)).showNicknameAndText(nick, text));
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.NICKNAME_POPUP));
        this.nickname = nick;
    }

    /**
     * Shows a popup indicating the chosen nickname and attempts to join a game.
     *
     * @param nickname The chosen nickname.
     */
    @Override
    public void show_chosenNickname(String nickname) {
        show_popupInfoAndNickname(nickname, "Trying to join a Game...");
    }

    /**
     * Shows a message indicating the current turn.
     *
     * @param model The game model.
     */
    @Override
    public void show_CurrentTurnMsg(GameImmutable model) {
        PauseTransition closePopupPause = new PauseTransition(Duration.seconds(3));
        closePopupPause.setOnFinished(event2 -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("It's your turn!", true));
        });
        closePopupPause.play();

    }

    /**
     * Shows a message asking the player to place cards on the main board.
     *
     * @param model The game model.
     */
    @Override
    public void show_askPlaceCardsMainMsg(GameImmutable model) {
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("CHOOSE A CARD TO PLACE", null));
            callPlatformRunLater(() -> this.guiApplication.showPlayerDeck(model, nickname));

        });
        pause.play();
    }

    /**
     * Shows a message indicating the player should pick a card.
     *
     * @param model The game model.
     */
    @Override
    public void show_PickCardMsg(GameImmutable model) {
        PauseTransition showMessagePause = new PauseTransition(Duration.seconds(5));
        showMessagePause.setOnFinished(event -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("CHOOSE A CARD TO PICK", null));
            PauseTransition activateScenePause = new PauseTransition(Duration.seconds(3));
            activateScenePause.setOnFinished(event2 -> {
                callPlatformRunLater(() -> {
                    this.guiApplication.setActiveScene(SceneType.BOARD_POPUP);
                    BoardPopUpController boardController = (BoardPopUpController) this.guiApplication.getController(SceneType.BOARD_POPUP);
                    boardController.enablePickCardTurn();
                    this.guiApplication.showBoard(model, true);
                });
            });
            activateScenePause.play();
        });
        showMessagePause.play();

    }

    /**
     * Requests the player to choose a card type
     *
     * @param model The game model.
     * @param nickname The nickname of the player
     */
    @Override
    public void show_askCardType(GameImmutable model, String nickname) {

    }

    /**
     * Displays persistent information of the game
     *
     * @param model The game model.
     * @param nickname The nickname of the player
     */
    @Override
    public void show_alwaysShow(GameImmutable model, String nickname) {

    }

    /**
     * Shows a message asking which cell on the book to place a card.
     *
     * @param model The game model.
     */
    @Override
    public void show_askWhichCellMsg(GameImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("Choose a Cell in the book to place the card:", null));
        callPlatformRunLater(()-> this.guiApplication.showBookChooseCell(model, nickname));
    }

    /**
     * Shows a message indicating that a card has been successfully placed.
     *
     * @param model The game model.
     */
    @Override
    public void show_cardPlacedMsg(GameImmutable model) {
        callPlatformRunLater(() -> ((MainSceneController) this.guiApplication.getController(SceneType.MAINSCENE)).cardPlacedCorrect(model));
    }

    /**
     * Shows a message indicating that a card has been drawn.
     *
     * @param model The game model.
     * @param nickname The player's nickname.
     */
    @Override
    public void show_cardDrawnMsg(GameImmutable model, String nickname) {
        BoardPopUpController controller = (BoardPopUpController) this.guiApplication.getController(SceneType.BOARD_POPUP);

        while (!model.isBoardUpdated()) { //TODO CONTROLLARE
            try {
                Thread.sleep(100); // Attendi 100 millisecondi prima di controllare di nuovo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Ripristina lo stato di interruzione
            }
        }
        controller.setBoard(model);

        PauseTransition pause2 = new PauseTransition(Duration.seconds(3));
        pause2.setOnFinished(event2 -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("This is your drawn card", true));
            show_playerDeck(model,nickname);
            callPlatformRunLater(() -> ((MainSceneController) this.guiApplication.getController(SceneType.MAINSCENE)).setGUI(this, model));
        });
        pause2.play();
    }

    /**
     * Shows a message indicating the next player's turn.
     *
     * @param model The game model.
     */
    @Override
    public void show_nextTurnMsg(GameImmutable model) {
        callPlatformRunLater(() -> ((MainSceneController) this.guiApplication.getController(SceneType.MAINSCENE)).highlightCurrentPlayer(model));

    }

    /**
     * Shows a message indicating points have been added.
     *
     * @param model The game model.
     * @param nickname The player's nickname.
     */
    @Override
    public void show_pointsAddedMsg(GameImmutable model, String nickname) {
        show_playerBook(model);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            if (model.getCurrentPlayer().getNickname().equals(nickname)) {

                callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("You scored some points!", true));

            } else {
                String msg = model.getNicknameCurrentPlaying() + " scored some points!";
                callPlatformRunLater(() -> this.guiApplication.changeLabelMessage(msg, false));
            }
            show_scoretrack(model);
        });
        pause.play();
    }

    /**
     * Shows a message indicating joining to a game with the chosen nickname.
     *
     * @param nick The chosen nickname.
     * @param color The color associated with the player.
     */
    @Override
    public void show_joiningToGameMsg(String nick, Color color) {
        // Mostra il pop-up con il messaggio
        show_popupInfoAndNickname(nick, "Trying to join a Game...");

        /* Imposta una transizione di pausa per 3 secondi
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            // Chiudi automaticamente il pop-up
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
        });
        pause.play();*/
    }


    /**
     * Shows the MainScene and initializes it with the game model and player information.
     *
     * @param model The game model.
     */
    @Override
    public void show_gameStarted(GameImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.MAINSCENE));
        callPlatformRunLater(() -> this.guiApplication.showMainScene(model, nickname, this));
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("GAME STARTED!", true));

    }

    /**
     * Shows the game ended scene and displays the final board.
     *
     * @param model The game model.
     */
    @Override
    public void show_gameEnded(GameImmutable model) {
       callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.GAMEENDED));
        callPlatformRunLater(() -> {
            try {
                this.guiApplication.showFinalBoard(model);
            } catch (NoPlayersException e) {
                throw new RuntimeException(e);
            }
        });
        alreadyShowedLobby=false;
    }

    /**
     * Shows a message asking the number of players before starting the game.
     */
    @Override
    public void show_askNumPlayersMessage() {
        Platform.runLater(() -> this.guiApplication.setInputReaderGUItoAllControllers(this.inputGUI));
        Platform.runLater(() -> this.guiApplication.createNewWindowWithStyle());
        Platform.runLater(() -> this.guiApplication.setActiveScene(SceneType.MENU));

    }

    /**
     * Shows a message asking for the game ID.
     */
    @Override
    public void show_askGameIDMessage() {

    }

    /**
     * Shows a message indicating an invalid selection.
     */
    @Override
    public void show_notValidMessage() {
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("Selection not valid!", false));
    }

    /**
     * Shows a message indicating that a player has joined the game.
     *
     * @param gameModel The game model.
     * @param nick The nickname of the player who joined.
     * @param color The color associated with the player.
     */
    @Override
    public void show_playerJoined(GameImmutable gameModel, String nick, Color color) {


        // Controlla se la lobby è già stata mostrata
        if (!alreadyShowedLobby) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                // Chiudi la finestra pop-up
                callPlatformRunLater(() -> {
                    this.guiApplication.closePopUpStage();

                    // Configura e mostra la lobby
                    LobbyController lobbyController = (LobbyController) this.guiApplication.getController(SceneType.LOBBY);
                    if (lobbyController != null) {
                        lobbyController.setGameid(gameModel.getGameId());
                        lobbyController.setVisibleBtnReady(true);
                        this.guiApplication.setActiveScene(SceneType.LOBBY);
                        this.guiApplication.showPlayerToLobby(gameModel);
                        alreadyShowedLobby = true;
                    } else {
                        System.err.println("LobbyController is null or invalid");
                    }
                });
            });
            pause.play();
        } else {
            // Mostra il giocatore nella lobby
            callPlatformRunLater(() -> this.guiApplication.showPlayerToLobby(gameModel));
        }
    }

    /**
     * Shows information representing all players in the game.
     *
     * @param model The game model
     */
    @Override
    public void show_allPlayers(GameImmutable model) {

    }

    /**
     * Shows a message indicating that the player is ready to start the game.
     *
     * @param model The game model.
     */
    @Override
    public void show_youAreReady(GameImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.disableBtnReadyToStart());
        callPlatformRunLater(() -> this.guiApplication.setPaneReady(model.getIndexPlayer(model.getPlayerByNickname(nickname)),model.getPlayerByNickname(nickname).getReadyToStart()));
    }

    /**
     * Shows a message indicating that the game is ready to start.
     *
     * @param gameModel The game model.
     * @param nickname The player's nickname.
     */
    @Override
    public void show_readyToStart(GameImmutable gameModel, String nickname) {
    }

    /**
     * Shows a message indicating to return to the main menu.
     */
    @Override
    public void show_returnToMenuMsg() {
        callPlatformRunLater(() -> this.guiApplication.showBtnReturnToMenu());
    }


    /**
     * Shows a message indicating an invalid cell selection.
     */
    @Override
    public void show_wrongSelMsg(int min, int max) {
        String errorMessage = "Invalid selection. Please choose a number between "+min+ " and " + max;
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.GENERIC_ERROR);
            this.guiApplication.showErrorGeneric(errorMessage, false);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                this.guiApplication.closePopUpStage();
            });
            pause.play();
        });
    }

    /**
     * Shows a message asking for a numerical input.
     *
     * @param msg The message prompting for numerical input.
     * @param gameModel The game model.
     * @param nickname The player's nickname.
     */
    @Override
    public void show_askNum(String msg, GameImmutable gameModel, String nickname) {

    }

    /**
     * Adds an important event to be shown in the GUI.
     *
     * @param input The important event to add.
     */
    @Override
    public void addImportantEvent(String input) {
        if (eventsToShow.size() + 1 >= DefaultValue.MaxEventToShow) {
            eventsToShow.removeFirst();
        }
        eventsToShow.add(input);
        callPlatformRunLater(() -> this.guiApplication.showImportantEvents(this.eventsToShow));
    }

    /**
     * Resets the list of important events shown in the GUI.
     */
    @Override
    public void resetImportantEvents() {

    }

    /**
     * Shows a message asking the player to draw from the deck.
     *
     * @param model The game model.
     * @param nickname The player's nickname.
     */
    @Override
    public void show_askDrawFromDeck(GameImmutable model, String nickname) {

    }

    /**
     * Shows a message indicating the player has to choose again due to an error.
     *
     * @param model The game model.
     * @param nickname The player's nickname.
     * @param msg The error message.
     */
    @Override
    public void show_playerHasToChooseAgain(GameImmutable model, String nickname, String msg) {
        String errorMessage = "ERROR: " + msg;
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.MAINSCENE));
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage(errorMessage, false));

    }

    /**
     * Shows a message indicating a wrong selection.
     */
    @Override
    public void show_wrongSelectionMsg() {

    }

    /**
     * Shows the temporary initial cards scene for a player.
     *
     * @param model The game model.
     * @param indexPlayer The index of the player.
     */
    @Override
    public void show_temporaryInitialCards(GameImmutable model, int indexPlayer) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.INITIALIZE_CARDS);
            this.guiApplication.setInitializationScene(model, true, indexPlayer);
        });
    }

    /**
     * Shows the objective cards scene for a player.
     *
     * @param model The game model.
     * @param indexPlayer The index of the player.
     */
    @Override
    public void show_ObjectiveCards(GameImmutable model, int indexPlayer) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.INITIALIZE_CARDS);
            this.guiApplication.setInitializationScene(model, false, indexPlayer);
        });
    }

    /**
     * Shows a scene indicating the personal objective for a player.
     *
     * @param model The game model.
     * @param indexPlayer The index of the player.
     */
    @Override
    public void show_personalObjective(GameImmutable model, int indexPlayer) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.WAITING_POPUP);
        });
    }

    /**
     * Closes the waiting popup stage.
     */
    @Override
    public void closeWaitPopUp(){
       callPlatformRunLater(()-> this.guiApplication.closePopUpStage());

    }

    /**
     * Shows a message indicating a no connection error.
     */
    @Override
    public void show_noConnectionError() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.GENERIC_ERROR));
        callPlatformRunLater(() -> this.guiApplication.showErrorGeneric("Connection to server lost!", true));
    }

    /**
     * Shows the player's deck in the main scene.
     *
     * @param model The game model.
     * @param nickname The player's nickname.
     */
    @Override
    public void show_playerDeck(GameImmutable model, String nickname) {
        while (!model.isDeckUpdated(nickname)) {
            try {
                Thread.sleep(100); // Attendi 100 millisecondi prima di controllare di nuovo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Ripristina lo stato di interruzione
            }
        }
        callPlatformRunLater(() -> ((MainSceneController) this.guiApplication.getController(SceneType.MAINSCENE)).setPlayerDeck(model, nickname));
    }

    /**
     * Shows the player's book in the main scene.
     *
     * @param model The game model.
     */
    @Override
    public void show_playerBook(GameImmutable model) {
        callPlatformRunLater(()-> this.guiApplication.showBook(model, nickname));
    }

    /**
     * Shows the score track popup with the current game model.
     *
     * @param model The game model.
     */
    @Override
    public void show_scoretrack(GameImmutable model) {
            callPlatformRunLater(() -> ((ScoretrackPopupController) this.guiApplication.getController(SceneType.SCORETRACK_POPUP)).setScoreTrack(model));
            callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.SCORETRACK_POPUP));

    }

    /**
     * Shows the board popup with the current game model.
     *
     * @param model The game model.
     */
    @Override
    public void show_board(GameImmutable model) {
        BoardPopUpController controller = (BoardPopUpController) this.guiApplication.getController(SceneType.BOARD_POPUP);

        while (!model.isBoardUpdated()) { //TODO CONTROLLARE
            try {
                Thread.sleep(100); // Attendi 100 millisecondi prima di controllare di nuovo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Ripristina lo stato di interruzione
            }
        }

        controller.setBoard(model);
        controller.enablePickCardTurn();
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.BOARD_POPUP));
    }

    /**
     * Shows a message indicating to wait for the player's turn.
     *
     * @param model The game model.
     * @param nickname The player's nickname.
     */
    @Override
    public void show_WaitTurnMsg(GameImmutable model, String nickname) {
        PauseTransition pause2 = new PauseTransition(Duration.seconds(5));
        pause2.setOnFinished(event -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            String msg= "WAIT! It's "+ model.getCurrentPlayer().getNickname()+" Turn";
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage(msg, false));
        });
        pause2.play();

    }

    /**
     * Shows the visible cards on the board.
     *
     * @param model The game model.
     * @param cardType The type of card to show.
     */
    @Override
    public void show_visibleCardsBoard(GameImmutable model, CardType cardType){
    }

    /**
     * Shows sent messages in the GUI.
     *
     * @param model The game model.
     * @param nickname The player's nickname.
     */
    @Override
    public void show_sentMessage(GameImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showMessages(model, this.nickname));
    }

    /**
     * Asks for chat interaction in the GUI.
     *
     * @param model The game model.
     * @param nick The nickname associated with the chat interaction.
     */
    @Override
    public void show_askForChat(GameImmutable model, String nick) {

    }

    /**
     * Shows a message for when a player reconnects.
     * The message is different for the reconnected player and the other players
     * @param model model where events happen
     * @param nick of the player
     * @param lastPlayerReconnected  nickname of the reconnected player
     */
    @Override
    public void show_PlayerReconnectedMsg(GameImmutable model, String nick, String lastPlayerReconnected) {
        if(nick.equals(lastPlayerReconnected)){ //TODO: NON serve metterlo dato che l'evento è aggiunto solo al giocattore
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.MAINSCENE));
            callPlatformRunLater(() -> this.guiApplication.showMainScene(model, nickname, this));

            callPlatformRunLater(() -> ((ScoretrackPopupController) this.guiApplication.getController(SceneType.SCORETRACK_POPUP)).setScoreTrack(model));

            BoardPopUpController controller = (BoardPopUpController) this.guiApplication.getController(SceneType.BOARD_POPUP);
            while (!model.isBoardUpdated()) { //TODO CONTROLLARE
                try {
                    Thread.sleep(100); // Attendi 100 millisecondi prima di controllare di nuovo
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Ripristina lo stato di interruzione
                }
            }
            controller.setBoard(model);

            show_playerBook(model);

            String msg= "YOU are back in the game!";
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage(msg, true));

        }/*else{
            String msg= "Player" + nick + "is back in the game!";
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage(msg, true));
        }*/

    }

    /**
     * Shows a message indicating a failed reconnection attempt.
     *
     * @param nick The nickname of the player who failed to reconnect.
     */
    @Override
    public void show_failedReconnectionMsg(String nick){
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.GENERIC_ERROR));
        callPlatformRunLater(() -> this.guiApplication.showErrorGeneric("Failed to reconnect to the game!", true));
    }

    /**
     * Asks if the player is trying to reconnect
     */
    @Override
    public void show_askForReconnection(){
        callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
       callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.RECONNECT_POPUP));

    }

    /**
     * Shows a message in case a player disconnectes
     * and only one player is connected indicating
     *  the game will end after a specified waiting period
     * @param secondsToWaitUntilGameEnded the seconds setted for reconnection timer
     */
    @Override
    public void show_onlyOnePlayerConnected(int secondsToWaitUntilGameEnded){
        String msg= "Only one player is connected, waiting " + secondsToWaitUntilGameEnded + " seconds before calling Game Ended!";
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage(msg,false));
    }




    /**
     * Adds a message to the chat and updates the GUI accordingly.
     *
     * @param msg The message to add.
     * @param model The game model.
     */
    @Override
    public void addMessage(Message msg, GameImmutable model) {
        show_sentMessage(model, model.getChat().getLastMessage().getSender().getNickname());
    }

}
