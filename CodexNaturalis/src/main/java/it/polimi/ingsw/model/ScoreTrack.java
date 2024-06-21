package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidPointsException;
import it.polimi.ingsw.exceptions.NoPlayersException;
import it.polimi.ingsw.exceptions.PlayerNotFoundException;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * ScoreTrack model
 * This class represents the score tracking system for players in the game
 */
public class ScoreTrack implements Serializable {

	private final Map<Player, Integer> pointsPlayers;

	/**
	 * Constructs a ScoreTrack instance with an empty map of players' scores.
	 */
	public ScoreTrack(){
        pointsPlayers = new HashMap<>();
    }

    /**
     * Adds a player to the score and initialize his score to 0.
	 *
     * @param player The player to be added.
     */
	public void addPlayer(Player player) {
		pointsPlayers.put(player, 0);
	}

	/**
	 * Removes a player from the score tracking system.
	 *
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
	 *
	 * @param player The player to check.
	 * @return true if the player exists in the system, false otherwise.
	 */
	public boolean checkPlayerExists(Player player) {
		return pointsPlayers.containsKey(player);
	}

	/**
	 * Adds points to the specified player's score.
	 *
	 * @param player The player whose score is to be updated.
	 * @param points The number of points to add to the player's score.
	 */
	public void addPoints(Player player, int points)   {
		try {
			if (!pointsPlayers.containsKey(player)) {
				throw new PlayerNotFoundException("Player not found in the map.");
			}

			if (points < 0) {
				throw new InvalidPointsException("Cannot add negative points.");
			}

			int currentPoints = pointsPlayers.get(player);
			pointsPlayers.put(player, currentPoints + points);
		}catch (PlayerNotFoundException | InvalidPointsException e){
			System.err.println("Error:"+ e.getMessage());
		}

	}

	/**
	 * Retrieves the score of the specified player.
	 *
	 * @param player The player whose score is to be retrieved.
	 * @return the score of the player.
	 */
	public int getPlayerScore(Player player){
		return pointsPlayers.getOrDefault(player, 0);
	}

	/**
	 * Sets the score of the player.
	 *
	 * @param player The player whose score is to be set.
	 * @param score the new score for the player.
	 */
	public void setPlayerScore(Player player, int score){
		pointsPlayers.put(player, score);
	}

	/**
	 * Checks if any player has reached 20 points.
	 *
	 * @return true if there is a player who has reached 20 points, false otherwise.
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
	 * Retrieves the player with the maximum score.
	 *
	 * @return The player with the highest score, or null if there are no players.
	 * @throws NoPlayersException If there are no players in the score tracking system.
	 */
	public Player getWinner() throws NoPlayersException {
		Player maxPlayer = null;
		int maxScore = Integer.MIN_VALUE;

		if (pointsPlayers.isEmpty()) {
			throw new NoPlayersException("There are no players in the map.");
		}
		for (Map.Entry<Player, Integer> entry : pointsPlayers.entrySet()) {
			Player player = entry.getKey();
			int score = entry.getValue();

			if (score > maxScore) {
				maxScore = score;
				maxPlayer = player;
			}
		}
		return maxPlayer;
	}

	/**
	 * Retrieves a list of players sorted by their scores in descending order.
	 *
	 * @return A list of players sorted by score (descending).
	 */
	public ArrayList<Player> getPlayersByScore() {
		ArrayList<Player> playerList =  new ArrayList<>(pointsPlayers.keySet());

		playerList.sort((p1, p2) -> {
			int scoreP1 = pointsPlayers.getOrDefault(p1, 0);
			int scoreP2 = pointsPlayers.getOrDefault(p2, 0);

			return Integer.compare(scoreP2, scoreP1);
		});

		return playerList;
	}

	/**
	 * Returns a string representation of the ScoreTrack, displaying players and their scores.
	 *
	 * @return String representation of the ScoreTrack.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("**********SCORETRACK**********: \n");
		result.append("\n");
		for (Map.Entry<Player, Integer> entry : pointsPlayers.entrySet()) {
			Player player = entry.getKey();
			int score = entry.getValue();
			result.append(player.getNickname()).append(": ").append(score).append("\n");
			result.append("-------------------------\n");
		}
		result.append("***************************\n");
		result.append("\n");
		return result.toString();
	}


}

