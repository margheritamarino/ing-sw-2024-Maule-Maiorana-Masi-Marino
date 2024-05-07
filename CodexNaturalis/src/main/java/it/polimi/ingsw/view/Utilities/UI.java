package it.polimi.ingsw.view.Utilities;

import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameImmutable;

import java.io.FileNotFoundException;
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

    public abstract void show_askPlaceCardsMainMsg(GameImmutable model);
    public abstract void show_alwaysShow(GameImmutable model, String nickname);
    public abstract void show_alwaysShowForAll(GameImmutable model);
    public abstract void show_askWhichCellMsg(GameImmutable model);
    public abstract void show_cardPlacedMsg(GameImmutable model);
    public abstract void show_pointsAddedMsg(GameImmutable model);
    public abstract void show_joiningToGameMsg(String nick);


    //show per mostrare come unirsi al gioco
    public abstract void show_menuOptions();

    public abstract void show_creatingNewGameMsg(String nickname);
    /**
     * Shows the join first available game message
     * @param nickname player's nickname
     */
    protected abstract void show_joiningFirstAvailableMsg(String nickname);


    /**
     * Asks the player for his nickname
     */
    public abstract void show_insertNicknameMessage();

    /**
     * Shows the player's chosen nickname
     *
     * @param nickname nickname just chosen by the player
     */
    public abstract void show_chosenNickname(String nickname);

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
    public abstract void show_gameStarted(GameImmutable model) throws IOException, InterruptedException;
    /**
     * Shows the playerDeck
     * @param model
     */
    protected abstract void show_playerDeck(GameImmutable model);
    /**
     * Shows the game ended message
     * @param model where the game is ended
     */
    public abstract void show_gameEnded(GameImmutable model);


    public abstract void show_notValidMessage();

    public abstract void show_temporaryInitialCards(GameImmutable model) throws FileNotFoundException, FileReadException;
    protected abstract void show_ObjectiveCards(GameImmutable model);

    public abstract void show_playerJoined(GameImmutable gameModel, String nick);
    public abstract void show_allPlayers(GameImmutable model);
    public abstract void show_youAreReady(GameImmutable model);

    /**
     * Shows that the playing player is ready to start
     *
     * @param gameModel     model where events happen
     * @param nickname player's nickname
     */
    public abstract void show_readyToStart(GameImmutable gameModel, String nickname);

    /**
     * Show the message for next turn or reconnected player
     *
     * @param model    model where events happen
     * @param nickname nick of reconnected player (or of the player that is now in turn)
     */
    protected abstract void show_nextTurnOrPlayerReconnected(GameImmutable model, String nickname);


    //SHOW DEI METODI ASK

    public abstract void show_returnToMenuMsg();

    protected abstract void show_askNum(String msg, GameImmutable gameModel, String nickname);
    public abstract void show_whichInitialCards();

    public abstract void show_whichObjectiveCards();

    public void show_insertGameIDMessage() {
    }

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



    //METODI CON AZIONI
    /**
     * Shows message on important event added
     * @param input the string of the important event to add
     */
    public abstract void addImportantEvent(String input);

    /**
     * Resets the important events
     */
    public abstract void resetImportantEvents();

    /**
     * Shows an error when there's no connection
     */
    protected abstract void show_noConnectionError();


    public abstract void show_wrongSelectionInitialMsg();

    public abstract void show_wrongSelectionObjectiveMsg();




}
