package it.polimi.ingsw.model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.model.cards.ObjectiveCard;

import java.io.*;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * ObjectiveDeck class
 */
public class ObjectiveDeck implements Serializable {
    private int numCards;
    private final ArrayList<ObjectiveCard> frontCards;

    /**
     * Constructs an ObjectiveDeck initializing it with the default number of objective cards.
     * It also initializes the deck by reading JSON files containing card data.
     */
    public ObjectiveDeck()  {
        this.frontCards = new ArrayList<>();
        this.numCards = DefaultValue.NumOfObjectiveCards;
        initializeDeck();
    }

    /**
     * Retrieves the list of objective cards currently in the front of the deck.
     * @return ArrayList of ObjectiveCard objects representing the front cards.
     */
    public ArrayList<ObjectiveCard> getFrontCards() {
        return frontCards;
    }


    /**
     * Initializes the deck of objective cards by reading from JSON files.
     * Reads the front cards from a JSON file and populates the frontCards list.
     * Handles various exceptions that may occur during file reading or JSON parsing.
     */
    public void initializeDeck() {
        Reader frontReader = null;
        Gson gson = new Gson();

        try {
            ArrayList<ObjectiveCard> frontCardList = null;

                    frontReader = new InputStreamReader(Objects.requireNonNull(ObjectiveDeck.class.getResourceAsStream("/json/ObjectiveCardsFront.json")), StandardCharsets.UTF_8);
                    Type objectiveCardType = new TypeToken<ArrayList<ObjectiveCard>>() {
                    }.getType();
                    frontCardList = gson.fromJson(frontReader, objectiveCardType);
                    frontCards.addAll(frontCardList);
                    frontReader.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found exception");

        } catch (IOException e) {
            System.err.println("error during reading file");

        } catch (JsonSyntaxException | JsonIOException e) {
            System.err.println("JSON file parsing error");

        } catch (ClassCastException e) {
            System.err.println("CASTING error accessing JSON file data: ");

        } catch (NullPointerException e) {
            System.err.println("Error null values not handled correctly ");
        } catch (Exception e) {
            System.err.println("Generic error exception during JSON file reading ");
        }
    }

    /**
     * Checks if the deck of objective cards has ended.
     * @return {true} if the deck has ended (no more cards are available),
     * {false} otherwise.
     */
    public boolean checkEndDeck() {
        return numCards <= 0;
    }


    /**
     * Returns a random objective card from the deck.
     * The method selects a random card from the  deck, decreases the
     * number of cards in the deck, and removes the selected card from the deck.
     *
     * @return the randomly selected objective card.
     */
    public ObjectiveCard returnCard() throws DeckEmptyException {
        if (checkEndDeck()) {
            throw new DeckEmptyException("The deck is empty. No more cards to draw.");
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(frontCards.size());

        ObjectiveCard frontCard = frontCards.get(randomIndex);
        numCards--;

        frontCards.remove(randomIndex);
        return frontCard;
    }

    /**
     * Retrieves the number of objective cards remaining in the deck.
     * @return the number of cards remaining in the objective card deck.
     */
    public int getNumCards() {
        return numCards;
    }

}
