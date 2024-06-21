package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import org.fusesource.jansi.Ansi;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Gold card class
 * GoldCard is a type of PlayableCard with additional properties
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


    /**
     * Returns the number of resources associated with a card
     *
     * @return the number of resources
     */
    @Override
    public int getNumResources() {
        return 0;
    }

    /**
     * Gets the main resource type of the card.
     *
     * @return the main resource type
     */
    @Override
    public ResourceType getMainResource() {
        return mainResource;
    }

    /**
     * Checks if the card has a symbol.
     *
     * @return true if the card has a symbol, false otherwise
     */
    public boolean hasSymbol() {
        return hasSymbol;
    }

    /**
     * Gets the symbol type of the card.
     *
     * @return the symbol type
     */
    @Override
    public SymbolType getSymbol() {
        return symbol;
    }

    /**
     * Gets the number of victory points the card provides.
     *
     * @return the number of victory points
     */
    @Override
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Gets the list of resource types required for placement.
     *
     * @return the list of resource types for placement condition
     */
    public List<ResourceType> getPlacementCondition() {
        return placementCondition;
    }

    /**
     * Checks if there is a points condition.
     *
     * @return true if there is a points condition, false otherwise
     */
    public boolean isPointsCondition() {
        return pointsCondition;
    }

    /**
     * Checks if there is a corner condition.
     *
     * @return true if there is a corner condition, false otherwise
     */
    public boolean isCornerCondition() {
        return cornerCondition;
    }

    /**
     * Gets the symbol type required for the condition.
     *
     * @return the symbol type for the condition
     */
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
    public List<String> getCornerContent() {
        List<String> cornerContent = new ArrayList<>();

        cornerContent.add(getCornerContentString(getTLCorner()));
        cornerContent.add(getCornerContentString(getTRCorner()));
        cornerContent.add(getCornerContentString(getBRCorner()));
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
                    return symbol.toString();
                case NoCorner:
                    return "NoCorner";
                default:
                    throw new IllegalArgumentException();
            }

        } else System.out.println("cornerLabel is null!!! \n Errors comes on GoldCard "+ this.getCardID());
        return "error";
    }

    /**
     * Gets the central resources of the card.
     *
     * @return the list of central resources
     */
    public List<ResourceType> getCentralResources() {
        return null;
    }

    /**
     * Gets the number of central resources of the card.
     *
     * @return the number of central resources
     */
    public int getNumCentralResources() {
        return 0;
    }


    /**
     * Gets the list of all resources associated with the card.
     *
     * @return the list of resources
     */
    public List<ResourceType> getResourceList() {
        return null;
    }


    /**
     * Constructs a GoldCard with the specified properties.
     *
     * @param cardID             the unique ID of the card
     * @param numCorners         the number of corners the card has
     * @param isFront            whether the card is front-facing
     * @param cardType           the type of the card
     * @param TLCorner           the top-left corner label
     * @param TRCorner           the top-right corner label
     * @param BRCorner           the bottom-right corner label
     * @param BLCorner           the bottom-left corner label
     * @param mainResource       the main resource type of the card
     * @param hasSymbol          whether the card has a symbol
     * @param symbol             the symbol type of the card
     * @param victoryPoints      the number of victory points the card provides
     * @param placementCondition the resource types required for placement
     * @param pointsCondition    whether there is a points condition
     * @param cornerCondition    whether there is a corner condition
     * @param symbolCondition    the symbol type required for the condition
     */
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
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String cardTypeName = "GoldCard";
        String FoB = isFront() ? "Front" : "Back";
        String victoryPoints = "P:" + getVictoryPoints();
        if (isPointsCondition() && !isCornerCondition()) {
            victoryPoints += "|" + convertToEmoji(getSymbolCondition().toString());
        } else if (isPointsCondition() && isCornerCondition()) {
            victoryPoints += "|[" + convertToEmoji("CoveredCorner") + "]";
        }

        List<String> corners = getCornerContent();
        String topLeft = padAndBorderEmoji(convertToEmoji(corners.get(0)));
        String topRight = padAndBorderEmoji(convertToEmoji(corners.get(1)));
        String bottomRight = padAndBorderEmoji(convertToEmoji(corners.get(2)));
        String bottomLeft = padAndBorderEmoji(convertToEmoji(corners.get(3)));

        int width = DefaultValue.printLenght;
        int height = DefaultValue.printHeight;

        // Map ANSI colors
        Map<Ansi.Color, String> ansiColorMap = new HashMap<>();
        ansiColorMap.put(Ansi.Color.RED, "\u001B[31m");
        ansiColorMap.put(Ansi.Color.MAGENTA, "\u001B[35m");
        ansiColorMap.put(Ansi.Color.GREEN, "\u001B[32m");
        ansiColorMap.put(Ansi.Color.CYAN, "\u001B[36m");
        ansiColorMap.put(Ansi.Color.DEFAULT, "\u001B[0m");

        // Get the color based on the main resource
        Ansi.Color textColor = getColorForResource(getMainResource());

        // Define the ANSI color codes
        String colorCode = ansiColorMap.getOrDefault(textColor, "\u001B[0m");
        String resetCode = "\u001B[0m";

        // Apply color to the border
        String border = colorCode + "+" + "-".repeat(width) + "+" + resetCode;

        int contentRows = 4;

        result.append(border).append("\n");

        int paddingVictoryPoints = Math.max(0, (width - victoryPoints.length() - 8) / 2);
        result.append(colorCode + "|")
                .append(topLeft)
                .append(" ".repeat(paddingVictoryPoints))
                .append(victoryPoints)
                .append(" ".repeat(Math.max(0, width - paddingVictoryPoints - victoryPoints.length() - 8)))
                .append(topRight)
                .append("|\n" + resetCode);

        String cardTypeLine = cardTypeName;
        int paddingType = Math.max(0, (width - cardTypeLine.length()) / 2);
        result.append(colorCode + "|")
                .append(" ".repeat(paddingType))
                .append(cardTypeLine)
                .append(" ".repeat(Math.max(0, width - paddingType - cardTypeLine.length())))
                .append("|\n" + resetCode);

        String faceLine = "(" + FoB + ")";
        int paddingFace = Math.max(0, (width - faceLine.length()) / 2);
        result.append(colorCode + "|")
                .append(" ".repeat(paddingFace))
                .append(faceLine)
                .append(" ".repeat(Math.max(0, width - paddingFace - faceLine.length())))
                .append("|\n" + resetCode);

        String mainResource = convertToEmoji(getMainResource().toString());
        int paddingMainResource = Math.max(0, (width - calculateEmojiWidth(mainResource)) / 2);
        result.append(colorCode + "|")
                .append(" ".repeat(paddingMainResource))
                .append(mainResource)
                .append(" ".repeat(Math.max(0, width - paddingMainResource - calculateEmojiWidth(mainResource))))
                .append("|\n" + resetCode);

        int remainingHeight = height - (contentRows + 2);
        for (int i = 0; i < remainingHeight; i++) {
            result.append(colorCode + "|").append(" ".repeat(width)).append("|\n" + resetCode);
        }

        List<ResourceType> placementCondition = getPlacementCondition();
        List<String> placementConditionEmojis = new ArrayList<>();
        for (ResourceType condition : placementCondition) {
            placementConditionEmojis.add(convertToEmoji(condition.toString()));
        }
        String placementConditionString = String.join(" ", placementConditionEmojis);
        int paddingPlacement = Math.max(0, (width - placementConditionString.length() - 8) / 2);
        result.append(colorCode + "|")
                .append(bottomLeft)
                .append(" ".repeat(paddingPlacement))
                .append(placementConditionString)
                .append(" ".repeat(Math.max(0, width - paddingPlacement - placementConditionString.length() - 8)))
                .append(bottomRight)
                .append("|\n" + resetCode);

        result.append(border).append("\n");

        return result.toString();
    }


    /**
     * Calculates the width of a given string containing emojis.
     * This method takes into account the variable width of emojis and characters.
     *
     * @param input the string containing emojis to measure
     * @return the calculated width of the string
     */
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

    /**
     * Adds a border to the provided emoji or symbol string.
     * Ensures the emoji or symbol fits within a defined width by adding necessary padding.
     *
     * @param content the emoji or symbol string to pad and border
     * @return the padded and bordered string
     */
    private String padAndBorderEmoji(String content) {
        int emojiWidth = calculateEmojiWidth(content);
        int padding = Math.max(0, 2 - emojiWidth);
        return "[" + content + " ".repeat(padding) + "]";
    }

    /**
     * Returns the ANSI color based on the main resource.
     *
     * @param mainResource the main resource of the card
     * @return the ANSI color
     */
    public Ansi.Color getColorForResource(ResourceType mainResource) {
        return switch (mainResource) {
            case Fungi -> Ansi.Color.RED;
            case Insect -> Ansi.Color.MAGENTA;
            case Plant -> Ansi.Color.GREEN;
            case Animal -> Ansi.Color.CYAN;
            default -> Ansi.Color.DEFAULT;
        };
    }




}

