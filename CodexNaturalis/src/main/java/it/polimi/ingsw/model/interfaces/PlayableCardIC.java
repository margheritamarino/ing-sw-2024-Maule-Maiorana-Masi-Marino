package it.polimi.ingsw.model.interfaces;

import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.CornerLabel;
import it.polimi.ingsw.model.SymbolType;
import it.polimi.ingsw.model.ResourceType;
import java.util.*;

/**
 * Represents an interface for a playable card.
 * <p>
 * This interface defines methods to retrieve various properties and characteristics
 * of a playable card used in the game.
 * </p>
 */
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
     * Retrieves the content of the specified corner of the playable card as a string.
     *
     * @param corner the position of the corner:
     *               0 for top left corner (TLCorner),
     *               1 for top right corner (TRCorner),
     *               2 for bottom right corner (BRCorner),
     *               3 for bottom left corner (BLCorner)
     * @return the content of the specified corner as a string
     */
    String getCornerContent(int corner);

    /**
     * Retrieves the list of all corner contents of the playable card.
     *
     * @return the list of all corner contents of the playable card
     */
    List<String> getCornerContent();
    /**
     * Retrieves the victory points of the playable card.
     *
     * @return the victory points of the playable card
     */
    int getVictoryPoints();
    /**
     * Retrieves the number of resources on the playable card.
     *
     * @return the number of resources on the playable card
     */
    int getNumResources();
    /**
     * Retrieves the symbol type of the playable card.
     *
     * @return the symbol type of the playable card
     */
    SymbolType getSymbol();
    /**
     * Checks if the playable card has a symbol.
     *
     * @return {@code true} if the playable card has a symbol, {@code false} otherwise
     */
    boolean hasSymbol();
    /**
     * Retrieves the main resource type of the playable card.
     *
     * @return the main resource type of the playable card
     */
    ResourceType getMainResource();
    /**
     * Retrieves the list of central resources of the playable card.
     *
     * @return the list of central resources of the playable card
     */
    List<ResourceType> getCentralResources();
    /**
     * Retrieves the number of central resources on the playable card.
     *
     * @return the number of central resources on the playable card
     */
    int getNumCentralResources();
    /**
     * Retrieves the list of all resources on the playable card.
     *
     * @return the list of all resources on the playable card
     */
    List<ResourceType> getResourceList();

    /**
     * Retrieves the list of placement conditions for the playable card.
     *
     * @return the list of placement conditions for the playable card
     */
    List<ResourceType> getPlacementCondition();
    /**
     * Checks if the playable card has points as a placement condition.
     *
     * @return {@code true} if the playable card has points as a placement condition, {@code false} otherwise
     */
    boolean isPointsCondition();
    /**
     * Checks if the playable card has corner conditions for placement.
     *
     * @return {@code true} if the playable card has corner conditions for placement, {@code false} otherwise
     */
    boolean isCornerCondition();
    /**
     * Retrieves the symbol type condition for the playable card.
     *
     * @return the symbol type condition for the playable card
     */
    SymbolType getSymbolCondition();









}
