package it.polimi.ingsw.model;

import java.util.Random;

public enum Color {
    YELLOW("/img/yellow.png"),
    BLUE("/img/blue.png"),
    RED("/img/red.png"),
    GREEN("/img/green.png");

    private final String path;
    Color(final String path){
        this.path = path;
    }

    //metodo per restituire il percorso del file FXML
    public String getPath(){
        return getClass().getResource(path).toExternalForm();
    }
    // Metodo per restituire un colore casuale
    public static Color getRandomColor() {
        Random random = new Random();
        Color[] colors = values();
        int index = random.nextInt(colors.length);
        return colors[index];
    }
}
