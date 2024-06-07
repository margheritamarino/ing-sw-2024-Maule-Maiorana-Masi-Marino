package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.view.GUI.GUIApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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
    @FXML
    private Stage boardPopUpStage;
    private GameImmutable model;

    public void setGUIApplication(GUIApplication guiApplication) {
        this.guiApplication = guiApplication;
    }
    public void setBoard(GameImmutable model) {
        //BOARD
        Board board = model.getBoard();
        String imagePath;

        //GOLD CARD
        imagePath = board.getGoldCardsDeck().getBackCards().getFirst().getImagePath();
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

        this.model = model;
    }
    @FXML
    private void handleCloseAction(ActionEvent event) {
        this.guiApplication.closePopUpStage();
    }


    private boolean pickCardTurn=false;
    @FXML
    HBox resourceHbox;
    @FXML
    HBox objectiveHbox;
    @FXML
    HBox goldHbox;
    public void enlargeAndHighlightBoardPane(boolean enablePickCardTurn) {
        goldHbox.getStyleClass().add("gold-glow-hbox");
        resourceHbox.getStyleClass().add("green-glow-hbox");
    }


    public void enablePickCardTurn() {
        pickCardTurn = true;
    }

    public void chooseCardClick(MouseEvent mouseEvent) {
        if (pickCardTurn) {
            ImageView clickedImageView = (ImageView) mouseEvent.getSource();
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
                getInputGUI().addTxt(String.valueOf(selectedIndex));
                //rimuovo l'immagine della carta selezionata
                clearCardImage(selectedIndex);

                String newImagePath = null;
                //rimuovo la carta dal modello e ottengo l'immagine relativa
                switch (selectedIndex) {
                    case 0:
                        newImagePath = imgDeckGold.getImage().getUrl();
                        imgGold0.setImage(new Image(newImagePath));
                        imgDeckGold.setImage(new Image(model.getBoard().getGoldCardsDeck().getBackCards().getFirst().getImagePath()));
                        break;
                    case 1:
                        newImagePath = imgDeckGold.getImage().getUrl();
                        imgGold1.setImage(new Image(newImagePath));
                        imgDeckGold.setImage(new Image(model.getBoard().getGoldCardsDeck().getBackCards().getFirst().getImagePath()));
                        break;
                    case 2:
                        newImagePath = model.getBoard().getGoldCardsDeck().getBackCards().getFirst().getImagePath();
                        imgDeckGold.setImage(new Image(newImagePath));
                        break;
                    case 3:
                        newImagePath = imgDeckResource.getImage().getUrl();
                        imgResource0.setImage(new Image(newImagePath));
                        imgDeckResource.setImage(new Image(model.getBoard().getResourcesCardsDeck().getBackCards().getFirst().getImagePath()));
                        break;
                    case 4:
                        newImagePath = imgDeckResource.getImage().getUrl();
                        imgResource1.setImage(new Image(newImagePath));
                        imgDeckResource.setImage(new Image(model.getBoard().getResourcesCardsDeck().getBackCards().getFirst().getImagePath()));
                        break;
                    case 5:
                        newImagePath = model.getBoard().getResourcesCardsDeck().getBackCards().getFirst().getImagePath();
                        imgDeckResource.setImage(new Image(newImagePath));
                        break;
                }

                pickCardTurn = false;
            }
        }
    }

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
