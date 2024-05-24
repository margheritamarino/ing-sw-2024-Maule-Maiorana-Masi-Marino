package it.polimi.ingsw.view.GUI.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * PublisherController class.
 */

public class PublisherController extends ControllerGUI{

    @FXML
    private Pane CodexNaturalis; // Assicurati che l'ID corrisponda a quello del file FXML

    @FXML
    private ImageView backgroundImageView; // Aggiungi un fx:id nel file FXML se desideri fare riferimento all'ImageView

    @FXML
    private Label titleLabel; // Aggiungi un fx:id nel file FXML se desideri fare riferimento alla Label

    @FXML
    private void initialize() {
        // Questo metodo viene chiamato automaticamente dopo che gli elementi FXML sono stati iniettati

        // Esempio di inizializzazione dei componenti se necessario
        titleLabel.setText("CODEX NATURALIS");
    }
}

