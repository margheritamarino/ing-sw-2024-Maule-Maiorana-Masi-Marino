package it.polimi.ingsw.view.GUI.scenes;

/**
 * SceneType class. It is used to store the paths of the FXML files of the scenes.
 */
public enum SceneType {
    PUBLISHER("/Publisher.fxml"),
    NICKNAME("/Nickname.fxml"),
    NICKNAME_POPUP("/NicknamePopUp.fxml"), //popup with nickname
    MENU("/Menu.fxml"),
    LOBBY("/Lobby.fxml"),
    INGAME("/InGame.fxml"),
    OTHERSBOOK("/OthersBook.fxml"),
    GAMEWAIT("/GameWait.fxml"),
    GAMEENDED("/GameEnded.fxml"),
    PLAYER_LOBBY0("/PlayerLobby0.fxml.fxml"),
    PLAYER_LOBBY1("/PlayerLobby1.fxml.fxml"),
    PLAYER_LOBBY2("/PlayerLobby2.fxml.fxml"),
    PLAYER_LOBBY3("/PlayerLobby3.fxml.fxml");
    private final String path; //contiene il

    // percorso dei file FXML associati alle scene

    //costruttore
    SceneType(final String path){
        this.path = path;
    }


    //metodo per restituire il percorso del file FXML
    public String path(){
        return path;
    }
}
