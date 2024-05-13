package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.flow.GameFlow;
import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//è la classe principale della GUI che è usata per avviare la GUI
//estende la classe Application che fa parte di JavaFX e serve a fornire una infrastruttura per la creazione delle applicazioni
// contiene i metodi per cambiare scena
public class GUIApplication extends Application {

    private GameFlow gameFlow;
    private Stage primaryStage;
    private StackPane root; //radice dell'interfaccia utente

    //metodo principe della classe Application che viene chiamato quando l'applicazione JavaFX viene avviata
    @Override
    public void start(Stage primaryStage) {

    }
}
