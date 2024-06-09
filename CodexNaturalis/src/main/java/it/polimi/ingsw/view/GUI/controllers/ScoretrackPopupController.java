package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.ScoreTrack;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.GUI.GUIApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    private Button[] btnPoints = new Button[21];
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

    private ArrayList<Player> playersWithPoints;

    private GUIApplication guiApplication;

    public void setGUIApplication(GUIApplication guiApplication) {
        this.guiApplication = guiApplication;
    }


    private void setTextColor(Text text, Color color) {
        switch (color) {
            case YELLOW:
                text.setStyle("-fx-fill: yellow;");
                break;
            case RED:
                text.setStyle("-fx-fill: red;");
                break;
            case BLUE:
                text.setStyle("-fx-fill: blue;");
                break;
            case GREEN:
                text.setStyle("-fx-fill: green;");
                break;
            default:
                text.setStyle("-fx-fill: black;"); // Default to black if no color matches
                break;
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

    public void setPoints(int points, int indexPlayer, Player currentPlayer) {
        switch (indexPlayer) {
            case 0 -> points0.setText(String.valueOf(points));
            case 1 -> points1.setText(String.valueOf(points));
            case 2 -> points2.setText(String.valueOf(points));
            case 3 -> points3.setText(String.valueOf(points));
            default -> System.out.println("Invalid player index: " + indexPlayer);
        }
        // Verifica se l'indice del giocatore corrente corrisponde all'indice del giocatore nel ciclo
        if (indexPlayer == playersWithPoints.indexOf(currentPlayer)) {
            // Resetta tutti i bottoni prima di evidenziare quello corretto
            Button[] buttons = {
                    btnPoints0, btnPoints1, btnPoints2, btnPoints3, btnPoints4,
                    btnPoints5, btnPoints6, btnPoints7, btnPoints8, btnPoints9,
                    btnPoints10, btnPoints11, btnPoints12, btnPoints13, btnPoints14,
                    btnPoints15, btnPoints16, btnPoints17, btnPoints18, btnPoints19,
                    btnPoints20
            };
            for (Button button : buttons) {
                resetButtonStyle(button);
            }

            // Evidenzia il bottone corrispondente ai punti
            if (points >= 0 && points <= 20) {
                highlightButton(buttons[points]);
            }
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

        ScoreTrack scoretrack= model.getScoretrack();
        playersWithPoints = scoretrack.getPlayersByScore();

        for (int i = 0; i < playersWithPoints.size(); i++) {
            Player player = playersWithPoints.get(i);
            setUsername(player, i);
            updatePlayerScore(player, scoretrack.getPlayerScore(player), model);
            setPoints(scoretrack.getPlayerScore(player), i, model.getCurrentPlayer());
        }
        // Imposta l'effetto glow del colore del giocatore per il relativo bottone
        for (int i = 0; i < playersWithPoints.size(); i++) {
            Player player = playersWithPoints.get(i);
            switch (player.getPlayerColor()) {
                case YELLOW:
                    btnPoints[scoretrack.getPlayerScore(player)].getStyleClass().add("button-glow-yellow");
                    break;
                case RED:
                    btnPoints[scoretrack.getPlayerScore(player)].getStyleClass().add("button-glow-red");
                    break;
                case BLUE:
                    btnPoints[scoretrack.getPlayerScore(player)].getStyleClass().add("button-glow-blue");
                    break;
                case GREEN:
                    btnPoints[scoretrack.getPlayerScore(player)].getStyleClass().add("button-glow-green");
                    break;
                default:
                    break;
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
        button.getStyleClass().remove("button-glow");
    }

    public void updatePlayerScore(Player player, int newScore, GameImmutable model) {
        ScoreTrack scoretrack = model.getScoretrack();
         // Rimuovi il punteggio del giocatore dalla lista dei punteggi
        int oldScore = scoretrack.getPlayerScore(player);
        int index = playersWithPoints.indexOf(player);
        if (index != -1) {
            setPoints(newScore, index, model.getCurrentPlayer()); // Aggiorna il punteggio nel controller
            resetButtonStyle(btnPoints[oldScore]); // Rimuovi lo stile glow dal vecchio punteggio
            highlightButton(btnPoints[newScore]); // Evidenzia il nuovo punteggio
        }
        scoretrack.addPoints(player, newScore);
    }
}


