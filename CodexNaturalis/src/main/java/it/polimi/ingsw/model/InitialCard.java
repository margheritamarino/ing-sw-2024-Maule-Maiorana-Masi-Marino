package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Card;

import java.util.List;

public class InitialCard extends PlayableCard {
    List<ResourceType> centralResources;
    int numCentralResources;
    int numResources;
    List<ResourceType> resourceList;

    public List<ResourceType> getCentralResources() {
        return centralResources;
    }

    public int getNumCentralResources() {
        return numCentralResources;
    }

    public int getNumResources() {
        return numResources;
    }

    public List<ResourceType> getResourceList() {
        return resourceList;
    }
}
