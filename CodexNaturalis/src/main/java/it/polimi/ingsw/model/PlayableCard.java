package it.polimi.ingsw.model;

import java.util.Map;

public abstract class PlayableCard {
    private int cardID;
    private Map<Corner, PlayableCard> cornersMap;
    private int numCorners;
    private boolean isFront;
    private CardType cardType;
    private CornerLabel TLCorner;
    private CornerLabel TRCorner;
    private CornerLabel BRCorner;
    private CornerLabel BLCorner;



}
