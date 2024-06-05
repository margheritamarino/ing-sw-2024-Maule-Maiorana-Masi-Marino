package it.polimi.ingsw.model.interfaces;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.PlayableCard;

import java.util.*;

//Capisci se serve aggiungere interfaccia CellIC per mandare istanze di Cell (non dovrebbe)
//nel caso modifica i metodi in modo che ritornino CellIC
//ALTRIMENTI non mandiamo istanze di Cell,in showAvailableCells(),  per intero ma troviamo un altro modo (es. inviare solo cordinate)

public interface BookIC {
    Cell getCellinMatrix(Cell cellToFind);

    boolean checkPlacementCondition(PlayableCard goldCard);

    int checkGoldPoints(PlayableCard goldCard, Cell cell);

    int checkGoldCornerCondition(Cell cell);

    int checkGoldSymbolCondition(PlayableCard goldCard);

    ArrayList<Cell> showAvailableCells();

    Map<ResourceType, Integer> getResourceMap();

    Map<SymbolType, Integer> getSymbolMap();

    int checkGoal(ObjectiveCard objectiveCard);

    int checkResourceCondition(ObjectiveCard objectiveCard);

    int checkSymbolCondition(ObjectiveCard objectiveCard);

    int checkDiagonalPlacement(ObjectiveCard objectiveCard);

    int checkLPlacement(ObjectiveCard objectiveCard);


}
