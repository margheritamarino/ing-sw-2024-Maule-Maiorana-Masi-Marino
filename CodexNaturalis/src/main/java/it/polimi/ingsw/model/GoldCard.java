package it.polimi.ingsw.model;

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


    public ResourceType getMainResource() {
        return mainResource;
    }

    public boolean isHasSymbol() {
        return hasSymbol;
    }

    public SymbolType getSymbol() {
        return symbol;
    }

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


    public List<String> getGoldCornerContent() { // Metodo per ottenere una lista di stringhe rappresentanti il contenuto degli angoli
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

    private String getCornerContentString(CornerLabel cornerLabel) {
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
}

