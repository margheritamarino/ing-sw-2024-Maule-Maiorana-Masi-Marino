package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import org.fusesource.jansi.Ansi;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Gold card class
 */
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
     *
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

    /**
     * Provides a string representation of the {@code GoldCard} for display purposes.
     * This includes details such as card type, face direction, points, corners, and conditions.
     *
     * @return A formatted string representing the card.
     */
   /* @Override
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
    }*/
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String cardTypeName = "GoldCard";
        String FoB = isFront() ? "Front" : "Back";

        // Costruzione della stringa dei punti vittoria con condizioni
        String victoryPoints = "P:" + getVictoryPoints();
        if (isPointsCondition() && !isCornerCondition()) {
            victoryPoints += "|" + convertToEmoji(getSymbolCondition().toString());
        } else if (isPointsCondition() && isCornerCondition()) {
            victoryPoints += "|["+ convertToEmoji("CoveredCorner")+ "]";
        }

        // Lettura delle risorse dagli angoli
        List<String> corners = getCornerContent();
        String topLeft = padAndBorderEmoji(convertToEmoji(corners.get(0)));
        String topRight = padAndBorderEmoji(convertToEmoji(corners.get(1)));
        String bottomRight = padAndBorderEmoji(convertToEmoji(corners.get(2)));
        String bottomLeft = padAndBorderEmoji(convertToEmoji(corners.get(3)));

        // Costruzione della carta
        int width = DefaultValue.printLenght;
        int height = DefaultValue.printHeight;
        String border = "+" + "-".repeat(width) + "+";

        // Calcolo delle righe di contenuto effettive
        int contentRows = 4;

        // Bordo superiore
        result.append(border).append("\n");

        // Riga con i punti vittoria centrati tra le emoji degli angoli superiori
        int paddingVictoryPoints = Math.max(0, (width - victoryPoints.length() - 8) / 2);
        result.append("|")
                .append(topLeft)
                .append(" ".repeat(paddingVictoryPoints))
                .append(victoryPoints)
                .append(" ".repeat(Math.max(0, width - paddingVictoryPoints - victoryPoints.length() - 8)))
                .append(topRight)
                .append("|\n");

        // Riga con il tipo di carta centrato
        String cardTypeLine = cardTypeName;
        int paddingType = Math.max(0, (width - cardTypeLine.length()) / 2);
        result.append("|")
                .append(" ".repeat(paddingType))
                .append(cardTypeLine)
                .append(" ".repeat(Math.max(0, width - paddingType - cardTypeLine.length())))
                .append("|\n");

        // Riga con il lato della carta centrato
        String faceLine = "(" + FoB + ")";
        int paddingFace = Math.max(0, (width - faceLine.length()) / 2);
        result.append("|")
                .append(" ".repeat(paddingFace))
                .append(faceLine)
                .append(" ".repeat(Math.max(0, width - paddingFace - faceLine.length())))
                .append("|\n");

        // Riga con il contenuto centrale (centrato)
        String mainResource = convertToEmoji(getMainResource().toString());
        int paddingMainResource = Math.max(0, (width - calculateEmojiWidth(mainResource)) / 2);
        result.append("|")
                .append(" ".repeat(paddingMainResource))
                .append(mainResource)
                .append(" ".repeat(Math.max(0, width - paddingMainResource - calculateEmojiWidth(mainResource))))
                .append("|\n");

        // Aggiungi righe vuote fino a raggiungere l'altezza desiderata della carta
        int remainingHeight = height - (contentRows + 2);
        for (int i = 0; i < remainingHeight; i++) {
            result.append("|").append(" ".repeat(width)).append("|\n");
        }

        // Riga inferiore con angoli e condizioni di posizionamento
        List<ResourceType> placementCondition = getPlacementCondition();
        List<String> placementConditionEmojis = new ArrayList<>();
        for (ResourceType condition : placementCondition) {
            placementConditionEmojis.add(convertToEmoji(condition.toString()));
        }
        String placementConditionString = String.join(" ", placementConditionEmojis);
        int paddingPlacement = Math.max(0, (width - placementConditionString.length() - 8) / 2);
        result.append("|")
                .append(bottomLeft)
                .append(" ".repeat(paddingPlacement))
                .append(placementConditionString)
                .append(" ".repeat(Math.max(0, width - paddingPlacement - placementConditionString.length() - 8)))
                .append(bottomRight)
                .append("|\n");

        // Bordo inferiore
        result.append(border).append("\n");

        return result.toString();
    }

    // Metodo per calcolare la larghezza delle emoji
    private int calculateEmojiWidth(String input) {
        int width = 0;
        for (int i = 0; i < input.length(); ) {
            int codePoint = input.codePointAt(i);
            if (Character.charCount(codePoint) > 1 || input.codePointAt(i) > 0xFFFF) {
                width += 2;
            } else {
                width += 1;
            }
            i += Character.charCount(codePoint);
        }
        return width;
    }

    // Metodo per contornare le emoji o i simboli
    private String padAndBorderEmoji(String content) {
        int emojiWidth = calculateEmojiWidth(content);
        int padding = Math.max(0, 2 - emojiWidth);
        return "[" + content + " ".repeat(padding) + "]";
    }


}

