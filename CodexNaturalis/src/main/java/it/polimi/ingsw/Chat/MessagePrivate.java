package it.polimi.ingsw.Chat;

import it.polimi.ingsw.model.player.Player;

/**
 * The MessagePrivate class represents a private message sent by a player in the chat.
 */
public class MessagePrivate extends Message{

    private String receiverPrivate;

    /**
     * Constructor that initializes a private message with the given text, sender, and receiver.
     *
     * @param text     the text content of the message
     * @param sender   the player who sends the message
     * @param receiver the receiver of the private message
     */
    public MessagePrivate(String text, Player sender, String receiver){
        super(text,sender);
        this.receiverPrivate=receiver;
    }

    /**
     * Returns the designated receiver for the private message.
     *
     * @return the receiver of the private message
     */
    @Override
    public String whoIsReceiver(){
        return receiverPrivate;
    }
}
