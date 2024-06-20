package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.DeckFullException;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.cards.PlayableCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the deck of playable cards for a player.
 */
public class PlayerDeck implements Serializable{
     public  ArrayList<PlayableCard[]> miniDeck;
     public int actualNumCards;


    /**
     * Constructor to initialize the PlayerDeck with an empty mini deck.
     */
     public PlayerDeck(){
         miniDeck= new ArrayList<>(3);
         actualNumCards=0;
     }

    /**
     * Retrieves the mini deck of playable cards.
     *
     * @return The mini deck of playable cards.
     */
     public ArrayList<PlayableCard[]> getMiniDeck(){
          return this.miniDeck;
     }

    /**
     * Retrieves the current number of cards in the deck.
     *
     * @return The current number of cards in the deck.
     */
     public int getNumCards(){
          return this.actualNumCards;
     }

    /**
     * Adds a new card to the mini deck if there is space available.
     *
     * @param newCard The new card to be added to the mini deck.
     * @throws DeckFullException If the deck is already full and cannot accommodate more cards.
     */
     public void addCard(PlayableCard[] newCard) throws DeckFullException {
         if (getNumCards() >= 6) {
             throw new DeckFullException("PlayerDeck is full. Cannot add more cards.");
         }
          miniDeck.add(newCard);
          actualNumCards += 2;
     }

    /**
     * Removes a card from the mini deck at the specified position.
     *
     * @param pos The position of the card to be removed.
     * @throws IndexOutOfBoundsException If the position is out of range (pos < 0 || pos >= miniDeck.size()).
     */
     public void removeCard(int pos) throws IndexOutOfBoundsException {
        int indexToRemove;

        switch (pos) {
            case 0, 1:
                indexToRemove = 0;
                break;
            case 2, 3:
                indexToRemove = 1;
                break;
            case 4, 5:
                indexToRemove = 2;
                break;
            default:
                throw new IndexOutOfBoundsException("Position is out of range");
        }

        if (miniDeck.size() <= indexToRemove ) {
            throw new IndexOutOfBoundsException("Not enough cards to remove at this position");
        }
        miniDeck.remove(indexToRemove);
        actualNumCards -= 2;
     }

    /**
     * Generates a string representation of the PlayerDeck.
     *
     * @return String representation of the PlayerDeck.
     */
     public String toString() {
        StringBuilder result = new StringBuilder();

        ArrayList<ArrayList<StringBuilder>> rows = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            ArrayList<StringBuilder> rowBuilders = new ArrayList<>();
            for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
                rowBuilders.add(new StringBuilder());
            }
            rows.add(rowBuilders);
        }
        int pos=0;
        int frontPos=0;
        for (int i = 0; i <actualNumCards; i++) {
                PlayableCard card = miniDeck.get(pos)[frontPos];
                String[] lines = card.toString().split("\n");

                int row = i % 2;
                ArrayList<StringBuilder> rowBuilders = rows.get(row);


                rowBuilders.get(0).append(String.format("%-3d", i));
                for (int k = 0; k < lines.length; k++) {
                    if (k > 0) {
                        rowBuilders.get(k).append("   ");
                    }
                    rowBuilders.get(k).append(lines[k]).append(" ");
                }
                if(frontPos==0){
                    frontPos=1;
                }else{
                    frontPos=0;
                    pos++;
                }
        }

        for (ArrayList<StringBuilder> row : rows) {
            for (StringBuilder sb : row) {
                result.append(sb.toString().stripTrailing()).append("\n");
            }
        }

        return result.toString();
     }

}
