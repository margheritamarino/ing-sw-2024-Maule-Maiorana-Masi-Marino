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
    INITIALIZE_CARDS("/it.polimi.ingsw.view.GUI/fxml/InitializeCards.fxml"),
    MAINSCENE("/it.polimi.ingsw.view.GUI/fxml/MainScene.fxml"),
    BOARD_POPUP("/it.polimi.ingsw.view.GUI/fxml/BoardPopUp.fxml"),
    SCORETRACK_POPUP("/it.polimi.ingsw.view.GUI/fxml/ScoretrackPopUp.fxml"),
    WAITING_POPUP("/it.polimi.ingsw.view.GUI/fxml/WaitingPopUp.fxml"),
    GAMEENDED("/it.polimi.ingsw.view.GUI/fxml/GameEnded.fxml"),
    GENERIC_ERROR("/it.polimi.ingsw.view.GUI/fxml/GenericError.fxml"),

    RECONNECT_POPUP("/it.polimi.ingsw.view.GUI/fxml/ReconnectionPopUp.fxml");

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
