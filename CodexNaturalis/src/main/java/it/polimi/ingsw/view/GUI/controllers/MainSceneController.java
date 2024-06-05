package it.polimi.ingsw.view.GUI.controllers;


import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.PlayerDeck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class MainSceneController extends ControllerGUI{

    @FXML
    private Text GameIDTextField;
    @FXML
    private Text nicknameTextField;

    //CHAT
    @FXML
    private Label labelMessage;
    @FXML
    private TextField messageText;
    @FXML
    private ListView chatList;
    @FXML
    private ComboBox boxMessage;
    @FXML
    private ImageView actionSendMessage;


    //PLAYER DECK
    private PlayerDeck playerDeck;
    @FXML
    private ImageView deckImg0;
    @FXML
    private ImageView deckImg1;
    @FXML
    private ImageView deckImg2;
    @FXML
    private Button turnbtn0;
    @FXML
    private Button turnbtn1;
    @FXML
    private Button turnbtn2;
    @FXML
    private Label personalObjective;

    //BOARD
    private Board board;
    @FXML
    private ImageView imgDeckGold;
    @FXML
    private ImageView imgGold0;
    @FXML
    private ImageView imgGold1;
    @FXML
    private ImageView imgDeckResource;
    @FXML
    private ImageView imgResource0;
    @FXML
    private ImageView imgResource1;
    @FXML
    private ImageView imgDeckOnjective;
    @FXML
    private ImageView imgObjective0;
    @FXML
    private ImageView imgObjective1;

    //BOOK

    //SCORETRACK
    @FXML
    private Label youPoints;




    public void setNicknameAndID(GameImmutable model, String nickname) {
        GameIDTextField.setText("GameID: "+model.getGameId());
        nicknameTextField.setText("Nickname: "+nickname );
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



    public void setBook(GameImmutable model, String nickname) {
    }

    public void setPlayerDeck(GameImmutable model, String nickname) {
        this.playerDeck= model.getPlayerByNickname(nickname).getPlayerDeck();
        String imagePath;

        imagePath=playerDeck.getMiniDeck().get(0).getImagePath();
        Image image = new Image(imagePath);
        deckImg0.setImage(image);
        imagePath=playerDeck.getMiniDeck().get(2).getImagePath();
        image = new Image(imagePath);
        deckImg1.setImage(image);
        imagePath=playerDeck.getMiniDeck().get(4).getImagePath();
         image = new Image(imagePath);
        deckImg2.setImage(image);

    }
    public void turnCard(ActionEvent e){
        if (e.getSource() == turnbtn0) {
            String imagePath = playerDeck.getMiniDeck().get(1).getImagePath();
            deckImg0.setImage(new Image(imagePath));
        } else if (e.getSource() == turnbtn1) {

            String imagePath = playerDeck.getMiniDeck().get(3).getImagePath();
            deckImg1.setImage(new Image(imagePath));
        } else if (e.getSource() == turnbtn2) {

            String imagePath = playerDeck.getMiniDeck().get(5).getImagePath();
            deckImg2.setImage(new Image(imagePath));
        }


    }

    public void setBoard(GameImmutable model) {
        this.board = model.getBoard();
        String imagePath;

        //GOLD CARD
        imagePath = board.getGoldCardsDeck().getBackCards().get(0).getImagePath();
        imgDeckGold.setImage(new Image(imagePath));
        imagePath = board.getGoldCards().get(0)[0].getImagePath();
        imgGold0.setImage(new Image(imagePath));
        imagePath = board.getGoldCards().get(1)[0].getImagePath();
        imgGold1.setImage(new Image(imagePath));

        //RESOURCE CARD
        imagePath = board.getResourcesCardsDeck().getBackCards().get(0).getImagePath();
        imgDeckResource.setImage(new Image(imagePath));
        imagePath = board.getResourceCards().get(0)[0].getImagePath();
        imgResource0.setImage(new Image(imagePath));
        imagePath = board.getResourceCards().get(1)[0].getImagePath();
        imgResource1.setImage(new Image(imagePath));

        /*
        //OBJECTIVE CARD
        imagePath = board.getObjectiveCardsDeck().getBackCards().get(0).getImagePath();
        imgDeckOnjective.setImage(new Image(imagePath));
        imagePath = board.getObjectiveCards()[0].getImagePath();
        imgObjective0.setImage(new Image(imagePath));
        imagePath = board.getObjectiveCards()[1].getImagePath();
        imgObjective1.setImage(new Image(imagePath));

         */
    }

    public void setScoretrack(GameImmutable model) {
    }

}
