package it.polimi.ingsw;
import java.util.*;




public class ResourceFront extends ResourceCard {
    final int VictoryPoints;

    /**
     * @author Martina Maiorana
     * constructor of Card type ResourceFront: it can generate randomically
     * 3 different types of Resource Front cards
     * @param cardID id of the Card
     * @throws IllegalStateException if random function generate a number out of range
     */
    public ResourceFront(int cardID) {

        super(cardID); // Chiama il costruttore della classe genitore per inizializzare l'ID della carta
        setNumCorners(3); //tutti i sottotipi di ResourceFront hanno 3 angoli
        setRandomCornersMap(3);

        Set<Corner> cornerSet = getCornersMap().keySet(); // Ottieni l'insieme delle chiavi

        Corner[] cornerArray = new Corner[3];// Creazione di un array di Corner di dimensione 3
        Iterator<Corner> iterator = cornerSet.iterator();

        for (int i = 0; i < 3 && iterator.hasNext(); i++) {
            cornerArray[i] = iterator.next();// Inserisci i primi 3 valori delle chiavi nell'array di Corner
        }
        // Ora cornerArray contiene i primi 3 valori delle chiavi della mappa cornersMap (i 3 Corner creati)

        Random random = new Random();
        int caseNumber = random.nextInt(3); // Genera un numero casuale tra 0 e 2

        switch (caseNumber) {

            case 0:
                VictoryPoints = 0;
                setNumResources(2);
                setNumSymbols(0);
                cornerArray[0].setResource(this.getMainResource());
                cornerArray[1].setResource(this.getMainResource());
                addKingdom(this.getMainResource());
                addKingdom(this.getMainResource());//aggiunge le risorse alla lista di resource di card (Kingdom)
                break;

            case 1:
                VictoryPoints = 1;
                setNumResources(1);
                setNumSymbols(0);
                cornerArray[0].setResource(this.getMainResource());
                addKingdom(this.getMainResource());
                break;

            case 2:
                VictoryPoints = 0;
                setNumResources(2);
                setNumSymbols(1);
                cornerArray[0].setResource(this.getMainResource());
                ResourceType randomExcludingMain = getMainResource().getRandomResourceTypeExcluding(getMainResource());
                cornerArray[1].setResource(randomExcludingMain);
                cornerArray[2].setRandomicSymbol();
                break;

            default:
                 throw new IllegalStateException("Unexpected value: " + caseNumber); //parte del costrutto switch-case nel caso in cui venga generato random un n° != da 0-1-2
        }
    }


    // Metodo per ottenere i punti vittoria
    public int getVictoryPoints() {
        return VictoryPoints;
    }


}
