package it.polimi.ingsw.view.GUI.scenes;


import it.polimi.ingsw.view.GUI.controllers.ControllerGUI;
import it.polimi.ingsw.view.Utilities.InputGUI;
import javafx.scene.Scene;

//classe usata per gestire le informazioni riguardo le scene
public class SceneInformation {

    private Scene scene;
    private SceneType sceneType;

    private ControllerGUI controller;

    /**
     * Constructor of the class.
     * @param scene the scene {@link Scene}
     * @param sceneType the scene enum {@link SceneType}
     * @param controller the generic controller {@link ControllerGUI}
     */
    public SceneInformation(Scene scene, SceneType sceneType, ControllerGUI controller){
        this.scene = scene;
        this.sceneType = sceneType;
        this.controller = controller;
    }

    //restituisce l'istanza della scena
    public Scene getScene() {
        return scene;
    }

    public SceneType getSceneType(){
        return sceneType;
    }

    public ControllerGUI getControllerGUI(){
        return controller;
    }

    //imposta l'inputGUI per il controller associato alla scena
    public void setInputGUI(InputGUI inputGUI){
        if(controller!=null){
            controller.setInputGUI(inputGUI);
        }
    }

}

