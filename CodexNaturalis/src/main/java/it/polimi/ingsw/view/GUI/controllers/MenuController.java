package it.polimi.ingsw.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MenuController extends ControllerGUI {

    @FXML
    Pane Menu;
    private TextField playersNumber;
    private TextField gameID;

    /**
     * Method to control the nickname.
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
