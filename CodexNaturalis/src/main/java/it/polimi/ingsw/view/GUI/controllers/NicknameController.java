package it.polimi.ingsw.view.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import javafx.scene.control.TextField;


/**
 * NicknameController class.
 */
public class NicknameController extends ControllerGUI {

    @FXML
    TextField nicknameTextField;

    /**
     * Method to control the nickname.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionEnter(ActionEvent e) throws IOException {
        if (!nicknameTextField.getText().isEmpty()) {
            getInputGUI().addTxt(nicknameTextField.getText());
        }

    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets an action listener on the nicknameTextField to handle enter key presses.
     */
    @FXML
    private void initialize() {
        nicknameTextField.setOnAction(event -> {
            try {
                actionEnter(new ActionEvent());
            } catch (IOException e) {
                System.err.println("Error during actionEnter");
            }
        });
    }
}
