package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.interfaces.PlayerIC;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.view.Utilities.UI;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
import static it.polimi.ingsw.view.TUI.PrintAsync.printAsyncNoCursorReset;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;


//da fare
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
        printAsync(ansi().fg(BLUE).a("Welcome " + nick).bold());
    }

    /**
     * Shows all players nicknames
     * @param model
     */

    public void show_allPlayers(GameImmutable model) {
        //toStringListPlayer restituisce la lista dei giocatori attuali
        printAsync("Players: \n" + model.toStringListPlayers());

    }


    //show di ciò che deve sempre essere mostrato ad ogni giocatore
    public void show_alwaysShow(GameImmutable model, String nick) {
        show_alwaysShowForAll(model);
        show_welcome(nick);
        show_playerDeck(model);
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
    }

    @Override
    public void show_publisher(){
        clearScreen();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().fg(GREEN).a("CRANIO CREATIONS").reset());

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
        //printstream per stampare nel terminale
        //se esiste la console viene usato il set di caratteri della console
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset() //se non è disponibile la console viene usato il set di caratteri predefinito
                //colore del testo ROSSO
        ).println(ansi().fg(RED).a("CODEX NATURALIS").reset()); //per evitare che prossime stampe possano basarsi su questa
    }


    public void show_scoretrack(GameImmutable model) {
        printAsync(model.getScoretrack().toString());
    }

    public void show_board(GameImmutable model) {
        printAsync(model.getBoard().toString());
    }

    //TODO
    public void show_playerBook(GameImmutable model){
        printAsync(model.getCurrentPlayer().getPlayerBook().toString());
    }

    @Override
    protected void show_playerDeck(GameImmutable model) {
        printAsync(model.getCurrentPlayer().getPlayerDeck().toString());
    }

    @Override
    public void show_askNum(String msg, GameImmutable model, String nickname) {
        printAsync("Insert 0 or 1: "); //TODO
    }

    @Override
    public void show_notValidMessage(){
        printAsync("Not Valid input");
    }

    @Override
    public void show_playerJoined(GameImmutable gameModel, String nick) {
        clearScreen();
        show_welcome(nick);
        printAsync(ansi().fg(YELLOW).a("GameID: [" + gameModel.getGameId().toString() + "]\n"));
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
        printAsync("> Choose the front or the back of  this initial card:");
    }

    @Override
    public void show_whichObjectiveCards() {
        printAsync("> Choose one of this two objective cards:");
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

    /**
     * Shows the game id
     * @param gameModel
     */
    public void show_gameId(GameImmutable gameModel) {
        printAsync(ansi().fg(DEFAULT).a("Game with id: [" + gameModel.getGameId() + "]"));
    }

    @Override
    public void show_gameStarted(GameImmutable model) {
        this.clearScreen();
        this.show_publisher();
        this.show_titleCodexNaturalis();
        this.show_allPlayers(model); //mostra la lista dei giocatori
        this.show_alwaysShowForAll(model);
        this.show_gameId(model);
    }

    @Override
    public void show_askPlaceCardsMainMsg(GameImmutable model){
        printAsync("It's " + model.getCurrentPlayer().getNickname() + "'s turn to draw a card!" );
    }

    @Override
    public void show_PickCardMsg(GameImmutable model){
        printAsync("It's " + model.getCurrentPlayer().getNickname() + "'s turn to pick a card!" );
    }

    @Override
    public void show_askCardType(GameImmutable model, String nickname){
        printAsync("Press R(r) if you want a Resource card or G(g) if you want a Gold card: " );
    }

    @Override
    public void show_askWhichCellMsg(GameImmutable model){
        printAsync("It's " + model.getCurrentPlayer().getNickname() + "'s turn to choose a cell to place the card!" );
    }

    @Override
    public void show_cardPlacedMsg(GameImmutable model){
        printAsync( model.getCurrentPlayer().getNickname() + " has placed a card!" );
    }

    @Override
    public void show_pointsAddedMsg(GameImmutable model){
        printAsync(model.getCurrentPlayer().getNickname() + " scored some points!");
        printAsync("New total score: ");
        show_scoretrack(model);
    }

    @Override
    public void show_joiningToGameMsg(String nickname){
        printAsync("You are joining a game!");
    }


    @Override
    public void addImportantEvent(String input) {
        //Want to show a numbeMaxEventToShow important event happened
        if (eventsToShow.size() + 1 >= DefaultValue.MaxEventToShow) {
            eventsToShow.remove(0);
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
        printAsync("Insert your nickname: ");
    }

    @Override
    public void show_chosenNickname(String nickname) {
        printAsync("Your nickname is " + nickname);
    }

    @Override
    public void show_gameEnded(GameImmutable model) {
        clearScreen();
        show_titleCodexNaturalis();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset() //se non è disponibile la console viene usato il set di caratteri predefinito
                //colore del testo ROSSO
        ).println(ansi().fg(RED).bold().a("GAME ENDED").boldOff().reset()); //per evitare che prossime stampe possano basarsi su questa

    }

    @Override
    public void show_returnToMenuMsg() {
        printAsync("You are back in the menu!");
    }



































    @Override
    protected void show_temporaryInitialCards(GameImmutable model){
        //TO DO (DECIDERE COME STAMPARE LE DUE INITIAL CARD A VIDEO)
    }

    @Override
    protected void show_ObjectiveCards(GameImmutable model){
        //TO DO (DECIDERE COME STAMPARE LE DUE INITIAL CARD A VIDEO)
    }





    /**
     * Shows the next player
     * @param model
     */
    public void show_nextTurn(GameImmutable model){

    }





    public void show_card(GameImmutable model, int index){
        model.getCurrentPlayer().getPlayerDeckIC().get(index).
    }


    @Override
    protected void show_nextTurnOrPlayerReconnected(GameImmutable model, String nickname) {

    }





    @Override
    protected void show_noConnectionError() {

    }




}
