package it.polimi.ingsw;
import java.util.*;

public enum ResourceType {
    PLANTKINGDOM,
    ANIMALKINGDOM,
    FUNGIKINGDOM,
    INSECTKINGDOM;

    /**
     * @author Martina Maiorana
     * This method returns a randomic value of ResourceType
     */
    public ResourceType getRandomResourceType() {
        ResourceType[] resourceTypes = ResourceType.values();
        Random random = new Random();
        int randomIndex = random.nextInt(resourceTypes.length);
        return resourceTypes[randomIndex];
    }

    /**
     * @author Martina Maiorana
     * This method returns a randomic value of ResourceType excluding the value passed by parameter
     * @param excludedType ResourceType we want to exclude from the selection
     */
    public ResourceType getRandomResourceTypeExcluding(ResourceType excludedType) {
        ResourceType[] types = values();
        ResourceType randomType;
        Random random = new Random();
        do { // Genera un numero casuale finché non si ottiene un tipo diverso da excludedType
            randomType = types[random.nextInt(types.length)];
        } while (randomType == excludedType);
        return randomType;

    }


}
