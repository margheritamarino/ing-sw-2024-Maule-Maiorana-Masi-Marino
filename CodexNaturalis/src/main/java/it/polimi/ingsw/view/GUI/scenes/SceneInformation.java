package it.polimi.ingsw.view.GUI.scenes;


import it.polimi.ingsw.view.GUI.controllers.ControllerGUI;
import it.polimi.ingsw.view.Utilities.InputGUI;
import javafx.scene.Scene;

/**
 * Represents information about a scene in the GUI, including the scene itself,
 * its type, and the associated controller.
 */
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

    /**
     * Retrieves the JavaFX Scene instance associated with this scene information.
     *
     * @return The Scene object representing the graphical scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Retrieves the SceneType enum value that categorizes the type of the scene.
     *
     * @return The SceneType enum value associated with this scene.
     */
    public SceneType getSceneType(){
        return sceneType;
    }

    /**
     * Retrieves the ControllerGUI instance associated with managing this scene.
     *
     * @return The ControllerGUI instance handling interactions with this scene.
     */
    public ControllerGUI getControllerGUI(){
        return controller;
    }

    /**
     * Sets the InputGUI instance for the ControllerGUI associated with this scene.
     * This allows setting user input handling for the scene's controller.
     *
     * @param inputGUI The InputGUI instance to be set for the controller.
     */
    public void setInputGUI(InputGUI inputGUI){
        if(controller!=null){
            controller.setInputGUI(inputGUI);
        }
    }

}

