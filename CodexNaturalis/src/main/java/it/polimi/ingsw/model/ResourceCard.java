package it.polimi.ingsw.model;
import java.util.*;

public class ResourceCard extends PlayableCard { //NON è PIU ABSTRACT
   private ResourceType mainResource;
   private int victoryPoints;
   private int numResources;
   private List<ResourceType> resourceList;
   private boolean hasSymbol;
   private SymbolType symbol;

}
