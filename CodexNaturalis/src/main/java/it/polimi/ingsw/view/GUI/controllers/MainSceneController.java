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
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



public class MainSceneController extends ControllerGUI{

    @FXML
    public Pane actionPane;
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
    @FXML
    private Stage boardPopUpStage;


    public void setImportantEvents(List<String> importantEvents){
        for (String s : importantEvents) {
            EventsList.getItems().add(s);
        }
        EventsList.scrollTo(EventsList.getItems().size());
    }
    public void setNicknameAndID(GameImmutable model, String nickname) {
        GameIDTextField.setText("GameID: "+model.getGameId());
        nicknameTextField.setText("Nickname: "+nickname );
        it.polimi.ingsw.model.Color playerColor= model.getPlayerColor(nickname);
        switch (playerColor) {
            case YELLOW:
                nicknameTextField.setStyle("-fx-fill: yellow;");
                break;
            case RED:
                nicknameTextField.setStyle("-fx-fill: red;");
                break;
            case BLUE:
                nicknameTextField.setStyle("-fx-fill: blue;");
                break;
            case GREEN:
                nicknameTextField.setStyle("-fx-fill: green;");
                break;
            default:
                nicknameTextField.setStyle("-fx-fill: black;"); // Default to black if no color matches
                break;
        }

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
    private boolean[] showBack = new boolean[3];
    public void setPlayerDeck(GameImmutable model, String nickname) {
        this.playerDeck= model.getPlayerByNickname(nickname).getPlayerDeck();
        String imagePath;

        imagePath=playerDeck.getMiniDeck().getFirst()[0].getImagePath();
        Image image = new Image(imagePath);
        deckImg0.setImage(image);
        imagePath=playerDeck.getMiniDeck().get(1)[0].getImagePath();
        image = new Image(imagePath);
        deckImg1.setImage(image);
        imagePath=playerDeck.getMiniDeck().get(2)[0].getImagePath();
         image = new Image(imagePath);
        deckImg2.setImage(image);
        // Initialize card state to false (showing initial images)
        showBack[0] = false;
        showBack[1] = false;
        showBack[2] = false;

    }
    public void turnCard(ActionEvent e) {
        if (e.getSource() == turnbtn0) {
            toggleCard(0, deckImg0);
        } else if (e.getSource() == turnbtn1) {
            toggleCard(1, deckImg1);
        } else if (e.getSource() == turnbtn2) {
            toggleCard(2, deckImg2);
        }
    }

    private void toggleCard(int cardIndex, ImageView deckImg) {
        String imagePath;
        //TODO
        if (showBack[cardIndex]) { //showBack=true (sono sul back)
            //BACK
            imagePath = playerDeck.getMiniDeck().get(cardIndex)[0].getImagePath();
            showBack[cardIndex]=false; //sono sul front oraa
        } else { //showBack=true
            //FRONT
            imagePath = playerDeck.getMiniDeck().get(cardIndex)[1].getImagePath();
            showBack[cardIndex]=true; //setto il back
        }
      //  showBack[cardIndex] = !showBack[cardIndex];
        deckImg.setImage(new Image(imagePath));
    }
    private boolean placeCardTurnCard =false;

    public void enlargeAndHighlightPlayerDeckPane(GameImmutable model, String nickname) {
        setPlayerDeck(model, nickname);
        placeCardTurnCard =true;
        // Ingrandire il pane
        PlayerDeckPane.setScaleX(1.2);
        PlayerDeckPane.setScaleY(1.2);

        PlayerDeckPane.getStyleClass().add("red-glow-pane");
    }

    public void resetPlayerDeckPane() {
        // Resettare la scala
        PlayerDeckPane.setScaleX(1.0);
        PlayerDeckPane.setScaleY(1.0);
        // Rimuovere l'effetto di illuminazione
        PlayerDeckPane.setEffect(null);
    }
    private  ImageView clickedImageView = null;
    public void chooseCardClick(MouseEvent mouseEvent) {
        if (placeCardTurnCard) {
            int selectedIndex = -1;
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
                clickedImageView.getStyleClass().add("image-view:pressed");
            }
        }
    }
    public void cardPlacedCorrect(GameImmutable model){
        if (clickedImageView != null) {
            clickedImageView.setImage(null); // Svuota l'immagine
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

    //ORDER PLAYERS
    @FXML
    public Text player0;
    @FXML
    public Text player1;
    @FXML
    public Text player2;
    @FXML
    public Text player3;
    @FXML
    public Pane orderPlayersPane;
    private Text[] playerTexts;
    @FXML
    public void initialize() {
        playerTexts = new Text[] { player0, player1, player2, player3 };
    }
    public void setOrderListText(GameImmutable model) {
        int[] orderArray = model.getOrderArray();

        for (int i = 0; i < orderArray.length; i++) {
            String nickname = model.getPlayers().get(orderArray[i]).getNickname();
            playerTexts[i].setText("["+(i + 1) + "]" + nickname);
        }
        highlightCurrentPlayer(model);
    }
    public void highlightCurrentPlayer(GameImmutable model){
        String currentPlayerNickname = model.getNicknameCurrentPlaying();
        for (int i = 0; i < model.getOrderArray().length; i++) {
            String nickname = model.getPlayers().get(model.getOrderArray()[i]).getNickname();
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
        // Crea un effetto Glow
        Glow glow = new Glow();
        glow.setLevel(0.8); // Imposta l'intensità del bagliore

        // Applica il bagliore al testo
        text.setEffect(glow);
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(0.0);
        dropShadow.setColor(Color.YELLOW);
        dropShadow.setInput(glow);
        text.setEffect(dropShadow);
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
        double paneHeight = 80;  // Imposta l'altezza desiderata per ogni carta

        // Calcola le dimensioni del bookPane per contenere tutte le carte
        double boardWidth = (maxJ - minJ + 1) * paneWidth;
        double boardHeight = (maxI - minI + 1) * paneHeight;

        // Posizionamento centrato all'interno di bookPane
        double offsetX = (bookPane.getWidth() - boardWidth) / 2;
        double offsetY = (bookPane.getHeight() - boardHeight) / 2;

        // Sovrapposizione
        double overlapX = 30;  // Numero di pixel di sovrapposizione orizzontale
        double overlapY = 30;  // Numero di pixel di sovrapposizione verticale

        // List to store cells with their positions and placement order
        List<Cell> cellList = new ArrayList<>();

        // Collect all cells with their positions and placement order
        for (int i = minI; i <= maxI; i++) {
            for (int j = minJ; j <= maxJ; j++) {
                Cell cell = bookMatrix[i][j];
                if (cell.getPlacementOrder() != -1) {
                    cellList.add(cell);
                }
            }
        }

        // Sort the cells based on placementOrder
        cellList.sort(Comparator.comparingInt(Cell::getPlacementOrder));

        // Create the Pane with ImageView for each cell in the sorted order
        for (Cell cell : cellList) {
            int i = cell.getRow();
            int j = cell.getColumn();
            PlayableCard card = cell.getCardPointer();
            int placementOrder = cell.getPlacementOrder();

            Pane cardPane = new Pane();
            cardPane.setPrefSize(paneWidth, paneHeight);

            // Posizionare i Pane all'interno di bookPane
            cardPane.setLayoutX((j - minJ) * (paneWidth - overlapX) + offsetX);
            cardPane.setLayoutY((i - minI) * (paneHeight - overlapY) + offsetY);

            ImageView cardImageView = new ImageView();
            cardImageView.setFitWidth(paneWidth);
            cardImageView.setFitHeight(paneHeight);
            cardImageView.setPreserveRatio(true);

            // Imposta l'ID dell'ImageView con il numero di riga e colonna
            cardImageView.setId(i + "-" + j);
            cardImageView.setOnMouseClicked(this::chooseCellClick);

            if (card != null) {
                Image cardImage = new Image(card.getImagePath());
                cardImageView.setImage(cardImage);

                // Imposta la profondità dello z-index in base all'ordine di piazzamento
                cardPane.setTranslateZ(placementOrder);
            } else {
                cardImageView.setImage(null);
            }
            cardPane.getChildren().add(cardImageView);
            bookPane.getChildren().add(cardPane);
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
        if (PlaceCardChooseCell) {
            Object source = mouseEvent.getSource();
            Pane clickedPane = null;

            if (source instanceof Pane) {
                clickedPane = (Pane) source;
            } else if (source instanceof ImageView) {
                // If the source is an ImageView, get its parent, assuming it's the Pane
                clickedPane = (Pane) ((ImageView) source).getParent();
            }

            if (clickedPane != null) {
                // Ottieni l'ID del Pane, che contiene la riga e la colonna
                String id = clickedPane.getId();

                if (id != null) {
                    // Dividi l'ID per ottenere riga e colonna
                    String[] partsID = id.split("-");
                    int rowIndex = Integer.parseInt(partsID[0]);
                    int colIndex = Integer.parseInt(partsID[1]);

                    // Aggiungi la riga e la colonna al testo di input
                    getInputGUI().addTxt(String.valueOf(rowIndex));
                    getInputGUI().addTxt(String.valueOf(colIndex));
                    clickedPane.getStyleClass().add("image-view:pressed");

                } else {
                    System.out.println("Pane clicked but no ID found.");
                }
            }
        }
        PlaceCardChooseCell = false;
    }

    private boolean PlaceCardChooseCell=false;
    public void highlightChooseCell(GameImmutable model, String nickname) {
        PlaceCardChooseCell=true;

        this.bookMatrix = model.getPlayerByNickname(nickname).getPlayerBook().getBookMatrix();
        // Pulisce il contenuto precedente
        bookPane.getChildren().clear();
        rootPane.getStyleClass().add("glow-pane");
        // Recupera i limiti della sotto-matrice che contiene le carte
        int[] limits = findSubMatrix();
        int minI = limits[0];
        int minJ = limits[1];
        int maxI = limits[2];
        int maxJ = limits[3];

        // Dimensioni del singolo pannello per ogni carta
        double paneWidth = 150;  // Imposta la larghezza desiderata per ogni carta
        double paneHeight = 110; // Imposta l'altezza desiderata per ogni carta

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
                cardPane.setLayoutX((j - minJ) * paneWidth + offsetX);
                cardPane.setLayoutY((i - minI) * paneHeight + offsetY);
                cardPane.setOnMouseClicked(this::chooseCellClick);
                cardPane.setId(i + "-" + j);

                //cardPane.setStyle("-fx-border-color: yellow; -fx-border-width: 2;");

                // Creare ImageView per la carta
                ImageView cardImageView = new ImageView();
                cardImageView.setFitWidth(paneWidth);
                cardImageView.setFitHeight(paneHeight);
                cardImageView.setPreserveRatio(true);
                // Imposta l'ID dell'ImageView con il numero di riga e colonna
                cardImageView.setId(i + "-" + j);

                PlayableCard card = bookMatrix[i][j].getCard();
                if (card != null) {
                    Image cardImage = new Image(card.getImagePath());
                    cardImageView.setImage(cardImage);

                } else {
                    cardImageView.setImage(null);
                }
                if( bookMatrix[i][j].isAvailable() && !bookMatrix[i][j].isWall()){
                    cardPane.setStyle("-fx-border-color: green; -fx-border-width: 3;");
                }
                cardPane.getChildren().add(cardImageView);
                bookPane.getChildren().add(cardPane);
            }
        }
        bookPane.layout();
        bookScrollPane.layout();
    }

}
