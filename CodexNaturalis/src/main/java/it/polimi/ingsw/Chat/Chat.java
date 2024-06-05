package it.polimi.ingsw.Chat;

import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The Chat class manages a list of messages exchanged between players.
 */
public class Chat implements Serializable {
    private List<Message> msgs;

    /**
     * Default constructor that initializes an empty list of messages.
     */
    public Chat() {
        msgs = new ArrayList<>();
    }

    /**
     * Constructor that initializes the chat with a given list of messages.
     *
     * @param msgs the list of messages
     */
    public Chat(List<Message> msgs) {
        this.msgs = msgs;
    }

    /**
     * Returns the list of messages.
     *
     * @return msgs
     */
    public List<Message> getMsgs() {
        return msgs;
    }


    /**
     * Adds a message to the list. If the list exceeds the maximum number of messages allowed,
     * it removes the oldest message.
     *
     * @param m the message to add
     */
    public void addMsg(Message m) {
        if (msgs.size() >= DefaultValue.max_messagesShow)
            msgs.remove(0);
        msgs.add(m);
    }

    /**
     * Creates a message with a sender and text, then adds it to the list. If the list exceeds
     * the maximum number of messages allowed, it removes the oldest message.
     *
     * @param sender the player sending the message
     * @param text   the text of the message
     */
    public void addMsg(Player sender, String text) {
        Message temp = new Message(text, sender);
        if (msgs.size() >= DefaultValue.max_messagesShow)
            msgs.remove(0);
        msgs.add(temp);
    }


    /**
     * @return the last message as a string.
     */
    public String getLast() {
        return msgs.get(msgs.size() - 1).toString();
    }

    /**
     * @return the last message in message form
     */
    public Message getLastMessage() {
        return msgs.get(msgs.size() - 1);
    }

    /**
     * Sets the list of messages
     *
     * @param msgs messages
     */
    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    /**
     * @return the chat as a string
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        int i = 0;
        int len = this.getMsgs().stream()
                .map(Message::getText)
                .max(Comparator.comparingInt(String::length))
                .orElse("")
                .length();
        for (Message m : msgs) {
            ret.append(m.toString(i, len, false)).append("\n");
            i++;
        }
        return ret.toString();
    }

    /**
     * Returns the private chat as a string for a specific user.
     *
     * @param privateMsgByNickname the nickname of the user
     * @return the private chat as a string for the specified user
     */
    public String toString(String privateMsgByNickname) {
        StringBuilder ret = new StringBuilder();
        int i = 0;
        int len = this.getMsgs().stream()
                .map(Message::getText)
                .max(Comparator.comparingInt(String::length))
                .orElse("")
                .length();

        for (Message m : msgs) {
            boolean isPrivate = !m.whoIsReceiver().equals("*") &&
                    (m.getSender().getNickname().equals(privateMsgByNickname) ||
                            m.whoIsReceiver().equals(privateMsgByNickname));
            ret.append(m.toString(i, len, isPrivate)).append("\n");
            i++;
        }
        return ret.toString();
    }
}
