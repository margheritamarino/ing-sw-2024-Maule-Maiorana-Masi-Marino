package it.polimi.ingsw.model;

import java.util.*;

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
        return Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
    }
    // Metodo per restituire un colore casuale
    public static synchronized Color getRandomColor() {
        if (availableColors.isEmpty()) {
            resetAvailableColors();
        }
        return availableColors.remove(RANDOM.nextInt(availableColors.size()));
    }
    public static void addColor(Color color){
        availableColors.add(color);
    }

    private static void resetAvailableColors() {
        Collections.addAll(availableColors, values());
    }
}
