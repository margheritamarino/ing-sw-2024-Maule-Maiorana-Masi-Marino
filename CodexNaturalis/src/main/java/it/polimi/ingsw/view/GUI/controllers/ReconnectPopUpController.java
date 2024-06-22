package it.polimi.ingsw.view.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller class for handling reconnection popup
 */
public class ReconnectPopUpController extends ControllerGUI{

    @FXML
    Button returnBtn;
    @FXML
    Button YesBtn;

    /**
     * Handles the action when the user clicks the return button.
     *
     * @param actionEvent the action event triggering the method call
     */
    public void ReturnOnClick(ActionEvent actionEvent) {
        getInputGUI().addTxt("return");
    }

    /**
     * Handles the action when the user clicks the reconnect button.
     *
     * @param actionEvent the action event triggering the method call
     */
    public void ReconnectOnClick(ActionEvent actionEvent) {
        getInputGUI().addTxt("YES");
    }
}
