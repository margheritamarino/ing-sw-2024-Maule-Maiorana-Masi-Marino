package it.polimi.ingsw.model.player;


import it.polimi.ingsw.model.cards.PlayableCard;

import java.util.ArrayList;

public class PlayerDeck {
     public  ArrayList<PlayableCard> miniDeck;

     public int actualNumCards;

     public PlayerDeck(){
         miniDeck= new ArrayList<>(6);
         actualNumCards=0;
     }

     public ArrayList<PlayableCard> getMiniDeck(){
          return this.miniDeck;
     }
     public int getNumCards(){
          return this.actualNumCards;
     }

     /**
      * Adds a new card to the mini deck if there is space available.
      * remember: a card is identified by an array of PlayableCards
      * (first the front then the back) and they are both added to the playerDeck one after the other
      *
      * @param newCard The new card to be added to the mini deck.
      */
     public void addCard(PlayableCard[] newCard) {
          if (actualNumCards < 6) { // Se ci sono meno di 3 carte nel mini deck
               miniDeck.add(newCard[0]); // Aggiungi la prima PlayableCard (fronte) al mini deck
               miniDeck.add(newCard[1]); //aggiunge il back in modo consecutivo
               actualNumCards+=2; // Incrementa il numero effettivo di carte (una carta aggiunta)
          }
     }

     /**
      * Removes a card from the mini deck at the specified position.
      * remember: both the chosen card and its front/back must be removed
      * if the card is a front -> its back is in the next position
      *   (and opposite)
      * @param pos The position of the card to be removed.
      * @throws IndexOutOfBoundsException If the position is out of range (pos < 0 || pos >= miniDeck.size()).
      */
     public void removeCard(int pos) throws IndexOutOfBoundsException {
          // Verifies if the position is valid
          if (pos < 0 || pos >= miniDeck.size()) {
               throw new IndexOutOfBoundsException("Position is out of range");
          }

          // If the card at the specified position is the front
          if (miniDeck.get(pos).isFront()) {
               // Ensures there is a card at the next position
               if (pos < miniDeck.size() - 1) {
                    miniDeck.remove(pos + 1); // Removes the back
               }
          } else { // If the card at the specified position is the back
               // Ensures there is a card at the previous position
               if (pos > 0) {
                    miniDeck.remove(pos - 1); // Removes the front
               }
          }

          miniDeck.remove(pos); // Removes the current card
          actualNumCards -= 2; // Decrements the effective number of cards (two cards removed)
     }

}
