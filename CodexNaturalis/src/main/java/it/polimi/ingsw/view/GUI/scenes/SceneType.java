package it.polimi.ingsw.view.GUI.scenes;

/**
 * SceneType class. It is used to store the paths of the FXML files of the scenes.
 */
public enum SceneType {
    PUBLISHER("/it/polimi/ingsw/Publisher.fxml"),
    NICKNAME("/it/polimi/ingsw/Nickname.fxml"),
    NICKNAME_POPUP("/it/polimi/ingsw/NicknamePopUp.fxml"),
    LOBBY("/it/polimi/ingsw/Lobby.fxml"),
    MENU("/it/polimi/ingsw/Menu.fxml"),
    INITIALIZE_CARDS("/it/polimi/ingsw/InitializeCards.fxml"),
    MAINSCENE("/it/polimi/ingsw/MainScene.fxml"),
    BOARD_POPUP("/it/polimi/ingsw/BoardPopUp.fxml"),
    SCORETRACK_POPUP("/it/polimi/ingsw/ScoretrackPopUp.fxml"),
    WAITING_POPUP("/it/polimi/ingsw/WaitingPopUp.fxml"),
    GAMEENDED("/it/polimi/ingsw/GameEnded.fxml"),
    GENERIC_ERROR("/it/polimi/ingsw/GenericError.fxml"),
    RECONNECT_POPUP("/it/polimi/ingsw/ReconnectionPopUp.fxml");

    private final String path;

    /**
     * Constructor for SceneType enum.
     *
     * @param path The path of the corresponding FXML file for the scene.
     */
    SceneType(final String path){
        this.path = path;
    }

    /**
     * Get the file path of the FXML file associated with the SceneType.
     *
     * @return The file path as a String.
     */
    public String path() {
        return path;
    }

}
