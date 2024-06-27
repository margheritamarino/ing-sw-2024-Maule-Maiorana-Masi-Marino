
package it.polimi.ingsw.model;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FileCastException;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.exceptions.JSONParsingException;
import it.polimi.ingsw.model.cards.*;


import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.Objects;
import java.util.Random;


/**
 * The `Deck` class represents a deck of playable cards. It initializes and manages
 * cards based on different types (GoldCard, ResourceCard, InitialCard) read from
 * JSON files.
 */
public class Deck implements Serializable {
    private int numCards;
    private final CardType cardType;
    private final ArrayList<PlayableCard> frontCards;
    private final ArrayList<PlayableCard> backCards;
    private int randomIndex;

    /**
     * Constructs a `Deck` object of a specified card type.
     *
     * @param cardType The type of cards in the deck (GoldCard, ResourceCard, InitialCard).
     */
    public Deck(CardType cardType) {
        this.cardType = cardType;
        this.frontCards = new ArrayList<>();
        this.backCards = new ArrayList<>();

        switch (cardType) {
                case GoldCard, ResourceCard -> this.numCards = DefaultValue.NumOfGoldCards;
                case InitialCard -> this.numCards = DefaultValue.NumOfInitialCards;
        }
        try {
            initializeDeck(cardType);
        } catch (FileNotFoundException e) {
            System.err.println("Error JSON file not found - " + e.getMessage());
        } catch (FileReadException e) {
            System.err.println("Error during JSON file reading - " + e.getMessage());
        }
        generateRandomIndex();

    }

    /**
     * Retrieves the current random index used for GUI purposes.
     *
     * @return The current random index.
     */
    public int getRandomIndex(){
        return this.randomIndex;
    }

    /**
     * Retrieves the number of cards remaining in the deck.
     *
     * @return The number of cards remaining.
     */
    public int getNumCards() {
        return numCards;
    }

    /**
     * Retrieves the list of front cards in the deck.
     *
     * @return The list of front cards.
     */
    public ArrayList<PlayableCard> getFrontCards() {
        return frontCards;
    }

    /**
     * Retrieves the list of back cards in the deck.
     *
     * @return The list of back cards.
     */
    public ArrayList<PlayableCard> getBackCards() {
        return backCards;
    }

    /**
     * Retrieves the type of cards in the deck.
     *
     * @return The type of cards (GoldCard, ResourceCard, InitialCard).
     */
    public CardType getCardType(){
        return cardType;
    }

    /**
     * Initializes the deck of cards (arrayList of PlayableCards) of the specified type.
     * Reads the JSON files of the specified Card Type containing the front and back cards,
     * and populates the frontCards and backCards lists with the read cards.
     *
     *
     * @param cardType the type of cards to initialize the deck
     * @throws FileNotFoundException if any of the JSON files are not found.
     * @throws FileReadException if there is an error while reading the files.
     */
    public void initializeDeck(CardType cardType) throws FileReadException, FileNotFoundException {
        Reader frontReader ;
        Reader backReader ;

        Gson gson = new Gson();

        try {
            ArrayList<InitialCard> frontCardList;
            ArrayList<InitialCard> backCardList ;
            switch (cardType) {
                case InitialCard:
                    frontReader = new InputStreamReader(Objects.requireNonNull(Deck.class.getResourceAsStream("/json/InitialCardsFront.json")), StandardCharsets.UTF_8);
                    backReader = new InputStreamReader(Objects.requireNonNull(Deck.class.getResourceAsStream("/json/InitialCardsBack.json")), StandardCharsets.UTF_8);
                    Type initialCardType = new TypeToken<ArrayList<InitialCard>>() {
                    }.getType();
                    frontCardList = gson.fromJson(frontReader, initialCardType);
                    frontCards.addAll(frontCardList);
                    frontReader.close();

                    backCardList = gson.fromJson(backReader, initialCardType);
                    backCards.addAll(backCardList);
                    backReader.close();
                    break;

                case ResourceCard:
                    frontReader = new InputStreamReader(Objects.requireNonNull(Deck.class.getResourceAsStream("/json/ResourceCardsFront.json")), StandardCharsets.UTF_8);
                    backReader = new InputStreamReader(Objects.requireNonNull(Deck.class.getResourceAsStream("/json/ResourceCardsBack.json")), StandardCharsets.UTF_8);
                    Type resourceCardType = new TypeToken<ArrayList<ResourceCard>>() {
                    }.getType();
                    frontCardList = gson.fromJson(frontReader, resourceCardType);
                    frontCards.addAll(frontCardList);
                    frontReader.close();

                    backCardList = gson.fromJson(backReader, resourceCardType);
                    backCards.addAll(backCardList);
                    backReader.close();
                    break;

                case GoldCard:
                    frontReader = new InputStreamReader(Objects.requireNonNull(Deck.class.getResourceAsStream("/json/GoldCardsFront.json")), StandardCharsets.UTF_8);
                    backReader = new InputStreamReader(Objects.requireNonNull(Deck.class.getResourceAsStream("/json/GoldCardsBack.json")), StandardCharsets.UTF_8);
                    Type goldCardType = new TypeToken<ArrayList<GoldCard>>() {
                    }.getType();
                    frontCardList = gson.fromJson(frontReader, goldCardType);
                    frontCards.addAll(frontCardList);
                    frontReader.close();

                    backCardList = gson.fromJson(backReader, goldCardType);
                    backCards.addAll(backCardList);
                    backReader.close();
                    break;
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + e.getMessage());

        } catch (IOException e) {
            throw new FileReadException("Error reading file: " + e.getMessage());

        } catch (JsonSyntaxException | JsonIOException e) {
            throw new JSONParsingException("JSON file parsing error: " + e.getMessage());

        } catch (ClassCastException e) {
            throw new FileCastException("CASTING error accessing JSON file data: " + e.getMessage());

        } catch (NullPointerException e) {
            throw new NullPointerException("Error null values not handled correctly: " + e.getMessage());

        } catch (Exception e) {
            throw new FileReadException("Generic exception: " + e.getMessage());
        }
    }

    /**
     * Checks if the deck has ended (no more cards available).
     *
     * @return `true` if the deck has ended, `false` otherwise.
     */
    public boolean checkEndDeck() {
        return numCards <= 0;
    }

    /**
     * Generates a random index within the range of available cards in the deck.
     * This index is used for GUI purposes to determine which card's back to display next.
     */
    private void generateRandomIndex() {
        if (numCards > 0) {
            randomIndex = new Random().nextInt(numCards);
        } else {
            randomIndex = -1;
        }
    }

    /**
     * Returns two cards from the deck at a random position (position = CardID).
     * Decreases the numCards attribute of the deck.
     * Removes the cards from the arrays
     *
     * @return an array containing two cards (one front and one back) drawn from the deck
     */
    public PlayableCard[] returnCard() throws DeckEmptyException {
        if (numCards == 0) {
            throw new DeckEmptyException("The deck is empty. No more cards to draw.");
        }
        PlayableCard frontCard = frontCards.get(randomIndex);
        PlayableCard backCard = backCards.get(randomIndex);

        numCards--;
        frontCards.remove(randomIndex);
        backCards.remove(randomIndex);
        if (numCards > 0) {
            generateRandomIndex();
        } else {
            randomIndex = -1;
        }
        return new PlayableCard[]{frontCard, backCard};
    }

}




