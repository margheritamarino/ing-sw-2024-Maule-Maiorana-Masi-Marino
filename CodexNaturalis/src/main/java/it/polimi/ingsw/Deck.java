package it.polimi.ingsw;


import java.util.ArrayList;

public class Deck {
/*
NOTA: in un deck ho un array per il front delle carte e un array per il back
        -> nella STESSA POSIZIONE dei due array trovo la STESSA CARTA
            (es. carta n.6 -> front in frontCardList[6] e back in backCardList[6])*/
    //private ArrayList<Card> frontCardList;
    //private ArrayList<Card> backCardList;
    private int numCards;
    private DeckType deckType;

    public Deck( DeckType deckType){
        //1 array per i front e 1 array per i back
        //this.frontCardList = new ArrayList<Card>();
        //this.backCardList = new ArrayList<Card>();
        this.deckType= deckType;

        // numero di carte varia in base al tipo di carta
        switch (deckType){
            case Gold, Resource -> {
                this.numCards = 40;
            }
            case Initial->{
                this.numCards=6;

            }
            case Objective -> {
                this.numCards = 16;
            }
        }
    }

    public void initializeDeck(Deck deck){
        if (deck!=null) {
            switch (deck.deckType){
                //DECK DELLE GOLD CARDS
                case (deck.deckType= Gold ) -> {
                    //istanzio arraylist per il FFRONT
                    for (int i = 1; i <= deck.numCards; i++) {
                        int cardID = i;
                        GoldCard newGoldFront = new GoldFront(cardID); //creo una carta del tipo GoldFront
                        //deck.frontCardList.add(newCard); //aggiungo la nuova carta all'arrayList dei front
                    //}
                    //for (int i = 0; i < deck.numCards; i++) {
                        GoldCard newGoldBack = new GoldBack(); //creo una carta del tipo GoldFront
                        //deck.backCardList.add(newCard); //aggiungo la nuova carta all'arrayList dei front
                    }
                }
                //DECK DELLE RESOURCE CARDS
                case (deck.deckType= Resource ) -> {
                    //istanzio arraylist per il FFRONT
                    for (int i = 41; i <= deck.numCards + 40; i++) {
                        //creo una carta del tipo GoldFront
                        int cardID = i;
                        ResourceCard newResourceFront = new ResourceFront(cardID); //MODIFICARE COSTRUTTORE
                        //deck.frontCardList.add(newCard); //aggiungo la nuova carta all'arrayList dei front
                    //}
                    //for (int i = 0; i < deck.numCards; i++) {
                        ResourceCard newResourceBack = new ResourceBack(cardID); //creo una carta del tipo GoldFront
                        //deck.backCardList.add(newCard); //aggiungo la nuova carta all'arrayList dei front
                    }
                }
            /*DECK DELLE OBJECTIVE
            SOLO array list del FRONT
            il BACK non è istanziato quindi BackCardList = null
         -->  RICORDA: quando accedi a un deck verificare se è diverso da null
            if (deck.backCardList != null)*/
                case (deck.deckType= Resource ) -> {
                    //istanzio arraylist per il FFRONT
                    for (int i = 81; i <= deck.numCards + 80; i++) {
                        int cardID = i;
                        ObjectiveCard newObjectiveFront = new ObjectiveFront(cardID); //creo una carta del tipo GoldFront
                        //deck.frontCardList.add(newCard); //aggiungo la nuova carta all'arrayList dei front
                    }
                }

                //DECK delle INITIAL
                case (deck.deckType= Initial ) -> {
                    //istanzio arraylist per il FFRONT
                    for (int i = 98; i <= deck.numCards + 97; i++) {
                        int cardID = i;
                        InitialCard newInitialFront = new InitialFront(cardID); //creo una carta del tipo GoldFront
                        //deck.frontCardList.add(newCard); //aggiungo la nuova carta all'arrayList dei front
                    //}
                    //for (int i = 0; i < deck.numCards; i++) {
                        InitialCard newInitialBack = new InitialBack(cardID); //creo una carta del tipo GoldFront
                        //deck.backCardList.add(newCard); //aggiungo la nuova carta all'arrayList dei front
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + deck.deckType);
            }
        }
        else
            throw new IllegalArgumentException("Deck non può essere null");
    }

    public void shuffle(){ //metodo che mescola le carte del deck
    //implEMENTA

    }

    public boolean isEmpty(Deck deck){ //verifica se il mazzo è vuoto
      //implementa
        return false;
    }
    public Card drawCard(){ //NOTA: RESTITUISCE CHE TIPO DI CARTA??
        //metodo che restituisce una carta pescata RANDOM dal deck rispettivo->
        // per ogni tipo di deck deve restituire il tipo di carta corrispondente
        // decrementa il numero di carte del deck
    }

    public Card pickCard(Deck deck, Player player) {
        //il giocatore pesca (in mano) -> aggiunge al suo playerDeck la prima carta e
        // decrementa il numero di carte del deck

    }

    public void addBoard() { // metodo da aggiungere al BOARD ???
        //quando il giocatore pesca una carta dal board,
        // il board si deve aggiornare aggiungendo una carta del deck del tipo che il player ha preso
        //il deck si deve aggiornare rimuovendo una carta -> METODO PER RIMUOVERE CARTA DAL DECK??
    }

    public ArrayList<Card> getFrontCardList(){ //getter della lista dei front
        if (frontCardList!= null)
            return frontCardList;
        return null;
    }
    public ArrayList<Card> getBackCardList(){ //getterLista dei back
        if (backCardList!= null)
            return backCardList;
        return null;
    }
}
