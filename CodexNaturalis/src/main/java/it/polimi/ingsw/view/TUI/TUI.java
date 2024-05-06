package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.interfaces.PlayerIC;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.view.Utilities.UI;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
import static it.polimi.ingsw.view.TUI.PrintAsync.printAsyncNoCursorReset;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
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

    /**
     * Resizes the console
     */
    public void resize() {
        try {
            //ridimensionamento della console impostando il nuovo numero di righe e colonne
            //TODO (da capire)
            new ProcessBuilder("cmd", "/c", "mode con:cols=160 lines=50").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            //couldn't resize the terminal window
        }
    }

    /**
     * @param input the string of the important event to add
     */
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

    }

    /**
     * Shows the important events
     */
    public void show_important_events() {

        StringBuilder ris = new StringBuilder();
        int i = 0;
        int longestImportantEvent = eventsToShow.stream().map(String::length).reduce(0, (a, b) -> a > b ? a : b);
        ris.append(ansi().fg(GREEN).cursor(DefaultValue.row_important_events + i, DefaultValue.col_important_events - 1).bold().a("Latest Events:").fg(DEFAULT).boldOff());
        for (String s : eventsToShow) {
            ris.append(ansi().fg(Ansi.Color.WHITE).cursor(DefaultValue.row_important_events + 1 + i, DefaultValue.col_important_events).a(s).a(" ".repeat(longestImportantEvent - s.length())).fg(DEFAULT));
            i++;
        }
        printAsync(ris);

        printAsync(ansi().cursor(DefaultValue.row_input, 0));
    }

    /**
     * Shows all players nicknames
     * @param model
     */
    public void show_allPlayers(GameImmutable model) {
        //toStringListPlayer restituisce la lista dei giocatori attuali
        printAsync("Players: \n" + model.toStringListPlayers());

    }



    //mostra il logo cranio creations
    @Override
    public void show_publisher() throws IOException, InterruptedException {
        this.resize();

        clearScreen();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().fg(GREEN).a("CRANIO CREATIONS").reset());

        try {
            Thread.sleep(DefaultValue.time_publisher_showing_seconds * 1000);
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

    @Override
    public void show_creatingNewGameMsg(String nickname) {

    }

    @Override
    protected void show_temporaryInitialCards(GameImmutable model){
        //TO DO (DECIDERE COME STAMPARE LE DUE INITIAL CARD A VIDEO)
    }

    @Override
    protected void show_ObjectiveCards(GameImmutable model){
        //TO DO (DECIDERE COME STAMPARE LE DUE INITIAL CARD A VIDEO)
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
     * Shows the chat messages
     * @param model
     */
    public void show_messages(GameImmutable model){

    }

    /**
     * Shows the next player
     * @param model
     */
    public void show_nextTurn(GameImmutable model){

    }

    /**
     * Shows a welcome message
     * @param nick
     */
    public void show_welcome(String nick) {
  //      printAsync(ansi().cursor(DefaultValue.row_nextTurn + 1, 0).bold().a("Welcome " + nick).boldOff());
    }

    @Override
    protected void show_joiningFirstAvailableMsg(String nickname) {

    }

    @Override
    protected void show_joiningToGameIdMsg(int idGame, String nickname) {

    }

    @Override
    public void show_insertNicknameMessage() {

    }

    @Override
    public void show_chosenNickname(String nickname) {

    }

    @Override
    protected void show_noAvailableGamesToJoin(String msg) {

    }

    @Override
    protected void show_insertPlayersNumber() {

    }


    /**
     * Stuff that always needs to be visible
     *
     * @param model
     */
    public void show_alwaysShowForAll(GameImmutable model) {
        this.clearScreen();
        //resize();
        show_titleCodexNaturalis();
        show_gameId(model);
        //show_board(model);
        //show_scoretrack(model);
        show_messages(model);
        //show_points(model);
        //show_important_events();
    }

    /**
     * Shows the game id
     * @param gameModel
     */
    public void show_gameId(GameImmutable gameModel) {
        //printAsync(ansi().cursor(DefaultValue.row_gameID, 0).bold().a("Game with id: [" + gameModel.getGameId() + "]").boldOff());
    }

    @Override
    public void show_gameStarted(GameImmutable model) {
        this.clearScreen();
        this.show_titleCodexNaturalis();
        this.show_allPlayers(model);
        this.show_alwaysShowForAll(model);
        this.show_gameId(model);
    }

    @Override
    protected void show_playerDeck(GameImmutable model) {

    }




    @Override
    public void show_gameEnded(GameImmutable model) {
        clearScreen();
        resize();
        show_titleCodexNaturalis();
        //... //TODO stampa scritte di fine gioco
    }

    public void show_chooseFrontOrBack(){

    }
    public void show_ChooseObjectiveCard(){

    }
    @Override
    public void show_playerJoined(GameImmutable gameModel, String nick) {
        clearScreen();
        show_titleCodexNaturalis();
        printAsync(ansi().cursor().a("GameID: [" + gameModel.getGameId().toString() + "]\n").fg(DEFAULT)); //capire come togliere ROW e COLUMN
        System.out.flush();
        //StringBuilder players = new StringBuilder();
        StringBuilder ris = new StringBuilder();

        int i = 0;
        for (PlayerIC p : gameModel.getPlayers()) {
            if (p.getReadyToStart()) {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" is ready!\n");
            } else {
                ris.append(ansi().cursor(12 + i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" has joined!\n");
            }
            i++;
        }
        printAsyncNoCursorReset(ris);


        for (PlayerIC p : gameModel.getPlayers())
            if (!p.getReadyToStart() && p.getNickname().equals(nick))
                printAsyncNoCursorReset(ansi().cursor(17, 0).fg(WHITE).a("> When you are ready to start, enter (y): \n"));
        System.out.flush();
    }

    @Override
    protected void show_youReadyToStart(GameImmutable gameModel, String nicknameofyou) {

    }

    @Override
    protected void show_nextTurnOrPlayerReconnected(GameImmutable model, String nickname) {

    }

    @Override
    public void show_returnToMenuMsg() {

    }

    @Override
    protected void show_askNum(String msg, GameImmutable gameModel, String nickname) {

    }

    @Override
    protected void show_pickCellRequest() {

    }

    @Override
    protected void show_pickCardRequest() {

    }

    @Override
    protected void show_pickDeckRequest() {

    }

    @Override
    protected void show_chooseArrayPosition() {

    }

    @Override
    protected void show_InformationGame() {

    }

    @Override
    protected void show_noConnectionError() {

    }

    /**
     * Messages that always need to be on screen
     *
     * @param model
     * @param nick
     */
    public void show_alwaysShow(GameImmutable model, String nick) {
       //TODO
        this.clearScreen();
        show_titleCodexNaturalis();
        //show_gameId();
        show_Book();
        show_important_events();

    }


}
