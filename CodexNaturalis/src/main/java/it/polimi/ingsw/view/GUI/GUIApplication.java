package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.view.GUI.controllers.ControllerGUI;
import it.polimi.ingsw.view.GUI.controllers.MenuController;
import it.polimi.ingsw.view.GUI.scenes.SceneInformation;
import it.polimi.ingsw.view.GUI.scenes.SceneType;
import it.polimi.ingsw.view.Utilities.InputGUI;
import it.polimi.ingsw.view.flow.GameFlow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        ConnectionType connectionType = null;

        if (unnamedParams == null || unnamedParams.isEmpty()) {
            System.err.println("No parameteres for ConnectionType. Used SOCKET.");
            connectionType = ConnectionType.SOCKET; // Valore predefinito
        } else {
            String firstParam = unnamedParams.get(0);
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
        Scene scene = new Scene(root, 1320, 720);
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();

        //si può aggiungere un suono di apertura
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
            loader = new FXMLLoader(getClass().getResource(sceneType.path()));
            try {
                root = loader.load();
                controller = loader.getController();
                scenes.add(new SceneInformation(new Scene(root), sceneType, controller));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load FXML file: " + sceneType.path(), e);
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
                case PUBLISHER -> {
                    this.primaryStage.setAlwaysOnTop(true);
                    this.primaryStage.centerOnScreen();

                }
                case NICKNAME -> {
                //TODO
                }
                case NICKNAME_POPUP -> {
                    openPopup(scenes.get(getSceneIndex(scene)).getScene());
                    return;
                }
                case ASK_NUM_PLAYERS -> {
                    //TODO
                }
                case  ASK_GAME_ID-> {
                    //TODO
                }


                default -> {
                    this.primaryStage.setAlwaysOnTop(false);

                }
            }
            this.primaryStage.setScene(s.getScene());
            this.primaryStage.show();
        }

        widthOld=primaryStage.getScene().getWidth();
        heightOld=primaryStage.getScene().getHeight();
        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            rescale((double)newVal-16,heightOld);
        });

        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            rescale(widthOld,(double)newVal-39);
        });
        resizing=true;

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
            //primaryStage.getScene().getRoot().getTransforms().add(scale);
            primaryStage.getScene().lookup("#content").getTransforms().add(scale);
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


}