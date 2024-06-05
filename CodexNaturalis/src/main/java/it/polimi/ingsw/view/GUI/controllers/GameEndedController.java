package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class GameEndedController extends ControllerGUI {
    @FXML
    private Label player0;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;

    @FXML
    private Button btnMenu;

    /**
     * Show everything that is needed.
     * @param model the model
     */
    public void show(GameImmutable model) {
        player0.setVisible(false);
        player0.setTextFill(Color.YELLOW);

        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        btnMenu.setVisible(true);

        int i=0;
        Label tmp = null;

        //TODO

    }

    /**
     * Show the button to return to the menu.
     */
    public void showBtnReturnToMenu() {
        btnMenu.setVisible(true);//not necessary
    }

    /**
     * Method to control the action
     * @param e ActionEvent
     */
    public void actionReturnMenu(ActionEvent e){
        getInputGUI().addTxt("a");
    }

}
