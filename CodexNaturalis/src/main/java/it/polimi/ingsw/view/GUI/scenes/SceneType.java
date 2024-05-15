package it.polimi.ingsw.view.GUI.scenes;

/**
 * SceneType class. It is used to store the paths of the FXML files of the scenes.
 */
public enum SceneType {
    PUBLISHER("/Publisher.fxml"),
    NICKNAME("/Nickname.fxml"),
    MENU("/Menu.fxml"),
    LOBBY("/Lobby.fxml"),
    INGAME("/InGame.fxml"),
    OTHERSBOOK("/OthersBook.fxml"),
    GAMEWAIT("/GameWait.fxml"),
    GAMEENDED("/GameEnded.fxml");



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
