package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.flow.GameFlow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;

import javafx.scene.control.TextField;
/**
 * NicknameController class.
 */
public class NicknameController extends ControllerGUI {

    @FXML
    TextField nicknameTextField; //campo in cui l'utente inserisce il proprio nome

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
    @FXML
    private void initialize() {
        // Configura il TextField per catturare l'evento di pressione del tasto ENTER
        nicknameTextField.setOnAction(event -> {
            try {
                actionEnter(new ActionEvent());
            } catch (IOException e) {
                System.err.println("Error during actionEnter");
            }
        });
    }
}
