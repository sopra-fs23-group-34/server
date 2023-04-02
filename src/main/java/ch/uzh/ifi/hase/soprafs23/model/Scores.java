package ch.uzh.ifi.hase.soprafs23.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scores {

    private Map<String, Integer> players_points;

    public Scores() {
        this.players_points = new HashMap<>();
    }

    public Map<String,ArrayList<Map<String,Integer>>> computeRoundScores(Map<String,Integer> playerGuesses, String username, Food food){
        Map<String,ArrayList<Map<String,Integer>>> roundScore = new HashMap<>();
        int player_points = 0;
        // todo handle what to do if player did not guess. Easiest way just give a high amount of points
        for ( String playerGuessFoodKey : playerGuesses.keySet()){
            int absoluteDeviation = Math.abs(playerGuesses.get(playerGuessFoodKey) - food.getNutritionValues().get(playerGuessFoodKey));
            player_points += absoluteDeviation;
            ArrayList<Map<String,Integer>> roundFoodScore = new ArrayList<>();
            Map<String,Integer> real_values = new HashMap<>();
            real_values.put("actualValues", food.getNutritionValues().get(playerGuessFoodKey));
            Map<String,Integer> guessed_values = new HashMap<>();
            guessed_values.put("guessedValues", playerGuesses.get(playerGuessFoodKey));
            Map<String,Integer> deviation = new HashMap<>();
            deviation.put("deviations", absoluteDeviation);
            roundFoodScore.add(real_values);
            roundFoodScore.add(guessed_values);
            roundFoodScore.add(deviation);
            roundScore.put(playerGuessFoodKey,roundFoodScore);
        };
        players_points.putIfAbsent(username, 0);
        players_points.put(username, players_points.get(username) + player_points);
        return roundScore;
    }
    public Map<String,Integer> getPlacement(){
        //todo eventually sort and take the 4 best plus the player and return in the order of points
        return players_points;
    }


}





