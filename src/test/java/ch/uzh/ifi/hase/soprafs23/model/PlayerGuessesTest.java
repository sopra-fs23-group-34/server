package ch.uzh.ifi.hase.soprafs23.model;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerGuessesTest {
    @Test
    void testGetContent() {
        PlayerGuesses playerGuesses = new PlayerGuesses();
        Map<String, Double> content = new HashMap<>();
        content.put("guess1", 1.0);
        content.put("guess2", 2.0);
        playerGuesses.setContent(content);
        assertEquals(content, playerGuesses.getContent());
    }
    @Test
    void testSetContent() {
        PlayerGuesses playerGuesses = new PlayerGuesses();
        Map<String, Double> content = new HashMap<>();
        content.put("guess1", 1.0);
        content.put("guess2", 2.0);
        playerGuesses.setContent(content);
        assertEquals(content, playerGuesses.getContent());
        Map<String, Double> newContent = new HashMap<>();
        newContent.put("guess3", 3.0);
        playerGuesses.setContent(newContent);
        assertEquals(newContent, playerGuesses.getContent());
    }
}
