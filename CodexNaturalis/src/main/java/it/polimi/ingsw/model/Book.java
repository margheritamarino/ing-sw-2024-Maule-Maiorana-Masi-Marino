package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.CellNotAvailableException;
import it.polimi.ingsw.model.cards.PlayableCard;
import java.util.HashMap;
import java.util.Map;


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
        this.resourceMap = new HashMap<>();
        this.symbolMap = new HashMap<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                assert false;
                bookMatrix[i][j] = new Cell(i, j); // Costruttore che inizializza righe e colonne e imposta isAvailable a false
            }
        }
    }

    public int addCard(PlayableCard card, Cell cell){ //metodo che piazza le carte nel gioco e restituisce i punti di quella carte (se non ha punti restituisce 0)
        int numPoints = 0;
        try {
            if(!cell.isAvailable()){
                throw new CellNotAvailableException("This Cell is not Available");
            }
            cell.setCardPointer(card); //setto il puntatore della cella alla carta che ho appena piazzato
            cell.setAvailable(false); //setto disponibilità cella a false
            updateMaps(card, cell); //aggiorna le mappe di simboli e risorse in base alle nuove risorse/simboli che si trovano sulla nuova carta appena piazzata e in base alle risorse/simboli che si trovano sugli angoli che vengono coperti dalla carta appena piazzata
            updateBook(card, cell);//aggiorna il book, ovvero aggiorna la disponibilità delle celle attorno alla cella della carta appena piazzata
            numPoints = card.getCardPoints();
        }catch (CellNotAvailableException e){
            System.err.println("Unable to place the card: " + e.getMessage());
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

           if(bookMatrix[i-1][j-1].getCard() != null && !(bookMatrix[i-1][j-1].getCard().getCornerContent(CornerType.BRCorner).equals("Empty"))){
                coverCorner(bookMatrix[i-1][j-1].getCard(), CornerType.BRCorner);
           }
           if(bookMatrix[i-1][j+1].getCard() != null && !(bookMatrix[i-1][j+1].getCard().getCornerContent(CornerType.BLCorner).equals("Empty"))){
               coverCorner(bookMatrix[i-1][j+1].getCard(), CornerType.BLCorner);
           }
           if(bookMatrix[i+1][j+1].getCard() != null && !(bookMatrix[i+1][j+1].getCard().getCornerContent(CornerType.TLCorner).equals("Empty"))){
               coverCorner(bookMatrix[i+1][j+1].getCard(), CornerType.TLCorner);
           }
           if(bookMatrix[i+1][j-1].getCard() != null && !(bookMatrix[i+1][j-1].getCard().getCornerContent(CornerType.TRCorner).equals("Empty"))){
               coverCorner(bookMatrix[i+1][j-1].getCard(), CornerType.TRCorner);
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
     * @param corner  The type of corner to cover.
     */
    public void coverCorner(PlayableCard card, CornerType corner){ //funzione che "copre" la risorsa o il simbolo di una carta passata la carta e l'angolo coperto. decrementa il valore della risorsa/simbolo nella mappa dei simboli risorse del book
        if(card.getCornerContent(corner).equals("Fungi")){
            decreaseResource(ResourceType.FUNGI);
        }
        else if(card.getCornerContent(corner).equals("Animal")){
            decreaseResource(ResourceType.ANIMAL);
        }else if(card.getCornerContent(corner).equals("Insect")){
            decreaseResource(ResourceType.INSECT);
        }else if(card.getCornerContent(corner).equals("Insect")){
            decreaseResource(ResourceType.PLANT);
        }else if(card.getCornerContent(corner).equals("Ink")){
            decreaseSymbol(SymbolType.INK);
        }else if(card.getCornerContent(corner).equals("Quill")){
            decreaseSymbol(SymbolType.QUILL);
        }else decreaseSymbol(SymbolType.MANUSCRIPT);
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
        for (CornerType corner : CornerType.values()) {
            String content = card.getCornerContent(corner);
            switch (content) {
                case "Fungi":
                    increaseResource(ResourceType.FUNGI);
                    break;
                case "Animal":
                    increaseResource(ResourceType.ANIMAL);
                    break;
                case "Insect":
                    increaseResource(ResourceType.INSECT);
                    break;
                case "Plant":
                    increaseResource(ResourceType.PLANT);
                    break;
                case "Ink":
                    increaseSymbol(SymbolType.INK);
                    break;
                case "Quill":
                    increaseSymbol(SymbolType.QUILL);
                    break;
                case "Manuscript":
                    increaseSymbol(SymbolType.MANUSCRIPT);
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
    public void clear(){ //elimina tutte le carte dal book
        // Itera attraverso tutte le celle del book
        for (int i = 0; i < bookMatrix.length; i++) {
            for (int j = 0; j < bookMatrix[i].length; j++) {
                // Imposta il puntatore della carta della cella a null
                bookMatrix[i][j].setCardPointer(null);
                // Imposta la disponibilità della cella a true
                bookMatrix[i][j].setAvailable(true);
            }
        }
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
            cell.setWall(true);
        }else if(!(newCard.getTLCorner() == CornerLabel.NoCorner) && !(bookMatrix[i-1][j-1].isWall())){
            bookMatrix[i-1][j-1].setAvailable(true);
        }

        if(newCard.getTLCorner() == CornerLabel.NoCorner){
            cell.setWall(true);
        }else if(!(newCard.getTLCorner() == CornerLabel.NoCorner) && !(bookMatrix[i-1][j+1].isWall())){
            bookMatrix[i-1][j+1].setAvailable(true);
        }

        if(newCard.getBRCorner() == CornerLabel.NoCorner){
            cell.setWall(true);
        }else if(!(newCard.getTLCorner() == CornerLabel.NoCorner) && !(bookMatrix[i+1][j+1].isWall())){
            bookMatrix[i+1][j+1].setAvailable(true);
        }

        if(newCard.getBLCorner() == CornerLabel.NoCorner){
            cell.setWall(true);
        }else if(!(newCard.getTLCorner() == CornerLabel.NoCorner) && !(bookMatrix[i+1][j-1]).isWall()){
            bookMatrix[i+1][j-1].setAvailable(true);
        }
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
     * It also calculates calculates how many points the player obtained achieving his Goal.
     *
     * @param objectiveCard Is the player's own ObjectiveCard.
     * @throws IllegalArgumentException If an invalid GoalType label is set on the objectiveCard attribute.
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

    private int checkResourceCondition(ObjectiveCard objectiveCard) {
        // Implementa la logica per controllare se è verificata la condizione di risorsa
        // ritorna la somma di punti ottenuti dal giocatore
    }

    private int checkSymbolCondition(ObjectiveCard objectiveCard) {
        // Implementa la logica per controllare se è verificata la condizione di simbolo
        // ritorna la somma di punti ottenuti dal giocatore
    }

    private int checkDiagonalPlacement(ObjectiveCard objectiveCard) {
        // Implementa la logica per controllare se è verificata la disposizione diagonale
        // ritorna la somma di punti ottenuti dal giocatore
    }

    private int checkLPlacement(ObjectiveCard objectiveCard) {
        // Implementa la logica per controllare se è verificata la disposizione a L
        // ritorna la somma di punti ottenuti dal giocatore
    }
}
