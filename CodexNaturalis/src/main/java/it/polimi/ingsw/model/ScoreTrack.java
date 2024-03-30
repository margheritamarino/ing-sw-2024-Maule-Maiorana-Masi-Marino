package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Irene Pia Masi
 * ScoreTrack model
 * This class represents the score tracking system for players in the game
 */
public class ScoreTrack {

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
     * Adds points to the specified player's score
     * @param player The player whose score is to be updated
     * @param points The number of points to add to the player's score
     */
	public void addPoints(Player player, int points, Map<Player, Integer> objectivePoints) {
		int currentPoints = pointsPlayers.getOrDefault(player, 0);
		pointsPlayers.put(player, currentPoints + points);

		// Check if the player has reached 20 points even with the points of the current round
		if (currentPoints + points >= 20) {
			// Aggiungi i punti delle carte obiettivo al giocatore
			Integer objectivePointsForPlayer = objectivePoints.get(player);
			if (objectivePointsForPlayer != null) {
				pointsPlayers.put(player, currentPoints + objectivePointsForPlayer);
			}
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
     * @return the player who has reached 20, or null if no player has reached that score yet.
     */

	public Player checkTo20() {
		int winningScore = 20;
		for (Map.Entry<Player, Integer> entry : pointsPlayers.entrySet()) {
			Player player = entry.getKey();
			int points = entry.getValue();
			if (points >= winningScore) {
				return player;
			}
		}
		return null;
	}
}
