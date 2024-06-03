package it.polimi.ingsw.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InitializeCardsController extends ControllerGUI {
    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;


    public void setCards(String imagePath1, String imagePath2) {
        Image image1;
        Image image2;
        try {
            image1 = new Image(imagePath1);
            if (image1.getWidth() <= 0 || image1.getHeight() <= 0) {
                throw new IllegalArgumentException("Invalid image path: " + imagePath1);
            }
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dell'immagine: " + imagePath1);
            image1 = new Image("/path/to/default/image1.png"); // Percorso di fallback
        }
        try {
            image2 = new Image(imagePath2);
            if (image2.getWidth() <= 0 || image2.getHeight() <= 0) {
                throw new IllegalArgumentException("Invalid image path: " + imagePath2);
            }
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dell'immagine: " + imagePath2);
            image2 = new Image("/path/to/default/image2.png"); // Percorso di fallback
        }

        imageView1.setImage(image1);
        imageView2.setImage(image2);
    }


    public void chooseCardClick(javafx.scene.input.MouseEvent mouseEvent) {
        ImageView source = (ImageView) mouseEvent.getSource();
        System.out.println("Image clicked: " + source.getId());
    }
}
