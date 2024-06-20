package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.model.cards.*;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.network.PrintAsync.printAsync;

/**
 * Board model class
 * This class represents the game board that contains decks of cards and cards placed on the board.
 */
public class Board implements Serializable {
    private final ArrayList<PlayableCard[]> goldCards;
    private final ArrayList<PlayableCard[]> resourceCards;
    private final ObjectiveCard[] objectiveCards;
    private final Deck goldCardsDeck;
    private final Deck resourcesCardsDeck;
    private final ObjectiveDeck objectiveCardsDeck;


    /**
     * Constructor for the Board class.
     * Initializes the board with empty lists for gold cards, resource cards, and objective cards.
     * Also initializes the respective decks for gold cards, resource cards, and objective cards.
     */
    public Board()  {
        this.goldCards = new ArrayList<>();
        this.resourceCards = new ArrayList<>();
        this.objectiveCards = new ObjectiveCard[2];
        this.goldCardsDeck = new Deck(CardType.GoldCard);
        this.resourcesCardsDeck = new Deck(CardType.ResourceCard);
        this.objectiveCardsDeck = new ObjectiveDeck();

        initializeBoard();
    }

    /**
     * Initializes the board by placing cards on it.
     * Sets the common Goals
     */
    public void initializeBoard()  {
        try {

            int MAX_SIZE = 2;
            for (int i = 0; i < MAX_SIZE; i++) {
                PlayableCard[] goldCards = goldCardsDeck.returnCard();
                PlayableCard[] resourceCards = resourcesCardsDeck.returnCard();
                this.goldCards.add(goldCards);
                this.resourceCards.add(resourceCards);
            }
            for (int i = 0; i < MAX_SIZE; i++) {
                ObjectiveCard objectiveCard = objectiveCardsDeck.returnCard();
                this.objectiveCards[i] = objectiveCard;
            }
        }catch (DeckEmptyException e){
            System.err.println("Error during board initialization");
        }
    }
    /**
     * Retrieves the array of objective cards currently placed on the board.
     * @return An array of ObjectiveCard objects representing the objective cards on the board.
     */
    public ObjectiveCard[] getObjectiveCards() {
        return this.objectiveCards;
    }

    /**
     * Retrieves the list of arrays of gold cards currently placed on the board.
     * Each array contains front and back cards of gold type.
     * @return An ArrayList of PlayableCard arrays representing the gold cards on the board.
     */
    public ArrayList<PlayableCard[]> getGoldCards() {
        return goldCards;
    }

    /**
     * Retrieves the list of arrays of resource cards currently placed on the board.
     * Each array contains front and back cards of resource type.
     * @return An ArrayList of PlayableCard arrays representing the resource cards on the board.
     */
    public ArrayList<PlayableCard[]> getResourceCards() {
        return resourceCards;
    }

    /**
     * Retrieves the deck of gold cards used on the board.
     * @return The Deck object representing the deck of gold cards.
     */
    public Deck getGoldCardsDeck() {
        return goldCardsDeck;
    }

    /**
     * Retrieves the deck of resource cards used on the board.
     * @return The Deck object representing the deck of resource cards.
     */
    public Deck getResourcesCardsDeck() {
        return resourcesCardsDeck;
    }

    /**
     * Retrieves an objective card from the objective cards deck.
     * @return An ObjectiveCard object picked from the objective cards deck.
     * @throws DeckEmptyException If the objective cards deck is empty when trying to draw a card.
     */
    public ObjectiveCard takeObjectiveCard() throws DeckEmptyException {
        return objectiveCardsDeck.returnCard();
    }

    /**
     * Takes a card from the board.
     * Depending on parameters, either draws a card from the respective deck or takes a card from the board.
     * @param cardType The type of card to take (GoldCard or ResourceCard).
     * @param drawFromDeck True if the card should be drawn from the deck, false if taken from the board.
     * @param pos The position of the card to take if drawing from the board.
     * @return The PlayableCard array representing the front and back cards taken from the board or deck.
     * @throws IllegalArgumentException If trying to draw an InitialCard, which is not allowed.
     * @throws DeckEmptyException If the respective deck is empty when trying to draw a card.
     * @throws IndexOutOfBoundsException If the position on the board is not valid.
     */
    public PlayableCard[] takeCardfromBoard(CardType cardType, boolean drawFromDeck, int pos) throws IllegalArgumentException, DeckEmptyException, IndexOutOfBoundsException  {
        if (drawFromDeck) {
            if(cardType== CardType.InitialCard){
                throw new IllegalArgumentException();
            }
            if (cardType == CardType.GoldCard && goldCardsDeck.checkEndDeck()) {
                throw new DeckEmptyException("Gold cards' deck is empty");
            } else if (cardType == CardType.ResourceCard && resourcesCardsDeck.checkEndDeck()) {
                throw new DeckEmptyException("Resource cards' deck is empty");
            }
            switch (cardType) {
                case GoldCard:
                    return goldCardsDeck.returnCard();
                case ResourceCard:
                    return resourcesCardsDeck.returnCard();
                default:
                    return null;
            }
        } else {
            ArrayList<PlayableCard[]> cardsOnBoard =null;
            PlayableCard[] selectedCards = null;
            switch (cardType) {
                case GoldCard:
                    cardsOnBoard = goldCards;
                    break;
                case ResourceCard:
                    cardsOnBoard = resourceCards;
                    break;
            }
            if (pos < 0 || pos >= cardsOnBoard.size()) {
                throw new IndexOutOfBoundsException("Position not valid on the board");
            }
            switch (pos){
                case 0-> selectedCards= cardsOnBoard.get(0);
                case 1-> selectedCards= cardsOnBoard.get(1);
            }
            cardsOnBoard.remove(pos);
            updateArray(cardsOnBoard, cardType);

            return selectedCards;
        }
    }


    /**
     * Updates the array of cards on the board by drawing a new card from the respective deck.
     * @param cards The ArrayList of PlayableCard arrays representing the cards on the board to update.
     * @param cardType The type of card (GoldCard or ResourceCard) for which to update the array.
     * @throws DeckEmptyException If the respective deck is empty when trying to draw a card.
     */
    public void updateArray(ArrayList<PlayableCard[]> cards, CardType cardType) throws DeckEmptyException {
        Deck deck ;
        switch (cardType) {
            case GoldCard:
                deck = goldCardsDeck;
                break;
            case ResourceCard:
                deck = resourcesCardsDeck;
                break;
            default:
                return;
        }
        PlayableCard[] newCard = deck.returnCard();
        cards.add(newCard);
    }


    /**
     * Verify the number of Gold cards on the Board
     * @return TRUE if there's a correct number of Gold Cards on the Board
     */
    public boolean verifyGoldCardsNumber() {
        return goldCards.size() == 2;
    }

    /**
     * Verify the number of Resource cards on the Board
     * @return TRUE if there's a correct number of Resource Cards on the Board
     */
    public boolean verifyResourceCardsNumber() {
        return resourceCards.size() == 2;
    }

    /**
     * Verify the number of Objective cards on the Board
     * @return TRUE if there's a correct number of Objective Cards on the Board
     */
    public boolean verifyObjectiveCardsNumber() {
        return objectiveCards.length == 2;
    }

    /**
     * Verify the number of Gold cards on the GoldDeck
     * @return TRUE if there's a correct number of Gold Cards on the Board
     * @param playersNumber to calculate the right number of Cards
     */
    public boolean verifyGoldDeckSize(int playersNumber) {
        return (goldCardsDeck.getNumCards())  == DefaultValue.NumOfGoldCards- 2 - playersNumber; //su Deck numCards va da 0 a 40
    }

    /**
     * Verify the number of Resource cards in the relative Deck
     * @return TRUE if there's a correct number of Resource Cards in the Deck on the Board
     * @param playersNumber to calculate the right number of Cards
     */
    public boolean verifyResourceDeckSize(int playersNumber) {
        return (resourcesCardsDeck.getNumCards())  == DefaultValue.NumOfResourceCards- 2 - (2* playersNumber); //su Deck numCards va da 0 a 40

    }

    /**
     * Verify the number of Objective cards in the relative Deck
     * @return TRUE if there's a correct number of Objective Cards in the Deck on the Board
     * @param playersNumber to calculate the right number of Cards
     */
    public boolean verifyObjectiveDeckSize(int playersNumber) {
        return objectiveCardsDeck.getNumCards() == DefaultValue.NumOfObjectiveCards - 2 - playersNumber;
    }

    /**
     * Generates a string representation of the board, including the current state of gold cards, resource cards,
     * and objective cards on the board.
     * @return A string representation of the board.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("***********BOARD***********\n");
        result.append("\n");

        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();
        for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
            rowBuilders.add(new StringBuilder());
        }

        System.out.print(ColorConsole.YELLOW_BACKGROUND.getCode());
        System.out.print(ColorConsole.BLACK.getCode());
        result.append("***GOLDCARDS***: \n");
        result.append("\n");
        result.append(cardsGoldToString());
        result.append("\n");
        System.out.print(ColorConsole.RESET.getCode());

        result.append("***RESOURCECARDS***: \n");
        result.append("\n");
        result.append(cardsResourceToString());
        result.append("\n");

        result.append("*******COMMON GOALS*******: \n");
        result.append("\n");
        result.append(cardsObjectiveToString());
        result.append("\n");
        return result.toString();
    }

    /**
     * Generates a string representation of the objective cards currently on the board.
     * @return A string representation of the objective cards on the board.
     */
    public String cardsObjectiveToString() {
        ObjectiveCard[] objectiveCards = getObjectiveCards();
        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();

        for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
            rowBuilders.add(new StringBuilder());
        }

        for (int i = 0; i < objectiveCards.length; i++) {
            ObjectiveCard card = objectiveCards[i];
            String[] lines = card.toString().split("\n");

            for (int k = 0; k < lines.length; k++) {
                rowBuilders.get(k).append(lines[k]).append(" ");
            }
        }
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowBuilders) {
            result.append(sb.toString().stripTrailing()).append("\n");
        }
        return result.toString();
    }

    /**
     * Generates a string representation of the gold cards currently on the board.
     * @return A string representation of the gold cards on the board.
     */
    public String cardsGoldToString() {
        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();
        for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
            rowBuilders.add(new StringBuilder());
        }

        PlayableCard card = goldCardsDeck.getBackCards().get(0);
        String[] lines = card.toString().split("\n");

        for (int k = 0; k < lines.length; k++) {
            rowBuilders.get(k).append(lines[k]).append(" ");
        }

        for (int i = 0; i < 2; i++) {
            card = goldCards.get(i)[0];
            lines = card.toString().split("\n");

            for (int k = 0; k < lines.length; k++) {
                rowBuilders.get(k).append(lines[k]).append(" ");
            }
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowBuilders) {
            result.append(sb.toString().stripTrailing()).append("\n");
        }

        return result.toString();
    }

    /**
     * Generates a string representation of the resource cards currently on the board.
     * @return A string representation of the resource cards on the board.
     */
    public String cardsResourceToString() {
        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();

        for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
            rowBuilders.add(new StringBuilder());
        }

        PlayableCard card = resourcesCardsDeck.getBackCards().get(0);
        String[] lines = card.toString().split("\n");

        for (int k = 0; k < lines.length; k++) {
            rowBuilders.get(k).append(lines[k]).append(" ");
        }

        for (int i = 0; i < 2; i++) {
            card = resourceCards.get(i)[0];
            lines = card.toString().split("\n");

            for (int k = 0; k < lines.length; k++) {
                rowBuilders.get(k).append(lines[k]).append(" ");
            }
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowBuilders) {
            result.append(sb.toString().stripTrailing()).append("\n");
        }

        return result.toString();
    }

    /**
     * Generates a formatted string representation of the visible Gold cards on the board.
     *
     * @return String representation of the visible Gold cards.
     */
    public String cardsVisibleGoldToString() {
        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();

        for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
            rowBuilders.add(new StringBuilder());
        }

        for (int i = 0; i < 2; i++) {
            PlayableCard card = goldCards.get(i)[0]; // Assumiamo che goldCards contenga le carte da visualizzare
            String[] lines = card.toString().split("\n"); // Otteniamo le linee della rappresentazione della carta

            int maxHeight = Math.min(lines.length, DefaultValue.printHeight + 2);

            for (int k = 0; k < maxHeight; k++) {
                if (k == 0) {
                    rowBuilders.get(k).append(" ").append(i + 1).append(" ").append(lines[k]).append(" ");
                } else {
                    rowBuilders.get(k).append("    ").append(lines[k]).append(" ");
                }
            }

            for (int k = maxHeight; k < DefaultValue.printHeight + 2; k++) {
                rowBuilders.get(k).append(" ".repeat(lines[0].length() + 4)); // +4 per lo spazio di separazione
            }
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowBuilders) {
            result.append(sb.toString().stripTrailing()).append("\n");
        }

        return result.toString();
    }


    /**
     * Generates a formatted string representation of the visible Resource cards on the board.
     *
     * @return String representation of the visible Resource cards.
     */
    public String cardsVisibleResourceToString() {
        ArrayList<StringBuilder> rowBuilders = new ArrayList<>();

        for (int k = 0; k < DefaultValue.printHeight + 2; k++) {
            rowBuilders.add(new StringBuilder());
        }

       for (int i = 0; i < 2; i++) {
            PlayableCard card = resourceCards.get(i)[0]; // Assumiamo che resourceCards contenga le carte da visualizzare
            String[] lines = card.toString().split("\n"); // Otteniamo le linee della rappresentazione della carta

            int maxHeight = Math.min(lines.length, DefaultValue.printHeight + 2);

            for (int k = 0; k < maxHeight; k++) {
                if (k == 0) {
                    rowBuilders.get(k).append(" ").append(i + 1).append(" ").append(lines[k]).append(" ");
                } else {
                    rowBuilders.get(k).append("    ").append(lines[k]).append(" ");
                }
            }

            for (int k = maxHeight; k < DefaultValue.printHeight + 2; k++) {
                rowBuilders.get(k).append(" ".repeat(lines[0].length() + 4)); // +4 per lo spazio di separazione
            }
        }
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rowBuilders) {
            result.append(sb.toString().stripTrailing()).append("\n");
        }

        return result.toString();
    }

}

