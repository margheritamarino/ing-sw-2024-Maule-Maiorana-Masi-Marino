package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.ConnectionType;
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
    private ArrayList<Scene> scenes;



    //metodo principe della classe Application che viene chiamato quando l'applicazione JavaFX viene avviata
    @Override
    public void start(Stage primaryStage) {
        //gameFlow = new GameFlow(this, ConnectionType.valueOf(getParameters().getUnnamed().get(0)));
        loadScenes(); //carica le scene
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Codex Naturalis");
        root = new StackPane();

        //si può aggiungere un suono di apertura
    }


    //carico tutte le scene disponibili nell'elenco scenes tramite FXMLLoader
    private void loadScenes(){
        scenes = new ArrayList<>();
        FXMLLoader loader; //formato per definire l'interfaccia utente dell'applicazione JavaFX in modo dichiarativo e che associa un controller ad ogni vista caricata
        Parent root;
        ControllerGUI controller;

        //TODO


    }

}
