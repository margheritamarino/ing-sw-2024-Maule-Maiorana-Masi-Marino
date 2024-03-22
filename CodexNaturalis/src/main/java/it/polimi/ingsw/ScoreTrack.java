package it.polimi.ingsw;

import java.util.Map;
import java.util.HashMap;

public class ScoreTrack {

	private Map<Player, Integer> pointsPlayers;
	public ScoreTrack(){
		pointsPlayers = new HashMap<>();
	}

//method to add a player to the score and initialize to 0
	public void addPlayer(Player player) {
		pointsPlayers.put(player, 0);
	}
	public void addPoints(Player player, int points) {
		int currentPoints = pointsPlayers.getOrDefault(player, 0);
		pointsPlayers.put(player, currentPoints + points);

	}

	public Map<Player, Integer> checkTo20() {
		Map<Player, Integer> pointsTo20 = new HashMap<>(); //new map with player and point to reach 20
		for (Map.Entry<Player, Integer> entry : pointsPlayers.entrySet()) { //itero la mappa attraverso entrySet ead accedo alle coppie chiave-valore tramite la Map.Entry
			Player player = entry.getKey();
			int points = entry.getValue();
			int pointsNeeded = Math.max(0, 20 - points);
			pointsTo20.put(player, pointsNeeded);
		}
		return pointsTo20;
	}

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
