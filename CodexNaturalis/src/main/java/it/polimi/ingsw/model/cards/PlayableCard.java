package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import it.polimi.ingsw.model.interfaces.PlayableCardIC;
import org.fusesource.jansi.Ansi;

import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

public abstract class PlayableCard implements PlayableCardIC {
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
    public String toString() { //capire se serve aggiungere altro
        StringBuilder result = new StringBuilder();

        // Colori per le diverse tipologie di carte
        Ansi.Color textColor;
        Ansi.Color bgColor;
        String cardTypeName;

        // Impostazione dei colori e del nome della tipologia di carta
        switch (cardType) {
            case InitialCard:
                textColor = Ansi.Color.WHITE;
                bgColor = Ansi.Color.BLUE;
                cardTypeName = "Initial";
                break;
            case ResourceCard:
                textColor = Ansi.Color.WHITE;
                bgColor = Ansi.Color.RED;
                cardTypeName = "Resource Card";
                break;
            case GoldCard:
                textColor = Ansi.Color.WHITE;
                bgColor = Ansi.Color.YELLOW;
                cardTypeName = "Gold Card";
                break;
            default:
                // Nel caso in cui il tipo di carta non sia riconosciuto
                textColor = Ansi.Color.DEFAULT;
                bgColor = Ansi.Color.DEFAULT;
                cardTypeName = "Unknown Card";
        }

        // Costruzione del risultato con colori e nome della carta
        result.append(ansi().fg(textColor).bg(bgColor).a(" "));
        result.append(cardTypeName);
        result.append(" ");
        result.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));

        return result.toString();
    }

}
