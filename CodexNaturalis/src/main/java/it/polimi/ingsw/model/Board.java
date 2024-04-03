package it.polimi.ingsw.model;

import java.util.ArrayList;
public class Board {

	/**
	 * @author Irene Pia Masi
	 * Board model class
	 * This class represents the game board that contains decks of cards and cards placed on the board.
	 */
	private ArrayList<GoldCard> goldCards; //dimension 2
	private ArrayList<ResourceCard> resourceCards;
	private ArrayList<ObjectiveCard> objectiveCards;
	private Deck goldCardsDeck;
	private Deck resourcesCardsDeck;
	private Deck objectiveCardsDeck;

	private final int MAX_SIZE = 2;

	/**
	 * Constructor
	 */

	public Board(){
		this.goldCards = new ArrayList<>();
		this.resourceCards = new ArrayList<>();
		this.objectiveCards = new ArrayList<>();
		this.goldCardsDeck = new Deck(CardType.GoldCard);
		this.resourcesCardsDeck = new Deck(CardType.ResourceCard);
        this.objectiveCardsDeck = new Deck();

        initializeBoard();
	}

    /**
     * Initializes the board by placing cards on it.
     */
    public void initializeBoard(){
        //posiziono due carte oro e due carte risorsa sul tavolo
        for (int i = 0; i < MAX_SIZE; i++) {
            PlayableCard[] goldCards = goldCardsDeck.returnCard();
            PlayableCard[] resourceCards = resourcesCardsDeck.returnCard();
            this.goldCards.add((GoldCard) goldCards[0]);
            this.resourceCards.add((ResourceCard) resourceCards[0]);
        }
        //posiziono le carte obbiettivo comuni
        for (int i = 0; i < MAX_SIZE; i++) {
            PlayableCard[] objectiveCards = objectiveCardsDeck.returnCard();
            this.objectiveCards.add((ObjectiveCard) objectiveCards[0]);
        }
    }

    /**
     * Allows a player to take a card from the board.
     * @param player The player who is taking the card.
     * @param cardType The type of card to take.
     * @param drawFromDeck True if the card should be drawn from the deck, false if it should be taken from the board.
     */
    public void takeCardFromBoard(Player player, CardType cardType, boolean drawFromDeck, PlayerDeck playerdeck) {
        //booleano che mi dice se il giocatore ha deciso di pescare una carta dal mazzo o da uno degli array
        if (drawFromDeck) {
            Deck deck = null;
            if (cardType == CardType.GoldCard) {
                deck = goldCardsDeck;
            } else if (cardType == CardType.ResourceCard) {
                deck = resourcesCardsDeck;
            }
            //controllo che il mazzo non è vuoto e pesco una carta da aggiungere al player deck
            if (deck != null && !deck.checkEndDeck()) {
                PlayableCard[] newCards = deck.returnCard();
                if (newCards != null) {
                    playerdeck.addCard(newCards[0]);
                }
            }
        } else {
            ArrayList<? extends PlayableCard> cardsOnBoard = null;
            if (cardType == CardType.GoldCard) {
                cardsOnBoard = goldCards;
            } else if (cardType == CardType.ResourceCard) {
                cardsOnBoard = resourceCards;
            }
            //se l'array non è vuoto si rimuove la prima carta dall'array e si aggiunge al player deck
            if (!cardsOnBoard.isEmpty()) {
                PlayableCard card = cardsOnBoard.remove(0);
                playerdeck.addCard(card);
            }
        }
        showBoardState();
    }

    /**
     * Displays the current state of the board.
     */
    public void showBoardState() {
        // Controllo e aggiornamento delle carte oro
        while (goldCards.size() < 2 && !goldCardsDeck.checkEndDeck()) {
            PlayableCard[] newCards = goldCardsDeck.returnCard();
            if (newCards != null) {
                goldCards.add((GoldCard) newCards[0]);
            }
        }

        // Controllo e aggiornamento delle carte risorse
        while (resourceCards.size() < 2 && !resourcesCardsDeck.checkEndDeck()) {
            PlayableCard[] newCards = resourcesCardsDeck.returnCard();
            if (newCards != null) {
                resourceCards.add((ResourceCard) newCards[0]);
            }
        }

        System.out.println("Stato attuale della board:");
        System.out.println("Carte oro:");
        for (GoldCard goldCard : goldCards) {
            System.out.println(goldCard); // Supponendo che la classe GoldCard abbia un metodo toString() appropriato
        }
        System.out.println("Carte risorse:");
        for (ResourceCard resourceCard : resourceCards) {
            System.out.println(resourceCard); // Supponendo che la classe ResourceCard abbia un metodo toString() appropriato
        }
    }

}