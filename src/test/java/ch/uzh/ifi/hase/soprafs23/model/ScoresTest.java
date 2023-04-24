package ch.uzh.ifi.hase.soprafs23.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class ScoresTest {
    private Scores scores;

    @BeforeEach
    public void setUp() {
        scores = new Scores();
    }
    @Test
    public void testUpdateRoundScore() {

        Map<String, Double> playerGuesses = new HashMap<>();
        playerGuesses.put("calories", 100.0);
        playerGuesses.put("fat", 20.0);
        playerGuesses.put("protein", 10.0);
        playerGuesses.put("carbs", 30.0);
        playerGuesses.put("sugar",5.0);

        Map<String, Double> actualValues = new HashMap<>();
        actualValues.put("calories", 100.0);
        actualValues.put("fat", 20.0);
        actualValues.put("protein", 10.0);
        actualValues.put("carbs", 30.0);
        actualValues.put("sugar",5.0);
        String username = "player1";

        Food food = new Food("chicken", actualValues, "prettyImmage");
        System.out.println(playerGuesses.get("fat"));
        scores.updateRoundScore(playerGuesses, username, food);

        Map<String, ArrayList<Map<String, Double>>> roundScore = scores.getRoundScore();

        //Check, that everything is in the round Score
        Assertions.assertEquals(5, roundScore.size());
        Assertions.assertTrue(roundScore.containsKey("calories"));
        Assertions.assertTrue(roundScore.containsKey("fat"));
        Assertions.assertTrue(roundScore.containsKey("protein"));
        Assertions.assertTrue(roundScore.containsKey("carbs"));
        Assertions.assertTrue(roundScore.containsKey("sugar"));

/*
        ArrayList<Map<String, Double>> caloriesScore = roundScore.get("calories");
        Assertions.assertEquals(3, caloriesScore.size());

        Map<String, Double> realValues = caloriesScore.get(0);
        Assertions.assertEquals(200.0, realValues.get("actualValues"));

        Map<String, Double> guessedValues = caloriesScore.get(1);
        Assertions.assertEquals(100.0, guessedValues.get("guessedValues"));

        Map<String, Double> deviation = caloriesScore.get(2);
        Assertions.assertEquals(100.0, deviation.get("deviations"));*/
    }
    /*
    @Test
    public void testGetPlacement() {
        Map<String, Double> playerGuesses = new HashMap<>();
        playerGuesses.put("calories", 100.0);
        playerGuesses.put("fat", 20.0);
        playerGuesses.put("protein", 10.0);
        playerGuesses.put("carbs", 30.0);
        String username = "player1";
        Food food = new Food("chicken", 200, 10, 40, 5);
        scores.updateRoundScore(playerGuesses, username, food);

        Map<String, Integer> placement = scores.getPlacement();

        Assertions.assertEquals(1, placement.size());
        Assertions.assertTrue(placement.containsKey("player1"));
        Assertions.assertEquals(200, placement.get("player1"));
    }*/
}
