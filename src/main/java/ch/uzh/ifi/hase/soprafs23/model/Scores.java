package ch.uzh.ifi.hase.soprafs23.model;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.round;

public class Scores {
    private final Map<String,ArrayList<Map<String,Double>>> roundScore;
    private Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScoresAllPlayer;
    private Map<String, Integer> players_points;
    private final SortByRoundScores sortByRoundScores;

    public Scores() {
        this.players_points = new HashMap<>();
        this.roundScore = new HashMap<>();
        this.roundScoresAllPlayer = new HashMap<>();
        this.sortByRoundScores = new SortByRoundScores();
    }

    public void updateRoundScore(Map<String,Double> playerGuesses, String username, Food food){
        int player_points = 0;
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
            /*
            Map<String,Double> points = new HashMap<>();
            points.put("points", (double) player_points);*/

            roundFoodScore.add(real_values);
            roundFoodScore.add(guessed_values);
            roundFoodScore.add(deviation);
            //roundFoodScore.add(points);
            roundScore.put(playerGuessFoodKey,roundFoodScore);
        }
        ArrayList<Map<String,Double>> roundPointsTotal = new ArrayList<>();
        Map<String,Double> roundPointsTotalMap = new HashMap<>();
        roundPointsTotalMap.put("points",(double) player_points);
        roundPointsTotal.add(roundPointsTotalMap);
        roundScore.put("points",roundPointsTotal);
        players_points.putIfAbsent(username, 0);
        players_points.put(username, players_points.get(username) + player_points);
        System.out.println(roundScore);

        //deep copy the roundScore map
        Map<String,ArrayList<Map<String,Double>>> copyRoundScore = new HashMap<>();
        for(String o : roundScore.keySet()){
            copyRoundScore.put(o, roundScore.get(o));
        }
        copyRoundScore = Collections.unmodifiableMap(copyRoundScore);
        //put into the final variable
        roundScoresAllPlayer.put(username,copyRoundScore);

        //sort roundScores descending

        roundScoresAllPlayer = sortByRoundScores.sortByPointsAllScores(roundScoresAllPlayer);
        System.out.println(roundScoresAllPlayer);

    }

    public Map<String,Map<String,ArrayList<Map<String,Double>>>> getRoundScore(){
        return roundScoresAllPlayer;
    }

    public Map<String,Integer> getPlacement(){
        //todo eventually sort and take the 4 best plus the player and return in the order of points
        players_points = sortByRoundScores.sortPlayerPoints(players_points);
        System.out.println(players_points);
        return players_points;
    }


}





