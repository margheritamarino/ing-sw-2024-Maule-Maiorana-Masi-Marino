package it.polimi.ingsw.model.interfaces;

import it.polimi.ingsw.model.Book;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.player.PlayerDeck;
import it.polimi.ingsw.model.player.PlayerState;

public interface PlayerIC {
    /**
     * This method is used to get the nickname of the player
     * @return the nickname of the player
     */
    String getNickname();

    /**
     * This method is used to get the state of the player
     * @return the state of the player.
     */
    public PlayerState getPlayerState();

    /**
     * This method check if the player is ready to start
     * @return true if the player is ready to start
     */
    boolean getReadyToStart();

    /**
     * This method check if the player is connected
     * @return true if the player is connected
     */
    boolean isConnected();

    /**
     * @return the objective card of the player  {@link ObjectiveCardIC}, his personal Goal
     */
    public ObjectiveCardIC getGoalIC();

    /**
     * @return the book of the player  {@link BookIC}
     */
    public BookIC getPlayerBookIC();

    /**
     * @return the 'playerDeck' of the player  {@link PlayerDeckIC}
     */
    public PlayerDeckIC getPlayerDeckIC();
}
