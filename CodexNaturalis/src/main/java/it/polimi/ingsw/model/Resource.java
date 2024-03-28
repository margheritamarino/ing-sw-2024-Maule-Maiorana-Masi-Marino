package it.polimi.ingsw.model;

import it.polimi.ingsw.model.ResourceType;

public class Resource {
    private ResourceType type;
    private int quantity;


    /**
     * @author Margherita Marino
     * constructor
     * @param type resource's Kingdom
     * @param quantity quantity of the resource
     * @throws IllegalArgumentException if type is not a valid ResourceType
     */
    public Resource(ResourceType type, int quantity) {
        if(type == null || !isValidResourceType(type)){
            throw new IllegalArgumentException("Invalid resource type.");
        }
        this.type = type;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ResourceType getType() {
        return type;
    }

    /**
     * @author Margherita Marino
     * setter
     * @param type resource's Kingdom
     * @throws IllegalArgumentException if type is not a valid ResourceType
     */
    public void setType(ResourceType type) {
        if (type == null || !isValidResourceType(type)) {
            throw new IllegalArgumentException("Invalid resource type.");
        }
        this.type = type;
    }

    /**
     * @author Margherita Marino
     * check if type is a valid ResourceType
     * @param type resource's Kingdom
     */
    public boolean isValidResourceType(ResourceType type){
        return type == ResourceType.ANIMALKINGDOM ||
                type == ResourceType.INSECTKINGDOM ||
                type == ResourceType.PLANTKINGDOM ||
                type == ResourceType.FUNGIKINGDOM;
    }

}
