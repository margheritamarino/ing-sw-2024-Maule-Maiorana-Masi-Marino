package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.Chat.Message;
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
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
        callPlatformRunLater(() -> ((OrderPlayersPopUp) this.guiApplication.getController(SceneType.ORDERPLAYERS_POPUP)).setOrderListText(model));
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ORDERPLAYERS_POPUP));
        });
        pause.play();
    }


    @Override
    public void show_askPlaceCardsMainMsg(GameImmutable model) {
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("CHOOSE A CARD TO PLACE", null));
            callPlatformRunLater(() -> this.guiApplication.showPlayerDeck());

        });
        pause.play();
    }

    @Override
    public void show_PickCardMsg(GameImmutable model) {
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(event -> {
            callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("CHOOSE A CARD TO PICK", null));
            callPlatformRunLater(() -> this.guiApplication.showBoard());
        });
        pause.play();
    }

    @Override
    public void show_askCardType(GameImmutable model, String nickname) {
        callPlatformRunLater(() -> this.guiApplication.changeLabelMessage("Which card do you want to pick?", null));

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

    }

    @Override
    public void show_cardDrawnMsg(GameImmutable model, String nickname) {

    }

    @Override
    public void show_nextTurnMsg(GameImmutable model) {

    }

    @Override
    public void show_pointsAddedMsg(GameImmutable model, String nickname) {

    }

    @Override
    public void show_joiningToGameMsg(String nick, Color color) {
        show_popupInfoAndNickname(nickname, "Trying to join a Game...");
    }


    @Override
    public void show_gameStarted(GameImmutable model) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.MAINSCENE));
        callPlatformRunLater(() -> this.guiApplication.showMainScene(model, nickname, this));

    }

    @Override
    public void show_gameEnded(GameImmutable model) {
       // callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.GAME_ENDED));
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
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
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

    }



    @Override
    public void show_wrongCardSelMsg() {

    }

    @Override
    public void show_wrongCellSelMsg() {

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
    public void show_playerHasToChooseAgain(GameImmutable model, String nickname) {

    }

    @Override
    public void show_wrongSelectionMsg() {

    }

    @Override
    public void show_temporaryInitialCards(GameImmutable model) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.INITIALIZE_CARDS);
            this.guiApplication.setInitializationScene(model, true);
        });
    }

    @Override
    public void show_ObjectiveCards(GameImmutable model) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(SceneType.INITIALIZE_CARDS);
            this.guiApplication.setInitializationScene(model, false);
        });
    }

    @Override
    public void show_personalObjective() {

    }

    @Override
    public void show_noConnectionError() {
        //callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.GENERIC_ERROR));
        //callPlatformRunLater(() -> this.guiApplication.showErrorGeneric("Connection to server lost!", true));
    }

    @Override
    public void show_playerDeck(GameImmutable model, String nickname) {

    }
    @Override
    public void show_playerBook(GameImmutable model) {

    }
    @Override
    public void show_scoretrack(GameImmutable model) {
        callPlatformRunLater(() -> ((ScoretrackPopupController) this.guiApplication.getController(SceneType.SCORETRACK_POPUP)).setScoreTrack(model));
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.SCORETRACK_POPUP));

    }
    @Override
    public void show_board(GameImmutable model) {
        callPlatformRunLater(() -> ((BoardPopUpController) this.guiApplication.getController(SceneType.BOARD_POPUP)).setBoard(model));
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.BOARD_POPUP));
    }


    @Override
    public void show_WaitTurnMsg(GameImmutable model, String nickname) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            callPlatformRunLater(() -> ((OrderPlayersPopUp) this.guiApplication.getController(SceneType.ORDERPLAYERS_POPUP)).setOrderListText(model));
            callPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneType.ORDERPLAYERS_POPUP));
        });
        pause.play();
        PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            callPlatformRunLater(() -> this.guiApplication.closePopUpStage());
            String msg= "WAIT! It's"+ model.getCurrentPlayer().getNickname()+"Turn";
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


    @Override
    public void addMessage(Message msg, GameImmutable model) {
        show_sentMessage(model, model.getChat().getLastMessage().getSender().getNickname());
    }
}
