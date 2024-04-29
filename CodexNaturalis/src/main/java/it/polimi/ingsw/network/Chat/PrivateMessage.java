package it.polimi.ingsw.network.Chat;

import it.polimi.ingsw.model.player.Player;

public class PrivateMessage extends Message{
    private final String privateReceiver;

    /**
     * Constructor
     * @param text
     * @param sender
     * @param receiver
     */
    public PrivateMessage(String text, Player sender, String receiver){
        super(text,sender);
        this.privateReceiver=receiver;
    }

    /**
     *
     * @return the designed receiver for the private message
     */
    @Override
    public String whoIsReceiver(){
        return privateReceiver;
    }
}
