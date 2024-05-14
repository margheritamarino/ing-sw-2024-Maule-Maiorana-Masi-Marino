package it.polimi.ingsw.view.GUI;


//classe usata per gestire le informazioni riguardo le scene
public class Scene {

    private Scene scene;
    private SceneType sceneType;

    private ControllerGUI controller;

    public Scene(Scene scene, SceneType sceneType, ControllerGUI controller){
        this.scene = scene;
        this.sceneType = sceneType;
        this.controller = controller;
    }

    public Scene getScene() {
        return scene;
    }

    public SceneType getSceneType(){
        return sceneType;
    }

    public ControllerGUI getControllerGUI(){
        return controller;
    }


    //TODO

}

