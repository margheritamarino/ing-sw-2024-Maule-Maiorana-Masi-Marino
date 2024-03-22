package it.polimi.ingsw;

public class Resource {
    private ResourceType Type;
    private int Quantity;

    /**
     * @author Margherita Marino
     * constructor
     * @param type resource's Kingdom
     * @param quantity quantity of the resource
     */
    public Resource(ResourceType type, int quantity) {
        Type = type;
        Quantity = quantity;
    }

    public ResourceType getType() {
        return Type;
    }

    public int getQuantity() {
        return Quantity;

        // da modificare
    }
}
