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

                // Se il giocatore è impostato e il messaggio inizia con "/cs"
                if (player != null && inputData.startsWith("/cs")) {
                    //rimuovo il prefisso /cs, se il quarto carattere è uno spazio toglie tutti e 4 altrimenti solo i primi 3
                    inputData = inputData.charAt(3) == ' ' ? inputData.substring(4) : inputData.substring(3);

                    //se il messaggio contiene uno spazio singifica che ci sono destinatio e messaggio separati dallo spazio
                    if(inputData.contains(" ")) {
                        String receiver = inputData.substring(0, inputData.indexOf(" ")); //estrae destinatario (prima dello spazio)
                        String msg = inputData.substring(receiver.length() + 1);
                        gameFlow.sendMessage(new MessagePrivate(msg, player, receiver)); //messaggio privati inviato trsmite gameFlow
                    }
                }
                // Se il giocatore è impostato e il messaggio inizia con "/c"
                else if (player != null && inputData.startsWith("/c")) {
                    // Invia un messaggio generico
                    //rimuove i primi 3 caratteri e guarda al 4
                    inputData = inputData.charAt(2) == ' ' ? inputData.substring(3) : inputData.substring(2);
                    gameFlow.sendMessage(new Message(inputData, player));
                }
                // Se il messaggio inizia con "/quit" o "/leave"
                else if (inputData.startsWith("/quit") || inputData.startsWith("/leave")) {
                    // Esci dall'applicazione
                    System.exit(1);
                }
                // Se non si verifica nessuno dei casi precedenti, aggiungi i dati al buffer unprocessedDataBuffer
                else {
                    unprocessedDataBuffer.addInputData(inputData);
                }

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
