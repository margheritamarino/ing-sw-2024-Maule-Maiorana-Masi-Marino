package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


//Game model
public class Game {

	private static Game instance;

	private final int chosenPlayersNumber;
	protected ArrayList<Player> players;
	protected Deck objectiveCardsDeck;
	protected Deck initialCardsDeck;
	protected Deck resourceCardsDeck;
	protected Deck goldCardsDeck;
	protected Scoretrack scoretrack;
	private int currentPlayer;
	protected Board board;
	protected boolean isEnded = false;
	protected boolean gamestarted = false;

//class singleton: private constructor
	private Game(int playersNumber){
		//check number of players
		if(playersNumber < 1 || playersNumber > 4){
			throw new IllegalArgumentException("The number of players must be between 1 and 4.");
		}
		this.chosenPlayersNumber = playersNumber;
		this.objectiveCardsDeck = new Deck();
		this.initialCardsDeck = new Deck();
		this.goldCardsDeck = new Deck();
		this.resourceCardsDeck = new Deck();

		this.players = new ArrayList<>();
		this.scoretrack = new Scoretrack();
		this.currentPlayer = 0;
		this.board = new Board();

	}

//Singleton
	public static synchronized Game getInstance(int playersNumber) {
		if (instance == null) {
			instance = new Game(playersNumber);
		}
		return instance;
	}

	public boolean isGamestarted(){
		return gamestarted;
	}

	public boolean isEnded(){
		return isEnded;
	}

//method to initialize players (firstplayer randomly)
	public void initializePlayers() {
		for (int i = 0; i < chosenPlayersNumber; i++) {
			Player player = new Player("Player " + (i + 1));
			players.add(player);
		}
		Collections.shuffle(players);
		// first player ramdomly
		currentPlayer = new Random().nextInt(players.size());
	}

	public void InizializeCards() {
		for (Player player : players) {
			Card initialCard = initialCardsDeck.drawCard();
			player.addCard(initialCard);
		}
//player choose ObjectiveCard
		ArrayList<Card>  drawnObjectiveCards = new ArrayList<>();
		for (Player player : players) {
			for (int i = 0; i < 2; i++) {
				Card objectiveCard = objectiveCardsDeck.drawCard();
				drawnObjectiveCards.add(objectiveCard);
			}
			Card chosenObjectiveCard = player.chooseObjectiveCard(drawnObjectiveCards);
			player.addCard(chosenObjectiveCard);
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

//ritorna l'indice del giocatore corrente nella lista di giocatori
	public int getCurrentPlayerIndex(){
		return this.currentPlayer;
	}
//ritorna il giocatore nella posizione dell'indice corrente
	public Player getCurrentPlayer(){
		return this.players.get(this.currentPlayer);
	}


//metodo per stabilire il prossimo giocatore in ordine antiorario
	public void nextTurn() {
		currentPlayer = (currentPlayer + players.size() - 1) % players.size();
		System.out.println("Turno del giocatore: " + players.get(currentPlayer).getName());
	}

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

//trovo il giocatore che ha raggiunto i 20 punti grazie alla Board
	public Player getWinner() {
		Player winner = scoretrack.checkWinner(); // Controlla se un giocatore ha raggiunto i 20 punti
		if (winner != null) {
			isEnded = true; // Il gioco è terminato
			return winner;
		}
		return null; // Nessun vincitore finora
	}

	//trovo il vero vincitore sommando i punti delle carte obbiettivo
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
