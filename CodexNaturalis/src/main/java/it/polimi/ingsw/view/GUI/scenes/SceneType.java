package it.polimi.ingsw.view.GUI.scenes;

/**
 * SceneType class. It is used to store the paths of the FXML files of the scenes.
 */
public enum SceneType {
    PUBLISHER("/it.polimi.ingsw.view.GUI/fxml/Publisher.fxml"),
    NICKNAME("/it.polimi.ingsw.view.GUI/fxml/Nickname.fxml"),
    NICKNAME_POPUP("/it.polimi.ingsw.view.GUI/fxml/NicknamePopUp.fxml"),
    LOBBY("/it.polimi.ingsw.view.GUI/fxml/Lobby.fxml"), //popup with nickname
    MENU("/it.polimi.ingsw.view.GUI/fxml/Menu.fxml"),

     INITIALIZE_CARDS("/it.polimi.ingsw.view.GUI/fxml/InitializeCards.fxml");
  /*
    MAINSCENE("/it.polimi.ingsw.view.GUI/fxml/MainScene.fxml"),
    OTHERSBOOK("/it.polimi.ingsw.view.GUI/fxml/OthersBook.fxml"),
    GAMEWAIT("/it.polimi.ingsw.view.GUI/fxml/GameWait.fxml"),
    GAMEENDED("/it.polimi.ingsw.view.GUI/fxml/GameEnded.fxml"),
    PLAYER_LOBBY0("/it.polimi.ingsw.view.GUI/fxml/PlayerLobby0.fxml.fxml"),
    PLAYER_LOBBY1("/it.polimi.ingsw.view.GUI/fxml/PlayerLobby1.fxml.fxml"),
    PLAYER_LOBBY2("/it.polimi.ingsw.view.GUI/fxml/PlayerLobby2.fxml.fxml"),
    PLAYER_LOBBY3("/it.polimi.ingsw.view.GUI/fxml/PlayerLobby3.fxml.fxml");*/

   // GENERIC_ERROR("/it.polimi.ingsw.view.GUI/fxml/GenericErrorController.fxml");
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
