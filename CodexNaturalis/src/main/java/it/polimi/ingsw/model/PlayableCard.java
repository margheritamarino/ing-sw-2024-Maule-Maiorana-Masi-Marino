package it.polimi.ingsw.model;

public abstract class PlayableCard {
    private int cardID;
    private Map<Corner, playableCard> cornersMap;
    private int numCorners;
    private boolean isFront;
    private CardType cardType;
    private CornerLabel TLCorner;
    private CornerLabel TRCorner;
    private CornerLabel BRCorner;
    private CornerLabel BLCorner;



}
