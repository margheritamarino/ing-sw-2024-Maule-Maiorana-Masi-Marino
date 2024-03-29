package it.polimi.ingsw.model;

import java.util.Random;

public class GoldBack  extends GoldCard {
    final ResourceType Resource;

    // Costruttore
    public GoldBack (int cardID) {
         { //Costruttore che assegna un valore RANDOMICO tra i 4 possibili all'attributo ResourceType
            super.setCardID(cardID); // Chiama il costruttore della classe genitore per inizializzare l'ID della carta

            // Ottieni un indice casuale per selezionare un valore casuale dall'enumerazione ResourceType
            Random random = new Random(); //crea un nuovo oggetto della classe Random
            int index = random.nextInt(ResourceType.values().length); // metodo nextInt(int n) della classe Random--> genera un numero intero casuale compreso tra 0 (incluso) e n (escluso)

            // Assegna il valore corrispondente all'indice generato casualmente
            this.Resource = ResourceType.values()[index];
        }
    }

    // Metodo per ottenere il tipo di risorsa presente sulla carta
    public ResourceType getResourceType() {
        return Resource;
    }
}
