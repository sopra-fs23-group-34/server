package ch.uzh.ifi.hase.soprafs23.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.round;

public class Scores {
    private Map<String,ArrayList<Map<String,Double>>> roundScore;
    private Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScoresAllPlayer;
    private final Map<String, Integer> players_points;

    public Scores() {
        this.players_points = new HashMap<>();
        this.roundScore = new HashMap<>();
        this.roundScoresAllPlayer = new HashMap<>();
    }
    /*public void sortMapDescending(){
        Stream<Map.Entry<K,V>> sorted = roundScoresAllPlayer.entrySet().stream().sorted(Map.Entry.comparingByValue());
    }*/
    public void updateRoundScore(Map<String,Double> playerGuesses, String username, Food food){
        int player_points = 0;
        // todo handle what to do if player did not guess. Easiest way just give a high amount of points
        for ( String playerGuessFoodKey : playerGuesses.keySet()){
            double absoluteDeviation = Math.abs(playerGuesses.get(playerGuessFoodKey) - food.getNutritionValues().get(playerGuessFoodKey));
            player_points += max(100-(absoluteDeviation*absoluteDeviation),0);
            ArrayList<Map<String,Double>> roundFoodScore = new ArrayList<>();
            Map<String,Double> real_values = new HashMap<>();
            real_values.put("actualValues", food.getNutritionValues().get(playerGuessFoodKey));
            Map<String,Double> guessed_values = new HashMap<>();
            guessed_values.put("guessedValues", playerGuesses.get(playerGuessFoodKey));
            Map<String,Double> deviation = new HashMap<>();
            deviation.put("deviations", absoluteDeviation);
            Map<String,Double> points = new HashMap<>();
            points.put("points", (double) player_points);
            roundFoodScore.add(real_values);
            roundFoodScore.add(guessed_values);
            roundFoodScore.add(deviation);
            roundFoodScore.add(points);
            roundScore.put(playerGuessFoodKey,roundFoodScore);
        }
        players_points.putIfAbsent(username, 0);
        players_points.put(username, players_points.get(username) + player_points);
        roundScoresAllPlayer.put(username,roundScore);
        //sortMapDescending();
        System.out.println(roundScoresAllPlayer);
    }

    public Map<String,Map<String,ArrayList<Map<String,Double>>>> getRoundScore(){
        return roundScoresAllPlayer;
    }

    public Map<String,Integer> getPlacement(){
        //todo eventually sort and take the 4 best plus the player and return in the order of points
        return players_points;
    }


}





