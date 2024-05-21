package it.polimi.ingsw.view.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;

public class GameIDController extends ControllerGUI{
    @FXML
    private TextField numGameId; //campo in cui l'utente inserisce il proprio nome

    /**
     * Method to control the nickname.
     * @param e the action event
     * @throws IOException if there are connection problems
     */
    public void actionEnter(ActionEvent e) throws IOException {
        if (!numGameId.getText().isEmpty()) {
            getInputGUI().addTxt(numGameId.getText());
        }
    }
}
