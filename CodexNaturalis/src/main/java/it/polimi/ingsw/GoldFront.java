package it.polimi.ingsw;

import java.util.List;

public class GoldFront extends GoldCard{
    //Attributes
    final int VictoryPoints;
    final List<Resource> PlacementCondition; //lista di risorse che devono essere presenti nel Book del Player perchè possano essere piazzate
    final int numResources;
    final Symbol symbolType;
    final boolean cornerCondition;

    //Methods
    // Costruttore
    public GoldFront(int victoryPoints, List<Resource> placementCondition, int numResources, Symbol symbolType, boolean cornerCondition) {
        this.VictoryPoints = victoryPoints;
        this.PlacementCondition = placementCondition;
        this.numResources = numResources;
        this.symbolType = symbolType;
        this.cornerCondition = cornerCondition;
    }

    // Metodi getter
    public int getVictoryPoints() {
        return VictoryPoints;
    }

    public List<Resource> getPlacementCondition() {
        return PlacementCondition;
    }

    public int getNumResources() {
        return numResources;
    }

    public Symbol getSymbolType() {
        return symbolType;
    }

    public boolean isCornerCondition() {
        return cornerCondition;
    }

    // Equals e HashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MyClass myClass = (MyClass) obj;
        return VictoryPoints == myClass.VictoryPoints &&
                numResources == myClass.numResources &&
                cornerCondition == myClass.cornerCondition &&
                PlacementCondition.equals(myClass.PlacementCondition) &&
                symbolType.equals(myClass.symbolType);
    }

    @Override
    public int hashCode() {
        int result = VictoryPoints;
        result = 31 * result + PlacementCondition.hashCode();
        result = 31 * result + numResources;
        result = 31 * result + symbolType.hashCode();
        result = 31 * result + (cornerCondition ? 1 : 0);
        return result;
    }

    // ToString
    @Override
    public String toString() {
        return "MyClass{" +
                "VictoryPoints=" + VictoryPoints +
                ", PlacementCondition=" + PlacementCondition +
                ", numResources=" + numResources +
                ", symbolType=" + symbolType +
                ", cornerCondition=" + cornerCondition +
                '}';
    }

}
