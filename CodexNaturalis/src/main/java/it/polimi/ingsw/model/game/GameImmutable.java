package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Chat.Chat;
import it.polimi.ingsw.model.Chat.Message;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.ScoreTrack;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerDeck;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

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
    private final List<PlayerIC> players;
    private final Integer playersNumber;
    private final ScoreTrackIC scoretrack;
    private final PlayerIC currentPlayer;
    private final DeckIC initialCardsDeck;
    private final BoardIC board;
    private final GameStatus status;
    private final ChatIC chat;

    /**
     * Constructor
     */
    public GameImmutable() {
        gameID=-1;
        players = new ArrayList<>();
        playersNumber= -1;
        scoretrack = new ScoreTrack();
        currentPlayer = new Player();
        initialCardsDeck = new Deck();
        board = new Board();
        status = GameStatus.WAIT;
        chat = new Chat();
    }

    /**
     * Constructor
     * @param modelToCopy
     */
    public GameImmutable(Game modelToCopy) {
        gameID = modelToCopy.getGameId();
        players = new ArrayList<>(modelToCopy.getPlayers());
        playersNumber = modelToCopy.getNumPlayers();
        scoretrack = modelToCopy.getScoretrack();
        currentPlayer = modelToCopy.getCurrentPlayer();
        board = modelToCopy.getBoard();
        status = modelToCopy.getStatus();
        chat = modelToCopy.getChat();
        initialCardsDeck = modelToCopy.getInitialCardsDeck();
    }

    /**
     * @return the nickname of the current playing player
     */
    public String getNicknameCurrentPlaying() {
        return currentPlayer.getNickname();
    }

    /**
     * @return the deck of the current playing player
     */
    public PlayerDeckIC CurrentPlayerDeck() {
        return currentPlayer.getplayerDeck();
    }

    /**
     * @return the winner
     */
    public PlayerIC getWinner() {
        scoretrack.getWinner();
    }

    /**
     * @return the list of players in game
     */
    public List<PlayerIC> getPlayers() {
        return players;
    }

    /**
     * @return the ID of the game
     */
    public Integer getGameId() {
        return gameID;
    }

    public Integer getPlayersNumber() {
        return playersNumber;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public ScoreTrack getScoretrack() {
        return new ScoreTrack();
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Deck getInitialCardsDeck() throws FileNotFoundException, FileReadException {
        return new Deck();
    }

    public Board getBoard() throws FileNotFoundException, FileReadException, DeckEmptyException {
        return new Board();
    }


    public GameStatus getStatus() {
        return status;
    }

    public Chat getChat() {
        return new Chat();
    }

    public void sentMessage(Message m) {
        // Do something with the message
    }


    //da fare
    public String toStringListPlayers() {
        StringBuilder ris = new StringBuilder();
        //da fare
        return ris.toString();
    }

    public int getNumPlayers() {
        return playersNumber;
    }

    public int getNumPlayersOnline() {
        int numplayers = 0;
        for (Player player : players) {
            if (player.isConnected()) {
                numplayers++;
            }
        }
        return numplayers;
    }

    public ObjectiveCard getCurrentPlayerGoal() {
        return currentPlayer.getGoal();
    }

    public void addPlayer(String nick) {

    }


    //public String getGameId() {
    //}
}
