package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import org.fusesource.jansi.Ansi;


import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Ansi.Color bgColor;
        Ansi.Color textColor = Ansi.Color.WHITE;
        String conditionPoint;

        //cambia il colore della carta in base alla mainResource
        switch(mainResource){
            case Fungi:
                bgColor = Ansi.Color.RED;
                break;
            case Insect:
                bgColor = Ansi.Color.MAGENTA;
                break;
            case Plant:
                bgColor = Ansi.Color.GREEN;
                break;
            case Animal:
                bgColor = Ansi.Color.BLUE;
                break;
            default:
                bgColor = Ansi.Color.DEFAULT;
        }

        if(isCornerCondition()){
             conditionPoint = "cornerCondition";
        }else if(!isPointsCondition()){
             conditionPoint = "no condition";
        }else{
             conditionPoint = symbolCondition.toString();
        }

        String cardTypeName = "Gold";
        int points = victoryPoints;
        List<String> corners = getCornerContent();
        List<ResourceType> conditionList = getPlacementCondition();

        // Costruzione del risultato con colori e nome della carta
        result.append(ansi().fg(textColor).bg(bgColor).a(" "));
        result.append("CardType: ");
        result.append(cardTypeName);
        result.append("\n");
        result.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
        result.append("Points: ");
        result.append(points);
        result.append("\n");
        result.append("Corners: ");
        String formattedCorners = String.join(" ", corners);
        result.append(formattedCorners);
        result.append("\n");
        result.append("PointsCondition: ");
        result.append(conditionPoint);
        result.append("\n");
        result.append("Placement condition: ");
        // Aggiungi gli elementi di conditionList formattati con spazi tra di loro
        for (int i = 0; i < conditionList.size(); i++) {
            result.append(conditionList.get(i));
            // Aggiungi uno spazio solo se non è l'ultimo elemento della lista
            if (i < conditionList.size() - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }
}

