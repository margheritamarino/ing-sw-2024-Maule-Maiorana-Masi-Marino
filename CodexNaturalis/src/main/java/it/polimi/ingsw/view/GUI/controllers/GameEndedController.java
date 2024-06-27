package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.exceptions.NoPlayersException;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

/**
 * This class is the controller for the game ended scene.
 */
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
    public void showFinal(GameImmutable model) throws NoPlayersException {
        player0.setVisible(false);
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        btnMenu.setVisible(true);

        var scoreTrack = model.getScoretrack();
        var winner = model.getWinner();
        if (winner != null) {
            int i = 0;
            Label tmp = null;

            for (Player player : scoreTrack.getPlayersByScore()) {
                switch (i) {
                    case 0 -> tmp = player0;
                    case 1 -> tmp = player1;
                    case 2 -> tmp = player2;
                    case 3 -> tmp = player3;
                    default -> throw new IllegalStateException("Unexpected value: " + i);
                }

                int playerScore = scoreTrack.getPlayerScore(player);
                tmp.setText(player.getNickname() + ": " + playerScore + " points");
                tmp.setVisible(true);

                if (player == winner) {
                    tmp.setTextFill(Color.GOLD);
                    DropShadow ds = new DropShadow();
                    ds.setOffsetY(3.0f);
                    ds.setColor(Color.GOLD);
                    tmp.setEffect(ds);
                }
                i++;
            }
        }

    }

    /**
     * Show the button to return to the menu.
     */
    public void showBtnReturnToMenu() {
        btnMenu.setVisible(true);
    }

    /**
     * Method to control the action
     * @param e ActionEvent
     */
    public void actionReturnMenu(ActionEvent e){
        getInputGUI().addTxt("a");
    }

}
