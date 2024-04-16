package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.cards.InitialCard;
import it.polimi.ingsw.model.cards.PlayableCard;

import java.util.ArrayList;

public class PlayerDeck {
     public  ArrayList<PlayableCard> miniDeck;
     //public InitialCard initCard;
     public int actualnumCards;

     public PlayerDeck(){
         miniDeck= new ArrayList<>(3); //solo carte
         actualnumCards=0;
     }

     public void addCard(PlayableCard newCard){
         //controlla se array pieno -> actualNumCards =3
         // altrimenti aggiunge la carta e incrementa numero
     }
     public void removeCard(Card newCard){
         //quando la carta viene piazzata deve essere rimossaa dal playerDeck
         //decremento actualNumCards
     }


}
