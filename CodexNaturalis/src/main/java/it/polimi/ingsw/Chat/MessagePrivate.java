package it.polimi.ingsw.Chat;

import it.polimi.ingsw.model.player.Player;

public class MessagePrivate extends Message{

    private String receiverPrivate;

    /**
     * Constructor
     * @param text
     * @param sender
     * @param receiver
     */
    public MessagePrivate(String text, Player sender, String receiver){
        super(text,sender);
        this.receiverPrivate=receiver;
    }

    /**
     *
     * @return the designed receiver for the private message
     */
    @Override
    public String whoIsReceiver(){
        return receiverPrivate;
    }
}
