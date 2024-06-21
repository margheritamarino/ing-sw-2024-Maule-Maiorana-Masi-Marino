package it.polimi.ingsw.view.Utilities;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


/**
 * User Interface
 * This class is used also for TUI and GUI
 */
public abstract class UI {
    protected List<String> eventsToShow;

    /**
     * Initialize GUI or TUI
     */
    public abstract void init();

    /**
     * Shows the publisher.
     *
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public abstract void show_publisher() throws IOException, InterruptedException;

    /**
     * Shows a message asking the player to place cards.
     *
     * @param model the game model
     */
    public abstract void show_askPlaceCardsMainMsg(GameImmutable model);

    /**
     * Shows a message to pick a card.
     *
     * @param model the game model
     */
    public abstract void show_PickCardMsg(GameImmutable model);

    /**
     * Shows a message asking the player to choose a card type.
     *
     * @param model the game model
     * @param nickname the player's nickname
     */
    public abstract void show_askCardType(GameImmutable model, String nickname);

    /**
     * Shows the always visible elements of the game for a specific player.
     *
     * @param model the game model
     * @param nickname the player's nickname
     */
    public abstract void show_alwaysShow(GameImmutable model, String nickname);

    /**
     * Shows a message asking the player to choose a cell.
     *
     * @param model the game model
     */
    public abstract void show_askWhichCellMsg(GameImmutable model);

    /**
     * Shows a message indicating a card has been placed.
     *
     * @param model the game model
     */
    public abstract void show_cardPlacedMsg(GameImmutable model);

    /**
     * Shows a message indicating a card has been drawn.
     *
     * @param model the game model
     * @param nickname the player's nickname
     */
    public abstract void show_cardDrawnMsg(GameImmutable model, String nickname);

    /**
     * Shows a message indicating the next turn.
     *
     * @param model the game model
     */
    public abstract void show_nextTurnMsg(GameImmutable model);

    /**
     * Shows a message indicating points have been added.
     *
     * @param model the game model
     * @param nickname the player's nickname
     */
    public abstract void show_pointsAddedMsg(GameImmutable model, String nickname);

    /**
     * Shows a message indicating a player has joined the game.
     *
     * @param nick the player's nickname
     * @param color the player's color
     */
    public abstract void show_joiningToGameMsg(String nick, Color color);

    /**
     * Shows the player's book.
     *
     * @param model the game model
     */
    public abstract void show_playerBook(GameImmutable model);

    /**
     * Shows the score track.
     *
     * @param model the game model
     */
    public abstract void show_scoretrack(GameImmutable model);

    /**
     * Shows the game board.
     *
     * @param model the game model
     */
    public abstract void show_board(GameImmutable model);

    /**
     * Asks the player for their nickname.
     */
    public abstract void show_insertNicknameMessage();

    /**
     * Shows the player's chosen nickname.
     *
     * @param nickname The nickname just chosen by the player.
     */
    public abstract void show_chosenNickname(String nickname);

    /**
     * Shows a message indicating the current turn.
     *
     * @param model the game model
     */
    public abstract void show_CurrentTurnMsg(GameImmutable model);

    /**
     * Shows game started message.
     *
     * @param model The model where the game has started.
     */
    public abstract void show_gameStarted(GameImmutable model);

    /**
     * Shows game ended message.
     *
     * @param model the game model
     */
    public abstract void show_gameEnded(GameImmutable model);

    /**
     * Shows a message asking for the number of players.
     */
    public abstract void show_askNumPlayersMessage();

    /**
     * Shows a message asking for the game ID.
     */
    public abstract void show_askGameIDMessage();

    /**
     * Shows a message indicating an invalid action or input.
     */
    public abstract void show_notValidMessage();

    /**
     * Shows a message indicating a player has joined the game.
     *
     * @param gameModel the game model
     * @param nick the player's nickname
     * @param color the player's color
     */
    public abstract void show_playerJoined(GameImmutable gameModel, String nick, Color color);

    /**
     * Shows all players in the game.
     *
     * @param model the game model
     */
    public abstract void show_allPlayers(GameImmutable model);

    /**
     * Shows a message indicating the player is ready.
     *
     * @param model the game model
     */
    public abstract void show_youAreReady(GameImmutable model);

    /**
     * Shows a message indicating the player is ready to start.
     *
     * @param gameModel the game model
     * @param nickname the player's nickname
     */
    public abstract void show_readyToStart(GameImmutable gameModel, String nickname);

    /**
     * Shows a message to return to the menu.
     */
    public abstract void show_returnToMenuMsg();

    /**
     * Shows a message indicating a wrong card selection.
     */
    public abstract void show_wrongCardSelMsg();

    /**
     * Shows a message indicating a wrong cell selection.
     */
    public abstract void show_wrongCellSelMsg();

    /**
     * Shows a message asking for a number input.
     *
     * @param msg the message to display
     * @param gameModel the game model
     * @param nickname the player's nickname
     */
    public abstract void show_askNum(String msg, GameImmutable gameModel, String nickname);

    /**
     * Shows a message for an important event added.
     *
     * @param input The string of the important event to add.
     */
    public abstract void addImportantEvent(String input);

    /**
     * Resets the important events.
     */
    public abstract void resetImportantEvents();

    /**
     * Shows a message asking the player to draw from the deck.
     *
     * @param model the game model
     * @param nickname the player's nickname
     */
    public abstract void show_askDrawFromDeck(GameImmutable model, String nickname);

    /**
     * Shows a message indicating the player has to choose again.
     *
     * @param model the game model
     * @param nickname the player's nickname
     * @param msg the message to display
     */
    public abstract void show_playerHasToChooseAgain(GameImmutable model, String nickname, String msg);

    /**
     * Shows a message indicating a wrong selection.
     */
    public abstract void show_wrongSelectionMsg();

    /**
     * Shows the temporary initial cards for a player.
     *
     * @param model the game model
     * @param indexPlayer the index of the player
     * @throws FileNotFoundException if the file is not found
     * @throws FileReadException if there is an error reading the file
     */
    public abstract void show_temporaryInitialCards(GameImmutable model, int indexPlayer) throws FileNotFoundException, FileReadException;

    /**
     * Shows the objective cards for a player.
     *
     * @param model the game model
     * @param indexPlayer the index of the player
     */
    public abstract void show_ObjectiveCards(GameImmutable model, int indexPlayer);

    /**
     * Shows the personal objective of a player.
     *
     * @param model the game model
     * @param indexPlayer the index of the player
     */
    public abstract void show_personalObjective(GameImmutable model, int indexPlayer);

    /**
     * Shows a message indicating no connection error.
     */
    public abstract void show_noConnectionError();

    /**
     * Shows the player's deck.
     *
     * @param model the game model
     * @param nickname the player's nickname
     */
    public abstract void show_playerDeck(GameImmutable model, String nickname);

    /**
     * Shows a message indicating the player should wait for their turn.
     *
     * @param model the game model
     * @param nickname the player's nickname
     */
    public abstract void show_WaitTurnMsg(GameImmutable model, String nickname);

    /**
     * Shows the visible cards on the board.
     *
     * @param model the game model
     * @param cardType the type of card to display
     */
    public abstract void show_visibleCardsBoard(GameImmutable model, CardType cardType);

    /**
     * Shows a message indicating a message has been sent.
     *
     * @param model the game model
     * @param nickname the player's nickname
     */
    public abstract void show_sentMessage(GameImmutable model, String nickname);

    /**
     * Shows a message asking the player if they want to chat.
     *
     * @param model the game model
     * @param nick the player's nickname
     */
    public abstract void show_askForChat(GameImmutable model, String nick);

    /**
     * Adds a message to the game.
     *
     * @param msg the message to add
     * @param model the game model
     */
     public abstract void addMessage(Message msg, GameImmutable model);

    /**
     * Closes the wait popup.
     */
    public abstract void closeWaitPopUp();

    /**
     * Show the message for next turn or reconnected player
     * @param model model where events happen
     * @param nick the player's nickname
     */
    public abstract void show_PlayerReconnectedMsg(GameImmutable model, String nick, String lastPlayerReconnected);

    /**
     * Shows a message indicating a failed reconnection attempt.
     *
     * @param nick the player's nickname
     */
    public abstract void show_failedReconnectionMsg( String nick);

    /**
     * Show the message if a client is trying a reconnection
     */
    public abstract void show_askForReconnection();

}
