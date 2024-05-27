package it.polimi.ingsw.view.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class LobbyController extends ControllerGUI{
    @FXML
    private Text nick1;
    @FXML
    private Text nick2;
    @FXML
    private Text nick3;
    @FXML
    private Text nick0;
    @FXML
    private Button btnReady;

    @FXML
    private Text GameIDTextField;
    @FXML
    private ImageView imgPlayer1;

    @FXML
    private ImageView imgPlayer2;

    @FXML
    private ImageView imgPlayer3;

    @FXML
    private ImageView imgPlayer0;

    /**
     * Method to control the ready button.
     *
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionReady(ActionEvent e) throws IOException {
        getInputGUI().addTxt("y");

    }

    /**
     * Method to set the nickname label
     * @param nickname the nickname
     */
    public void setUsername(String nickname, int indexPlayer){
        switch (indexPlayer) {
            case 0 -> nick0.setText(nickname);
            case 1 -> nick1.setText(nickname);
            case 2 -> nick2.setText(nickname);
            case 3 -> nick3.setText(nickname);
            default -> System.out.println("Invalid player index: " + indexPlayer);
        }
    }

    /**
     * Method to set game id lable
     * @param id the id to show
     */
    public void setGameid(int id) {
        GameIDTextField.setText("GameID: "+id);
    }

    /**
     * method to set the visibility of the ready button
     *
     * @param visibility the visibility
     */
    public void setVisibleBtnReady(boolean visibility){
        btnReady.setVisible(visibility);
    }
    public void setPlayerImage(String imagePath, int indexPlayer) {
        Image image = new Image(imagePath);
        switch (indexPlayer) {
            case 0 -> imgPlayer0.setImage(image);
            case 1 -> imgPlayer1.setImage(image);
            case 2 -> imgPlayer2.setImage(image);
            case 3 -> imgPlayer3.setImage(image);
            default -> System.out.println("Invalid player index: " + indexPlayer);
        }
    }

}
