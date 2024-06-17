package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.SymbolType;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * ResourceCard class
 */
public class ResourceCard extends PlayableCard implements Serializable {
   private ResourceType mainResource;
   private int victoryPoints;
   private int numResources;
   private List<ResourceType> resourceList;
   private boolean hasSymbol;
   private SymbolType symbol;

   @Override
   public ResourceType getMainResource() {
      return mainResource;
   }
   @Override
   public int getVictoryPoints() {
      return victoryPoints;
   }

   @Override
   public int getNumResources() {
      return numResources;
   }
   public List<ResourceType> getResourceList() {
      return resourceList;
   }

   public boolean hasSymbol() {
      return hasSymbol;
   }

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

      // Angolo in alto a SX
      cornerContent.add(getCornerContentString(getTLCorner(), i));

      if (getTLCorner() == CornerLabel.WithResource) {
         i++;
      } else i = 0;

      // Angolo in alto a DX
      cornerContent.add(getCornerContentString(getTRCorner(), i));

      if (getTRCorner() == CornerLabel.WithResource) {
         i++;
      } else i = 0;

      // Angolo in basso a DX
      cornerContent.add(getCornerContentString(getBRCorner(), i));

      if (getBRCorner() == CornerLabel.WithResource) {
         i++;
      } else i = 0;

      // Angolo in basso a SX
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
   // Metodo per ottenere la stringa rappresentante il contenuto di un angolo
   public String getCornerContentString(CornerLabel cornerLabel, int i) {
       return switch (cornerLabel) {
           case Empty -> "Empty";
           case WithResource ->
               // Se l'angolo contiene una risorsa, restituisci la risorsa effettiva
                   resourceList.get(i).toString();
           case WithSymbol ->
               // Se l'angolo contiene un simbolo, restituisci il simbolo effettivo
                   symbol.toString();
           case NoCorner -> "NoCorner";
           default -> throw new IllegalArgumentException();
       };
   }

   public List<ResourceType> getCentralResources() {
      return null;
   }
   public int getNumCentralResources() {
      return 0;
   }
   public List<ResourceType> getPlacementCondition() {
      return null;
   }
   public boolean isPointsCondition() {
      return false;
   }
   public boolean isCornerCondition() {
      return false;
   }
   public SymbolType getSymbolCondition() {
      return null;
   }



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
      Ansi.Color bgColor;
      Ansi.Color textColor = Ansi.Color.WHITE;
      String FoB;
      if (isFront()) {
         FoB = "Front";
      } else {
         FoB = "Back";
      }

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

      String cardTypeName = "Resource";
      int points = victoryPoints;
      List<String> corners = getCornerContent();
      List<String> emojiCorners = new ArrayList<>();
      for (String corner : corners) {
         emojiCorners.add(convertToEmoji(corner));
      }

      // Costruzione delle righe del contenuto
      List<String> contentLines = new ArrayList<>();
      contentLines.add("CardType: " + cardTypeName);
      contentLines.add("Face: " + FoB);
      contentLines.add("Points: " + points);
      contentLines.add("Corners: " + String.join(" ", emojiCorners));

      // Trova la lunghezza massima delle linee di contenuto
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


      return result.toString();
   }
//TODO: Metodo come le Initial card
   //TODO: modificare e fare i victoryPoints in alto nella riga delle emoji angoli TL e TR
   //TODO: dopo aver fatto le carte modificare come viene mostrata la board nella TUI perchè dà errore -> vedi show_InitialCard
   //TODO per le GOLD: se si riesce a mettere i punti in alto aggiungere anche la condizione di punti se c'è
   //TODO GOLD:         invece in basso aggiungere la condizione di piazzamento
   //TODO alla fine: modificare i default Values printHEight e printLenght in DefaultValue per diminuire la grandezza
   //
   /*
   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();
      String cardTypeName = "Resource";
      String FoB = isFront() ? "Front" : "Back";
      String victoryPoints= String.valueOf(getVictoryPoints());


      // Lettura delle risorse dagli angoli
      List<String> corners = getCornerContent(); // Ordine: TL, TR, BR, BL
      String topLeft = convertToEmoji(corners.get(0));
      String topRight = convertToEmoji(corners.get(1));
      String bottomRight = convertToEmoji(corners.get(2));
      String bottomLeft = convertToEmoji(corners.get(3));



      // Costruzione della carta
      int width = DefaultValue.printLenght; // Larghezza della carta
      int height = DefaultValue.printHeight; // Altezza della carta
      String border = "+" + "-".repeat(width) + "+";

      // Calcolo delle righe di contenuto effettive
      int contentRows = 4; // Numero effettivo di righe per il contenuto (tipo carta, lato, risorse centrali, angoli)

      // Bordo superiore
      result.append(border).append("\n");

      // Riga superiore con angoli
      result.append("|").append(padEmoji(topLeft, 2))
              .append(" ".repeat(width - calculateEmojiWidth(topLeft) - calculateEmojiWidth(topRight) - 2))
              .append(padEmoji(topRight, 2)).append("|\n");

      // Riga con il tipo di carta centrato
      String cardTypeLine = "CardType: " + cardTypeName;
      int paddingType = (width - cardTypeLine.length()) / 2;
      result.append("|").append(" ".repeat(paddingType)).append(cardTypeLine)
              .append(" ".repeat(width - paddingType - cardTypeLine.length())).append("|\n");

      // Riga con il lato della carta centrato
      String faceLine = "Face: " + FoB;
      int paddingFace = (width - faceLine.length()) / 2;
      result.append("|").append(" ".repeat(paddingFace)).append(faceLine)
              .append(" ".repeat(width - paddingFace - faceLine.length())).append("|\n");
      // Riga con il lato della carta centrato
      String pointsLine = "Points: " + victoryPoints;
      int paddingPoints = (width - pointsLine.length()) / 2;
      result.append("|").append(" ".repeat(paddingPoints)).append(pointsLine)
              .append(" ".repeat(width - paddingPoints - pointsLine.length())).append("|\n");

      // Riga con il contenuto centrale (centrato)
      String mainResouce= convertToEmoji( getMainResource().toString());

      int paddingMainResource = (width - calculateEmojiWidth(mainResouce)) / 2;
      result.append("|").append(" ".repeat(paddingMainResource)).append(mainResouce)
              .append(" ".repeat(width - paddingMainResource - calculateEmojiWidth(mainResouce))).append("|\n");

      // Aggiungi righe vuote fino a raggiungere l'altezza desiderata della carta
      int remainingHeight = height - (contentRows-1 + 2); // 2 righe per i bordi superiori e inferiori
      for (int i = 0; i < remainingHeight; i++) {
         result.append("|").append(" ".repeat(width)).append("|\n");
      }
      // Riga inferiore con angoli
      result.append("|").append(padEmoji(bottomLeft, 2))
              .append(" ".repeat(width - calculateEmojiWidth(bottomLeft) - calculateEmojiWidth(bottomRight) - 2))
              .append(padEmoji(bottomRight, 2)).append("|\n");

      // Bordo inferiore
      result.append(border).append("\n");

      return result.toString();
   }

   private int calculateEmojiWidth(String input) {
      int width = 0;
      for (int i = 0; i < input.length(); ) {
         int codePoint = input.codePointAt(i);
         if (Character.charCount(codePoint) > 1 || input.codePointAt(i) > 0xFFFF) {
            // Considera le emoji e caratteri speciali come doppia larghezza
            width += 2;
         } else {
            // I caratteri normali contano come una larghezza
            width += 1;
         }
         i += Character.charCount(codePoint);
      }
      return width;
   }


   private String padEmoji(String content, int totalLength) {
      int emojiWidth = calculateEmojiWidth(content);
      int padding = totalLength - emojiWidth;
      return content + " ".repeat(padding);
   }
*/
}
