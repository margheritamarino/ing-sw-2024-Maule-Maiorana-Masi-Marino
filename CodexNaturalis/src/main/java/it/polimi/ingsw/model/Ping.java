package it.polimi.ingsw.model;


/**
 * Ping class for RMI connection.
 * This class represents a ping sent from the client to the server
 * to indicate that the client is still connected.
 */
public class Ping {
    private final Long beat;
    private final String nick;

    /**
     * Constructs a Ping object with a ping and nickname.
     *
     * @param beat The ping value.
     * @param nick The nickname of the player pinging the server.
     */
    public Ping(Long beat, String nick) {
        this.beat = beat;
        this.nick = nick;
    }

    /**
     * Retrieves the ping value.
     *
     * @return The ping value.
     */
    public Long getBeat() {
        return beat;
    }

    /**
     * Retrieves the nickname of the player pinging the server.
     *
     * @return The nickname of the player.
     */
    public String getNick() {
        return nick;
    }

}
