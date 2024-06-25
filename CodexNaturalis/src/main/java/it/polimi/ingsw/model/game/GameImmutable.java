package it.polimi.ingsw.model.game;

import it.polimi.ingsw.Chat.Chat;
import it.polimi.ingsw.exceptions.NoPlayersException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.ScoreTrack;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


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

/**
 * Represents an immutable version of the Game model specifically designed
 * for transmission to clients over the network.
 *
 * This class ensures that all objects contained within are immutable, preventing
 * clients from modifying the game state. To achieve this, a Strategy Pattern is
 * employed:
 *
 * - Two interfaces are implemented for each mutable object:
 *   - One interface for the server (which mirrors the mutable class).
 *   - One interface for the client, which exposes only getter methods.
 *
 * By implementing these interfaces, the client only has access to getter methods
 * and cannot invoke setter methods, thereby maintaining the immutability of the
 * game state throughout its transmission.
 */

public class GameImmutable implements Serializable {

    private final Integer gameID;
    private final List<Player> players;
    private final Integer playersNumber;
    private final ScoreTrack scoreTrack;
    private final Player currentPlayer;
    private final ArrayList<PlayableCard[]> temporaryInitialCard;
    private final ArrayList<ObjectiveCard[]> temporaryObjectiveCards;
    private final Board board;
    private final GameStatus status;
    private final int[] orderArray;
    private int currentCardPoints;
    private final Chat chat;

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
        chat = modelToCopy.getChat();
        status = modelToCopy.getStatus();
        temporaryInitialCard = modelToCopy.getTemporaryInitialCardsDeck();
        temporaryObjectiveCards = modelToCopy.getTemporaryObjectiveCardsDeck();
        this.orderArray = modelToCopy.getOrderArray();
        this.currentCardPoints= modelToCopy.getCurrentCardPoints();
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
    public int getIndexPlayer(Player p){
        for(int i=0; i< playersNumber; i++){
            if(players.get(i).equals(p))
                return i;
        }
        return -1;
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
    public Player getPlayerByNickname(String playerNickname) {
        return players.stream().filter(x -> x.getNickname().equals(playerNickname)).toList().getFirst();
    }
    public Color getPlayerColor(String playerNickname){
        return getPlayerByNickname(playerNickname).getPlayerColor();
    }

    /**
     * @param playerNickname search for this player in the game
     * @return the ObjectiveCard of the player with that nickname
     */
    public ObjectiveCard getPlayerGoalByNickname(String playerNickname) {
        return players.stream()
                .filter(x -> x.getNickname().equals(playerNickname))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Player not found"))
                .getGoal();
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
     * @return the list of players in string format with their ready status colored
     */
    public String toStringListPlayersReady() {
        final String RESET = "\u001B[0m";
        final String GREEN = "\u001B[32m";
        final String RED = "\u001B[31m";

        StringBuilder ris = new StringBuilder();
        int i = 1;
        for (Player p : players) {
            ris.append("[#").append(i).append("]: ").append(p.getNickname());
            if (p.getReadyToStart()) {
                ris.append(" - ").append(GREEN).append("Ready").append(RESET);
            } else {
                ris.append(" - ").append(RED).append("NOT Ready").append(RESET);
            }
            ris.append("\n");
            i++;
        }
        return ris.toString();
    }

    /**
     * Generates a string representation of the order array, listing each player's nickname
     * along with their position in the order array.
     *
     * @return A string representation of the order array.
     */
    public String toStringListOrderArray() {
        StringBuilder ris = new StringBuilder();

        for (int o : orderArray) {
            ris.append("[#").append(o).append("]: ").append(players.get(o).getNickname()).append("\n");
        }
        return ris.toString();
    }
    public int[] getOrderArray(){
        return orderArray;
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
            if (player.getConnected()) {
                numplayers++;
            }
        }
        return numplayers;
    }


    public ArrayList<PlayableCard[]> getInitialCard(){
        return temporaryInitialCard;
    }

    public ArrayList<ObjectiveCard[]> getObjectiveCard(){
        return temporaryObjectiveCards;
    }

    public int getCurrentCardPoints(){
         return currentCardPoints;
    }

    public Chat getChat(){
        return chat;
    }

    // Implementazione del metodo isBoardUpdated
    public boolean isBoardUpdated() {
        // Controlla se ci sono almeno 2 carte d'oro sul board
        if (board.getGoldCards() == null || board.getGoldCards().size() < 2) {
            return false;
        }

        // Controlla se ci sono almeno 2 carte risorsa sul board
        if (board.getResourceCards() == null || board.getResourceCards().size() < 2) {
            return false;
        }


        // Verifica che i mazzi non siano vuoti
        if (board.getGoldCardsDeck().checkEndDeck() || board.getResourcesCardsDeck().checkEndDeck()) {
            return false;
        }

        // Se tutti i controlli sono passati, la board è aggiornata
        return true;
    }

    public boolean arePlayersEnough(){
        return players.size() == getNumPlayers();
    }
}

