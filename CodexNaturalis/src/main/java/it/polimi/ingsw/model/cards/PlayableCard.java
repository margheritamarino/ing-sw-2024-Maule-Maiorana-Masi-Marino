package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CornerLabel;
import it.polimi.ingsw.model.CornerType;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import it.polimi.ingsw.model.cards.CardType;

import java.util.List;
import java.util.Map;

public abstract class PlayableCard {
    private int cardID;
    private int numCorners;
    private boolean isFront;
    private CardType cardType;
    private CornerLabel TLCorner;
    private CornerLabel TRCorner;
    private CornerLabel BRCorner;
    private CornerLabel BLCorner;

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

}
