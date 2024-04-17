package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Book;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;

import java.util.ArrayList;

public class Player {

    private String nickname;
    private PlayerColor color;
    private PlayerState state;
    private PlayerDeck playerDeck;
    private Book playerBook;

    private ObjectiveCard playerGoal; //identifica l'obbiettivo che ha il player


    public Player(String nickname, PlayerColor color) {
        this.nickname = nickname;
        this.color = color;
        this.playerGoal = new ObjectiveCard();
        this.state = PlayerState.Start; // Imposta lo stato iniziale a "Start"
        this.playerBook = new Book(40, 40); //ho messo 40 x 40 solo per verificare la correttezza del metodo, questo valore dobbiamo poi renderlo variabile in base al numero di giocatori
        this.playerDeck = new PlayerDeck();
    };

    /**
     * Sets the nickname of the player.
     * @param nickname -> the nickname chosen by the player to rappresent him.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Retrieves the nickname of the player.
     * @return The nickname of the player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the color of the player -> the color rapresenting the player's piece.
     * @param color The color chosen by the player.
     */
    public void setColor(PlayerColor color) {
        this.color = color;
    }

    /**
     * Retrieves the color of the player rapresenting the color of the player's piece.
     * @return The color of the player.
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Sets the state of the player
     * @param state -> the actual state of the player
     */
    public void setPlayerState(PlayerState state) {
        this.state = state;
    }

    /**
     * Retrieves the state of the player.
     * @return The state of the player.
     */
    public PlayerState getPlayerState() {
        return this.state;
    }

    /**
     * Sets the player's Objective card rapresenting the player's goal.
     * @param chosenCard is the objective card chosen between 2 Objective Cards
     */
    public void setGoal(ObjectiveCard chosenCard) {
        this.playerGoal=  chosenCard;
    }

   public ObjectiveCard getGoal(){
        return this.playerGoal;
   }

   public Book getPlayerBook(){
        return this.playerBook;
   }

   public PlayerDeck getPlayerDeck(){
        return this.playerDeck;
   }




    /**
     * Picks a card from the board and adds it to the player's deck.
     *
     * @param board         The board from which to pick the card.
     * @param cardType      The type of card to pick.
     * @param drawFromDeck  Indicates whether to draw the card from the deck or the discard pile.
     * @param pos           The position of the card to pick from the board.
     * @throws IllegalArgumentException If the specified card type is invalid.
     * @throws IndexOutOfBoundsException If the position is out of range or the card cannot be picked.
     */
    public void pickCard(Board board, CardType cardType, boolean drawFromDeck, int pos) throws IllegalArgumentException, IndexOutOfBoundsException {
        // Take a card from the board
        PlayableCard[] pickedCard;
        try {
            pickedCard = board.takeCardfromBoard(cardType, drawFromDeck, pos);
        } catch (IllegalArgumentException e) {
            // Handle invalid card type exception
            throw new IllegalArgumentException("Invalid card type", e);
        } catch (IndexOutOfBoundsException e) {
            // Handle out of range or unpickable card exception
            throw new IndexOutOfBoundsException("Position is out of range or the card cannot be picked");
        }

        // Check if a card is successfully picked
        if (pickedCard != null) {
            // Add the picked card to the player's deck
            try {
                playerDeck.addCard(pickedCard);
            } catch (IllegalArgumentException e) {
                // Handle invalid card addition exception
                throw new IllegalArgumentException("Invalid card addition to the player's deck", e);
            }
        }
    }

    /**
     * Chooses a cell from the available positions shown by the player's book.
     *
     * @param pos The position of the cell to choose.
     * @return The chosen cell.
     * @throws IndexOutOfBoundsException If the position is out of range.
     */
    public Cell chooseCell(int pos) throws IndexOutOfBoundsException {
        // Get the available positions from the player's book
        ArrayList<Cell> availablePositions = playerBook.showAvailablePositions();

        // Verify if the position is valid
        if (pos < 0 || pos >= availablePositions.size()) {
            throw new IndexOutOfBoundsException("Position is out of range");
        }

        // Get the cell at the specified position
        Cell chosenCell = availablePositions.get(pos);

        // Return the chosen cell
        return chosenCell;
    }
    /**@author Sofia Maule
     * Chooses a card from the player's deck at the specified position,
     * removes it from the playerDeck and returns it.
     *
     * @param pos The position of the card to choose.
     * @return The card from the player's deck at the specified position,
     * or null if the deck is empty or the position is invalid.
     */

    public PlayableCard chooseCard(int pos) throws IndexOutOfBoundsException {
        // Verifies if the position is valid
        if (pos < 0 || pos >= playerDeck.getNumCards()) {
            throw new IndexOutOfBoundsException("Position is out of range");
        }

        // Retrieve the card at the specified position from the player's deck
        PlayableCard chosenCard = playerDeck.getMiniDeck().get(pos);

        // Remove the chosen card from the player's deck
        try {
            playerDeck.removeCard(pos);
        } catch (IndexOutOfBoundsException e) {
            // Handle the exception if the position is out of bounds after removing the card
            throw new IndexOutOfBoundsException("Position is out of range after card removal");
        }

        // Return the chosen card
        return chosenCard;
    }

    /*
     DA RIVEDERE PERCHè LA CELLA E LA CARTA DA PRENDERE DAL PLAYERDECK DEVONO
     ESSERE PASSATI COME PARAMETRI IN INPUT (DALLA VIEW) ????
     */
    public int placeCard(int posCell, int posCard) throws IndexOutOfBoundsException {
        // Choose a cell and a card
        Cell chosenCell = chooseCell(posCell); // Choose the first cell
        PlayableCard chosenCard = chooseCard(posCard); // Choose the first card

        // Place the chosen card on the chosen cell using the addCard method of the player's book
        return playerBook.addCard(chosenCard, chosenCell);
    }

    public int checkGoal(Board board){}
    /* in base al tipo di obbiettivo della Objective Card chiama i metodi del book per controllare se è verificato
        restituisce i punti fatti
        è gia nel BOOK (CONTROLLA)     */

    //GESTISCI PLAYERSTATE

}
