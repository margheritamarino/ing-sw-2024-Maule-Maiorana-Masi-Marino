package it.polimi.ingsw.view.Utilities;

import it.polimi.ingsw.model.interfaces.PlayerIC;
import it.polimi.ingsw.view.Utilities.Buffer;
import it.polimi.ingsw.view.flow.GameFlow;


//classe che serve per gestire l'input dell'utente e per coordinare lìinivio di messaggi all'interno del gioco

public class InputController extends Thread{

    private final Buffer inputBuffer; //buffer da cui la classe estrae i dati in entrata degli utenti da elaborare
    private final Buffer unprocessedDataBuffer; //conserva temporaneamente i dati che devono essere processati dal GameFlow perché non presenti nell' inputController

    private final GameFlow gameFlow;

    private PlayerIC player;
    private Integer gameID;

    /**
     * Constructor
     * @param inputBuffer
     * @param gameFlow
     */
    public InputController(Buffer inputBuffer, GameFlow gameFlow) {
        this.inputBuffer = inputBuffer;
        unprocessedDataBuffer = new Buffer();
        this.gameFlow = gameFlow;
        this.player = null;
        this.gameID = null;
        this.start();
    }

    public void run() {
        String inputData;
        while (!this.isInterrupted()) { //thread rimane in attesa finché non viene interrotto
            try {
                // Estrai dati dall'inputBuffer
                inputData = inputBuffer.popInputData();

                //manca la chat
                unprocessedDataBuffer.addInputData(inputData);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setPlayer(PlayerIC player) {
        this.player = player;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    //metodo per ritornare dati non elaborati del buffer unprocessed
    public Buffer getUnprocessedData() {
        return unprocessedDataBuffer;
    }


}
