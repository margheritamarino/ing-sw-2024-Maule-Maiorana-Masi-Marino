package it.polimi.ingsw.model;

public class Cell {
    private int row; // indice della matrice
    private int column; // indice della matrice

    private boolean available;
    //dice se la cella è disponibile per il piazzamento della carta.
    // inizialmente setto tutto a false -> diventa true se c’è angolo nella carta piazzata
    private PlayableCard cardPointer;
    //puntatore alla carta posizionata sulla cella
    // ->  mi serve perchè da questa guardo gli angoli che ha la carta
    // e modifico le celle adiacenti

    // COSTRUTTORE
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.available = false; // Inizialmente la cella non è disponibile
        this.cardPointer = null; // Inizialmente non c'è nessuna carta posizionata sulla cella
    }


    //metodo chiamato DOPO che ho piazzato una carta in questa cella
    // ?? CONTROLLA

    public void updateCell(PlayableCard newCard){
        this.available = false;
        this.cardPointer = newCard;
    }


    // Metodi getter e setter
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public PlayableCard getCard() { //RESTITUISCE LA CARTA POSIZIONATA NELLA CELLA
        return cardPointer;
    }

    public void setCardPointer(PlayableCard cardPointer) {
        this.cardPointer = cardPointer;
    }




}
