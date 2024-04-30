package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Chat.Chat;
import it.polimi.ingsw.model.Chat.Message;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.ScoreTrack;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.Player;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

public class GameImmutable implements Serializable {

    private final int gameID;
    private final ArrayList<Player> players;
    private final ScoreTrack scoretrack;
    private final Player currentPlayer;
    private final Deck initialCardsDeck;
    private final Board board;
    private final GameStatus status;
    private final Chat chat;

    public GameImmutable(Game modeltoCopy) {
        gameID = modeltoCopy.getGameId();
        players = new ArrayList<>(modeltoCopy.getPlayers());
        scoretrack = modeltoCopy.getScoretrack();
        currentPlayer = modeltoCopy.getCurrentPlayer();
        board = modeltoCopy.getBoard();
        status = modeltoCopy.getStatus();
        chat = modeltoCopy.getChat();
        initialCardsDeck = modeltoCopy.getInitialCardDeck();
    }

    public int getGameID() {
        return gameID;
    }

    public int getPlayersNumber() {
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

    public int[] getOrderArray() {
        return orderArray.clone();
    }

    public PlayableCard[] getTemporaryInitialCard() {
        return temporaryInitialCard.clone();
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

    //public String getGameId() {
    //}
}
