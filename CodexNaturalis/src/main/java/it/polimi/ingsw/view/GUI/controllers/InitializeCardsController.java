package it.polimi.ingsw.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.*;

public class InitializeCardsController extends ControllerGUI {
    @FXML
    private ImageView imageView0;

    @FXML
    private ImageView imageView1;
    @FXML
    private Text titleField;


    public void setCards(String imagePath1, String imagePath2) {
        Image image1;
        Image image2;

        image1 = new Image(imagePath1);
        image2 = new Image(imagePath2);

        imageView0.setImage(image1);
        imageView1.setImage(image2);
    }
    public void setTitleField(String text) {
        // Logica per aggiornare il campo titleField
        titleField.setText(text); // Esempio di aggiornamento
    }

    public void chooseCardClick(javafx.scene.input.MouseEvent mouseEvent) {
        int selectedIndex = -1;

        if (mouseEvent.getSource() == imageView0) {
            selectedIndex = 0;
        } else if (mouseEvent.getSource() == imageView1) {
            selectedIndex = 1;
        }

        if (selectedIndex != -1) {
            getInputGUI().addTxt(String.valueOf(selectedIndex)); // Passa l'indice come stringa
        }
    }

    @FXML
    public void initialize() {
        // Imposta i listener per i clic sulle immagini
        imageView0.setOnMouseClicked(this::chooseCardClick);
        imageView1.setOnMouseClicked(this::chooseCardClick);
    }
    @FXML
    public void highlightImage(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.LIGHTSKYBLUE);
        dropShadow.setRadius(20);
        imageView.setEffect(dropShadow);
    }

    @FXML
    public void unhighlightImage(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(null);
    }
}
