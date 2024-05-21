package it.polimi.ingsw.view.Utilities;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.Chat.MessagePrivate;
import it.polimi.ingsw.model.interfaces.PlayerIC;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.Utilities.Buffer;
import it.polimi.ingsw.view.flow.GameFlow;


//classe che serve per gestire l'input dell'utente e per coordinare l'inivio di messaggi all'interno del gioco

public class InputController extends Thread{

    private final Buffer inputBuffer; //buffer da cui la classe estrae i dati in entrata degli utenti da elaborare
    private final Buffer unprocessedDataBuffer; //conserva temporaneamente i dati che devono essere processati dal GameFlow perché non presenti nell' inputController

    private final GameFlow gameFlow;

    private Player player;
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //I popped an input from the buffer
            if (player != null && inputData.startsWith("/cs")) {
                inputData = inputData.charAt(3) == ' ' ? inputData.substring(4) : inputData.substring(3);
                if(inputData.contains(" ")){
                    String receiver = inputData.substring(0, inputData.indexOf(" "));
                    String msg = inputData.substring(receiver.length() + 1);
                    gameFlow.sendMessage(new MessagePrivate(msg, player, receiver));
                }
            } else if (player != null && inputData.startsWith("/c")) {
                //I send a message
                inputData = inputData.charAt(2) == ' ' ? inputData.substring(3) : inputData.substring(2);
                gameFlow.sendMessage(new Message(inputData, player));

            } else if (inputData.startsWith("/quit")) {
                assert player != null;
                System.exit(1);
                gameFlow.leave(player.getNickname());
                gameFlow.playerLeftForGameEnded();

            } else {
                //I add the data to the buffer processed via GameFlow
                 unprocessedDataBuffer.addInputData(inputData);
            }
        }
    }

    public void setPlayer(Player player) {
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
