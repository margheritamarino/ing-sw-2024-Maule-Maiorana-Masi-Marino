package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.ColorConsole;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.player.Player;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.view.Utilities.UI;

import java.io.IOException;
import java.util.ArrayList;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
import static it.polimi.ingsw.view.TUI.PrintAsync.printAsyncNoCursorReset;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;


/**
 * TUI (Textual User Interface) class extending the UI class.
 */
public class TUI extends UI {

    private String nickname;
    private boolean alreadyShowedLobby;

    /**
     * Constructor
     * Initializes the TUI instance and calls the init method.
     */
    public TUI(){
        init();
    }

    /**
     * Initializes the TUI instance by setting up the ANSI console and initializing the eventsToShow list.
     */
    @Override
    public void init() {
        AnsiConsole.systemInstall();
        eventsToShow = new ArrayList<>();
    }

    /**
     * Displays a welcome message to the user with their nickname.
     *
     * @param nick the nickname of the user
     */
    public void show_welcome(String nick) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("*** WELCOME " + nick + "!***");
    }

    /**
     * Displays all players' information.
     *
     * @param model the GameImmutable instance containing game data
     */
    public void show_allPlayers(GameImmutable model) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync( model.toStringListPlayers());

    }

    /**
     * Displays the order of the players.
     *
     * @param model the GameImmutable instance containing game data
     */
    public void show_OrderPlayers(GameImmutable model) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("The ORDER of the players is: \n" + model.toStringListOrderArray());

    }


    /**
     * Continuously displays the player's deck and book.
     *
     * @param model the GameImmutable instance containing game data
     * @param nick the nickname of the player
     */
    public void show_alwaysShow(GameImmutable model, String nick) {
        show_playerDeck(model, nick);
        show_playerBook(model);
    }

    /**
     * Displays the publisher information.
     */
    @Override
    public void show_publisher(){
        clearScreen();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printAsync (ColorConsole.YELLOW.getCode() +
                "  +-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+\n" +
                " |C|R|A|N|I|O| |C|R|E|A|T|I|O|N|S|\n" +
                " +-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+\n"
                + ColorConsole.RESET.getCode());

        try {
            Thread.sleep(DefaultValue.time_publisher_showing_seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.show_titleCodexNaturalis();
    }

    /**
     * Prints title of the game
     */
    public void show_titleCodexNaturalis(){
         try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync(ColorConsole.GREEN_BRIGHT.getCode()+

                " ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗\n" +
                "██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝\n" +
                "██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗\n" +
                "██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║\n" +
                "╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║\n" +
                " ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝\n" +
                "                                                                                                                    \n"
      + ColorConsole.RESET.getCode());

    }

    /**
     * Displays the score track of the game.
     *
     * @param model the GameImmutable instance containing game data
     */
    @Override
    public void show_scoretrack(GameImmutable model) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync(model.getScoretrack().toString());
    }

    /**
     * Displays the current state of the game board.
     *
     * @param model the GameImmutable instance containing game data
     */
    @Override
    public void show_board(GameImmutable model) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String boldStart = "\u001B[1m";
        String boldEnd = "\u001B[0m";

        System.out.print("***********BOARD***********\n");
        System.out.print("\n");

        // GOLDCARDS
        System.out.print(boldStart + "***GOLDCARDS***: \n" + boldEnd);
        System.out.print("\n");
        System.out.print(model.getBoard().cardsGoldToString());
        System.out.print("\n");

        // RESOURCECARDS
        System.out.print(boldStart + "***RESOURCECARDS***: \n" + boldEnd);
        System.out.print("\n");
        System.out.print(model.getBoard().cardsResourceToString());
        System.out.print("\n");

        // OBJECTIVE
        System.out.print(boldStart + "*******COMMON GOALS*******: \n" + boldEnd);
        System.out.print("\n");
        System.out.print(model.getBoard().cardsObjectiveToString());
        System.out.print("\n");
    }

    /**
     * Displays the player's book.
     *
     * @param model the GameImmutable instance containing game data
     */
    @Override
    public void show_playerBook(GameImmutable model) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync(model.getCurrentPlayer().getPlayerBook().toString());
    }

    /**
     * Displays the player's deck.
     *
     * @param model the GameImmutable instance containing game data
     * @param nick the nickname of the player
     */
    @Override
    public void show_playerDeck(GameImmutable model, String nick) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String boldStart = "\u001B[1m";
        String boldEnd = "\u001B[0m";

        printAsync(boldStart + "*** YOUR DECK ***" + boldEnd);

        Player p = model.getPlayerByNickname(nick);
        printAsync(p.getPlayerDeck().toString());
    }

    /**
     * Displays a message asking for a number input.
     *
     * @param msg the message to display
     * @param model the GameImmutable instance containing game data
     * @param nickname the nickname of the player
     */
    @Override
    public void show_askNum(String msg, GameImmutable model, String nickname) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync(msg);
    }

    /**
     * Displays a message asking for the number of players in the game.
     */
    @Override
    public void show_askNumPlayersMessage(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Insert the number of the players in the Game: ");
    }

    /**
     * Displays a message asking for the game ID.
     */
    @Override
    public void show_askGameIDMessage(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Insert the GameID: ");
    }

    /**
     * Displays a message indicating that the input is not valid.
     */
    @Override
    public void show_notValidMessage(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Not valid input. Please try again. ");
    }

    /**
     * Displays a message indicating that a player has joined the game.
     *
     * @param gameModel the GameImmutable instance containing game data
     * @param nick the nickname of the player who joined
     * @param color the color associated with the player
     */
    @Override
    public void show_playerJoined(GameImmutable gameModel, String nick, Color color) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!alreadyShowedLobby) {
            clearScreen();
            show_welcome(nick);
            printAsync("GameID: [" + gameModel.getGameId().toString() + "]");
            printAsync("Players in the LOBBY:");
            show_allPlayers(gameModel);
        }else{
            add_newPlayer(gameModel);
        }
        System.out.flush();
    }

    /**
     * Adds a new player to the list of players displayed.
     *
     * @param gameModel the GameImmutable instance containing game data
     */
    private void add_newPlayer(GameImmutable gameModel) {
        Player newPlayer = gameModel.getLastPlayer();
        if (newPlayer != null) {
            printAsync("[# " + gameModel.getPlayers().size() + "]: " + newPlayer.getNickname());
        }
    }

    /**
     * Displays a message indicating that the player is ready to start the game.
     *
     * @param gameModel the GameImmutable instance containing game data
     * @param nickname the nickname of the player
     */
    @Override
    public void show_readyToStart(GameImmutable gameModel, String nickname) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Press y if you are ready to start the game");
    }

    /**
     * Displays a message indicating that the player is ready to start the game and shows the players ready in the lobby.
     *
     * @param model the GameImmutable instance containing game data
     */
    @Override
    public void show_youAreReady(GameImmutable model){
        printAsync("You are ready to start the game!\n PLAYERS READY in the LOBBY:");
        printAsync( model.toStringListPlayersReady());

    }

    /**
     * Displays a message indicating that the selection is invalid.
     */
    @Override
    public void show_wrongSelectionMsg() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Invalid selection. Please choose 0 or 1.");
    }

    /**
     * Displays a message indicating that the card selection is invalid.
     */
    @Override
    public void show_wrongCardSelMsg() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Invalid selection. Please choose a number between 0 and 6.");
    }

    /**
     * Displays a message indicating that the cell selection is invalid.
     */
    @Override
    public void show_wrongCellSelMsg() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Invalid selection. Please choose a number between: "+ DefaultValue.BookSizeMin+ " and " + DefaultValue.BookSizeMax);
    }

    /**
     * Displays the personal objective of a player.
     *
     * @param model the GameImmutable instance containing game data
     * @param indexPlayer the index of the player
     */
    @Override
    public void show_personalObjective(GameImmutable model, int indexPlayer){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the console
     */
    public void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a message indicating that the current player should wait for their turn.
     *
     * @param model the GameImmutable instance containing game data
     * @param nickname the nickname of the player who should wait
     */
    @Override
    public void show_WaitTurnMsg(GameImmutable model, String nickname) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("WAIT! It's "+ model.getCurrentPlayer().getNickname()+" TURN\n");

    }

    /**
     * Displays a message indicating that it is the player's turn.
     *
     * @param model the GameImmutable instance containing game data
     */
    @Override
    public void show_CurrentTurnMsg(GameImmutable model){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("It's your TURN!\n");
    }

    /**
     * Displays a message indicating that the game has started.
     *
     * @param model the GameImmutable instance containing game data
     */
    @Override
    public void show_gameStarted(GameImmutable model) {
        this.clearScreen();
        this.show_publisher();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync(ansi().fg(DEFAULT).a("***GAME STARTED!***"));
        show_OrderPlayers(model);
        show_board(model);
        show_scoretrack(model);
    }

    /**
     * Displays a message asking the player to place a card.
     *
     * @param model the GameImmutable instance containing game data
     */
    @Override
    public void show_askPlaceCardsMainMsg(GameImmutable model){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("It's your turn to PLACE A CARD" );
    }

    /**
     * Displays a message asking the player to pick a card.
     *
     * @param model the GameImmutable instance containing game data
     */
    @Override
    public void show_PickCardMsg(GameImmutable model){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("It's your turn to PICK A CARD " );
        show_board(model);
    }

    /**
     * Displays a message asking the player to choose the type of card to pick.
     *
     * @param model the GameImmutable instance containing game data
     * @param nickname the nickname of the player
     */
    @Override
    public void show_askCardType(GameImmutable model, String nickname){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Which card do you want to pick? Press R(r) if you want a Resource card or G(g) if you want a Gold card:" );
    }

    /**
     * Displays a message asking the player if they want to draw from the deck.
     *
     * @param model the GameImmutable instance containing game data
     * @param nickname the nickname of the player
     */
    @Override
    public void show_askDrawFromDeck(GameImmutable model, String nickname){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Press Y if you want to draw from deck or N if you want a visible card:" );
    }

    /**
     * Displays a message asking the player to choose a cell in the book to place the card.
     *
     * @param model the GameImmutable instance containing game data
     */
    @Override
    public void show_askWhichCellMsg(GameImmutable model){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Choose a Cell in the book to place the card:" );
    }

    /**
     * Notifies the player that they need to choose again and displays an error message.
     *
     * @param model   the immutable game model containing the current state of the game
     * @param nickname the nickname of the player who needs to choose again
     * @param msg     the error message to be displayed
     */
    @Override
    public void show_playerHasToChooseAgain(GameImmutable model, String nickname, String msg){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("ERROR: " + msg );

    }

    /**
     * Displays a message indicating that the current player has placed a card.
     *
     * @param model the immutable game model containing the current state of the game
     */
    @Override
    public void show_cardPlacedMsg(GameImmutable model){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync( model.getCurrentPlayer().getNickname() + " has placed a card!\n" );
    }

    /**
     * Displays a message indicating that it is the next player's turn.
     *
     * @param model the immutable game model containing the current state of the game
     */
    @Override
    public void show_nextTurnMsg(GameImmutable model){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Next turn: " + model.getCurrentPlayer().getNickname());
    }

    /**
     * Displays a message indicating the card drawn by the player.
     *
     * @param model    the immutable game model containing the current state of the game
     * @param nickname the nickname of the player who drew the card
     */
    @Override
    public void show_cardDrawnMsg(GameImmutable model, String nickname){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       ArrayList<PlayableCard[]> minideck= model.getCurrentPlayer().getPlayerDeck().getMiniDeck();
        printAsync( "This is your drawn card:\n" + minideck.get(2)[0].toString() + minideck.get(2)[1].toString());

    }

    /**
     * Displays a message indicating that points have been added to a player's score.
     *
     * @param model    the immutable game model containing the current state of the game
     * @param nickname the nickname of the player who scored points
     */
    @Override
    public void show_pointsAddedMsg(GameImmutable model, String nickname){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(model.getCurrentPlayer().equals(nickname) )
            printAsync("You scored " + model.getCurrentCardPoints()+ "points!");
        else
            printAsync(model.getCurrentPlayer().getNickname() + " scored " + model.getCurrentCardPoints()+ " points!");
        printAsync("NEW TOTAL SCORE");
        show_scoretrack(model);
    }

    /**
     * Displays a message indicating that a player is trying to join a game.
     *
     * @param nickname the nickname of the player attempting to join
     * @param color    the color associated with the player
     */
    @Override
    public void show_joiningToGameMsg(String nickname, Color color){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Trying to join a game...\n");
    }

    /**
     * Adds an important event to the list and displays all important events.
     *
     * @param input the important event to add
     */
    @Override
    public void addImportantEvent(String input) {
        if (eventsToShow.size() + 1 >= DefaultValue.MaxEventToShow) {
            eventsToShow.removeFirst();
        }
        eventsToShow.add(input);
        show_important_events();
    }

    /**
     * Resets the list of important events and clears the nickname.
     */
    @Override
    public void resetImportantEvents() {
        this.eventsToShow = new ArrayList<>();
        this.nickname = null;
    }

    /**
     * Shows the important events
     */
    public void show_important_events() {
        StringBuilder ris = new StringBuilder();
        int longestImportantEvent = eventsToShow.stream().map(String::length).reduce(0, (a, b) -> a > b ? a : b);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ris.append(ansi().fg(GREEN).bold().a("Latest Events:").fg(DEFAULT).boldOff());
        for (String s : eventsToShow) {
            ris.append(ansi().fg(Ansi.Color.WHITE).a(s).a(" ".repeat(longestImportantEvent - s.length())).fg(DEFAULT));
        }
        printAsync(ris);
    }

    /**
     * Displays a message prompting the user to insert their nickname.
     */
    @Override
    public void show_insertNicknameMessage(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Insert your nickname: \n");
    }


    /**
     * Displays a message showing the chosen nickname.
     *
     * @param nickname the nickname chosen by the user
     */
    @Override
    public void show_chosenNickname(String nickname) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Your nickname is " + nickname );
    }

    /**
     * Displays a message indicating that the game has ended and resets the screen.
     *
     * @param model the immutable game model containing the current state of the game
     */
    @Override
    public void show_gameEnded(GameImmutable model) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearScreen();
        show_titleCodexNaturalis();
        printAsync("***GAME ENDED***\n");

    }

    /**
     * Displays a message indicating that the user has returned to the menu.
     */
    @Override
    public void show_returnToMenuMsg() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("You are back in the menu!\n");
    }

    /**
     * Displays the temporary initial cards for a player and prompts them to choose one.
     *
     * @param model       the immutable game model containing the current state of the game
     * @param indexPlayer the index of the player whose initial cards are being shown
     */
    @Override
    public void show_temporaryInitialCards(GameImmutable model, int indexPlayer) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printAsync("> Choose the front[0] or the back[1] of the following initial card to place it at the center of your book:");
        PlayableCard[] initialCards = model.getInitialCard().get(indexPlayer);

        if (initialCards.length != 2) {
            printAsync("Error: Expected exactly 2 initial cards.");
            return;
        }

        String card0 = initialCards[0].toString();
        String card1 = initialCards[1].toString();

        String[] lines0 = card0.split("\n");
        String[] lines1 = card1.split("\n");


        StringBuilder result = new StringBuilder();
        int height = Math.max(lines0.length, lines1.length);

        for (int i = 0; i < height; i++) {
            if (i == 0) {
                result.append("[0] ").append(lines0[i])
                        .append("   ")
                        .append("[1] ").append(lines1[i])
                        .append("\n");
            } else {
                String line0 = lines0[i];
                String line1 = lines1[i];

                int maxLength = Math.max(line0.length(), line1.length());
                line0 = line0 + " ".repeat(maxLength - line0.length());
                line1 = line1 + " ".repeat(maxLength - line1.length());

                result.append("    ").append(line0)
                        .append("   ")
                        .append("    ").append(line1)
                        .append("\n");
            }
        }

        printAsync(result.toString());
    }

    /**
     * Displays the objective cards for a player and prompts them to choose one.
     *
     * @param model       the immutable game model containing the current state of the game
     * @param indexPlayer the index of the player whose objective cards are being shown
     */
    @Override
    public void show_ObjectiveCards(GameImmutable model, int indexPlayer) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Set your GOAL for the Game:\n> Choose one between these objective cards:");

        ObjectiveCard[] objectiveCards = model.getObjectiveCard().get(indexPlayer);

        if (objectiveCards.length != 2) {
            printAsync("Error: Expected exactly 2 objective cards.");
            return;
        }

        String card0 = objectiveCards[0].toString();
        String card1 = objectiveCards[1].toString();

        String[] lines0 = card0.split("\n");
        String[] lines1 = card1.split("\n");

        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();
        int height = Math.max(lines0.length, lines1.length);

        for (int k = 0; k < height + 1; k++) {
            rowBuilders.add(new StringBuilder());
        }

        for (int k = 0; k < height; k++) {
            if (k < lines0.length) {
                if (k == 0) {
                    rowBuilders.get(k).append("[0] ").append(lines0[k]).append("   ").append("[1] ").append(lines1[k]);
                } else {
                    rowBuilders.get(k).append("    ").append(lines0[k]).append("   ").append("    ").append(lines1[k]);
                }
            } else {
                rowBuilders.get(k).append("    ").append(" ".repeat(lines0[0].length())).append("   ").append("    ").append(lines1[k]);
            }
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowBuilders) {
            result.append(sb.toString().stripTrailing()).append("\n");
        }

        printAsync(result.toString());
    }


    /* VERSIONE DA PROVARE:

    public void show_ObjectiveCards(GameImmutable model, int indexPlayer) {
    // Pausa di 0.5 secondi
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    printAsync("Set your GOAL for the Game:\n> Choose one between these objective cards:");

    ObjectiveCard[] objectiveCards = model.getObjectiveCard().get(indexPlayer);

    if (objectiveCards.length == 0) {
        printAsync("Error: No objective cards found.");
        return;
    }

    if (objectiveCards.length > 2) {
        printAsync("Error: Expected at most 2 objective cards.");
        return;
    }

    // Recupera le stringhe di rappresentazione delle carte obiettivo
    String card0 = objectiveCards[0].toString();
    String card1 = objectiveCards.length > 1 ? objectiveCards[1].toString() : "";

    String[] lines0 = card0.split("\n");
    String[] lines1 = card1.split("\n");

    // Costruisce la stringa finale riga per riga
    StringBuilder result = new StringBuilder();
    int height = Math.max(lines0.length, lines1.length); // Altezza massima tra le due carte (assume che ci sia almeno una carta)

    for (int i = 0; i < height; i++) {
        if (i == 0) {
            result.append("[0] ").append(lines0[i]);
            if (lines1.length > 0) {
                result.append("   [1] ").append(lines1[i]);
            }
            result.append("\n");
        } else {
            String line0 = lines0.length > i ? lines0[i] : "";
            String line1 = lines1.length > i ? lines1[i] : "";

            int maxLength = Math.max(line0.length(), line1.length());
            line0 = line0 + " ".repeat(maxLength - line0.length());
            line1 = line1 + " ".repeat(maxLength - line1.length());

            result.append("    ").append(line0);
            if (!line1.isEmpty()) {
                result.append("   ").append(line1);
            }
            result.append("\n");
        }
    }

    // Stampa il risultato
    printAsync(result.toString());
}

     */

/*
    private String getColorCode(CardType cardType){
        switch(cardType) {
            case GoldCard: {
                return ColorConsole.YELLOW_BACKGROUND_BRIGHT.getCode();
            }
            case ResourceCard: {
                return ColorConsole.GREEN_BACKGROUND_BRIGHT.getCode();
            }
            case InitialCard: {
                return ColorConsole.WHITE_BACKGROUND_BRIGHT.getCode();
            }
            //case ObjectiveCard: { --> AGGIUNGI ObjectiveCard IN CardType
            //    return ColorConsole.RED_BACKGROUND_BRIGHT.getCode();
            //}

        }
        return ColorConsole.RESET.getCode();
    }

 */

    /**
     * Displays an error message indicating that the connection to the server was lost and exits the application.
     */
    @Override
    public void show_noConnectionError() {
        printAsync("CONNECTION TO SERVER LOST!\n");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(-1);
    }

    /**
     * Displays the visible cards on the board based on the specified card type.
     *
     * @param model    the immutable game model containing the current state of the game
     * @param cardType the type of cards to be displayed (ResourceCard or GoldCard)
     */
    @Override
    public void show_visibleCardsBoard(GameImmutable model, CardType cardType){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(cardType.equals(CardType.ResourceCard)){
            printAsync(model.getBoard().cardsVisibleResourceToString());
        }else if(cardType.equals(CardType.GoldCard))
            printAsync(  model.getBoard().cardsVisibleGoldToString());
        else
            show_wrongSelectionMsg();

    }

    /**
     * Displays instructions for using the chat during the game, including how to send public or private messages and how to exit the game.
     *
     * @param model the immutable game model containing the current state of the game
     * @param nick  the nickname of the player
     */
    public void show_askForChat(GameImmutable model, String nick){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("\nIf during the game you want to send a public message, a private message or you want to exit from the game, you can write one of this following commands:\n ");
        printAsyncNoCursorReset(ansi().a("""
                > Commands:
                \t\t  type "/c + [msg]" to send a public message!
                \t\t  type "/cs + [playerName] + [msg]" to send a private message!
                \t\t  type "/quit" for leaving the game!
                \t
                \n
                """).fg(DEFAULT));

    }

    /**
     * Displays the latest chat messages.
     *
     * @param model the immutable game model containing the current state of the game
     */
    public void show_messages(GameImmutable model){
        String ris = "Latest Messages:\n" + model.getChat().toString(this.nickname);
        printAsync(ris);
    }

    /**
     * Adds a message to the chat and displays the latest messages.
     *
     * @param msg   the message to add
     * @param model the immutable game model containing the current state of the game
     */
    @Override
    public void addMessage(Message msg, GameImmutable model) {
        show_messages(model);
    }

    /**
     * Displays the messages sent by a specific user.
     *
     * @param model    the immutable game model containing the current state of the game
     * @param nickname the nickname of the player whose messages are being shown
     */
    @Override
    public void show_sentMessage(GameImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

    /**
     * Closes any active wait pop-up
     */
    @Override
    public void closeWaitPopUp() {

    }

    /**
     * Shows a message for the next turn or when a player reconnects
     * @param model model where events happen
     * @param nick of the reconnected player
     */
    @Override
    public void show_PlayerReconnectedMsg(GameImmutable model, String nick, String lastPlayerReconnected) {
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(nick.equals(lastPlayerReconnected)){
            printAsync("You are back in the game! \n");
        }
        else
            printAsync("Player" + nick + "is back in the game! \n");
    }

    /**
     * Shows a message indicating that a player failed to reconnect to the game.
     *
     * @param nick the nickname of the player who failed to reconnect
     */
    @Override
    public void show_failedReconnectionMsg( String nick){
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Failed to reconnect " + nick + " to the game! \n");
    }

    /**
     * Asks if the player is trying to reconnect
     */
    @Override
    public void show_askForReconnection(){ //Show mostrata al solo giocatore che sta provando a riconnettersi
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Press YES if you are trying to RECONNECT to the game \n");

    }
}
