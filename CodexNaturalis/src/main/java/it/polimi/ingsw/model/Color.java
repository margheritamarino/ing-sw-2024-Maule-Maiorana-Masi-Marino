package it.polimi.ingsw.model;

import java.util.*;

/**
 * Enumeration representing different colors for the players with associated image paths.
 */
public enum Color {
    YELLOW("/img/yellow.png"),
    BLUE("/img/blue.png"),
    RED("/img/red.png"),
    GREEN("/img/green.png");

    private final String path;
    private static final ArrayList<Color> availableColors = new ArrayList<>();
    private static final Random RANDOM = new Random();

    /**
     * Constructor to initialize a color with its corresponding image path.
     *
     * @param path The path to the image representing the color.
     */
    Color(final String path){
        this.path = path;
    }

    /**
     * Retrieves the path to the image associated with this color.
     *
     * @return The path to the color's image as a String.
     */
    public String getPath(){
        return Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
    }

    /**
     * Retrieves a random color from the available set of colors.
     *
     * @return A randomly selected color.
     */
    public static synchronized Color getRandomColor() {
        if (availableColors.isEmpty()) {
            resetAvailableColors();
        }
        return availableColors.remove(RANDOM.nextInt(availableColors.size()));
    }

    /**
     * Adds a color to the available colors list.
     *
     * @param color The color to add.
     */
    public static void addColor(Color color){
        availableColors.add(color);
    }

    /**
     * Resets the list of available colors to include all enum values.
     */
    private static void resetAvailableColors() {
        Collections.addAll(availableColors, values());
    }
}
