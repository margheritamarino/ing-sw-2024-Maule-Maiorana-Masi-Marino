package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Color {
    YELLOW("/img/yellow.png"),
    BLUE("/img/blue.png"),
    RED("/img/red.png"),
    GREEN("/img/green.png");

    private final String path;
    private static final ArrayList<Color> availableColors = new ArrayList<>();
    private static final Random RANDOM = new Random();
    Color(final String path){
        this.path = path;
    }

    //metodo per restituire il percorso del file FXML
    public String getPath(){
        return getClass().getResource(path).toExternalForm();
    }
    // Metodo per restituire un colore casuale
    public static Color getRandomColor() {
        if (availableColors.isEmpty()) {
            resetAvailableColors();
        }
        return availableColors.remove(RANDOM.nextInt(availableColors.size()));
    }

    private static void resetAvailableColors() {
        Collections.addAll(availableColors, values());
    }
}
