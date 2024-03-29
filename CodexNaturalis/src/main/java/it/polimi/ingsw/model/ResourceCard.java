package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.ResourceBack;
import it.polimi.ingsw.model.ResourceFront;

public class ResourceCard extends Card { //NON è PIU ABSTRACT
    private ResourceBack Back; // riferimento al Back
    private ResourceFront Front; //riferimento al Front
    public ResourceCard(int cardId) {
        super(cardId);
        this.Front =new ResourceFront(cardId);
        this.Back = new ResourceBack((cardId);

    }
    public ResourceFront getFront(){
        return this.Front;
    }
    public ResourceBack getBack(){
        return Back;
    }

}
