package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.Board;
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
    }
    @FXML
    private void handleCloseAction(ActionEvent event) {
        this.guiApplication.closePopUpStage();
    }

    public void enlargeAndHighlightBoardPane(boolean enablePickCardTurn) {
        try {
            Parent root = imgDeckGold.getParent().getParent(); // Otteniamo il genitore del genitore di un qualsiasi nodo nell'interfaccia grafica

            Stage boardPopUpStage = new Stage();
            boardPopUpStage.setScene(new Scene(root));
            boardPopUpStage.initStyle(StageStyle.UNDECORATED);
            boardPopUpStage.show();

            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(20.0);
            dropShadow.setOffsetX(0.0);
            dropShadow.setOffsetY(0.0);
            dropShadow.setColor(Color.BLUE);
            root.setEffect(dropShadow);

            if (enablePickCardTurn) {
                pickCardTurn = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean pickCardTurn=false;
    public void enablePickCardTurn() {
        pickCardTurn = true;
    }

    public void chooseCardClick(MouseEvent mouseEvent) {
        if (pickCardTurn) {
            int selectedIndex = -1;

            if (mouseEvent.getSource() == imgGold0) {
                selectedIndex = 0; // First gold card
            } else if (mouseEvent.getSource() == imgGold1) {
                selectedIndex = 1; // Second gold card
            } else if (mouseEvent.getSource() == imgDeckGold) {
                selectedIndex = 2; // Deck gold card
            } else if (mouseEvent.getSource() == imgResource0) {
                selectedIndex = 3; // First resource card
            } else if (mouseEvent.getSource() == imgResource1) {
                selectedIndex = 4; // Second resource card
            } else if (mouseEvent.getSource() == imgDeckResource) {
                selectedIndex = 5; // Deck resource card
            }

            if (selectedIndex != -1) {
                getInputGUI().addTxt(String.valueOf(selectedIndex)); // Pass the index as a string
                clearCardImage(selectedIndex); // Clear the clicked card image
                pickCardTurn = false;
                this.guiApplication.closePopUpStage();
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
