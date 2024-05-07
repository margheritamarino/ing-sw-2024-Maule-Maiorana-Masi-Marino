package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.exceptions.NoPlayersException;
import it.polimi.ingsw.model.Board;
//import it.polimi.ingsw.model.Chat.Chat;
//import it.polimi.ingsw.model.Chat.Message;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.ScoreTrack;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerDeck;
import it.polimi.ingsw.model.interfaces.*;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Capire COSA deve avere una IC (è una copia?), COSA deve essere mandato sulla Rete:
// vanno bene anche metodi che fanno una COPIA della vera e propria istanza (es. che duplica la Carta Obiettivo del Player)
// e poi vengono mandati sulla rete

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
    private final ScoreTrack scoreTrack;
    private final PlayerIC currentPlayer;
    private final PlayableCardIC[] temporaryInitialCard;

    private final BoardIC board;
    private final GameStatus status;
    //private final ChatIC chat;




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
        //chat = modelToCopy.getChat();
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
    public PlayerIC getLastPlayer() {
        return players.get(players.size() - 1);
    }

    /**
     * @return the winner
     */
    public PlayerIC getWinner() throws NoPlayersException {
        return scoreTrack.getWinner();
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


    public PlayerIC getCurrentPlayer() {
        return currentPlayer;
    }

    public PlayableCardIC[] getTemporaryInitialCardsDeck() throws FileNotFoundException, FileReadException {
        return temporaryInitialCard;
    }

    public BoardIC getBoard(){
        return board;
    }

    public GameStatus getStatus() {
        return status;
    }

    /*
    public ChatIC getChat() {
        return chat;
    }
    */

    /**
     * @param playerNickname search for this player in the game
     * @return the instance of Player with that nickname
     */
    public PlayerIC getPlayerEntity(String playerNickname) {
        return players.stream().filter(x -> x.getNickname().equals(playerNickname)).toList().get(0);
    }

    /**
     * @param nickname player to check if in turn
     * @return true if is the turn of the player's passed by parameter
     */
    public boolean isMyTurn(String nickname) {
        return currentPlayer.getNickname().equals(nickname);
    }

    /*
    public void sentMessage(Message m) {
        // Do something with the message
    }
    */

    /**
     * @return the list of players in string format
     */
    public String toStringListPlayers() {
        StringBuilder ris = new StringBuilder();
        int i = 1;
        for (PlayerIC p : players) {
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
        for (PlayerIC player : players) {
            if (player.isConnected()) {
                numplayers++;
            }
        }
        return numplayers;
    }


    /**
     * @return current player's Goal
     */
    public ObjectiveCardIC getCurrentPlayerGoalIC() {
        return (ObjectiveCard) currentPlayer.getGoalIC();
    }




}
