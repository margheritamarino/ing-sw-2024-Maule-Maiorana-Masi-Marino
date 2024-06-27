package it.polimi.ingsw.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Controller class for initializing and managing UI components related to selecting cards in the GUI.
 */
public class InitializeCardsController extends ControllerGUI {
    @FXML
    private ImageView imageView0;
    @FXML
    private ImageView imageView1;
    @FXML
    private Text titleField;

    /**
     * Sets the images for the two card ImageViews.
     *
     * @param imagePath1 path to the first card image
     * @param imagePath2 path to the second card image
     */
    public void setCards(String imagePath1, String imagePath2) {
        Image image1;
        Image image2;

        image1 = new Image(imagePath1);
        image2 = new Image(imagePath2);

        imageView0.setImage(image1);
        imageView1.setImage(image2);
    }

    /**
     * Updates the title field with the specified text.
     *
     * @param text the text to set as the title
     */
    public void setTitleField(String text) {
        titleField.setText(text);
    }

    /**
     * Handles the click event on the card ImageViews.
     * Adds the selected index as a string to the input GUI.
     *
     * @param mouseEvent the MouseEvent triggered by clicking on an ImageView
     */
    public void chooseCardClick(javafx.scene.input.MouseEvent mouseEvent) {
        int selectedIndex = -1;
        if (mouseEvent.getSource() == imageView0) {
            selectedIndex = 0;
        } else if (mouseEvent.getSource() == imageView1) {
            selectedIndex = 1;
        }

        if (selectedIndex != -1) {
            getInputGUI().addTxt(String.valueOf(selectedIndex));
        }
    }

    /**
     * Initializes the controller after the FXML file has been loaded.
     * Sets up event listeners for mouse clicks on the card ImageViews.
     */
    @FXML
    public void initialize() {
        imageView0.setOnMouseClicked(this::chooseCardClick);
        imageView1.setOnMouseClicked(this::chooseCardClick);
    }

    /**
     * Highlights the ImageView when the mouse enters.
     *
     * @param event the MouseEvent triggered by entering the ImageView area
     */
    @FXML
    public void highlightImage(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.LIGHTSKYBLUE);
        dropShadow.setRadius(20);
        imageView.setEffect(dropShadow);
    }

    /**
     * Removes the highlight effect from the ImageView when the mouse exits.
     *
     * @param event the MouseEvent triggered by exiting the ImageView area
     */
    @FXML
    public void unhighlightImage(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(null);
    }
}
