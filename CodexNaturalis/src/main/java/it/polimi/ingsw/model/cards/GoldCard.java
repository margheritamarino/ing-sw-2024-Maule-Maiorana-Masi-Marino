package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import org.fusesource.jansi.Ansi;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class GoldCard extends PlayableCard implements Serializable {

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
        if(cornerLabel != null){
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

        } else System.out.println("cornerLabel is null!!! \n Errors comes on GoldCard "+ this.getCardID());
        return "error";
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

    /*public GoldCard() {
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
    }*/


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
        String conditionPoint = "";
        String FoB = isFront() ? "Front" : "Back";

        // Cambia il colore della carta in base alla mainResource
        switch (mainResource) {
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

        if (!isPointsCondition()) {
            conditionPoint = "no condition";
        } else if (isPointsCondition() && isCornerCondition()) {
            conditionPoint = "cornerCondition";
        } else if (isPointsCondition() && !isCornerCondition()) {
            conditionPoint = convertToEmoji(symbolCondition.toString());
        }

        String cardTypeName = "Gold";
        int points = victoryPoints;
        List<String> corners = getCornerContent();
        List<String> emojiCorners = new ArrayList<>();
        for (String corner : corners) {
            emojiCorners.add(convertToEmoji(corner));
        }

        List<ResourceType> conditionList = getPlacementCondition();
        List<String> conditionEmoji = new ArrayList<>();
        for (ResourceType condition : conditionList) {
            conditionEmoji.add(convertToEmoji(condition.toString()));
        }

        // Costruzione delle righe del contenuto
        List<String> contentLines = new ArrayList<>();
        contentLines.add("CardType: " + cardTypeName);
        contentLines.add("Face: " + FoB);
        contentLines.add("Points: " + points);
        contentLines.add("Corners: " + String.join(" ", emojiCorners));
        contentLines.add("PointsC: " + conditionPoint);
        contentLines.add("PlacementC: " + String.join(" ", conditionEmoji));

        // Trova la lunghezza massima delle linee di contenuto
        int maxWidth = DefaultValue.printLenght;
        /*
        for (String line : contentLines) {
            maxWidth = Math.max(maxWidth, line.length());
        }

         */

        // Costruzione del bordo superiore
        String borderLine = "+" + "-".repeat(maxWidth + 2) + "+";
        result.append(borderLine).append("\n");

        // Costruzione delle linee di contenuto con bordi laterali


        for (String line : contentLines) {
            result.append("| ").append(line);
            // Aggiungi spazi per allineare al massimo
            result.append(" ".repeat(maxWidth - line.length()));
            result.append(" |\n");
        }
        for(int i=contentLines.size(); i< DefaultValue.printHeight; i++){
            result.append("| ");
            // Aggiungi spazi per allineare al massimo
            result.append(" ".repeat(maxWidth));
            result.append(" |\n");
        }

        // Costruzione del bordo inferiore
        result.append(borderLine);

        // Applicazione del colore
        String finalResult = ansi().fg(textColor).bg(bgColor).a(result.toString()).reset().toString();

        return finalResult;
    }


}

