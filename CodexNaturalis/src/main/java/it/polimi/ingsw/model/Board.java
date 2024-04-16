package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.cards.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Board model class
 * This class represents the game board that contains decks of cards and cards placed on the board.
 * @author Irene Pia Masi
 */
public class Board {
    private ArrayList<PlayableCard> goldCards; //dimension 2
    private ArrayList<PlayableCard> resourceCards;
    private ArrayList<ObjectiveCard> objectiveCards;
    private Deck goldCardsDeck;
    private Deck resourcesCardsDeck;
    private ObjectiveDeck objectiveCardsDeck;
    private int MAX_SIZE = 2;

    /**
     * Constructor
     */
    public Board() throws FileNotFoundException, FileReadException {
        this.goldCards = new ArrayList<>();
        this.resourceCards = new ArrayList<>();
        this.objectiveCards = new ArrayList<>();
        this.goldCardsDeck = new Deck(CardType.GoldCard);
        this.resourcesCardsDeck = new Deck(CardType.ResourceCard);
        this.objectiveCardsDeck = new ObjectiveDeck();

        initializeBoard();
    }

    /**
     * Initializes the board by placing cards on it.
     */
    public void initializeBoard() {
        //posiziono due carte oro e due carte risorsa sul tavolo
        for (int i = 0; i < MAX_SIZE; i++) {
            PlayableCard[] goldCards = goldCardsDeck.returnCard();
            PlayableCard[] resourceCards = resourcesCardsDeck.returnCard();
            this.goldCards.add((GoldCard) goldCards[0]);
            this.resourceCards.add((ResourceCard) resourceCards[0]);
        }
        //posiziono le carte obbiettivo comuni
        for (int i = 0; i < MAX_SIZE; i++) {
            ObjectiveCard[] objectiveCards = objectiveCardsDeck.returnCard();
            this.objectiveCards.add((ObjectiveCard) objectiveCards[0]);
        }
    }

    /**
     * Takes a card from the board.
     *
     * @param cardType     The type of card to take
     * @param drawFromDeck True if the card should be drawn from the deck, false if it should be taken from the board.
     * @param pos          The position of the card to take if drawing from the board.
     * @return The card taken from the board, or null.
     */
    public PlayableCard takeCardfromBoard(CardType cardType, boolean drawFromDeck, int pos) {
        if (drawFromDeck) { //booleano per vedere se il giocatore vuole pescare dai mazzi o dagli array
            // Verifico se il mazzo ha finito le carte prima di pescare
            if (cardType == CardType.GoldCard && goldCardsDeck.checkEndDeck()) {
                return null; // Restituisce null se il mazzo di carte è vuoto
            } else if (cardType == CardType.ResourceCard && resourcesCardsDeck.checkEndDeck()) {
                return null;
            }
            switch (cardType) {
                case GoldCard:
                    return goldCardsDeck.returnCard()[0];
                case ResourceCard:
                    return resourcesCardsDeck.returnCard()[0];
                default:
                    return null; //null in caso di tipo di carta non valido
            }
        } else {
            ArrayList<PlayableCard> cardsOnBoard = null;
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
            if (pos < 0 || pos > cardsOnBoard.size()) {
                return null;
            }
            // Prendo la carta dalla posizione specificata e la rimuovo dall'array
            PlayableCard selectedCard = cardsOnBoard.get(pos - 1);
            cardsOnBoard.remove(pos - 1);
            // Aggiorno l'array e restituisco la carta selezionata
            updateArray(cardsOnBoard, cardType);

            return selectedCard;
        }
    }

    /**
     * Update array.
     * @param cards The array of cards to update.
     * @param cardType The type of card for which to update the array.
     */
    public void updateArray(ArrayList<PlayableCard> cards, CardType cardType) {
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
        PlayableCard newCard = deck.returnCard()[0];
        // Aggiungo la nuova carta all'array
        cards.add(newCard);
    }
}


