package it.polimi.ingsw.view.GUI.scenes;

//enumerazione che contiene i nomi dei file FXML associati alle diverse scene
public enum SceneType {
    MENU("/Menu.fxml");

    //TODO


    private final String path; //contiene il percorso dei file FXML associati alle scene

    //costruttore
    SceneType(final String path){
        this.path = path;
    }


    //metodo per restituire il percorso del file FXML
    public String path(){
        return path;
    }
}
