package ch.uzh.ifi.hase.soprafs23.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ScoresTest {

    Map<String, Map> roundScores = new HashMap<>();
    Scores scores = new Scores(roundScores);

    @Test
    public void testComputeRoundScores_withPlayerGuessesAndFood_returnsPlayerRoundScores() {
        // arrange
        Map<String, Double> nutritionVales = new HashMap<>();
        nutritionVales.put("carbs", 20.0);
        nutritionVales.put("protein", 1.0);
        nutritionVales.put("fat", 1.0);
        nutritionVales.put("calories", 95.0);
        nutritionVales.put("sugar", 5.0);
        Map<String, Double> playerGuesses = new HashMap<>();
        playerGuesses.put("carbs", 20.0);
        playerGuesses.put("protein", 1.0);
        playerGuesses.put("fat", 1.0);
        playerGuesses.put("calories", 95.0);
        playerGuesses.put("sugar", 5.0);
        String username = "player1";
        String username2 = "player2";
        Food food = new Food("apple", nutritionVales, "image");

        System.out.println(nutritionVales.get("carbs"));
        // act
        Map<String, Map> playerRoundScores = scores.computeRoundScores(playerGuesses, username, food);
        Map<String, Map> playerRoundScoress = scores.computeRoundScores(playerGuesses, username2, food);
        //System.out.println(playerRoundScores);
/*
        // assert
        assertNotNull(playerRoundScores);
        assertEquals(1, playerRoundScores.size());
        assertTrue(playerRoundScores.containsKey(username));
        Map<String, Map> playerGuessedDetails = playerRoundScores.get(username);
        assertNotNull(playerGuessedDetails);
        assertEquals(4, playerGuessedDetails.size()); // 3 guessed values + points
        assertTrue(playerGuessedDetails.containsKey("calories"));
        assertTrue(playerGuessedDetails.containsKey("protein"));
        assertTrue(playerGuessedDetails.containsKey("fat"));
        assertTrue(playerGuessedDetails.containsKey("points"));
        Map<String, Double> playerGuessedValues = playerGuessedDetails.get("points");
        assertNotNull(playerGuessedValues);
        assertEquals(1, playerGuessedValues.size());
        assertTrue(playerGuessedValues.containsKey("pointsScored"));
        assertEquals(10100.0, playerGuessedValues.get("pointsScored"), 0.01);*/
    }

/*
    @Test
    public void testComputeRoundScores_withNullPlayerGuesses_returnsPlayerRoundScoresWithHighPoints() {
        // arrange
        Map<String, Double> playerGuesses = null;
        String username = "player2";
        Food food = new Food("banana", "fruit", "yellow", 70.0, 5.0, 2.0);

        // act
        Map<String, Map> playerRoundScores = myClass.computeRoundScores(playerGuesses, username, food);

        // assert
        assertNotNull(playerRoundScores);
        assertEquals(1, playerRoundScores.size());
        assertTrue(playerRoundScores.containsKey(username));
        Map<String, Map> playerGuessedDetails = playerRoundScores.get(username);
        assertNotNull(playerGuessedDetails);
        assertEquals(1, playerGuessedDetails.size()); // only points
        assertTrue(playerGuessedDetails.containsKey("points"));
        Map<String, Double> playerGuessedValues = playerGuessedDetails.get("points");
        assertNotNull(playerGuessedValues);
        assertEquals(1, playerGuessedValues.size());
        assertTrue(playerGuessedValues.containsKey("pointsScored"));
        assertEquals(300.0, playerGuessedValues.get("pointsScored"), 0.01);
    }

    @Test
    public void testComputeRoundScores_withMissingPlayerGuess_returnsPlayerRoundScoresWithHighPoints() {
        // arrange
        Map<String, Double> playerGuesses = new HashMap<>();
        playerGuesses.put("calories", 100.0);
        String username = "player3";
        Food food = new Food("pear", "fruit", "green", 60.0, 3.0, 1.0);

        // act


    }*/
}
