package it.polimi.ingsw;

import java.util.ArrayList;

public class PlayerDeck {
     public  ArrayList<Card> miniDeck;
     public InitialCard initCard;
     public int actualnumCards;

     public PlayerDeck(){
         miniDeck= new ArrayList<>(3);
         initCard = new InitialCard(initCard.cardID) ; //?? dove prendo il CardID
     }

     public void addCard(Card newCard){
         //controlla se array pieno -> actualNumCards =3
         // altrimenti aggiunge la carta e incrementa numero
              miniDeck.add(newCard);
     }
     public void removeCard(Card newCard){
         //quando la carta viene piazzata deve essere rimossaa dal playerDeck
         //decremento actualNumCards
     }
     public void showPlayerDeck(){
         //fa vedere le carte del deck
         // nelle carte ci devono essere i metodi che mostrano (fronte e retro)
     }

}
