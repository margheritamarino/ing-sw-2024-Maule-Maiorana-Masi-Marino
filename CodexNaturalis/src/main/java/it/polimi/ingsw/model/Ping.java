package it.polimi.ingsw.model;


    /** Heartbeat's class<br>
      This class implements the method we used to ping the server<br>
 * to let him know that we are still connected (needed for rmi connection)<br>
 */
public class Ping {
    private final Long beat;
    private final String nick;

    /**
     * Constructor
     *
     * @param beat
     * @param nick
     */
    public Ping(Long beat, String nick) {
        this.beat = beat;
        this.nick = nick;
    }

    /**
     * @return the heartbeat
     */
    public Long getBeat() {
        return beat;
    }

    /**
     * @return the nickname of the player pinging the server
     */
    public String getNick() {
        return nick;
    }

}
