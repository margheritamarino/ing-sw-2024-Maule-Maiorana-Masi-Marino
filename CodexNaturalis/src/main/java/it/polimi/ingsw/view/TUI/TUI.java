package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import it.polimi.ingsw.model.game.Game;
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



//la classe TUI deve funzionare come una vista che consenta al gioco di interagire con l'utente per fargli visualizzare lo stato del gioco e fargli delle richieste
//deve avere gli show del gioco (showTitle, showScoretrack, showBoard, showPlayerDeck)
//e dei metodi per interagire con l'utente per fargli operare delle scelte (fare una mossa, scegliere la carta obbiettivo iniziale, scegliere fronte\retro delle carte?)

//il codice ANSI viene usato per controllare gli aspetti visivi della console (colore del testo, sfondo, etc)
public class TUI extends UI {

    private String nickname; //scopo: personalizzare l'interazione con l'utente


    /**
     * Constructor
     */
    public TUI(){
        init();
    }


    //inizializzo la console ANSI e una lista vuota per memorizzare gli eventi da mostrare
    @Override
    public void init() {
        AnsiConsole.systemInstall();
        eventsToShow = new ArrayList<>();
    }


    //messaggio di benvenuto per ogni giocatore in grassetto
    public void show_welcome(String nick) {
        printAsync("Welcome " + nick);
    }

    public void show_allPlayers(GameImmutable model) {
        //toStringListPlayer restituisce la lista dei giocatori attuali
        printAsync( model.toStringListPlayers());

    }
    public void show_OrderPlayers(GameImmutable model) {
        //toStringListPlayer restituisce la lista dei giocatori attuali
        printAsync("The ORDER of the players is: \n" + model.toStringListOrderArray());

    }

    //show di ciò che deve sempre essere mostrato ad ogni giocatore
    public void show_alwaysShow(GameImmutable model, String nick) {
        show_playerDeck(model, nick);
        show_playerBook(model);
    }

    public void show_alwaysShowForAll(GameImmutable model) {
        this.clearScreen();
        show_publisher();
        show_titleCodexNaturalis();
        show_gameId(model);
        show_board(model);
        show_scoretrack(model);
        show_important_events();
        //mostro tutti i messaggi della chat
        show_messages(model);
    }

    @Override
    public void show_publisher(){
        clearScreen();
        printAsync("CRANIO CREATIONS");

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
        printAsync("CODEX NATURALIS");
    }


    @Override
    public void show_scoretrack(GameImmutable model) {
        printAsync(model.getScoretrack().toString());
    }

    @Override
    public void show_board(GameImmutable model) {
        printAsync(model.getBoard().toString());
    }


    @Override
    public void show_playerBook(GameImmutable model){
        printAsync(model.getCurrentPlayer().getPlayerBook().toString());
    }

    @Override
    public void show_playerDeck(GameImmutable model, String nick) {
        printAsync("*** YOUR DECK ***");
        Player p = model.getPlayerByNickname(nick);
      /*  for (int i = 0; i < p.getPlayerDeck().miniDeck.size(); i++) {
            printAsync("[" + i + "]: \n" + p.getPlayerDeck().miniDeck.get(i).toString());
        }*/
        printAsync(p.getPlayerDeck().toString());
    }

    @Override
    public void show_askNum(String msg, GameImmutable model, String nickname) {
        printAsync(msg);
    }

    @Override
    public void show_askNumPlayersMessage(){
        printAsync("Insert the number of the players in the Game: ");
    }

    @Override
    public void show_askGameIDMessage(){
        printAsync("Insert the GameID: ");
    }
    @Override
    public void show_notValidMessage(){
        printAsync("Not valid input. Please try again. ");
    }

    @Override
    public void show_playerJoined(GameImmutable gameModel, String nick) {
        clearScreen();
        show_welcome(nick);
        printAsync("GameID: [" + gameModel.getGameId().toString() + "]");
        printAsync("Players in the LOBBY:");
        show_allPlayers(gameModel);
        System.out.flush(); //Svuota il buffer di output per garantire che tutti i dati scritti siano visibili immediatamente
    }

    @Override
    public void show_readyToStart(GameImmutable gameModel, String nickname) {
        printAsync("Press y if you are ready to start the game");
    }

    @Override
    public void show_youAreReady(GameImmutable model){
        printAsync("You are ready to start the game!");
    }




    @Override
    public void show_whichInitialCards() {
        printAsync("> Choose the front[0] or the back[1] of the following initial card to place it at the center of your book:");
    }

    @Override
    public void show_wrongSelectionMsg() {
        printAsync("Invalid selection. Please choose 0 or 1.");
    }

    @Override
    public void show_wrongCardSelMsg() {
        printAsync("Invalid selection. Please choose a number between 0 and 6.");
    }

    @Override
    public void show_wrongCellSelMsg() {
        printAsync("Invalid selection. Please choose a number between: "+ DefaultValue.BookSizeMin+ " and " + DefaultValue.BookSizeMax);
    }

    @Override
    public void show_whichObjectiveCards() {
        printAsync("Set your GOAL for the Game:");
        printAsync("> Choose one between these objective cards:");
    }

    @Override
    public void show_personalObjective(){
        printAsync("This is your personal objective card! \n");
    }

    /**
     * Clears the console
     */
    public void clearScreen(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            //if not on a Windows machine
        } catch (IOException | InterruptedException e) {
            //for mac
            printAsyncNoCursorReset("\033\143");
        }
    }

    public void show_gameId(GameImmutable gameModel) {
        printAsync(ansi().fg(DEFAULT).a("Game with id: [" + gameModel.getGameId() + "]"));
    }

    @Override
    public void show_WaitTurnMsg(GameImmutable model, String nickname) {
        printAsync("WAIT! It's "+ model.getCurrentPlayer().getNickname()+" TURN\n");

    }
    @Override
    public void show_CurrentTurnMsg(){
        printAsync("It's your TURN!\n");
    }
    @Override
    public synchronized void show_gameStarted(GameImmutable model) {
        this.clearScreen();
        this.show_publisher();
        printAsync(ansi().fg(DEFAULT).a("***GAME STARTED!***"));
        show_OrderPlayers(model);
        show_board(model);
        show_scoretrack(model);

      //  this.show_titleCodexNaturalis();
       // this.show_allPlayers(model); //mostra la lista dei giocatori
      //  this.show_alwaysShowForAll(model);
    }

    @Override
    public void show_askPlaceCardsMainMsg(GameImmutable model){
        printAsync("It's your turn to PLACE A CARD" );
    }

    @Override
    public void show_PickCardMsg(GameImmutable model){
        printAsync("It's your turn to PICK A CARD " );
        show_board(model);
    }

    @Override
    public void show_askCardType(GameImmutable model, String nickname){
        printAsync("Which card do you want to pick? Press R(r) if you want a Resource card or G(g) if you want a Gold card:" );
    }

    @Override
    public void show_askDrawFromDeck(GameImmutable model, String nickname){
        printAsync("Press Y if you want to draw from deck or N if you want a visible card:" );
    }

    @Override
    public void show_askWhichCellMsg(GameImmutable model){
        printAsync("Choose a Cell in the book to place the card:" );
    }

    @Override
    public void show_playerHasToChooseAgain(GameImmutable model, String nickname){
        printAsync("ERROR: invalid selection. Choose again!" );

    }

    @Override
    public void show_cardPlacedMsg(GameImmutable model){
        printAsync( model.getCurrentPlayer().getNickname() + " has placed a card!\n" );
    }

    @Override
    public void show_nextTurnMsg(GameImmutable model){
        printAsync("Next turn: " + model.getCurrentPlayer().getNickname());
    }

    @Override
    public void show_cardDrawnMsg(GameImmutable model, String nickname){
            int lastPos= model.getCurrentPlayer().getPlayerDeck().getNumCards()-1;
            printAsync( "This is your drawn card:\n " + model.getCurrentPlayer().getPlayerDeck().getMiniDeck().get(lastPos).toString());

    }

    @Override
    public void show_pointsAddedMsg(GameImmutable model, String nickname){
        if(model.getCurrentPlayer().equals(nickname) )
            printAsync("You scored " + model.getCurrentCardPoints()+ "points!");
        else
            printAsync(model.getCurrentPlayer().getNickname() + " scored " + model.getCurrentCardPoints()+ " points!");
        printAsync("NEW TOTAL SCORE");
        show_scoretrack(model);
    }

    @Override
    public void show_joiningToGameMsg(String nickname){
        printAsync("Trying to join a game...\n");
    }


    @Override
    public void addImportantEvent(String input) {
        //Want to show a numbeMaxEventToShow important event happened
        if (eventsToShow.size() + 1 >= DefaultValue.MaxEventToShow) {
            eventsToShow.removeFirst();
        }
        eventsToShow.add(input);
        show_important_events();
    }

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
        ris.append(ansi().fg(GREEN).bold().a("Latest Events:").fg(DEFAULT).boldOff());
        for (String s : eventsToShow) {
            ris.append(ansi().fg(Ansi.Color.WHITE).a(s).a(" ".repeat(longestImportantEvent - s.length())).fg(DEFAULT));
        }
        printAsync(ris);
    }

    @Override
    public void show_insertNicknameMessage(){
        printAsync("Insert your nickname: \n");
    }

    @Override
    public void show_chosenNickname(String nickname) {
        printAsync("Your nickname is " + nickname);
    }

    @Override
    public void show_gameEnded(GameImmutable model) {
        clearScreen();
        show_titleCodexNaturalis();
        printAsync("***GAME ENDED***\n");

    }

    @Override
    public void show_returnToMenuMsg() {
        printAsync("You are back in the menu!\n");
    }
    @Override
    public void show_temporaryInitialCards(GameImmutable model) {
        printAsync("Initial Card...\n");
        PlayableCard[] initialCards = model.getInitialCard();

        // Lista per accumulare le stringhe delle righe
        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();

        // Inizializziamo le righe con StringBuilder
        for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
            rowBuilders.add(new StringBuilder());
        }

        for (int i = 0; i < initialCards.length; i++) {
            PlayableCard card = initialCards[i];
            String[] lines = card.toString().split("\n");

            // Aggiungi il numero identificativo alla prima riga della carta
            rowBuilders.get(0).append("[").append(i).append("] "); // Formattato per aggiungere indice
            for (int k = 0; k < lines.length; k++) {
                if (k > 0) {
                    rowBuilders.get(k).append("   "); // Spazi per allineare con il numero identificativo
                }
                rowBuilders.get(k).append(lines[k]).append(" ");
            }
        }

        // Costruiamo l'output finale unendo tutte le righe
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowBuilders) {
            result.append(sb.toString().stripTrailing()).append("\n");
        }

        printAsync(result.toString());
    }



    @Override
    public void show_ObjectiveCards(GameImmutable model) {
        printAsync("Objective Cards...\n");
        ArrayList<ObjectiveCard> objectiveCards = model.getObjectiveCard();

        // Lista per accumulare le stringhe delle righe
        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();

        // Inizializziamo le righe con StringBuilder
        for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
            rowBuilders.add(new StringBuilder());
        }

        for (int i = 0; i < objectiveCards.size(); i++) {
            ObjectiveCard card = objectiveCards.get(i);
            String[] lines = card.toString().split("\n");

            // Aggiungi il numero identificativo alla prima riga della carta
            for (int k = 0; k < lines.length; k++) {
                if (k > 0) {
                    rowBuilders.get(k).append("   "); // Spazi per allineare con il numero identificativo
                }
                rowBuilders.get(k).append(lines[k]).append(" ");
            }
        }

        // Costruiamo l'output finale unendo tutte le righe
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowBuilders) {
            result.append(sb.toString().stripTrailing()).append("\n");
        }

        printAsync(result.toString());
    }


    @Override
    public void show_noConnectionError(){
        printAsync("CONNECTION TO SERVER LOST!\n");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(-1);
    }

    @Override
    public void show_visibleCardsBoard(GameImmutable model, CardType cardType){
        if(cardType.equals(CardType.ResourceCard)){
            printAsync(model.getBoard().cardsVisibleResourceToString());
        }else if(cardType.equals(CardType.GoldCard))
            printAsync(  model.getBoard().cardsVisibleGoldToString());
        else
            show_wrongSelectionMsg();

    }



    //*****CHAT******

    public void show_askForChat(GameImmutable model, String nick){
        printAsync("Do you want to send a public message, a private message or neither?\n ");
        printAsyncNoCursorReset(ansi().a("""
                > Select one option:
                \t
                \t -> Useful commands that can be used at any point in the game:
                \t\t  type "/c [msg]" to send a public message!
                \t\t  type "/cs [playerName] [msg]" to send a private message!
                \t\t  type "/quit" and you can leave the game!
                \t""").fg(DEFAULT));
    }

    //mostra i messaggi presenti nel modello di gioco nella chat
    public void show_messages(GameImmutable model){
        String ris = "Latest Messages:\n" + model.getChat().toString(this.nickname);
        printAsync(ris);
    }

    /**
     * Search for the longest message
     * @param model
     * @return
     */
    @Override
    public int getLengthLongestMessage(GameImmutable model) {
        return model.getChat().getMsgs().stream()
                .map(Message::getText)
                .reduce((a, b) -> a.length() > b.length() ? a : b)
                .toString().length();
    }

    //mostra i messaggi già presenti per aggiungerne altri
    @Override
    public void addMessage(Message msg, GameImmutable model) {
        show_messages(model);
    }

    //mostra i messaggi inviati da un utente specifico
    @Override
    public void show_sentMessage(GameImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }
}
