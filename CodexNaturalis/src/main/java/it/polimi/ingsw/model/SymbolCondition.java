package it.polimi.ingsw.model;

import java.util.ArrayList;
public class SymbolCondition extends Goal {
    // Attributi
    list <SymbolType> symbolsList;
    int numSymbols;

    // Metodi
    ArrayList<SymbolType> getObjective(ObjectiveCard objCard) {
        // Implementazione
    }

    int getNumSymbol() {
        // Implementazione
    }

    int checkFirstObjective(Book book, Player player, SymbolType symbolType) {
        // Implementazione
    }

    int checkSecondObjective(Book book, Player player) {
        // Implementazione
    }
}
