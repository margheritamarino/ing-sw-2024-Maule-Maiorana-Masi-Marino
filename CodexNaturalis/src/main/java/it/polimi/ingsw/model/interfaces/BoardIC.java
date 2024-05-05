package it.polimi.ingsw.model.interfaces;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.ObjectiveDeck;
import it.polimi.ingsw.model.cards.*;

import java.util.ArrayList;

public interface BoardIC {

    ObjectiveDeck getObjectiveCardsDeck();
    ObjectiveCard[] getObjectiveCards();
    ArrayList<PlayableCard[]> getGoldCards();
    ArrayList<PlayableCard[]> getResourceCards();
    Deck getGoldCardsDeck();
    Deck getResourcesCardsDeck();

    ObjectiveCardIC takeObjectiveCardIC() throws DeckEmptyException; //CONTROLLA

    PlayableCardIC[] takeCardfromBoardIC(CardType cardType, boolean drawFromDeck, int pos); //CONTROLLA

    boolean verifyGoldCardsNumber();
    boolean verifyResourceCardsNumber();
    boolean verifyObjectiveCardsNumber();
    boolean verifyGoldDeckSize(int playersNumber);
    boolean verifyResourceDeckSize(int playersNumber);
    boolean verifyObjectiveDeckSize(int playersNumber);
}
