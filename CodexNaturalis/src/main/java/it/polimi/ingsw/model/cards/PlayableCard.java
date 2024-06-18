package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import it.polimi.ingsw.model.interfaces.PlayableCardIC;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

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
    public String getImagePath(){
        final String path;
        int idTemp = this.getCardID();
        String typeTemp= this.getCardType().toString();
        String sideTemp;
        String mainResource;

        if(this.isFront()){
            sideTemp= "Front";
        } else {
            sideTemp= "Back";

        }

        if(typeTemp.equals("InitialCard")){
            path= "/img/Cards/initialCards/"+ idTemp+ "_Initial"+ sideTemp+ ".png";
        } else {

            switch (typeTemp) {
                case "GoldCard":
                    typeTemp = "Gold";
                    break;
                case "ResourceCard":
                    typeTemp = "Resource";
                    break;
                default:
                    throw new IllegalArgumentException("Card type not recognized: " + typeTemp);
            }
            if (sideTemp.equals("Front")) {
                path="/img/Cards/"+ typeTemp+"Cards/"+ idTemp+ "_"+ typeTemp+ "Front.png";

            } else {
                mainResource= this.getMainResource().toString(); //used only for Back img
                path="/img/Cards/"+ typeTemp+"Cards/"+ typeTemp+ mainResource+ "Back.png";
            }
        }
        return Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
    }


    /**
     * Retrieves the content of the specified corner of a PlayableCard.
     *
     * @param corner       position of the corner: 0 TLCorner, 1 TRCorner, 2 BRCorner, 3 BLCorner
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
    public abstract int getVictoryPoints();
    public abstract int getNumResources();
    public abstract SymbolType getSymbol();
    public abstract boolean hasSymbol();
    public abstract ResourceType getMainResource();
    public abstract List<ResourceType> getCentralResources();
    public abstract int getNumCentralResources();
    public abstract List<ResourceType> getResourceList();
    public abstract List<ResourceType> getPlacementCondition();
    public abstract boolean isPointsCondition();
    public abstract boolean isCornerCondition();
    public abstract SymbolType getSymbolCondition();

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

    @Override
    public abstract String toString();


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
