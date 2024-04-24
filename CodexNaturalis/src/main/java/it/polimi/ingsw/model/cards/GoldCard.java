package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.CornerLabel;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;

import java.util.ArrayList;
import java.util.List;

public class GoldCard extends PlayableCard {

    private ResourceType mainResource;
    private boolean hasSymbol;
    private SymbolType symbol;
    private int victoryPoints;
    private List<ResourceType> placementCondition;
    private boolean pointsCondition;
    private boolean cornerCondition;
    private SymbolType symbolCondition;


    @Override
    public int getNumResources() {
        return 0;
    }
    @Override
    public ResourceType getMainResource() {
        return mainResource;
    }


    public boolean hasSymbol() {
        return hasSymbol;
    }
    @Override
    public SymbolType getSymbol() {
        return symbol;
    }
    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    public List<ResourceType> getPlacementCondition() {
        return placementCondition;
    }

    public boolean isPointsCondition() {
        return pointsCondition;
    }

    public boolean isCornerCondition() {
        return cornerCondition;
    }

    public SymbolType getSymbolCondition() {
        return symbolCondition;
    }


    /**
     * Retrieves the content of the initial corners of the card as a list of strings.
     * Each string represents the content of a corner in the following order:
     * top-left, top-right, bottom-right, bottom-left.
     * @author Margherita Marino
     * @return A list containing the corner content strings.
     */
    @Override
    public List<String> getCornerContent() { // Metodo per ottenere una lista di stringhe rappresentanti il contenuto degli angoli
        List<String> cornerContent = new ArrayList<>();

        // Angolo in alto a SX
        cornerContent.add(getCornerContentString(getTLCorner()));

        // Angolo in alto a DX
        cornerContent.add(getCornerContentString(getTRCorner()));

        // Angolo in basso a DX
        cornerContent.add(getCornerContentString(getBRCorner()));

        // Angolo in basso a SX
        cornerContent.add(getCornerContentString(getBLCorner()));

        return cornerContent;
    }

    /**
     * Retrieves the string representation of the content of a corner.
     *
     * @param cornerLabel The label indicating the type of content in the corner.
     * @return The string representing the corner content.
     * @throws IllegalArgumentException If an invalid corner label is provided.
     */
    public String getCornerContentString(CornerLabel cornerLabel) {
        switch (cornerLabel) {
            case Empty:
                return "Empty";
            case WithSymbol:
                // Se l'angolo contiene un simbolo, restituisci il simbolo effettivo
                return symbol.toString();
            case NoCorner:
                return "NoCorner";
            default:
                throw new IllegalArgumentException();
        }
    }

    public List<ResourceType> getCentralResources() {
        return null;
    }
    public int getNumCentralResources() {
        return 0;
    }
    public List<ResourceType> getResourceList() {
        return null;
    }

    public GoldCard() {
        // Chiama il costruttore della classe base PlayableCard
        super();
        this.victoryPoints = 0;
        this.mainResource = null;
        this.hasSymbol = false;
        this.symbol = null;
        this.placementCondition = new ArrayList<>();
        this.pointsCondition = false;
        this.cornerCondition = false;
        this.symbolCondition = null;
    }


    public GoldCard(int cardID, int numCorners, boolean isFront, CardType cardType, CornerLabel TLCorner, CornerLabel TRCorner, CornerLabel BRCorner, CornerLabel BLCorner, ResourceType mainResource, boolean hasSymbol, SymbolType symbol, int victoryPoints, List<ResourceType> placementCondition, boolean pointsCondition, boolean cornerCondition, SymbolType symbolCondition) {
        super(cardID, numCorners, isFront, cardType, TLCorner, TRCorner, BRCorner, BLCorner);
        this.mainResource = mainResource;
        this.hasSymbol = hasSymbol;
        this.symbol = symbol;
        this.victoryPoints = victoryPoints;
        this.placementCondition = placementCondition;
        this.pointsCondition = pointsCondition;
        this.cornerCondition = cornerCondition;
        this.symbolCondition = symbolCondition;
    }
}

