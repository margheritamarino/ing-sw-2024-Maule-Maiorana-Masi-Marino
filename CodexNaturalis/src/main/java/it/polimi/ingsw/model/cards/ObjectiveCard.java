package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Objective card class
 */
public class ObjectiveCard implements Serializable {
    private int cardID;
    private boolean isFront;
    private GoalType goalType;
    private int victoryPoints;
    private ResourceType mainResource;
    private CornerType direction;
    private int numResources;
    private int numSymbols;
    private List<SymbolType> symbols;
    private ResourceType secondResource;

    /**
     * this method is used for the GUI
     * @return the path for the specific istance of Objective Card (contained in the package resources-->img)
     */
    public String getImagePath(){
        String path;
        int idTemp= this.getCardID();

        if(this.isFront()){
            path="/img/Cards/ObjectiveCards/"+ idTemp+ "_ObjectiveFront.png";

        } else {
            path="/img/Cards/ObjectiveCards/ObjectiveBack.png";
        }
        return Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
    }

    /**
     * Retrieves the unique identifier for the objective card.
     *
     * @return The card's ID.
     */
    public int getCardID() {
        return cardID;
    }

    /**
     * Checks if the objective card is front-facing.
     *
     * @return {@code true} if the card is front-facing, {@code false} otherwise.
     */
    public boolean isFront() {
        return isFront;
    }

    /**
     * Retrieves the goal type associated with this objective card.
     *
     * @return The goal type.
     */
    public GoalType getGoalType() {
        return goalType;
    }

    /**
     * Retrieves the victory points awarded by this objective card.
     *
     * @return The victory points.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Retrieves the number of symbols required by this objective card.
     *
     * @return The number of symbols.
     */
    public int getNumSymbols() {
        return numSymbols;
    }

    /**
     * Retrieves the list of symbols associated with this objective card.
     *
     * @return The list of symbols.
     */
    public List<SymbolType> getSymbols() {
        return symbols;
    }


    /**
     * Retrieves the number of resources required by this objective card.
     *
     * @return The number of resources.
     */
    public int getNumResources() {
        return numResources;
    }

    /**
     * Retrieves the main resource type associated with this objective card.
     *
     * @return The main resource type.
     */
    public ResourceType getMainResource() {
        return mainResource;
    }

    /**
     * Retrieves the placement direction associated with this objective card.
     *
     * @return The placement direction.
     */
    public CornerType getDirection() {
        return direction;
    }

    /**
     * Retrieves the secondary resource type associated with this objective card.
     *
     * @return The secondary resource type.
     */
    public ResourceType getSecondResource() {
        return secondResource;
    }

    /**
     * Constructs an ObjectiveCard with the specified attributes.
     *
     * @param cardID The unique identifier of the objective card.
     * @param isFront Indicates whether the objective card is front-facing.
     * @param goalType The type of goal associated with the objective card.
     * @param victoryPoints The number of victory points awarded by this objective card.
     * @param mainResource The main resource type required or associated with this objective card.
     * @param direction The placement direction or corner type associated with this objective card.
     * @param numResources The number of resources required by this objective card.
     * @param numSymbols The number of symbols required by this objective card.
     * @param symbols The list of symbol types associated with this objective card.
     * @param secondResource The secondary resource type associated with this objective card.
     */
    public ObjectiveCard(int cardID, boolean isFront, GoalType goalType, int victoryPoints, ResourceType mainResource, CornerType direction, int numResources, int numSymbols, List<SymbolType> symbols, ResourceType secondResource) {
        this.cardID = cardID;
        this.isFront = isFront;
        this.goalType = goalType;
        this.victoryPoints = victoryPoints;
        this.numSymbols = numSymbols;
        this.symbols = symbols;
        this.numResources = numResources;
        this.mainResource = mainResource;
        this.direction = direction;
        this.secondResource = secondResource;
    }

    /**
     * Provides a string representation of the {@code ObjectiveCard} for display purposes.
     * This includes details such as points and conditions.
     *
     * @return A formatted string representing the card.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String condition = " ";
        List<String> emojiSymbol = new ArrayList<>();

        switch (goalType) {
            case ResourceCondition:
                condition = convertToEmoji(mainResource.toString());
                break;
            case SymbolCondition:
                List<SymbolType> symbols = getSymbols();
                for (SymbolType s : symbols) {
                    emojiSymbol.add(convertToEmoji(s.toString()));
                }
                break;
            case DiagonalPlacement:
            case LPlacement:
                condition = direction.toString();
                break;
            default:
        }

        String cardTypeName = "ObjectiveCard";
        String pointsLine = "Points: [" + victoryPoints + "]";
        String goalLine = "Goal: " + goalType.toString();

        List<String> contentLines = new ArrayList<>();
        contentLines.add(cardTypeName);
        contentLines.add(pointsLine);
        contentLines.add(goalLine);

        StringBuilder conditionLine = new StringBuilder(condition);
        for (String symbol : emojiSymbol) {
            conditionLine.append(" ").append(symbol);
        }
        contentLines.add(conditionLine.toString());

        int maxWidth = 25;
        for (String line : contentLines) {
            maxWidth = Math.max(maxWidth, line.length());
        }
        maxWidth += 4;

        int cardHeight = DefaultValue.printHeight;

        String borderLine = "+" + "-".repeat(maxWidth - 2) + "+";
        result.append(borderLine).append("\n");

        for (String line : contentLines) {
            int padding = Math.max(0, (maxWidth - line.length() - 4) / 2);
            result.append("| ")
                    .append(" ".repeat(padding))
                    .append(line)
                    .append(" ".repeat(maxWidth - padding - line.length() - 4))
                    .append(" |\n");
        }

        for (int i = contentLines.size(); i < cardHeight - 2; i++) {
            result.append("| ").append(" ".repeat(maxWidth - 4)).append(" |\n");
        }

        result.append(borderLine).append("\n");


        return result.toString();
    }

    /**
     * Converts a string representation of a resource type into its corresponding emoji.
     * This method supports converting specific resource types used in the game into emoji symbols.
     *
     * @param input The string representation of the resource type to convert.
     * @return The corresponding emoji symbol as a string, or "E" if no matching emoji is found.
     */
    public String convertToEmoji(String input){
        String output;
        if(input.equals("Fungi")){
            output =  "\uD83C\uDF44";
        }else if(input.equals("Animal")){
            output = "\uD83D\uDC3A";
        }else if(input.equals("Insect")){
            output = "\uD83E\uDD8B";
        }else if(input.equals("Plant")){
            output = "\uD83C\uDF40";
        }else if(input.equals("NoCorner")){
            output = "\u274C";
        }else if(input.equals("Ink")){
            output = "\u26AB";
        }else if(input.equals("Manuscript")){
            output= "\uD83D\uDCDC";
        }else if(input.equals("Quill")){
            output = "\uD83E\uDEB6";
        }else output = input;
        return output;
    }
}
