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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.StageStyle;
/**
 * This class is the main class of the GUI, it extends Application and it is used to start the GUI. It contains all the
 * methods to change the scene and to get the controller of a specific scene.
 *
 */
public class GUIApplication extends Application {

    private GameFlow gameFlow;
    private Stage primaryStage, popUpStage;
    private StackPane root; //radice dell'interfaccia utente
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
            connectionType = ConnectionType.SOCKET; // Valore predefinito
        } else {
            String firstParam = unnamedParams.getFirst();
            try {
                connectionType = ConnectionType.valueOf(firstParam);
            } catch (IllegalArgumentException e) {
                System.err.println("Parameter not valid for ConnectionType: " + firstParam + ". Used SOCKET.");
                connectionType = ConnectionType.SOCKET; // Valore predefinito in caso di errore
            }
        }

        // Crea GameFlow con il ConnectionType
        gameFlow = new GameFlow(this, connectionType);

        loadScenes(); //carica le scene

        this.primaryStage.setTitle("Codex Naturalis");
        root = new StackPane();
        Scene mainScene = new Scene(root, 1320, 720);
        this.primaryStage.setScene(mainScene);
        this.primaryStage.show();

    }



    /**
     * This method use the FXMLLoader to load the scene and the controller of the scene.
     */
    private void loadScenes() {
        scenes = new ArrayList<>();
        FXMLLoader loader; //formato per definire l'interfaccia utente dell'applicazione JavaFX in modo dichiarativo e che associa un controller ad ogni vista caricata
        Parent root;
        ControllerGUI controller;

        for (SceneType sceneType : SceneType.values()) {
            String path = sceneType.path();
            System.out.println("Loading FXML file: " + path); // Messaggio di debug
            loader = new FXMLLoader(getClass().getResource(path));
            try {
                root = loader.load();
                controller = loader.getController();
                Scene scene = new Scene(root);

                // Imposta il controller come UserData della scena
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

    //ritorna l'indice di quella scena nell'elenco delle scene
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
        this.primaryStage.setTitle("Codex Naturalis - " + scene.name()); //imposta il titolo della finestra principale aggiungendo il nome della scena attiva
        resizing = false; //disabilità la possibilità di ridimensionare la finestra durante il cambio di scena
        int index = getSceneIndex(scene); //indice della scena
        if (index != -1) {
            SceneInformation s = scenes.get(index);
            switch (scene) {
                /*case GENERIC_ERROR -> {
                    this.closePopUpStage();
                }*/

                case PUBLISHER -> {
                    this.primaryStage.setAlwaysOnTop(true);
                    this.primaryStage.centerOnScreen();

                }

                case NICKNAME_POPUP, WAITING_POPUP, GENERIC_ERROR-> {
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
                case MENU, INITIALIZE_CARDS, GAMEENDED -> {
                    this.primaryStage.centerOnScreen();
                    this.primaryStage.setAlwaysOnTop(false);
                }
                case MAINSCENE -> {
                   /* this.closePopUpStage();
                    MainSceneController controller = (MainSceneController) s.getControllerGUI();*/
                    this.primaryStage.centerOnScreen();
                    this.primaryStage.setAlwaysOnTop(false);
                }

                default ->
                    this.primaryStage.setAlwaysOnTop(false);


            }
            this.primaryStage.setScene(s.getScene());
            this.primaryStage.show();
           /* root.getChildren().clear();
            root.getChildren().add(s.getScene().getRoot());*/
        }

        widthOld=primaryStage.getScene().getWidth();
        heightOld=primaryStage.getScene().getHeight();
        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            rescale((double)newVal-16,heightOld);
        });

        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> rescale(widthOld,(double)newVal-39));
        resizing=true;
        // Cambia il contenuto del root StackPane per mostrare la nuova scena

    }

    /**
     * This method is used to rescale the scene.
     */
    public void rescale(double width, double heigh) {
        if(resizing) {
            double widthWindow = width;
            double heightWindow = heigh;


            double w = widthWindow / widthOld;  // your window width
            double h = heightWindow / heightOld;  // your window height

            widthOld = widthWindow;
            heightOld = heightWindow;
            Scale scale = new Scale(w, h, 0, 0);

            Node contentNode = primaryStage.getScene().lookup("#content");
            if (contentNode != null) {
                contentNode.getTransforms().add(scale);
            } else {
                System.err.println("Nodo con ID 'content' non trovato.");
            }

            //primaryStage.getScene().getRoot().getTransforms().add(scale);
            //primaryStage.getScene().lookup("#content").getTransforms().add(scale);
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

    public void createNewWindowWithStyle() {
        // Crea una nuova finestra con lo stile desiderato
        Stage newStage = new Stage();

        // Copia la scena dalla finestra precedente
        newStage.setScene(this.primaryStage.getScene());

        // Mostra la nuova finestra
        newStage.show();

        // Chiudi la finestra precedente
        this.primaryStage.close();

        // Imposta la nuova finestra come primaryStage
        this.primaryStage = newStage;
        this.primaryStage.centerOnScreen();
        this.primaryStage.setAlwaysOnTop(true);

        this.primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing all");

            System.exit(1);
        });
    }


    public void showGenericError(String message){
        GenericErrorController controller = (GenericErrorController) scenes.get(getSceneIndex(SceneType.GENERIC_ERROR)).getControllerGUI();
        controller.setMsg(message, false);
    }

    public void showErrorGeneric(String msg, boolean needToExitApp) {
        GenericErrorController controller = (GenericErrorController) scenes.get(getSceneIndex(SceneType.GENERIC_ERROR)).getControllerGUI();
        controller.setMsg(msg,needToExitApp);
    }



    public void showPlayerToLobby(GameImmutable model) {
        hidePanesInLobby();
        int i = 0;
        for (Player p : model.getPlayers()) {
            addLobbyPanePlayer(p.getNickname(), i, p.getReadyToStart(), p.getPlayerColor());
            i++;
        }
    }
    private void addLobbyPanePlayer(String nick, int indexPlayer, boolean isReady, Color color) {
        // Recupera il controller dalla scena attiva
        //LobbyController controller = (LobbyController) this.primaryStage.getScene().getUserData();
        LobbyController controller = (LobbyController) this.getController(SceneType.LOBBY);
        if (controller != null) {
            controller.setUsername(nick, indexPlayer);
            Pane panePlayerLobby = (Pane) this.primaryStage.getScene().lookup("#pane" + indexPlayer);
            panePlayerLobby.setVisible(true);
            String imagePath= color.getPath();
            controller.setPlayerImage(imagePath, indexPlayer);
            setPaneReady( indexPlayer , isReady);

        } else {
            System.err.println("LobbyController is null or invalid");
        }
    }
    public void setPaneReady(int indexPlayer, boolean isReady){
        Pane paneReady = (Pane) this.primaryStage.getScene().lookup("#ready" + indexPlayer);
        paneReady.setVisible(isReady);
    }

    private void hidePanesInLobby() {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.primaryStage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(false);

            Pane paneReady = (Pane) this.primaryStage.getScene().getRoot().lookup("#ready" + i);
            paneReady.setVisible(false);
        }
    }
    public void disableBtnReadyToStart() {
        //I set not visible the btn "Ready to start"
        ((LobbyController) scenes.get(getSceneIndex(SceneType.LOBBY)).getControllerGUI()).setVisibleBtnReady(false);
    }

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
        // Popola la ComboBox dei giocatori con i nomi dei giocatori disponibili
        controller.setPlayerComboBoxItems(playerNames);
    }
    public void showBook(GameImmutable model, String nickname){
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.updateBookPane(model, nickname);
    }
    public void showBookChooseCell(GameImmutable model, String nickname){
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.highlightChooseCell(model, nickname);
    }
    public void changeLabelMessage(String msg, Boolean success) {
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.setMsgToShow(msg, success);

    }

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
    public void showPlayerDeck(GameImmutable model, String nickname) {
        MainSceneController controller = (MainSceneController) scenes.get(getSceneIndex(SceneType.MAINSCENE)).getControllerGUI();
        controller.enlargeAndHighlightPlayerDeckPane(model, nickname);
    }

    public void showBoard(GameImmutable model, boolean enablePickCardTurn){
        BoardPopUpController controller = (BoardPopUpController) scenes.get(getSceneIndex(SceneType.BOARD_POPUP)).getControllerGUI();
        controller.setBoard(model);
        controller.enlargeAndHighlightBoardPane(enablePickCardTurn);
    }

    public void showFinalBoard(GameImmutable model) throws NoPlayersException {
        GameEndedController controller = (GameEndedController) scenes.get(getSceneIndex(SceneType.GAMEENDED)).getControllerGUI();
        controller.showFinal(model);
    }

    public void showBtnReturnToMenu() {
        GameEndedController controller = (GameEndedController) scenes.get(getSceneIndex(SceneType.GAMEENDED)).getControllerGUI();
        controller.showBtnReturnToMenu();
    }



}