package it.polimi.ingsw.view.Utilities;

import it.polimi.ingsw.Chat.Message;
import it.polimi.ingsw.Chat.MessagePrivate;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.flow.GameFlow;


/**
 * Manages user input and coordinates message sending within the game.
 * This class extends Thread and continuously processes user input from an input buffer
 */
public class InputController extends Thread{

    private final Buffer inputBuffer;
    private final Buffer unprocessedDataBuffer;
    private final GameFlow gameFlow;
    private Player player;
    private Integer gameID;

    /**
     * Constructs an InputController with the specified input buffer and GameFlow instance.
     *
     * @param inputBuffer The buffer containing incoming user input data.
     * @param gameFlow The GameFlow instance responsible for handling game-related operations.
     */
    public InputController(Buffer inputBuffer, GameFlow gameFlow) {
        this.inputBuffer = inputBuffer;
        unprocessedDataBuffer = new Buffer();
        this.gameFlow = gameFlow;
        this.player = null;
        this.gameID = null;
        this.start();
    }

    /**
     * Continuously processes user input from the input buffer.
     * This method listens for user commands and messages, directing them to the appropriate processing methods in GameFlow.
     */
    public void run() {
        String inputData;
        while (!this.isInterrupted()) {
            try {
                inputData = inputBuffer.popInputData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (player != null && inputData.startsWith("/cs")) {
                inputData = inputData.charAt(3) == ' ' ? inputData.substring(4) : inputData.substring(3);
                if(inputData.contains(" ")){
                    String receiver = inputData.substring(0, inputData.indexOf(" "));
                    String msg = inputData.substring(receiver.length() + 1);
                    gameFlow.sendMessage(new MessagePrivate(msg, player, receiver));
                }
            } else if (player != null && inputData.startsWith("/c")) {
                inputData = inputData.charAt(2) == ' ' ? inputData.substring(3) : inputData.substring(2);
                gameFlow.sendMessage(new Message(inputData, player));

            } else if (inputData.startsWith("/quit")) {
                assert player != null;
                System.exit(1);
                gameFlow.leave(player.getNickname());
                gameFlow.playerLeftForGameEnded();

            } else {
                 unprocessedDataBuffer.addInputData(inputData);
            }
        }
    }

    /**
     * Sets the current player associated with this InputController.
     *
     * @param player The player to set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets the current game ID associated with this InputController.
     *
     * @param gameID The game ID to set.
     */
    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    /**
     * Retrieves the buffer containing unprocessed data for further handling by GameFlow.
     *
     * @return The Buffer instance containing unprocessed data.
     */
    public Buffer getUnprocessedData() {
        return unprocessedDataBuffer;
    }


}
