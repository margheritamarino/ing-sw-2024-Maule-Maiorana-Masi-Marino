package it.polimi.ingsw.model.interfaces;

import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.CornerLabel;
import it.polimi.ingsw.model.SymbolType;
import it.polimi.ingsw.model.ResourceType;
import java.util.*;


//Capisci se ti serve aggiungere ResourceTypeIC e SymbolTypeIC
//(nel caso modifica i metodi e fai ritornare le interfacce ResourceTypeIC e SymbolTypeIC)

public interface PlayableCardIC {

    /**
     * @return the ID of the playable card
     */
    int getCardID();

    /**
     * @return the number of corners of the playable card
     */
     int getNumCorners();

    /**
     * @return if is the Front of the card
     */
    boolean isFront();

    /**
     * @return the type of the playable card
     */
    CardType getCardType();

    /**
     * @return the top left corner of the playable card
     */
    CornerLabel getTLCorner();

    /**
     * @return the top right corner of the playable card
     */
    CornerLabel getTRCorner();

    /**
     * @return the bottom left corner of the playable card
     */
    CornerLabel getBLCorner();

    /**
     * @return the bottom right corner of the playable card
     */
    CornerLabel getBRCorner();

    /**
     * @param corner represent the position of the corner: 0 TLCorner, 1 TRCorner, 2 BRCorner, 3 BLCorner
     * @return The content of the specified corner as a string.
     */
    String getCornerContent(int corner);

    List<String> getCornerContent();
    int getVictoryPoints();
    int getNumResources();
    SymbolType getSymbol();
    boolean hasSymbol();
    ResourceType getMainResource();
    List<ResourceType> getCentralResources();
    int getNumCentralResources();
    List<ResourceType> getResourceList();
    List<ResourceType> getPlacementCondition();
    boolean isPointsCondition();
    boolean isCornerCondition();
    SymbolType getSymbolCondition();








}
