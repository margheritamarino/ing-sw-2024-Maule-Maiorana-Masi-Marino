package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.game.GameImmutable;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
public class OrderPlayersPopUp extends ControllerGUI{

    public Text player0;
    public Text player1;
    public Text player2;
    public Text player3;
    private Text[] playerTexts;
    @FXML
    public void initialize() {
        playerTexts = new Text[] { player0, player1, player2, player3 };
    }
    public void setOrderListText(GameImmutable model) {
        int[] orderArray = model.getOrderArray();
        String currentPlayerNickname = model.getNicknameCurrentPlaying();

        for (int i = 0; i < orderArray.length; i++) {
            String nickname = model.getPlayers().get(orderArray[i]).getNickname();
            playerTexts[i].setText("["+(i + 1) + "]" + nickname);

            // Reset the effects
            playerTexts[i].setEffect(null);

            // Highlight the current player
            if (nickname.equals(currentPlayerNickname)) {
                highlightText(playerTexts[i]);
            }
        }
    }

    private void highlightText(Text text) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        dropShadow.setColor(Color.YELLOW);
        text.setEffect(dropShadow);
    }

}
