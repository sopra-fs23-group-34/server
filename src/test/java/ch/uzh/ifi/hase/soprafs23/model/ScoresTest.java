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

        Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScore = scores.getRoundScore();

        //Check, that everything is in the round Score
        Assertions.assertEquals(1, roundScore.size());
        Assertions.assertTrue(roundScore.containsKey("calories"));
        Assertions.assertTrue(roundScore.containsKey("fat"));
        Assertions.assertTrue(roundScore.containsKey("protein"));
        Assertions.assertTrue(roundScore.containsKey("carbs"));
        Assertions.assertTrue(roundScore.containsKey("sugar"));

    }
    @Test
    public void testUpdateRoundScoreTwoPlayer() {
        String username = "player1";
        String username1 = "player2";

        Map<String, Double> playerGuesses = new HashMap<>();
        playerGuesses.put("calories", 100.0);
        playerGuesses.put("fat", 20.0);
        playerGuesses.put("protein", 10.0);
        playerGuesses.put("carbs", 30.0);
        playerGuesses.put("sugar",5.0);

        Map<String, Double> playerGuesses1 = new HashMap<>();
        playerGuesses1.put("calories", 100.0);
        playerGuesses1.put("fat", 11.0);
        playerGuesses1.put("protein", 10.0);
        playerGuesses1.put("carbs", 30.0);
        playerGuesses1.put("sugar",5.0);

        Map<String, Double> actualValues = new HashMap<>();
        actualValues.put("calories", 100.0);
        actualValues.put("fat", 20.0);
        actualValues.put("protein", 10.0);
        actualValues.put("carbs", 30.0);
        actualValues.put("sugar",5.0);


        Food food = new Food("chicken", actualValues, "prettyImmage");
        System.out.println(playerGuesses.get("fat"));
        scores.updateRoundScore(playerGuesses, username, food);
        scores.updateRoundScore(playerGuesses1, username1, food);
        Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScore = scores.getRoundScore();

        //Check, that everything is in the round Score
        Assertions.assertEquals(2, roundScore.size());
        Assertions.assertTrue(roundScore.containsKey("calories"));
        Assertions.assertTrue(roundScore.containsKey("fat"));
        Assertions.assertTrue(roundScore.containsKey("protein"));
        Assertions.assertTrue(roundScore.containsKey("carbs"));
        Assertions.assertTrue(roundScore.containsKey("sugar"));
        System.out.println(roundScore);
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
