package it.polimi.ingsw.Chat;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {

    private String text;
    private Player sender;
    private LocalTime time;

    public Message(String text, Player sender){
        this.text = text;
        this.sender = sender;
        this.time = LocalTime.now();
    }

    public Message() {
        this.text = null;
        this.sender = null;
        this.time = null;
    }

    /**
     *
     * @return the message's text content
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text in the message to the param
     * @param text text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     *
     * @return the message's sender
     */
    public Player getSender() {
        return sender;
    }

    /**
     * Sets the message's sender
     * @param sender sender
     */
    public void setSender(Player sender) {
        this.sender = sender;
    }

    /**
     *
     * @return the message's time of sending
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the message time to the parameter
     * @param time param
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     *
     * @return the receiver
     */
    public String whoIsReceiver() {
        return "*";
    }

    /**
     * Returns the message in string format
     * @param i
     * @param len
     * @param isPrivate
     * @return
     */
    public String toString(int i, int len, boolean isPrivate) {
        String padding = " ".repeat(Math.max(0, (len - text.length())));
        String priv = isPrivate ? "[Private] " : "";
        String timestamp = String.format("[%02d:%02d:%02d] ", time.getHour(), time.getMinute(), time.getSecond());
        String nickname = sender.getNickname().length() > 4 ? sender.getNickname().substring(0, 4) + "." : sender.getNickname();
        return priv + timestamp + nickname + ": " + text + padding;

    }



}
