package it.polimi.ingsw.model;

import java.util.Random;

public enum SymbolType {
    QUILL,
    INKWELL,
    MANUSCRIPT;

    /**
     * @author Martina Maiorana
     * This method returns a randomic value of SymbolType
     */
    public SymbolType getRandomSymbolType() {
        SymbolType[] symbolTypes = SymbolType.values();
        Random random = new Random();
        int randomIndex = random.nextInt(symbolTypes.length);
        return symbolTypes[randomIndex];
    }

    /**
     * @author Martina Maiorana
     * This method returns a randomic value of SymbolType excluding the value passed by parameter
     * @param excludedType SymbolType we want to exclude from the selection
     */
    public SymbolType getRandomSymbolTypeExcluding(SymbolType excludedType) {
        SymbolType[] types = values();
        SymbolType randomType;
        Random random = new Random();
        do { // Genera un numero casuale finché non si ottiene un tipo diverso da excludedType
            randomType = types[random.nextInt(types.length)];
        } while (randomType == excludedType);
        return randomType;

    }
}
