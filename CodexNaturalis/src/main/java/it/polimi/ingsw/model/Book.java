package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// BOOK: disposizione delle carte di ogni player
public class Book {

//    private ArrayList<PlayableCard> bookArray;

    private ArrayList<ArrayList<Cell>> bookMatrix; //matrice di celle
    private Map<ResourceType, Integer> resourceMap; //mappa di numero di risorse per tipo
    private Map<SymbolType, Integer> symbolMap; //n° di simboli per tipo
    // Costruttore

    //NOTA: passare max numero di righe e colonne che si vogliono istanziare
    public Book(int rows, int columns) {
        // Inizializza la matrice di celle con le dimensioni fornite
        this.bookMatrix = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> rowList = new ArrayList<>(columns);
            for (int j = 0; j < columns; j++) {
                rowList.add(new Cell(i, j));
            }
            this.bookMatrix.add(rowList);
        }

        // Inizializza le mappe di risorse e simboli
        this.resourceMap = new HashMap<>();
        this.symbolMap = new HashMap<>();
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

    showAvailablePosition(); // METODO CHE MOSTRA DOVE SI PUò PIAZZARE UNA CARTA

    public int addCard(PlayableCard newCard, Cell chosenCell) {
        //aggiunge una carta al book e restituisce se la carta ha punti
        //  chiama metodo UpdateCell() nella cella dove aggiungo la carta
    }

    public void updateBook(){
/* metodo che chiamo DOPO CHE è STATA PIAZZATA UNA CARTA:
    1) guardo il riferimento agli angoli
        ->  se l’angolo esiste -> Available = true
    2) imposto le celle intorno alla carta
    Available = false && cardPointer = null -> cella mai disponibile (X) (quelle lato carta dove non ci sono gli angoli)
    Available= true && CardPointer = null -> ANGOLO LIBERO dove piazzare una prossima carta cella disponibile e non c’è nessuna carta piazzata */
    }


    public void checknewCorner(){
    // metodo che controlla gli angoli della carta appena piazzata (newCard)
    // se c’è un simbolo/risorsa -> aggiorna mappa (+)
    }

    public void checkCoveredCorner() {
        /*
        metodo che controlla se l’angolo relativo
        alla cella dove ho piazzato(angolo coperto) la carta conteneva risorse/simboli
        -> se sì (sono stati coperti) -> decremento la mappa) */
    }


    // Metodi getter e setter
    public ArrayList<ArrayList<Cell>> getBookMatrix() {
        return bookMatrix;
    }

    public void setBookMatrix(ArrayList<ArrayList<Cell>> bookMatrix) {
        this.bookMatrix = bookMatrix;
    }

    public Map<ResourceType, Integer> getResourceMap() {
        return resourceMap;
    }

    public void setResourceMap(Map<ResourceType, Integer> resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Map<SymbolType, Integer> getSymbolMap() {
        return symbolMap;
    }

    public void setSymbolMap(Map<SymbolType, Integer> symbolMap) {
        this.symbolMap = symbolMap;
    }





}
