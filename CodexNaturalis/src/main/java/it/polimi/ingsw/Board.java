package it.polimi.ingsw;

import java.util.ArrayList;
public class Board {

	/**
	 * @author Irene Pia Masi
	 * Board model
	 * This class represents the game board that contains decks of cards and cards placed on the board.
	 */
	private ArrayList<GoldCard> goldCards; //dimension 2
	private ArrayList<ResourceCard> resourceCards;
	private ArrayList<ObjectiveCard> objectiveCards;
	private Deck goldCardsDeck;
	private Deck resourcesCardsDeck;
	private Deck objectiveCardsDeck;

	private final int Max_size = 2;

	/**
	 * constructor
	 */

	public Board(){
		this.goldCards = new ArrayList<>();
		this.resourceCards = new ArrayList<>();
		this.objectiveCards = new ArrayList<>();
		this.goldCardsDeck = new Deck();
		this.resourcesCardsDeck = new Deck();
	}

	/**
	 * Adds a new Gold card to the board.
	 */
//metodi set per aggiungere nuove carte alla Board (2 gold, 2 resource, 2 objective)
	public void setGoldCards() {
		if (goldCards.size() < Max_size) { //se non ho 2 carte piazzate allora pesco una carta dal deck corrispondente usando draw
			GoldCard goldCard = goldCardsDeck.drawCard();
			if (goldCard != null) { //se è stata estratta una carta allora viene poggiata sulla Board (array di due carte)
				this.goldCards.add(goldCard);
				showBoardState(); //visualizzo lo stato aggiornato della Board
			} else {
				System.out.println("No more gold cards in the deck.");
			}
		} else {
			System.out.println("Maximum number of gold cards reached.");
		}
	}

	/**
	 * Adds a new Resource card to the board.
	 */
	public void setResourceCards() {
		if (Max_size > resourceCards.size()) {
			ResourceCard resourceCard = resourcesCardsDeck.drawCard();
			if (resourceCard != null) {
				this.resourceCards.add(resourceCard);
				showBoardState();
			} else {
				System.out.println("No more resource cards in the deck.");
			}
		} else {
			System.out.println("Maximum number of resource cards reached.");
		}
	}

	/**
	 * Sets the Objective cards for the board.
	 * @param objectiveCards The list of Objective cards to set.
	 */
	public void setObjectiveCards(ArrayList<ObjectiveCard> objectiveCards) {
		if (objectiveCards.size() == Max_size) {
			this.objectiveCards = objectiveCards;
			showBoardState();
		} else {
			System.out.println("Exactly " + Max_size + " objective cards must be provided.");
		}
	}

	/**
	 * Get methods that return the list of cards located on the board.
	 * @return the list of cards on the board.
	 */
//metodi get per restituire l'elenco delle carte
	public ArrayList<GoldCard> getGoldCards() {
		return this.goldCards;
	}
	public ArrayList<ResourceCard> getResourceCards() {
		return this.resourceCards;
	}
	public ArrayList<ObjectiveCard> getObjectiveCards() {
		return this.objectiveCards;
	}

	/**
	 * Displays the current state of the board, including all placed cards.
	 */
	public void showBoardState() {
		System.out.println("Gold Cards:");
		for (GoldCard card : goldCards) {
			System.out.println(card);
		}

		System.out.println("Resource Cards:");
		for (ResourceCard card : resourceCards) {
			System.out.println(card);
		}

		System.out.println("Objective Cards:");
		for (ObjectiveCard card : objectiveCards) {
			System.out.println(card);
		}
	}

}