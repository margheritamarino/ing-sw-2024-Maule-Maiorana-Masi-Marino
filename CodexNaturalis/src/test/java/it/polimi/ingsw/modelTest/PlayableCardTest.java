package it.polimi.ingsw.modelTest;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.FileReadException;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.GoldCard;
import it.polimi.ingsw.model.cards.PlayableCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class PlayableCardTest {
    private Deck goldCardsDeck;
    private  Deck ResourceCardsDeck;
    private  Deck InitialcardsDeck;
    private ArrayList<PlayableCard> goldCardsFront = new ArrayList<>();
    private ArrayList<PlayableCard> goldCardsBack = new ArrayList<>();

    @Test
    void testInitializeGoldDeck() throws FileNotFoundException, FileReadException {
        // Creazione di un oggetto Deck
        goldCardsDeck= new Deck(CardType.GoldCard);
        goldCardsFront = goldCardsDeck.getFrontCards();
        goldCardsBack = goldCardsDeck.getBackCards();

        assertTrue(goldCardsFront.get(0).getCardID() == 0);


    }
    @Test
    void JSONBlueDevelopmentArray() {
        Gson gson = new Gson();
        JsonReader reader = null;

        try {
            reader = new JsonReader(new FileReader("./src/main/resources/json/GoldCardsFront.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Type DevelopmentArray = new TypeToken<ArrayList<DevelopmentCard>>() {}.getType();
        ArrayList<DevelopmentCard> blueDevCards = gson.fromJson(reader, DevelopmentArray);
        assertTrue(blueDevCards.get(0).getVictoryPoints() == 12);
        assertTrue(blueDevCards.get(0).getLevel() == 3);
        assertTrue(blueDevCards.get(0).getColor() == CardColor.BLUE);
    }

}
