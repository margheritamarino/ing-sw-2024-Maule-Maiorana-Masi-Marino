package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.view.GUI.GUIApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


/**
 * Controller class for the board pop-up view in the GUI.
 */
public class BoardPopUpController extends ControllerGUI{

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
    private GUIApplication guiApplication;
    private boolean pickCardTurn=false;
    @FXML
    HBox resourceHbox;
    @FXML
    HBox objectiveHbox;
    @FXML
    HBox goldHbox;

    /**
     * Sets the GUI application instance for this controller.
     *
     * @param guiApplication the GUI application instance
     */
    public void setGUIApplication(GUIApplication guiApplication) {
        this.guiApplication = guiApplication;
    }

    /**
     * Sets the board state in the view based on the game model.
     *
     * @param model the immutable game model containing the current state of the game
     */
    public void setBoard(GameImmutable model) {
        Board board = model.getBoard();
        String imagePath;
        int goldRandIndex= board.getGoldCardsDeck().getRandomIndex();
        int resourceRandIndex=board.getResourcesCardsDeck().getRandomIndex();

        //GOLD CARD
        imagePath = board.getGoldCardsDeck().getBackCards().get(goldRandIndex).getImagePath();
        imgDeckGold.setImage(new Image(imagePath));
        imagePath = board.getGoldCards().get(0)[0].getImagePath();
        imgGold0.setImage(new Image(imagePath));
        imagePath = board.getGoldCards().get(1)[0].getImagePath();
        imgGold1.setImage(new Image(imagePath));

        //RESOURCE CARD
        imagePath = board.getResourcesCardsDeck().getBackCards().get(resourceRandIndex).getImagePath();
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

    /**
     * Handles the action to close the board pop-up.
     *
     * @param event the action event triggered by closing the pop-up
     */
    @FXML
    private void handleCloseAction(ActionEvent event) {
        this.guiApplication.closePopUpStage();
    }

    /**
     * Enlarges and highlights the board pane, enabling card selection.
     *
     * @param enablePickCardTurn whether card picking is enabled
     */
    public void enlargeAndHighlightBoardPane(boolean enablePickCardTurn) {
        goldHbox.getStyleClass().add("gold-glow-hbox");
        resourceHbox.getStyleClass().add("green-glow-hbox");
    }

    /**
     * Enables the card picking turn.
     */
    public void enablePickCardTurn() {
        pickCardTurn = true;
    }

    /**
     * Handles the click event on a card image, allowing the player to choose a card.
     *
     * @param mouseEvent the mouse event triggered by clicking a card
     */
    public void chooseCardClick(MouseEvent mouseEvent) {
        if (pickCardTurn) {
            ImageView clickedImageView = (ImageView) mouseEvent.getSource();
            clickedImageView.getStyleClass().add("image-view:pressed");
            int selectedIndex = -1;

            if (clickedImageView == imgGold0) {
                selectedIndex = 0; // First gold card
            } else if (clickedImageView == imgGold1) {
                selectedIndex = 1; // Second gold card
            } else if (clickedImageView == imgDeckGold) {
                selectedIndex = 2; // Deck gold card
            } else if (clickedImageView == imgResource0) {
                selectedIndex = 3; // First resource card
            } else if (clickedImageView == imgResource1) {
                selectedIndex = 4; // Second resource card
            } else if (clickedImageView == imgDeckResource) {
                selectedIndex = 5; // Deck resource card
            }

            if (selectedIndex != -1) {
                if (selectedIndex == 0 || selectedIndex == 1 ||selectedIndex == 2)
                    getInputGUI().addTxt("G");
                else {
                    getInputGUI().addTxt("R");
                }

                if (selectedIndex == 2 || selectedIndex == 5){
                    getInputGUI().addTxt("yes");
                }
                else {
                    getInputGUI().addTxt("no");
                    int pos = 0;
                    if(selectedIndex==0 || selectedIndex==3) {
                        pos=0;
                    }
                    else {
                        pos=1;
                    }
                    getInputGUI().addTxt(String.valueOf(pos));
                }
                clearCardImage(selectedIndex);
                this.guiApplication.closePopUpStage();
                pickCardTurn=false;
            }
        }
    }

    /**
     * Clears the image of the selected card.
     *
     * @param index the index of the selected card
     */
    private void clearCardImage(int index) {
        switch (index) {
            case 0 -> imgGold0.setImage(null);
            case 1 -> imgGold1.setImage(null);
            case 2 -> imgDeckGold.setImage(null);
            case 3 -> imgResource0.setImage(null);
            case 4 -> imgResource1.setImage(null);
            case 5 -> imgDeckResource.setImage(null);
        }
    }

}
