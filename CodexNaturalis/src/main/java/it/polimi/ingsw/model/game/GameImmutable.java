package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.NoPlayersException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.ScoreTrack;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TUTOR DICE: basta un GameImmutable (copia del Game) che viene inviato sulla rete,
// poi si ricava tutto il resto da questo tramite i metodi GETTER

/**
 * A different implementation of the GameModel class, this is the one we send to the CLIENTS
 * As such, we need to make all the objects in this class immutable, so that the clients
 * cannot modify the course of the game.
 * To do so, a STRATEGY PATTERN was implemented.
 * The pattern consists of implementing for each mutable object two different interfaces,
 * one for the server, one for the client.
 * The server one has no changes from the class it's implemented by
 * the client one, on the other hand, only has getter methods, named differently that the server one,
 * so that the client can only get the object, and doesn't know the names of the setter methods
 * */
public class GameImmutable implements Serializable {

    private final Integer gameID;
    private final List<Player> players;
    private final Integer playersNumber;
    private final ScoreTrack scoreTrack;
    private final Player currentPlayer;
    private final PlayableCard[] temporaryInitialCard;
    private final Board board;
    private final GameStatus status;


    /**
     * Constructor
     * @param modelToCopy is the model istance to copy
     */
    public GameImmutable(Game modelToCopy) {
        gameID = modelToCopy.getGameId();
        players = new ArrayList<>(modelToCopy.getPlayers());
        playersNumber = modelToCopy.getNumPlayers();
        scoreTrack = modelToCopy.getScoretrack();
        currentPlayer = modelToCopy.getCurrentPlayer();
        board = modelToCopy.getBoard();
        status = modelToCopy.getStatus();
        temporaryInitialCard = modelToCopy.getTemporaryInitialCardsDeck();
    }

    /**
     * @return the nickname of the current playing player
     */
    public String getNicknameCurrentPlaying() {
        return currentPlayer.getNickname();
    }



    /**
     * @return the last player in the list of players
     */
    public Player getLastPlayer() {
        return players.get(players.size() - 1);
    }

    /**
     * @return the winner
     */
    public Player getWinner() throws NoPlayersException {
        return scoreTrack.getWinner();
    }

    /**
     * @return the list of players in game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return the ID of the game
     */
    public Integer getGameId() {
        return gameID;
    }

    /**
     * @return the game's scoreTrack
     */
    public ScoreTrack getScoretrack(){
        return scoreTrack;
    }

    /**
     * @return the number of players of the game
     */
    public Integer getPlayersNumber() {
        return playersNumber;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public PlayableCard[] getTemporaryInitialCardsDeck() {
        return temporaryInitialCard;
    }

    public Board getBoard(){
        return board;
    }

    public GameStatus getStatus() {
        return status;
    }


    /**
     * @param playerNickname search for this player in the game
     * @return the instance of Player with that nickname
     */
    public Player getPlayerEntity(String playerNickname) {
        return players.stream().filter(x -> x.getNickname().equals(playerNickname)).toList().get(0);
    }


    /**
     * @return the list of players in string format
     */
    public String toStringListPlayers() {
        StringBuilder ris = new StringBuilder();
        int i = 1;
        for (Player p : players) {
            ris.append("[#").append(i).append("]: ").append(p.getNickname()).append("\n");
            i++;
        }
        return ris.toString();
    }

    /**
     * @return the number of players
     */
    public Integer getNumPlayers() {
        return playersNumber;
    }

    /**
     * @return the number of players connected
     */
    public int getNumPlayersOnline() {
        int numplayers = 0;
        for (Player player : players) {
            if (player.isConnected()) {
                numplayers++;
            }
        }
        return numplayers;
    }


    /**
     * @return current player's Goal
     */
    public ObjectiveCard getCurrentPlayerGoal() {
        return (ObjectiveCard) currentPlayer.getGoal();
    }




}
