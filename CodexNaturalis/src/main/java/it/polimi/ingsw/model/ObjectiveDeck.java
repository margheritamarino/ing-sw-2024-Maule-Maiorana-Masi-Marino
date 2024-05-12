package it.polimi.ingsw.model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileCastException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.exceptions.JSONParsingException;
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
public class ObjectiveDeck {
    private int numCards;
    private final ArrayList<ObjectiveCard> frontCards;


    public ObjectiveDeck()  {
        this.frontCards = new ArrayList<>();
        this.numCards = DefaultValue.NumOfObjectiveCards;
        initializeDeck();
    }


    public ArrayList<ObjectiveCard> getFrontCards() {
        return frontCards;
    }


    /**
    Initializes the deck of objectiveCard.
     * Reads the JSON files containing the front and back cards,
     * and populates the frontCards and backCards lists with the read cards.
     * @author Irene Pia Masi
     */
    public void initializeDeck() {
        Reader frontReader = null;
        Gson gson = new Gson();

        try {
            // Leggi dal file JSON frontCards
            ArrayList<ObjectiveCard> frontCardList = null;

                    frontReader = new InputStreamReader(Objects.requireNonNull(ObjectiveDeck.class.getResourceAsStream("/json/ObjectiveCardsFront.json")), StandardCharsets.UTF_8);
                    Type objectiveCardType = new TypeToken<ArrayList<ObjectiveCard>>() {
                    }.getType();
                    frontCardList = gson.fromJson(frontReader, objectiveCardType);
                    frontCards.addAll(frontCardList);
                    frontReader.close();

        } catch (FileNotFoundException e) {
            // Eccezione lanciata se il file non viene trovato
            System.err.println("File not found exception");

        } catch (IOException e) {
            // Eccezione lanciata in caso di problemi durante la lettura del file
            System.err.println("error during reading file");

        } catch (JsonSyntaxException | JsonIOException e) {
            // Eccezione lanciata se ci sono problemi di parsing JSON
            System.err.println("JSON file parsing error");

        } catch (ClassCastException e) {
            // Eccezione lanciata se ci sono problemi di casting durante l'accesso ai dati JSON
            System.err.println("CASTING error accessing JSON file data: ");

        } catch (NullPointerException e) {
            // Eccezione lanciata se ci sono valori nulli non gestiti correttamente
            System.err.println("Error null values not handled correctly ");
        } catch (Exception e) {
            // Eccezione generica per gestire altri tipi di eccezioni
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
     * @return the randomly selected objective card.
     */
    public ObjectiveCard returnCard() throws DeckEmptyException {
        if (checkEndDeck()) {
            throw new DeckEmptyException("The deck is empty. No more cards to draw.");
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(frontCards.size());

        ObjectiveCard frontCard = frontCards.get(randomIndex);
        // Decrease the numCards attribute of the deck
        numCards--;

        // Remove the retrieved cards from the deck
        frontCards.remove(randomIndex);
        return frontCard;
    }

    public int getNumCards() {
        return numCards;
    }

}
