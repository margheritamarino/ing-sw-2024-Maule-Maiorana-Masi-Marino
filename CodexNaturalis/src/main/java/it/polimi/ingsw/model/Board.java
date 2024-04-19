package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import java.util.ArrayList;

import java.io.FileNotFoundException;

/**
 * Board model class
 * This class represents the game board that contains decks of cards and cards placed on the board.
 * @author Irene Pia Masi
 */
public class Board {
    private final ArrayList<PlayableCard[]> goldCards;
    private final ArrayList<PlayableCard[]> resourceCards;
    private final ObjectiveCard[] objectiveCards; //commonGoals
    private final Deck goldCardsDeck;
    private final Deck resourcesCardsDeck;
    private final ObjectiveDeck objectiveCardsDeck;

    /**
     * Constructor
     */
    public Board() throws FileNotFoundException, FileReadException {
        this.goldCards = new ArrayList<>();
        this.resourceCards = new ArrayList<>();
        this.objectiveCards = new ObjectiveCard[2];
        this.goldCardsDeck = new Deck(CardType.GoldCard);
        this.resourcesCardsDeck = new Deck(CardType.ResourceCard);
        this.objectiveCardsDeck = new ObjectiveDeck();

        initializeBoard();
    }

    /**
     * Initializes the board by placing cards on it.
     * Sets the common Goals
     */
    public void initializeBoard() {
        //posiziono due carte oro e due carte risorsa sul tavolo
        //perchè la board ha 2 coppie di carte per ciascuna tipologia di carta
        int MAX_SIZE = 2;
        for (int i = 0; i < MAX_SIZE; i++) {
            PlayableCard[] goldCards = goldCardsDeck.returnCard();
            PlayableCard[] resourceCards = resourcesCardsDeck.returnCard();
            this.goldCards.add(goldCards);
            this.resourceCards.add(resourceCards);
        }
        //posiziono le carte obbiettivo comun
        for (int i = 0; i < MAX_SIZE; i++) {
            ObjectiveCard objectiveCard = objectiveCardsDeck.returnCard();
            this.objectiveCards[i] =objectiveCard;
        }
    }

    /*
    takes an ObjectiveCard from the Board
     */
    public ObjectiveCard takeObjectiveCard(){
        return objectiveCardsDeck.returnCard();
    }

    /**
     * Takes a card from the board.
     *
     * @param cardType     The type of card to take
     * @param drawFromDeck True if the card should be drawn from the deck, false if it should be taken from the board.
     * @param pos          The position of the card to take if drawing from the board.
     * @return The card taken from the board, or null.
     */
    public PlayableCard[] takeCardfromBoard(CardType cardType, boolean drawFromDeck, int pos) {
        if (drawFromDeck) { //booleano per vedere se il giocatore vuole pescare dai mazzi o dagli array
            // Verifico se il mazzo ha finito le carte prima di pescare
            if (cardType == CardType.GoldCard && goldCardsDeck.checkEndDeck()) {
                return null; // Restituisce null se il mazzo di carte è vuoto
            } else if (cardType == CardType.ResourceCard && resourcesCardsDeck.checkEndDeck()) {
                return null;
            }
            switch (cardType) {
                case GoldCard:
                    return goldCardsDeck.returnCard();
                case ResourceCard:
                    return resourcesCardsDeck.returnCard();
                default:
                    return null; //null in caso di tipo di carta non valido
            }
        } else {
            ArrayList<PlayableCard[]> cardsOnBoard ;
            switch (cardType) {
                case GoldCard:
                    cardsOnBoard = goldCards;
                    break;
                case ResourceCard:
                    cardsOnBoard = resourceCards;
                    break;
                default:
                    return null;
            }
            // Controllo che la posizione sia valida
            if (pos < 0 || pos >= cardsOnBoard.size()) {
                return null;
            }
            // Prelevo front e back card dalla board e creo l'array risultante
            PlayableCard[] selectedCards = new PlayableCard[2];
            int startIndex = pos * 2; //indice di partenza deve essere quello del front delle carte
            selectedCards[0] = cardsOnBoard.get(startIndex)[0];
            selectedCards[1] = cardsOnBoard.get(startIndex + 1)[0];

            // Rimuovi front e back card dall'array
            cardsOnBoard.remove(startIndex);
            cardsOnBoard.remove(startIndex); // Rimuovi anche il successivo perché abbiamo già scalato di 2

            // Aggiorno l'array e restituisco la carta selezionata
            updateArray(cardsOnBoard, cardType);

            return selectedCards;
        }
    }

    /**
     * Update array.
     * @param cards The array of cards to update.
     * @param cardType The type of card for which to update the array.
     */
    public void updateArray(ArrayList<PlayableCard[]> cards, CardType cardType) {
        Deck deck = null;
        switch (cardType) {
            case GoldCard:
                deck = goldCardsDeck;
                break;
            case ResourceCard:
                deck = resourcesCardsDeck;
                break;
            default:
                return;
        }
        // Prendo una nuova carta dal deck corrispondente tramite il returnCard
        PlayableCard[] newCard = deck.returnCard();
        // Aggiungo la nuova carta all'array
        cards.add(newCard);
    }
}


