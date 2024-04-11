package it.polimi.ingsw.model;

import java.util.ArrayList;
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

}
