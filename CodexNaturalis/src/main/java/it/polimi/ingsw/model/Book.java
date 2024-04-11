package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// BOOK: disposizione delle carte di ogni player
public class Book {

//    private ArrayList<PlayableCard> bookArray;

    //private ArrayList<ArrayList<Cell>> bookMatrix; //matrice di celle
    private Cell[][] bookMatrix; //matrice di celle
    private Map<ResourceType, Integer> resourceMap; //mappa di numero di risorse per tipo
    private Map<SymbolType, Integer> symbolMap; //n° di simboli per tipo
    // Costruttore


    /*
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

     */

    public Book(int rows, int columns, PlayableCard card){
        // Inizializza le mappe di risorse e simboli
        this.resourceMap = new HashMap<>();
        this.symbolMap = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                assert false;
                bookMatrix[i][j] = new Cell(i, j); // Costruttore che inizializza righe e colonne e imposta isAvailable a false
            }
        }
    }

    public int addCard(PlayableCard card, Cell cell){ //metodo che piazza le carte nel gioco e restituisce i punti di quella carte (se non ha punti restituisce 0)
        if (cell.isAvailable()){
            cell.setCardPointer(card); //setto il puntatore della cella alla carta che ho appena piazzato
            cell.setAvailable(false); //setto disponibilità cella a false
            updateCoveredCorners(cell);


        }

    }

    public boolean updateCoveredCorners(Cell cell){
        boolean cornerCovered = false;
        if(cell.getCard()!=null){
           int i = cell.getRow();
           int j = cell.getColumn();
           if((bookMatrix[i-1][j-1].getCard() != null && bookMatrix[i-1][j-1].getCard().getBRCorner().equals("WithSymbol") || bookMatrix[i-1][j-1].getCard().getBRCorner().equals("WithResource")))

       }
        return cornerCovered;
    }

    public boolean checkCorner(int i, int j) { //funzione che verifica se, posizionata una carta sulla cella i,j, questa copre gli angoli di altre carte posizionate in gioco
        if ((bookMatrix[i - 1][j - 1].getCard() != null && bookMatrix[i - 1][j - 1].getCard().getBRCorner().equals("WithSymbol") || bookMatrix[i - 1][j - 1].getCard().getBRCorner().equals("WithResource"))){
        }
    }



    // Metodo per creare una mappa che conta il numero di RISORSE per ogni tipo, presenti negli angoli
    public void updateMap (Map<ResourceType, Integer> resourceMap, Map<SymbolType, Integer> symbolMap, PlayableCard newCard){
        //metodo che quando AGGIUNGO una carta al tabellone aggiorna le rispettive mappe in base
        // al numero di risorse e simboli nella nuovaCarta
        // se una carta viene coperta vengono TOLTE le risorse/simboli dell'angolo coperto
    }


    public void clear(){
        //RIMUOVE TUTTE LE CARTE DAL BOOK
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


    /*// Metodi getter e setter
    public ArrayList<ArrayList<Cell>> getBookMatrix() {
        return bookMatrix;
    }

    public void setBookMatrix(ArrayList<ArrayList<Cell>> bookMatrix) {
        this.bookMatrix = bookMatrix;
    }
*/
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
