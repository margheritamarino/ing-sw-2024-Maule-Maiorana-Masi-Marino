package it.polimi.ingsw;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DefaultValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.Chat.Chat;
import it.polimi.ingsw.Chat.Message;


import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChatTest {

    Player player1;
    Player player2;

    @BeforeEach
    void setUp() {
        player1 = new Player("p1", Color.RED);
        player2 = new Player("p2", Color.BLUE);

        DefaultValue.max_messagesShow = 5; // Imposta il valore massimo di messaggi mostrati
    }

    @Test
    @DisplayName("Test for the chat")
    void chatTest() {
        Chat chat = new Chat();
        Message message = new Message("It's a test", player1);

        chat.addMsg(message);
        assert (chat.getLastMessage().getText().equals("It's a test"));
        assert (chat.getLastMessage().getSender().getNickname().equals("p1"));
        //verifico che l'orario dell'ultimo messaggio non sia nullo
        assert (chat.getLastMessage().getTime() != null);
        chat.addMsg(player2, "Test2");
        assert (chat.getLastMessage().getText().equals("Test2"));
        assert (chat.getLastMessage().getSender().getNickname().equals("p2"));
        assert (chat.getLastMessage().getTime() != null);

        chat.addMsg(player2, "Test3");
        chat.addMsg(player2, "Test4");
        chat.addMsg(player2, "Test5");
        chat.addMsg(player2, "Test6");

        assert(chat.getLastMessage().getText().equals("Test6"));
        assert(chat.getLast().length() > 0);

        //verifico che l'intera chat non sia vuota
        assert(chat.toString().length()>0);
        //verifico che la chat privata per il player1 non sia vuota
        assert (chat.toString(player1.getNickname()).length() > 0);


        //primo messaggio dovrebbe essere stato rimosso
        List<Message> messages = chat.getMsgs();
        assertEquals(DefaultValue.max_messagesShow, messages.size());
        assertEquals("Test2", messages.get(0).getText());
    }
}
