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
import java.io.PrintStream;

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
    private boolean alreadyShowedLobby;


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
        // Pausa di 1 sec
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("*** WELCOME " + nick + "!***");
    }


    public void show_allPlayers(GameImmutable model) {
        // Pausa di 0.5 secondo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //toStringListPlayer restituisce la lista dei giocatori attuali
        printAsync( model.toStringListPlayers());

    }
    public void show_OrderPlayers(GameImmutable model) {
        //toStringListPlayer restituisce la lista dei giocatori attuali
        // Pausa di 0.5 secondo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //out.print(ColorEnum.YELLOW);
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

        // Pausa di 1.5 secondi
         try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //out.print(ColorEnum.GREEN_BRIGHT);
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




    @Override
    public void show_scoretrack(GameImmutable model) {

        // Pausa di 0.5 secondo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync(model.getScoretrack().toString());
    }

    @Override
    public void show_board(GameImmutable model) {
        // Pausa di 0.5 secondo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //printAsync(model.getBoard().toString());
        System.out.print("***********BOARD***********\n");
        System.out.print("\n");

        // GOLDCARDS
        System.out.print(ColorConsole.YELLOW_BACKGROUND.getCode()+ColorConsole.BLACK.getCode()+"***GOLDCARDS***: \n"+ColorConsole.RESET.getCode());
        System.out.print("\n");
        System.out.print(model.getBoard().cardsGoldToString());
        System.out.print("\n");


        // RESOURCECARDS
        System.out.print(ColorConsole.GREEN.getCode()+"***RESOURCECARDS***: \n"+ ColorConsole.RESET.getCode());
        System.out.print("\n");
        System.out.print(model.getBoard().cardsResourceToString());
        System.out.print("\n");

        // OBJECTIVE
        System.out.print(ColorConsole.RED_BRIGHT.getCode()+"*******COMMON GOALS*******: \n"+ ColorConsole.RESET.getCode());
        System.out.print("\n");
        System.out.print(model.getBoard().cardsObjectiveToString());
        System.out.print("\n");
    }


    @Override
    public void show_playerBook(GameImmutable model){
        // Pausa di 0.5 secondo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync(model.getCurrentPlayer().getPlayerBook().toString());
    }

    @Override
    public void show_playerDeck(GameImmutable model, String nick) {
        // Pausa di 0.5 secondo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("*** YOUR DECK ***");
        Player p = model.getPlayerByNickname(nick);
      /*  for (int i = 0; i < p.getPlayerDeck().miniDeck.size(); i++) {
            printAsync("[" + i + "]: \n" + p.getPlayerDeck().miniDeck.get(i).toString());
        }*/
        printAsync(p.getPlayerDeck().toString());
    }

    @Override
    public void show_askNum(String msg, GameImmutable model, String nickname) {

        // Pausa di 0.5 secondo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync(msg);
    }

    @Override
    public void show_askNumPlayersMessage(){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Insert the number of the players in the Game: ");
    }

    @Override
    public void show_askGameIDMessage(){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Insert the GameID: ");
    }
    @Override
    public void show_notValidMessage(){
        // Pausa di 0.5 secondo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Not valid input. Please try again. ");
    }

    @Override
    public void show_playerJoined(GameImmutable gameModel, String nick, Color color) {
        // Pausa di 0.5 secondo
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



        System.out.flush(); //Svuota il buffer di output per garantire che tutti i dati scritti siano visibili immediatamente
    }
    // Metodo per aggiungere solo il nuovo giocatore
    private void add_newPlayer(GameImmutable gameModel) {
        Player newPlayer = gameModel.getLastPlayer();
        if (newPlayer != null) {
            printAsync("[# " + gameModel.getPlayers().size() + "]: " + newPlayer.getNickname());
        }
    }

    @Override
    public void show_readyToStart(GameImmutable gameModel, String nickname) {
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Press y if you are ready to start the game");
    }

    @Override
    public void show_youAreReady(GameImmutable model){
        printAsync("You are ready to start the game!\n PLAYERS READY in the LOBBY:");
        printAsync( model.toStringListPlayersReady());

    }


    @Override
    public void show_wrongSelectionMsg() {
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Invalid selection. Please choose 0 or 1.");
    }

    @Override
    public void show_wrongCardSelMsg() {
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Invalid selection. Please choose a number between 0 and 6.");
    }

    @Override
    public void show_wrongCellSelMsg() {
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Invalid selection. Please choose a number between: "+ DefaultValue.BookSizeMin+ " and " + DefaultValue.BookSizeMax);
    }


    @Override
    public void show_personalObjective(GameImmutable model, int indexPlayer){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        String nickname= model.getPlayers().get(indexPlayer).getNickname();
//        ObjectiveCard objectiveCard = model.getPlayerGoalByNickname(nickname);
//        printAsync("This is your personal objective card! \n"+ objectiveCard.toString());

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

    public void show_gameId(GameImmutable gameModel) {
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync(ansi().fg(DEFAULT).a("Game with id: [" + gameModel.getGameId() + "]"));
    }

    @Override
    public void show_WaitTurnMsg(GameImmutable model, String nickname) {
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("WAIT! It's "+ model.getCurrentPlayer().getNickname()+" TURN\n");

    }
    @Override
    public void show_CurrentTurnMsg(GameImmutable model){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("It's your TURN!\n");
    }
    @Override
    public void show_gameStarted(GameImmutable model) {
        this.clearScreen();
        this.show_publisher();
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("It's your turn to PLACE A CARD" );
    }

    @Override
    public void show_PickCardMsg(GameImmutable model){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("It's your turn to PICK A CARD " );
        show_board(model);
    }

    @Override
    public void show_askCardType(GameImmutable model, String nickname){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Which card do you want to pick? Press R(r) if you want a Resource card or G(g) if you want a Gold card:" );
    }

    @Override
    public void show_askDrawFromDeck(GameImmutable model, String nickname){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Press Y if you want to draw from deck or N if you want a visible card:" );
    }

    @Override
    public void show_askWhichCellMsg(GameImmutable model){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Choose a Cell in the book to place the card:" );
    }

    @Override
    public void show_playerHasToChooseAgain(GameImmutable model, String nickname, String msg){
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("ERROR: " + msg );

    }

    @Override
    public void show_cardPlacedMsg(GameImmutable model){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync( model.getCurrentPlayer().getNickname() + " has placed a card!\n" );
    }

    @Override
    public void show_nextTurnMsg(GameImmutable model){
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Next turn: " + model.getCurrentPlayer().getNickname());
    }

    @Override
    public void show_cardDrawnMsg(GameImmutable model, String nickname){
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       ArrayList<PlayableCard[]> minideck= model.getCurrentPlayer().getPlayerDeck().getMiniDeck();
        printAsync( "This is your drawn card:\n" + minideck.get(2)[0].toString() + minideck.get(2)[1].toString());

    }

    @Override
    public void show_pointsAddedMsg(GameImmutable model, String nickname){
        // Pausa di 0.5 secondi
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

    @Override
    public void show_joiningToGameMsg(String nickname, Color color){
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        // Pausa di 0.5 secondi
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

    @Override
    public void show_insertNicknameMessage(){

        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Insert your nickname: \n");
    }

    @Override
    public void show_chosenNickname(String nickname) {
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Your nickname is " + nickname );
    }

    @Override
    public void show_gameEnded(GameImmutable model) {
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearScreen();
        show_titleCodexNaturalis();
        printAsync("***GAME ENDED***\n");

    }

    @Override
    public void show_returnToMenuMsg() {
        // Pausa di 0.5 secondi
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("You are back in the menu!\n");
    }

    // Metodo per stampare le carte affiancate con indici in alto a sinistra
    @Override
    public void show_temporaryInitialCards(GameImmutable model, int indexPlayer) {
        // Pausa di 1.5 secondi
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

        // Recupera le stringhe di rappresentazione delle carte
        String card0 = initialCards[0].toString();
        String card1 = initialCards[1].toString();

        // Divide le stringhe delle carte in righe
        String[] lines0 = card0.split("\n");
        String[] lines1 = card1.split("\n");

        // Costruisce la stringa finale riga per riga
        StringBuilder result = new StringBuilder();
        int height = Math.max(lines0.length, lines1.length); // Assumiamo che entrambe le carte abbiano la stessa altezza

        // Aggiunge l'indice alle righe
        for (int i = 0; i < height; i++) {
            if (i == 0) {
                result.append("[0] ").append(lines0[i])
                        .append("   ")
                        .append("[1] ").append(lines1[i])
                        .append("\n");
            } else {
                // Aggiusta l'allineamento aggiungendo spazi se necessario
                String line0 = lines0[i];
                String line1 = lines1[i];

                // Calcola la lunghezza delle linee per mantenere l'allineamento
                int maxLength = Math.max(line0.length(), line1.length());
                line0 = line0 + " ".repeat(maxLength - line0.length());
                line1 = line1 + " ".repeat(maxLength - line1.length());

                result.append("    ").append(line0)
                        .append("   ")
                        .append("    ").append(line1)
                        .append("\n");
            }
        }

        // Stampa il risultato
        printAsync(result.toString());
    }
    @Override
    public void show_ObjectiveCards(GameImmutable model, int indexPlayer) {
        // Pausa di 0.5 secondo
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

        // Raccogliamo le stringhe di rappresentazione delle carte
        String card0 = objectiveCards[0].toString();
        String card1 = objectiveCards[1].toString();

        // Divide le stringhe delle carte in righe
        String[] lines0 = card0.split("\n");
        String[] lines1 = card1.split("\n");

        // Inizializziamo l'ArrayList per contenere le righe combinate
        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();
        int height = Math.max(lines0.length, lines1.length); // Assume che entrambe le carte abbiano la stessa altezza

        // Inizializziamo le righe con StringBuilder
        for (int k = 0; k < height + 1; k++) {
            rowBuilders.add(new StringBuilder());
        }

        // Aggiungi l'indice e le righe della carta riga per riga
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

        // Costruisci l'output finale unendo tutte le righe
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowBuilders) {
            result.append(sb.toString().stripTrailing()).append("\n");
        }

        // Stampa il risultato
        printAsync(result.toString());
    }


   /* @Override
    public void show_ObjectiveCards(GameImmutable model, int indexPlayer) {
        // Pausa di 0.5 secondo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Set your GOAL for the Game:\n> Choose one between these objective cards:");

        ObjectiveCard[] objectiveCards = model.getObjectiveCard().get(indexPlayer);

        // Lista per accumulare le stringhe delle righe
        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();

        // Inizializziamo le righe con StringBuilder
        for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
            rowBuilders.add(new StringBuilder());
        }

        for (int i = 0; i < objectiveCards.length; i++) {
            ObjectiveCard card = objectiveCards[i];
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
    }*/
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

    @Override
    public void show_visibleCardsBoard(GameImmutable model, CardType cardType){
        // Pausa di 0.5 secondo
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



    //*****CHAT******

    public void show_askForChat(GameImmutable model, String nick){
        // Pausa di 0.5 secondi
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

    @Override
    public void closeWaitPopUp() {

    }

    /**
     * Shows a message for the next turn or when a player reconnects
     * @param model model where events happen
     * @param nick of the reconnected player
     */
    @Override
    public void show_PlayerReconnectedMsg(GameImmutable model, String nick) {
        // Pausa di 1 secondo
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printAsync("Player" + nick + "is back in the game! \n");
    }

    //mostra i messaggi inviati da un utente specifico
    @Override
    public void show_sentMessage(GameImmutable model, String nickname) {
        this.show_alwaysShow(model, nickname);
    }

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
