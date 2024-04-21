package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.exceptions.IllegalArgumentException;
import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.exceptions.GameNotStartedException;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerDeck;

import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.*;

/**
 * Game model
 *  GameModel is the class that represents the game, it contains all the information about the game, and it's based on a MVC pattern
 *  It manages the game state, including players, cards, decks, and game progression
 *   It also contains the current player that is playing
 *   It also contains the listenersHandler that handles the listeners
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
	private GameStatus status;
	private int[] orderArray;
	private Integer firstFinishedPlayer = -1;
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
		this.orderArray = new int[playersNumber];
		this.status = GameStatus.WAIT;
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

	/**
	 * Returns the number of players.
	 * @return the number of players as an integer.
	 */
	public int getNumPlayers(){
		return this.playersNumber;
	}

	//PLAYERS

	/**
	 * return the player who is playing the current turn
	 * @return the current player as a `Player` object	 */
	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}

	/**
	 * @return the game id
	 */
	public int getGameId() {
		return gameID;
	}

	/**
	 * Sets the game id
	 *
	 * @param gameID new game id
	 */
	public void setGameId(int gameID) {
		this.gameID = gameID;
	}

	/**
	 * @return the book of the CurrentPlayer	 */
	public Book getCurrentPlayerBook(){
		return currentPlayer.getPlayerBook();
	}
	/**
	 * @return the Goal (ObjectiveCard of the CurrentPlayer	 */
	public ObjectiveCard getCurrentPlayerGoal(){
		return currentPlayer.getGoal();
	}


	/**
	 * Returns the player at the specified position in the list of players.
	 * @param pos The position of the player to retrieve
	 * @return The player at the specified position in the list.**/
	public Player getPlayer(int pos) {
		return players.get(pos);
	}


	/**
	 * Checks if the game is full or if the provided nickname is already taken.
	 *
	 * @param nickname the nickname of the player who wants to enter.
	 * @return true if the game is full (i.e., there are already 4 players),
	 * the game has started, or the nickname is already taken; otherwise, returns false.
	 */
	public boolean isFull(String nickname) {
		return (this.playersNumber==4 || gameStarted || checkNickname(nickname));

	}
	/**
	 * @param nickname nickname to check
	 * @return true if already exist
	 */
	public boolean checkNickname(String nickname)  {
		for(Player p : this.players) {
			if(nickname.equals(p.getNickname())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a new player to the game.
	 * after checking if there is space in the match and if the nickname is available
	 * @param nickname the nickname of the player to be added.
	 * @throws NicknameAlreadyTaken if the provided nickname is already taken.
	 * @throws MatchFull if the game is full and cannot accommodate more players.
	 */
	public void addPlayer(String nickname) throws Exception {
		// Check if the game is not full and the nickname is not taken
		if (!isFull(nickname)) {
			// Create a new player with the given nickname
			Player newPlayer = new Player(nickname);

			// Add the new player to the list of players
			players.add(newPlayer);

			// Add the new player to the score track
			scoretrack.addPlayer(newPlayer);

			// Increment the number of players
			playersNumber++;
		} else if (checkNickname(nickname)) {
			throw new NicknameAlreadyTaken(nickname);
		} else {
			throw new MatchFull("There are already 4 players");
		}
	}

	/**
	 * Chooses a random first player from the array of players, assigns them to currentPlayer,
	 * and saves the order of players for the next turns starting from the first player.
	 * @return An array with the order of players starting from the first player chosen.
	 */
	//ORDINE DEI GIOCATORI: scelgo a caso il primo, gli altri sONO QUELLI SUCCESSIVI AL PRIMO in ordine di inserimento
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
	 * The function removes the player with username "username" from the game.
	 * @param nickname is the nickname of the Player that you want to remove from the game.
	 *
	 * @throws  IllegalArgumentException if the player's nickname is not in the game
	 */
	public void removePlayer (String nickname) throws IllegalArgumentException {

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getNickname().equals(nickname)) {
				scoretrack.removePlayer(players.get(i));
				players.remove(i);
				return;
			}
		}
		throw new IllegalArgumentException("Player not in this game");
	}

	/**
	 * Starts the game by randomly selecting the first player and
	 * sets up the game order creating an array of order indices for the next turns.
	 * Initializes the game board and cards and returns the order array.
	 * @throws NotEnoughPlayersException if the number of players is less than two.
	 */
	public void startGame() throws NotEnoughPlayersException, NoPlayersException {

		if (playersNumber < 2)
			throw new NotEnoughPlayersException("The game cannot start without at least two players");

		// Ottieni l'ordine dei giocatori da `chooseOrderPlayers`
		orderArray = chooseOrderPlayers();

		// Assegna il primo giocatore selezionato casualmente a `currentPlayer`
		currentPlayer = players.get(orderArray[0]);
		gameStarted = true; //controlla se serve
		setStatus(GameStatus.RUNNING);
		board.initializeBoard();
		inizializeCards();
	}

	/**
	 * @return the game status
	 */
	public GameStatus getStatus() {
		return status;
	}

	/**
	 * Sets the game status
	 * @param status is the status of the game
	 */
	//copiato da quello dell'anno scorso -> da modificare
	public void setStatus(GameStatus status) throws NoPlayersException {
		//If I want to set the gameStatus to "RUNNING", there needs to be at least
		// DefaultValue.minNumberOfPlayers -> (2) in lobby
		if (status.equals(GameStatus.RUNNING) &&
				((players.size() < DefaultValue.minNumOfPlayer
						|| getNumOfCommonCards() != DefaultValue.NumOfCommonCards
						|| !doAllPlayersHaveGoalCard())
						|| currentPlaying == -1)) {
			throw new NotReadyToRunException();
		} else {
			this.status = status;

			if (status == GameStatus.RUNNING) {
				listenersHandler.notify_GameStarted(this);
				listenersHandler.notify_nextTurn(this);
			} else if (status == GameStatus.ENDED) {
				Player winner = getWinner(); //Find winner
				listenersHandler.notify_GameEnded(this, winner);
			} else if (status == GameStatus.LAST_CIRCLE) {
				listenersHandler.notify_LastCircle(this);
			}
		}
	}


	public void nextTurn() throws GameEndedException, GameNotStartedException {
		if (status.equals(GameStatus.RUNNING) || status.equals(GameStatus.LAST_CIRCLE)) {
			// Trova l'indice dell'attuale currentPlayer in orderArray
			int currentIndex = -1;
			for (int i = 0; i < orderArray.length; i++) {
				if (players.get(orderArray[i]).equals(currentPlayer)) {
					currentIndex = i;
					break;
				}
			}

			if (scoretrack.checkTo20()) { //= true -> un giocatore è arrivato alla fine (chiamo ultimo turno)
				setStatus(GameStatus.LAST_CIRCLE); //viene notificato qui
				setFinishedPlayer(currentIndex);
			}

			// Calcola l'indice del giocatore successivo
			int nextIndex = (currentIndex + 1) % orderArray.length; //se è l'ultimo riparte dall'inizio
			// Imposta il nuovo currentPlayer
			currentPlayer = players.get(orderArray[nextIndex]);

			// Verifica se è arrivato alla fine del giro a partire da firstFinishedPlayer
			if (status.equals(GameStatus.LAST_CIRCLE) && currentIndex == firstFinishedPlayer) {
				// Se si verifica questa condizione, il gioco termina
				setStatus(GameStatus.ENDED);
				throw new GameEndedException();
			}
			listenersHandler.notify_nextTurn(this);
		}

		else if (status.equals(GameStatus.ENDED)) {
			throw new GameEndedException();
		} else {
			throw new GameNotStartedException();
		}
	}
	/**
	 * @param indexPlayer sets the indexPlayer as the index of the first player to fill his shelf
	 */
	public void setFinishedPlayer(Integer indexPlayer) {
		firstFinishedPlayer = indexPlayer;
	}



	/**
	 * Checks the personal goal and common goals of the currentPlayer.
	 * This method first checks the personal goal of the current player, and then
	 * verifies the common goals present on the board. If any errors occur during the
	 * checks, such as invalid points or player not found, the exceptions
	 * `InvalidPointsException` and `PlayerNotFoundException` will be thrown.
	 *
	 * @throws InvalidPointsException if there are errors related to points during the goal checks.
	 * @throws PlayerNotFoundException if the current player is not found.
	 */
	public void LastTurnCheck() throws InvalidPointsException, PlayerNotFoundException { //
		// Controlla l'obiettivo del giocatore corrente
		checkGoal(currentPlayer.getGoal());

		// Ottieni le carte degli obiettivi dal board e controlla gli obiettivi
		ObjectiveCard[] commonGoals = board.getObjectiveCards();
		for (ObjectiveCard commonGoal : commonGoals) {
			checkGoal(commonGoal);
		}

	}

	//risale al playerbook del giocatore corrente e
	//chiama il metodo checkGoal del giocatore corrente per aggiungere i punti
	public void checkGoal(ObjectiveCard goalToCheck) throws InvalidPointsException, PlayerNotFoundException {
		Book currentBook = currentPlayer.getPlayerBook();
		int goalPoints = currentBook.checkGoal(goalToCheck);
		scoretrack.addPoints(currentPlayer, goalPoints);
	}


	/**
	 * Initializes the cards for the game.
	 * Each player picks
	 * Distributes initial cards, objective cards, resource cards, and gold cards to each player.
	 */
	public void inizializeCards() {
		//pick an InitialCard
		for (Player player : players) {
			//INITIAL CARDS
			PlayableCard[] initialCard = initialCardsDeck.returnCard();
			PlayerDeck playerDeck= player.getPlayerDeck();
			playerDeck.addCard(initialCard);


			//GOLD CARD E RESOURCE CARD
			for (int i = 0; i < 2; i++) {
				player.pickCard(board, CardType.ResourceCard, true, 0);
			}
			player.pickCard(board, CardType.GoldCard, true, 0);


			// Inizializza gli obiettivi
			ArrayList<ObjectiveCard> drawnCards = drawObjectiveCards(); //restituisce due carte dal deck ObjectiveCards


			// Chiama il controller per mostrare le carte al giocatore
			controller.showObjectiveCardsToPlayer(player, drawnCards);


			//poi il controller dentro quest metodo chiama: model.setPlayerGoal
		}
	}

	public ArrayList<ObjectiveCard> drawObjectiveCards() throws IllegalStateException {
		ArrayList<ObjectiveCard> drawnCards = new ArrayList<ObjectiveCard>();

		// Controlla che il gioco sia in uno stato valido per pescare carte obiettivo
		if (status.equals(GameStatus.WAIT)) {
			// Pesca due carte obiettivo
			for (int i = 0; i < 2; i++) {
				ObjectiveCard objectiveCard = board.takeObjectiveCard();
				drawnCards.add(objectiveCard);
			}
		}
		else
			throw new IllegalStateException("Game already started");
		return drawnCards;
	}

	public void setPlayerGoal(Player player, ObjectiveCard chosenCard) {
		player.setGoal(chosenCard);
	}



	/** Determines the winner of the game based on the score thanks to the Board.
	 * @return The player WINNER
	 */
	public Player getWinner() throws NoPlayersException {
		Player winner = scoretrack.getWinner();
		return winner;
	}
	/**
	 * Retrieves the available cells for the current player.
	 *
	 * This method returns an ArrayList of cells that are avilable to place a card
	 * It retrieves the available cells from
	 * the current player's player book and provides them as a list.
	 *
	 * @return an ArrayList of available cells for the current player.
	 */
	public ArrayList<Cell> getCurrentPlayerCells(){
		return currentPlayer.getPlayerBook().showAvailableCells();
	}

	/**
	 * @return the current player's player Deck
	 */
	public PlayerDeck getCurrentPlayerDeck(){
		return currentPlayer.getPlayerDeck();
	}
	public void PlaceCardTurn( int posCell, int posCard) throws InvalidPointsException, PlayerNotFoundException {
		int points= currentPlayer.placeCard(scoretrack, posCell, posCard);
		scoretrack.addPoints(currentPlayer, points);
		//NOTIFICARE
	}

	public void pickCardTurn(Board board, CardType cardType, boolean drawFromDeck, int pos){
		currentPlayer.pickCard(board, cardType, drawFromDeck, pos);
		//NOTIFICARE
	}




//---------------------------------------------------------------------------
	//fino a qui


	/*public Map<Player, Integer> getObjectivePoints() {
		return objectivePoints;
	}
	/*
	public void nextTurn() {
		currentPlayer = (currentPlayer + players.size() - 1) % players.size();
		System.out.println("Player's turn: " + players.get(currentPlayer).getColor());
	}*/






//	/**
//	 * Checks the scores from objective cards to determine the winner.
//	 */

	/*public Player checkGoals() {
		// Calcola i punteggi relativi alle carte obiettivo per ciascun giocatore
		Map<Player, Integer> goalScores = new HashMap<>();
		for (Player player : players) {
			int playerScore = 0;
			ObjectiveCard objectiveCard = player.getGoal();
			playerScore += objectiveCard.getVictoryPoints();

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
	}*/

}
