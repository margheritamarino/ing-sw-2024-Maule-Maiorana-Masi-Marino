package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;

import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * Abstract class that represents a card that can be played in the game.
 */
public abstract class PlayableCard implements Serializable {
    private final int cardID;
    private final int numCorners;
    private final boolean isFront;
    private final CardType cardType;
    private final CornerLabel TLCorner;
    private final CornerLabel TRCorner;
    private final CornerLabel BRCorner;
    private final CornerLabel BLCorner;
    private String[][] cardMatrix = new String[2][3];


    /**
     * Retrieves the unique identifier for the card.
     * @return The card's ID.
     */
    public int getCardID() {
        return cardID;
    }

    /**
     * Retrieves the number of corners on the card.
     *
     * @return The number of corners.
     */
    public int getNumCorners() {
        return numCorners;
    }

    /**
     * Checks if the card is front-facing.
     *
     * @return {@code true} if the card is front-facing, {@code false} otherwise.
     */
    public boolean isFront() {
        return isFront;
    }

    /**
     * @return the card's type
     */
    public CardType getCardType() {
        return cardType;
    }

    /**
     * @return The label for the top-left corner.
     */
    public CornerLabel getTLCorner() {
        return TLCorner;
    }

    /**
     * @return The label for the top-right corner.
     */
    public CornerLabel getTRCorner() {
        return TRCorner;
    }

    /**
     * @return The label for the bottom-right corner.
     */
    public CornerLabel getBRCorner() {
        return BRCorner;
    }

    /**
     * @return the label for the bottom-left corner.
     */
    public CornerLabel getBLCorner() {
        return BLCorner;
    }

    /**
     * This method is used for the GUI
     * @return the path for the specific istance of Card (contained in the package resources-->img)
     */

  /*  public String getImagePath(){
        final String path;
        int idTemp = this.getCardID();
        String typeTemp= this.getCardType().toString(); //InitialCard, ResourceCard, GoldCard
        String sideTemp;
        String mainResource;

        final String DEFAULT_RESOURCE_FRONT = "/img/Cards/ResourceCards/0_ResourceFront.png";
        final String DEFAULT_GOLD_FRONT = "/img/Cards/GoldCards/0_GoldFront.png";
        final String DEFAULT_INITIAL_FRONT = "/img/Cards/initialCards/InitialCards/0_InitialFront.png";
        final String DEFAULT_INITIAL_BACK = "/img/Cards/initialCards/InitialCards/0_InitialBack.png";

        if(this.isFront()){
            sideTemp= "Front";
        } else {
            sideTemp= "Back";

        }

        if(typeTemp.equals("InitialCard")){
            path= "/img/Cards/initialCards/"+ idTemp+ "_Initial"+ sideTemp+ ".png";
        }else {
            switch (typeTemp) {
                case "GoldCard":
                    typeTemp = "Gold";
                    break;
                case "ResourceCard":
                    typeTemp = "Resource";
                    break;
                default:
                    typeTemp = "Resource";
                    break;
            }
            if (sideTemp.equals("Front")) {
                path="/img/Cards/"+ typeTemp+"Cards/"+ idTemp+ "_"+ typeTemp+ "Front.png";

            } else {
                mainResource= this.getMainResource().toString(); //used only for Back img
                path="/img/Cards/"+ typeTemp+"Cards/"+ typeTemp+ mainResource+ "Back.png";
            }
        }
        try {
            String resourcePath = getClass().getResource(path) != null ? getClass().getResource(path).toExternalForm() : null;

            if (resourcePath != null) {
                return resourcePath;
            } else {
                // Percorso dell'immagine predefinito se l'immagine specificata non esiste
                if (typeTemp.equals("Resource")) {
                    return getClass().getResource(DEFAULT_RESOURCE_FRONT).toExternalForm();
                } else if (typeTemp.equals("Gold")) {
                    return getClass().getResource(DEFAULT_GOLD_FRONT).toExternalForm();
                } else if (typeTemp.equals("InitialCard")) {
                    return getClass().getResource(sideTemp.equals("Front") ? DEFAULT_INITIAL_FRONT : DEFAULT_INITIAL_BACK).toExternalForm();
                } else {
                    return getClass().getResource(DEFAULT_RESOURCE_FRONT).toExternalForm();
                }
            }
        } catch (Exception e) {
            if (typeTemp.equals("Resource")) {
                return getClass().getResource(DEFAULT_RESOURCE_FRONT).toExternalForm();
            } else if (typeTemp.equals("Gold")) {
                return getClass().getResource(DEFAULT_GOLD_FRONT).toExternalForm();
            } else if (typeTemp.equals("InitialCard")) {
                return getClass().getResource(sideTemp.equals("Front") ? DEFAULT_INITIAL_FRONT : DEFAULT_INITIAL_BACK).toExternalForm();
            } else {
                return getClass().getResource(DEFAULT_RESOURCE_FRONT).toExternalForm();
            }
        }
    }*/

    public String getImagePath() {
        final String path;
        int idTemp = this.getCardID();
        String typeTemp = this.getCardType().toString();
        String sideTemp;
        String mainResource;

        if (this.isFront()) {
            sideTemp = "Front";
        } else {
            sideTemp = "Back";
        }

        if (typeTemp.equals("InitialCard")) {
            path = "/img/Cards/initialCards/" + idTemp + "_Initial" + sideTemp + ".png";
        } else {
            switch (typeTemp) {
                case "GoldCard":
                    typeTemp = "Gold";
                    break;
                case "ResourceCard":
                    typeTemp = "Resource";
                    break;
                default:
                    typeTemp = "Resource";
                    break;
            }
            if (sideTemp.equals("Front")) {
                path = "/img/Cards/" + typeTemp + "Cards/" + idTemp + "_" + typeTemp + "Front.png";
            } else {
                mainResource = this.getMainResource().toString(); // used only for Back img
                path = "/img/Cards/" + typeTemp + "Cards/" + typeTemp + mainResource + "Back.png";
            }
        }

        String resourcePath = getResourcePath(path);

        if (resourcePath != null) {
            return resourcePath;
        } else {
            // Fallback to default images
            String defaultPath = typeTemp.equals("Resource") ? "/img/Cards/0_ResourceFront.png" : "/img/Cards/0_GoldFront.png";
            resourcePath = getResourcePath(defaultPath);
            if (resourcePath != null) {
                return resourcePath;
            } else {
                System.err.println("Default image not found.");
                return null; // Or handle as needed
            }
        }
    }

    private String getResourcePath(String path) {
        try {
            URL resourceUrl = getClass().getResource(path);
            if (resourceUrl != null) {
                return resourceUrl.toExternalForm();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error finding resource: " + path + " - " + e.getMessage());
            return null;
        }
    }


    /**
     * Retrieves the content of the specified corner of a PlayableCard.
     *
     * @param corner position of the corner: 0 TLCorner, 1 TRCorner, 2 BRCorner, 3 BLCorner
     * @return The content of the specified corner as a string.
     */
    public String getCornerContent(int corner){
        String content = null;
                if(corner == 0) content = getCornerContent().get(0);
                else if(corner == 1) content = getCornerContent().get(1);
                else if(corner == 2) content = getCornerContent().get(2);
                else content = getCornerContent().get(3);
        return content;
    }

    /**
     * Retrieves the content of the corners of the card as a list of strings.
     * This method is intended to be overridden in subclasses such as GoldCard to provide specific behavior.
     *
     * @return A list containing the gold corner content strings.
     */
    public abstract List<String> getCornerContent();

    /**
     * Retrieves the victory points associated with the card.
     * This method is intended to be overridden in subclasses.
     *
     * @return The number of victory points.
     */
    public abstract int getVictoryPoints();

    /**
     * Retrieves the total number of resources on the card.
     * This method is intended to be overridden in subclasses.
     *
     * @return The number of resources on the card.
     */
    public abstract int getNumResources();

    /**
     * Retrieves the symbol type associated with the card, if any.
     * This method is intended to be overridden in subclasses.
     *
     * @return The symbol type of the card, or null if no symbol is present.
     */
    public abstract SymbolType getSymbol();

    /**
     * Checks if the card has a symbol.
     * This method is intended to be overridden in subclasses.
     *
     * @return {@code true} if the card has a symbol, {@code false} otherwise.
     */
    public abstract boolean hasSymbol();

    /**
     * Retrieves the main resource associated with the card.
     * This method is intended to be overridden in subclasses.
     *
     * @return The main resource of the card.
     */
    public abstract ResourceType getMainResource();


    /**
     * Retrieves the central resources associated with the card.
     * This method is intended to be overridden in subclasses.
     *
     * @return A list containing the central resources.
     */
    public abstract List<ResourceType> getCentralResources();

    /**
     * Retrieves the number of central resources associated with the card.
     * This method is intended to be overridden in subclasses.
     *
     * @return The number of central resources.
     */
    public abstract int getNumCentralResources();

    /**
     * Retrieves the list of resources associated with the card.
     * This method is intended to be overridden in subclasses.
     *
     * @return A list containing the resources of the card.
     */
    public abstract List<ResourceType> getResourceList();

    /**
     * Retrieves the placement condition resources associated with the card.
     * This method is intended to be overridden in subclasses.
     *
     * @return A list containing the placement condition resources.
     */
    public abstract List<ResourceType> getPlacementCondition();


    /**
     * Checks if the card has a points condition.
     * This method is intended to be overridden in subclasses.
     *
     * @return {@code true} if the card has a points condition, {@code false} otherwise.
     */
    public abstract boolean isPointsCondition();

    /**
     * Checks if the card has a corner condition.
     * This method is intended to be overridden in subclasses.
     *
     * @return {@code true} if the card has a corner condition, {@code false} otherwise.
     */
    public abstract boolean isCornerCondition();

    /**
     * Retrieves the symbol condition associated with the card, if any.
     * This method is intended to be overridden in subclasses.
     *
     * @return The symbol condition of the card, or null if no symbol condition is present.
     */
    public abstract SymbolType getSymbolCondition();

    /**
     * Constructs a PlayableCard with the specified attributes.
     *
     * @param cardID    the unique identifier for the card.
     * @param numCorners    the number of corners on the card.
     * @param isFront   the side of the card (true if front, false if back).
     * @param cardType  the type of the card.
     * @param TLCorner  the label for the top-left corner.
     * @param TRCorner  the label for the top-right corner.
     * @param BRCorner  the label for the bottom-right corner.
     * @param BLCorner  the label for the bottom-left corner.
     */
    public PlayableCard( int cardID, int numCorners, boolean isFront, CardType cardType, CornerLabel TLCorner, CornerLabel TRCorner, CornerLabel BRCorner, CornerLabel BLCorner) {
        this.cardID = cardID;
        this.numCorners = numCorners;
        this.isFront = isFront;
        this.cardType = cardType;
        this.TLCorner = TLCorner;
        this.TRCorner = TRCorner;
        this.BRCorner = BRCorner;
        this.BLCorner = BLCorner;
    }


    /**
     * Retrieves a string representation of the card.
     * This method is intended to be overridden in subclasses to provide specific details about the card.
     *
     * @return A string representation of the card.
     */
    @Override
    public abstract String toString();

    /**
     * Converts a string representation of a resource type into its corresponding emoji.
     * This method supports converting specific resource types used in the game into emoji symbols.
     *
     * @param input The string representation of the resource type to convert.
     * @return The corresponding emoji symbol as a string, or "E" if no matching emoji is found.
     */
    public String convertToEmoji(String input){
        String output;
        if(input.equals("Fungi")){
            output =  "\uD83C\uDF44";
        }else if(input.equals("Animal")){
            output = "\uD83D\uDC3A";
        }else if(input.equals("Insect")){
            output = "\uD83E\uDD8B";
        }else if(input.equals("Plant")){
            output = "\uD83C\uDF40";
        }else if(input.equals("NoCorner")){
            output = "\u274C";
        }else if(input.equals("Ink")){
            output = "\uD83D\uDD8A\uFE0F";
        }else if(input.equals("Manuscript")){
            output= "\uD83D\uDCDC";
        }else if(input.equals("Quill")){
            output = "\uD83E\uDEB6";
        }else if(input.equals("CoveredCorner")){
            output = "\u2B1B";
        }else output = "E";
        return output;
    }

}
