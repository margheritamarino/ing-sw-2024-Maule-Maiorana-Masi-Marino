package it.polimi.ingsw.view.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class GenericErrorController extends ControllerGUI {

    @FXML
    private TextArea message; //area di testo che mostra il messaggio d'errore
    @FXML
    private Button button; //per chiudere l'app o ritornare alla schermata insert nickname per entrare in un nuovo gioco
    private boolean needToExitApp; //variabile per definire se l'applicazione deve essere chiusa quando il pulsante viene premuto

    /**
     * Method to control the action
     * @param e ActionEvent
     */
    public void actionMenu(ActionEvent e){
        if(!needToExitApp) {
            getInputGUI().addTxt("a");
        }else{
            //chiude l'applicazione
            System.exit(-1);
        }
    }



    /**
     * Method to set the message
     * @param msg the error message
     * @param needToExitApp true if the app needs to be closed
     */
    public void setMsg(String msg, boolean needToExitApp){
        this.message.setText(msg);
        if(needToExitApp){
            button.setText("Close App");
        }
        this.needToExitApp=needToExitApp;

    }

}
