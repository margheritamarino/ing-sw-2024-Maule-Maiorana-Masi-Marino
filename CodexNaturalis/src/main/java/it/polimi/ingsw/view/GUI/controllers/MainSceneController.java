package it.polimi.ingsw.view.GUI.controllers;


import it.polimi.ingsw.Chat.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class MainSceneController extends ControllerGUI{

    @FXML
    private Label labelMessage;
    @FXML
    private TextField messageText;
    @FXML
    private ListView chatList;
    @FXML
    private ComboBox boxMessage;
    @FXML
    private Text GameIDTextField;


    @FXML
    private Label youPoints;
    @FXML
    private Label personalObjective;
    @FXML
    private Button otherPlayers;
    @FXML
    private ImageView actionSendMessage;

    /**
     * Method to set game id lable
     * @param id the id to show
     */
    public void setGameid(int id) {
        GameIDTextField.setText("GameID: "+id);
    }


    /**
     * This method manage the sending of the message
     *
     * @param e the mouse event
     */
    public void actionSendMessage(MouseEvent e) {
        if (!messageText.getText().isEmpty()) {

            if (boxMessage.getValue().toString().isEmpty()) {
                getInputGUI().addTxt("/c " + messageText.getText());
            } else {
                //Player wants to send a private message
                getInputGUI().addTxt("/cs " + boxMessage.getValue().toString() + " " + messageText.getText());
                boxMessage.getSelectionModel().selectFirst();
            }
            messageText.setText("");

        }
    }

    /**
     * This method manage the click on the button to send the message
     *
     * @param ke the key event
     */
    public void chatOnClick(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            actionSendMessage(null);
        }
    }

    /**
     * This method set the color of a message
     * @param msg the message to show
     * @param success if the message is a success or not
     */
    public void setMsgToShow(String msg, Boolean success) {
        labelMessage.setText(msg);
        if (success == null) {
            labelMessage.setTextFill(Color.WHITE);
        } else if (success) {
            labelMessage.setTextFill(Color.GREEN);
        } else {
            labelMessage.setTextFill(Color.RED);
        }
    }

    /**
     * This method set the message in the correct format
     * @param msgs the list of messages
     * @param myNickname the nickname of the player
     */
    public void setMessage(List<Message> msgs, String myNickname) {
        chatList.getItems().clear();
        for (Message m : msgs) {
            String r = "[" + m.getTime().getHour() + ":" + m.getTime().getMinute() + ":" + m.getTime().getSecond() + "] " + m.getSender().getNickname() + ": " + m.getText();

            if (m.whoIsReceiver().equals("*")) {
                chatList.getItems().add(r);
            } else if (m.whoIsReceiver().toUpperCase().equals(myNickname.toUpperCase()) || m.getSender().getNickname().toUpperCase().equals(myNickname.toUpperCase())) {
                //Message private
                chatList.getItems().add("[Private] " + r);
            }
        }
    }








    @FXML
    void viewPlayersBook(ActionEvent event) {
        // Logica per visualizzare i dettagli degli altri giocatori
        System.out.println("Visualizza i dettagli degli altri giocatori");
    }

}
