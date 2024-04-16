package it.polimi.ingsw.model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import it.polimi.ingsw.exceptions.FileCastException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.exceptions.JSONParsingException;
import it.polimi.ingsw.model.cards.ObjectiveCard;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

/**
 * ObjectiveDeck class
 */
public class ObjectiveDeck {
    private int numCards;
    private ArrayList<ObjectiveCard> frontCards;
    private ArrayList<ObjectiveCard> backCards;

    public ObjectiveDeck() throws FileReadException, FileNotFoundException {
        this.frontCards = new ArrayList<>();
        this.backCards = new ArrayList<>();
        this.numCards = 40;

        initializeDeck();
    }

    /**
    Initializes the deck of objectiveCard.
     * Reads the JSON files containing the front and back cards,
     * and populates the frontCards and backCards lists with the read cards.
     * @author Irene Pia Masi
     */
    public void initializeDeck() throws FileReadException, FileNotFoundException {
        String frontFileName = "ObjectiveCardsFront.json";
        String backFileName = "ObjectiveCardsBack.json";

        try {
            Gson gson = new Gson();
            Type objectivecardListType = new TypeToken<ArrayList<ObjectiveCard>>() {
            }.getType();

            FileReader frontReader = new FileReader(frontFileName);
            ArrayList<ObjectiveCard> frontCardList = gson.fromJson(frontReader, objectivecardListType);
            frontCards.addAll(frontCardList);
            frontReader.close();

            FileReader backReader = new FileReader(backFileName);
            ArrayList<ObjectiveCard> backCardList = gson.fromJson(backReader, objectivecardListType);
            backCards.addAll(backCardList);
            backReader.close();
        } catch (FileNotFoundException e) {
            // Eccezione lanciata se il file non viene trovato
            throw new FileNotFoundException("File not found: " + e.getMessage());

        } catch (IOException e) {
            // Eccezione lanciata in caso di problemi durante la lettura del file
            throw new FileReadException("Error reading file: " + e.getMessage());

        } catch (JsonSyntaxException | JsonIOException e) {
            // Eccezione lanciata se ci sono problemi di parsing JSON
            throw new JSONParsingException("JSON file parsing error: " + e.getMessage());

        } catch (ClassCastException e) {
            // Eccezione lanciata se ci sono problemi di casting durante l'accesso ai dati JSON
            throw new FileCastException("CASTING error accessing JSON file data: " + e.getMessage());

        } catch (NullPointerException e) {
            // Eccezione lanciata se ci sono valori nulli non gestiti correttamente
            throw new NullPointerException("Error null values not handled correctly: " + e.getMessage());

        } catch (Exception e) {
            // Eccezione generica per gestire altri tipi di eccezioni
            throw new FileReadException("Generic exception: " + e.getMessage());
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
     * @author Irene Pia Masi
     * @return an array containing two cards (one front and one back) drawn from the deck
     * Returns two cards from the deck at a random position (position = CardID).
     * Decreases the numCards attribute of the deck.
     * Removes the cards from the arrays
     */
    public ObjectiveCard[] returnCard() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(frontCards.size());

        ObjectiveCard frontCard = frontCards.get(randomIndex);
        ObjectiveCard backCard = backCards.get(randomIndex);
        // Decrease the numCards attribute of the deck
        numCards--;

        // Remove the retrieved cards from the deck
        frontCards.remove(randomIndex);
        backCards.remove(randomIndex);

        return new ObjectiveCard[]{frontCard, backCard};
    }


}
