package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.exceptions.IllegalArgumentException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerDeck;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;


/**
 * @author Irene Pia Masi
 * Game model
 * The Game class manages the game state, including players, cards, decks, and game progression.
 * It implements the singleton pattern to ensure only one instance of the game exists.
 */
public class Game {

	private int gameID;
	private static Game instance;
	private int playersNumber;
	protected ArrayList<Player> players;
	protected ScoreTrack scoretrack;
	private Player currentPlayer;
	private final Deck initialCardsDeck;
	protected Board board;
	protected boolean gameEnded = false;
	protected boolean gameStarted = false;


	/**
	 * Private Constructor
	 * @param playersNumber The number of players in the game.
	 * @throws IllegalArgumentException If the number of players is not between 1 and 4.
	 */
	private Game(int playersNumber) throws IllegalArgumentException, FileNotFoundException, FileReadException {
		//check number of players
		if(playersNumber < 1 || playersNumber > 4){
			throw new IllegalArgumentException("The number of players must be between 1 and 4.");
		}
		this.playersNumber = playersNumber;
		this.initialCardsDeck = new Deck(CardType.InitialCard);
		this.players = new ArrayList<>();
		this.scoretrack = new ScoreTrack();
		this.currentPlayer = null;
		this.board = new Board();

	}

	/**
	 * Singleton class
	 * @param playersNumber The number of players in the game
	 * @return the game instance.
	 */
	public static synchronized Game getInstance(int playersNumber) {
		try {
			if (instance == null || instance.playersNumber != playersNumber) {
				instance = new Game(playersNumber);
			}
			return instance;
		} catch (IllegalArgumentException e) {
			System.err.println("Error: " + e.getMessage());
			return null;
		} catch (FileNotFoundException | FileReadException e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * Checks if the game is started.
	 * @return True if the game is started
	 */
	public boolean isGamestarted(){
		return gameStarted;
	}

	/**
	 * Checks if the game is ended.
	 * @return True if the game is ended
	 */
	public boolean isEnded(){
		return gameEnded;
	}
	public int getNumPlayers(){
		return this.playersNumber;
	}

	//PLAYERS
	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}
	/**
	 * @return the nickname of the CurrentPlayer
	 */
	public String getCurrentPlayerNickname(){
		return currentPlayer.getNickname();
	}
	/**
	 * @return the book of the CurrentPlayer
	 */
	public Book getCurrentPlayerBook(){
		return currentPlayer.getPlayerBook();
	}
	public ObjectiveCard getCurrentPlayerGoal(){
		return currentPlayer.getGoal();
	}
	public PlayerDeck getCurrentPlayerDeck(){
		return currentPlayer.getPlayerDeck();
	}

	/**
	 * Returns the player at the specified position in the list of players.
	 *
	 * @param pos The position of the player to retrieve
	 * @return The player at the specified position in the list.**/
	public Player getPlayer(int pos) {
		return players.get(pos);
	}



	/**
	 * @param nickname nickname of player who want to enter
	 * @return true if thers no space of nickname already taken
	 */
	public boolean isFull(String nickname) {
		return (this.playersNumber==4 || gameStarted || checkNickname(nickname));

	}
	/**
	 * @param nickname nickname to check
	 * @return true if already exist
	 */
	public boolean checkNickname(String nickname) {
		for(Player p : this.players)
		{
			if(nickname.equals(p.getNickname())) return true;
		}
		return false;
	}

	/**
	 * function to add a new player to the game
	 * @param nickname the nickname of the player
	 * @throws Exception
	 */
	public void addPlayer(String nickname) throws Exception {
		if(playersNumber<4) {
			for (Player p: players) { //check if there is already this nickname
				if(p.getNickname().equals(nickname))
					throw new NicknameAlreadyTaken(nickname);
			}
			// Create a new player with the given nickname
			Player newPlayer = new Player(nickname);
			// Add the new player to the list of players
			players.add(newPlayer);
			// Add the new player to the score track
			scoretrack.addPlayer(newPlayer);
			// Increment the number of players
			playersNumber++;
		}
		else
			throw new MatchFull("There are already 4 players");
	}

	/**
	 * Chooses a random first player from the array of players, assigns them to currentPlayer,
	 * and saves the order of players for the next turns starting from the first player.
	 * @return An array with the order of players starting from the first player chosen.
	 */
	public int[] chooseOrderPlayers() {

		Random random = new Random();
		int randomIndex = random.nextInt(players.size());
		currentPlayer = players.get(randomIndex);

		// Create an array to store the order of players starting from the chosen first player
		int[] orderArray = new int[players.size()];

		// Fill the order array starting from the chosen first player
		for (int i = 0; i < players.size(); i++) {
			orderArray[i] = (randomIndex + i) % players.size();
		}
		// Return the order array
		return orderArray;
	}

	/**
	 * Starts the game by randomly selecting the first player and
	 * sets up the game order creating an array of order indices for the next turns.
	 * Initializes the game board and cards and returns the order array.
	 *
	 * @return an array representing the order of players, starting from the first player.
	 * @throws NotEnoughPlayersException if the number of players is less than two.
	 */
	public int[] startGame() throws NotEnoughPlayersException {

		if (playersNumber < 2)
			throw new NotEnoughPlayersException("The game cannot start without at least two players");

		// Ottieni l'ordine dei giocatori da `chooseOrderPlayers`
		int[] orderArray = chooseOrderPlayers();

		// Assegna il primo giocatore selezionato casualmente a `currentPlayer`
		currentPlayer = players.get(orderArray[0]);

		/* Calcola e imposta gli indici dei controller per ogni giocatore
		for (int i = 0; i < players.size(); i++) {
			int playerIndex = orderArray[i];
			Player player = players.get(playerIndex);

			// Calcola e imposta l'indice del controller
			player.setControllerIndex(i);		 */

		gameStarted = true;
		board.initializeBoard();
		inizializeCards();
		return orderArray;
	}





	/**
	 * Initializes the cards for the game.
	 * Distributes initial cards, objective cards, resource cards, and gold cards to each player.
	 */
	public void inizializeCards() {
		//pick an InitialCard
		for (Player player : players) {
			PlayableCard[] initialCard = initialCardsDeck.returnCard();
			PlayerDeck playerDeck= player.getPlayerDeck();
			playerDeck.addCard(initialCard);
		}
		//player choose ObjectiveCard
		/* - pesca dal deck 2 carte casuali
		 - chiede al player quale carta vuole tra le 2
		 - aggiunge l'obbiettivo al player		 */
		ArrayList<ObjectiveCard>  drawnObjectiveCards = new ArrayList<ObjectiveCard>();
		for (Player player : players) {
			for (int i = 0; i < 2; i++) {
				ObjectiveCard objectiveCard = board.takeObjectiveCard();
				drawnObjectiveCards.add(objectiveCard);
			}
			ObjectiveCard chosenObjectiveCard = chooseObjectiveCard(drawnObjectiveCards);
			player.setGoal(chosenObjectiveCard); //assegna al playerGoal la carta obbiettivo scelta
		}

		for (Player player : players) {
			for (int i = 0; i < 2; i++) {
				player.pickCard(board, CardType.ResourceCard, true, 0);
			}
			player.pickCard(board, CardType.GoldCard, true, 0);
		}
	}


	//---------------------------------------------------------------------------
	//fino a qui

	//METODO DA IMPLEMENTARE
	public ObjectiveCard chooseObjectiveCard(ArrayList<ObjectiveCard> drawnObjectiveCards){
		return ;
	}



	/**
	 * Sets the current player to the next player in counterclockwise order.
	 */

	public Map<Player, Integer> getObjectivePoints() {
		return objectivePoints;
	}
	public void nextTurn() {
		currentPlayer = (currentPlayer + players.size() - 1) % players.size();
		System.out.println("Player's turn: " + players.get(currentPlayer).getColor());
	}




	/**
	 * Determines the winner of the game based on the score thanks to the Board.
	 * @return The player who has reached 20 points, or null.
	 */

	public Player getWinner() {
		Player winner = scoretrack.checkTo20(); // Controlla se un giocatore ha raggiunto i 20 punti
		if (winner != null) {
			gameEnded = true; // Il gioco è terminato
			return winner;
		}
		return null; // Nessun vincitore finora
	}

	/**
	 * Checks the scores from objective cards to determine the winner.
	 */

	public Player checkGoals() {
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
