package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {

    private String nickname;
    private PlayerColor color;
    private PlayerState state;
    private PlayerDeck playerDeck;
    private Book playerBook;


    //    private boolean firstPlayer; -> a cosa serve??
    private ObjectiveCard playerGoal; //identifica l'obbiettivo che ha il player

    // CHI DECIDE LO STATO DEI GIOCATORI?
    public Player(String nickname, PlayerColor color) {
        this.nickname = nickname;
        this.color = color;
        this.playerGoal = new ObjectiveCard();
        this.state = PlayerState.Start; // Imposta lo stato iniziale a "Start"
        this.playerBook = new Book();
        this.playerDeck = new PlayerDeck();
    };

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return nickname;
    }

    public void setColor(PlayerColor color){
        this.color = color;
    }

    public PlayerColor getColor(){
        return color;
    }
    public void setPlayerState(Player player, PlayerState state) {
        player.state=state;
    }
    public PlayerState getPlayerState(Player player) {
        return player.state;
    }

    //il game passa la carta scelta fra le 2 objectiveCard e setta il goal
    public void setGoal(ObjectiveCard chosenCard) {
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
     * @param player
     * @return  type ObjectiveCard -> rapresenting player's goal */
    public ObjectiveCard getGoal(Player player) {
        return player.playerGoal;
    }

    /**
     * @param player
     * @return player's nickname
     */
    public String getNickname(Player player){
        return player.nickname;
    }

    public void addCard(Card newcard) {
        //IMPLEMENTA METODO che aggiunge la nuova carta passata al PlayerDeck
    }
    public PlayableCard pickCard(){
        //IMPLEMENTA
        /*  METODO che fa fa pescare il giocatore dal Board
            e aggiorna il PlayerDeck */
    }

    public void placeCard(Card chosenCard){
        //Igiocatore sceglie la carta tra le 3 del playerDeck
        //
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
