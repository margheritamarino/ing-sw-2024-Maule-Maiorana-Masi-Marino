package it.polimi.ingsw.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InitializeCardsController extends ControllerGUI {
    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;


    public void setCards(String imagePath1,String imagePath2 ) {
        Image image1 = new Image(imagePath1);
        imageView1.setImage(image1);
        Image image2 = new Image(imagePath2);
        imageView2.setImage(image2);

    }

    public void chooseCardClick(javafx.scene.input.MouseEvent mouseEvent) {
        ImageView source = (ImageView) mouseEvent.getSource();
        System.out.println("Image clicked: " + source.getId());
    }
}
