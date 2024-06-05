package it.polimi.ingsw.Chat;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * The Message class represents a message sent by a player in the chat.
 */
public class Message implements Serializable {

    private String text;
    private Player sender;
    private LocalTime time;

    /**
     * Constructor that initializes a message with the given text and sender.
     * The time of the message is set to the current time.
     *
     * @param text   the text content of the message
     * @param sender the player who sends the message
     */
    public Message(String text, Player sender) {
        this.text = text;
        this.sender = sender;
        this.time = LocalTime.now();
    }

    /**
     * Default constructor that initializes a message with null values.
     */
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
     * Set the text content of the message.
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
     * Sets the message's sender.
     * @param sender the sender to set
     */
    public void setSender(Player sender) {
        this.sender = sender;
    }

    /**
     * Returns the time the message was sent.
     * @return the message's time of sending
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the message time to the parameter.
     * @param time the time to set
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     *
     * @return the receiver of the message
     */
    public String whoIsReceiver() {
        return "*";
    }

    /**
     * Returns the message in string format.
     *
     * @param i the index of the message in chat
     * @param len the maximum length of the message text
     * @param isPrivate wheter the message is private
     * @return the message
     */
    public String toString(int i, int len, boolean isPrivate) {
        String padding = " ".repeat(Math.max(0, (len - text.length())));
        String priv = isPrivate ? "[Private] " : "";
        String timestamp = String.format("[%02d:%02d:%02d] ", time.getHour(), time.getMinute(), time.getSecond());
        String nickname = sender.getNickname().length() > 4 ? sender.getNickname().substring(0, 4) + "." : sender.getNickname();
        return priv + timestamp + nickname + ": " + text + padding;

    }



}
