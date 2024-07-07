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
 * Represents an immutable version of the Game model specifically designed
 * for transmission to clients over the network.
 *
 * This class ensures that all objects contained within are immutable, preventing
 * clients from modifying the game state.
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
     * @param modelToCopy is the model instance to copy
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
     * Retrieves the current player.
     *
     * @return the {@code Player} object representing the current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Retrieves the current game board.
     *
     * @return the {@code Board} object representing the current game board.
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Retrieves the current status of the game.
     *
     * @return the {@code GameStatus} object representing the current status of the game.
     */
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

    /**
     * Retrieves the color associated with a player based on their nickname.
     *
     * This method finds the player by their nickname and returns the color associated
     * with that player.
     *
     * @param playerNickname the nickname of the player whose color is to be retrieved.
     * @return the {@code Color} object representing the player's color.
     */
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
     * Retrieves the temporary deck of initial playable cards.
     *
     * This method returns an {@code ArrayList} of arrays containing {@code PlayableCard} objects,
     * which represent the temporary initial cards deck.
     *
     * @return an {@code ArrayList} of {@code PlayableCard} arrays representing the temporary initial cards deck.
     */
    public ArrayList<PlayableCard[]> getInitialCard(){
        return temporaryInitialCard;
    }

    /**
     * Retrieves the temporary deck of objective cards.
     *
     * This method returns an {@code ArrayList} of arrays containing {@code ObjectiveCard} objects,
     * which represent the temporary objective cards deck.
     *
     * @return an {@code ArrayList} of {@code ObjectiveCard} arrays representing the temporary objective cards deck.
     */
    public ArrayList<ObjectiveCard[]> getObjectiveCard(){
        return temporaryObjectiveCards;
    }

    /**
     * Retrieves the current card points.
     *
     * This method returns the current points from the cards as an integer.
     *
     * @return the current card points.
     */
    public int getCurrentCardPoints(){
         return currentCardPoints;
    }

    /**
     * Retrieves the current chat instance.
     *
     * This method returns the {@code Chat} object representing the current chat instance.
     *
     * @return the {@code Chat} object representing the current chat instance.
     */
    public Chat getChat(){
        return chat;
    }

    /**
     * Checks if the game board is updated.
     *
     * This method verifies several conditions to determine if the game board is in an updated state:
     * - The gold cards list is not null and contains at least 2 cards.
     * - The resource cards list is not null and contains at least 2 cards.
     * - Neither the gold cards deck nor the resource cards deck is at the end of the deck.
     *
     * @return {@code true} if the board is updated and valid, {@code false} otherwise.
     */
    public boolean isBoardUpdated() {
        if (board.getGoldCards() == null || board.getGoldCards().size() < 2) {
            return false;
        }
        if (board.getResourceCards() == null || board.getResourceCards().size() < 2) {
            return false;
        }
        if (board.getGoldCardsDeck().checkEndDeck() || board.getResourcesCardsDeck().checkEndDeck()) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the player's deck is updated.
     *
     * This method verifies if the player's mini deck has exactly 3 card arrays and if each card
     * within these arrays is not null. It uses the player's nickname to find the player and
     * their deck.
     *
     * @param nick the nickname of the player whose deck is to be checked.
     * @return {@code true} if the player's mini deck is updated and valid, {@code false} otherwise.
     */
    public boolean isDeckUpdated(String nick){
        Player p= getPlayerByNickname(nick);
        return (p.getPlayerDeck().getMiniDeck().size()==3 && checkPlayerDeck(p.getPlayerDeck().getMiniDeck()));

    }
    /**
     * Checks if the player deck is valid.
     *
     * This method iterates through the given deck and ensures that all card arrays and their
     * contained cards are not null.
     *
     * @param deck the deck to be checked.
     * @return {@code true} if all cards in the deck are not null, {@code false} otherwise.
     */
    private boolean checkPlayerDeck(ArrayList<PlayableCard[]> deck){
        for (PlayableCard[] cardArray : deck) {
            for (PlayableCard card : cardArray) {
                if (card == null) {
                    return false;
                }
            }
        }
        return true;
    }
}

