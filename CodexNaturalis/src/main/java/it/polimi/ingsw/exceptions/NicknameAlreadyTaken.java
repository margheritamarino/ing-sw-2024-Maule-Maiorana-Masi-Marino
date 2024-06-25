package it.polimi.ingsw.exceptions;

public class NicknameAlreadyTaken extends Exception{
    private String nickname;

    public NicknameAlreadyTaken(String nickname) {
        super("The nickname '" + nickname + "' is already taken.");
        this.nickname = nickname;
    }

    // Metodo per ottenere il nickname che ha causato l'eccezione
    public String getNickname() {
        return nickname;
    }
}
