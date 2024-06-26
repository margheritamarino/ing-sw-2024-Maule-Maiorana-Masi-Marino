package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.exceptions.NoPlayersException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.view.GUI.controllers.*;
import it.polimi.ingsw.view.GUI.scenes.SceneInformation;
import it.polimi.ingsw.view.GUI.scenes.SceneType;
import it.polimi.ingsw.view.Utilities.InputGUI;
import it.polimi.ingsw.view.flow.GameFlow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.StageStyle;
/**
 * This class is the main class of the GUI, it extends Application and it is used to start the GUI. It contains all the
 * methods to change the scene and to get the controller of a specific scene.
 */
public class GUIApplication extends Application {

    private GameFlow gameFlow;
    private Stage primaryStage, popUpStage;
    private StackPane root;
    private ArrayList<SceneInformation> scenes;
    private boolean resizing=true;
    private double widthOld, heightOld;



    /**
     * Method to set the scene index
     * @param primaryStage the primary stage {@link Stage}
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        List<String> unnamedParams = getParameters().getUnnamed();
        ConnectionType connectionType ;

        if (unnamedParams == null || unnamedParams.isEmpty()) {
            System.err.println("No parameteres for ConnectionType. Used SOCKET.");
            connectionType = ConnectionType.SOCKET;
        } else {
            String firstParam = unnamedParams.getFirst();
            try {
                connectionType = ConnectionType.valueOf(firstParam);
            } catch (IllegalArgumentException e) {
                System.err.println("Parameter not valid for ConnectionType: " + firstParam + ". Used SOCKET.");
                connectionType = ConnectionType.SOCKET;
            }
        }

        gameFlow = new GameFlow(this, connectionType);

        loadScenes();

        this.primaryStage.setTitle("Codex Naturalis");
        root = new StackPane();
        Scene mainScene = new Scene(root, 1320, 720);
        this.primaryStage.setScene(mainScene);
        this.primaryStage.show();

    }

    /**
     * This method use the FXMLLoader to load the scene and the controller of the scene.
     */
    /*private void loadScenes() {
        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        ControllerGUI controller;

        for (SceneType sceneType : SceneType.values()) {
            String path = sceneType.path();
            System.out.println("Loading FXML file: " + path);
            loader = new FXMLLoader(getClass().getResource(path));
            try {
                root = loader.load();
                controller = loader.getController();
                Scene scene = new Scene(root);

                scene.setUserData(controller);

                scenes.add(new SceneInformation(scene, sceneType, controller));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load FXML file: " + path, e);
            }
        }
    }*/
    private void loadScenes() {
        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        ControllerGUI controller;

        for (SceneType sceneType : SceneType.values()) {
            String path = sceneType.path();
            System.out.println("Loading FXML file: " + path);
            URL fxmlLocation = getClass().getResource(path);

            if (fxmlLocation == null) {
                throw new RuntimeException("FXML file not found: " + path);
            }

            loader = new FXMLLoader(fxmlLocation);

            try {
                root = loader.load();
                controller = loader.getController();
                Scene scene = new Scene(root);
                scene.setUserData(controller);

                scenes.add(new SceneInformation(scene, sceneType, controller));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load FXML file: " + path, e);
            }
        }
    }


    /**
     * This method set the input reader GUI to all the controllers.
     * @param inputGUI the input reader GUI {@link InputGUI}
     */
    public void setInputReaderGUItoAllControllers(InputGUI inputGUI) {
        loadScenes();
        for (SceneInformation s : scenes) {
            s.setInputGUI(inputGUI);
        }
    }

    /**
     * This method is use to get a controller of a specific scene.
     * @param scene the scene {@link SceneType}
     * @return the controller of the scene {@link ControllerGUI}
     */
    public ControllerGUI getController(SceneType scene) {
        int index = getSceneIndex(scene);
        if (index != -1) {
            return scenes.get(getSceneIndex(scene)).getControllerGUI();
        }
        return null;
    }

    /**
     * Returns the index of the specified scene in the list of scenes.
     *
     * @param scene The SceneType to find the index of.
     * @return The index of the specified scene, or -1 if the scene is not found.
     */
    private int getSceneIndex(SceneType scene) {
        for (int i = 0; i < scenes.size(); i++) {
            if (scenes.get(i).getSceneType().equals(scene))
                return i;
        }
        return -1;
    }

    /**
     * This method is used to set the active scene.
     * @param scene the scene {@link SceneType}
     */
    public void setActiveScene(SceneType scene) {
        this.primaryStage.setTitle("Codex Naturalis - " + scene.name());
        resizing = false;
        int index = getSceneIndex(scene);
        if (index != -1) {
            SceneInformation s = scenes.get(index);
            switch (scene) {
                case PUBLISHER -> {
                    this.primaryStage.setAlwaysOnTop(true);
                    this.primaryStage.centerOnScreen();

                }

                case NICKNAME_POPUP, WAITING_POPUP, GENERIC_ERROR, RECONNECT_POPUP -> {
                    openPopup(scenes.get(getSceneIndex(scene)).getScene());
                    return;
                }


                case BOARD_POPUP-> {
                    openPopup(scenes.get(getSceneIndex(scene)).getScene());
                    BoardPopUpController controller = (BoardPopUpController) s.getControllerGUI();
                    controller.setGUIApplication(this);
                    return;
                }
                case SCORETRACK_POPUP-> {
                    openPopup(scenes.get(getSceneIndex(scene)).getScene());
                    ScoretrackPopupController controller = (ScoretrackPopupController) s.getControllerGUI();
                    controller.setGUIApplication(this);

                    return;
                }
                case MENU, INITIALIZE_CARDS, GAMEENDED, MAINSCENE -> {
                    this.primaryStage.centerOnScreen();
                    this.primaryStage.setAlwaysOnTop(false);
                }

                default ->
                    this.primaryStage.setAlwaysOnTop(false);


            }
            this.primaryStage.setScene(s.getScene());
            this.primaryStage.show();
        }

        widthOld=primaryStage.getScene().getWidth();
        heightOld=primaryStage.getScene().getHeight();
        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            rescale((double)newVal-16,heightOld);
        });

        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> rescale(widthOld,(double)newVal-39));
        resizing=true;

    }

    /**
     * This method is used to rescale the scene.
     */
    public void rescale(double width, double heigh) {
        if(resizing) {
            double widthWindow = width;
            double heightWindow = heigh;


            double w = widthWindow / widthOld;
            double h = heightWindow / heightOld;

            widthOld = widthWindow;
            heightOld = heightWindow;
            Scale scale = new Scale(w, h, 0, 0);

            Node contentNode = primaryStage.getScene().lookup("#content");
            if (contentNode != null) {
                contentNode.getTransforms().add(scale);
            } else {
                System.err.println("Nodo con ID 'content' non trovato.");
            }

        }
    }

    /**
     * This method is used to open the popup.
     * @param scene the scene {@link Scene}
     */
    private void openPopup(Scene scene) {
        popUpStage = new Stage();
        popUpStage.setTitle("Info");
        popUpStage.setResizable(false);
        popUpStage.setScene(scene);

        popUpStage.initStyle(StageStyle.UNDECORATED);
        popUpStage.setOnCloseRequest(we -> System.exit(0));
        popUpStage.show();

        popUpStage.setX(primaryStage.getX() + (primaryStage.getWidth() - scene.getWidth()) * 0.5);
        popUpStage.setY(primaryStage.getY() + (primaryStage.getHeight() - scene.getHeight()) * 0.5);
    }

    /**
     * This method is used to close the popup.
     */
    public void closePopUpStage() {
        if (popUpStage != null)
            popUpStage.hide();
    }

    /**
     * Creates a new window using the current primary stage's scene, closes the current primary stage,
     * and sets the new stage as the primary stage. The new stage is centered on the screen and set
     * to always stay on top. Additionally, it sets an action to exit the application when the new
     * stage is closed.
     */
    public void createNewWindowWithStyle() {
        Stage newStage = new Stage();

        newStage.setScene(this.primaryStage.getScene());
        newStage.show();
        this.primaryStage.close();

        this.primaryStage = newStage;
        this.primaryStage.centerOnScreen();
        this.primaryStage.setAlwaysOnTop(true);

        this.primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing all");

            System.exit(1);
        });
    }

    /**
     * Shows a generic error message using the GenericErrorController.
     *
     * @param msg The message to display.
     * @param needToExitApp Indicates whether the application needs to exit after showing the error.
     */
    public void showErrorGeneric(String msg, boolean needToExitApp) {
        GenericErrorController controller = (GenericErrorController) scenes.get(getSceneIndex(SceneType.GENERIC_ERROR)).getControllerGUI();
        controller.setMsg(msg,needToExitApp);
    }

    /**
     * Updates the lobby UI with player information from the provided game model.
     *
     * @param model The game model containing player information.
     */
    public void showPlayerToLobby(GameImmutable model) {
        Platform.runLater(() -> {
            try {
                hidePanesInLobby();
                int i = 0;
                for (Player p : model.getPlayers()) {
                    addLobbyPanePlayer(p.getNickname(), i, p.getReadyToStart(), p.getPlayerColor());
                    i++;
                }
            } catch (Exception e) {
                // Gestione dell'eccezione generale
                System.err.println("An error occurred while showing players in the lobby: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Adds a player to the lobby, setting their username, player image, and ready indicator.
     *
     * @param nick The nickname of the player to add.
     * @param indexPlayer The index of the player in the lobby UI.
     * @param isReady Indicates whether the player is ready to start the game.
     * @param color The color associated with the player.
     */
    private void addLobbyPanePlayer(String nick, int indexPlayer, boolean isReady, Color color) {
        try {
            LobbyController controller = (LobbyController) this.getController(SceneType.LOBBY);
            if (controller != null) {
                controller.setUsername(nick, indexPlayer);
                Pane panePlayerLobby = (Pane) this.primaryStage.getScene().lookup("#pane" + indexPlayer);
                if (panePlayerLobby != null) {
                    panePlayerLobby.setVisible(true);
                    String imagePath = color.getPath();
                    controller.setPlayerImage(imagePath, indexPlayer);
                    setPaneReady(indexPlayer, isReady);
                } else {
                    System.err.println("Pane for player index " + indexPlayer + " not found.");
                }
            } else {
                System.err.println("LobbyController is null or invalid.");
            }
        } catch (Exception e) {
            // Gestione dell'eccezione generale
            System.err.println("An error occurred while adding a player to the lobby: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sets the visibility of the ready indicator pane for a specific player in the lobby.
     *
     * @param indexPlayer The index of the player whose ready indicator pane to update.
     * @param isReady Indicates whether the player is ready.
     */
    public void setPaneReady(int indexPlayer, boolean isReady){
        Pane paneReady = (Pane) this.primaryStage.getScene().lookup("#ready" + indexPlayer);
        paneReady.setVisible(isReady);
    }

    /**
     * Hides player panes and ready indicators in the lobby UI.
     * Iterates through all possible player indices (0 to 3) and sets their corresponding
     * panes to invisible.
     */
    private void hidePanesInLobby() {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + i);
            if (panePlayerLobby != null) {
                panePlayerLobby.setVisible(false);
            } else {
                System.err.println("Pane for player index " + i + " not found.");
            }

            Pane paneReady = (Pane) this.primaryStage.getScene().getRoot().lookup("#ready" + i);
            if (paneReady != null) {
                paneReady.setVisible(false);
            } else {
                System.err.println("Ready pane for player index " + i + " not found.");
            }
        }
    }

    /**
     * Disables the "Ready to Start" button in the lobby UI.
     */
    public void disableBtnReadyToStart() {
        ((LobbyController) scenes.get(getSceneIndex(SceneType.LOBBY)).getControllerGUI()).setVisibleBtnReady(false);
    }

    /**
     * Sets up the initialization scene for either choosing personal goals or initial cards.
     *
     * @param model The game model containing relevant initialization data.
     * @param initial Indicates whether it's for initial cards (true) or personal goals (false).
     * @param indexPlayer The index of the player for whom the initialization scene is set.
     */
    public void setInitializationScene(GameImmutable model, boolean initial, int indexPlayer){
        InitializeCardsController controller = (InitializeCardsController) scenes.get(getSceneIndex(SceneType.INITIALIZE_CARDS)).getControllerGUI();

        if (!initial) {
            controller.setTitleField("Choose your PERSONAL GOAL for the Game...");
            ObjectiveCard[] temp = model.getObjectiveCard().get(indexPlayer);
            String path1 = temp[0].getImagePath();
            String path2 = temp[1].getImagePath();
            controller.setCards(path1, path2);
        } else{
            controller.setTitleField("Choose if you want to place the Front or the Back...");
            PlayableCard[] temp = model.getInitialCard().get(indexPlayer);
            String path1 = temp[0].getImagePath();
            String path2 = temp[1].getImagePath();
            controller.setCards(path1, path2);
        }


    }

    //MAIN SCENE

    /**
     * Displays the main game scene with updated information.
     *
     * @param model The immutable game model containing current game state.
     * @param nickname The nickname of the local player.
     * @param gui The GUI instance controlling the application.
     */
    public void showMainScene(GameImmutable model, String nickname, GUI gui){
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.setGUI(gui, model);
        controller.setPersonalObjective(model, nickname);
        controller.setNicknameAndID(model, nickname);

        controller.setPlayerDeck(model, nickname);
        controller.setOrderListText(model);
        showBook(model, nickname);
        List<Player> players = model.getPlayers();
        List<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getNickname());
        }
        controller.setPlayerComboBoxItems(playerNames);
    }

    /**
     * Updates and displays the book pane in the main game scene.
     *
     * @param model The immutable game model containing current game state.
     * @param nickname The nickname of the local player.
     */
    public void showBook(GameImmutable model, String nickname){
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.updateBookPane(model, nickname);
    }

    /**
     * Highlights the cell selection in the book pane in the main game scene.
     *
     * @param model The immutable game model containing current game state.
     * @param nickname The nickname of the local player.
     */
    public void showBookChooseCell(GameImmutable model, String nickname){
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.highlightChooseCell(model, nickname);
    }

    /**
     * Updates the message displayed on the main scene.
     *
     * @param msg The message to display.
     * @param success Specifies if the message indicates a success (true) or an error (false).
     */
    public void changeLabelMessage(String msg, Boolean success) {
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.setMsgToShow(msg, success);

    }

    /**
     * Displays chat messages in the main game scene.
     *
     * @param model The immutable game model containing current game state and chat messages.
     * @param myNickname The nickname of the local player.
     */
    public void showMessages(GameImmutable model, String myNickname) {
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.setMessage(model.getChat().getMsgs(), myNickname);
    }

    /**
     * This method is used to show all the important events.
     * @param importantEvents the list of the important events
     */
    public void showImportantEvents(List<String> importantEvents) {
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.setImportantEvents(importantEvents);
    }

    /**
     * Enlarges and highlights the player's deck pane in the main game scene.
     *
     * @param model The immutable game model containing current game state.
     * @param nickname The nickname of the local player.
     */
    public void showPlayerDeck(GameImmutable model, String nickname) {
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.enlargeAndHighlightPlayerDeckPane(model, nickname);
    }

    /**
     * Sets the board model and optionally enables the pick card turn in the board popup scene.
     *
     * @param model The immutable game model containing current game state.
     * @param enablePickCardTurn Flag indicating whether to enable pick card turn.
     */
    public void showBoard(GameImmutable model, boolean enablePickCardTurn){
        BoardPopUpController controller = (BoardPopUpController) scenes.get(getSceneIndex(SceneType.BOARD_POPUP)).getControllerGUI();
        controller.setBoard(model);
        controller.enlargeAndHighlightBoardPane(enablePickCardTurn);
    }




    /**
     * Displays the final board scene with the given game model.
     *
     * @param model The immutable game model containing final game state.
     * @throws NoPlayersException If there are no players in the game.
     */
    public void showFinalBoard(GameImmutable model) throws NoPlayersException {
        GameEndedController controller = (GameEndedController) scenes.get(getSceneIndex(SceneType.GAMEENDED)).getControllerGUI();
        controller.showFinal(model);
    }

    /**
     * Displays the button to return to the main menu in the game ended scene.
     */
    public void showBtnReturnToMenu() {
        GameEndedController controller = (GameEndedController) scenes.get(getSceneIndex(SceneType.GAMEENDED)).getControllerGUI();
        controller.showBtnReturnToMenu();
    }



}