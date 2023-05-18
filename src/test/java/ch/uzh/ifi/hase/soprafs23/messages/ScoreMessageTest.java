package ch.uzh.ifi.hase.soprafs23.messages;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreMessageTest {
    @Test
    void testGetTopic() {
        ArrayList foodGuesses = new ArrayList();
        HashMap roundScores = new HashMap<String,HashMap>();
        HashMap middleMap = new HashMap<String, ArrayList>();
        HashMap mostInner = new HashMap<String, Double>();
        mostInner.put("carbs",20);
        foodGuesses.add(mostInner);
        middleMap.put("guess",foodGuesses);
        roundScores.put("name",middleMap);

        RoundScoreMessage message = new RoundScoreMessage("topic",roundScores);
        assertEquals("topic", message.getTopic());
    }
    @Test
    void testGetContent() {
        ArrayList foodGuesses = new ArrayList();
        HashMap roundScores = new HashMap<String,HashMap>();
        HashMap middleMap = new HashMap<String, ArrayList>();
        HashMap mostInner = new HashMap<String, Double>();
        mostInner.put("carbs",20);
        foodGuesses.add(mostInner);
        middleMap.put("guess",foodGuesses);
        roundScores.put("name",middleMap);

        // Create a new MapMessage object with sample data
        RoundScoreMessage message = new RoundScoreMessage("topic", roundScores);

        // Get the content of the message
        Map<String, Map<String, ArrayList<Map<String, Double>>>> content = message.getContent();

        // Check that the content is equal to the expected value
        assertEquals(roundScores, content);
    }
    @Test
    void GetTypeTest(){
        ArrayList foodGuesses = new ArrayList();
        HashMap roundScores = new HashMap<String,HashMap>();
        HashMap middleMap = new HashMap<String, ArrayList>();
        HashMap mostInner = new HashMap<String, Double>();
        mostInner.put("carbs",20);
        foodGuesses.add(mostInner);
        middleMap.put("guess",foodGuesses);
        roundScores.put("name",middleMap);

        RoundScoreMessage message = new RoundScoreMessage("map", roundScores);
        String s = message.getType();
        assertEquals(null,s);
    }
}
