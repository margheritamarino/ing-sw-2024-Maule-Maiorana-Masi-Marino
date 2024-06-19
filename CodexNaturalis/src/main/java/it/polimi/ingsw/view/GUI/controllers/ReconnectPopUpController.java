package it.polimi.ingsw.view.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ReconnectPopUpController extends ControllerGUI{

    @FXML
    Button returnBtn;
    @FXML
    Button YesBtn;
    public void ReturnOnClick(ActionEvent actionEvent) {
        getInputGUI().addTxt("return");
    }


    public void ReconnectOnClick(ActionEvent actionEvent) {
        getInputGUI().addTxt("YES");
    }
}
