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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Controller class for managing the main game scene
 */
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
    private  ImageView clickedImageView = null;

    //EVENTS
    @FXML
    private ListView EventsList;
    @FXML
    private AnchorPane PlayerDeckPane;


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

    /**
     * Sets the important events to display in the event log.
     *
     * @param importantEvents the list of important events as strings
     */
    public void setImportantEvents(List<String> importantEvents){
        for (String s : importantEvents) {
            EventsList.getItems().add(s);
        }
        EventsList.scrollTo(EventsList.getItems().size());
    }

    /**
     * Sets the nickname, game ID, and text color based on player information.
     *
     * @param model the GameImmutable model containing game information
     * @param nickname the nickname of the player
     */
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
                nicknameTextField.setStyle("-fx-fill: black;");
                break;
        }

    }

    /**
     * Sets the items in the player message combo box.
     *
     * @param playerNames the list of player names to set in the combo box
     */
    public void setPlayerComboBoxItems(List<String> playerNames) {
        boxMessage.getItems().clear();
        boxMessage.getItems().add("All players");

        for (String playerName : playerNames) {
            if (!playerName.equals(nicknameTextField)) {
                boxMessage.getItems().add(playerName);
            }
        }
        boxMessage.getSelectionModel().selectFirst();
    }

    /**
     * Manages sending a message based on user interaction.
     *
     * @param e the mouse event triggering the method call
     */
    public void actionSendMessage(MouseEvent e) {
        if (e == null || e.getSource() == actionSendMessage) {
            if (!messageText.getText().isEmpty()) {
                String recipient = boxMessage.getValue();

                if (recipient.equals("All players")) {
                    getInputGUI().addTxt("/c " + messageText.getText());
                } else {
                    getInputGUI().addTxt("/cs " + recipient + " " + messageText.getText());
                }

                messageText.setText("");
            }
        }
    }

    /**
     * Handles key press events for sending messages.
     *
     * @param e the key event triggering the method call
     */
    public void handleKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            actionSendMessage(null);
        }
    }

    /**
     * Handles key events for the chat interaction.
     *
     * @param ke the key event triggering the method call
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
            String time = "[" + m.getTime().getHour() + ":" + m.getTime().getMinute() + ":" + m.getTime().getSecond() + "] ";
            String sender = m.getSender().getNickname() + ": ";
            String text = m.getText();
            String receiver = m.whoIsReceiver();

            Text timeText = new Text(time);
            timeText.setFont(Font.font("System", FontWeight.BOLD, 12));

            Text senderText = new Text(sender);
            senderText.setFont(Font.font("System", FontWeight.BOLD, 12));

            Text messageText = new Text(text);

            TextFlow textFlow = new TextFlow();

            if (receiver.equals("*")  ) {
                // Public message
                timeText.setFill(Color.BLACK);
                senderText.setFill(Color.RED);
                messageText.setFill(Color.BLACK);

                textFlow.getChildren().addAll(timeText, senderText, messageText);
            } else if (receiver.toUpperCase().equals(myNickname.toUpperCase()) || m.getSender().getNickname().toUpperCase().equals(myNickname.toUpperCase())) {
                // Private message
                Text privateText = new Text("[Private] ");
                privateText.setFill(Color.BLUE);
                privateText.setFont(Font.font("System", FontWeight.BOLD, 12));

                timeText.setFill(Color.BLACK);
                senderText.setFill(Color.BLUE);
                messageText.setFill(Color.BLACK);

                textFlow.getChildren().addAll(privateText, timeText, senderText, messageText);
            }

            chatList.getItems().add(textFlow);
        }

    }

    //PLAYERDECK
    private boolean[] showBack = new boolean[3];

    /**
     * Sets the player deck information in the UI.
     *
     * @param model    the GameImmutable model containing game information
     * @param nickname the nickname of the player
     */
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
        showBack[0] = false;
        showBack[1] = false;
        showBack[2] = false;

    }

    /**
     * Toggles between showing the front and back of a card in the player deck.
     *
     * @param e the action event triggering the method call
     */
    public void turnCard(ActionEvent e) {
        if (e.getSource() == turnbtn0) {
            toggleCard(0, deckImg0);
        } else if (e.getSource() == turnbtn1) {
            toggleCard(1, deckImg1);
        } else if (e.getSource() == turnbtn2) {
            toggleCard(2, deckImg2);
        }
    }

    /**
     * Toggles between showing the front and back of a card in the player deck.
     *
     * @param cardIndex the index of the card in the player's deck
     * @param deckImg   the ImageView component displaying the card image
     */
    private void toggleCard(int cardIndex, ImageView deckImg) {
        String imagePath;
        if (showBack[cardIndex]) {
            //BACK
            imagePath = playerDeck.getMiniDeck().get(cardIndex)[0].getImagePath();
            showBack[cardIndex]=false;
        } else {
            //FRONT
            imagePath = playerDeck.getMiniDeck().get(cardIndex)[1].getImagePath();
            showBack[cardIndex]=true;
        }
        deckImg.setImage(new Image(imagePath));
    }
    private boolean placeCardTurnCard =false;

    /**
     * Enlarges and highlights the player deck pane for interaction.
     *
     * @param model    the GameImmutable model containing game information
     * @param nickname the nickname of the player
     */
    public void enlargeAndHighlightPlayerDeckPane(GameImmutable model, String nickname) {
        setPlayerDeck(model, nickname);
        placeCardTurnCard =true;
        // Ingrandire il pane
        PlayerDeckPane.setScaleX(1.2);
        PlayerDeckPane.setScaleY(1.2);

        PlayerDeckPane.getStyleClass().add("red-glow-pane");
    }

    /**
     * Resets the player deck pane to its default size and styling.
     */
    public void resetPlayerDeckPane() {
        PlayerDeckPane.setScaleX(1.0);
        PlayerDeckPane.setScaleY(1.0);
        PlayerDeckPane.setEffect(null);
    }

    /**
     * Handles clicking on a card in the player deck for interaction.
     *
     * @param mouseEvent the mouse event triggering the method call
     */
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
                getInputGUI().addTxt(String.valueOf(selectedIndex));
                clickedImageView.getStyleClass().add("image-view:pressed");
            }
        }
    }

    /**
     * Resets the state after a card is placed correctly.
     *
     * @param model the GameImmutable model containing game information
     */
    public void cardPlacedCorrect(GameImmutable model){
        if (clickedImageView != null) {
            clickedImageView.setImage(null);
        }
        placeCardTurnCard = false;
        resetPlayerDeckPane();
    }

    /**
     * Sets the personal objective image for the player in the UI.
     *
     * @param model    the GameImmutable model containing game information
     * @param nickname the nickname of the player
     */
    public void setPersonalObjective(GameImmutable model, String nickname) {
        String imagePath = model.getPlayerGoalByNickname(nickname).getImagePath();
        Image image = new Image(imagePath);
        personalObjective.setImage(image);

    }

    /**
     * Sets the GUI instance and the GameImmutable model for interaction.
     *
     * @param gui   the GUI instance
     * @param model the GameImmutable model containing game information
     */
    public void setGUI(GUI gui, GameImmutable model) {
        this.gui = gui;
        this.model = model;
    }

    /**
     * Shows the board popup when the corresponding button is clicked.
     *
     * @param e the action event triggering the method call
     */
    public void showBoardPopUp(ActionEvent e){
        if(e.getSource() == showBoardBtn){
            if (gui != null) {
                gui.show_board(this.model);
            }
        }
    }

    /**
     * Shows the score track popup when the corresponding button is clicked.
     *
     * @param e the action event triggering the method call
     */
    public void showScoretrackPopUp(ActionEvent e){
        if(e.getSource() == showScoretrackBtn){
            if (gui != null) {
                gui.show_scoretrack(model);
            }
        }
    }

    /**
     * Initializes the controller and sets up the player order pane.
     */
    @FXML
    public void initialize() {
        playerTexts = new Text[] { player0, player1, player2, player3 };
    }

    /**
     * Sets the text for player order display based on the game model.
     *
     * @param model the GameImmutable model containing game information
     */
    public void setOrderListText(GameImmutable model) {
        int[] orderArray = model.getOrderArray();

        for (int i = 0; i < orderArray.length; i++) {
            String nickname = model.getPlayers().get(orderArray[i]).getNickname();
            playerTexts[i].setText("["+(i + 1) + "]" + nickname);
        }
        highlightCurrentPlayer(model);
    }

    /**
     * Highlights the text of the current player in the player order display.
     *
     * @param model the GameImmutable model containing game information
     */
    public void highlightCurrentPlayer(GameImmutable model){
        String currentPlayerNickname = model.getNicknameCurrentPlaying();
        for (int i = 0; i < model.getOrderArray().length; i++) {
            String nickname = model.getPlayers().get(model.getOrderArray()[i]).getNickname();
            playerTexts[i].setEffect(null);
            if (nickname.equals(currentPlayerNickname)) {
                highlightText(playerTexts[i]);
            }
        }
    }

    /**
     * Applies a visual highlight effect to the specified Text object using DropShadow and Glow effects.
     *
     * @param text The Text object to be highlighted.
     */
    private void highlightText(Text text) {
        DropShadow dropShadow = new DropShadow();
        Glow glow = new Glow();
        glow.setLevel(0.8);

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

    /**
     * Updates the book display pane based on the player's book matrix, showing cards and managing layout dynamically.
     *
     * @param model    The GameImmutable object containing the game state.
     * @param nickname The nickname of the player whose book is being updated.
     */
    public void updateBookPane(GameImmutable model, String nickname) {
        this.bookMatrix = model.getPlayerByNickname(nickname).getPlayerBook().getBookMatrix();

        bookScrollPane.setVisible(true);
        bookScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        bookScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        bookPane.getChildren().clear();

        int[] limits = findSubMatrix();
        int minI = limits[0];
        int minJ = limits[1];
        int maxI = limits[2];
        int maxJ = limits[3];

        double paneWidth = 120;
        double paneHeight = 80;

        double overlapX = 30;
        double overlapY = 30;

        double boardWidth = (maxJ - minJ + 1) * (paneWidth - overlapX) + overlapX;
        double boardHeight = (maxI - minI + 1) * (paneHeight - overlapY) + overlapY;

        List<Cell> cellList = new ArrayList<>();

        for (int i = minI; i <= maxI; i++) {
            for (int j = minJ; j <= maxJ; j++) {
                Cell cell = bookMatrix[i][j];
                if (cell.getPlacementOrder() != -1) {
                    cellList.add(cell);
                }
            }
        }

        cellList.sort(Comparator.comparingInt(Cell::getPlacementOrder));

        double offsetX = (bookScrollPane.getWidth() - boardWidth) / 2;
        double offsetY = (bookScrollPane.getHeight() - boardHeight) / 2;


        for (Cell cell : cellList) {
            int i = cell.getRow();
            int j = cell.getColumn();
            PlayableCard card = cell.getCardPointer();
            int placementOrder = cell.getPlacementOrder();

            Pane cardPane = new Pane();
            cardPane.setPrefSize(paneWidth, paneHeight);

            cardPane.setLayoutX((j - minJ) * (paneWidth - overlapX) + offsetX);
            cardPane.setLayoutY((i - minI) * (paneHeight - overlapY) + offsetY);

            ImageView cardImageView = new ImageView();
            cardImageView.setFitWidth(paneWidth);
            cardImageView.setFitHeight(paneHeight);
            cardImageView.setPreserveRatio(true);

            cardImageView.setId(i + "-" + j);
            cardImageView.setOnMouseClicked(this::chooseCellClick);

            if (card != null) {
                Image cardImage = new Image(card.getImagePath());
                cardImageView.setImage(cardImage);
                cardPane.setTranslateZ(placementOrder);
            } else {
                cardImageView.setImage(null);
            }
            cardPane.getChildren().add(cardImageView);
            bookPane.getChildren().add(cardPane);
        }
        rootPane.setPrefSize(750, 380);
        bookPane.setPrefSize(750, 380);
        bookScrollPane.setPrefSize(750, 380);
        bookScrollPane.setContent(null);
        bookScrollPane.layout();
        bookScrollPane.setContent(bookPane);
        bookScrollPane.setVvalue(1);
        bookScrollPane.setHvalue(1);

        bookScrollPane.setVisible(true);
        bookScrollPane.setManaged(true);
        bookScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        bookScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    /**
     * Finds the minimum and maximum indices of rows and columns containing non-null cards in the bookMatrix.
     * Returns an array containing these indices in the order: [minRow, minColumn, maxRow, maxColumn].
     *
     * @return An array of integers representing the limits of the submatrix containing non-null cards.
     */
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

    /**
     * Handles the click event on selectable cells in the book pane, allowing the player to choose a cell for card placement.
     *
     * @param mouseEvent The MouseEvent generated by clicking on a cell in the book pane.
     */
    public void chooseCellClick(MouseEvent mouseEvent) {

        bookPane.getChildren().clear();
        if (PlaceCardChooseCell) {
            Object source = mouseEvent.getSource();
            Pane clickedPane = null;

            if (source instanceof Pane) {
                clickedPane = (Pane) source;
            } else if (source instanceof ImageView) {
                clickedPane = (Pane) ((ImageView) source).getParent();
            }

            if (clickedPane != null) {
                String id = clickedPane.getId();
                if (id != null) {
                    String[] partsID = id.split("-");
                    int rowIndex = Integer.parseInt(partsID[0]);
                    int colIndex = Integer.parseInt(partsID[1]);
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

    private boolean PlaceCardChooseCell = false;

    /**
     * Highlights the selectable cells in the book pane, allowing the player to choose a cell for card placement.
     *
     * @param model    The GameImmutable object containing the game state.
     * @param nickname The nickname of the player whose book is being highlighted for cell selection.
     */
    public void highlightChooseCell(GameImmutable model, String nickname) {
        PlaceCardChooseCell = true;

        bookScrollPane.setVisible(true);
        bookScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        bookScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        bookPane.getChildren().clear();

        this.bookMatrix = model.getPlayerByNickname(nickname).getPlayerBook().getBookMatrix();
        bookPane.getChildren().clear();

        int[] limits = findSubMatrix();
        int minI = limits[0];
        int minJ = limits[1];
        int maxI = limits[2];
        int maxJ = limits[3];

        double paneWidth = 150;
        double paneHeight = 110;
        double overlapX = 30;
        double overlapY = 30;

        double boardWidth = (maxJ - minJ + 1) * (paneWidth - overlapX) + overlapX;
        double boardHeight = (maxI - minI + 1) * (paneHeight - overlapY) + overlapY;

        double fixedWidth = 750;
        double fixedHeight = 380;

        bookPane.setPrefSize(Math.max(fixedWidth, boardWidth), Math.max(fixedHeight, boardHeight));
        bookScrollPane.setPrefSize(fixedWidth, fixedHeight);

        double offsetX = (fixedWidth - boardWidth) / 2;
        double offsetY = (fixedHeight - boardHeight) / 2;

        for (int i = minI; i <= maxI; i++) {
            for (int j = minJ; j <= maxJ; j++) {
                Pane cardPane = new Pane();
                cardPane.setPrefSize(paneWidth, paneHeight);
                cardPane.setLayoutX((j - minJ) * (paneWidth - overlapX) + offsetX);
                cardPane.setLayoutY((i - minI) * (paneHeight - overlapY) + offsetY);
                cardPane.setOnMouseClicked(this::chooseCellClick);
                cardPane.setId(i + "-" + j);

                ImageView cardImageView = new ImageView();
                cardImageView.setFitWidth(paneWidth);
                cardImageView.setFitHeight(paneHeight);
                cardImageView.setPreserveRatio(true);
                cardImageView.setId(i + "-" + j);

                PlayableCard card = bookMatrix[i][j].getCard();
                if (card != null) {
                    Image cardImage = new Image(card.getImagePath());
                    cardImageView.setImage(cardImage);
                } else {
                    cardImageView.setImage(null);
                }
                if (bookMatrix[i][j].isAvailable()) {
                    cardPane.setStyle("-fx-border-color: green; -fx-border-width: 3;");
                }
                cardPane.getChildren().add(cardImageView);
                bookPane.getChildren().add(cardPane);
            }
        }

        rootPane.setPrefSize(fixedWidth, fixedHeight);
        bookPane.setPrefSize(fixedWidth, fixedHeight);
        bookScrollPane.setPrefSize(fixedWidth, fixedHeight);

        bookPane.layout();
        bookScrollPane.layout();
        bookScrollPane.setContent(bookPane);
        bookScrollPane.setVisible(true);
        bookScrollPane.setManaged(true);
        bookScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        bookScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

}
