package it.polimi.ingsw.view.GUI.controllers;


import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.player.PlayerDeck;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    @FXML
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
    private Button showScoretrackBtn;
    @FXML
    private Button showBoardBtn;
    private GUI gui;
    private GameImmutable model;

    //EVENTS
    @FXML
    private ListView EventsList;
    @FXML
    private AnchorPane PlayerDeckPane;


    public void setImportantEvents(List<String> importantEvents){
        for (String s : importantEvents) {
            EventsList.getItems().add(s);
        }
        EventsList.scrollTo(EventsList.getItems().size());
    }
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




    //PLAYERDECK
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
    private boolean placeCardTurnCard =false;
    public void enlargeAndHighlightPlayerDeckPane() {
        placeCardTurnCard =true;
        // Ingrandire il pane
        PlayerDeckPane.setScaleX(1.2);
        PlayerDeckPane.setScaleY(1.2);

        // Applicare l'effetto di illuminazione
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        dropShadow.setColor(Color.RED);
        PlayerDeckPane.setEffect(dropShadow);
    }

    public void resetPlayerDeckPane() {
        // Resettare la scala
        PlayerDeckPane.setScaleX(1.0);
        PlayerDeckPane.setScaleY(1.0);
        // Rimuovere l'effetto di illuminazione
        PlayerDeckPane.setEffect(null);
    }

    public void chooseCardClick(MouseEvent mouseEvent) {
        if (placeCardTurnCard) {
            int selectedIndex = -1;
            ImageView clickedImageView = null;

            if (mouseEvent.getSource() == deckImg0) {
                selectedIndex = showBack[0] ? 1 : 0;
                clickedImageView = deckImg0;
            } else if (mouseEvent.getSource() == deckImg1) {
                selectedIndex = showBack[1] ? 3 : 2;
                clickedImageView = deckImg1;
            } else if (mouseEvent.getSource() == deckImg2) {
                selectedIndex = showBack[2] ? 5 : 4;
                clickedImageView = deckImg2;
            }

            if (selectedIndex != -1) {
                getInputGUI().addTxt(String.valueOf(selectedIndex)); // Passa l'indice come stringa
                if (clickedImageView != null) {
                    clickedImageView.setImage(null); // Svuota l'immagine
                }
            }
        }
        placeCardTurnCard = false;
        resetPlayerDeckPane();
    }


//PLAYER GOAL
    public void setPersonalObjective(GameImmutable model, String nickname) {
        String imagePath = model.getPlayerGoalByNickname(nickname).getImagePath();
        Image image = new Image(imagePath);
        personalObjective.setImage(image);

    }
    // Metodo per impostare l'istanza di GUIApplication
    public void setGUI(GUI gui, GameImmutable model) {
        this.gui = gui;
        this.model =model;
    }
    public void showBoardPopUp(ActionEvent e){
        if (gui != null) {
            gui.show_board(this.model);

        }
    }

    public void showScoretrackPopUp(ActionEvent e){
        if (gui != null) {
            gui.show_scoretrack(model);
        }
    }




    //BOOK

    @FXML
    public ScrollPane bookScrollPane;

    @FXML
    public AnchorPane bookPane;
    @FXML
    public AnchorPane rootPane;

    private Cell[][] bookMatrix;
    public void updateBookPane(GameImmutable model, String nickname) {
        this.bookMatrix = model.getPlayerByNickname(nickname).getPlayerBook().getBookMatrix();
        // Pulisce il contenuto precedente
        bookPane.getChildren().clear();

        // Recupera i limiti della sotto-matrice che contiene le carte
        int[] limits = findSubMatrix();
        int minI = limits[0];
        int minJ = limits[1];
        int maxI = limits[2];
        int maxJ = limits[3];

        // Dimensioni del singolo pannello per ogni carta
        double paneWidth = 120;  // Imposta la larghezza desiderata per ogni carta
        double paneHeight = 80; // Imposta l'altezza desiderata per ogni carta

        // Calcola le dimensioni del gameBoardPane per contenere tutte le carte
        double boardWidth = (maxJ - minJ + 1) * paneWidth;
        double boardHeight = (maxI - minI + 1) * paneHeight;

        // Posizionamento centrato all'interno di gameBoardPane
        double offsetX = (bookPane.getWidth() - boardWidth) / 2;
        double offsetY = (bookPane.getHeight() - boardHeight) / 2;

        // Creare i Pane con ImageView per le carte non nulle
        for (int i = minI; i <= maxI; i++) {
            for (int j = minJ; j <= maxJ; j++) {
                Pane cardPane = new Pane();
                cardPane.setPrefSize(paneWidth, paneHeight);

                // Posizionare i Pane all'interno di gameBoardPane
                cardPane.setLayoutX((j - minJ) * paneWidth + offsetX);
                cardPane.setLayoutY((i - minI) * paneHeight + offsetY);
                //cardPane.setStyle("-fx-border-color: yellow; -fx-border-width: 2;");

                // Creare ImageView per la carta
                ImageView cardImageView = new ImageView();
                cardImageView.setFitWidth(paneWidth);
                cardImageView.setFitHeight(paneHeight);
                cardImageView.setPreserveRatio(true);
                // Imposta l'ID dell'ImageView con il numero di riga e colonna
                cardImageView.setId(i + "-" + j);
                cardImageView.setOnMouseClicked(event -> chooseCellClick(event));

                PlayableCard card = bookMatrix[i][j].getCard();
                if (card != null) {
                    Image cardImage = new Image(card.getImagePath());
                    cardImageView.setImage(cardImage);
                } else {
                    cardImageView.setImage(null);
                }
                cardPane.getChildren().add(cardImageView);
                bookPane.getChildren().add(cardPane);
            }
        }
        bookPane.layout();
        bookScrollPane.layout();
    }

    public int[] findSubMatrix() {
        int[] limits = new int[4];
        int minI = bookMatrix.length;
        int minJ = bookMatrix[0].length;
        int maxI = -1;
        int maxJ = -1;

        for (int i = 0; i < bookMatrix.length; i++) {
            for (int j = 0; j < bookMatrix[i].length; j++) {
                if (bookMatrix[i][j].getCard() != null) {
                    if (i < minI) minI = i;
                    if (j < minJ) minJ = j;
                    if (i > maxI) maxI = i;
                    if (j > maxJ) maxJ = j;
                }
            }
        }

        if (minI > 0) minI--;
        if (minJ > 0) minJ--;
        if (maxI < bookMatrix.length - 1) maxI++;
        if (maxJ < bookMatrix[0].length - 1) maxJ++;

        limits[0] = minI;
        limits[1] = minJ;
        limits[2] = maxI;
        limits[3] = maxJ;

        return limits;
    }

    //ASK PLACE CARDS - CHOOSE CELL
    //ACTION CLICKED per scegliere la riga e la colonna
    public void chooseCellClick(MouseEvent mouseEvent) {
        if(PlaceCardChooseCell){
            // Ottieni l'ID dell'ImageView cliccato
            String id = ((ImageView) mouseEvent.getSource()).getId();
            // Dividi l'ID per ottenere la riga e la colonna dell'immagine cliccata
            String[] partsID = id.split("-");
            int rowIndex = Integer.parseInt(partsID[0]);
            getInputGUI().addTxt(String.valueOf(rowIndex));
            int colIndex = Integer.parseInt(partsID[1]);
            getInputGUI().addTxt(String.valueOf(colIndex));
        }
        PlaceCardChooseCell=false;
    }
    private boolean PlaceCardChooseCell=false;
    public void highlightChooseCell(GameImmutable model, String nickname) {
        PlaceCardChooseCell=true;
        this.bookMatrix = model.getPlayerByNickname(nickname).getPlayerBook().getBookMatrix();
        // Pulisce il contenuto precedente
        bookPane.getChildren().clear();

        // Recupera i limiti della sotto-matrice che contiene le carte
        int[] limits = findSubMatrix();
        int minI = limits[0];
        int minJ = limits[1];
        int maxI = limits[2];
        int maxJ = limits[3];

        // Dimensioni del singolo pannello per ogni carta
        double paneWidth = 120;  // Imposta la larghezza desiderata per ogni carta
        double paneHeight = 80; // Imposta l'altezza desiderata per ogni carta

        // Calcola le dimensioni del gameBoardPane per contenere tutte le carte
        double boardWidth = (maxJ - minJ + 1) * paneWidth;
        double boardHeight = (maxI - minI + 1) * paneHeight;

        // Posizionamento centrato all'interno di gameBoardPane
        double offsetX = (bookPane.getWidth() - boardWidth) / 2;
        double offsetY = (bookPane.getHeight() - boardHeight) / 2;

        // Creare i Pane con ImageView per le carte non nulle
        for (int i = minI; i <= maxI; i++) {
            for (int j = minJ; j <= maxJ; j++) {
                Pane cardPane = new Pane();
                cardPane.setPrefSize(paneWidth, paneHeight);

                // Posizionare i Pane all'interno di gameBoardPane
                cardPane.setLayoutX((j - minJ) * paneWidth + offsetX);
                cardPane.setLayoutY((i - minI) * paneHeight + offsetY);
                //cardPane.setStyle("-fx-border-color: yellow; -fx-border-width: 2;");

                // Creare ImageView per la carta
                ImageView cardImageView = new ImageView();
                cardImageView.setFitWidth(paneWidth);
                cardImageView.setFitHeight(paneHeight);
                cardImageView.setPreserveRatio(true);
                // Imposta l'ID dell'ImageView con il numero di riga e colonna
                cardImageView.setId(i + "-" + j);
                cardImageView.setOnMouseClicked(event -> chooseCellClick(event));

                PlayableCard card = bookMatrix[i][j].getCard();
                if (card != null) {
                    Image cardImage = new Image(card.getImagePath());
                    cardImageView.setImage(cardImage);

                } else {
                    cardImageView.setImage(null);
                }
                if( bookMatrix[i][j].isAvailable()){
                    // Crea l'effetto DropShadow per evidenziare il contorno dell'ImageView
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setColor(Color.GREEN);
                    dropShadow.setRadius(10);
                    dropShadow.setSpread(0.5);
                    dropShadow.setOffsetX(0);
                    dropShadow.setOffsetY(0);

                    // Applica l'effetto DropShadow all'ImageView
                    cardImageView.setEffect(dropShadow);
                }
                cardPane.getChildren().add(cardImageView);
                bookPane.getChildren().add(cardPane);
            }
        }
        bookPane.layout();
        bookScrollPane.layout();
    }

}
