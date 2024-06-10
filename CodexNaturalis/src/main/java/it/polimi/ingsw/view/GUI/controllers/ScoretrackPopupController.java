package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.ScoreTrack;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.GUI.GUIApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

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

    private Button[] btnPoints;
    @FXML
    private Button btnPoints0;
    @FXML
    private Button btnPoints1;
    @FXML
    private Button btnPoints2;
    @FXML
    private Button btnPoints3;
    @FXML
    private Button btnPoints4;
    @FXML
    private Button btnPoints5;
    @FXML
    private Button btnPoints6;
    @FXML
    private Button btnPoints7;
    @FXML
    private Button btnPoints8;
    @FXML
    private Button btnPoints9;
    @FXML
    private Button btnPoints10;
    @FXML
    private Button btnPoints11;
    @FXML
    private Button btnPoints12;
    @FXML
    private Button btnPoints13;
    @FXML
    private Button btnPoints14;
    @FXML
    private Button btnPoints15;
    @FXML
    private Button btnPoints16;
    @FXML
    private Button btnPoints17;
    @FXML
    private Button btnPoints18;
    @FXML
    private Button btnPoints19;
    @FXML
    private Button btnPoints20;

    private List<Player> playersWithPoints;

    private GUIApplication guiApplication;

    public void setGUIApplication(GUIApplication guiApplication) {
        this.guiApplication = guiApplication;
    }

    private void setTextColor(Text text, Color color) {
        switch (color) {
            case YELLOW -> text.setStyle("-fx-fill: yellow;");
            case RED -> text.setStyle("-fx-fill: red;");
            case BLUE -> text.setStyle("-fx-fill: blue;");
            case GREEN -> text.setStyle("-fx-fill: green;");
            default -> text.setStyle("-fx-fill: black;"); // Default to black if no color matches
        }
    }

    public void setUsername(Player player, int indexPlayer) {
        String nickname = player.getNickname();
        Color color = player.getPlayerColor();
        switch (indexPlayer) {
            case 0 -> {
                nick0.setText(nickname);
                setTextColor(nick0, color);
            }
            case 1 -> {
                nick1.setText(nickname);
                setTextColor(nick1, color);
            }
            case 2 -> {
                nick2.setText(nickname);
                setTextColor(nick2, color);
            }
            case 3 -> {
                nick3.setText(nickname);
                setTextColor(nick3, color);
            }
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

    @FXML
    private void initialize() {
        btnPoints = new Button[] {
                btnPoints0, btnPoints1, btnPoints2, btnPoints3, btnPoints4,
                btnPoints5, btnPoints6, btnPoints7, btnPoints8, btnPoints9,
                btnPoints10, btnPoints11, btnPoints12, btnPoints13, btnPoints14,
                btnPoints15, btnPoints16, btnPoints17, btnPoints18, btnPoints19,
                btnPoints20
        };
    }

    public void setScoreTrack(GameImmutable model) {
        ScoreTrack scoretrack = model.getScoretrack();
        playersWithPoints = scoretrack.getPlayersByScore();

        for (int i = 0; i < playersWithPoints.size(); i++) {
            Player player = playersWithPoints.get(i);
            setUsername(player, i);
            setPoints(scoretrack.getPlayerScore(player), i);
        }

        updateButtonGlow(scoretrack);
    }

    private void updateButtonGlow(ScoreTrack scoretrack) {
        // Resetta lo stile di tutti i bottoni
        for (Button button : btnPoints) {
            resetButtonStyle(button);
        }

        // Imposta l'effetto glow del colore del giocatore per il relativo bottone
        for (Player player : playersWithPoints) {
            int playerScore = scoretrack.getPlayerScore(player);
            switch (player.getPlayerColor()) {
                case YELLOW -> btnPoints[playerScore].getStyleClass().add("button-glow-yellow");
                case RED -> btnPoints[playerScore].getStyleClass().add("button-glow-red");
                case BLUE -> btnPoints[playerScore].getStyleClass().add("button-glow-blue");
                case GREEN -> btnPoints[playerScore].getStyleClass().add("button-glow-green");
                default -> {}
            }
        }
    }

    @FXML
    private void handleCloseAction(ActionEvent event) {
        this.guiApplication.closePopUpStage();
    }

    private void highlightButton(Button button) {
        button.getStyleClass().add("button-glow");
    }

    private void resetButtonStyle(Button button) {
        button.getStyleClass().remove("button-glow-yellow");
        button.getStyleClass().remove("button-glow-red");
        button.getStyleClass().remove("button-glow-blue");
        button.getStyleClass().remove("button-glow-green");
    }

    public void updatePlayerScore(Player player, int newScore, GameImmutable model) {
        ScoreTrack scoretrack = model.getScoretrack();
        int oldScore = scoretrack.getPlayerScore(player);
        int index = playersWithPoints.indexOf(player);
        if (index != -1) {
            // Aggiorna il punteggio nel modello
            scoretrack.setPlayerScore(player, newScore);

            // Aggiorna il punteggio nel controller
            setPoints(newScore, index);

            // Resetta lo stile del vecchio bottone
            if (oldScore >= 0 && oldScore < btnPoints.length) {
                resetButtonStyle(btnPoints[oldScore]);
            }

            // Applica lo stile al nuovo bottone
            if (newScore >= 0 && newScore < btnPoints.length) {
                highlightButton(btnPoints[newScore]);
            }

            // Aggiorna il glow dei bottoni
            updateButtonGlow(scoretrack);
        }
    }
}
