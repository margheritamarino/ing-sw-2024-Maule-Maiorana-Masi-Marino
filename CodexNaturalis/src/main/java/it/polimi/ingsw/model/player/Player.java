package it.polimi.ingsw.model.player;

import java.io.IOException;
import java.lang.IllegalStateException;
import java.rmi.RemoteException;
import java.util.*;


import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.Book;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameImmutable;
import java.io.Serializable;


import static it.polimi.ingsw.network.PrintAsync.printAsync;

/**
 * Represents a player in the game.
 * <p>
 * This class encapsulates the attributes and behaviors of a player participating in the game.
 * It manages the player's nickname, state, deck, book, objective card, connection status, and listeners for game events.
 * </p>
 */
public class Player implements Serializable {

    private final String nickname;
    private PlayerState state;
    private final PlayerDeck playerDeck;
    private final Book playerBook;
    private ObjectiveCard playerGoal; //identifica l'obbiettivo che ha il player
    private boolean connected;
    private boolean readyToStart = false;
    private final transient ArrayList<GameListenerInterface>listeners;
    private final Color playerColor;

    /**
     * Constructs a player with a specified nickname and color.
     *
     * @param nickname the nickname of the player
     * @param color    the color assigned to the player
     */
    public Player(String nickname, Color color) {
        this.nickname = nickname;
        this.playerGoal = null;
        this.state = PlayerState.Start; // Imposta lo stato iniziale a "Start"
        this.playerBook = new Book(40, 40); //ho messo 40 x 40 solo per verificare la correttezza del metodo, questo valore dobbiamo poi renderlo variabile in base al numero di giocatori
        this.playerDeck = new PlayerDeck();
        this.connected = false;
        this.listeners= new ArrayList<>();
        this.playerColor=color;

    }
    /**
     * Retrieves the list of listeners registered for this player.
     *
     * @return the list of listeners
     */
    public ArrayList<GameListenerInterface> getListeners(){
        return this.listeners;
    }
    /**
     * Adds a listener to the list
     * @param lis listener to add
     */
    public void addListener(GameListenerInterface lis) {
        listeners.add(lis);
    }
    /**
     * Retrieves the nickname of the player.
     *
     * @return The nickname of the player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the player's connection status
     */
    public boolean getConnected() {
        return this.connected;
    }

    /**
     * Sets the player as not ready to play
     */
    public void setNotReadyToStart() {
        readyToStart = false;
    }

    /**
     * @param connected sets the player's connection
     */
    public void setConnected(boolean connected) {
        System.out.println("Player - setConnected( " + connected + " ) \n");
        this.connected = connected;
    }

    /**
     * @return the player's readiness to start
     */
    public boolean getReadyToStart() {
        return this.readyToStart;
    }

    /**
     * Sets the player as ready to play
     */
    public void setReadyToStart() {
        this.readyToStart = true;
    }


    /**
     * Retrieves the state of the player.
     *
     * @return The state of the player.
     */
    public PlayerState getPlayerState() {
        return this.state;
    }

    /**
     * Sets the player's Objective card rapresenting the player's goal.
     *
     * @param chosenCard is the objective card chosen between 2 Objective Cards
     */

    public boolean initialized=false;
    public void setGoal(ObjectiveCard chosenCard) {
        this.playerGoal = chosenCard;
        initialized=true;
        System.out.println("Goal setted: " +chosenCard.getCardID());
    }
    public boolean isInitialized(){
            return this.initialized;
    }
    public ObjectiveCard getGoal() {
        return this.playerGoal;
    }

    public Book getPlayerBook() {
        return this.playerBook;
    }

    public PlayerDeck getPlayerDeck() {
        return this.playerDeck;
    }
    public Color getPlayerColor(){
        return this.playerColor;
    }


    /**
     * Places a card at the specified position in the player's book.
     *
     * @param posCard the position of the card in the player's deck to be placed
     * @param rowCell the row index of the cell in the book where the card should be placed
     * @param rowCol  the column index of the cell in the book where the card should be placed
     * @return the victory points earned by placing the card
     * @throws PlacementConditionViolated if the placement conditions are violated
     * @throws CellNotAvailableException  if the specified cell is not available for placing the card
     */
    public int placeCard(int posCard, int rowCell, int rowCol) throws PlacementConditionViolated, CellNotAvailableException {
        ArrayList<Cell> availableCells = this.playerBook.showAvailableCells();
        Cell chosenCell = null;
        PlayableCard chosenCard;
        boolean found = false;


        for (Cell availableCell : availableCells) {
            if (availableCell.getRow() == rowCell && availableCell.getColumn() == rowCol) {
                chosenCell = availableCell;
                found = true;
            }
        }
        if (!found)
            throw new IndexOutOfBoundsException("Invalid Cell Position! Choose an Available CELL");

        int points =-1;
        try {
            ArrayList<PlayableCard[]> miniDeck = this.playerDeck.getMiniDeck();
            chosenCard = switch (posCard) {
                case 0 -> miniDeck.getFirst()[0];
                case 1 -> miniDeck.get(0)[1];
                case 2 -> miniDeck.get(1)[0];
                case 3 -> miniDeck.get(1)[1];
                case 4 -> miniDeck.get(2)[0];
                case 5 -> miniDeck.get(2)[1];

                default -> throw new IllegalStateException("Unexpected value: " + posCard);
            };

            if (chosenCard != null){
                points = playerBook.addCard(chosenCard, chosenCell);
                playerDeck.removeCard(posCard);
            }
        }catch (PlacementConditionViolated | CellNotAvailableException e){
            throw e;
        }
        return points;
    }

    /**
     * Notifies listeners that a card has been drawn.
     *
     * @param model the game model to pass as a new immutable game model
     */
    public void notify_CardDrawn(Game model) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.cardDrawn(new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_CardDrawn, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies listeners that initial setup is required.
     *
     * @param model the game model to pass as a new immutable game model
     * @param index the index indicating the specific requirement
     */
    public void notify_requireInitial( Game model, int index){

        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.requireInitialReady(new GameImmutable(model), index);
            } catch (FileReadException | IOException e) {
                printAsync("During notification of notify_requireInitial, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies listeners that the player is ready to start the game.
     *
     * @param model the game model to pass as a new immutable game model
     */
  public void notify_playerReady( Game model){
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.playerReady(new GameImmutable(model), nickname);
            } catch ( IOException e) {
                printAsync("During notification of notify_playerReady, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies listeners that goals setup is required.
     *
     * @param model the game model to pass as a new immutable game model
     * @param index the index indicating the specific requirement
     */
    public void notify_requireGoals( Game model, int index){
        System.out.println("Player: notify_requireGoals");
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                // Ottieni le carte obiettivo utilizzando il metodo drawObjectiveCards()
                l.requireGoalsReady(new GameImmutable(model), index);
            } catch (RemoteException | IllegalStateException e) {
                printAsync("During notification of notify_requireGoals, a disconnection has been detected before ping");

            }
        }
    }

    /**
     * Notifies listeners that the player's cards are ready.
     *
     * @param model the game model to pass as a new immutable game model
     */
    public void notify_cardsReady( Game model){
        System.out.println("Player: notify_requireGoals");
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.cardsReady(new GameImmutable(model));
            } catch (RemoteException | IllegalStateException e) {
                printAsync("During notification of notify_requireGoals, a disconnection has been detected before ping");

            }
        }
    }

    /**
     * The notify_NotCorrectChosenCard method notifies that a player chose a card that cannot place in his Book
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public void notify_NotCorrectChosenCard(Game model, String msg){
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.wrongChooseCard( new GameImmutable(model), msg);
            } catch (RemoteException e) {
                printAsync("During notification of notify_NotCorrectChosenCard, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

    /**
     * Notifies listeners that a message has been sent.
     *
     * @param gameModel the game model to pass as a new immutable game model
     * @param msg       the message that has been sent
     */
    public void notify_SentMessage(Game gameModel, Message msg) {
        System.out.println("Notifying listeners of new message: " + msg.getText());
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.sentMessage(new GameImmutable(gameModel), msg);
                System.out.println("Listener notified: " + l.toString());
            } catch (RemoteException e) {
                printAsync("During notification of notify_SentMessage, a disconnection has been detected before PING");
                i.remove();
            }
        }
    }

    /**
     * Compares this player to another object for equality.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return Objects.equals(nickname, player.nickname);
    }

    /**
     * Returns the hash code of this player.
     *
     * @return the hash code of this player
     */
    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }


    /**
     * Removes a listener from the player's list of listeners.
     *
     * @param lis the listener to remove
     */
    public void removeListener(GameListenerInterface lis) {
        listeners.remove(lis);
    }

    /**
     *  Notifies the player's listener that an attempt of reconnection has failed.
     * @param msg the message to show in the view
     */
    public void notify_ReconnectionFailed(String msg) {
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.errorReconnecting(msg);
            } catch (RemoteException e) {
                printAsync("During notification of notify_ReconnectionFailed, a disconnection has been detected before ping");
                i.remove();
            }
        }
    }

}
