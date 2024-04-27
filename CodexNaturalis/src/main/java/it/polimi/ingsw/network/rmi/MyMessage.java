package it.polimi.ingsw.network.rmi;

public class MyMessage implements Message{
    private final String content;

    public MyMessage(String s) {
        content = s;
    }

    public String getContent() {
        return content;
    }
}
