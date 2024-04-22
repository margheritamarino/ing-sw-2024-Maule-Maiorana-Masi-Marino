package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CornerLabel;
import it.polimi.ingsw.model.CornerType;
import it.polimi.ingsw.model.ResourceType;
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
     * @param corner       The type of corner whose content is to be retrieved.
     * @return The content of the specified corner as a string.
     */
    public String getCornerContent(CornerType corner){
        String content = null;
        switch (cardType) {
            case ResourceCard -> {
                if(corner == CornerType.TLCorner) content = getResourceCornerContent().get(0);
                else if(corner == CornerType.TRCorner) content = getResourceCornerContent().get(1);
                else if(corner == CornerType.BRCorner) content = getResourceCornerContent().get(2);
                else content = getResourceCornerContent().get(3);
            }
            case GoldCard -> {
                if(corner == CornerType.TLCorner) content = getGoldCornerContent().get(0);
                else if(corner == CornerType.TRCorner) content = getGoldCornerContent().get(1);
                else if(corner == CornerType.BRCorner) content = getGoldCornerContent().get(2);
                else content = getGoldCornerContent().get(3);
            }
            case InitialCard -> {
                if(corner == CornerType.TLCorner) content = getInitialCornerContent().get(0);
                else if(corner == CornerType.TRCorner) content = getInitialCornerContent().get(1);
                else if(corner == CornerType.BRCorner) content = getInitialCornerContent().get(2);
                else content = getInitialCornerContent().get(3);
            }
        }
        return content;
    }


    /**
     * Retrieves the content of the gold corners of the card as a list of strings.
     * This method is intended to be overridden in subclasses such as GoldCard to provide specific behavior.
     *
     * @author Margherita Marino
     * @return A list containing the gold corner content strings.
     */
    public  List<String> getGoldCornerContent(){
       return null;
    }

    /**
     * Retrieves the content of the gold corners of the card as a list of strings.
     * This method is intended to be overridden in subclasses such as ResourceCard to provide specific behavior.
     *
     * @author Margherita Marino
     * @return A list containing the gold corner content strings.
     */
    public  List<String> getResourceCornerContent(){
        return null;
    }

    /**
     * Retrieves the content of the gold corners of the card as a list of strings.
     * This method is intended to be overridden in subclasses such as InitialCard to provide specific behavior.
     *
     * @author Margherita Marino
     * @return A list containing the gold corner content strings.
     */
    public List<String> getInitialCornerContent() {
        return null;
    }


    /**
     * Returns the number of victory points associated with this card.
     * This method is intended to be overridden in subclasses to provide specific behavior.
     * functionality based on the type of card.
     *
     * @author Margherita Marino
     * @return The number of victory points associated with this card.
     */
    public int getVictoryPoints(){
        return 0;
    }

    /**
     * Calculates and returns the number of points of the provided PlayableCard.
     * If the card is a GoldCard or a ResourceCard, it retrieves the victory points
     * using the {@link #getVictoryPoints()} method. Otherwise, it returns 0.
     *
     * @author Margherita Marino
     * @return The number of points of the provided PlayableCard.
     */
    public int getCardPoints(){
        int points = 0;
        switch(cardType){
            case GoldCard, ResourceCard -> {
                points = getVictoryPoints();
            }
            case InitialCard -> {}
        }
        return points;
    }


    public int getNumResources() {
        return 0;
    }

    public Object getSymbol() {
        return null;
    }

    public boolean hasSymbol() {
        return false;
    }

    public ResourceType getCardMainResource() {
        ResourceType mainResource = null;
        switch(cardType){
            case GoldCard, ResourceCard -> {
                mainResource = getMainResource();
            }
            case InitialCard -> {}
        }
        return mainResource;

}
