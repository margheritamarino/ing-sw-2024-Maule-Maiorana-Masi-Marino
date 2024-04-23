package it.polimi.ingsw.exceptions;

public class MaxPlayersInException extends Exception {
    public MaxPlayersInException() {
        super("Max number of players is reached");
    }
}
