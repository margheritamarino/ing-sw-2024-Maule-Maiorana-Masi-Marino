package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CellNotAvailableException;
import it.polimi.ingsw.model.cards.*;
import java.util.*;



// BOOK: disposizione delle carte di ogni player
public class Book {
    private Cell[][] bookMatrix; //matrice di celle
    private Map<ResourceType, Integer> resourceMap; //mappa di numero di risorse per tipo
    private Map<SymbolType, Integer> symbolMap; //n° di simboli per tipo


    /**
     * Constructs a Book object with the specified number of rows, columns, and a PlayableCard.
     * Initializes the resource and symbol maps and sets up the book matrix with Cell objects.
     * Each Cell object is initialized with its row and column indices and is marked as unavailable.
     *
     * @author Margherita Marino
     * @param rows    The number of rows in the book.
     * @param columns The number of columns in the book.
     */
    public Book(int rows, int columns){
        // Inizializza le mappe di risorse e simboli
        initializeMaps();

        this.bookMatrix = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                bookMatrix[i][j] = new Cell(i, j); // Costruttore che inizializza righe e colonne e imposta isAvailable a false
            }
        }
    }
    public void initializeMaps(){
        this.resourceMap = new HashMap<>();
        resourceMap.put(ResourceType.Animal,0);
        resourceMap.put(ResourceType.Fungi,0);
        resourceMap.put(ResourceType.Insect,0);
        resourceMap.put(ResourceType.Plant,0);

        this.symbolMap = new HashMap<>();
        symbolMap.put(SymbolType.Ink,0);
        symbolMap.put(SymbolType.Quill,0);
        symbolMap.put(SymbolType.Manuscript,0);
    }
    public Cell getCellinMatrix(Cell findCell) {
        for (int row = 0; row < bookMatrix.length; row++) {
            for (int col = 0; col < bookMatrix[row].length; col++) {
                Cell currentCell = bookMatrix[row][col];
                // Verifica se la cella corrente è uguale a findCell
                if (currentCell.equals(findCell)) {
                    return currentCell; // Restituisci la cella trovata
                }
            }
        }
        // Cella non trovata, restituisci null
        return null;
    }

    /**
     * Adds the initial card to the center of the player's book matrix.
     * This method adds the initial card to the center of the book matrix, which represents the player's book.
     * The book matrix has dimensions of 70x70 cells. The initial card is placed in the center cell (at index [35][35]).
     * After placing the card, the cell's availability is set to false to indicate it's occupied.
     * Then, the resource map is updated based on the resources present on the initial card, both on the corners
     * and in the central part, and the surrounding cells' availability is updated accordingly.
     *
     * @author Margherita Marino
     * @param initialCard The initial card to add to the center of the book matrix.
     */
    public void addInitial(PlayableCard initialCard){ //cella 35x35
        Cell initialCell = bookMatrix[35][35];
        initialCell.setAvailable(true);
        initialCell.setCardPointer(initialCard);
        initialCell.setAvailable(false); //setto disponibilità cella a false
        UpdateMapInitial(initialCard); //aggiorno la mappa delle risorse presenti sul book in base alle risorse presenti sugli angoli e o nel centro della initialCard
        updateBook(initialCard, initialCell);//aggiorna il book, ovvero aggiorna la disponibilità delle celle attorno alla cella della carta appena piazzata in base alla presenza o meno degli angoli
    }

    /**
     * Updates the resource map based on the provided initial card.
     *
     * This method evaluates the presence of resources in the initial card, which can have resources
     * both on the corners of the card and in the central part.
     *
     * @author Margherita Marino
     * @param initialCard The initial card to update the resource map with.
     */
    public void UpdateMapInitial(PlayableCard initialCard){
        if(initialCard.getNumCentralResources() != 0) {
            if (!initialCard.getCentralResources().isEmpty()) {
                for (ResourceType resource : initialCard.getCentralResources()) {
                    increaseResource(resource);
                }
            }
        }
        if(initialCard.getNumResources() != 0){
            updateNewCardCorners(initialCard);
        }
    }


    /**
     * Places a resource card onto a cell in the game and returns the points earned by that card.
     *
     * @author Margherita Marino
     * @param resourceCard The resource card to be placed.
     * @param cell         The cell onto which the card will be placed.
     * @return The points earned by placing the card on the cell. Returns 0 if the card has no points.
     */
    public int addResourceCard(PlayableCard resourceCard, Cell cell){ //metodo che piazza le carte nel gioco e restituisce i punti di quella carte (se non ha punti restituisce 0)
        int numPoints = 0;
        try {
            if(!cell.isAvailable()){
                throw new CellNotAvailableException("This Cell is not Available");
            }
            cell.setCardPointer(resourceCard); //setto il puntatore della cella alla carta che ho appena piazzato
            cell.setAvailable(false);
            updateMaps(resourceCard, cell); //aggiorna le mappe di simboli e risorse in base alle nuove risorse/simboli che si trovano sulla nuova carta appena piazzata e in base alle risorse/simboli che si trovano sugli angoli che vengono coperti dalla carta appena piazzata
            updateBook(resourceCard, cell);//aggiorna il book, ovvero aggiorna la disponibilità delle celle attorno alla cella della carta appena piazzata
            numPoints = resourceCard.getVictoryPoints();
        }catch (CellNotAvailableException e){
            System.err.println("Unable to place the card: " + e.getMessage());
        }
        return numPoints;
    }

    public int addGoldCard(PlayableCard goldCard, Cell cell){
        int numPoints = 0;
        if(checkPlacementCondition(goldCard)){
            try {
                if(!cell.isAvailable()){
                    throw new CellNotAvailableException("This Cell is not Available");
                }
                cell.setCardPointer(goldCard); //setto il puntatore della cella alla carta che ho appena piazzato
                cell.setAvailable(false); //setto disponibilità cella a false
                updateMaps(goldCard, cell); //aggiorna le mappe di simboli e risorse in base alle nuove risorse/simboli che si trovano sulla nuova carta appena piazzata e in base alle risorse/simboli che si trovano sugli angoli che vengono coperti dalla carta appena piazzata
                updateBook(goldCard, cell);//aggiorna il book, ovvero aggiorna la disponibilità delle celle attorno alla cella della carta appena piazzata
                if(!goldCard.isPointsCondition()){
                    numPoints = goldCard.getVictoryPoints();
                }else{
                    numPoints = checkGoldPoints(goldCard, cell);
                }
            }catch (CellNotAvailableException e){
                System.err.println("Unable to place the card: " + e.getMessage());
            }
        }
        return numPoints;
    }

    /**
     * Checks if the placement condition of a given gold card is satisfied based on the available resources.
     *
     * @author Margherita Marino
     * @param goldCard The gold card whose placement condition needs to be checked.
     * @return True if the placement condition is satisfied, false otherwise.
     */
    public boolean checkPlacementCondition(PlayableCard goldCard){
        Map<ResourceType, Integer> conditionsMap = new HashMap<>();
        //inizializzo la mappa conditionsMap
        conditionsMap.put(ResourceType.Animal,0);
        conditionsMap.put(ResourceType.Fungi,0);
        conditionsMap.put(ResourceType.Insect,0);
        conditionsMap.put(ResourceType.Plant,0);
        boolean check = true;
        //realizzo una mappa che contiene la condizione di piazzamenoto di ogni risorsa recuperando la condizione dall'array di ResourceType
        for (ResourceType resource : goldCard.getPlacementCondition()) {
            conditionsMap.put(resource, conditionsMap.get(resource) + 1);
        }
        for (ResourceType resourceType : conditionsMap.keySet()) {
            int conditionValue = conditionsMap.get(resourceType);
            int resourceValue = resourceMap.getOrDefault(resourceType, 0);
            if (conditionValue > resourceValue) {
                check = false;
            }
        }
        return check;
    }

    /**
     * Checks the points earned by a gold card based on its conditions.
     *
     * @author Margherita Marino
     * @param goldCard The gold card to be checked.
     * @param cell     The cell associated with the gold card.
     * @return The number of points earned by the gold card.
     * @see #checkCornerCondition(PlayableCard, Cell)
     * @see #checkGoldSymbolCondition(PlayableCard)
     */
    public int checkGoldPoints(PlayableCard goldCard, Cell cell){
        int numPoints = 0;
        if(goldCard.isCornerCondition()){
            numPoints = checkCornerCondition(cell);
        }else{
            numPoints = checkGoldSymbolCondition(goldCard);
        }
        return numPoints;
    }

    public int checkCornerCondition(Cell cell){
        int cornerCovered = 0;
        int i = cell.getRow();
        int j = cell.getColumn();

        if(bookMatrix[i-1][j-1].getCard() != null){
            cornerCovered++;
        }
        if(bookMatrix[i-1][j+1].getCard() != null ){
            cornerCovered++;
        }
        if(bookMatrix[i+1][j+1].getCard() != null){
            cornerCovered++;
        }
        if(bookMatrix[i+1][j-1].getCard() != null){
            cornerCovered++;
        }
        return cornerCovered;
    }

    /**
     * Checks the gold symbol condition of a playable gold card.
     *
     * @author Margherita Marino
     * @param goldCard The gold card to be checked.
     * @return The number of occurrences of the gold card's symbol condition in a symbol map.
     */
    public int checkGoldSymbolCondition(PlayableCard goldCard){
        return symbolMap.getOrDefault(goldCard.getSymbolCondition(), 0);
    }

    /**
     * Adds a playable card to a cell and returns the number of points earned.
     *
     * @author Margherita Marino
     * @param card The playable card to be added.
     * @param cell The cell to which the card will be added.
     * @return The number of points earned by adding the card to the cell.
     */
    public int addCard(PlayableCard card, Cell cell){
        int numPoints = 0;
        switch (card.getCardType()){
            case GoldCard -> numPoints = addGoldCard(card, cell);
            case ResourceCard -> numPoints = addResourceCard(card, cell);
        }
        return numPoints;
    }


    /**
     * Updates the covered corners of the neighboring cards of the specified cell.
     * If a neighboring cell contains a card and its corner is not empty,
     * the corresponding resource or symbol in the book's resource/symbol map is decremented.
     *
     * @author Margherita Marino
     * @param cell The cell for which to update the covered corners of neighboring cards.
     * @return True if any corner was covered, false otherwise.
     */
    public boolean updateCoveredCorners(Cell cell){
        boolean cornerCovered = false;
        if(cell.getCard()!=null){
           int i = cell.getRow();
           int j = cell.getColumn();

           if(bookMatrix[i-1][j-1].getCard() != null && !(bookMatrix[i-1][j-1].getCard().getCornerContent(2).equals("Empty"))){
                coverCorner(bookMatrix[i-1][j-1].getCard(),2);
           }
           if(bookMatrix[i-1][j+1].getCard() != null && !(bookMatrix[i-1][j+1].getCard().getCornerContent(3).equals("Empty"))){
               coverCorner(bookMatrix[i-1][j+1].getCard(), 3);
           }
           if(bookMatrix[i+1][j+1].getCard() != null && !(bookMatrix[i+1][j+1].getCard().getCornerContent(0).equals("Empty"))){
               coverCorner(bookMatrix[i+1][j+1].getCard(), 0);
           }
           if(bookMatrix[i+1][j-1].getCard() != null && !(bookMatrix[i+1][j-1].getCard().getCornerContent(1).equals("Empty"))){
               coverCorner(bookMatrix[i+1][j-1].getCard(), 1);
           }
       }
        return cornerCovered;
    }

    /**
     * Covers the resource or symbol of a specified corner on a PlayableCard.
     * Decreases the quantity of the corresponding resource or symbol in the book's resource/symbol map.
     *
     * @author Margherita Marino
     * @param card    The PlayableCard for which the corner is to be covered.
     * @param corner  The type of corner to cover (0: TLCorner, 1: TRCorner, 2: BRCorner, 3: BLCorner).
     */
    public void coverCorner(PlayableCard card, int corner){ //funzione che "copre" la risorsa o il simbolo di una carta passata la carta e l'angolo coperto. decrementa il valore della risorsa/simbolo nella mappa dei simboli risorse del book
        if((card.getCornerContent(corner).equals("Fungi")) || (card.getCornerContent(corner).equals("Animal")) || (card.getCornerContent(corner).equals("Insect")) || (card.getCornerContent(corner).equals("Plant"))){
            String content = card.getCornerContent(corner);
            switch (content){
                case("Fungi"): {
                    decreaseResource(ResourceType.Fungi);
                    break;
                }
                case("Animal"):{
                    decreaseResource(ResourceType.Animal);
                    break;
                }
                case("Insect"):{
                    decreaseResource(ResourceType.Insect);
                    break;
                }
                case("Plant"):{
                    decreaseResource(ResourceType.Plant);
                    break;
                }
                default: break;
            }
        }else if((card.getCornerContent(corner).equals("Ink")) || (card.getCornerContent(corner).equals("Quill") || (card.getCornerContent(corner).equals("Manuscript")))){
            String content = card.getCornerContent(corner);
            switch (content) {
                case ("Ink"): {
                    decreaseSymbol(SymbolType.Ink);
                    break;
                }
                case ("Quill"): {
                    decreaseSymbol(SymbolType.Quill);
                    break;
                }
                case ("Manuscript"): {
                    decreaseSymbol(SymbolType.Manuscript);
                    break;
                }
                default: break;
            }
        }
    }

    /**
     * Decreases the quantity of the specified type of resource by one.
     *
     * @author Margherita Marino
     * @param resourceType The type of resource to decrease.
     */
    public void decreaseResource(ResourceType resourceType){ //funzione che decrementa la risorsa passata per parametro
        int numResources = resourceMap.get(resourceType);
        numResources = (numResources == 0) ? 0 : numResources - 1;
        resourceMap.put(resourceType, numResources);
    }

    /**
     * Decreases the quantity of the specified type of symbol by one.
     *
     * @author Margherita Marino
     * @param symbolType The type of symbol to decrease.
     */
    public void decreaseSymbol(SymbolType symbolType){ //funzione che decrementa la risorsa passata per parametro
        int numSymbols = symbolMap.get(symbolType);
        numSymbols = (numSymbols == 0) ? 0 : numSymbols - 1;
        symbolMap.put(symbolType, numSymbols);
    }

    /**
     * Increases the quantity of the specified type of resource by one.
     *
     * @author Margherita Marino
     * @param resourceType The type of resource to increase.
     */
    public void increaseResource(ResourceType resourceType){ //funzione che incrementa la risorsa passata per parametro
        int numResources = resourceMap.get(resourceType);
        numResources++;
        resourceMap.put(resourceType, numResources);
    }

    /**
     * Increases the quantity of the specified type of symbol by one.
     *
     * @author Margherita Marino
     * @param symbolType The type of symbol to increase.
     */
    public void increaseSymbol(SymbolType symbolType){ //funzione che incrementa il simbolo passato per parametro
        int numSymbols = symbolMap.get(symbolType);
        numSymbols++;
        symbolMap.put(symbolType, numSymbols);
    }

    /**
     * Updates the symbol map and the resource map when adding a new card to the book.
     * This method updates the symbol map and the resource map by calling the
     * {@link #updateNewCardCorners(PlayableCard)} method to incorporate symbols and/or resources
     * from the newly added card, and the {@link #updateCoveredCorners(Cell)} method to adjust
     * the resource and symbol counts based on the neighboring cards of the specified cell.
     *
     * @author Margherita Marino
     * @param card The PlayableCard to add to the book.
     * @param cell The cell where the card is placed.
     */
    public void updateMaps(PlayableCard card, Cell cell){ //metodo per aggiornare la mappa dei simboli e la mappa delle risorse quando aggiungo una nuova carta
        updateNewCardCorners(card); //Aggiunge Simboli e Risorse della Carta appena piazzata nel Book
        updateCoveredCorners(cell); //Decrementa simboli e risorse che sono stati coperti dalla nuova carta piazzata
    }

    /**
     * Updates the resource and symbol maps with the symbols and/or resources present on a card
     * when it is placed in the book.
     *
     * @author Margherita Marino
     * @param card The PlayableCard to update the resource and symbol maps with.
     */
    public void updateNewCardCorners(PlayableCard card) {
        for (int i = 0; i < 4; i++) {
            String content = card.getCornerContent(i);
            switch (content) {
                case "Fungi":
                    increaseResource(ResourceType.Fungi);
                    break;
                case "Animal":
                    increaseResource(ResourceType.Animal);
                    break;
                case "Insect":
                    increaseResource(ResourceType.Insect);
                    break;
                case "Plant":
                    increaseResource(ResourceType.Plant);
                    break;
                case "Ink":
                    increaseSymbol(SymbolType.Ink);
                    break;
                case "Quill":
                    increaseSymbol(SymbolType.Quill);
                    break;
                case "Manuscript":
                    increaseSymbol(SymbolType.Manuscript);
                    break;
                default:
                    // Handle the case when the content is not recognized or empty
                    break;
            }
        }
    }


    /**
     * Removes all cards from the book.
     * This method clears the book of all cards, leaving it empty.
     * @author Margherita Marino
     */
    public void clear(){ //elimina tutte le carte dal book e setta le celle a
        // Itera attraverso tutte le celle del book
        for (int i = 0; i < bookMatrix.length; i++) {
            for (int j = 0; j < bookMatrix[i].length; j++) {
                // Imposta il puntatore della carta della cella a null
                bookMatrix[i][j].setCardPointer(null);
                // Imposta la disponibilità della cella a true
                bookMatrix[i][j].setAvailable(false);
                // Imposta l'attributo wall delle celle a false
                bookMatrix[i][j].setWall(false);
            }
        }
        initializeMaps();
    }


    /**
     * Updates the availability of cells surrounding the newly placed card.
     * This method adjusts the availability of cells surrounding the newly placed card
     * based on the presence of corners on the card. If a corner exists on the card and
     * the adjacent diagonal cell does not have its wall attribute set to true,
     * it sets the adjacent diagonal cell to available. If the corner does not exist (NoCorner),
     * the availability of the cell remains false, and the cell's wall attribute is set to true,
     * indicating that no further cards can be placed in that cell in the future.
     *
     * @param newCard The PlayableCard that has been placed.
     * @param cell The Cell where the card is placed.
     */
    public void updateBook(PlayableCard newCard, Cell cell){ //metodo che imposta la disponibilità delle celle attorno alla carta appena piazzata,passata per parametro, mettendole a false se non è presente un angolo
        int i = cell.getRow();
        int j = cell.getColumn();

        if(newCard.getTLCorner() == CornerLabel.NoCorner){
            bookMatrix[i-1][j-1].setWall(true);
        }else if(!(newCard.getTLCorner() == CornerLabel.NoCorner) && !(bookMatrix[i-1][j-1].isWall())){
            bookMatrix[i-1][j-1].setAvailable(true);
        }

        if(newCard.getTLCorner() == CornerLabel.NoCorner){
            bookMatrix[i-1][j+1].setWall(true);
        }else if(!(newCard.getTLCorner() == CornerLabel.NoCorner) && !(bookMatrix[i-1][j+1].isWall())){
            bookMatrix[i-1][j+1].setAvailable(true);
        }

        if(newCard.getBRCorner() == CornerLabel.NoCorner){
            bookMatrix[i+1][j+1].setWall(true);
        }else if(!(newCard.getTLCorner() == CornerLabel.NoCorner) && !(bookMatrix[i+1][j+1].isWall())){
            bookMatrix[i+1][j+1].setAvailable(true);
        }

        if(newCard.getBLCorner() == CornerLabel.NoCorner){
            bookMatrix[i+1][j-1].setWall(true);
        }else if(!(newCard.getTLCorner() == CornerLabel.NoCorner) && !(bookMatrix[i+1][j-1]).isWall()){
            bookMatrix[i+1][j-1].setAvailable(true);
        }
    }

    /**
     * Returns an array of available cells in the book matrix.
     * A cell is considered available if its 'isAvailable' attribute is set to true.
     *
     * @author Margherita Marino
     * @return An array of Cell objects representing the available cells in the book matrix.
     */
    public ArrayList<Cell> showAvailableCells() {
        ArrayList<Cell> availableCellsList = new ArrayList<>();

        // Scorre la matrice e aggiunge le celle disponibili alla lista
        for (int i = 0; i < bookMatrix.length; i++) {
            for (int j = 0; j < bookMatrix.length; j++) {
                if (bookMatrix[i][j].isAvailable()){
                    availableCellsList.add(bookMatrix[i][j]);
                }
            }
        }
        return availableCellsList;
    }

    public Map<ResourceType, Integer> getResourceMap() {
        return resourceMap;
    }

    public void setResourceMap(Map<ResourceType, Integer> resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Map<SymbolType, Integer> getSymbolMap() {
        return symbolMap;
    }

    public void setSymbolMap(Map<SymbolType, Integer> symbolMap) {
        this.symbolMap = symbolMap;
    }

    /**
     * This method checks on the player's Book how many times he achieved his own Goal.
     * It also calculates how many points the player obtained achieving his Goal.
     *
     * @param objectiveCard Is the player's own ObjectiveCard.
     * @throws IllegalArgumentException If an invalid GoalType label is set on the objectiveCard attribute.
     * @return Victory Points obtained by the player reaching the goal required by his Objective card.
     * @author Martina Maiorana
     */
    public int checkGoal(ObjectiveCard objectiveCard) {
        switch (objectiveCard.getGoalType()) {
            case ResourceCondition:
                return checkResourceCondition(objectiveCard);
            case SymbolCondition:
                return checkSymbolCondition(objectiveCard);
            case DiagonalPlacement:
                return checkDiagonalPlacement(objectiveCard);
            case LPlacement:
                return checkLPlacement(objectiveCard);
            default:
                throw new IllegalArgumentException("Invalid GoalType");
        }
    }


    /**
     * @return Victory Points obtained by the player reaching the Resource condition required by his Objective card.
     * @param objectiveCard The player's own ObjectiveCard.
     * @author Martina Maiorana
     */
    public int checkResourceCondition(ObjectiveCard objectiveCard) {

        ResourceType mainResourceType = objectiveCard.getMainResource(); //ResourceType required by the card
        int numMainResources = resourceMap.getOrDefault(mainResourceType, 0);
        int numTriplets = numMainResources / 3; //Groups of 3 required Resource on the player's Book
        return numTriplets * objectiveCard.getVictoryPoints();

    }

    /**
     * @return Victory Points obtained by the player reaching the Symbol condition required by his Objective card.
     * @param objectiveCard The player's own ObjectiveCard.
     * @author Martina Maiorana
     */
    public int checkSymbolCondition(ObjectiveCard objectiveCard) {

        switch (objectiveCard.getVictoryPoints()) {
            case 2:
                SymbolType symbolToCheck = objectiveCard.getSymbols().get(0);
                int numSymbol = symbolMap.getOrDefault(symbolToCheck, 0);
                int numPairs = numSymbol / 2;
                return numPairs * 2;
            case 3:
                int numQuill = symbolMap.getOrDefault(SymbolType.Quill, 0);
                int numInk = symbolMap.getOrDefault(SymbolType.Ink, 0);
                int numManuscript = symbolMap.getOrDefault(SymbolType.Manuscript, 0);
                int minSymbolCount = Math.min(numQuill, Math.min(numInk, numManuscript)); //gets the MINIMUM of the 3 symbols quantities
                int numTriplets = minSymbolCount / 3;
                return numTriplets * 3;

        }
        throw new IllegalArgumentException("Invalid victoryPoints");
    }

        /**
         * @return Victory Points obtained by the player reaching the diagonalPlacement condition required by his Objective card.
         * @param objectiveCard The player's own ObjectiveCard.
         * @author Martina Maiorana
         */
        public int checkDiagonalPlacement(ObjectiveCard objectiveCard) {
            int count = 0;
            ResourceType mainResource = objectiveCard.getMainResource();
            CornerType direction = objectiveCard.getDirection();

            int rows = bookMatrix.length;
            int columns = bookMatrix[0].length;

            if (direction == CornerType.BRCorner) {
                // Scansione dalla riga 0 alla penultima e dalla colonna 0 alla penultima
                for (int i = 0; i < rows - 2; i++) {
                    for (int j = 0; j < columns - 2; j++) {
                        // Controllo se le tre celle consecutive sono diagonalmente disposte
                        if (bookMatrix[i][j].getCard() != null &&
                                bookMatrix[i + 1][j + 1].getCard() != null &&
                                bookMatrix[i + 2][j + 2].getCard() != null &&
                                bookMatrix[i][j].getCard().getMainResource() == mainResource &&
                                bookMatrix[i + 1][j + 1].getCard().getMainResource() == mainResource &&
                                bookMatrix[i + 2][j + 2].getCard().getMainResource() == mainResource) {
                            count++;
                        }
                    }
                }
            } else if (direction == CornerType.BLCorner) {
                // Scansione dalla riga 0 alla penultima e dalla colonna 2 alla ultima
                for (int i = 0; i < rows - 2; i++) {
                    for (int j = 2; j < columns; j++) {
                        // Controllo se le tre celle consecutive sono diagonalmente disposte
                        if (bookMatrix[i][j].getCard() != null &&
                                bookMatrix[i + 1][j - 1].getCard() != null &&
                                bookMatrix[i + 2][j - 2].getCard() != null &&
                                bookMatrix[i][j].getCard().getMainResource() == mainResource &&
                                bookMatrix[i + 1][j - 1].getCard().getMainResource() == mainResource &&
                                bookMatrix[i + 2][j - 2].getCard().getMainResource() == mainResource) {
                            count++;
                        }
                    }
                }
            }

            // Ritorna il numero di gruppi trovati moltiplicato per 2 (punti vittoria guadagnati per ogni tripletta in diagonale che doddisfa i requisiti
            return count * 2;
        }
        /*ALTRO METODO con cui potresti implementare checkDiagonalPlacement è scansionare bookMatrix per righe e
         fermarti su ogni cella che contiene una PlayableCard che abbia la mainResource uguale a quella della ObjectiveCard,
         se è così allora controllo le due celle consecutive in diagonale: se contengono carte del regno richiesto, incremento il count*/



        /**
         * @return Victory Points obtained by the player reaching the LPlacement condition required by his Objective card.
         * @param objectiveCard The player's own ObjectiveCard.
         * @author Martina Maiorana
         */
        public int checkLPlacement(ObjectiveCard objectiveCard) {
                int count = 0;
                ResourceType mainResource = objectiveCard.getMainResource();
                ResourceType secondResource = objectiveCard.getSecondResource();

                switch (objectiveCard.getDirection()) { //i 4 possibili valori che può assumere 'direction' danno luogo alle 4 casistiche:
                    case TLCorner:
                        for (int i = 2; i < bookMatrix.length; i++) {
                            for (int j = 1; j < bookMatrix[i].length - 1; j++) {
                                if (bookMatrix[i][j].getCard() != null && bookMatrix[i][j].getCard().getMainResource() == mainResource) {
                                    if (bookMatrix[i - 1][j - 1].getCard() != null && bookMatrix[i - 2][j - 1].getCard() != null &&
                                            bookMatrix[i - 1][j - 1].getCard().getMainResource() == secondResource &&
                                            bookMatrix[i - 2][j - 1].getCard().getMainResource() == secondResource) {
                                        count++;
                                    }
                                }
                            }
                        }
                        break; //capisci se togliere il break
                    case TRCorner:
                        for (int i = 2; i < bookMatrix.length; i++) {
                            for (int j = 0; j < bookMatrix[i].length - 2; j++) {
                                if (bookMatrix[i][j].getCard() != null && bookMatrix[i][j].getCard().getMainResource() == mainResource) {
                                    if (bookMatrix[i - 1][j + 1].getCard() != null && bookMatrix[i - 2][j + 1].getCard() != null &&
                                            bookMatrix[i - 1][j + 1].getCard().getMainResource() == secondResource &&
                                            bookMatrix[i - 2][j + 1].getCard().getMainResource() == secondResource) {
                                        count++;
                                    }
                                }
                            }
                        }
                        break;
                    case BLCorner:
                        for (int i = 0; i < bookMatrix.length - 2; i++) {
                            for (int j = 1; j < bookMatrix[i].length - 1; j++) {
                                if (bookMatrix[i][j].getCard() != null && bookMatrix[i][j].getCard().getMainResource() == mainResource) {
                                    if (bookMatrix[i + 1][j - 1].getCard() != null && bookMatrix[i + 2][j - 1].getCard() != null &&
                                            bookMatrix[i + 1][j - 1].getCard().getMainResource() == secondResource &&
                                            bookMatrix[i + 2][j - 1].getCard().getMainResource() == secondResource) {
                                        count++;
                                    }
                                }
                            }
                        }
                        break;
                    case BRCorner:
                        for (int i = 0; i < bookMatrix.length - 2; i++) {
                            for (int j = 0; j < bookMatrix[i].length - 2; j++) {
                                if (bookMatrix[i][j].getCard() != null && bookMatrix[i][j].getCard().getMainResource() == mainResource) {
                                    if (bookMatrix[i + 1][j + 1].getCard() != null && bookMatrix[i + 2][j + 1].getCard() != null &&
                                            bookMatrix[i + 1][j + 1].getCard().getMainResource() == secondResource &&
                                            bookMatrix[i + 2][j + 1].getCard().getMainResource() == secondResource) {
                                        count++;
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid direction");
                }

                return count*3;
            }


    public Cell[][] getBookMatrix() {
        return bookMatrix;
    }
}
