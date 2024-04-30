package it.polimi.ingsw.model.Chat;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {

    public final static int max_messagesShown = 5;

    private List<Message> msgs;

    /**
     * Constructor
     */
    public Chat() {
        msgs = new ArrayList<>();
    }

    /**
     * Constructor
     * @param msgs
     */
    public Chat(List<Message> msgs) {
        this.msgs = msgs;
    }

    /**
     *
     * @return the list of messages
     */
    public List<Message> getMsgs() {
        return msgs;
    }

    /**
     * Adds a message
     * @param m message param
     */
    public void addMsg(Message m) {
        if (msgs.size() > max_messagesShown)
            msgs.remove(0);
        msgs.add(m);
    }

    /**
     * Adds a message
     * @param sender message param
     * @param text message param
     */
    public void addMsg(Player sender, String text) {
        Message temp = new Message(text, sender);

        if (msgs.size() > max_messagesShown)
            msgs.remove(0);
        msgs.add(temp);
    }

    /**
     *
     * @return the last message in string form
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
     * Sets the chat messages
     *
     * @param msgs messages
     */
    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    /**
     * @return the chat in string form
     */
    @Override
    public String toString() {
       return null;
    }

    /**
     * @param privateMsgByNickname
     * @return the private chat in string form
     */
    public String toString(String privateMsgByNickname) {
        return null;
    }
}
