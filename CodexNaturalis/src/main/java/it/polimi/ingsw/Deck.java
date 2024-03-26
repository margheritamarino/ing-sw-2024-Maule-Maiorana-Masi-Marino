package it.polimi.ingsw;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private int numCards;
    private final DeckType deckType;
    private static ArrayList<Integer> cardIds;

    public Deck( DeckType deckType){
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
        this.cardIds= new ArrayList<>(numCards);
    }

    public void initializeDeck(){
        switch (deckType) {

            //GoldCards' deck -> CardIds: 1 - 40
            case Gold:
                for (int i = 1; i <= numCards; i++) {
                    //riempio l'array di GoldCard con i cardIds da 1 a 40
                    cardIds.add(i);
                }
            break;
            //ResourceCards' deck -> CardIds: 41 - 80
            case Resource:
                for (int i = 41; i <= numCards + 40; i++) {
                    cardIds.add(i);

                }
            break;
            //ObjectiveCards's deck-> CardIds: 81 - 97
            case Objective:
                for (int i = 81; i <= numCards + 80; i++) {
                    cardIds.add(i);
                }
            break;

            //InitialCArds's deck-> CardIds: 98 - 103
            case Initial:
                for (int i = 98; i <= numCards + 97; i++) {
                    cardIds.add(i);
                }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + deckType);
        }
    }

    public void shuffle(){ //metodo che mescola le carte del deck
    //non so se serve

    }
    public boolean checkEndDeck(){
        if(cardIds!=null){
            if(numCards>0)
                return true;
            else
                return false;
        }
        else
            throw new IllegalArgumentException("Initialized Deck");
    }

    //IDEA: creare la Enum CardType che identifica il tipo di carta che voglio creare e restituisco quella
        public static Card drawCard(DeckType deckType) {
            Random random = new Random();
            int randomIndex = random.nextInt(cardIds.size());  // Generazione di un indice casuale
            int cardId = cardIds.get(randomIndex); // Recupero del valore corrispondente all'indice casuale
            switch (deckType) {
                case Objective:
                    return new ObjectiveFront(cardId);
                    break;
                case Resource:
                    return new ResourceFront(cardId); //COME FACCIO A TORNARE ENTRAMBE
                    return new ResourceBack(cardId);
                    break;
                case Initial:
                    return new InitialCard(cardId) ; //NON SI PUO PERCHE E' ASTRATTA
                case Gold:

                     break;
                default:
                    throw new IllegalArgumentException("Tipo di carta non supportato: " + cardType);
            }
        }

//oppure creo un metodo per ogni sottotipo
    public ObjectiveFront drawObjectiveCard(){
        Random random = new Random();
        int randomIndex = random.nextInt(cardIds.size());  // Generazione di un indice casuale
        int cardId = cardIds.get(randomIndex); // Recupero del valore corrispondente all'indice casuale

        ObjectiveFront newCard= new ObjectiveFront(cardId); //controllare costruttore
        return newCard;
    }

}
