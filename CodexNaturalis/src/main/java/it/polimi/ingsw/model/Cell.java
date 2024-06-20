package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PlayableCard;

import java.io.Serializable;


/**
 * Represents cell in player's book for placing playable cards.
 * Each cell maintains information about its position, availability, wall status, and the card placed on it.
 */
public class Cell implements Serializable {
    private int row;
    private int column;
    private boolean wall;
    private boolean available;
    private int placementOrder;
    private PlayableCard cardPointer;


    /**
     * Constructs a Cell object with specified row and column indices.
     * Initially sets the cell as unavailable, without a wall, and without any card placed.
     *
     * @param row    The row index of the cell in the grid.
     * @param column The column index of the cell in the grid.
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.available = false;
        this.wall = false;
        this.cardPointer = null;
        this.placementOrder = -1;
    }


    /**
     * Updates the cell with a new playable card, marking it as unavailable for further placement.
     *
     * @param newCard The new playable card to be placed on the cell.
     */
    public void updateCell(PlayableCard newCard){
        this.available = false;
        this.cardPointer = newCard;
    }

    /**
     * Sets the order of placement for the card on the cell.
     *
     * @param placementOrder The order of placement to set.
     */
    public void setPlacementOrder(int placementOrder) {
        this.placementOrder = placementOrder;
    }

    /**
     * Retrieves the order of placement of the card on the cell.
     *
     * @return The order of placement of the card.
     */
    public int getPlacementOrder() {
        return placementOrder;
    }

    /**
     * Retrieves the row index of the cell.
     *
     * @return The row index of the cell.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row index of the cell.
     *
     * @param row The row index to set.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Retrieves the column index of the cell.
     *
     * @return The column index of the cell.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the column index of the cell.
     *
     * @param column The column index to set.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Checks if the cell is available for placing a card.
     *
     * @return true if the cell is available, false otherwise.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the availability status of the cell for placing a card.
     *
     * @param available The availability status to set.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Retrieves the playable card placed on the cell.
     *
     * @return The playable card placed on the cell.
     */
    public PlayableCard getCard() {
        return cardPointer;
    }

    /**
     * Sets the playable card placed on the cell.
     *
     * @param cardPointer The playable card to set on the cell.
     */
    public void setCardPointer(PlayableCard cardPointer) {
        this.cardPointer = cardPointer;
    }

    /**
     * Checks if the cell has a wall.
     *
     * @return true if the cell has a wall, false otherwise.
     */
    public boolean isWall() {
        return wall;
    }

    /**
     * Sets the wall status of the cell.
     *
     * @param wall The wall status to set.
     */
    public void setWall(boolean wall) {
        this.wall = wall;
    }

    /**
     * Calculates the total number of cells in a grid with the given number of rows and columns.
     *
     * @param numRows   The number of rows in the grid.
     * @param numColumns The number of columns in the grid.
     * @return The total number of cells in the grid.
     */
    public int size(int numRows, int numColumns) {
        return numRows*numColumns;
    }

    /**
     * Retrieves the playable card currently placed on this cell.
     *
     * @return The playable card placed on this cell, or {@code null} if no card is placed.
     */
    public PlayableCard getCardPointer() {
        return cardPointer;
    }
}
