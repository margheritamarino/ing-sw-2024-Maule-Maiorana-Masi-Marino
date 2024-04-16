package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Book;
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


    /**@author Sofia Maule
     * Chooses a card from the player's deck at the specified position,
     * removes it from the playerDeck and returns it.
     *
     * @param pos The position of the card to choose.
     * @return The card from the player's deck at the specified position,
     * or null if the deck is empty or the position is invalid.
     */
    /* CONTROLLA SE SERVE -> può farlo direttamente controller
    *  DA CORREGGERE!!
    *    */
    public PlayableCard chooseCard(int pos) {
        ArrayList<PlayableCard> miniDeck = playerDeck.getMiniDeck();

        // Verifies if the deck contains cards and if the position is valid
        if (miniDeck.isEmpty() || pos < 0 || pos >= miniDeck.size()) {
            return null; // Returns null if the deck is empty or the position is invalid
        }

        // Gets the card from the deck at the specified position
        PlayableCard chosenCard = miniDeck.get(pos);

        // Removes the chosen card from the mini deck
        playerDeck.removeCard(chosenCard);
        return chosenCard;
    }

    /* CONTROLLA SE SERVE -> può farlo direttamente controller
    *
    *  DA CORREGGERE!!!!
    *  */
    public void pickCard(Board board, CardType cardType, boolean drawFromDeck, int pos){
        PlayableCard pickedCard = board.takeCardfromBoard(cardType, drawFromDeck, pos);
        if (pickedCard != null) {
            playerDeck.addCard(pickedCard);
        }
    }

    public void placeCard(PlayableCard chosenCard){

    }




}
