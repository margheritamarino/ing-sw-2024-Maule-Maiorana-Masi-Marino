package it.polimi.ingsw;

public class GoldBack  extends GoldCard{
    private ResourceType Resource;

    // Costruttore
    public GoldBack (ResourceType Resource) {
        this.Resource = Resource;
    }

    // Metodo per ottenere il tipo di risorsa presente sulla carta
    public ResourceType getResourceType() {
        return Resource;
    }
}
