package ch.uzh.ifi.hase.soprafs23.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scores {
    private Map<String,ArrayList<Map<String,Double>>> roundScore;
    private final Map<String, Integer> players_points;

    public Scores() {
        this.players_points = new HashMap<>();
        this.roundScore = new HashMap<>();
    }

    public void updateRoundScore(Map<String,Double> playerGuesses, String username, Food food){
        int player_points = 0;
        // todo handle what to do if player did not guess. Easiest way just give a high amount of points
        for ( String playerGuessFoodKey : playerGuesses.keySet()){
            double absoluteDeviation = Math.abs(playerGuesses.get(playerGuessFoodKey) - food.getNutritionValues().get(playerGuessFoodKey));
            player_points += absoluteDeviation;
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
            roundScore.put(playerGuessFoodKey,roundFoodScore);
        }
        players_points.putIfAbsent(username, 0);
        players_points.put(username, players_points.get(username) + player_points);
    }

    public Map<String,ArrayList<Map<String,Double>>> getRoundScore(){
        return roundScore;
    }

    public Map<String,Integer> getPlacement(){
        //todo eventually sort and take the 4 best plus the player and return in the order of points
        return players_points;
    }


}





