package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.ScoreTrack;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.GUI.GUIApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ScoretrackPopupController extends ControllerGUI {

    @FXML
    private Text nick0;
    @FXML
    private Text nick1;
    @FXML
    private Text nick2;
    @FXML
    private Text nick3;
    @FXML
    private Text points0;
    @FXML
    private Text points1;
    @FXML
    private Text points2;
    @FXML
    private Text points3;

    private GUIApplication guiApplication;

    public void setGUIApplication(GUIApplication guiApplication) {
        this.guiApplication = guiApplication;
    }


    public void setUsername(String nickname, int indexPlayer) {
        switch (indexPlayer) {
            case 0 -> nick0.setText(nickname);
            case 1 -> nick1.setText(nickname);
            case 2 -> nick2.setText(nickname);
            case 3 -> nick3.setText(nickname);
            default -> System.out.println("Invalid player index: " + indexPlayer);
        }
    }

    public void setPoints(int points, int indexPlayer) {
        switch (indexPlayer) {
            case 0 -> points0.setText(String.valueOf(points));
            case 1 -> points1.setText(String.valueOf(points));
            case 2 -> points2.setText(String.valueOf(points));
            case 3 -> points3.setText(String.valueOf(points));
            default -> System.out.println("Invalid player index: " + indexPlayer);
        }
    }

    public void setScoreTrack(GameImmutable model) {

        ScoreTrack scoretrack= model.getScoretrack();

        ArrayList<Player> playersWithPoints = scoretrack.getPlayersByScore();

        for (int i = 0; i < playersWithPoints.size(); i++) {
            Player player = playersWithPoints.get(i);
            setUsername(player.getNickname(), i);
            setPoints(scoretrack.getPlayerScore(player), i);
        }
    }
    @FXML
    private void handleCloseAction(ActionEvent event) {
        this.guiApplication.closePopUpStage();
    }

}


