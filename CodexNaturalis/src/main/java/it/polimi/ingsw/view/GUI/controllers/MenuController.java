package it.polimi.ingsw.view.GUI.controllers;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.control.TextField;

/**
 * Controller class for managing the menu of the game.
 * This controller handles input fields for entering player number and game ID.
 */
public class MenuController extends ControllerGUI {

    @FXML
    private TextField playersNumber;
    @FXML
    private TextField gameID;

    /**
     * Handles the action triggered by clicking the continue button.
     * Sends the content of the playersNumber and gameID text fields to the input GUI.
     *
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionContinue(ActionEvent e) throws IOException {
        if(!playersNumber.getText().isEmpty()) {
            getInputGUI().addTxt(playersNumber.getText());
        }
        if(!gameID.getText().isEmpty()) {
            getInputGUI().addTxt(gameID.getText());
        }
    }

}
