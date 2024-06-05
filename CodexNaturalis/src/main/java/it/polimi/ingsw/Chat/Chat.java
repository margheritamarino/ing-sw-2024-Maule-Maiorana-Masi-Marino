package it.polimi.ingsw.Chat;

import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Chat implements Serializable {
    private List<Message> msgs;

    public Chat() {
        msgs = new ArrayList<>();
    }

    // Costruttore che accetta una lista di messaggi esistenti
    public Chat(List<Message> msgs) {
        this.msgs = msgs;
    }

    public List<Message> getMsgs() {
        return msgs;
    }

    // Aggiunge un messaggio alla lista e se è piena li rimuove
    public void addMsg(Message m) {
        if (msgs.size() >= DefaultValue.max_messagesShow)
            msgs.remove(0);
        msgs.add(m);
    }

    // Crea un messaggio con un mittente e un testo
    public void addMsg(Player sender, String text) {
        Message temp = new Message(text, sender);
        if (msgs.size() >= DefaultValue.max_messagesShow)
            msgs.remove(0);
        msgs.add(temp);
    }

    /**
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
     * Imposta i messaggi della chat
     *
     * @param msgs messages
     */
    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    /**
     * @return la chat in formato stringa
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
     * @param privateMsgByNickname
     * @return la chat privata in formato stringa
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
