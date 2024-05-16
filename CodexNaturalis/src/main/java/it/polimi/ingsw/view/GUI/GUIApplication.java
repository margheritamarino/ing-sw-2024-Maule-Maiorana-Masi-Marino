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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

//estende la classe Application che fa parte di JavaFX e serve a fornire un'infrastruttura per la creazione delle applicazioni
// contiene i metodi per cambiare scena
public class GUIApplication extends Application {

    private GameFlow gameFlow;
    private Stage primaryStage;
    private StackPane root; //radice dell'interfaccia utente
    private ArrayList<SceneInformation> scenes;
    private boolean resizing=true;


    //metodo principe della classe Application che viene chiamato quando l'applicazione JavaFX viene avviata
    @Override
    public void start(Stage primaryStage) {
        gameFlow = new GameFlow(ConnectionType.valueOf(getParameters().getUnnamed().get(0))); //TODO
        loadScenes(); //carica le scene
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Codex Naturalis");
        root = new StackPane();

        //si può aggiungere un suono di apertura
    }


    //carico tutte le scene disponibili nell'elenco scenes tramite FXMLLoader
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


    //imposta l'inputGUI per tutti i controller delle scene
    public void setInputReaderGUItoAllControllers(InputGUI inputGUI) {
        loadScenes();
        for (SceneInformation s : scenes) {
            s.setInputGUI(inputGUI);
        }
    }

    //ritorna il controller di una specifica scena
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

    public void setActiveScene(SceneType scene) {
        this.primaryStage.setTitle("MyShelfie - " + scene.name()); //imposta il titolo della finestra principale aggiungendo il nome della scena attiva
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

                }
                case MENU -> {
                    this.primaryStage.centerOnScreen();
                    this.primaryStage.setAlwaysOnTop(false);
                    MenuController controller = (MenuController) s.getControllerGUI();
                }





            }
        }
    }






}