
package it.polimi.ingsw.model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// RICORDA - DA FARE
// GESTIRE ECCEZIONI QUANDO SI LEGGE DAI FILE JSON

public class Deck {
    private int numCards;

    private final CardType cardType;

    private ArrayList<PlayableCard> frontCards;
    private ArrayList<PlayableCard> backCards;

    public Deck(CardType cardType) {
        this.cardType = cardType;
        this.frontCards = new ArrayList<PlayableCard>();
        this.backCards = new ArrayList<PlayableCard>();

        // numero di carte varia in base al tipo di carta
        switch (cardType) {
            case GoldCard, ResourceCard -> {
                this.numCards = 40;
            }
            case InitialCard -> {
                this.numCards = 6;
            }
        }
        initializeDeck(cardType);
    }

    /** Initializes the deck of cards of the specified type.
     * Reads the JSON files containing the front and back cards,
     * and populates the frontCards and backCards lists with the read cards.
     *
     * @author Sofia Maule
     * @param cardType the type of cards to initialize the deck
     */
    private void initializeDeck(CardType cardType) {
        String frontFileName = cardType.toString() + "CardsFront.json";
        String backFileName = cardType.toString() + "CardsBack.json";

        try {
            // Leggi dal file JSON frontCards
            FileReader frontReader = new FileReader(frontFileName);
            Gson gson = new Gson();
            Type frontCardListType = new TypeToken<ArrayList<PlayableCard>>() {}.getType();
            ArrayList<PlayableCard> frontCardList = gson.fromJson(frontReader, frontCardListType);
            //deserializza le info lette nel file nel frontCardListType corretto (es.GoldCard)
            frontCards.addAll(frontCardList);
            frontReader.close();

            // Leggi dal file JSON backCards
            FileReader backReader = new FileReader(backFileName);
            ArrayList<PlayableCard> backCardList = gson.fromJson(backReader, frontCardListType);
            backCards.addAll(backCardList);
            backReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* CONTROLLARE perchè la struttursa non può essere cambiata
    _> se non serve meglio
    public void shuffle() {
        returnCard(); // noncambio struttura e ritorno semplicemente un'altra carta tornata random
    }     */

    /**
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
    public PlayableCard[] returnCard() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(frontCards.size()); // Generates a random index within the range of the deck size
        // randomIndex = randomPosition = randomCardID -> same ID for front and back
        // Retrieve the cards at the random index from both frontCards and backCards arrays
        PlayableCard frontCard = frontCards.get(randomIndex);
        PlayableCard backCard = backCards.get(randomIndex);

        // Decrease the numCards attribute of the deck
        numCards--;

        // Remove the retrieved cards from the deck
        frontCards.remove(randomIndex);
        backCards.remove(randomIndex);

        // Return the retrieved cards as an array
        return new PlayableCard[]{frontCard, backCard};
    }
}




