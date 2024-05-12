package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import it.polimi.ingsw.model.interfaces.PlayableCardIC;
import org.fusesource.jansi.Ansi;

import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

public abstract class PlayableCard {
    private final int cardID;
    private final int numCorners;
    private final boolean isFront;
    private final CardType cardType;
    private final CornerLabel TLCorner;
    private final CornerLabel TRCorner;
    private final CornerLabel BRCorner;
    private final CornerLabel BLCorner;

    public int getCardID() {
        return cardID;
    }

    public int getNumCorners() {
        return numCorners;
    }

    public boolean isFront() {
        return isFront;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CornerLabel getTLCorner() {
        return TLCorner;
    }

    public CornerLabel getTRCorner() {
        return TRCorner;
    }

    public CornerLabel getBRCorner() {
        return BRCorner;
    }

    public CornerLabel getBLCorner() {
        return BLCorner;
    }

    /**
     * Retrieves the content of the specified corner of a PlayableCard.
     *
     * @author Margherita Marino
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
     * @author Margherita Marino
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

    public PlayableCard() {
        this.cardID = 0;
        this.isFront = true;
        this.cardType = null;
        this.numCorners = 0;
        this.TLCorner = null;
        this.TRCorner = null;
        this.BRCorner = null;
        this.BLCorner = null;
    }

    public PlayableCard(int cardID, int numCorners, boolean isFront, CardType cardType, CornerLabel TLCorner, CornerLabel TRCorner, CornerLabel BRCorner, CornerLabel BLCorner) {
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
            output = "\u26AB";
        }else if(input.equals("Manuscript")){
            output= "\uD83D\uDCDC";
        }else if(input.equals("Quill")){
            output = "\uD83E\uDEB6";
        }else output = input;
        return output;
    }

}
