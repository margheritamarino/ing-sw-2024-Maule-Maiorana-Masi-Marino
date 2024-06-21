package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;

import java.io.Serializable;
import java.util.*;

/**
 * ResourceCard class
 * ResourceCard is a type of PlayableCard with additional properties
 */
public class ResourceCard extends PlayableCard implements Serializable {
   private ResourceType mainResource;
   private int victoryPoints;
   private int numResources;
   private List<ResourceType> resourceList;
   private boolean hasSymbol;
   private SymbolType symbol;

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
    * Gets the number of victory points the card provides.
    *
    * @return the number of victory points
    */
   @Override
   public int getVictoryPoints() {
      return victoryPoints;
   }

   /**
    * Returns the number of resources associated with a card
    *
    * @return the number of resources
    */
   @Override
   public int getNumResources() {
      return numResources;
   }

   /**
    * Gets the list of all resources associated with the card.
    *
    * @return the list of resources
    */
   public List<ResourceType> getResourceList() {
      return resourceList;
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
   public SymbolType getSymbol() {
      return symbol;
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
      int i = 0;

      cornerContent.add(getCornerContentString(getTLCorner(), i));
      if (getTLCorner() == CornerLabel.WithResource) {
         i++;
      } else i = 0;

      cornerContent.add(getCornerContentString(getTRCorner(), i));
      if (getTRCorner() == CornerLabel.WithResource) {
         i++;
      } else i = 0;

      cornerContent.add(getCornerContentString(getBRCorner(), i));
      if (getBRCorner() == CornerLabel.WithResource) {
         i++;
      } else i = 0;

      cornerContent.add(getCornerContentString(getBLCorner(), i));

      return cornerContent;
   }

   /**
    * Retrieves the string representation of the content of a corner.
    *
    * @param cornerLabel The label indicating the type of content in the corner.
    * @param i           An index used to retrieve the actual resource in case of 'WithResource' label.
    * @return The string representing the corner content.
    * @throws IllegalArgumentException If an invalid corner label is provided.
    */
   public String getCornerContentString(CornerLabel cornerLabel, int i) {
       return switch (cornerLabel) {
           case Empty -> "Empty";
           case WithResource ->
                   resourceList.get(i).toString();
           case WithSymbol ->
                   symbol.toString();
           case NoCorner -> "NoCorner";
           default -> throw new IllegalArgumentException();
       };
   }

   /**
    * Retrieves the list of central resources associated with a card
    *
    * @return a list of ResourceType representing the central resources.
    */
   public List<ResourceType> getCentralResources() {
      return null;
   }

   /**
    * Retrieves the number of central resources associated with a card
    *
    * @return the number of central resources.
    */
   public int getNumCentralResources() {
      return 0;
   }

   /**
    * Gets the list of resource types required for placement.
    *
    * @return the list of resource types for placement condition
    */
   public List<ResourceType> getPlacementCondition() {
      return null;
   }

   /**
    * Checks if there is a points condition.
    *
    * @return true if there is a points condition, false otherwise
    */
   public boolean isPointsCondition() {
      return false;
   }

   /**
    * Checks if there is a corner condition.
    *
    * @return true if there is a corner condition, false otherwise
    */
   public boolean isCornerCondition() {
      return false;
   }

   /**
    * Gets the symbol type required for the condition.
    *
    * @return the symbol type for the condition
    */
   public SymbolType getSymbolCondition() {
      return null;
   }

   /**
    * Constructs a ResourceCard with the specified attributes.
    *
    * @param cardID       the unique identifier for the card.
    * @param numCorners   the number of corners on the card.
    * @param isFront      the side of the card (true if front, false if back).
    * @param cardType     the type of the card.
    * @param TLCorner     the label for the top-left corner.
    * @param TRCorner     the label for the top-right corner.
    * @param BRCorner     the label for the bottom-right corner.
    * @param BLCorner     the label for the bottom-left corner.
    * @param mainResource the main resource associated with the card.
    * @param victoryPoints the number of victory points the card provides.
    * @param numResources the total number of resources on the card.
    * @param resourceList the list of resources on the card.
    * @param hasSymbol    indicates if the card has a symbol.
    * @param symbol       the symbol type of the card, if any.
    */
   public ResourceCard(int cardID, int numCorners, boolean isFront, CardType cardType, CornerLabel TLCorner, CornerLabel TRCorner, CornerLabel BRCorner, CornerLabel BLCorner, ResourceType mainResource, int victoryPoints, int numResources, List<ResourceType> resourceList, boolean hasSymbol, SymbolType symbol) {
      super(cardID, numCorners, isFront, cardType, TLCorner, TRCorner, BRCorner, BLCorner);
      this.mainResource = mainResource;
      this.victoryPoints = victoryPoints;
      this.numResources = numResources;
      this.resourceList = resourceList;
      this.hasSymbol = hasSymbol;
      this.symbol = symbol;
   }

   /**
    * Provides a string representation of the {@code InitialCard} for display purposes.
    * This includes details such as card type, face direction, points, corners, and conditions.
    *
    * @return A formatted string representing the card.
    */
   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();
      String cardTypeName = "ResourceCard";
      String FoB = isFront() ? "Front" : "Back";
      String victoryPoints = "Points: " + getVictoryPoints();

      List<String> corners = getCornerContent();
      String topLeft = padAndBorderEmoji(convertToEmoji(corners.get(0)));
      String topRight = padAndBorderEmoji(convertToEmoji(corners.get(1)));
      String bottomRight = padAndBorderEmoji(convertToEmoji(corners.get(2)));
      String bottomLeft = padAndBorderEmoji(convertToEmoji(corners.get(3)));

      int width = DefaultValue.printLenght;
      int height = DefaultValue.printHeight;
      String border = "+" + "-".repeat(width) + "+";

      int contentRows = 4;

      result.append(border).append("\n");

      int paddingVictoryPoints = Math.max(0, (width - victoryPoints.length() - 8) / 2);
      result.append("|")
              .append(topLeft)
              .append(" ".repeat(Math.max(0, paddingVictoryPoints)))
              .append(victoryPoints)
              .append(" ".repeat(Math.max(0, width - paddingVictoryPoints - victoryPoints.length() - 8)))
              .append(topRight)
              .append("|\n");

      String cardTypeLine = cardTypeName;
      int paddingType = Math.max(0, (width - cardTypeLine.length()) / 2);
      result.append("|")
              .append(" ".repeat(Math.max(0, paddingType)))
              .append(cardTypeLine)
              .append(" ".repeat(Math.max(0, width - paddingType - cardTypeLine.length())))
              .append("|\n");

      String faceLine = "(" + FoB + ")";
      int paddingFace = Math.max(0, (width - faceLine.length()) / 2);
      result.append("|")
              .append(" ".repeat(Math.max(0, paddingFace)))
              .append(faceLine)
              .append(" ".repeat(Math.max(0, width - paddingFace - faceLine.length())))
              .append("|\n");


      String mainResource = convertToEmoji(getMainResource().toString());
      int paddingMainResource = Math.max(0, (width - calculateEmojiWidth(mainResource)) / 2);
      result.append("|")
              .append(" ".repeat(Math.max(0, paddingMainResource)))
              .append(mainResource)
              .append(" ".repeat(Math.max(0, width - paddingMainResource - calculateEmojiWidth(mainResource))))
              .append("|\n");

      int remainingHeight = height - (contentRows + 2);
      for (int i = 0; i < remainingHeight; i++) {
         result.append("|").append(" ".repeat(Math.max(0, width))).append("|\n");
      }

      result.append("|")
              .append(bottomLeft)
              .append(" ".repeat(Math.max(0, width - 8)))
              .append(bottomRight)
              .append("|\n");

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



}
