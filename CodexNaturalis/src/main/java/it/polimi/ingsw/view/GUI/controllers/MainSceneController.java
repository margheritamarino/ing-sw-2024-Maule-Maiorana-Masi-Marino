package it.polimi.ingsw.view.GUI.controllers;


import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Book;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.Player;
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
    private Text labelMessage;
    @FXML
    private TextField messageText;
    @FXML
    private ListView chatList;
    @FXML
    private ComboBox<String> boxMessage;
    @FXML
    private ImageView actionSendMessage;

    @FXML
    private ImageView initialCardImg;

    //PLAYER DECK
    private PlayerDeck playerDeck;
    private boolean[] showBack = new boolean[3];
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
    private ImageView personalObjective;

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

    public void setPlayerComboBoxItems(List<String> playerNames) {
        // Rimuovi eventuali elementi precedenti dalla ComboBox
        boxMessage.getItems().clear();
        // Aggiungi l'opzione per inviare un messaggio a tutti
        boxMessage.getItems().add("All players");

        // Aggiungi i nomi dei giocatori diversi dal tuo alla ComboBox
        for (String playerName : playerNames) {
            if (!playerName.equals(nicknameTextField)) {
                boxMessage.getItems().add(playerName);
            }
        }
        // Seleziona l'opzione "All players" come predefinita
        boxMessage.getSelectionModel().selectFirst();
    }

    /**
     * This method manage the sending of the message
     *
     * @param e the mouse event
     */
    public void actionSendMessage(MouseEvent e) {
        if (e == null || e.getSource() == actionSendMessage) {
            if (!messageText.getText().isEmpty()) {
                String recipient = boxMessage.getValue();

                if (recipient.equals("All players")) {
                    getInputGUI().addTxt("/c " + messageText.getText()); // Invia a tutti
                } else {
                    getInputGUI().addTxt("/cs " + recipient + " " + messageText.getText()); // Invia al giocatore selezionato
                }

                messageText.setText("");
            }
        }
    }

    public void handleKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            actionSendMessage(null);
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
            labelMessage.setFill(Color.BLACK);
        } else if (success) {
            labelMessage.setFill(Color.GREEN);
        } else {
            labelMessage.setFill(Color.RED);
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
        try {
            String imagePath= model.getPlayerByNickname(nickname).getPlayerBook().getInitialCard().getImagePath();
            Image image = new Image(imagePath);
            initialCardImg.setImage(image);
        }catch (NullPointerException e){
            System.err.println("InitialCard Null");
        }
    }

    public void setPlayerDeck(GameImmutable model, String nickname) {
        this.playerDeck= model.getPlayerByNickname(nickname).getPlayerDeck();
        String imagePath;

        imagePath=playerDeck.getMiniDeck().getFirst().getImagePath();
        Image image = new Image(imagePath);
        deckImg0.setImage(image);
        imagePath=playerDeck.getMiniDeck().get(2).getImagePath();
        image = new Image(imagePath);
        deckImg1.setImage(image);
        imagePath=playerDeck.getMiniDeck().get(4).getImagePath();
         image = new Image(imagePath);
        deckImg2.setImage(image);
        // Initialize card state to false (showing initial images)
        showBack[0] = false;
        showBack[1] = false;
        showBack[2] = false;

    }
    public void turnCard(ActionEvent e) {
        if (e.getSource() == turnbtn0) {
            toggleCard(0, 0, 1, deckImg0);
        } else if (e.getSource() == turnbtn1) {
            toggleCard(1, 2, 3, deckImg1);
        } else if (e.getSource() == turnbtn2) {
            toggleCard(2, 4, 5, deckImg2);
        }
    }

    private void toggleCard(int cardIndex, int initialImageIndex, int alternateImageIndex, ImageView deckImg) {
        String imagePath;
        if (showBack[cardIndex]) {
            imagePath = playerDeck.getMiniDeck().get(initialImageIndex).getImagePath();
        } else {
            imagePath = playerDeck.getMiniDeck().get(alternateImageIndex).getImagePath();
        }
        showBack[cardIndex] = !showBack[cardIndex];
        deckImg.setImage(new Image(imagePath));
    }

    public void setBoard(GameImmutable model) {
        //BOARD
        Board board = model.getBoard();
        String imagePath;

        //GOLD CARD
        imagePath = board.getGoldCardsDeck().getBackCards().get(0).getImagePath();
        imgDeckGold.setImage(new Image(imagePath));
        imagePath = board.getGoldCards().get(0)[0].getImagePath();
        imgGold0.setImage(new Image(imagePath));
        imagePath = board.getGoldCards().get(1)[0].getImagePath();
        imgGold1.setImage(new Image(imagePath));

        //RESOURCE CARD
        imagePath = board.getResourcesCardsDeck().getBackCards().getFirst().getImagePath();
        imgDeckResource.setImage(new Image(imagePath));
        imagePath = board.getResourceCards().get(0)[0].getImagePath();
        imgResource0.setImage(new Image(imagePath));
        imagePath = board.getResourceCards().get(1)[0].getImagePath();
        imgResource1.setImage(new Image(imagePath));


        //OBJECTIVE CARD
        imagePath = board.getObjectiveCards()[0].getImagePath();
        imgObjective0.setImage(new Image(imagePath));
        imagePath = board.getObjectiveCards()[1].getImagePath();
        imgObjective1.setImage(new Image(imagePath));


    }

    public void setPersonalObjective(GameImmutable model, String nickname) {
        try {
            Player player = model.getPlayerByNickname(nickname);
            System.out.println("Player obtained: " +player.getNickname());
            if (player.getGoal() == null) {
                System.err.println("Player goal is null for player: " + nickname);
                return;
            }
            String imagePath = player.getGoal().getImagePath();
            Image image = new Image(imagePath);
            personalObjective.setImage(image);
        } catch (NullPointerException e) {
            System.err.println("Player goal is null (caught in exception) for player: " + nickname);
        }
    }


    public void setScoretrack(GameImmutable model){

    }
}
