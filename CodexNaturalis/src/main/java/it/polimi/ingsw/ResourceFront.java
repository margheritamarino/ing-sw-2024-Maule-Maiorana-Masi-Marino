package it.polimi.ingsw;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class ResourceFront extends ResourceCard {
    final int VictoryPoints;

    // Costruttore
    public ResourceFront(int cardID) {

        super(cardID); // Chiama il costruttore della classe genitore per inizializzare l'ID della carta
        numCorners=3; //tutti i sottotipi di ResourceFront hanno 3 angoli

        Random random = new Random();
        int caseNumber = random.nextInt(3); // Genera un numero casuale tra 0 e 2 per i tre casi possibili

        switch (caseNumber) {
            case 0:
                // Caso 1: Victory Points = 0, 3 Corner con 2 Resource uguali, 0 Symbols
                VictoryPoints = 0;

                numResources = 2;
                numSymbols = 0;
                kingdom = new ArrayList<>();
                addCornerWithType(random);
                addCornerWithType(random);
                addCornerWithType(random);
                break;

            case 1:
                // Caso 2: Victory Points = 1, 3 Corner con 1 uguale, 0 Symbols
                VictoryPoints = 1;
                numResources = 1;
                numSymbols = 0;
                kingdom = new ArrayList<>();
                addCornerWithType(random);
                addCornerWithType(random);
                addCornerWithType(random);
                break;
            case 2:

                // Caso 3: Victory Points = 0, 2 Corner con risorse, 1 Symbol
                VictoryPoints = 0;
                numResources = 2;
                numSymbols = 1;
                kingdom = new ArrayList<>();
                symbols = new ArrayList<>();
                addCornerWithResourceType(random);
                addCornerWithResourceType(random);
                addSymbol(random);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + caseNumber); //parte del costrutto switch-case nel caso in cui venga generato random un n° != da 0-1-2
        }
    }

    // Metodo per aggiungere un Corner con tipo random //Metodi Privati perchè usati solo dalla classe stessa e non accessibili esternamente
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
    }

    // Metodo per ottenere i punti vittoria
    public int getVictoryPoints() {
        return VictoryPoints;
    }


}
