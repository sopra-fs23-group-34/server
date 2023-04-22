package ch.uzh.ifi.hase.soprafs23.model;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    void testGetUsername() {
        Player player = new Player("Alice", 1L, false);
        assertEquals("Alice", player.getUsername());
    }
    @Test
    void testIsHost() {
        Player player = new Player("Alice", 1L, true);
        assertTrue(player.isHost());
        player = new Player("Bob", 2L, false);
        assertFalse(player.isHost());
    }
    @Test
    void testGetPoints() {
        Player player = new Player("Alice", 1L, false);
        player.setPoints(10);
        assertEquals(10, player.getPoints());
    }
    @Test
    void testGetGuesses() {
        Player player = new Player("Alice", 1L, false);
        Map<String, Double> guesses = new HashMap<>();
        guesses.put("guess1", 1.0);
        guesses.put("guess2", 2.0);
        player.setGuesses(guesses);
        assertEquals(guesses, player.getGuesses());
    }
}
