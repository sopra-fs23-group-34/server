package ch.uzh.ifi.hase.soprafs23.model;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.max;

public class Scores {

    private final Map<String, Integer> players_points;
    private final Map<String, Map> playerRoundScores;
    public Scores(Map<String, Map> playerRoundScores) {
        this.playerRoundScores = playerRoundScores;
        this.players_points = new HashMap<>();
    }

    public Map<String, Map> computeRoundScores(Map<String,Double> playerGuesses, String username, Food food){
        //Map<String, ArrayList<Map<String,Double>>> roundScore = new HashMap<>();
        // todo handle what to do if player did not guess. Easiest way just give a high amount of points
        Map<String,Map> playerGuessedDetails = new HashMap<>(); //Map for all the different guessed objects
        Map<String, Double> pointsTotal = new HashMap<>();
        double player_points = 0;

        System.out.println(playerGuesses.get("carbs"));

        for ( String playerGuessFoodKey : playerGuesses.keySet()){
            Map<String, Double> playerGuessedValues = new HashMap<>(); //Map for all the different guessed values
            System.out.println("inloop");
            double absoluteMalus = 0;
            //playerGuessedDetails.put(playerGuessFoodKey,playerGuessedValues);

            playerGuessedValues.put("guessed",playerGuesses.get(playerGuessFoodKey));

            playerGuessedValues.put("realValue",food.getNutritionValues().get(playerGuessFoodKey));
            /*ArrayList<Map<String,Double>> roundFoodScore = new ArrayList<>();
            Map<String, Double> guessed_values = new HashMap<>();
            Map<String, Integer> points = new HashMap<>();
            Map<String,ArrayList> roundPoints= (Map<String, ArrayList>) new ArrayList<Map>();*/
            //if a player did guess, then give him points
            if (!playerGuesses.keySet().equals(null)){
                absoluteMalus = Math.abs(playerGuesses.get(playerGuessFoodKey) - food.getNutritionValues().get(playerGuessFoodKey));
                player_points += max(100-(absoluteMalus*absoluteMalus),0);
            }
            player_points += absoluteMalus;
            /*
            guessed_values.put("guessedValues", playerGuesses.get(playerGuessFoodKey));
            points.put("pointsRound",player_points);
            players_points.putIfAbsent(username, 0);
            players_points.put(username, players_points.get(username) + player_points);*/
            /*
             ArrayList<Map<String,Double>> roundFoodScore = new ArrayList<>();
             Map<String,Double> real_values = new HashMap<>();
            real_values.put("actualValues", food.getNutritionValues().get(playerGuessFoodKey));
            Map<String,Double> guessed_values = new HashMap<>();
            guessed_values.put("guessedValues", playerGuesses.get(playerGuessFoodKey));
            Map<String,Double> deviation = new HashMap<>();
            deviation.put("deviations", absoluteDeviation);
            roundFoodScore.add(real_values);
            roundFoodScore.add(guessed_values);
            roundFoodScore.add(deviation);
            */
            //roundScore.put(playerGuessFoodKey,roundFoodScore);
            playerGuessedDetails.put(playerGuessFoodKey, playerGuessedValues);
        }
        pointsTotal.put("pointsTotal",player_points);
        playerGuessedDetails.put("points",pointsTotal);
        playerRoundScores.put(username,playerGuessedDetails);
        //playerGuessedDetails.put("points",playerGuessedValues);
        //playerGuessedValues.put("pointsScored",player_points);
        // update players_points
        int currentScrore = 0;
        if(players_points.get(username)!= null){
            currentScrore = players_points.get(username);
        }

        currentScrore = currentScrore + (int)player_points;
        players_points.put(username,currentScrore);
        System.out.println(currentScrore);
        System.out.println(playerRoundScores);
        return playerRoundScores;
    }
    public Map<String,Integer> getPlacement(){
        return players_points;
    }
    public Map<String, Map> getPlayerRoundScores(){
        return playerRoundScores;
    }




}





