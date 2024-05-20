package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.view.GUI.controllers.ControllerGUI;
import it.polimi.ingsw.view.GUI.controllers.MenuController;
import it.polimi.ingsw.view.GUI.scenes.SceneInformation;
import it.polimi.ingsw.view.GUI.scenes.SceneType;
import it.polimi.ingsw.view.Utilities.InputGUI;
import it.polimi.ingsw.view.flow.GameFlow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is the main class of the GUI, it extends Application and it is used to start the GUI. It contains all the
 * methods to change the scene and to get the controller of a specific scene.
 *
 */
public class GUIApplication extends Application {

    private GameFlow gameFlow;
    private Stage primaryStage;
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
        gameFlow = new GameFlow(this, ConnectionType.valueOf(getParameters().getUnnamed().get(0))); //TODO
        loadScenes(); //carica le scene
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Codex Naturalis");
        root = new StackPane();

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

        for (int i = 0; i < SceneType.values().length; i++) {
            loader = new FXMLLoader(getClass().getResource(SceneType.values()[i].path()));
            try {
                root = loader.load();
                controller = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scenes.add(new SceneInformation(new Scene(root), SceneType.values()[i], controller));
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
            SceneInformation s = scenes.get(getSceneIndex(scene));
            switch (scene) {
                case PUBLISHER -> {
                    this.primaryStage.setAlwaysOnTop(true);
                    this.primaryStage.centerOnScreen();

                }
                case NICKNAME -> {
                //TODO
                }
                case MENU -> {
                    this.primaryStage.centerOnScreen();
                    this.primaryStage.setAlwaysOnTop(false);
                    MenuController controller = (MenuController) s.getControllerGUI();
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









}