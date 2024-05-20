package it.polimi.ingsw.view.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;

/**
 * NicknameController class.
 */
public class NicknameController extends  ControllerGUI{


    @FXML
    private TextField nickname; //campo in cui l'utente inserisce il proprio nome

    /**
     * Method to control the nickname.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionEnter(ActionEvent e) throws IOException {
        if (!nickname.getText().isEmpty()) {
            getInputGUI().addTxt(nickname.getText());
        }
    }
}
