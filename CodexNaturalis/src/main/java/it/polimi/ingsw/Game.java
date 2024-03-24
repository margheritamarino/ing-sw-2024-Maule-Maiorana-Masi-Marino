package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * @author Irene Pia Masi
 * Game model
 * The Game class manages the game state, including players, cards, decks, and game progression.
 * It implements the singleton pattern to ensure only one instance of the game exists.
 */
public class Game {

	private static Game instance;

	private final int chosenPlayersNumber;
	protected ArrayList<Player> players;
	protected Deck objectiveCardsDeck;
	protected Deck initialCardsDeck;
	protected Deck resourceCardsDeck;
	protected Deck goldCardsDeck;
	protected ScoreTrack scoretrack;
	private int currentPlayer;
	protected Board board;
	protected boolean isEnded = false;
	protected boolean gamestarted = false;

	/**
	 * Private Constructor
	 * @param playersNumber The number of players in the game.
	 * @throws IllegalArgumentException If the number of players is not between 1 and 4.
	 */
	private Game(int playersNumber){
		//check number of players
		if(playersNumber < 1 || playersNumber > 4){
			throw new IllegalArgumentException("The number of players must be between 1 and 4.");
		}
		this.chosenPlayersNumber = playersNumber;
		this.objectiveCardsDeck = new Deck(DeckType.Objective);
		this.initialCardsDeck = new Deck(DeckType.Initial);
		this.goldCardsDeck = new Deck(DeckType.Gold);
		this.resourceCardsDeck = new Deck(DeckType.Resource);
	//nota: ho fatto il costruttore del deck che ha come parametro
	// la stringa del tipo di carte -> assegno un numCards diverso(perchè sono diversi il base al tipo di carte)

		this.players = new ArrayList<>();
		this.scoretrack = new Scoretrack();
		this.currentPlayer = 0;
		this.board = new Board();

	}

	/**
	 * Singleton class
	 * @param playersNumber The number of players in the game
	 * @return the game instance.
	 */
	public static synchronized Game getInstance(int playersNumber) {
		if (instance == null) {
			instance = new Game(playersNumber);
		}
		return instance;
	}

	/**
	 * Checks if the game is started.
	 * @return True if the game is started
	 */
	public boolean isGamestarted(){
		return gamestarted;
	}

	/**
	 * Checks if the game is ended.
	 * @return True if the game is ended
	 */
	public boolean isEnded(){
		return isEnded;
	}

	/**
	 * Initializes the players for the game.
	 */

	public void initializePlayers() {
		for (int i = 0; i < chosenPlayersNumber; i++) {
			Player player = new Player("Player " + (i + 1));
			players.add(player);
		}
		Collections.shuffle(players);
		// first player ramdomly
		currentPlayer = new Random().nextInt(players.size());
	}

	/**
	 * Initializes the cards for the game.
	 * Distributes initial cards, objective cards, resource cards, and gold cards to each player.
	 */
	public void InizializeCards() {
		for (Player player : players) {
			Card initialCard = initialCardsDeck.drawCard();
			player.addCard(initialCard);
		}
//player choose ObjectiveCard
		/* - pesca dal deck 2 carte casuali
		 - chiede al player quale carta vuole tra le 2
		 - aggiunge l'obbiettivo al player

		 */
		ArrayList<ObjectiveFront>  drawnObjectiveCards = new ArrayList<ObjectiveFront>();
		for (Player player : players) {
			for (int i = 0; i < 2; i++) {
				ObjectiveFront objectiveCard = (ObjectiveFront) objectiveCardsDeck.drawCard(); //possibile errori di sopraclasse/sottoclasse
				//drawCard restituisce Card (?)
				drawnObjectiveCards.add(objectiveCard);
			}
			ObjectiveFront chosenObjectiveCard = player.chooseObjectiveCard(drawnObjectiveCards);
			player.setGoal(chosenObjectiveCard); //assegna al playerGoal la carta obbiettivo scelta
		}

		for (Player player : players) {
			for (int i = 0; i < 2; i++) {
				Card resourceCard = resourceCardsDeck.drawCard();
				player.addCard(resourceCard);
			}
		}
		for (Player player : players) {
			Card goldCard = goldCardsDeck.drawCard();
			player.addCard(goldCard);
		}
	}

	/**
	 * @return index of current player inside players list
	 */
	public int getCurrentPlayerIndex(){
		return this.currentPlayer;
	}

	/**
	 * @return player at "currentIndex" position
	 */
	public Player getCurrentPlayer(){
		return this.players.get(this.currentPlayer);
	}

	/**
	 * Sets the current player to the next player in counterclockwise order.
	 */

	public void nextTurn() {
		currentPlayer = (currentPlayer + players.size() - 1) % players.size();
		System.out.println("Turno del giocatore: " + players.get(currentPlayer).getName());
	}

	/**
	 * Starts the game.
	 * @return An array representing the indices of the players in the shuffled players list.
	 * @throws NotEnoughPlayers If there are fewer than two players in the game.
	 */
	public int[] startGame() throws NotEnoughPlayers {
		if (chosenPlayersNumber < 2)
			throw new NotEnoughPlayers("The game cannot start without at least two players");

		// Shuffle the players list randomly
		Collections.shuffle(players);

		// Set the first player randomly
		currentPlayer = new Random().nextInt(players.size());
		System.out.println("The first player is: " + players.get(currentPlayer).getName());

		gameStarted = true;
		System.out.println("Game ON");
	}

	/**
	 * Determines the winner of the game based on the score thanks to the Board.
	 * @return The player who has reached 20 points, or null.
	 */

	public Player getWinner() {
		Player winner = scoretrack.checkWinner(); // Controlla se un giocatore ha raggiunto i 20 punti
		if (winner != null) {
			isEnded = true; // Il gioco è terminato
			return winner;
		}
		return null; // Nessun vincitore finora
	}

	/**
	 * Checks the scores from objective cards to determine the winner.
	 */

	public void checkGoals() {
		// Calcola i punteggi relativi alle carte obiettivo per ciascun giocatore
		Map<Player, Integer> goalScores = new HashMap<>();
		for (Player player : players) {
			int playerScore = 0;
			for (ObjectiveCard objectiveCard : player.getObjectiveCards()) {
				playerScore += objectiveCard.getPoints();
			}
			goalScores.put(player, playerScore);
		}
		// Trova il giocatore con il punteggio più alto
		Player winner = null;
		int maxScore = Integer.MIN_VALUE;
		for (Map.Entry<Player, Integer> entry : goalScores.entrySet()) {
			Player player = entry.getKey();
			int score = entry.getValue();
			if (score > maxScore) {
				maxScore = score;
				winner = player;
			}
		}
		// Aggiungi i punteggi delle carte obiettivo ai punteggi generali
		for (Player player : players) {
			int totalScore = scoretrack.getPlayerScore(player) + goalScores.getOrDefault(player, 0);
			scoretrack.setPlayerScore(player, totalScore);
		}

		isEnded = true;
		return winner;
	}

}
