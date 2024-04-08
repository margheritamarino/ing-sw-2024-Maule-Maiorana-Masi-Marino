package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.ResourceBack;
import it.polimi.ingsw.model.ResourceFront;

public class ResourceCard extends PlayableCard { //NON è PIU ABSTRACT
   private ResourceType mainResource;
   private int victoryPoints;
   private int numResources;
   private List<resourceType> resourceList;
   private boolean hasSymbol;
   private SymbolType symbol;

}
