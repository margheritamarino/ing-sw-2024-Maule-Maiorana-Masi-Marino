package it.polimi.ingsw.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MenuController extends ControllerGUI {

    @FXML
    Pane CodexNaturalis;


    //metodi action associati ai bottoni

    public void actionJoinGame(ActionEvent e) throws IOException{
        getInputGUI().addTxt("y");
    }

}
