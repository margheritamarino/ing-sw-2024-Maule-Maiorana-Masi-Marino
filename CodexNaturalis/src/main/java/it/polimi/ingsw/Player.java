package it.polimi.ingsw;

import javax.swing.plaf.synth.ColorType;
import java.util.ArrayList;
import java.util.Random;

public class Player {

    private String nickname;
    private ColorType color;
    private PlayerState state; // CHI DECIDE LO STATO DEI GIOCATORI?
    private PlayerDeck playerDeck;
    private Book playerBook;

//    private boolean firstPlayer; -> a cosa serve??
    private ObjectiveFront playerGoal; //carta OBBIETTIVO che ha il player


    public Player(String nickname, ColorType color) {
        this.nickname = nickname;
        this.color = color;
        this.playerGoal = new ObjectiveFront();
        this.state = PlayerState.Start; // Imposta lo stato iniziale a "Start"
        this.playerBook = new Book();
        this.playerDeck = new PlayerDeck();
    };

    public void setPlayerState(Player player, PlayerState state) {
         player.state=state;
    }
    public PlayerState getPlayerState(Player player) {
        return player.state;
    }

 //il game passa la carta scelta fra le 2 objectiveCard e setta il goal
    public void setGoal(ObjectiveFront chosenCard) {
        this.playerGoal=  chosenCard;
    }


    //IMPLEMENTA
    public ObjectiveFront chooseObjectiveCard(ArrayList<ObjectiveFront> drawnObjectiveCards){
        //metodo che prende in ingresso le 2 carte Obbiettivo pescate dal player
        // mostra a video le due carte
        //chiede al player quale vuole
        //restituisce la carta scelta dal player fra le due
    }

/**
 * @return  type ObjectiveCard -> rapresenting player's goal */
    public ObjectiveFront getGoal() {
        return playerGoal;
    }

    /**
     *
     * @return player's nickname
     */
    public String getNickname(){
        return this.nickname;
    }

    public void addCard(Card newcard) {
        //IMPLEMENTA METODO che aggiunge la nuova carta passata al PlayerDeck
    }
    public void pickCard(Board board){
        //IMPLEMENTA
        /*  METODO che fa fa pescare il giocatore dal Board
            e aggiorna il PlayerDeck */
    }

    public void placeCard(Card chosenCard){
        //IMPLEMENTA metodo che aggiunge la carta selta tra le 3 del playerDeck
        // al Book del player
        // scelta posizione ?
    }

    public void showPlayerDeck(){
        //IMPLEMENTA METODO CHE STAMPA LE CARTE DEL PLAYERDECK
    }

    public void showPlayerBook(){
        //IMPLEMENTA METODO CHE STAMPA LE CARTE presenti nel BOOK
    }


}
