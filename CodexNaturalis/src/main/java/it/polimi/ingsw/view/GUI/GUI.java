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
import it.polimi.ingsw.exceptions.PlacementConditionViolated;

import java.util.ArrayList;

import static it.polimi.ingsw.network.PrintAsync.printAsync;

/**
 * GUI class.
 * This class is the GUI implementation of the UI abstract class and it manages all the GUI-related operations.
 */
public class GUI extends UI {

    private GUIApplication guiApplication;
    private  InputGUI inputGUI;

    private String nickname;
    boolean showedPublisher = false;
    private boolean alreadyShowedLobby = false;

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

    public void callPlatformRunLater(Runnable r) {
        //Need to use this method to call any methods inside the GuiApplication
        //Doing so, the method requested will be executed on the JavaFX Thread (else exception)
        Platform.runLater(r);
    }

    @Override
    public void show_publisher()  {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.PUBLISHER));

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(DefaultValue.time_publisher_showing_seconds));
        pauseTransition.setOnFinished(event -> {
            showedPublisher = true; // Serve un booleano per evitare che venga mostrato nuovamente in futuro
            this.show_insertNicknameMessage();
        });
        pauseTransition.play(); // Non dimenticare di far partire la transizione
    }


    /**
     * The show method is used to show the GUI, and set the active scene to the publisher.
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
     * This method show the info about the chosen nickname.
     *
     * @param nick the nickname
     * @param text the info
     */
    public void show_popupInfoAndNickname(String nick, String text) {
        callPlatformRunLater(() -> ((NicknamePopUpController) this.guiApplication.getController(SceneType.NICKNAME_POPUP)).showNicknameAndText(nick, text));
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.NICKNAME_POPUP));
        this.nickname = nick;
    }

    @Override
    public void show_chosenNickname(String nickname) {
        show_popupInfoAndNickname(nickname, "Trying to join a Game...");
    }

    @Override
    public void show_CurrentTurnMsg(GameImmutable model) {
        PauseTransition closePopupPause = new PauseTransition(Duration.seconds(3));
        closePopupPause.setOnFinished(event2 -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("It's your turn!", true));
        });
        closePopupPause.play();

    }


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

    @Override
    public void show_PickCardMsg(GameImmutable model) {
        // Pausa di 5 secondi prima di mostrare il messaggio
        PauseTransition showMessagePause = new PauseTransition(Duration.seconds(5));
        showMessagePause.setOnFinished(event -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("CHOOSE A CARD TO PICK", null));
            // Pausa di 5 secondi prima di attivare la scena del board
            PauseTransition activateScenePause = new PauseTransition(Duration.seconds(3));
            activateScenePause.setOnFinished(event2 -> {
                callPlatformRunLater(() -> {
                    this.guiApplication.setActiveScene(SceneType.BOARD_POPUP);
                    BoardPopUpController boardController = (BoardPopUpController) this.guiApplication.getController(SceneType.BOARD_POPUP);
                    boardController.enablePickCardTurn();
                    this.guiApplication.showBoard(model, true); // Pass true per abilitare la possibilità di pescare una carta
                });
            });
            activateScenePause.play();
        });
        showMessagePause.play();

    }

    @Override
    public void show_askCardType(GameImmutable model, String nickname) {

    }

    @Override
    public void show_alwaysShow(GameImmutable model, String nickname) {

    }

    @Override
    public void show_alwaysShowForAll(GameImmutable model) {

    }

    @Override
    public void show_askWhichCellMsg(GameImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("Choose a Cell in the book to place the card:", null));
        callPlatformRunLater(()-> this.guiApplication.showBookChooseCell(model, nickname));
    }

    @Override
    public void show_cardPlacedMsg(GameImmutable model) {
        callPlatformRunLater(() -> ((MainSceneController) this.guiApplication.getController(SceneType.MAINSCENE)).cardPlacedCorrect(model));
    }

    @Override
    public void show_cardDrawnMsg(GameImmutable model, String nickname) {
        callPlatformRunLater(() -> ((BoardPopUpController) this.guiApplication.getController(SceneType.BOARD_POPUP)).setBoard(model));
        PauseTransition pause2 = new PauseTransition(Duration.seconds(3));
        pause2.setOnFinished(event2 -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("This is your drawn card", true));
            show_playerDeck(model,nickname);
            callPlatformRunLater(() -> ((MainSceneController) this.guiApplication.getController(SceneType.MAINSCENE)).setGUI(this, model));
        });
        pause2.play();
    }

    @Override
    public void show_nextTurnMsg(GameImmutable model) {
        callPlatformRunLater(() -> ((MainSceneController) this.guiApplication.getController(SceneType.MAINSCENE)).highlightCurrentPlayer(model));

    }


    @Override
    public void show_pointsAddedMsg(GameImmutable model, String nickname) {
        show_playerBook(model); //((playerBook aggiornato))
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

    @Override
    public void show_joiningToGameMsg(String nick, Color color) {
        show_popupInfoAndNickname(nickname, "Trying to join a Game...");
    }


    @Override
    public void show_gameStarted(GameImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.MAINSCENE));
        callPlatformRunLater(() -> this.guiApplication.showMainScene(model, nickname, this));
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("GAME STARTED!", true));

    }

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
    }

    @Override
    public void show_askNumPlayersMessage() {
        Platform.runLater(() -> this.guiApplication.setInputReaderGUItoAllControllers(this.inputGUI));
        Platform.runLater(() -> this.guiApplication.createNewWindowWithStyle());
        Platform.runLater(() -> this.guiApplication.setActiveScene(SceneType.MENU));

    }

    @Override
    public void show_askGameIDMessage() {

    }

    @Override
    public void show_notValidMessage() {
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("Selection not valid!", false));
    }

    @Override
    public void show_playerJoined(GameImmutable gameModel, String nick, Color color) {
        if (!alreadyShowedLobby) {
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                callPlatformRunLater(() -> this.guiApplication.closePopUpStage());

                callPlatformRunLater(() -> {
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
            // The player is in lobby and another player has joined
            callPlatformRunLater(() -> this.guiApplication.showPlayerToLobby(gameModel));
        }
    }


    @Override
    public void show_allPlayers(GameImmutable model) {

    }



    @Override
    public void show_youAreReady(GameImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.disableBtnReadyToStart());
        callPlatformRunLater(() -> this.guiApplication.setPaneReady(model.getIndexPlayer(model.getPlayerByNickname(nickname)),model.getPlayerByNickname(nickname).getReadyToStart()));
    }


    @Override
    public void show_readyToStart(GameImmutable gameModel, String nickname) {
    }

    @Override
    public void show_returnToMenuMsg() {
        callPlatformRunLater(() -> this.guiApplication.showBtnReturnToMenu());
    }



    @Override
    public void show_wrongCardSelMsg() {
        String errorMessage = "Invalid selection. Please choose a number between 0 and 6.";
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.GENERIC_ERROR);
            this.guiApplication.showErrorGeneric(errorMessage, false);

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                this.guiApplication.closePopUpStage();
            });
            pause.play();
        });
       // callPlatformRunLater(()-> this.guiApplication.showPlayerDeck(GameImmutable model));
    }

    @Override
    public void show_wrongCellSelMsg() {
        String errorMessage = "Invalid cell selection. Please choose an empty cell with a valid coordinate!";
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

    @Override
    public void show_askNum(String msg, GameImmutable gameModel, String nickname) {

    }

    @Override
    public void addImportantEvent(String input) {
        //Want to show a numbeMaxEventToShow important event happened
        if (eventsToShow.size() + 1 >= DefaultValue.MaxEventToShow) {
            eventsToShow.removeFirst();
        }
        eventsToShow.add(input);
        callPlatformRunLater(() -> this.guiApplication.showImportantEvents(this.eventsToShow));
    }


    @Override
    public void resetImportantEvents() {

    }

    @Override
    public void show_askDrawFromDeck(GameImmutable model, String nickname) {

    }

    @Override
    public void show_playerHasToChooseAgain(GameImmutable model, String nickname, String msg) {
        String errorMessage = "ERROR: " + msg;
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.MAINSCENE));
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage(errorMessage, false));

    }

    @Override
    public void show_wrongSelectionMsg() {

    }

    @Override
    public void show_temporaryInitialCards(GameImmutable model, int indexPlayer) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.INITIALIZE_CARDS);
            this.guiApplication.setInitializationScene(model, true, indexPlayer);
        });
    }

    @Override
    public void show_ObjectiveCards(GameImmutable model, int indexPlayer) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.INITIALIZE_CARDS);
            this.guiApplication.setInitializationScene(model, false, indexPlayer);
        });
    }

    @Override
    public void show_personalObjective(GameImmutable model, int indexPlayer) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.WAITING_POPUP);
        });
    }
    @Override
    public void closeWaitPopUp(){
       callPlatformRunLater(()-> this.guiApplication.closePopUpStage());

    }

    @Override
    public void show_noConnectionError() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.GENERIC_ERROR));
        callPlatformRunLater(() -> this.guiApplication.showErrorGeneric("Connection to server lost!", true));
    }

    @Override
    public void show_playerDeck(GameImmutable model, String nickname) {
        callPlatformRunLater(() -> ((MainSceneController) this.guiApplication.getController(SceneType.MAINSCENE)).setPlayerDeck(model,nickname));
    }
    @Override
    public void show_playerBook(GameImmutable model) {
        callPlatformRunLater(()-> this.guiApplication.showBook(model, nickname));
    }
    @Override
    public void show_scoretrack(GameImmutable model) {

            callPlatformRunLater(() -> ((ScoretrackPopupController) this.guiApplication.getController(SceneType.SCORETRACK_POPUP)).setScoreTrack(model));
            callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.SCORETRACK_POPUP));

    }

    @Override
    public void show_board(GameImmutable model) {
        callPlatformRunLater(() -> ((BoardPopUpController) this.guiApplication.getController(SceneType.BOARD_POPUP)).setBoard(model));
        ((BoardPopUpController) this.guiApplication.getController(SceneType.BOARD_POPUP)).enablePickCardTurn();
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.BOARD_POPUP));
    }


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
    @Override
    public void show_visibleCardsBoard(GameImmutable model, CardType cardType){

    }



    //TODO

    @Override
    public void show_sentMessage(GameImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.showMessages(model, this.nickname));
    }

    @Override
    public void show_askForChat(GameImmutable model, String nick) {

    }

    @Override
    protected int getLengthLongestMessage(GameImmutable model) {
        return 0;
    }


    /**
     * Shows a message for the next turn or when a player reconnects
     * @param model model where events happen
     * @param nick of the reconnected player
     */
    @Override
    public void show_PlayerReconnectedMsg(GameImmutable model, String nick) {
        callPlatformRunLater(() -> ((MainSceneController) this.guiApplication.getController(SceneType.MAINSCENE)).playerReconnectedInGame(model));
    }


    @Override
    public void addMessage(Message msg, GameImmutable model) {
        show_sentMessage(model, model.getChat().getLastMessage().getSender().getNickname());
    }

    @Override
    public void show_failedReconnectionMsg(GameImmutable model, String nick){
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.GENERIC_ERROR));
        callPlatformRunLater(() -> this.guiApplication.showErrorGeneric("Failed to recconnect to the game!", true));
    }
}
