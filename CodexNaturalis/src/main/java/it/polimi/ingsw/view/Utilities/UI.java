package it.polimi.ingsw.view.Utilities;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.IOException;
import java.util.List;

//Definisce l'interfaccia per la visualizzazione del gioco comune che servirà sia per la TUI che per la GUI

/**
 * User Interface
 * This class is used also for TUI and GUI
 */
public abstract class UI {
    protected List<String> eventsToShow; //eventi che devono essere mostrati sullo schermo


    /**
     * Initialize GUI or TUI
     */
    public abstract void init();

    /**
     * Show's Cranio Creation's logo
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public abstract void show_publisher() throws IOException, InterruptedException;

    /**
     * Shows the join first available game message
     * @param nickname player's nickname
     */
    protected abstract void show_joiningFirstAvailableMsg(String nickname);

    /**
     * Shows the join to specific game message
     * @param idGame   id of the game the player is trying to join
     * @param nickname player's nickname
     */
    protected abstract void show_joiningToGameIdMsg(int idGame, String nickname);

    /**
     * Shows the creating new game message
     * @param nickname player's nickname
     */
    protected abstract void show_creatingNewGameMessage(String nickname);

    protected abstract void show_temporaryInitialCards(GameImmutable model);

    public abstract void show_whichInitialCards();


    /**
     * Message that asks to insert specific game id
     */
    protected abstract void show_inputGameIdMessage();

    /**
     * Asks the player for his nickname
     */
    protected abstract void show_insertNicknameMessage();

    /**
     * Shows the player's chosen nickname
     *
     * @param nickname nickname just chosen by the player
     */
    protected abstract void show_chosenNickname(String nickname);

    /**
     * Shows error message when there are no games available for joining
     *
     * @param msg message that needs visualisation
     */
    protected abstract void show_noAvailableGamesToJoin(String msg);

    /**
     * Asks the player for number of players for a new game
     */
    protected abstract void show_insertPlayersNumber();

    /**
     * Shows game started message
     * @param model model where the game has started
     */
    protected abstract void show_gameStarted(GameImmutable model);

    /**
     * Shows the playerDeck
     * @param model
     */
    protected abstract void show_playerDeck(GameImmutable model);
    /**
     * Shows the game ended message
     * @param model where the game is ended
     */
    protected abstract void show_gameEnded(GameImmutable model);

    /**
     * Shows the players that have joined
     *
     * @param gameModel model where events happen
     * @param nick      player's nickname
     */
    public abstract void show_playerJoined(GameImmutable gameModel, String nick);

    /**
     * Shows that the playing player is ready to start
     *
     * @param gameModel     model where events happen
     * @param nicknameofyou player's nickname
     */
    protected abstract void show_youReadyToStart(GameImmutable gameModel, String nicknameofyou);

    /**
     * Show the message for next turn or reconnected player
     *
     * @param model    model where events happen
     * @param nickname nick of reconnected player (or of the player that is now in turn)
     */
    protected abstract void show_nextTurnOrPlayerReconnected(GameImmutable model, String nickname);

    /**
     * Show the message for pick cell
     */
    protected abstract void show_pickCellRequest();

    /**
     * Show the message for pick card
     */
    protected abstract void show_pickCardRequest();

    /**
     * Show the message for pick deck
     */
    protected abstract void show_pickDeckRequest();

    /**
     * Show the message for choosing Array Position
     */
    protected abstract void show_chooseArrayPosition();

    /**
     * Show reset information game
     */
    protected abstract void show_InformationGame();

//other
    // es show_addedPoints

    /**
     * Shows an error when there's no connection
     */
    protected abstract void show_noConnectionError();

    /**
     * Shows message on important event added
     * @param input the string of the important event to add
     */
    public abstract void addImportantEvent(String input);

}
