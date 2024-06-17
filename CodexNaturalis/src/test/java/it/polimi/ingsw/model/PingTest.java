package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PingTest {
    @Test
    public void testGetBeat() {
        // Setup
        Long expectedBeat = 12345L;
        String nick = "testPlayer";

        // Instantiate Ping object
        Ping ping = new Ping(expectedBeat, nick);

        // Assertion
        assertEquals(expectedBeat, ping.getBeat(), "getBeat() should return the correct heartbeat");
    }

    @Test
    public void testGetNick() {
        // Setup
        Long beat = 54321L;
        String expectedNick = "anotherPlayer";

        // Instantiate Ping object
        Ping ping = new Ping(beat, expectedNick);

        // Assertion
        assertEquals(expectedNick, ping.getNick(), "getNick() should return the correct nickname");
    }

    @Test
    public void testConstructorAndGetters() {
        // Setup
        Long beat = 99999L;
        String nick = "thirdPlayer";

        // Instantiate Ping object
        Ping ping = new Ping(beat, nick);

        // Assertions
        assertEquals(beat, ping.getBeat(), "Constructor should initialize beat correctly");
        assertEquals(nick, ping.getNick(), "Constructor should initialize nick correctly");
    }
}
