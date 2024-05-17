package it.polimi.ingsw.model.player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;


import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.DeckFullException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.exceptions.PlacementConditionViolated;
import it.polimi.ingsw.listener.GameListenerInterface;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Book;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.model.interfaces.*;


import java.io.Serializable;
import java.util.List;

import static it.polimi.ingsw.network.PrintAsync.printAsync;

public class Player implements Serializable {

    private final String nickname;
    private PlayerState state;
    private final PlayerDeck playerDeck;
    private final Book playerBook;
    private ObjectiveCard playerGoal; //identifica l'obbiettivo che ha il player
    private boolean connected;
    private boolean readyToStart = false;
    private transient ArrayList<GameListenerInterface>listeners;

    public Player(String nickname) {
        this.nickname = nickname;
        this.playerGoal = null;
        this.state = PlayerState.Start; // Imposta lo stato iniziale a "Start"
        this.playerBook = new Book(40, 40); //ho messo 40 x 40 solo per verificare la correttezza del metodo, questo valore dobbiamo poi renderlo variabile in base al numero di giocatori
        this.playerDeck = new PlayerDeck();
        this.connected = false;
        this.listeners= new ArrayList<>();
    }
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
    public boolean isConnected() {
        return connected;
    }

    /**
     * @param connected sets the player's connection
     */
    public void setConnected(boolean connected) {
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
     * Sets the player as not ready to play
     */
    public void setNotReadyToStart() {
        readyToStart = false;
    }

    /**
     * Sets the state of the player
     *
     * @param state -> the actual state of the player
     */
    public void setPlayerState(PlayerState state) {
        this.state = state;
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
    public void setGoal(ObjectiveCard chosenCard) {
        this.playerGoal = chosenCard;
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

    /**
     * @return an ObjectiveCardIC, the personal goal of the player, an interface
     * that we send to the clients to make the model immutable
     *//*
    public ObjectiveCardIC getGoalIC() {
        return (ObjectiveCardIC) playerGoal; //controlla sia giusto fare il cast o se serve fare una COPIA
    }*/


    /**
     * @return a BookIC of the player, an interface
     * that we send to the clients to make the model immutable
     */
     // public BookIC getPlayerBookIC() {
      //  return (BookIC) playerBook;
     // }

    /**
     * @return the list of PlayableCardIC of the player deck, an interface
     * that we send to the clients to make the model immutable
     *//*
    public List<PlayableCardIC> getPlayerDeckIC() {
        List<PlayableCardIC> deckIC = new ArrayList<>();
        deckIC.addAll(playerDeck.miniDeck);
        return deckIC;
    }*/


    /**


    /**
     * Places a card at the specified position in the player's book.
     * taking the reference to the cell and card of the playerDeck chosen

     * @param rowCell the row of the cell in the book where the card should be placed.
     * @param rowCol  the column of the cell in the book where the card should be placed.
     * @param posCard the position of the card in the player's deck chosen to be placed on the chosen cell.
     * @return the victoryPoints of the placed card
     */
    public int placeCard(int posCard, int rowCell, int rowCol) throws PlacementConditionViolated, IndexOutOfBoundsException{
        ArrayList<Cell> availableCells = this.playerBook.showAvailableCells();
        Cell chosenCell = null;
        PlayableCard chosenCard= null;
        boolean found = false;


        for (Cell availableCell : availableCells) {
            if (availableCell.getRow() == rowCell && availableCell.getColumn() == rowCol) {
                chosenCell = availableCell;
                found = true;
            }
        }
        if (!found)
            throw new IndexOutOfBoundsException("Invalid cell position");

        // Check if the card position is valid
        ArrayList<PlayableCard> miniDeck = this.playerDeck.getMiniDeck();
        if (posCard < 0 || posCard >= miniDeck.size()) {
            throw new IndexOutOfBoundsException("Invalid card position");
        }

        // Retrieve the card at the specified position from the player's deck
        chosenCard = this.playerDeck.getMiniDeck().get(posCard);
        playerDeck.removeCard(posCard);

        // Place the chosen card on the chosen cell using the addCard method of the player's book
        return playerBook.addCard(chosenCard, chosenCell);

    }
    public synchronized void notify_requireInitial( Game model){
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.requireInitialReady(new GameImmutable(model));
            } catch (FileReadException | IOException e) {
                printAsync("During notification of notify_requireInitial, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }
    public synchronized void notify_cardsReady( Game model){
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.cardsReady(new GameImmutable(model), nickname);
            } catch ( IOException e) {
                printAsync("During notification of notify_requireInitial, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_requireGoals( Game model){
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                // Ottieni le carte obiettivo utilizzando il metodo drawObjectiveCards()
                l.requireGoalsReady(new GameImmutable(model));
            } catch (RemoteException | IllegalStateException e) {
                printAsync("During notification of notify_requireGoals, a disconnection has been detected before heartbeat");

            }
        }
    }

    /**
     * The notify_NotCorrectChosenCard method notifies that a player chose a card that cannot place in his Book
     * @param model is the Game to pass as a new GameModelImmutable
     */
    public synchronized void notify_NotCorrectChosenCard(Game model){
        Iterator<GameListenerInterface> i = listeners.iterator();
        while (i.hasNext()) {
            GameListenerInterface l = i.next();
            try {
                l.wrongChooseCard( new GameImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_NotCorrectChoosenCard, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }
}
