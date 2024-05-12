package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.*;
import org.fusesource.jansi.Ansi;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

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

    public int getCardID() {
        return cardID;
    }

    public boolean isFront() {
        return isFront;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getNumSymbols() {
        return numSymbols;
    }

    public List<SymbolType> getSymbols() {
        return symbols;
    }

    public int getNumResources() {
        return numResources;
    }

    public ResourceType getMainResource() {
        return mainResource;
    }

    public CornerType getDirection() {
        return direction;
    }

    public ResourceType getSecondResource() {
        return secondResource;
    }


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
     * @return a copy of the current objective Card
     */
    public ObjectiveCard copy(){
        // Creo una nuova istanza di ObjectiveCard con gli stessi valori dei campi
        ObjectiveCard copiedCard = new ObjectiveCard(
                this.cardID,
                this.isFront,
                this.goalType,
                this.victoryPoints,
                this.mainResource,
                this.direction,
                this.numResources,
                this.numSymbols,
                new ArrayList<>(this.symbols), // Creo una copia della lista di simboli
                this.secondResource
        );
        return copiedCard;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Ansi.Color bgColor = null;
        Ansi.Color textColor = Ansi.Color.WHITE;
        String condition = " ";
        List<String> emojiSymbol = new ArrayList<>();


        switch(goalType){
            case ResourceCondition:
                changeColor();
                condition = convertToEmoji(mainResource.toString());
                break;
            case SymbolCondition:
                bgColor = Ansi.Color.YELLOW;
                List<SymbolType> symbols = getSymbols();
                for (SymbolType s : symbols) {
                    emojiSymbol.add(convertToEmoji(s.toString()));
                }
                break;
            case DiagonalPlacement, LPlacement:
                changeColor();
                condition = direction.toString();
                break;
            default:
                bgColor = Ansi.Color.DEFAULT;
        }

        String cardTypeName = "Objective";
        int points = victoryPoints;

        // Costruzione del risultato con colori e nome della carta
        result.append(ansi().fg(textColor).bg(bgColor).a(" "));
        result.append("CardType: ");
        result.append(cardTypeName);
        result.append("\n");
        result.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT));
        result.append("Points: ");
        result.append(points);
        result.append("\n");
        result.append("Condition: ");
        result.append(condition);
        for (int i = 0; i < symbols.size(); i++) {
            result.append(emojiSymbol.get(i));
            // Aggiungi uno spazio solo se non è l'ultimo elemento della lista
            if (i < symbols.size() - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    public void changeColor(){
        Ansi.Color bgColor = switch (mainResource) {
            case Fungi -> Ansi.Color.RED;
            case Insect -> Ansi.Color.MAGENTA;
            case Plant -> Ansi.Color.GREEN;
            case Animal -> Ansi.Color.BLUE;
            default -> Ansi.Color.DEFAULT;
        };
        //cambia il colore della carta in base alla mainResource
    }

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
