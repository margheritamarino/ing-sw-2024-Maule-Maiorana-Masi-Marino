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
 * Initial Card class
 */
public class InitialCard extends PlayableCard implements Serializable {
    List<ResourceType> centralResources;
    int numCentralResource;
    int numResources;
    List<ResourceType> resourceList;

    public List<ResourceType> getCentralResources() {
        return centralResources;
    }

    public int getNumCentralResources() {
        return numCentralResource;
    }

    @Override
    public int getNumResources() {
        return numResources;
    }
    public SymbolType getSymbol() {
        return null;
    }
    @Override
    public int getVictoryPoints() {
        return 0;
    }

    @Override
    public List<ResourceType> getResourceList() {
        return resourceList;
    }
    @Override
    public boolean hasSymbol() {
        return false;
    }
    @Override
    public ResourceType getMainResource() {
        return null;
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
        }

        // Angolo in alto a DX
        cornerContent.add(getCornerContentString(getTRCorner(), i));

        if (getTRCorner() == CornerLabel.WithResource) {
            i++;
        }

        // Angolo in basso a DX
        cornerContent.add(getCornerContentString(getBRCorner(), i));

        if (getBRCorner() == CornerLabel.WithResource) {
            i++;
        }
        // Angolo in basso a SX
        cornerContent.add(getCornerContentString(getBLCorner(), i));

        return cornerContent;
    }



    /**
     * Retrieves the string representation of the content of a corner.
     * @param cornerLabel The label indicating the type of content in the corner.
     * @param i           An index used to retrieve the actual resource in case of 'WithResource' label.
     * @return The string representing the corner content.
     * @throws IllegalArgumentException If an invalid corner label is provided.
     */
    // Metodo per ottenere la stringa rappresentante il contenuto di un angolo
    public String getCornerContentString(CornerLabel cornerLabel, int i) {
        switch (cornerLabel) {
            case Empty:
                return "Empty";
            case WithResource:
                // Se l'angolo contiene una risorsa, restituisci la risorsa effettiva
                return resourceList.get(i).toString();
            case NoCorner:
                return "NoCorner";
            default:
                throw new IllegalArgumentException();
        }
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



    public InitialCard(int cardID, int numCorners, boolean isFront, CardType cardType, CornerLabel TLCorner, CornerLabel TRCorner, CornerLabel BRCorner, CornerLabel BLCorner, List<ResourceType> centralResources, int numCentralResource, int numResources, List<ResourceType> resourceList) {
        super(cardID, numCorners, isFront, cardType, TLCorner, TRCorner, BRCorner, BLCorner);
        this.centralResources = centralResources;
        this.numCentralResource = numCentralResource;
        this.numResources = numResources;
        this.resourceList = resourceList;
    }


     /**
      * Provides a string representation of the {@code InitialCard} for display purposes.
      * This includes details such as card type, face direction, points, corners, and conditions.
      *
      * @return A formatted string representing the card.
      */
     /*@Override
     public String toString() {
         StringBuilder result = new StringBuilder();
         Ansi.Color bgColor = Ansi.Color.YELLOW;
         Ansi.Color textColor = Ansi.Color.WHITE;
         String cardTypeName = "Initial";
         String FoB = isFront() ? "Front" : "Back";

         // Lettura delle risorse dagli angoli
         List<String> corners = getCornerContent(); // Ordine: TL, TR, BR, BL
         String topLeft = convertToEmoji(corners.get(0));
         String topRight = convertToEmoji(corners.get(1));
         String bottomRight = convertToEmoji(corners.get(2));
         String bottomLeft = convertToEmoji(corners.get(3));

         // Lettura delle risorse centrali
         StringBuilder centralResourcesBuilder = new StringBuilder();
         List<String> emojiCentral = new ArrayList<>();
         List<ResourceType> centralR = getCentralResources();
         for (ResourceType central : centralR) {
             emojiCentral.add(convertToEmoji(central.toString()));
             centralResourcesBuilder.append(convertToEmoji(central.toString())).append(" ");
         }
         String centralResources = centralResourcesBuilder.toString().trim();

         // Costruzione della carta
         int width = DefaultValue.printLenght; // Larghezza della carta
         int height = DefaultValue.printHeight; // Altezza della carta
         String border = "+" + "-".repeat(width) + "+\n";

         // Bordo superiore
         result.append(border);

         // Riga superiore con angoli
         result.append("|").append(topLeft)
                 .append(" ".repeat(width - 2)).append(topRight).append("|\n");

         // Riga vuota tra i bordi e il contenuto
         result.append("|").append(" ".repeat(width)).append("|\n");

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

         // Riga con il contenuto centrale (centrato)
         int paddingCentral = (width - centralResources.length()) / 2;
         result.append("|").append(" ".repeat(paddingCentral)).append(centralResources)
                 .append(" ".repeat(width - paddingCentral - centralResources.length())).append("|\n");

         // Riga vuota tra il contenuto e il bordo inferiore
         result.append("|").append(" ".repeat(width)).append("|\n");

         // Riga inferiore con angoli
         result.append("|").append(bottomLeft)
                 .append(" ".repeat(width - 2)).append(bottomRight).append("|\n");

         // Bordo inferiore
         result.append(border);

         String finalResult = ansi().fg(textColor).bg(bgColor).a(result.toString()).reset().toString();

         return finalResult;
     }*/
     //TODO NE SERVONO 4 PER LE INITIAL
     @Override
     public String toString() {
         StringBuilder result = new StringBuilder();
         Ansi.Color bgColor = Ansi.Color.YELLOW;
         Ansi.Color textColor = Ansi.Color.WHITE;
         String cardTypeName = "InitialCard";
         String FoB = isFront() ? "Front" : "Back";

         // Lettura delle risorse dagli angoli
         List<String> corners = getCornerContent(); // Ordine: TL, TR, BR, BL
         String topLeft = padAndBorderEmoji(convertToEmoji(corners.get(0)));
         String topRight = padAndBorderEmoji(convertToEmoji(corners.get(1)));
         String bottomRight = padAndBorderEmoji(convertToEmoji(corners.get(2)));
         String bottomLeft = padAndBorderEmoji(convertToEmoji(corners.get(3)));

         // Lettura delle risorse centrali
         StringBuilder centralResourcesBuilder = new StringBuilder();
         List<ResourceType> centralR = getCentralResources();
         for (ResourceType central : centralR) {
             centralResourcesBuilder.append(convertToEmoji(central.toString())).append(" ");
         }
         String centralResources = centralResourcesBuilder.toString().trim();

         // Costruzione della carta
         int width = DefaultValue.printLenght; // Larghezza della carta
         int height = DefaultValue.printHeight; // Altezza della carta
         String border = "+" + "-".repeat(width) + "+";

         // Calcolo delle righe di contenuto effettive
         int contentRows = 4; // Numero effettivo di righe per il contenuto (tipo carta, lato, risorse centrali, angoli)

         // Bordo superiore
         result.append(border).append("\n");

         // Riga superiore con angoli
         int paddingTopLeft = Math.max(0, (width - calculateEmojiWidth(topLeft) - calculateEmojiWidth(topRight)) / 2);
         result.append("|")
                 .append(topLeft)
                 .append(" ".repeat(paddingTopLeft))
                 .append(" ".repeat(Math.max(0, width - paddingTopLeft - calculateEmojiWidth(topLeft) - calculateEmojiWidth(topRight))))
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
         int paddingCentral = Math.max(0, (width - calculateEmojiWidth(centralResources)) / 2);
         result.append("|")
                 .append(" ".repeat(paddingCentral))
                 .append(centralResources)
                 .append(" ".repeat(Math.max(0, width - paddingCentral - calculateEmojiWidth(centralResources))))
                 .append("|\n");

         // Aggiungi righe vuote fino a raggiungere l'altezza desiderata della carta
         int remainingHeight = height - (contentRows + 2); // 2 righe per i bordi superiori e inferiori
         for (int i = 0; i < remainingHeight; i++) {
             result.append("|").append(" ".repeat(width)).append("|\n");
         }

         // Riga inferiore con angoli
         int paddingBottomLeft = Math.max(0, (width - calculateEmojiWidth(bottomLeft) - calculateEmojiWidth(bottomRight)) / 2);
         result.append("|")
                 .append(bottomLeft)
                 .append(" ".repeat(paddingBottomLeft))
                 .append(" ".repeat(Math.max(0, width - paddingBottomLeft - calculateEmojiWidth(bottomLeft) - calculateEmojiWidth(bottomRight))))
                 .append(bottomRight)
                 .append("|\n");

         // Bordo inferiore
         result.append(border).append("\n");

         // Conversione del risultato in stringa ANSI colorata
         String finalResult = ansi().fg(textColor).bg(bgColor).a(result.toString()).reset().toString();
         return finalResult;
     }

    // Metodo per calcolare la larghezza delle emoji
    private int calculateEmojiWidth(String input) {
        int width = 0;
        for (int i = 0; i < input.length(); ) {
            int codePoint = input.codePointAt(i);
            if (Character.charCount(codePoint) > 1 || codePoint > 0xFFFF) {
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

    // Metodo per contornare le emoji o i simboli
    private String padAndBorderEmoji(String content) {
        int emojiWidth = calculateEmojiWidth(content);
        int padding = Math.max(0, 2 - emojiWidth);
        return "[" + content + " ".repeat(padding) + "]";
    }


   /* @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Ansi.Color bgColor = Ansi.Color.YELLOW;
        Ansi.Color textColor = Ansi.Color.WHITE;
        String cardTypeName = "Initial";
        String FoB = isFront() ? "Front" : "Back";

        List<String> corners = getCornerContent();
        List<String> emojiCorners = new ArrayList<>();
        for (String corner : corners) {
            emojiCorners.add(convertToEmoji(corner));
        }

        List<ResourceType> centralR = getCentralResources();
        List<String> emojiCentral = new ArrayList<>();
        for (ResourceType central : centralR) {
            emojiCentral.add(convertToEmoji(central.toString()));
        }

        // Costruzione delle righe del contenuto
        List<String> contentLines = new ArrayList<>();
        contentLines.add("CardType: " + cardTypeName);
        contentLines.add("Face: " + FoB);
        contentLines.add("Corners: " + String.join(" ", emojiCorners));
        contentLines.add("Central: " + String.join(" ", emojiCentral));

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





}



