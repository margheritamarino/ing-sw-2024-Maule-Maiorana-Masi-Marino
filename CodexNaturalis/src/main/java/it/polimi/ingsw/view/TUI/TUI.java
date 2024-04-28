package it.polimi.ingsw.view.TUI;

// import org.fusesource.jansi.AnsiConsole;
import it.polimi.ingsw.model.game.GameImmutable;
import it.polimi.ingsw.view.Utilities.UI;

import java.io.IOException;
import java.util.ArrayList;

import static it.polimi.ingsw.network.PrintAsync.printAsync;


//da fare
public class TUI extends UI {

    private String nickname;


    /**
     * Constructor
     */
    public TUI(){
        init();
    }

    @Override
    public void init() {
    //    AnsiConsole.systemInstall();
        eventsToShow = new ArrayList<>();
    }

    @Override
    protected void show_publisher() throws IOException, InterruptedException {

    }

    @Override
    protected void show_creatingNewGameMessage(String nickname) {

    }

    @Override
    protected void show_insertNicknameMessage() {

    }

    @Override
    protected void show_chosenNickname(String nickname) {

    }

    @Override
    protected void show_noAvailableGamesToJoin(String msg) {

    }

    @Override
    protected void show_insertPlayersNumber() {

    }

    @Override
    protected void show_chooseGameID() {

    }

    /**
     * Shows all players' nicknames
     *
     * @param model
     */
    public void show_allPlayers(GameImmutable model) {
        printAsync("Current Players: \n" + model.toStringListPlayers());
    }


    @Override
    protected void show_gameStarted(GameImmutable model) {

    }

    @Override
    protected void show_gameEnded(GameImmutable model) {

    }

    @Override
    protected void show_playerJoined(GameImmutable gameModel, String nick) {

    }

    @Override
    protected void show_youReadyToStart(GameImmutable gameModel, String nicknameofyou) {

    }

    @Override
    protected void show_nextTurnOrPlayerReconnected(GameImmutable model, String nickname) {

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
}
