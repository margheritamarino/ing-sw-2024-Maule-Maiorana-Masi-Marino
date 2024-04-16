package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
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


public class ObjectiveDeck {
    private int numCards;
    private final ArrayList<ObjectiveCard> frontCards;
    private final ArrayList<ObjectiveCard> backCards;

    public ObjectiveDeck() throws FileNotFoundException, FileReadException {
        this.numCards= 16;
        this.backCards= new ArrayList<ObjectiveCard>();
        this.frontCards= new ArrayList<ObjectiveCard>();
        initializeDeck();
    }

    /**
     * @author Sofia Maule
     * Initializes the deck reading Objective Cards from JSON files containing
     * the front and the back of the cards,
     * and populates the frontCards and backCards lists with the read cards.
     *
     *  * @throws FileNotFoundException if any of the JSON files are not found.
     *  * @throws FileReadException if there is an error while reading the files.
     *  */


    private void initializeDeck() throws FileNotFoundException, FileReadException {
        // Leggi le ObjectiveCards dal file JSON ObjectiveCardsBack
        try (FileReader backReader = new FileReader("ObjectiveCardsBack.json")) {
            Gson gson = new Gson();
            Type objectiveCardListType = new TypeToken<ArrayList<ObjectiveCard>>() {}.getType();
            ArrayList<ObjectiveCard> backCardList = gson.fromJson(backReader, objectiveCardListType);
            backCards.addAll(backCardList);
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

        // Leggi le ObjectiveCards dal file JSON ObjectiveCardsFront
        try (FileReader frontReader = new FileReader("ObjectiveCardsFront.json")) {
            Gson gson = new Gson();
            Type objectiveCardListType = new TypeToken<ArrayList<ObjectiveCard>>() {}.getType();
            ArrayList<ObjectiveCard> frontCardList = gson.fromJson(frontReader, objectiveCardListType);
            frontCards.addAll(frontCardList);
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

    /** @author Sofia Maule
     * Checks the deck's numCards to see if the deck has ended
     * @return { true} if the deck has ended (no more cards are available),
     *         {@code false} otherwise.
     */
    public boolean checkEndDeck() {
        return numCards > 0 ? false : true;
    }


    /**
     * @author Sofia Maule
     * @return an array containing two cards (one front and one back) drawn from the deck
     *
     * Returns two cards from the deck at a random position (position = CardID).
     * Decreases the numCards attribute of the deck.
     * Removes the cards from the arrays
     *
     */
    public ObjectiveCard[] returnCard() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(frontCards.size()); // Generates a random index within the range of the deck size
        // randomIndex = randomPosition = randomCardID -> same ID for front and back
        // Retrieve the cards at the random index from both frontCards and backCards arrays
        ObjectiveCard frontCard = frontCards.get(randomIndex);
        ObjectiveCard backCard = backCards.get(randomIndex);

        // Decrease the numCards attribute of the deck
        numCards--;

        // Remove the retrieved cards from the deck
        frontCards.remove(randomIndex);
        backCards.remove(randomIndex);

        // Return the retrieved cards as an array
        return new ObjectiveCard[]{frontCard, backCard};
    }

}
