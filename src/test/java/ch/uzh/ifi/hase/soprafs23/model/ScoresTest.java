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
        scores.updateRoundScore(playerGuesses, username, food);

        Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScore = scores.getRoundScore();

        //Check, that everything is in the round Score
        Assertions.assertEquals(1, roundScore.size());
        Assertions.assertTrue(roundScore.get("player1").containsKey("calories"));
        Assertions.assertTrue(roundScore.get("player1").containsKey("fat"));
        Assertions.assertTrue(roundScore.get("player1").containsKey("protein"));
        Assertions.assertTrue(roundScore.get("player1").containsKey("carbs"));
        Assertions.assertTrue(roundScore.get("player1").containsKey("sugar"));

    }
    @Test
    public void testUpdateRoundScoreThreePlayer() {
        String username = "player1";
        String username1 = "player2";
        String username2 = "player3";

        Map<String, Double> playerGuesses = new HashMap<>();
        playerGuesses.put("calories", 100.0);
        playerGuesses.put("fat", 20.0);
        playerGuesses.put("protein", 10.0);
        playerGuesses.put("carbs", 30.0);


        Map<String, Double> playerGuesses1 = new HashMap<>();
        playerGuesses1.put("calories", 111.0);
        playerGuesses1.put("fat", 5.0);
        playerGuesses1.put("protein", 20.0);
        playerGuesses1.put("carbs", 20.0);
        playerGuesses1.put("sugar",50.0);

        Map<String, Double> playerGuesses2 = new HashMap<>();
        playerGuesses2.put("calories", 100.0);
        playerGuesses2.put("fat", 11.0);
        playerGuesses2.put("protein", 20.0);
        playerGuesses2.put("carbs", 20.0);
        playerGuesses2.put("sugar",50.0);

        Map<String, Double> actualValues = new HashMap<>();
        actualValues.put("calories", 100.0);
        actualValues.put("fat", 20.0);
        actualValues.put("protein", 10.0);
        actualValues.put("carbs", 30.0);
        actualValues.put("sugar",5.0);


        Food food = new Food("chicken", actualValues, "prettyImmage");

        scores.updateRoundScore(playerGuesses, username, food);
        scores.updateRoundScore(playerGuesses1, username1, food);
        scores.updateRoundScore(playerGuesses2, username2, food);
        Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScore = scores.getRoundScore();

        //Check, that everything is in the round Score (player1)
        Assertions.assertEquals(3, roundScore.size());
        Assertions.assertTrue(roundScore.get("player1").containsKey("calories"));
        Assertions.assertTrue(roundScore.get("player1").containsKey("fat"));
        Assertions.assertTrue(roundScore.get("player1").containsKey("protein"));
        Assertions.assertTrue(roundScore.get("player1").containsKey("carbs"));
        Assertions.assertEquals( 400.0,roundScore.get("player1").get("points").get(0).get("points"));

        //player2
        Assertions.assertTrue(roundScore.get("player2").containsKey("calories"));
        Assertions.assertTrue(roundScore.get("player2").containsKey("fat"));
        Assertions.assertTrue(roundScore.get("player2").containsKey("protein"));
        Assertions.assertTrue(roundScore.get("player2").containsKey("carbs"));
        Assertions.assertEquals( 0.0,roundScore.get("player2").get("points").get(0).get("points"));

        //player 3
        Assertions.assertTrue(roundScore.get("player3").containsKey("calories"));
        Assertions.assertTrue(roundScore.get("player3").containsKey("fat"));
        Assertions.assertTrue(roundScore.get("player3").containsKey("protein"));
        Assertions.assertTrue(roundScore.get("player3").containsKey("carbs"));
        Assertions.assertEquals( 119.0,roundScore.get("player3").get("points").get(0).get("points"));
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
