package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.DeckFullException;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.interfaces.PlayerDeckIC;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerDeck implements Serializable, PlayerDeckIC {
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
     public void addCard(PlayableCard[] newCard)  {

          // Aggiungi la prima PlayableCard (fronte) al mini deck
          miniDeck.add(newCard[0]);
          // Aggiungi la seconda PlayableCard (retro) al mini deck
          miniDeck.add(newCard[1]);
          // Incrementa il numero effettivo di carte
          actualNumCards += 2;
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
          // Verifies if there are enough cards to remove
          if (pos < 0 || pos > miniDeck.size()) {
               throw new IndexOutOfBoundsException("Position is out of range");
          }

          if (pos % 2 == 0) {
               // Se la posizione specificata è pari, si tratta di un fronte (posizione 0, 2, 4, ...)
               // Assicurati che la prossima posizione (pos + 1) sia valida
               if (pos + 1 < miniDeck.size()) {
                    miniDeck.remove(pos + 1); // Rimuovi il retro
                    miniDeck.remove(pos); // Removes the current card
               }
          } else {
               miniDeck.remove(pos); // Removes the current card
               // Se la posizione specificata è dispari, si tratta di un retro (posizione 1, 3, 5, ...)
              miniDeck.remove(pos - 1); // Rimuovi il fronte (posizione precedente)
          }

          actualNumCards -= 2; // Decrements the effective number of cards (two cards removed)
     }

     public String toString(){
          StringBuilder result = new StringBuilder();
          result.append("*******YOUR DECK*******\n");
          result.append("CARD 0: front\n");
          result.append(miniDeck.get(0).toString());
          result.append("CARD 0: back\n");
          result.append(miniDeck.get(1).toString());
          result.append("CARD 1: front\n");
          result.append(miniDeck.get(2).toString());
          result.append("CARD 1: back\n");
          result.append(miniDeck.get(3).toString());
          result.append("CARD 2: front\n");
          result.append(miniDeck.get(4).toString());
          result.append("CARD 2: back\n");
          result.append(miniDeck.get(5).toString());

          return result.toString();
     }

}
