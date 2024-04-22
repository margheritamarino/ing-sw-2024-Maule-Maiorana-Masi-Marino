package it.polimi.ingsw.modelTest;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileNotFoundException;

import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.cards.PlayableCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;;



public class PlayableCardTest {

    @Test
    public void testGoldCardAttributes() {
        try {
            // Load the test JSON file containing the data for GoldCard with cardID = 0
            String testJsonFile = "goldCard_0.json"; // Update with the actual file name
            PlayableCard goldCard = loadGoldCardFromJson(testJsonFile);

            // Assert the attributes of the gold card
            Assertions.assertEquals(0, goldCard.getCardID());
            Assertions.assertTrue(goldCard.isFront());
            Assertions.assertEquals(CardType.ResourceCard, goldCard.getCardType());
            Assertions.assertEquals(0, goldCard.getVictoryPoints());
            Assertions.assertEquals(3, goldCard.getNumCorners());
            Assertions.assertEquals("Fungi", goldCard.getMainResource());
            Assertions.assertEquals(2, goldCard.getNumResources());
            Assertions.assertEquals("withResource", goldCard.getTLCorner());
            Assertions.assertEquals("Empty", goldCard.getTRCorner());
            Assertions.assertEquals("NoCorner", goldCard.getBRCorner());
            Assertions.assertEquals("withResource", goldCard.getBLCorner());
            Assertions.assertFalse(goldCard.hasSymbol());
            Assertions.assertNull(goldCard.getSymbol());

        } catch (FileNotFoundException e) {
            Assertions.fail("Test JSON file not found: " + e.getMessage());
        }
    }
    // Helper method to load a GoldCard object from a JSON file
    private PlayableCard loadGoldCardFromJson(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        try {
            // Read the JSON file and deserialize the PlayableCard object
            FileReader reader = new FileReader(fileName);
            PlayableCard goldCard = gson.fromJson(reader, PlayableCard.class);
            reader.close();
            return goldCard;
        } catch (FileNotFoundException e) {
            // Handle the exception if the file is not found
            throw new FileNotFoundException("Test JSON file not found: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions that might occur during deserialization
            throw new RuntimeException("Error loading GoldCard from JSON: " + e.getMessage());
        }
    }

}
