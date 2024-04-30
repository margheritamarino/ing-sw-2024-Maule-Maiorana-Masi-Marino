package it.polimi.ingsw.view.flow;

import it.polimi.ingsw.listener.GameListener;
import it.polimi.ingsw.model.game.GameImmutable;


//classe astratta che fornisce funzionalità di base comuni che possono essere estese dal GameFlow
public abstract class Flow implements GameListener {


    //reimposta l'ID del gioco per tutti i players quando il gioco viene resettato o terminato
    protected void resetGameId(FileDisconnection fileDisconnection, GameImmutable model) {
        for (PlayerIC p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(), -1);
        }
    }

    //salvo l'ultimo ID del gioco associato al giocatore nel file di disconnessione
    protected void saveGameId(FileDisconnection fileDisconnection, String nick, int gameId) {
        fileDisconnection.setLastGameId(nick, gameId);
    }

    //gestisce gli errori di mancata connessione e deve essere implementato specificatamente per l'interfaccia utente che si utilizza (TUI o GUI)
    public abstract void noConnectionError();

}
