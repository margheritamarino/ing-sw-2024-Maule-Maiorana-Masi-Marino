package it.polimi.ingsw.model;

import java.util.Random;

public enum Color {
    YELLOW("/it.polimi.ingsw.view.GUI/img/yellow.png"), //TODO
    BLUE("/it.polimi.ingsw.view.GUI/img/blue.png"),//TODO
    RED("/it.polimi.ingsw.view.GUI/img/red.png"),//TODO
    GREEN("/it.polimi.ingsw.view.GUI/img/green.png");

    private final String path;
    Color(final String path){
        this.path = path;
    }

    //metodo per restituire il percorso del file FXML
    public String path(){
        return path;
    }
    // Metodo per restituire un colore casuale
    public static Color getRandomColor() {
        Random random = new Random();
        Color[] colors = values();
        int index = random.nextInt(colors.length);
        return colors[index];
    }
}
