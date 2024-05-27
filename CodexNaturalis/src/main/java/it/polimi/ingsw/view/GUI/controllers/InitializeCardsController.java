package it.polimi.ingsw.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.awt.event.MouseEvent;

public class InitializeCardsController extends ControllerGUI {
    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private void chooseCardClick(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        System.out.println("Image clicked: " + source.getId());
        // Aggiungi qui la logica per gestire la selezione dell'immagine
    }
}
