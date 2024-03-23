package it.polimi.ingsw;

public class ResourceFront extends ResourceCard {
    final int VictoryPoints;

    // Costruttore
    public ResourceFront(int victoryPoints) {
        this.VictoryPoints = victoryPoints;
    }

    // Metodo per ottenere i punti vittoria
    public int getVictoryPoints() {
        return VictoryPoints;
    }


}
