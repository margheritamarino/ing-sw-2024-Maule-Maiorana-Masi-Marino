package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.exceptions.IllegalArgumentException;
import it.polimi.ingsw.exceptions.GameEndedException;
import it.polimi.ingsw.exceptions.GameNotStartedException;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.interfaces.PlayableCardIC;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerDeck;

import it.polimi.ingsw.listener.ListenersHandler;


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
	private PlayableCard[] temporaryInitialCard;
	private ArrayList<ObjectiveCard> temporaryObjectiveCards;


	private final transient ListenersHandler listenersHandler; //transient: non può essere serializzato

	/**
	 * Private Constructor
	 * @param playersNumber The number of players in the game.
	 * @throws IllegalArgumentException If the number of players is not between 1 and 4.
	 */
	public Game(int playersNumber) throws IllegalArgumentException, FileNotFoundException, FileReadException, DeckEmptyException {

		this.playersNumber = playersNumber;
		this.initialCardsDeck = new Deck(CardType.InitialCard);
		this.players = new ArrayList<>();
		this.scoretrack = new ScoreTrack();
		this.currentPlayer = null;
		this.board = new Board();
		this.orderArray = new int[playersNumber];
		this.status = GameStatus.WAIT;
		this.temporaryInitialCard= new PlayableCard[2];
		this.listenersHandler = new ListenersHandler();

	}
	public Game() throws IllegalArgumentException, FileNotFoundException, FileReadException, DeckEmptyException {
		this.playersNumber = 0;
		this.initialCardsDeck = new Deck(CardType.InitialCard);
		this.players = new ArrayList<>();
		this.scoretrack = new ScoreTrack();
		this.currentPlayer = null;
		this.board = new Board();
		this.orderArray = new int[playersNumber];
		this.status = GameStatus.WAIT;
		this.temporaryInitialCard= new PlayableCard[2];
		this.listenersHandler = new ListenersHandler();

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
        } catch (DeckEmptyException e) {
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
	public ScoreTrack getScoretrack(){
		return this.scoretrack;
	}

	/**
	 * Returns the number of players registered for the game
	 * @return the number of players as an integer.
	 */
	public int getNumPlayers(){
		return this.playersNumber;
	}

	/**
	 * Returns the number of players who are currently online.
	 * @return the number of players online
	 */
	public int getNumPlayersOnline() { //serve perché uno dei giocatori iscritto al gioco potrebbe accidentalmente perdere la connessione
		int numplayers = 0;
		for (Player player : players) {
			if (player.isConnected()) {
				numplayers++;
			}
		}
		return numplayers;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

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
	 * * @return the list of listeners
	 *
	 * */
	public List<GameListenerInterface> getListeners() {
		return listenersHandler.getListeners();
	}

	/**
	 * @param listener adds the listener to the list
	 * */
	public void addListener(GameListenerInterface listener) {
		listenersHandler.addListener(listener);
	}

	/**
	 * @param listener removes listener from list
	 */
	public void removeListener(GameListenerInterface listener) {
		listenersHandler.removeListener(listener);
	}

	/**
	 * @return the book of the CurrentPlayer
	 */
	public Book getCurrentPlayerBook(){
		return currentPlayer.getPlayerBook();
	}
	/**
	 * @return the Goal (ObjectiveCard of the CurrentPlayer
	 */
	public ObjectiveCard getCurrentPlayerGoal(){
		return currentPlayer.getGoal();
	}

	public void setNumPlayers(int playersNumber) {
		this.playersNumber = playersNumber;
	}

	/**
	 * Returns the player at the specified position in the list of players.
	 * @param pos The position of the player to retrieve
	 * @return The player at the specified position in the list.**/
	public Player getPlayer(int pos) {
		return players.get(pos);
	}

	/**
	 * @param player is set as ready, then everyone is notified
	 */
	public void playerIsReadyToStart(Player player) {
		player.setReadyToStart();
		listenersHandler.notify_PlayerIsReadyToStart(this, player.getNickname());
	}

	/**
	 * Checks if the game is full or if the provided nickname is already taken.
	 *
	 * @return true if the game is full (i.e., there are already 4 players),
	 */
	public boolean isFull() {
		return playersNumber == 4;
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
	public void addPlayer(String nickname) {
		// Check if the game is not full and the nickname is not taken
		try {
			// Check if the game is not full
			if (isFull()) {
				// Game is full
				listenersHandler.notify_JoinUnableGameFull(null, this);
				throw new MatchFull("There are already 4 players");
			}

			// Check if the nickname is already taken
			if (checkNickname(nickname)) {
				// Nickname is already taken
				listenersHandler.notify_JoinUnableNicknameAlreadyIn(null);
				throw new NicknameAlreadyTaken("The nickname " + nickname + " is already taken");
			}

			// Create a new player with the given nickname
			Player newPlayer = new Player(nickname);
			players.add(newPlayer);
			scoretrack.addPlayer(newPlayer);
			// Increment the number of players
			playersNumber++;

			// Notify listeners that a player has joined the game
			listenersHandler.notify_PlayerJoined(this, nickname);

		} catch (NicknameAlreadyTaken e) {
			// Handle the case where the nickname is already taken
			System.err.println("Error: " + e.getMessage());

		} catch (MatchFull e) {
			// Handle the case where the game is full
			System.err.println("Error: " + e.getMessage());
		}

	}

	/**
	 * Chooses a random first player from the array of players, assigns them to currentPlayer,
	 * and saves the order of players for the next turns starting from the first player.
	 * @return An array with the order of players starting from the first player chosen.
	 */
	//ORDINE DEI GIOCATORI: scelgo a caso il primo, gli altri sONO QUELLI SUCCESSIVI AL PRIMO in ordine di inserimento
	public void chooseOrderPlayers() {

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
		this.orderArray= orderArray;
	}
	/**
	 * The function removes the player with username "username" from the game.
	 * @param nickname is the nickname of the Player that you want to remove from the game.
	 *
	 * @throws  IllegalArgumentException if the player's nickname is not in the game
	 */
	public void removePlayer (String nickname) {

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getNickname().equals(nickname)) {
				scoretrack.removePlayer(players.get(i));
				players.remove(i);
				listenersHandler.notify_PlayerLeft(this, nickname);
				if (this.status.equals(GameStatus.RUNNING) && players.stream().filter(Player::isConnected).toList().size() <= 1) {
					//Not enough players to keep playing
					this.setStatus(GameStatus.ENDED);
				}
			}
		}


	}

	/**
	 * @return true if the player in turn is online
	 */
	public boolean isTheCurrentPlayerOnline() {
		return currentPlayer.isConnected();
	}

	/**
	 * Sets the player with the specified nickname as disconnected.
	 * Removes the player disconnected from the game and notifies listeners.
	 *
	 * @param nickname the nickname of the player to set as disconnected.
	 */
	public void setPlayerDisconnected(String nickname) {
		//TODO
		Player disconnectedPlayer = null;
		for (Player player : players) {
			if (player.getNickname().equals(nickname)) {
				disconnectedPlayer = player;
				break;
			}
		}
		// Se il giocatore disconnesso è stato trovato
		if (disconnectedPlayer != null) {
			// Imposta il giocatore come disconnesso e come non pronto a giocare
			disconnectedPlayer.setConnected(false);
			disconnectedPlayer.setNotReadyToStart();

			// Notifica tutti gli altri giocatori sulla disconnessione
			for (Player player : players) {
				if (!player.getNickname().equals(nickname)) {
					listenersHandler.notify_playerDisconnected(this, nickname);
				}
			}

		}
	}


	/**
	 * @param nickname is the name of player that wants to reconnect
	 * @throws PlayerAlreadyInException player is already in
	 * @throws MaxPlayersInException    there's already 4 players in game
	 * @throws GameEndedException       the game has ended
	 */
	//TODO
	public boolean reconnectPlayer(String nickname) throws PlayerAlreadyInException, MaxPlayersInException, GameEndedException, PlayerNotFoundException, GameNotStartedException, InvalidPointsException, NoPlayersException {
		// Check if the game has ended (serve perché se il gioco è finito è meglio evitare che dei giocatori si riconnettano e magari modifichino il risultato finale)
		if (gameEnded) {
			throw new GameEndedException();
		}
		// Check if the player is already in the game
		if (checkNickname(nickname)) {
			throw new PlayerAlreadyInException();
		}
		// Check if the game is full
		if (players.size() >= 4) {
			throw new MaxPlayersInException();
		}
		// Find the player by nickname in the list of players
		Player playerToReconnect = null;
		for (Player player : players) {
			if (player.getNickname().equals(nickname)) {
				playerToReconnect = player;
				break;
			}
		}
		// If player not found, throw exception
		if (playerToReconnect == null) {
			throw new PlayerNotFoundException("Player not found with the given nickname.");
		}
		// Reconnect the player
		playerToReconnect.setConnected(true);
		listenersHandler.notify_playerReconnected(this, nickname);

		//DA GESTIRE TURNI APPENA SI RICONNETTE
//		// If the reconnected player is not the current player, advance to the next turn
//		if (!playerToReconnect.equals(currentPlayer)) {
//			nextTurn(); //faccio avanzare il turno solo se il giocatore riconnesso non è quello corrente quindi nel frattempo vado avanti
//		}
//		// If the reconnected player is not online, go to next turn
//		if (!isTheCurrentPlayerOnline()) {
//			nextTurn();
//		}
		return true;
	}


	/**
	 * @return true if there are enough players to start, and if every one of them is ready
	 */
	public boolean arePlayersReadyToStartAndEnough() {
		//If every player is ready, the game starts
		return players.stream().filter(Player::getReadyToStart)
				.count() == players.size() && players.size() >= DefaultValue.minNumOfPlayer;
	}
	/**
	 * @return the game status
	 */
	public GameStatus getStatus() {
		return status;
	}
	public int[] getOrderArray(){
		return this.orderArray;
	}
	public void setCurrentPlayer(Player p){
		this.currentPlayer=p;
	}

	public void initializeBoard() {
		board.initializeBoard();
	}

	/**
	 * Sets the initial RUNNING game status
	 * If I want to set the gameStatus to "RUNNING", there needs to be at least
	 * DefaultValue.minNumberOfPlayers -> (2) in lobby, the right number of Cards on the Board and a valid currentPlayer
	 */
	public void setInitialStatus() {
		try {
			if (this.status == GameStatus.WAIT && //devo essere PRIMA che inizi il gioco (altrimenti il checkBoard() NON ha senso!!
					players.size() >= 2
					&& checkBoard()
					&& currentPlayer != null) {
				this.status = GameStatus.RUNNING;
				listenersHandler.notify_GameStarted(this);
			}
		}catch (BoardSetupException e){
			System.err.println("Error during Board setup: " + e.getMessage());
		}
	}

	/**
	 * Sets the game status in case of ENDED or LAST_CIRCLE
	 * @param status is the status of the game
	 */
	public void setStatus(GameStatus status) {
		this.status = status;

		if (status == GameStatus.ENDED) {
				listenersHandler.notify_GameEnded(this);

		} else if (status == GameStatus.LAST_CIRCLE) {
				listenersHandler.notify_LastCircle(this);
		}
	}
		/**
		 * Check the board for correct setup
		 * @throws BoardSetupException if any of the board setups is incorrect
		 */
		public boolean checkBoard() throws BoardSetupException {
			// Verifying all conditions together
			if (!(board.verifyGoldCardsNumber() &&
					board.verifyResourceCardsNumber() &&
					board.verifyObjectiveCardsNumber() &&
					board.verifyGoldDeckSize(playersNumber) &&
					board.verifyResourceDeckSize(playersNumber) &&
					board.verifyObjectiveDeckSize(playersNumber))) {
				throw new BoardSetupException("Board setup is incorrect");
				}

			// All verifications passed, return true
			return true;
		}


	public void nextTurn() throws GameEndedException, GameNotStartedException, NoPlayersException, InvalidPointsException, PlayerNotFoundException {
		if (status.equals(GameStatus.RUNNING) || status.equals(GameStatus.LAST_CIRCLE)) {
			// Trova l'indice dell'attuale currentPlayer in orderArray
			int currentIndex = -1;
			for (int i = 0; i < orderArray.length; i++) {
				if (players.get(orderArray[i]).equals(currentPlayer)) {
					currentIndex = i;
					break;
				}
			}
			if(currentIndex == playersNumber - 1){
				if (scoretrack.checkTo20()) { //= true -> un giocatore è arrivato alla fine (chiamo ultimo turno)
					setStatus(GameStatus.LAST_CIRCLE); //viene notificato qui
				}
			}
			if (status.equals(GameStatus.LAST_CIRCLE)){
				lastTurnGoalCheck();

				//condizione FINE GIOCO
				if(currentIndex == playersNumber-1) {
					setStatus(GameStatus.ENDED);
					throw new GameEndedException(); //TERMINA IL GIOCO
				}
			}

			// Calcola l'indice del giocatore successivo
			int nextIndex = (currentIndex + 1) % orderArray.length; //se è l'ultimo riparte dall'inizio
			// Imposta il nuovo currentPlayer
			currentPlayer = players.get(orderArray[nextIndex]);
			listenersHandler.notify_nextTurn(this);
		}
		else if (status.equals(GameStatus.ENDED)) {
			throw new GameEndedException();
		} else {
			throw new GameNotStartedException();
		}
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
	public void lastTurnGoalCheck() throws InvalidPointsException, PlayerNotFoundException { //
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
	public void initializeCards(Player player) {
		boolean initializationSuccessful = true;

		try {
			temporaryInitialCard = initialCardsDeck.returnCard();
			listenersHandler.notify_requireInitial(this);

			//GOLD CARD E RESOURCE CARD
			for (int i = 0; i < 2; i++) {
				player.pickCard(board, CardType.ResourceCard, true, 0);
			}
			player.pickCard(board, CardType.GoldCard, true, 0);

			temporaryObjectiveCards = drawObjectiveCards();
			// Inizializza gli obiettivi
			listenersHandler.notify_requireGoals(this); //view richiede le 2 carte obbiettivo da mostrar con il metodo drawObjectiveCards()

		} catch (FileNotFoundException e) {
			System.err.println("Error: file not found during cards initialization - " + e.getMessage());
			initializationSuccessful = false;

		} catch (DeckEmptyException e) {
			System.err.println("Error: deck empty during cards initialization - " + e.getMessage());
			initializationSuccessful = false;


		} catch (DeckFullException e) {
			System.err.println("Error: playerDeck full during cards initialization - " + e.getMessage());
			initializationSuccessful = false;
		}

	}

	public PlayableCard[] getInitialCard(){
		return temporaryInitialCard;
	}

	/**
	 * Sets the initial card front or back chosen
	 * in the book of the given player
	 * @param player The player for whom the initial card will be set.
	 * @param pos The position of the temporary initial card to choose from.
	 *            This index is used to fetch the card from the `temporaryInitialCard` array.
	 */
	public void setInitialCard(Player player, int pos){
		PlayableCard chosenInitialCard = temporaryInitialCard[pos];
		Book playerBook= player.getPlayerBook();
		playerBook.addInitial(chosenInitialCard);
	}


	/**
	 * Draws two objective cards from the board's objective card deck.
	 * to be chosen by the player
	 * @return An ArrayList containing two drawn objective cards from the deck.
	 * @throws IllegalStateException If the game has already started and cards cannot be drawn.
	 * @throws DeckEmptyException If the objective card deck is empty.
	 */
	public ArrayList<ObjectiveCard> drawObjectiveCards() throws IllegalStateException, DeckEmptyException {
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
	/**
	 * Assigns the chosen objective card to a specific player.
	 *
	 * @param player The player to whom the chosen objective card will be assigned.
	 * @param index the idenx of the objective card to be assigned to the player.
	 */
	public void setPlayerGoal(Player player, int index) {
		ObjectiveCard chosenObjectiveCard = temporaryObjectiveCards.get(index);
		player.setGoal(chosenObjectiveCard);
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
	 * @return an ArrayList of available cells for the current player.
	 */
	public ArrayList<Cell> getCurrentPlayerCells(){
		return currentPlayer.getPlayerBook().showAvailableCells();
	}
	public Book getBook(){
		return currentPlayer.getPlayerBook();
	}
	/**
	 * @return the current player's player Deck
	 */
	public PlayerDeck getCurrentPlayerDeck(){
		return currentPlayer.getPlayerDeck();
	}


	public int placeCardTurn( Player p, int chosenCard, int rowCell, int colCell)  {
		try {
			int points=  p.placeCard(chosenCard, rowCell, colCell);
			listenersHandler.notify_CardPlaced(this);
			return points;
		}catch(PlacementConditionViolated e){
			listenersHandler.notify_NotCorrectChosenCard(this);
			return 0;
		}catch (IndexOutOfBoundsException e){
			listenersHandler.notify_NotCorrectChosenCell(this);
			return 0;
		}

	}

	public void addPoints(Player p, int points) {
		scoretrack.addPoints(p, points);
		listenersHandler.notify_PointsAdded(this);
	}



	public void pickCardTurn(Board board, CardType cardType, boolean drawFromDeck, int pos)  {
		try {
			currentPlayer.pickCard(board, cardType, drawFromDeck, pos);
		} catch (FileNotFoundException e) {
			System.err.println("cannot find json file");
		} catch (DeckEmptyException e) {
            System.err.println("Resource cards' deck is empty");
        }catch (DeckFullException e){
			System.err.println("PlayerDeck is full");
		}
        // Notifica ai listeners che una carta è stata pescata
		listenersHandler.notify_CardDrawn(this);
	}

	public Board getBoard(){
		return this.board;
	}
	/**
	 * Gets the list of gold cards on the board.
	 * @return an ArrayList of PlayableCard arrays representing the gold cards on the board.
	 */
	public ArrayList<PlayableCard[]> getBoardGoldCards() {
		return board.getGoldCards();
	}
	/**
	 * Gets the list of resource cards on the board.
	 *
	 * @return an ArrayList of PlayableCard arrays representing the resource cards on the board.
	 */
	public ArrayList<PlayableCard[]> getBoardResourceCards() {
		return board.getResourceCards();
	}

	/**
	 * Gets the array of objective cards on the board.
	 * @return an array of ObjectiveCard objects representing the common goals on the board.
	 */
	public ObjectiveCard[] getCommonGoals() {
		return board.getObjectiveCards();
	}

	/**
	 * Gets the gold cards deck on the board.
	 * @return a Deck object representing the gold cards deck on the board.
	 */
	public Deck getGoldCardsDeck() {
		return board.getGoldCardsDeck();
	}

	/**
	 * Gets the resources cards deck on the board.
	 *
	 * @return a Deck object representing the resources cards deck on the board.
	 */
	public Deck getResourcesCardsDeck() {
		return board.getResourcesCardsDeck();
	}

	/**
	 * Gets the objective cards deck on the board.
	 * @return an ObjectiveDeck object representing the objective cards deck on the board.
	 */
	public ObjectiveDeck getObjectiveCardsDeck() {
		return board.getObjectiveCardsDeck();
	}


	public Deck getInitialCardsDeck() {
		return initialCardsDeck;
	}

	public PlayableCardIC[] getTemporaryInitialCardsDeck() {
		return temporaryInitialCard;
	}

	/**
	 * @param playerNickname
	 * @return player by nickname
	 */
	public Player getPlayerByNickname(String playerNickname) {
		// Utilizza lo stream per cercare un giocatore con il nickname specificato
		Optional<Player> optionalPlayer = players.stream()
				.filter(player -> player.getNickname().equals(playerNickname))
				.findFirst();

		// Restituisce il giocatore se trovato, altrimenti null
		return optionalPlayer.orElse(null);
	}
}
