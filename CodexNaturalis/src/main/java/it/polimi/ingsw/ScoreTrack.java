package it.polimi.ingsw;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Irene Pia Masi
 * ScoreTrack model
 * This class represents the score tracking system for players in the game
 */
public class ScoreTrack {

	private Map<Player, Integer> pointsPlayers;
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
	public void addPoints(Player player, int points) {
		int currentPoints = pointsPlayers.getOrDefault(player, 0);
		pointsPlayers.put(player, currentPoints + points);

	}

    /**
     * Checks the points needed by each player to reach 20 points.
     * @return a map containing each player and the points needed to reach a score of 20.
     */

	public Map<Player, Integer> checkTo20() {
		Map<Player, Integer> pointsTo20 = new Map<>(); //new map with player and point to reach 20
		for (Map.Entry<Player, Integer> entry : pointsPlayers.entrySet()) { //itero la mappa attraverso entrySet ead accedo alle coppie chiave-valore tramite la Map.Entry
			Player player = entry.getKey();
			int points = entry.getValue();
			int pointsNeeded = Math.max(0, 20 - points);
			pointsTo20.put(player, pointsNeeded);
		}
		return pointsTo20;
	}

    /**
     * Determines the winner based on the current scores.
     * @return the player with the highest score equal to or greater than 20, or null if there is no winner yet.
     */

	public Player checkWinner() {
		int winningScore = 20;
		Player winner = null;
		for (Map.Entry<Player, Integer> entry : pointsPlayers.entrySet()) {
			Player player = entry.getKey();
			int points = entry.getValue();
			if (points >= winningScore) { // Controlla se il giocatore ha raggiunto o superato il punteggio di vittoria
				if (winner == null || points > pointsPlayers.get(winner)) {
					winner = player;
				}
			}
		}
		return winner;
	}

}
