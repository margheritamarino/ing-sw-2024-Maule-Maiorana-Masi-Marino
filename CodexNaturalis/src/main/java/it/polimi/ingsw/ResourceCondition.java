package it.polimi.ingsw;

import java.util.ArrayList;

public class ResourceCondition extends Goal{
    // Attributi
    ArrayList<Resource> resourceList; // Dimensione = 3
    ResourceType kingdom;

    // Metodi
    ArrayList<ResourceType> getObjective(ObjectiveCard objCard) {
        // Implementazione
    }
    ResourceType getResourceType(ObjectiveCard objCard) {
        // Implementazione
    }

    int checkObjective(Book book, Player player, ResourceType resourceType) {
        // Implementazione
    }
}
