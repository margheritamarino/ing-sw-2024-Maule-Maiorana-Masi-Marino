package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Map;

// BOOK: disposizione delle carte di ogni player
public class Book {
    private Player player; //riferimento al player di cui è il book
    private ArrayList<Card> bookArray;  //arra

    private Map<ResourceType, Integer> resourceMap; //mappa di numero di risorse per tipo
    private Map<SymbolType, Integer> symbolMap; //n° di simboli per tipo

    public Book(Player player) {
        this.player = player;
        this.bookArray = new ArrayList<>();
    }

    //METODI GETTER
    public ArrayList<Card> getBookArray() { //ritorna l'array di carte
        return this.bookArray;
    }

    public Player getPlayer() {
        return player;
    }

    public int bookSize(){ //ritorna il numero di carte della lista (presenti nel book)

    }

    public void addCard(Card card){ // !!!METODO CHE PIAZZA LE CARTE NEL GIOCO
        //aggiunge una carta al book -> COSTRUISCE IL BOOK ARRA
        /* ricorda che è una lista di CARTE
        - posizione
        - newCard = new tipoCard()
        - add(newCArd)
         */
    }

    public Card getCard(int pos){ //RIVEDERE I PARAMETRI
        //restituisce la carta nella poszione indicata in pos

    }

    // Metodo per creare una mappa che conta il numero di RISORSE per ogni tipo, presenti negli angoli
    public void updateMap (Map<ResourceType, Integer> resourceMap, Map<SymbolType, Integer> symbolMap, Card newCard){
        //metodo che quando AGGIUNGO una carta al tabellone aggiorna le rispettive mappe in base
        // al numero di risorse e simboli nella nuovaCarta
        // se una carta viene coperta vengono TOLTE le risorse/simboli dell'angolo coperto
    }


    public void clear(){
        //RIMUOVE TUTTE LE CARTE DAL BOOK
    }


}
