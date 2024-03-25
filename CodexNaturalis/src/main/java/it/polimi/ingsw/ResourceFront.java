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

        //Random random = new Random();
        //addCornerWithType(random, numCorners); // Chiamata alla funzione per aggiungere i CornerType

        Set<Corner> cornerSet = cornersMap.keySet(); // Ottieni l'insieme delle chiavi

        // Creazione di un array di Corner di dimensione 3
        Corner[] cornerArray = new Corner[3];

        // Ottenere un iteratore per l'insieme delle chiavi
        Iterator<Corner> iterator = cornerSet.iterator();

        // Inserisci i primi 3 valori delle chiavi nell'array di Corner
        for (int i = 0; i < 3 && iterator.hasNext(); i++) {
            cornerArray[i] = iterator.next();
        }

        // Ora cornerArray contiene i primi 3 valori delle chiavi della mappa cornersMap (i 3 Corner creati)

        int caseNumber = random.nextInt(3); // Genera un numero casuale tra 0 e 2

        switch (caseNumber) {

            case 0:
                VictoryPoints = 0;
                setNumResources(2);
                setNumSymbols(0);
                setResource(cornerArray[0], mainResource);
                setResource(cornerArray[1], mainResource);
                kingdom.add(cornerArray[0].resource));
                kingdom.add(cornerArray[1].resource));//aggiunge le risorse alla lista di resource di card (Kingdom)
                break;

            case 1:
                VictoryPoints = 1;
                setNumResources(1);
                setNumSymbols(0);
                setResource(cornerArray[0], mainResource);
                kingdom.add(cornerArray[0].resource));
                break;

            case 2:
                VictoryPoints = 0;
                setNumResources(2);
                setNumSymbols(1);
                setResource(cornerArray[0], mainResource);
                generateResource(cornerArray[1]);
                kingdom.add(cornerArray[1].resource));
                generateSymbol(cornerArray[2].getResourceType);
                symbols.add(cornerArray[2].getSymbolType);


                break;

            default:
                 throw new IllegalStateException("Unexpected value: " + caseNumber); //parte del costrutto switch-case nel caso in cui venga generato random un n° != da 0-1-2
            //
        }
    }

    private void addCornerWithType(int num) {
        List<CornerType> cornerTypes = new ArrayList<>(); // Crea lista vuota di CornerType
        Collections.addAll(cornerTypes, CornerType.values()); // Riempie la lista con tutti i possibili CornerType
        Collections.shuffle(cornerTypes); // Mescola i valori casualmente

        for (int i = 0; i < num; i++) {
            cornersMap.put(new Corner(cornerTypes.get(i)), this);
        }
    }




    /*// Metodo per aggiungere un Corner con tipo random //Metodi Privati perchè usati solo dalla classe stessa e non accessibili esternamente
    private void addCornerWithType(Random random) {
        List<CornerType> cornerTypes = new ArrayList<>(); //creo lista vuota di CronerType
        Collections.addAll(cornerTypes, CornerType.values()); //riempio la lista con tutti i possibili CornerType
        Collections.shuffle(cornerTypes); //mescolo i valori casualmente
        CornerType cornerType = cornerTypes.get(0); //prendo il primo

        kingdom.add(new Corner(cornerType));
    }

    // Metodo per aggiungere un Corner con ResourceType random
    private void addCornerWithResourceType(Random random) {
        List<ResourceType> resourceTypes = new ArrayList<>();
        Collections.addAll(resourceTypes, ResourceType.values());
        Collections.shuffle(resourceTypes);
        ResourceType resourceType = resourceTypes.get(0);

        kingdom.add(new Corner(resourceType));
    }

    // Metodo per aggiungere un Symbol random
    private void addSymbol(Random random) {
        List<SymbolType> symbolTypes = new ArrayList<>();
        Collections.addAll(symbolTypes, SymbolType.values());
        Collections.shuffle(symbolTypes);
        SymbolType symbolType = symbolTypes.get(0);

        symbols.add(new Symbol(symbolType));
    }*/

    // Metodo per ottenere i punti vittoria
    public int getVictoryPoints() {
        return VictoryPoints;
    }


}
