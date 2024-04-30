package it.polimi.ingsw.view.flow;
import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.game.GameImmutable;


public abstract class Flow implements GameListener {

    /**
     * Resets the game id
     * @param fileDisconnection file to reset
     * @param model
     */
    protected void resetGameId(FileDisconnection fileDisconnection, GameImmutable model) {
        for (PlayerIC p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(), -1);
        }
    }

    /**
     * Saves latest game id
     * @param fileDisconnection file to write
     * @param nick
     * @param gameId
     */
    protected void saveGameId(FileDisconnection fileDisconnection, String nick, int gameId) {
        fileDisconnection.setLastGameId(nick, gameId);
    }

    /**
     * Shows no connection error
     */
    public abstract void noConnectionError();

}
