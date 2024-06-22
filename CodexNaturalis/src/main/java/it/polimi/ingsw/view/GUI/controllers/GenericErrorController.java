package it.polimi.ingsw.view.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Controller class for handling generic error messages in the GUI.
 * This controller manages displaying an error message and optionally exiting the application.
 */
public class GenericErrorController extends ControllerGUI {

    @FXML
    private TextArea message;
    @FXML
    private Button button;
    private boolean needToExitApp;

    /**
     * Method to control the action
     * @param e ActionEvent
     */
    public void actionMenu(ActionEvent e){
        if(!needToExitApp) {
            getInputGUI().addTxt("a");
        }else{
            System.exit(-1);
        }
    }



    /**
     * Sets the error message and determines whether the application should be closed.
     *
     * @param msg           the error message to display
     * @param needToExitApp true if the application should be closed, false otherwise
     */
    public void setMsg(String msg, boolean needToExitApp){
        this.message.setText(msg);
        if(needToExitApp){
            button.setText("Close App");
        }
        this.needToExitApp=needToExitApp;

    }

}
