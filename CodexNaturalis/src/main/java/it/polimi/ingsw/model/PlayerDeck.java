package it.polimi.ingsw.model;


import it.polimi.ingsw.model.cards.PlayableCard;

import java.util.ArrayList;

public class PlayerDeck {
     public  ArrayList<PlayableCard> miniDeck;

     public int actualnumCards;

     public PlayerDeck(){
         miniDeck= new ArrayList<>(6);
         actualnumCards=0;
     }

     public ArrayList<PlayableCard> getMiniDeck(){
          return this.miniDeck;
     }
     public int getNumCards(){
          return this.actualnumCards;
     }

     /**
      * Adds a new card to the mini deck if there is space available.
      *@author Sofia Maule
      * @param newCard The new card to be added to the mini deck.
      */
     public void addCard(PlayableCard[] newCard) {
          if (actualnumCards < 3) {
               miniDeck.add(newCard);
               actualnumCards++;
               //notifyCardAdded(); // Notifica il successo
          } else {
              // notifyCardAdditionFailed(); // Notifica il fallimento
          }
     }

     /**
      * Removes a card from the mini deck if it exists.
      * @author Sofia Maule
      * @param cardToRemove The card to be removed from the mini deck.
      */
     public void removeCard(PlayableCard[] cardToRemove) {
          if (miniDeck.remove(cardToRemove)) {
               actualnumCards--;
               // Notifica il successo ->  notifyCardRemoved();
          } else {
               // Notifica il fallimento
               //notifyCardRemovalFailed();
          }
     }
}
