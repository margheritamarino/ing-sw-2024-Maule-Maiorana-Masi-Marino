package it.polimi.ingsw.model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

    public ObjectiveDeck(){
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
    public void initializeDeck(){
        String frontFileName = "ObjectiveCardsFront.json";
        String backFileName = "ObjectiveCardsBack.json";

        try {
            Gson gson = new Gson();
            Type objectivecardListType = new TypeToken<ArrayList<ObjectiveCard>>() {}.getType();

            FileReader frontReader = new FileReader(frontFileName);
            ArrayList<ObjectiveCard> frontCardList = gson.fromJson(frontReader, objectivecardListType);
            frontCards.addAll(frontCardList);
            frontReader.close();

            FileReader backReader = new FileReader(backFileName);
            ArrayList<ObjectiveCard> backCardList = gson.fromJson(backReader, objectivecardListType);
            backCards.addAll(backCardList);
            backReader.close();
        } catch (IOException e) {
            e.printStackTrace();
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
     *
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
