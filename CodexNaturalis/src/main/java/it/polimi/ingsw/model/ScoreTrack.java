package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidPointsException;
import it.polimi.ingsw.exceptions.NoPlayersException;
import it.polimi.ingsw.exceptions.PlayerNotFoundException;
//import it.polimi.ingsw.model.interfaces.ScoreTrackIC;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Irene Pia Masi
 * ScoreTrack model
 * This class represents the score tracking system for players in the game
 */
public class ScoreTrack implements Serializable {

	private final Map<Player, Integer> pointsPlayers;
	public ScoreTrack(){
        pointsPlayers = new HashMap<>();
    }

    /**
     * Adds a player to the score and initialize his score to 0.
     * @param player The player to be added.
     */
	public void addPlayer(Player player) {
		pointsPlayers.put(player, 0);
	}

	/**
	 * Removes a player from the score tracking system.
	 * @param player The player to be removed.
	 */
	public void removePlayer(Player player) {
		try {
			if (pointsPlayers.containsKey(player)) {
				pointsPlayers.remove(player);
			} else {
				throw new PlayerNotFoundException("Player not found in the score tracking system.");
			}
		} catch (PlayerNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Checks if the specified player exists in the score tracking system (for removing him).
	 * @param player The player to check.
	 * @return true if the player exists in the system, false otherwise.
	 */
	public boolean checkPlayerExists(Player player) {
		return pointsPlayers.containsKey(player);
	}

	/**
	 * Adds points to the specified player's score.
	 * @param player The player whose score is to be updated.
	 * @param points The number of points to add to the player's score.
	 * @throws PlayerNotFoundException If the player is not found in the map.
	 * @throws InvalidPointsException If the points to add are negative.
	 */
	public void addPoints(Player player, int points)   {
		try {
			// Controlla se il giocatore è presente nella mappa
			if (!pointsPlayers.containsKey(player)) {
				throw new PlayerNotFoundException("Player not found in the map.");
			}

			// Controlla se i punti sono negativi
			if (points < 0) {
				throw new InvalidPointsException("Cannot add negative points.");
			}

			// Aggiungi i punti al punteggio corrente del giocatore
			int currentPoints = pointsPlayers.get(player);
			pointsPlayers.put(player, currentPoints + points);
		}catch (PlayerNotFoundException | InvalidPointsException e){
			System.err.println("Error:"+ e.getMessage());
		}

	}

	/**
	 * Retrieves the score of the specified player.
	 * @param player The player whose score is to be retrieved.
	 * @return the score of the player.
	 */
	public int getPlayerScore(Player player){
		return pointsPlayers.getOrDefault(player, 0);
	}

	/**
	 * Sets the score of the player.
	 * @param score the new score for the player.
	 */
	public void setPlayerScore(Player player, int score){
		pointsPlayers.put(player, score);
	}

    /**
     * Determines if any player has reached 20 points.
     * @return true if there is a player who has reached 20, or false otherwise.
     */
	public boolean checkTo20() {
		int winningScore = 20;
		for (Map.Entry<Player, Integer> entry : pointsPlayers.entrySet()) {
			int points = entry.getValue();
			if (points >= winningScore) {
				return true;
			}
		}
		return false;
	}

	/**
	* Returns the player with the maximum score in the map.
	* @return The player with the highest score, or null if there are no players.			*/
	public Player getWinner() throws NoPlayersException {
		// Variabile per tenere traccia del giocatore con il punteggio massimo
		Player maxPlayer = null;
		// Variabile per tenere traccia del punteggio massimo
		int maxScore = Integer.MIN_VALUE;

		// Controlla se ci sono giocatori nella mappa
		if (pointsPlayers.isEmpty()) {
			throw new NoPlayersException("There are no players in the map.");
		}
		// Itera sulla mappa dei punteggi dei giocatori
		for (Map.Entry<Player, Integer> entry : pointsPlayers.entrySet()) {
			// Ottieni il giocatore e il punteggio corrente dall'entry della mappa
			Player player = entry.getKey();
			int score = entry.getValue();

			// Confronta il punteggio corrente con il punteggio massimo
			if (score > maxScore) {
				// Se il punteggio corrente è superiore, aggiorna il giocatore e il punteggio massimo
				maxScore = score;
				maxPlayer = player;
			}
		}
		// Ritorna il giocatore con il punteggio massimo
		return maxPlayer;
	}



	/**
	 * @return list with Players SORTED by Score (descending order)
	 */
	public ArrayList<Player> getPlayersByScore() {
		// Create a list of players from the map attribute pointsPlayers
		ArrayList<Player> playerList =  new ArrayList<>(pointsPlayers.keySet());

		// Sort the list using a comparator
		playerList.sort((p1, p2) -> {
			// Get scores of players p1 and p2 from the map
			int scoreP1 = pointsPlayers.getOrDefault(p1, 0);
			int scoreP2 = pointsPlayers.getOrDefault(p2, 0);

			// Sort in descending order by score
			return Integer.compare(scoreP2, scoreP1);
		});

		// Return the sorted list
		return playerList;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("**********SCORETRACK**********: \n");
		for (Map.Entry<Player, Integer> entry : pointsPlayers.entrySet()) {
			Player player = entry.getKey();
			int score = entry.getValue();
			result.append(player.getNickname()).append(": ").append(score).append("\n");
			result.append("***************************\n");
			result.append("\n");
		}
		return result.toString();
	}


}

