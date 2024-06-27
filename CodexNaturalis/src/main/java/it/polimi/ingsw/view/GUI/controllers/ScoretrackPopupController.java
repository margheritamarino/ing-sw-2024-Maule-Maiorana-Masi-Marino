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

import java.util.List;

/**
 * Controller class for managing the scoretrack popup
 */
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
    @FXML
    private Button btnPoints21;
    @FXML
    private Button btnPoints22;
    @FXML
    private Button btnPoints23;
    @FXML
    private Button btnPoints24;
    @FXML
    private Button btnPoints25;
    @FXML
    private Button btnPoints26;
    @FXML
    private Button btnPoints27;
    @FXML
    private Button btnPoints28;
    @FXML
    private Button btnPoints29;
    private List<Player> playersWithPoints;
    private GUIApplication guiApplication;


    /**
     * Sets the GUIApplication instance for handling popup stage operations.
     *
     * @param guiApplication the GUIApplication instance
     */
    public void setGUIApplication(GUIApplication guiApplication) {
        this.guiApplication = guiApplication;
    }

    /**
     * Sets the text color for a Text object based on a Color enumeration.
     *
     * @param text the Text object to set the color for
     * @param color the Color enumeration representing the desired color
     */
    private void setTextColor(Text text, Color color) {
        switch (color) {
            case YELLOW -> text.setStyle("-fx-fill: yellow;");
            case RED -> text.setStyle("-fx-fill: red;");
            case BLUE -> text.setStyle("-fx-fill: blue;");
            case GREEN -> text.setStyle("-fx-fill: green;");
            default -> text.setStyle("-fx-fill: black;");
        }
    }

    /**
     * Sets the username and text color for the player at the specified index.
     *
     * @param player the Player object containing nickname and color information
     * @param indexPlayer the index of the player (0 to 3)
     */
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

    /**
     * Sets the points for the player at the specified index.
     *
     * @param points the points to set
     * @param indexPlayer the index of the player (0 to 3)
     */
    public void setPoints(int points, int indexPlayer) {
        switch (indexPlayer) {
            case 0 -> points0.setText(String.valueOf(points));
            case 1 -> points1.setText(String.valueOf(points));
            case 2 -> points2.setText(String.valueOf(points));
            case 3 -> points3.setText(String.valueOf(points));
            default -> System.out.println("Invalid player index: " + indexPlayer);
        }
    }

    /**
     * Initializes the button array for score points.
     */
    @FXML
    private void initialize() {
        btnPoints = new Button[] {
                btnPoints0, btnPoints1, btnPoints2, btnPoints3, btnPoints4,
                btnPoints5, btnPoints6, btnPoints7, btnPoints8, btnPoints9,
                btnPoints10, btnPoints11, btnPoints12, btnPoints13, btnPoints14,
                btnPoints15, btnPoints16, btnPoints17, btnPoints18, btnPoints19,
                btnPoints20, btnPoints21, btnPoints22, btnPoints23, btnPoints24,
                btnPoints25, btnPoints26, btnPoints27, btnPoints28, btnPoints29
        };
    }

    /**
     * Sets the score track for the popup, updating player names, scores,
     * and button glows based on current score track information.
     *
     * @param model the GameImmutable model containing score track information
     */
    public void setScoreTrack(GameImmutable model) {
        ScoreTrack scoretrack = model.getScoretrack();
        playersWithPoints = scoretrack.getPlayersByScore();

        for (int i = 0; i < playersWithPoints.size(); i++) {
            Player player = playersWithPoints.get(i);
            setUsername(player, i);
            setPoints(scoretrack.getPlayerScore(player), i);
        }

        updateButtonGlow(scoretrack);
        updateAllPlayerScores(model);
    }

    /**
     * Updates the button glow based on the current score track.
     *
     * @param scoretrack the ScoreTrack object containing score information
     */
    private void updateButtonGlow(ScoreTrack scoretrack) {
        for (Button button : btnPoints) {
            resetButtonStyle(button);
        }
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

    /**
     * Handles the action when the popup is closed.
     *
     * @param event the action event triggering the method call
     */
    @FXML
    private void handleCloseAction(ActionEvent event) {
        this.guiApplication.closePopUpStage();
    }

    /**
     * Highlights a specific button with a glow effect.
     *
     * @param button the Button object to highlight
     */
    private void highlightButton(Button button) {
        button.getStyleClass().add("button-glow");
    }

    /**
     * Resets the style of a button, removing any glow effects.
     *
     * @param button the Button object to reset
     */
    private void resetButtonStyle(Button button) {
        button.getStyleClass().remove("button-glow-yellow");
        button.getStyleClass().remove("button-glow-red");
        button.getStyleClass().remove("button-glow-blue");
        button.getStyleClass().remove("button-glow-green");
    }

    /**
     * Updates scores for all players based on the current score track.
     *
     * @param model the GameImmutable model containing score track information
     */
    public void updateAllPlayerScores(GameImmutable model) {
        ScoreTrack scoretrack = model.getScoretrack();
        playersWithPoints = scoretrack.getPlayersByScore();

        for (Button button : btnPoints) {
            resetButtonStyle(button);
        }

        for (int i = 0; i < playersWithPoints.size(); i++) {
            Player player = playersWithPoints.get(i);
            int newScore = scoretrack.getPlayerScore(player);

            setPoints(newScore, i);

            if (newScore >= 0 && newScore < btnPoints.length) {
                highlightButton(btnPoints[newScore]);
            }
        }

        updateButtonGlow(scoretrack);
    }
}
